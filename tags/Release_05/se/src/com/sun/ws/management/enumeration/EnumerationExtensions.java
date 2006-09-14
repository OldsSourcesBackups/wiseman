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
 * $Id: EnumerationExtensions.java,v 1.5 2006-08-08 15:46:50 pmonday Exp $
 */

package com.sun.ws.management.enumeration;

import com.sun.ws.management.Management;
import com.sun.ws.management.addressing.Addressing;
import com.sun.ws.management.server.EnumerationItem;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPException;
import org.dmtf.schemas.wbem.wsman._1.wsman.AnyListType;
import org.dmtf.schemas.wbem.wsman._1.wsman.AttributableEmpty;
import org.dmtf.schemas.wbem.wsman._1.wsman.AttributableNonNegativeInteger;
import org.dmtf.schemas.wbem.wsman._1.wsman.AttributablePositiveInteger;
import org.dmtf.schemas.wbem.wsman._1.wsman.EnumerationModeType;
import org.w3c.dom.Element;
import org.xmlsoap.schemas.ws._2004._08.addressing.EndpointReferenceType;
import org.xmlsoap.schemas.ws._2004._09.enumeration.EnumerateResponse;
import org.xmlsoap.schemas.ws._2004._09.enumeration.EnumerationContextType;
import org.xmlsoap.schemas.ws._2004._09.enumeration.FilterType;

public class EnumerationExtensions extends Enumeration {
    
    public static final QName REQUEST_TOTAL_ITEMS_COUNT_ESTIMATE =
            new QName(Management.NS_URI, "RequestTotalItemsCountEstimate", Management.NS_PREFIX);
    
    public static final QName TOTAL_ITEMS_COUNT_ESTIMATE =
            new QName(Management.NS_URI, "TotalItemsCountEstimate", Management.NS_PREFIX);
    
    public static final QName OPTIMIZE_ENUMERATION =
            new QName(Management.NS_URI, "OptimizeEnumeration", Management.NS_PREFIX);
    
    public static final QName MAX_ELEMENTS =
            new QName(Management.NS_URI, "MaxElements", Management.NS_PREFIX);
    
    public static final QName ENUMERATION_MODE =
            new QName(Management.NS_URI, "EnumerationMode", Management.NS_PREFIX);
    
    public static final QName ITEMS =
            new QName(Management.NS_URI, "Items", Management.NS_PREFIX);

    public static final QName END_OF_SEQUENCE =
            new QName(Management.NS_URI, "EndOfSequence", Management.NS_PREFIX);
    
    public enum Mode {
        EnumerateEPR("EnumerateEPR"),
        EnumerateObjectAndEPR("EnumerateObjectAndEPR");
        
        public static Mode fromBinding(final JAXBElement<EnumerationModeType> t) {
            return valueOf(t.getValue().value());
        }
        
        private String mode;
        Mode(final String m) { mode = m; }
        public JAXBElement<EnumerationModeType> toBinding() {
            return Management.FACTORY.createEnumerationMode(EnumerationModeType.fromValue(mode));
        }
    }
    
    public EnumerationExtensions() throws SOAPException {
        super();
    }
    
    public EnumerationExtensions(final Addressing addr) throws SOAPException {
        super(addr);
    }
    
    public EnumerationExtensions(final InputStream is) throws SOAPException, IOException {
        super(is);
    }
    
    public void setEnumerate(final EndpointReferenceType endTo,
            final String expires, final FilterType filter, final Mode mode,
            final boolean optimize, final int maxElements)
            throws JAXBException, SOAPException {
        
        final JAXBElement<EnumerationModeType> modeElement =
                mode == null ? null : mode.toBinding();
        if (optimize) {
            final JAXBElement<AttributableEmpty> optimizedElement =
                    Management.FACTORY.createOptimizeEnumeration(new AttributableEmpty());
            JAXBElement<AttributablePositiveInteger> maxElem = null;
            if (maxElements > 0) {
                final AttributablePositiveInteger posInt = new AttributablePositiveInteger();
                posInt.setValue(new BigInteger(Integer.toString(maxElements)));
                maxElem = Management.FACTORY.createMaxElements(posInt);
            }
            super.setEnumerate(endTo, expires, filter, modeElement,
                    optimizedElement, maxElem);
        } else {
            super.setEnumerate(endTo, expires, filter, modeElement);
        }
    }
    
    public void setEnumerateResponse(final Object context, final String expires,
            final List<EnumerationItem> items, final EnumerationModeType mode, final boolean haveMore)
            throws JAXBException, SOAPException {
        
        removeChildren(getBody(), ENUMERATE_RESPONSE);
        final EnumerateResponse response = FACTORY.createEnumerateResponse();
        
        final EnumerationContextType contextType = FACTORY.createEnumerationContextType();
        contextType.getContent().add(context);
        response.setEnumerationContext(contextType);
        
        final AnyListType anyListType = Management.FACTORY.createAnyListType();
        final List<Object> any = anyListType.getAny();
        for (final EnumerationItem ee : items) {
            /*
             * TODO: Add wrapper for <item> if EnumerationMode is ObjectAndEPR
             * per R5.7-2 of the specification.  Waiting on fix to DMTF
             * schema and subsequent JAXB generated source to include the
             * wsman:Item element
             */
            if (mode == null || EnumerationModeType.ENUMERATE_OBJECT_AND_EPR.equals(mode)) {
                any.add(ee.getItem());
            }
            if (mode != null) {
                any.add(Addressing.FACTORY.createEndpointReference(ee.getEndpointReference()));
            }
        }
        response.getAny().add(Management.FACTORY.createItems(anyListType));
        
        if (!haveMore) {
            response.getAny().add(Management.FACTORY.createEndOfSequence(new AttributableEmpty()));
        }
        
        getXmlBinding().marshal(response, getBody());
    }
    
    public static List<EnumerationItem> getItems(final EnumerateResponse response)
    throws JAXBException, SOAPException {
        final Object obj = extract(response.getAny(), AnyListType.class, ITEMS);
        if (obj instanceof AnyListType) {
            final AnyListType anyListType = (AnyListType) obj;
            return unbindItems(anyListType.getAny());
        }
        return null;
    }
    
    public static boolean isEndOfSequence(final EnumerateResponse response)
    throws JAXBException, SOAPException {
        return null != extract(response.getAny(), AttributableEmpty.class, END_OF_SEQUENCE);
    }
    
    public static List<EnumerationItem> unbindItems(final List<Object> items) {
        final int size = items.size();
        final List<EnumerationItem> itemList = new ArrayList<EnumerationItem>();
        // the three possibilities are: EPR only, Item and EPR or Item only
        for (int i = 0; i < size; i++) {
            final Object obj = items.get(i);
            if (obj instanceof JAXBElement) {
                // EPR only
                final JAXBElement elt = (JAXBElement) obj;
                if (EndpointReferenceType.class.equals(elt.getDeclaredType())) {
                    itemList.add(new EnumerationItem(null,
                            ((JAXBElement<EndpointReferenceType>) obj).getValue()));
                }
            } else if (obj instanceof Element) {
                // could be item only or item + EPR
                if ((i+1 < size) && (items.get(i+1) instanceof JAXBElement)) {
                    // item + EPR
                    final Object nextObj = items.get(i+1);
                    final JAXBElement elt = (JAXBElement) nextObj;
                    if (EndpointReferenceType.class.equals(elt.getDeclaredType())) {
                        itemList.add(new EnumerationItem((Element) obj,
                                ((JAXBElement<EndpointReferenceType>) nextObj).getValue()));
                        // advance past the next object since we've already processed it
                        i++;
                    }
                } else {
                    // item only
                    itemList.add(new EnumerationItem((Element) obj, null));
                }
            }
        }
        return itemList;
    }
    
    public void setRequestTotalItemsCountEstimate() throws JAXBException {
        final AttributableEmpty empty = new AttributableEmpty();
        final JAXBElement<AttributableEmpty> emptyElement =
                Management.FACTORY.createRequestTotalItemsCountEstimate(empty);
        getXmlBinding().marshal(emptyElement, getHeader());
    }
    
    public AttributableEmpty getRequestTotalItemsCountEstimate() throws JAXBException, SOAPException {
        final Object value = unbind(getHeader(), REQUEST_TOTAL_ITEMS_COUNT_ESTIMATE);
        return value == null ? null : ((JAXBElement<AttributableEmpty>) value).getValue();
    }
    
    public void setTotalItemsCountEstimate(final BigInteger itemCount) throws JAXBException {
        final AttributableNonNegativeInteger count = new AttributableNonNegativeInteger();
        final JAXBElement<AttributableNonNegativeInteger> countElement =
                Management.FACTORY.createTotalItemsCountEstimate(count);
        if (itemCount == null) {
            /*
             * TODO: does not work yet - bug in JAXB 2.0 FCS, see Issue 217 in JAXB
             * https://jaxb.dev.java.net/issues/show_bug.cgi?id=217
             */
            countElement.setNil(true);
        } else {
            count.setValue(itemCount);
        }
        getXmlBinding().marshal(countElement, getHeader());
    }
    
    public AttributableNonNegativeInteger getTotalItemsCountEstimate() throws JAXBException, SOAPException {
        final Object value = unbind(getHeader(), TOTAL_ITEMS_COUNT_ESTIMATE);
        return value == null ? null : ((JAXBElement<AttributableNonNegativeInteger>) value).getValue();
    }
    
    private static Object extract(final List<Object> anyList, final Class classType, final QName eltName) {
        for (final Object any : anyList) {
            if (any instanceof JAXBElement) {
                final JAXBElement elt = (JAXBElement) any;
                if ((classType != null && classType.equals(elt.getDeclaredType())) &&
                        (eltName != null && eltName.equals(elt.getName()))) {
                    return elt.getValue();
                }
            }
        }
        return null;
    }
}
