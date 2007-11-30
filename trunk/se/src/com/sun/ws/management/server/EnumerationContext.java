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
 ** Copyright (C) 2006, 2007 Hewlett-Packard Development Company, L.P.
 **
 ** Authors: Simeon Pinder (simeon.pinder@hp.com), Denis Rachal (denis.rachal@hp.com),
 ** Nancy Beers (nancy.beers@hp.com), William Reichardt
 **
 **$Log: not supported by cvs2svn $
 **Revision 1.13  2007/05/30 20:31:04  nbeers
 **Add HP copyright header
 **
 **
 * $Id: EnumerationContext.java,v 1.14 2007-11-30 14:32:38 denis_rachal Exp $
 */

package com.sun.ws.management.server;

import java.util.ArrayList;
import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;

import org.dmtf.schemas.wbem.wsman._1.wsman.EnumerationModeType;

final class EnumerationContext extends BaseContext {


    private final EnumerationIterator iterator;
    private final EnumerationModeType mode;
    private final List<EnumerationItem> passed;

    EnumerationContext(final XMLGregorianCalendar expiration,
            final Filter filter,
            final EnumerationModeType mode,
            final EnumerationIterator iterator,
            final ContextListener listener) {
        super(expiration, filter, listener);
        this.iterator = iterator;
        this.mode = mode;
        this.passed = new ArrayList<EnumerationItem>();
    }

    /**
     * Returns the EnumerationMode
     * @return the EnumerationModeType, null if the mode was not set
     */
    public EnumerationModeType getEnumerationMode() {
        return this.mode;
    }

    /**
     * Returns the iterator associated with this enumeration.
     *
     * @return the iterator associated with this enumeration
     */
    public EnumerationIterator getIterator() {
        return this.iterator;
    }
    
    public List<EnumerationItem> getItems() {
    	return this.passed;
    }

    public void setDeleted() {
    	super.setDeleted();
    }

    protected void finalize () throws Throwable {
        if (this.iterator != null) {
        	this.iterator.release();
        }
    }
}
