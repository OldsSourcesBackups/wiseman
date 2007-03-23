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
 * $Id: EnumerationContext.java,v 1.8 2007-01-14 17:52:34 denis_rachal Exp $
 */

package com.sun.ws.management.server;

import javax.xml.datatype.XMLGregorianCalendar;
import org.dmtf.schemas.wbem.wsman._1.wsman.EnumerationModeType;

final class EnumerationContext extends BaseContext {
    
    
    private final EnumerationIterator iterator;
    private final EnumerationModeType mode;
    
    EnumerationContext(final XMLGregorianCalendar expiration,
            final Filter filter,
            final EnumerationModeType mode,
            final EnumerationIterator iterator) {
        super(expiration, filter);
        this.iterator = iterator;
        this.mode = mode;
    }
    
    /**
     * Returns the EnumerationMode
     * @return the EnumerationModeType, null if the mode was not set
     */
    public EnumerationModeType getEnumerationMode() {
        return mode;
    }

    EnumerationIterator getIterator() {
        return iterator;
    }
}