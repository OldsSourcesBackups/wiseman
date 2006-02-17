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
 * $Id: pullSource_Handler.java,v 1.2 2006-02-16 20:12:43 akhilarora Exp $
 */

package com.sun.ws.management.server.handler.wsman.test;

import com.sun.ws.management.eventing.DeliveryModeRequestedUnavailableFault;
import com.sun.ws.management.eventing.EventingExtensions;
import com.sun.ws.management.server.Handler;
import com.sun.ws.management.Management;
import com.sun.ws.management.addressing.ActionNotSupportedFault;
import com.sun.ws.management.enumeration.Enumeration;
import com.sun.ws.management.eventing.Eventing;
import com.sun.ws.management.server.EnumerationIterator;
import com.sun.ws.management.server.EnumerationSupport;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xmlsoap.schemas.ws._2004._08.eventing.DeliveryType;
import org.xmlsoap.schemas.ws._2004._08.eventing.Subscribe;

public class pullSource_Handler implements Handler, EnumerationIterator {
    
    private static final String NS_URI = "https://wiseman.dev.java.net/test/events/pull";
    private static final String NS_PREFIX = "log";
    
    // TODO: replace with real events - perhaps from the JVM?
    private static final String[][] EVENT_LOG = {
        { "event1", "critical" }, 
        { "event2", "warning" },
        { "event3", "info" },
        { "event4", "debug" }
    };

    private boolean cancelled;
    
    public void handle(final String action, final String resource,
            final Management request, final Management response) throws Exception {
        
        final Enumeration enuRequest = new Enumeration(request);
        final Enumeration enuResponse = new Enumeration(response);
        
        if (Eventing.SUBSCRIBE_ACTION_URI.equals(action)) {
            final EventingExtensions evtx = new EventingExtensions(request);
            final Subscribe subscribe = evtx.getSubscribe();
            final DeliveryType deliveryType = subscribe.getDelivery();
            if (EventingExtensions.PULL_DELIVERY_MODE.equals(deliveryType.getMode())) {
                enuResponse.setAction(Eventing.SUBSCRIBE_RESPONSE_URI);
                final Map<String, String> namespaces = new HashMap<String, String>();
                namespaces.put(NS_PREFIX, NS_URI);
                EnumerationSupport.enumerate(enuRequest, enuResponse, this, EVENT_LOG, namespaces);
            } else {
                throw new DeliveryModeRequestedUnavailableFault();
            }
        } else if (Enumeration.PULL_ACTION_URI.equals(action)) {
            enuResponse.setAction(Enumeration.PULL_RESPONSE_URI);
            EnumerationSupport.pull(enuRequest, enuResponse);
        } else if (Enumeration.RELEASE_ACTION_URI.equals(action)) {
            enuResponse.setAction(Enumeration.RELEASE_RESPONSE_URI);
            EnumerationSupport.release(enuRequest, enuResponse);
        } else {
            throw new ActionNotSupportedFault(action);
        }
    }
    
    public List<Element> next(final Document doc, final Object context,
            final int start, final int count) {        
        cancelled = false;
        final String[][] events = (String[][]) context;
        final int returnCount = Math.min(count, events.length - start);
        final List<Element> items = new ArrayList(returnCount);
        for (int i = 0; i < returnCount && !cancelled; i++) {
            final String key = events[start + i][0];
            final String value = events[start + i][1];
            final Element item = doc.createElementNS(NS_URI, NS_PREFIX + ":" + key);
            item.setTextContent(value);
            items.add(item);
        }
        return items;
    }
    
    public boolean hasNext(final Object context, final int start) {
        final String[][] events = (String[][]) context;
        return start < events.length;
    }

    public void cancel(final Object context) {
        cancelled = true;
    }
}