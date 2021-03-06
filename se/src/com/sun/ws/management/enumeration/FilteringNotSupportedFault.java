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
 * $Id: FilteringNotSupportedFault.java,v 1.2 2006-05-01 23:32:21 akhilarora Exp $
 */

package com.sun.ws.management.enumeration;

import com.sun.ws.management.soap.SenderFault;
import javax.xml.namespace.QName;
import org.w3c.dom.Node;

public class FilteringNotSupportedFault extends SenderFault {
    
    public static final QName FILTERING_NOT_SUPPORTED = 
            new QName(Enumeration.NS_URI, "FilteringNotSupported", Enumeration.NS_PREFIX);
    public static final String FILTERING_NOT_SUPPORTED_REASON = 
        "Filtered enumeration is not supported.";
    
    public FilteringNotSupportedFault(final Node... details) {
        super(Enumeration.FAULT_ACTION_URI, FILTERING_NOT_SUPPORTED,
                FILTERING_NOT_SUPPORTED_REASON, details);
    }
}
