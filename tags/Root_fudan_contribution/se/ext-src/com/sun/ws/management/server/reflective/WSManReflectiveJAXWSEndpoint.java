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
 * $Id: WSManReflectiveJAXWSEndpoint.java,v 1.1 2007-04-06 10:03:11 jfdenise Exp $
 */

package com.sun.ws.management.server.reflective;

import com.sun.ws.management.InternalErrorFault;
import com.sun.ws.management.Management;
import com.sun.ws.management.Message;
import com.sun.ws.management.server.WSManAgent;
import com.sun.ws.management.server.jaxws.WSManJAXWSEndpoint;
import com.sun.ws.management.transport.ContentType;
import com.sun.ws.management.xml.XmlBinding;
import java.net.URL;
import java.security.Principal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.security.auth.Subject;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.transform.Source;
import javax.xml.validation.Schema;
import javax.xml.ws.WebServiceContext;
import java.lang.management.ManagementFactory;
import javax.annotation.Resource;
import javax.management.MBeanServer;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.Provider;
import javax.xml.ws.Service;
import javax.xml.ws.ServiceMode;
import javax.xml.ws.WebServiceProvider;
import javax.xml.ws.handler.MessageContext;
import org.xml.sax.SAXException;

/**
 * JAX-WS Compliant endpoint.
 */
@WebServiceProvider()
@ServiceMode(value=Service.Mode.MESSAGE)
public class WSManReflectiveJAXWSEndpoint extends WSManJAXWSEndpoint {
    
    private static final Logger LOG = Logger.getLogger(WSManReflectiveJAXWSEndpoint.class.getName());
    
    // Agent constuction parameters
    private Map<String,String> wisemanConf;
    private Source[] customSchemas;
    private Map<String,String> bindingConf;
    
    // JAXWS Context injection
    @Resource
    private WebServiceContext context;
    public WSManReflectiveJAXWSEndpoint() {
        this(null, null, null);
    }
    /**
     * JAX-WS Endpoint constructor
    */
    public WSManReflectiveJAXWSEndpoint(Map<String,String> wisemanConf, Source[] customSchemas, 
            Map<String,String> bindingConf) {
       this.wisemanConf = wisemanConf;
       this.customSchemas = customSchemas;
       this.bindingConf = bindingConf;
    }
    
    /*
     * In case this class is extended, Annotations @WebServiceProvider()
     * @ServiceMode(value=Service.Mode.MESSAGE) @Resource must be set on the extended class
     * or JAX-WS will not recognize the endpoint as being a valid JAX-WS endpoint.
     * This method is used to retrieve the injected resource of the extended class.
     */
    protected WebServiceContext getWebServiceContext() {
        return context;
    }
    
    protected WSManAgent createWSManAgent() throws SAXException {
        // It is an extension of WSManAgent to handle Reflective Dispatcher
        return new WSManReflectiveAgent(wisemanConf, customSchemas, 
                bindingConf);
    }
}
