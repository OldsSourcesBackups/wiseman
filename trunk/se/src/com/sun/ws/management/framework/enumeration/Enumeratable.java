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
 * $Id: Enumeratable.java,v 1.4 2006-10-16 14:50:09 obiwan314 Exp $
 *
 */
package com.sun.ws.management.framework.enumeration;

import com.sun.ws.management.enumeration.Enumeration;
import com.sun.ws.management.server.HandlerContext;

/**
 *
 */
public interface Enumeratable {
    void release(HandlerContext context,Enumeration enuRequest, Enumeration enuResponse);

    void pull(HandlerContext context,Enumeration enuRequest, Enumeration enuResponse);

    void enumerate(HandlerContext context,Enumeration enuRequest, Enumeration enuResponse);

    void getStatus(HandlerContext context,Enumeration enuRequest, Enumeration enuResponse);

    void renew(HandlerContext context,Enumeration enuRequest, Enumeration enuResponse);
}
