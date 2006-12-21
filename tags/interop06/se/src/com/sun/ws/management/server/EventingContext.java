/*
 * Copyright 2006 Sun Microsystems, Inc.
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
 * $Id: EventingContext.java,v 1.3 2006-06-28 22:32:46 akhilarora Exp $
 */

package com.sun.ws.management.server;

import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.xpath.XPathExpressionException;
import org.xmlsoap.schemas.ws._2004._08.addressing.EndpointReferenceType;

final class EventingContext extends BaseContext {
    
    private final EndpointReferenceType notifyTo;
    
    EventingContext(final XMLGregorianCalendar expiration, 
            final String filter, 
            final NamespaceMap namespaces, 
            final EndpointReferenceType notifyTo) throws XPathExpressionException {
        super(expiration, filter, namespaces);
        this.notifyTo = notifyTo;
    }
    
    EndpointReferenceType getNotifyTo() {
        return notifyTo;
    }
}