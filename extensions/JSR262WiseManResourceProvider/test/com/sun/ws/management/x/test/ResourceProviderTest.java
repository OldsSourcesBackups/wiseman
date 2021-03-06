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
 * $Id: ResourceProviderTest.java,v 1.3 2007-04-06 13:41:55 jfdenise Exp $
 */

package com.sun.ws.management.x.test;

import com.sun.ws.management.Management;
import com.sun.ws.management.addressing.Addressing;
import com.sun.ws.management.identify.Identify;
import com.sun.ws.management.identify.IdentifyUtility;
import com.sun.ws.management.metadata.annotations.AnnotationProcessor;
import com.sun.ws.management.transfer.Transfer;
import com.sun.ws.management.transport.ContentType;
import com.sun.ws.management.transport.HttpClient;
import com.sun.ws.management.x.jmx.WiseManResourceHandler;
import java.lang.management.ManagementFactory;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import javax.management.MBeanServer;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXConnectorServerFactory;
import javax.management.remote.JMXServiceURL;
import javax.management.remote.ws.JMXWSDefinitions;
import javax.management.remote.ws.JMXWSManResourceHandler;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.Duration;
import javax.xml.soap.SOAPElement;
import junit.framework.*;
import org.dmtf.schemas.wbem.wsman._1.wsman.SelectorType;

/**
 *
 * @author jfdenise
 */
public class ResourceProviderTest extends TestCase {
    
    public ResourceProviderTest(String testName) {
        super(testName);
    }
    
    protected void setUp() throws Exception {
    }
    
    protected void tearDown() throws Exception {
    }
    
    public void testPlugProvider() {
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        try {
            //String RESOURCE = "wsman:test/java/system/properties";
            String RESOURCE = "urn:jvm/ThreadInfo";
            Map<String, Object> env = new HashMap<String, Object>();
            Map<String, JMXWSManResourceHandler> providers = new HashMap<String, JMXWSManResourceHandler>();
            providers.put(RESOURCE, new WiseManResourceHandler());
            
            env.put(JMXWSDefinitions.JMX_WS_MAN_RESOURCE_HANDLERS, providers);
            env.put("jmx.remote.x.ws.wsman.identify.qnames", 
                    WiseManResourceHandler.getAdditionalIdentifyElements());
            
            JMXServiceURL url = new JMXServiceURL("service:jmx:ws:" +
                    "//localhost:0/wsman");
            JMXConnectorServer connector =
                    JMXConnectorServerFactory.newJMXConnectorServer(url, env, mbs);
            connector.start();
            
            JMXServiceURL realURL = connector.getAddress();
            String httpurl = "http://"+ realURL.getHost() +":" +
                    realURL.getPort() + realURL.getURLPath();
            System.out.println("Connecting to : " + httpurl);

            // Then the client side...
            
            final Identify identify = new Identify();
            identify.setIdentify();
            
            //Send identify request
            Addressing response = HttpClient.sendRequest(identify.getMessage(), httpurl);
            
            if (response.getBody().hasFault()) {
                fail(response.getBody().getFault().getFaultString());
            }
            
            //Parse the identify response
            final Identify id = new Identify(response);
            final SOAPElement idr = id.getIdentifyResponse();
            assertNotNull(idr);
            SOAPElement el =IdentifyUtility.locateElement(id,
                    AnnotationProcessor.META_DATA_RESOURCE_URI);
            assertNotNull("MetaDatResourceURI is null.",el);
            //retrieve the MetaData ResourceURI
            String resUri=el.getTextContent();
            assertNotNull("Retrieved resourceURI is null.",resUri);
            el =IdentifyUtility.locateElement(id,
                    AnnotationProcessor.META_DATA_TO);
            assertNotNull("MetaDataTo is null",el);
            //retrieve the MetaData To/Destination
            String metTo=el.getTextContent();
            assertNotNull("Retrieved destination is null.",metTo);
            
            
            
            final Transfer xf = new Transfer();
            xf.setAction(Transfer.GET_ACTION_URI);
            xf.setReplyTo(Addressing.ANONYMOUS_ENDPOINT_URI);
            xf.setMessageId("uuid:" + UUID.randomUUID().toString());
            
            final Management mgmt = new Management(xf);
            mgmt.setTo(httpurl);
            mgmt.setResourceURI(RESOURCE);
            final Duration timeout = DatatypeFactory.newInstance().newDuration(60000);
            mgmt.setTimeout(timeout);
            
            final Set<SelectorType> selectorSet = new HashSet<SelectorType>();
            final SelectorType selector = new SelectorType();
            selector.setName("SystemName");
            selector.getContent().add("sun-v20z-1");
            mgmt.setSelectors(selectorSet);
            
            // send this message encoded in UTF-16
            mgmt.setContentType(ContentType.UTF16_CONTENT_TYPE);
            
            response = HttpClient.sendRequest(mgmt);
            if (response.getBody().hasFault()) {
                fail(response.getBody().getFault().getFaultString());
            }
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
}
