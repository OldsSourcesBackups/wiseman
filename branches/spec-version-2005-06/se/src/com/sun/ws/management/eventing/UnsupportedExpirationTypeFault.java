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
 * $Id: UnsupportedExpirationTypeFault.java,v 1.2 2006-03-03 20:51:11 akhilarora Exp $
 */

package com.sun.ws.management.eventing;

import com.sun.ws.management.soap.SenderFault;
import org.w3c.dom.Node;

public class UnsupportedExpirationTypeFault extends SenderFault {
    
    public UnsupportedExpirationTypeFault(final Node... details) {
        super(Eventing.UNSUPPORTED_EXPIRATION_TYPE, 
                Eventing.UNSUPPORTED_EXPIRATION_TYPE_REASON, details);
    }
}
