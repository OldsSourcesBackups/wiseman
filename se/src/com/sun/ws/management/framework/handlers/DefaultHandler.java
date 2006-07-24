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
 */
package com.sun.ws.management.framework.handlers;

import javax.servlet.http.HttpServletRequest;

import com.sun.ws.management.Management;
import com.sun.ws.management.addressing.ActionNotSupportedFault;
import com.sun.ws.management.enumeration.Enumeration;
import com.sun.ws.management.server.Handler;
import com.sun.ws.management.server.HandlerContext;
import com.sun.ws.management.transfer.Transfer;
/**
 * This class can be extended to implement as wiseman handler.
 * By Default all actions it handles will return an Action Not Supported Fault.
 * Each of the well known actions are provided with handler methods
 * which can be ovveridden in the extended class to change the behavior
 * from not supported to an implementation relevant to your model.
 * 
 * @author obiwan314
 *
 */
public class DefaultHandler implements Handler {

    public DefaultHandler() {
        super();
    }

    //TODO obiwan314 Pass Servlet Request Down into handler methods
	public void handle(String action, String resourceURI,  HandlerContext context, Management request, Management response) throws Exception {

        if (Transfer.GET_ACTION_URI.equals(action)) {
            response.setAction(Transfer.GET_RESPONSE_URI);
            get(resourceURI, request, response);
            return;
        }

        if (Transfer.PUT_ACTION_URI.equals(action)) {
            response.setAction(Transfer.PUT_RESPONSE_URI);
            put(resourceURI, request, response);
            return;
        }
        if (Transfer.DELETE_ACTION_URI.equals(action)) {
            response.setAction(Transfer.DELETE_RESPONSE_URI);
            delete(resourceURI, request, response);
            return;
        }
        if (Transfer.CREATE_ACTION_URI.equals(action)) {
            response.setAction(Transfer.CREATE_RESPONSE_URI);
            create(resourceURI, request, response);
            return;
        }

        if (Enumeration.ENUMERATE_ACTION_URI.equals(action)) {
            response.setAction(Enumeration.ENUMERATE_RESPONSE_URI);
            Enumeration enuRequest = new Enumeration(request);
            Enumeration enuResponse = new Enumeration(response);
            enumerate(resourceURI, enuRequest, enuResponse);
            return;
        }
        if (Enumeration.PULL_ACTION_URI.equals(action)) {
            response.setAction(Enumeration.PULL_RESPONSE_URI);
            final Enumeration enuRequest = new Enumeration(request);
            final Enumeration enuResponse = new Enumeration(response);
            pull(resourceURI, enuRequest, enuResponse);
            return;
        }
        if (Enumeration.RELEASE_ACTION_URI.equals(action)) {
            response.setAction(Enumeration.RELEASE_RESPONSE_URI);
            final Enumeration enuRequest = new Enumeration(request);
            final Enumeration enuResponse = new Enumeration(response);
            release(resourceURI, enuRequest, enuResponse);
            return;
        }

        if(!customDispatch(action, resourceURI, request, response))
        throw new ActionNotSupportedFault(action);
		
	}

    public void release(String resource, Enumeration enuRequest, Enumeration enuResponse) {
        throw new ActionNotSupportedFault(Enumeration.RELEASE_ACTION_URI);
    }

    public void pull(String resource, Enumeration enuRequest, Enumeration enuResponse) {
        throw new ActionNotSupportedFault(Enumeration.PULL_ACTION_URI);
    }

    public void enumerate(String resource, Enumeration enuRequest, Enumeration enuResponse) {
        throw new ActionNotSupportedFault(Enumeration.ENUMERATE_ACTION_URI);
    }

    public void getStatus(String resource, Enumeration enuRequest, Enumeration enuResponse) {
        throw new ActionNotSupportedFault(Enumeration.GET_STATUS_ACTION_URI);
    }

    public void renew(String resource, Enumeration enuRequest, Enumeration enuResponse) {
        throw new ActionNotSupportedFault(Enumeration.RENEW_ACTION_URI);
    }

    public void get(String resource, Management request, Management response) {
        throw new ActionNotSupportedFault(Transfer.GET_ACTION_URI);
    }

    public void put(String resource, Management request, Management response){
        throw new ActionNotSupportedFault(Transfer.PUT_ACTION_URI);
    }

    public void delete(String resource, Management request, Management response){
        throw new ActionNotSupportedFault(Transfer.DELETE_ACTION_URI);
    }

    public void create(String resource, Management request, Management response){
        throw new ActionNotSupportedFault(Transfer.CREATE_ACTION_URI);
    }

    public boolean customDispatch(String action, String resource, Management request, Management response) throws Exception {
        return false;
    }


}
