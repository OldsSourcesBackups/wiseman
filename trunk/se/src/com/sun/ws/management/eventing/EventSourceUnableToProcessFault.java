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
 * $Id: EventSourceUnableToProcessFault.java,v 1.1 2005-06-29 19:18:21 akhilarora Exp $
 */

package com.sun.ws.management.eventing;

import com.sun.ws.management.soap.SOAP;
import com.sun.ws.management.soap.SenderFault;
import org.w3c.dom.Node;

public class EventSourceUnableToProcessFault extends SenderFault {
    
    public EventSourceUnableToProcessFault(final String explanation) {
        this(SOAP.createFaultDetail(explanation, null, null, null));
    }
    
    public EventSourceUnableToProcessFault(final Node... details) {
        super(Eventing.EVENT_SOURCE_UNABLE_TO_PROCESS,
                Eventing.EVENT_SOURCE_UNABLE_TO_PROCESS_REASON, details);
    }
}
