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
 ** Copyright (C) 2006, 2007 Hewlett-Packard Development Company, L.P.
 **
 ** Authors: Simeon Pinder (simeon.pinder@hp.com), Denis Rachal (denis.rachal@hp.com),
 ** Nancy Beers (nancy.beers@hp.com), William Reichardt
 **
 **$Log: not supported by cvs2svn $
 **Revision 1.53  2007/10/30 09:27:30  jfdenise
 **WiseMan to take benefit of Sun JAX-WS RI Message API and WS-A offered support.
 **Commit a new JAX-WS Endpoint and a set of Message abstractions to implement WS-Management Request and Response processing on the server side.
 **
 **Revision 1.52  2007/10/02 10:43:43  jfdenise
 **Fix for bug ID 134, Enumeration Iterator look up is static
 **Applied to Enumeration and Eventing
 **
 **Revision 1.51  2007/05/30 20:31:04  nbeers
 **Add HP copyright header
 **
 **
 * $Id: WSEnumerationSupport.java,v 1.1 2007-10-31 12:25:38 jfdenise Exp $
 */

package com.sun.ws.management.server;

import com.sun.ws.management.Message;
import com.sun.ws.management.server.message.WSEnumerationRequest;
import com.sun.ws.management.server.message.WSEnumerationResponse;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPException;
import javax.xml.xpath.XPathException;

import org.dmtf.schemas.wbem.wsman._1.wsman.AttributableURI;
import org.dmtf.schemas.wbem.wsman._1.wsman.DialectableMixedDataType;
import org.dmtf.schemas.wbem.wsman._1.wsman.EnumerationModeType;
import org.dmtf.schemas.wbem.wsman._1.wsman.MixedDataType;
import org.dmtf.schemas.wbem.wsman._1.wsman.SelectorSetType;
import org.dmtf.schemas.wbem.wsman._1.wsman.SelectorType;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xmlsoap.schemas.ws._2004._08.addressing.EndpointReferenceType;
import org.xmlsoap.schemas.ws._2004._08.addressing.ReferenceParametersType;
import org.xmlsoap.schemas.ws._2004._08.eventing.Subscribe;
import org.xmlsoap.schemas.ws._2004._08.eventing.Unsubscribe;
import org.xmlsoap.schemas.ws._2004._09.enumeration.Enumerate;
import org.xmlsoap.schemas.ws._2004._09.enumeration.EnumerationContextType;
import org.xmlsoap.schemas.ws._2004._09.enumeration.Pull;
import org.xmlsoap.schemas.ws._2004._09.enumeration.Release;

import com.sun.ws.management.InternalErrorFault;
import com.sun.ws.management.Management;
import com.sun.ws.management.UnsupportedFeatureFault;
import com.sun.ws.management.addressing.ActionNotSupportedFault;
import com.sun.ws.management.addressing.Addressing;
import com.sun.ws.management.enumeration.CannotProcessFilterFault;
import com.sun.ws.management.enumeration.Enumeration;
import com.sun.ws.management.enumeration.EnumerationExtensions;
import com.sun.ws.management.enumeration.InvalidEnumerationContextFault;
import com.sun.ws.management.enumeration.TimedOutFault;
import com.sun.ws.management.eventing.FilteringRequestedUnavailableFault;
import com.sun.ws.management.eventing.InvalidMessageFault;
import com.sun.ws.management.soap.FaultException;
import com.sun.ws.management.soap.SOAP;

/**
 * A helper class that encapsulates some of the arcane logic to allow data
 * sources to be enumerated using the WS-Enumeration protocol.
 *
 * @see IteratorFactory
 * @see EnumerationIterator
 */
public final class WSEnumerationSupport extends WSEnumerationBaseSupport {
    
    private static final int DEFAULT_ITEM_COUNT = 1;
    private static final int DEFAULT_EXPIRATION_MILLIS = 60000;
    private static final long DEFAULT_MAX_TIMEOUT_MILLIS = 300000;
    private static Duration defaultExpiration = null;
    
    static {
        defaultExpiration = datatypeFactory.newDuration(DEFAULT_EXPIRATION_MILLIS);
    }
    
    private WSEnumerationSupport() {
        // super();
    }
    
    public static void enumerate(final HandlerContext handlerContext,
            final WSEnumerationRequest request,
            final WSEnumerationResponse response,
            final ContextListener listener,
            final WSEnumerationIteratorFactory factory)
            throws DatatypeConfigurationException, SOAPException,
            JAXBException, FaultException {
        
        final EnumerationIterator iterator = newIterator(factory,
                handlerContext, request, response);
        
        if (iterator == null) {
            throw new ActionNotSupportedFault();
        }
        
        enumerate(handlerContext, request, response, iterator, listener);
    }
    
    static EnumerationContext createContext(HandlerContext handlerContext,
            String expires, Filter filter,
            EnumerationModeType enumerationMode, EnumerationIterator iterator,
            ContextListener listener) {
        XMLGregorianCalendar expiration = initExpiration(expires);
        if (expiration == null) {
            final GregorianCalendar now = new GregorianCalendar();
            expiration = datatypeFactory.newXMLGregorianCalendar(now);
            expiration.add(defaultExpiration);
        }
        EnumerationContext ctx = new EnumerationContext(expiration, filter, enumerationMode,
                iterator, listener);
        return ctx;
    }
    
    public static void enumerate(final HandlerContext handlerContext,
            final WSEnumerationRequest request,
            final WSEnumerationResponse response,
            final EnumerationIterator iterator,
            final ContextListener listener)
            throws DatatypeConfigurationException, SOAPException,
            JAXBException, FaultException {
        EnumerationContext ctx = null;
        UUID context = null;
        
        try {
            assert datatypeFactory != null : UNINITIALIZED;
            assert defaultExpiration != null : UNINITIALIZED;
            
            String expires = null;
            Filter filter = null;
            
            final Enumerate enumerate = request.getEnumerate();
            EnumerationModeType enumerationMode = null;
            boolean optimize = false;
            int maxElements = DEFAULT_ITEM_COUNT;
            
            if (iterator.isFiltered() == false) {
                // We will do the filtering
                filter = createFilter(request);
            }
            
            if (enumerate.getEndTo() != null) {
                throw new UnsupportedFeatureFault(
                        UnsupportedFeatureFault.Detail.ADDRESSING_MODE);
            }
            
            expires = enumerate.getExpires();
            enumerationMode = request.getModeType();
            optimize = request.getOptimize();
            maxElements = request.getMaxElements();
            
            ctx = createContext(handlerContext, expires, filter,
                    enumerationMode, iterator, listener);
            
            context = initContext(handlerContext, ctx);
            
            if (optimize) {
                Duration maxTime = request.getMaxTime();
                
                final List<EnumerationItem> passed = new ArrayList<EnumerationItem>();
                final boolean more = doPull(handlerContext,
                        request,
                        response,
                        context,
                        ctx,
                        maxTime,
                        passed,
                        maxElements);
                
                response.setEnumerateResponse(context.toString(),
                        ctx.getExpiration(),
                        passed,
                        enumerationMode,
                        more);
            } else {
                // place an item count estimate if one was requested
                insertTotalItemCountEstimate(request, response, iterator);
                response.setEnumerateResponse(context.toString(), ctx
                        .getExpiration());
            }
        } catch (TimedOutFault e) {
            // Do not delete the context for timeouts
            throw e;
        } catch (FaultException e) {
            if ((ctx != null) && (ctx.isDeleted() == false)) {
                removeContext(handlerContext, ctx);
            }
            throw e;
        } catch (Throwable t) {
            if ((ctx != null) && (ctx.isDeleted() == false)) {
                removeContext(handlerContext, ctx);
            }
            throw new InternalErrorFault(t);
        }
    }
    
    /**
     * Handle a {@link org.xmlsoap.schemas.ws._2004._09.enumeration.Pull Pull}
     * request.
     *
     * @param request
     *            The incoming SOAP message that contains the
     *            {@link org.xmlsoap.schemas.ws._2004._09.enumeration.Pull Pull}
     *            request.
     *
     * @param response
     *            The empty SOAP message that will contain the
     *            {@link org.xmlsoap.schemas.ws._2004._09.enumeration.PullResponse PullResponse}.
     *
     * @throws InvalidEnumerationContextFault
     *             if the supplied context is missing, is not understood or is
     *             not found because it has expired or the server has been
     *             restarted.
     *
     * @throws TimedOutFault
     *             if the data source fails to provide the items to be returned
     *             within the specified
     *             {@link org.xmlsoap.schemas.ws._2004._09.enumeration.Pull#getMaxTime timeout}.
     */
    public static void pull(final HandlerContext handlerContext,
            final WSEnumerationRequest request,
            final WSEnumerationResponse response)
            throws SOAPException, JAXBException, FaultException {
        
        assert datatypeFactory != null : UNINITIALIZED;
        
        final Pull pull = request.getPull();
        if (pull == null) {
            throw new InvalidEnumerationContextFault();
        }
        
        final BigInteger maxChars = pull.getMaxCharacters();
        if (maxChars != null) {
            // TODO: add support for maxChars
            throw new UnsupportedFeatureFault(
                    UnsupportedFeatureFault.Detail.MAX_ENVELOPE_SIZE);
        }
        
        final EnumerationContextType contextType = pull.getEnumerationContext();
        final UUID context = extractContext(contextType);
        final EnumerationContext ctx = (EnumerationContext) getContext(context);
        if (ctx == null) {
            throw new InvalidEnumerationContextFault();
        }
        
        if (ctx.isDeleted()) {
            throw new InvalidEnumerationContextFault();
        }
        final GregorianCalendar now = new GregorianCalendar();
        final XMLGregorianCalendar nowXml = datatypeFactory.newXMLGregorianCalendar(now);
        if (ctx.isExpired(nowXml)) {
            removeContext(handlerContext, context);
            throw new InvalidEnumerationContextFault();
        }
        
        final BigInteger maxElementsBig = pull.getMaxElements();
        final int maxElements;
        if (maxElementsBig == null) {
            maxElements = DEFAULT_ITEM_COUNT;
        } else {
            // NOTE: downcasting from BigInteger to int
            maxElements = maxElementsBig.intValue();
        }
        
        Duration maxTime = request.getMaxTime();
        if (maxTime == null) {
            maxTime = pull.getMaxTime();
        }
        final List<EnumerationItem> passed = new ArrayList<EnumerationItem>();
        final boolean more = doPull(handlerContext, request, response, context,
                ctx, maxTime, passed, maxElements);
        
        if (more) {
            response.setPullResponse(passed, context.toString(), true,
                    ctx.getEnumerationMode());
        } else {
            response.setPullResponse(passed, null, false,
                    ctx.getEnumerationMode());
        }
    }
    
    private static boolean doPull(final HandlerContext handlerContext,
            final WSEnumerationRequest request,
            final WSEnumerationResponse response,
            final UUID context,
            final EnumerationContext ctx,
            final Duration maxTimeout,
            final List<EnumerationItem> passed,
            final int maxElements)
            throws SOAPException, JAXBException, FaultException {
        
        final EnumerationIterator iterator = ctx.getIterator();
        final GregorianCalendar start = new GregorianCalendar();
        
        long timeout = DEFAULT_MAX_TIMEOUT_MILLIS;
        if (maxTimeout != null) {
            timeout = maxTimeout.getTimeInMillis(start);
        }
        final long end = start.getTimeInMillis() + timeout;
        
        // Check the enumeration mode
        boolean includeItem = false;
        boolean includeEPR = false;
        final EnumerationModeType mode = ctx.getEnumerationMode();
        if (mode == null) {
            includeItem = true;
            includeEPR = false;
        } else {
            final String modeString = mode.value();
            if (modeString.equals(EnumerationExtensions.Mode.EnumerateEPR.toString())) {
                includeItem = false;
                includeEPR = true;
            } else if (modeString
                    .equals(EnumerationExtensions.Mode.EnumerateObjectAndEPR
                    .toString())) {
                includeItem = true;
                includeEPR = true;
            } else {
                removeContext(handlerContext, context);
                throw new UnsupportedFeatureFault(
                        UnsupportedFeatureFault.Detail.ENUMERATION_MODE);
            }
        }
        Boolean fragmentCheck = null;
        
        // Synchronize on the iterator
        synchronized (iterator) {
            try {
                // XXX REVISIT,
                // Need to use reflection to remove dependency on EventingExtensions and
                // Enumeration.
                if (iterator instanceof EnumerationPullIterator) {
                    Management mgt;
                    try {
                        mgt = new Management(request.toSOAPMessage());
                    } catch (Exception ex) {
                        throw new SOAPException(ex.toString());
                    }

                    Enumeration en = new Enumeration(mgt);
                    ((EnumerationPullIterator) iterator).startPull(
                            handlerContext, en);
                } else {
                    if(iterator instanceof WSEnumerationPullIterator)
                        ((WSEnumerationPullIterator) iterator).startPull(
                                handlerContext, request);
                }
                
                while ((passed.size() < maxElements) && (iterator.hasNext())) {
                    if (ctx.isDeleted()) {
                        // Context was deleted. Abort the request.
                        throw new InvalidEnumerationContextFault();
                    }
                    
                    // Check for a timeout
                    if (new GregorianCalendar().getTimeInMillis() >= end) {
                        if (passed.size() == 0) {
                            // timed out with no data
                            throw new TimedOutFault();
                        } else {
                            // timed out with data
                            break;
                        }
                    }
                    EnumerationItem ee = iterator.next();
                    if (ctx.isDeleted()) {
                        // Context was deleted while we were waiting
                        throw new InvalidEnumerationContextFault();
                    }
                    if ((ee == null) && (passed.size() == 0)) {
                        // wait for some data to arrive
                        long timeLeft = end
                                - new GregorianCalendar().getTimeInMillis();
                        while ((timeLeft > 0) && (ee == null)) {
                            try {
                                iterator.wait(timeLeft);
                                if (ctx.isDeleted()) {
                                    // Context was deleted while we were waiting
                                    throw new InvalidEnumerationContextFault();
                                }
                                ee = iterator.next();
                                timeLeft = end
                                        - new GregorianCalendar()
                                        .getTimeInMillis();
                            } catch (InterruptedException e) {
                                break;
                            }
                        }
                    }
                    if (ee == null) {
                        if (passed.size() == 0) {
                            throw new TimedOutFault();
                        } else {
                            break;
                        }
                    }
                    
                    // apply filter, if any
                    //
                    // retrieve the document element from the enumeration
                    // element
                    final Object element = ee.getItem();
                    
                    // Check if request matches data provided:
                    // data only, EPR only, or data and EPR
                    if ((includeEPR == true)
                    && (ee.getEndpointReference() == null)) {
                        removeContext(handlerContext, context);
                        throw new UnsupportedFeatureFault(
                                UnsupportedFeatureFault.Detail.INVALID_VALUES);
                    }
                    if ((includeItem == true) && (element == null)) {
                        removeContext(handlerContext, context);
                        throw new UnsupportedFeatureFault(
                                UnsupportedFeatureFault.Detail.INVALID_VALUES);
                    }
                    if (iterator.isFiltered()) {
                        passed.add(ee);
                    } else {
                        if (element != null) {
                            final Element item;
                            
                            if (element instanceof Element) {
                                item = (Element) element;
                            } else if (element instanceof Document) {
                                item = ((Document) element)
                                .getDocumentElement();
                                // append the Element to the owner document
                                // if it has not been done
                                // this is critical for XPath filtering to work
                                final Document owner = item.getOwnerDocument();
                                if (owner.getDocumentElement() == null) {
                                    owner.appendChild(item);
                                }
                            } else {
                                Document doc = Message.newDocument();
                                try {
                                    response.getJAXBContext().createMarshaller().marshal(element,
                                            doc);
                                } catch (Exception e) {
                                    removeContext(handlerContext, context);
                                    final String explanation = "XML Binding marshall failed for object of type: "
                                            + element.getClass().getName();
                                    throw new InternalErrorFault(SOAP
                                            .createFaultDetail(explanation,
                                            null, e, null));
                                }
                                item = doc.getDocumentElement();
                            }
                            final NodeList result;
                            try {
                                result = ctx.evaluate(item);
                            } catch (XPathException xpx) {
                                removeContext(handlerContext, context);
                                throw new CannotProcessFilterFault(
                                        "Error evaluating XPath: "
                                        + xpx.getMessage());
                            } catch (Exception ex) {
                                removeContext(handlerContext, context);
                                throw new CannotProcessFilterFault(
                                        "Error evaluating Filter: "
                                        + ex.getMessage());
                            }
                            if ((result != null) && (result.getLength() > 0)) {
                                // Then add this instance
                                if (fragmentCheck == null) {
                                    // Only check this once
                                    // If 'result' is same as the 'item'
                                    // then this is not a fragment selection
                                    fragmentCheck = new Boolean(result.item(0)
                                    .equals(item));
                                }
                                if (fragmentCheck == true) {
                                    // Whole node was selected
                                    passed.add(ee);
                                } else {
                                    // Fragment(s) selected
                                    JAXBElement<MixedDataType> fragment = createXmlFragment(result);
                                    EnumerationItem fragmentItem = new EnumerationItem(
                                            fragment, ee.getEndpointReference());
                                    passed.add(fragmentItem);
                                }
                                final String nsURI = item.getNamespaceURI();
                                final String nsPrefix = item.getPrefix();
                                if (nsPrefix != null && nsURI != null) {
                                    Map<String, String> ns = new HashMap<String, String>();
                                    ns.put(nsPrefix, nsURI);
                                    response.addNamespaceDeclarations(ns);
                                }
                            }
                        } else {
                            if (EnumerationModeType.ENUMERATE_EPR.equals(mode)) {
                                if (ee.getEndpointReference() != null)
                                    passed.add(ee);
                            }
                        }
                    }
                }
            } finally {
                if (iterator instanceof EnumerationPullIterator) {
                    Management mgt;
                    try {
                        mgt = new Management(request.toSOAPMessage());
                    }catch (Exception ex) {
                        throw new SOAPException(ex.toString());
                    }
                    // XXX REVISIT,
                    // Need to use reflection to remove dependency on EventingExtensions and
                    // Enumeration.
                    Enumeration en = new Enumeration(mgt);
                    ((EnumerationPullIterator) iterator).endPull(en);
                } else {
                    if(iterator instanceof WSEnumerationPullIterator)
                        ((WSEnumerationPullIterator) iterator).endPull(response);
                }
            }
            
            // place an item count estimate if one was requested
            insertTotalItemCountEstimate(request, response, ctx.getIterator());
            
            if (iterator.hasNext() == false) {
                // remove the context -
                // a subsequent release will fault with an invalid context
                removeContext(handlerContext, context);
                return false;
            }
            return iterator.hasNext();
        }
    }
    
    /**
     * {@link org.xmlsoap.schemas.ws._2004._09.enumeration.Release Release} an
     * {@link org.xmlsoap.schemas.ws._2004._09.enumeration.Enumerate Enumeration}
     * in progress.
     *
     * @param request
     *            The incoming SOAP message that contains the
     *            {@link org.xmlsoap.schemas.ws._2004._09.enumeration.Release Release}
     *            request.
     *
     * @param response
     *            The empty SOAP message that will contain the
     *            {@link org.xmlsoap.schemas.ws._2004._09.enumeration.Release Release}
     *            response.
     *
     * @throws InvalidEnumerationContextFault
     *             if the supplied context is missing, is not understood or is
     *             not found because it has expired or the server has been
     *             restarted.
     */
    public static void release(final HandlerContext handlerContext,
            final WSEnumerationRequest request,
            final WSEnumerationResponse response)
            throws SOAPException, JAXBException, FaultException {
        
        final Release release = request.getRelease();
        
        final EnumerationContextType contextType = release
                .getEnumerationContext();
        if (contextType == null) {
            throw new InvalidEnumerationContextFault();
        }
        final UUID context = extractContext(contextType);
        final BaseContext ctx = getContext(context);
        if (ctx == null) {
            throw new InvalidEnumerationContextFault();
        }
        if ((ctx instanceof EnumerationContext) == false) {
            throw new InvalidEnumerationContextFault();
        }
        
        // Set single thread use of this context
        // synchronized (ctx) {
        if (ctx.isDeleted()) {
            throw new InvalidEnumerationContextFault();
        }
        // Make sure this is not an Eventing Pull
        EnumerationIterator iterator = ((EnumerationContext) ctx).getIterator();
        if (iterator instanceof EventingIterator) {
            // Release is not supported for Eventing Pull
            throw new ActionNotSupportedFault();
        }
        final BaseContext rctx = removeContext(handlerContext, context);
        if (rctx == null) {
            throw new InvalidEnumerationContextFault();
        }
        // }
    } 
    
    /**
     * Create a Filter from an Enumeration request
     *
     * @return Returns a Filter object if a filter exists in the request,
     *         otherwise null.
     * @throws CannotProcessFilterFault,
     *             FilteringRequestedUnavailableFault, InternalErrorFault
     */
    public static Filter createFilter(final WSEnumerationRequest request)
    throws CannotProcessFilterFault, FilteringRequestedUnavailableFault {
        try {
            final Enumerate enumerate = request.getEnumerate();
            final org.xmlsoap.schemas.ws._2004._09.enumeration.FilterType enuFilter = request.getEnumerationFilter();
            final DialectableMixedDataType enxFilter = request
                    .getWsmanEnumerationFilter();
            
            if ((enuFilter == null) && (enxFilter == null)) {
                return null;
            }
            if ((enuFilter != null) && (enxFilter != null)) {
                // Both are not allowed. Throw an exception
                throw new CannotProcessFilterFault(
                        SOAP
                        .createFaultDetail(
                        "Both wsen:Filter and wsman:Filter were specified in the request. Only one is allowed.",
                        null, null, null));
            }
            
            // This is the namespaces used in the filter expression itself
            final NamespaceMap nsMap = getNamespaceMap(request);
            
            if (enxFilter != null)
                return createFilter(enxFilter.getDialect(), enxFilter
                        .getContent(), nsMap);
            else
                return createFilter(enuFilter.getDialect(), enuFilter
                        .getContent(), nsMap);
        } catch (SOAPException e) {
            throw new InternalErrorFault(e);
        } catch (JAXBException e) {
            throw new InternalErrorFault(e);
        }
    }
    
    public static NamespaceMap getNamespaceMap(final WSEnumerationRequest request) {
        final NamespaceMap nsMap;
        final SOAPBody body;
        try {
            body = request.toSOAPMessage().getSOAPBody();
        }catch(Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Impossible to convert to SOAPMessage");
        }
        
        NodeList wsmanFilter = body.getElementsByTagNameNS(
                EnumerationExtensions.FILTER.getNamespaceURI(),
                EnumerationExtensions.FILTER.getLocalPart());
        NodeList enumFilter = body.getElementsByTagNameNS(Enumeration.FILTER
                .getNamespaceURI(), Enumeration.FILTER.getLocalPart());
        if ((wsmanFilter != null) && (wsmanFilter.getLength() > 0)) {
            nsMap = new NamespaceMap(wsmanFilter.item(0));
        } else if ((enumFilter != null) && (enumFilter.getLength() > 0)) {
            nsMap = new NamespaceMap(enumFilter.item(0));
        } else {
            NodeList enumElement = body.getElementsByTagNameNS(
                    Enumeration.ENUMERATE.getNamespaceURI(),
                    Enumeration.ENUMERATE.getLocalPart());
            nsMap = new NamespaceMap(enumElement.item(0));
        }
        return nsMap;
    }
    
    private static UUID extractContext(final EnumerationContextType contextType)
    throws FaultException {
        
        if (contextType == null) {
            throw new InvalidEnumerationContextFault();
        }
        
        final String contextString = (String) contextType.getContent().get(0);
        UUID context;
        try {
            context = UUID.fromString(contextString);
        } catch (IllegalArgumentException argex) {
            throw new InvalidEnumerationContextFault();
        }
        
        return context;
    }
    
    private static void insertTotalItemCountEstimate(final WSEnumerationRequest request,
            final WSEnumerationResponse response, final EnumerationIterator iterator)
            throws SOAPException, JAXBException {
        // place an item count estimate if one was requested
        if (request.getRequestTotalItemsCountEstimate() != null) {
            final int estimate = iterator.estimateTotalItems();
            if (estimate < 0) {
                // estimate not available
                response.setTotalItemsCountEstimate(null);
            } else {
                response.setTotalItemsCountEstimate(new BigInteger(Integer
                        .toString(estimate)));
            }
        }
    }
    
    @SuppressWarnings("static-access")
    synchronized static EnumerationIterator newIterator(
            final Object factory,
            final HandlerContext context, final WSEnumerationRequest request,
            final WSEnumerationResponse response) throws SOAPException, JAXBException {
        
        if (factory == null) {
            return null;
        }
        final Boolean includeItem;
        final Boolean includeEPR;
        
        final EnumerationExtensions.Mode mode = request.getMode();
        if (mode == null) {
            includeItem = true;
            includeEPR = false;
        } else {
            if (mode.equals(EnumerationExtensions.Mode.EnumerateEPR)) {
                includeItem = false;
                includeEPR = true;
            } else if (mode.equals(EnumerationExtensions.Mode.EnumerateObjectAndEPR)) {
                includeItem = true;
                includeEPR = true;
            } else {
                throw new UnsupportedFeatureFault(
                        UnsupportedFeatureFault.Detail.ENUMERATION_MODE);
            }
        }
        if(factory instanceof IteratorFactory) {
            IteratorFactory fact = (IteratorFactory) factory;
            Management req;
            try {
                req = new Management(request.toSOAPMessage());
            } catch (Exception ex) {
                throw new SOAPException(ex.toString());
            }
            Enumeration en = new Enumeration(req);
            return fact.newIterator(context, en, Message.getDocumentBuilder(), includeItem,
                    includeEPR);
        } else {
            return ((WSEnumerationIteratorFactory) factory).newIterator(context, request, includeItem,
                    includeEPR);
        }
    }
}