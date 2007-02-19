/*
 * Copyright 2005 Sun Microsystems, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * $Id: EventingSupport.java,v 1.18 2007-01-14 17:52:34 denis_rachal Exp $
 */

package com.sun.ws.management.server;

import java.io.IOException;
import java.util.GregorianCalendar;
import java.util.UUID;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPException;
import javax.xml.xpath.XPathExpressionException;

import org.dmtf.schemas.wbem.wsman._1.wsman.DialectableMixedDataType;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xmlsoap.schemas.ws._2004._08.addressing.EndpointReferenceType;
import org.xmlsoap.schemas.ws._2004._08.addressing.ReferencePropertiesType;
import org.xmlsoap.schemas.ws._2004._08.eventing.DeliveryType;
import org.xmlsoap.schemas.ws._2004._08.eventing.Subscribe;
import org.xmlsoap.schemas.ws._2004._08.eventing.Unsubscribe;

import com.sun.ws.management.InternalErrorFault;
import com.sun.ws.management.UnsupportedFeatureFault;
import com.sun.ws.management.addressing.Addressing;
import com.sun.ws.management.enumeration.CannotProcessFilterFault;
import com.sun.ws.management.eventing.DeliveryModeRequestedUnavailableFault;
import com.sun.ws.management.eventing.EventSourceUnableToProcessFault;
import com.sun.ws.management.eventing.Eventing;
import com.sun.ws.management.eventing.EventingExtensions;
import com.sun.ws.management.eventing.FilteringRequestedUnavailableFault;
import com.sun.ws.management.eventing.InvalidMessageFault;
import com.sun.ws.management.soap.FaultException;
import com.sun.ws.management.soap.SOAP;
import com.sun.ws.management.transport.HttpClient;

/**
 * A helper class that encapsulates some of the arcane logic to manage
 * subscriptions using the WS-Eventing protocol.
 */
public final class EventingSupport extends BaseSupport {
    
    // TODO: add more delivery modes as they are implemented
    private static final String[] SUPPORTED_DELIVERY_MODES = {
        Eventing.PUSH_DELIVERY_MODE,
        EventingExtensions.PULL_DELIVERY_MODE
    };
    
    private EventingSupport() {}
    
    public static String[] getSupportedDeliveryModes() {
        return SUPPORTED_DELIVERY_MODES;
    }
    
    public static boolean isDeliveryModeSupported(final String deliveryMode) {
        for (final String mode : SUPPORTED_DELIVERY_MODES) {
            if (mode.equals(deliveryMode)) {
                return true;
            }
        }
        return false;
    }
    
    // the EventingExtensions.PULL_DELIVERY_MODE is handled by EnumerationSupport
    public static Object subscribe(final Eventing request, final Eventing response,
        final NamespaceMap... namespaces)
        throws DatatypeConfigurationException, SOAPException, JAXBException, FaultException {
        
        final Subscribe subscribe = request.getSubscribe();
        Filter filter = null;
        try {
             filter = createFilter(request);
        } catch(FilteringRequestedUnavailableFault fex) {
            throw fex;
        }
        catch(Exception ex) {
            throw new EventSourceUnableToProcessFault(ex.getMessage());
        }
        
        final EndpointReferenceType endTo = subscribe.getEndTo();
        if (endTo != null) {
            throw new UnsupportedFeatureFault(UnsupportedFeatureFault.Detail.ADDRESSING_MODE);
        }
        
        final DeliveryType delivery = subscribe.getDelivery();
        String deliveryMode = delivery.getMode();
        if (deliveryMode == null) {
            // implied value
            deliveryMode = Eventing.PUSH_DELIVERY_MODE;
        }
        
        if (!isDeliveryModeSupported(deliveryMode)) {
            throw new DeliveryModeRequestedUnavailableFault(SUPPORTED_DELIVERY_MODES);
        }
        
        EndpointReferenceType notifyTo = null;
        for (final Object content : delivery.getContent()) {
            final Class contentClass = content.getClass();
            if (JAXBElement.class.equals(contentClass)) {
                final JAXBElement<Object> element = (JAXBElement<Object>) content;
                final QName name = element.getName();
                final Object item = element.getValue();
                if (item instanceof EndpointReferenceType) {
                    final EndpointReferenceType epr = (EndpointReferenceType) item;
                    if (Eventing.NOTIFY_TO.equals(name)) {
                        notifyTo = epr;
                    }
                }
            }
        }
        if (notifyTo == null) {
            throw new InvalidMessageFault("Event destination not specified: missing NotifyTo element");
        }
        if (notifyTo.getAddress() == null) {
            throw new InvalidMessageFault("Event destination not specified: missing NotifyTo.Address element");
        }
        
        EventingContext ctx = new EventingContext(
                initExpiration(subscribe.getExpires()),
                filter,
                notifyTo);
        
        final UUID context = initContext(ctx);
        response.setSubscribeResponse(
            createSubscriptionManager(request, response, context),
            ctx.getExpiration());
        return context;
    }
    
    public static EndpointReferenceType createSubscriptionManager(
        final Addressing request, final Addressing response,
        final Object context) throws SOAPException, JAXBException {
        final ReferencePropertiesType refp = Addressing.FACTORY.createReferencePropertiesType();
        final Document doc = response.newDocument();
        final Element identifier = doc.createElementNS(Eventing.IDENTIFIER.getNamespaceURI(),
            Eventing.IDENTIFIER.getPrefix() + ":" + Eventing.IDENTIFIER.getLocalPart());
        identifier.setTextContent(context.toString());
        doc.appendChild(identifier);
        refp.getAny().add(doc.getDocumentElement());
        return Addressing.createEndpointReference(request.getTo(), refp, null, null, null);
    }
    
    public static void unsubscribe(final Eventing request, final Eventing response)
    throws SOAPException, JAXBException, FaultException {
        
        final Unsubscribe unsubscribe = request.getUnsubscribe();
        if (unsubscribe == null) {
            throw new InvalidMessageFault("Missing Unsubsribe element");
        }
        
        final String identifier = request.getIdentifier();
        if (identifier == null) {
            throw new InvalidMessageFault("Missing Identifier header element");
            
        }
        
        final Object found = removeContext(UUID.fromString(identifier));
        if (found == null) {
            /*
             * TODO: Convert to InvalidContextFault when available in
             * updated WS-Management specification
             */
            throw new InvalidMessageFault("Subscription with Identifier: " +
                identifier + " not found");
        }
        
        response.setIdentifier(identifier);
    }
    
    // TODO: avoid blocking the sender - use a thread pool to send notifications
    public static boolean sendEvent(final Object context, final Addressing msg,
        final NamespaceMap nsMap)
        throws SOAPException, JAXBException, IOException, XPathExpressionException, Exception {
        
        assert datatypeFactory != null : UNINITIALIZED;
        
        final BaseContext bctx = getContext(context);
        if (bctx == null) {
            throw new RuntimeException("Context not found: subscription expired?");
        }
        if (!(bctx instanceof EventingContext)) {
            throw new RuntimeException("Context not found");
        }
        final EventingContext ctx = (EventingContext) bctx;
        
        final GregorianCalendar now = new GregorianCalendar();
        final XMLGregorianCalendar nowXml = datatypeFactory.newXMLGregorianCalendar(now);
        if (ctx.isExpired(nowXml)) {
            removeContext(context);
            throw new RuntimeException("Subscription expired");
        }
        
        // the filter is only applied to the first child in soap body
        final Node content = msg.getBody().getFirstChild();
        try {
            if (ctx.evaluate(content) == null)
                return false;
        }catch(XPathExpressionException ex) {
            throw ex;
        }
        
        final EndpointReferenceType notifyTo = ctx.getNotifyTo();
        final ReferencePropertiesType refp = notifyTo.getReferenceProperties();
        if (refp != null) {
            msg.addHeaders(refp);
        }
        msg.setTo(notifyTo.getAddress().getValue());
        msg.setMessageId(UUID_SCHEME + UUID.randomUUID().toString());
        HttpClient.sendResponse(msg);
        return true;
    }
    
    /**
     * Create a Filter from an Eventing request
     * 
     * @return Returns a Filter object if a filter exists in the request, otherwise null.
     * @throws CannotProcessFilterFault, FilteringRequestedUnavailableFault, InternalErrorFault 
     */
    public static Filter createFilter(final Eventing request) 
                     throws CannotProcessFilterFault, FilteringRequestedUnavailableFault {
    	try {
			final EventingExtensions evtxRequest = new EventingExtensions(request);
			final Subscribe subscribe = evtxRequest.getSubscribe();
			final org.xmlsoap.schemas.ws._2004._08.eventing.FilterType evtFilter = subscribe.getFilter();
			final DialectableMixedDataType evtxFilter = evtxRequest.getWsmanFilter();

			if ((evtFilter == null) && (evtxFilter == null)) {
				return null;
			}
			if ((evtFilter != null) && (evtxFilter != null)) {
				// Both are not allowed. Throw an exception
				throw new CannotProcessFilterFault(
						SOAP.createFaultDetail(
										"Both wse:Filter and wsman:Filter were specified in the request. Only one is allowed.",
										null, null, null));
			}

			final NamespaceMap nsMap = getNamespaceMap(request);
			
			if (evtxFilter != null)
				return createFilter(evtxFilter.getDialect(), 
						evtxFilter.getContent(), nsMap);
			else
				return createFilter(evtFilter.getDialect(), 
						evtFilter.getContent(), nsMap);
		} catch (SOAPException e) {
			throw new InternalErrorFault(e.getMessage());
		} catch (JAXBException e) {
			throw new InternalErrorFault(e.getMessage());
		}
    }
    
	private static NamespaceMap getNamespaceMap(final Eventing request) {
        final NamespaceMap nsMap;
        final SOAPBody body = request.getBody();
        
        NodeList wsmanFilter = body.getElementsByTagNameNS(EventingExtensions.FILTER.getNamespaceURI(),
        		                                           EventingExtensions.FILTER.getLocalPart());
    	NodeList evtFilter = body.getElementsByTagNameNS(Eventing.FILTER.getNamespaceURI(),
                                                         Eventing.FILTER.getLocalPart());
        if ((wsmanFilter != null) && (wsmanFilter.getLength() > 0)) {
        	nsMap = new NamespaceMap(wsmanFilter.item(0));
        } else if ((evtFilter != null) && (evtFilter.getLength() > 0)) {
        	nsMap = new NamespaceMap(evtFilter.item(0));
        } else {
        	NodeList evtElement = body.getElementsByTagNameNS(Eventing.SUBSCRIBE.getNamespaceURI(),
        			                                           Eventing.SUBSCRIBE.getLocalPart());
            nsMap = new NamespaceMap(evtElement.item(0));
        }
        return nsMap;
	}
}
