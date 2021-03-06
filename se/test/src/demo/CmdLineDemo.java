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
 ** Copyright (C) 2006, 2007 Hewlett-Packard Development Company, L.P.
 **  
 ** Authors: Simeon Pinder (simeon.pinder@hp.com), Denis Rachal (denis.rachal@hp.com), 
 ** Nancy Beers (nancy.beers@hp.com), William Reichardt
 **
 **$Log: not supported by cvs2svn $
 **
 * $Id: CmdLineDemo.java,v 1.15 2007-05-30 20:30:29 nbeers Exp $
 */

package demo;

import com.sun.ws.management.Management;
import com.sun.ws.management.transport.HttpClient;
import com.sun.ws.management.addressing.Addressing;
import com.sun.ws.management.enumeration.Enumeration;
import com.sun.ws.management.identify.Identify;
import com.sun.ws.management.soap.SOAP;
import com.sun.ws.management.transfer.Transfer;
import com.sun.ws.management.xml.XmlBinding;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.logging.LogManager;
import javax.xml.soap.Detail;
import javax.xml.soap.DetailEntry;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPFault;
import javax.xml.soap.SOAPMessage;
import org.dmtf.schemas.wbem.wsman._1.wsman.SelectorType;
import org.w3c.dom.Element;
import org.xmlsoap.schemas.ws._2004._09.enumeration.EnumerateResponse;
import org.xmlsoap.schemas.ws._2004._09.enumeration.EnumerationContextType;
import org.xmlsoap.schemas.ws._2004._09.enumeration.ItemListType;
import org.xmlsoap.schemas.ws._2004._09.enumeration.PullResponse;
import transport.BasicAuthenticator;

public final class CmdLineDemo {
    
    private static final String IDENTIFY = "identify";
    private static final String GET = "get";
    private static final String ENUMERATE = "enumerate";
    
    private static String dest = null;
    private static String verb = null;
    private static String resource = null;
    private static Set<SelectorType> selectors = new HashSet<SelectorType>();
    
    private static String enumContext = null;
    
    public static void main(java.lang.String[] args) throws Exception {
        if (args.length < 1) {
            System.err.println("USAGE: verb resource selectors");
            System.err.println("  where verb is identify, get or enumerate");
            System.err.println("  and selectors are zero or more key-value pairs (values can be quoted)");
            return;
        }
        verb = args[0];
        if (args.length > 1) {
            // resource not required for identify
            resource = args[1];
        }
        for (int i = 2; i + 1 < args.length; i += 2) {
            final SelectorType selector = new SelectorType();
            selector.setName(args[i]);
            selector.getContent().add(unquote(args[i+1]));
            selectors.add(selector);
        }
        
        dest = System.getProperty("wsman.dest", "http://localhost:8080/wsman/");
        
        // echo cmdline
        System.out.print(dest + " ");
        System.out.print(verb + " ");
        System.out.print(resource + " ");
        System.out.println(selectors);
        System.out.println();
        
        InputStream is = CmdLineDemo.class.getResourceAsStream("/log.properties");
        if (is != null) {
            LogManager.getLogManager().readConfiguration(is);
        }
        
        final String basicAuth = System.getProperty("wsman.basicauthentication");
        if ("true".equalsIgnoreCase(basicAuth)) {
            HttpClient.setAuthenticator(new BasicAuthenticator());
        }
        
        
        if (IDENTIFY.equals(verb)) {
            final Identify identify = new Identify();
            identify.setIdentify();
            System.out.println("\n  ---- request ----  \n");
            identify.prettyPrint(System.out);
            final Addressing response = HttpClient.sendRequest(identify.getMessage(), dest);
            System.out.println("\n  ---- response ----  \n");
            response.prettyPrint(System.out);
        } else {
            sendRequest();
        }
    }
    
    private static void sendRequest() throws Exception {
        String action = Transfer.GET_ACTION_URI;
        if (verb.equals(GET)) {
            action = Transfer.GET_ACTION_URI;
        } else if (verb.equals(ENUMERATE)) {
            if (enumContext == null) {
                action = Enumeration.ENUMERATE_ACTION_URI;
            } else {
                action = Enumeration.PULL_ACTION_URI;
            }
        } else {
            // invoke custom method
            action = verb;
        }
        
        Management mgmt = new Management();
        mgmt.setTo(dest);
        mgmt.setResourceURI(resource);
        mgmt.setAction(action);
        mgmt.setReplyTo(Addressing.ANONYMOUS_ENDPOINT_URI);
        mgmt.setMessageId("uuid:" + UUID.randomUUID().toString());
        if (!selectors.isEmpty()) {
            mgmt.setSelectors(selectors);
        }
        
        if (verb.equals(ENUMERATE)) {
            Enumeration enu = new Enumeration(mgmt);
            if (enumContext == null) {
                enu.setEnumerate(null, null, null);
            } else {
                // pull a few items at a time to make things interesting
                enu.setPull(enumContext, -1, 3, null);
            }
        }
        
        System.out.println("\n  ---- request ----  \n");
        mgmt.prettyPrint(System.out);
        
        Addressing addr = HttpClient.sendRequest(mgmt);
        
        System.out.println("\n  ---- response ----  \n");
        addr.prettyPrint(System.out);
        
        if (addr.getBody().hasFault()) {
            handleFault(addr.getBody().getFault());
            return;
        }
        
        action = addr.getAction();
        if (Transfer.GET_RESPONSE_URI.equals(action)) {
            handleGetResponse(addr);
        } else if (Enumeration.ENUMERATE_RESPONSE_URI.equals(action)) {
            handleEnumerateResponse(addr);
        } else if (Enumeration.PULL_RESPONSE_URI.equals(action)) {
            handlePullResponse(addr);
        }
    }
    
    private static void handleGetResponse(Addressing addr) throws Exception {
    }
    
    private static void handleEnumerateResponse(Addressing addr) throws Exception {
        Enumeration en = new Enumeration(addr);
        EnumerateResponse response = en.getEnumerateResponse();
        EnumerationContextType contextType = response.getEnumerationContext();
        List<Object> contextRoot = contextType.getContent();
        enumContext = contextRoot.get(0).toString();
        sendRequest();
    }
    
    private static void handlePullResponse(Addressing addr) throws Exception {
        Enumeration en = new Enumeration(addr);
        PullResponse response = en.getPullResponse();
        
        // update context
        EnumerationContextType contextType = response.getEnumerationContext();
        if (contextType != null) {
            List<Object> contextRoot = contextType.getContent();
            if (contextRoot != null) {
                String newContext = contextRoot.get(0).toString();
                if (newContext != null) {
                    enumContext = newContext;
                }
            }
        }
        
        ItemListType items = response.getItems();
        List<Object> elements = items.getAny();
        for (Object obj : elements) {
            if (obj instanceof Element) {
                Element el = (Element) obj;
                System.out.println("  " + el.getLocalName());
            }
        }
        
        // continue pulling if there's more
        if (response.getEndOfSequence() == null) {
            sendRequest();
        }
    }
    
    private static void handleFault(SOAPFault fault) throws Exception {
        Detail detail = fault.getDetail();
        if (detail == null) {
            return;
        }
        Iterator di = detail.getDetailEntries();
        while (di.hasNext()) {
            DetailEntry de = null;
            Object obj = di.next();
            if (obj instanceof DetailEntry) {
                de = (DetailEntry) obj;
                System.err.println("fault detail: " + de.getTextContent());
            }
        }
    }
    
    private static String unquote(final String str) {
        if ((str.startsWith("\"") && str.endsWith("\"")) ||
                str.startsWith("'") && str.endsWith("'")) {
            return str.substring(1, str.length() - 1);
        }
        return str;
    }
}
