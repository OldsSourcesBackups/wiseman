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
 * $Id: InvalidOptionsFault.java,v 1.2 2006-05-01 23:32:18 akhilarora Exp $
 */

package com.sun.ws.management;

import com.sun.ws.management.soap.SOAP;
import com.sun.ws.management.soap.SenderFault;
import javax.xml.namespace.QName;
import org.w3c.dom.Node;

public class InvalidOptionsFault extends SenderFault {
    
    public static final QName INVALID_OPTIONS = 
            new QName(Management.NS_URI, "InvalidOptions", Management.NS_PREFIX);
    public static final String INVALID_OPTIONS_REASON =
            "One or more options were not valid.";
    
    public static enum Detail {
        NOT_SUPPORTED("http://schemas.dmtf.org/wbem/wsman/1/wsman/faultDetail/NotSupported"),
        INVALID_NAME("http://schemas.dmtf.org/wbem/wsman/1/wsman/faultDetail/InvalidName"),
        INVALID_VALUE("http://schemas.dmtf.org/wbem/wsman/1/wsman/faultDetail/InvalidValue");

        private final String uri;
        Detail(final String uri) { this.uri = uri; }
        public String toString() { return uri; }
    }
    
    public InvalidOptionsFault(final Detail... detail) {
        this(SOAP.createFaultDetail(null, 
                detail == null ? null : (detail.length == 0 ? null : detail[0].toString()), 
                null, null));
    }
    
    public InvalidOptionsFault(final Node... details) {
        super(Management.FAULT_ACTION_URI, INVALID_OPTIONS, INVALID_OPTIONS_REASON, details);
    }
}
