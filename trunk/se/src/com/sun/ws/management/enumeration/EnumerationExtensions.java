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
 * $Id: EnumerationExtensions.java,v 1.3 2006-06-07 17:08:18 akhilarora Exp $
 */

package com.sun.ws.management.enumeration;

import com.sun.ws.management.Management;
import com.sun.ws.management.addressing.Addressing;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPException;
import org.dmtf.schemas.wbem.wsman._1.wsman.AttributableEmpty;
import org.dmtf.schemas.wbem.wsman._1.wsman.AttributableNonNegativeInteger;
import org.dmtf.schemas.wbem.wsman._1.wsman.EnumerationModeType;
import org.xmlsoap.schemas.ws._2004._08.addressing.EndpointReferenceType;
import org.xmlsoap.schemas.ws._2004._09.enumeration.FilterType;

public class EnumerationExtensions extends Enumeration {
    
    public static final QName REQUEST_TOTAL_ITEMS_COUNT_ESTIMATE =
            new QName(Management.NS_URI, "RequestTotalItemsCountEstimate", Management.NS_PREFIX);

    public static final QName TOTAL_ITEMS_COUNT_ESTIMATE =
            new QName(Management.NS_URI, "TotalItemsCountEstimate", Management.NS_PREFIX);

    public static final QName ENUMERATION_MODE = new QName(NS_URI, "EnumerationMode", NS_PREFIX);
    
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
            final String expires, final FilterType filter, final Mode mode) 
            throws JAXBException, SOAPException {
        super.setEnumerate(endTo, expires, filter, mode.toBinding());
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
            // TODO: does not work yet
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
}
