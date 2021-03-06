/*
 * Copyright (C) 2006, 2007 Hewlett-Packard Development Company, L.P.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *
 ** Copyright (C) 2006, 2007 Hewlett-Packard Development Company, L.P.
 **  
 ** Authors: Simeon Pinder (simeon.pinder@hp.com), Denis Rachal (denis.rachal@hp.com), 
 ** Nancy Beers (nancy.beers@hp.com), William Reichardt 
 **
 **$Log: not supported by cvs2svn $
 **Revision 1.15  2007/11/30 14:32:36  denis_rachal
 **Issue number:  140
 **Obtained from:
 **Submitted by:  jfdenise
 **Reviewed by:
 **
 **WSManAgentSupport and WSEnumerationSupport changed to coordinate their separate threads when handling wsman:OperationTimeout and wsen:MaxTime timeouts. If a timeout now occurs during an enumeration operation the WSEnumerationSupport is notified by the WSManAgentSupport thread. WSEnumerationSupport saves any items collected from the EnumerationIterator in the context so they may be fetched by the client on the next pull. Items are no longer lost on timeouts.
 **
 **Tests were added to correctly test this functionality and older tests were updated to properly test timeout functionality.
 **
 **Additionally some tests were updated to make better use of the XmlBinding object and improve performance on testing.
 **
 **Revision 1.14  2007/05/30 20:30:23  nbeers
 **Add HP copyright header
 **
 ** 
 *
 * $Id: TransferExtensionsTest.java,v 1.16 2007-12-03 09:15:09 denis_rachal Exp $
 */


package management;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.xml.bind.JAXBElement;
import javax.xml.soap.SOAPHeaderElement;

import org.dmtf.schemas.wbem.wsman._1.wsman.MixedDataType;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xmlsoap.schemas.ws._2004._08.addressing.EndpointReferenceType;

import util.TestBase;

import com.sun.ws.management.Management;
import com.sun.ws.management.addressing.Addressing;
import com.sun.ws.management.transfer.Transfer;
import com.sun.ws.management.transfer.TransferExtensions;
import com.sun.ws.management.transfer.TransferMessageValues;
import com.sun.ws.management.transfer.TransferUtility;
import com.sun.ws.management.transport.HttpClient;
import com.sun.ws.management.xml.XPath;
import com.sun.ws.management.xml.XmlBinding;

import foo.test.Foo;

public class TransferExtensionsTest extends TestBase {
    
    private static final String CUSTOM_JAXB_PREFIX = "jb";
    private static final String CUSTOM_JAXB_NS = "http://test.foo";
    private static final Map<String, String> NAMESPACES = new HashMap<String, String>();

    static {
        NAMESPACES.put(CUSTOM_JAXB_PREFIX, CUSTOM_JAXB_NS);
    }

    public TransferExtensionsTest(String testName) {
        super(testName);
    }
    
    public static junit.framework.Test suite() {
        final junit.framework.TestSuite suite = new junit.framework.TestSuite(TransferExtensionsTest.class);
        return suite;
    }
    
    /**
     * Visual test of request structure..
     * <p/>
     * Note: Visual tests invoke server-side operations directly and are not
     * meant to be viewed as proper client invocations.
     *
     * @throws Exception
     */
    public void testFragmentGetVisual() throws Exception {
        //setup Transfer object for request
        final TransferExtensions transfer = new TransferExtensions();
        transfer.setXmlBinding(binding);
        transfer.addNamespaceDeclarations(NAMESPACES);
        transfer.setAction(Transfer.GET_ACTION_URI);
        
        //xpath expression
        final String expression = "//foo/bar";
        //this call ensures the fragment header is initialized
        transfer.setFragmentHeader(expression, XPath.NS_URI);
        
        //print contents of Transfer
        transfer.prettyPrint(logfile);
        
        final ByteArrayOutputStream bos = new ByteArrayOutputStream();
        transfer.writeTo(bos);
        final TransferExtensions trans = new TransferExtensions(new ByteArrayInputStream(bos.toByteArray()));
        
        //try to get the fragmenttransfer header
        final SOAPHeaderElement fragmentTransferHeader = trans.getFragmentHeader();
        assertNotNull(fragmentTransferHeader);
        assertEquals(expression, fragmentTransferHeader.getTextContent());
        assertEquals(XPath.NS_URI, fragmentTransferHeader.getAttributeValue(TransferExtensions.DIALECT));
    }
    
    /**
     * Visual test of response structure..
     * Note: Visual tests invoke server-side operations directly and are not
     * meant to be viewed as proper client invocations.
     *
     * @throws Exception
     */
    public void testFragmentGetResponseVisual() throws Exception {
        //setup Transfer object for request
        final TransferExtensions transfer = new TransferExtensions();
        transfer.setXmlBinding(binding);
        transfer.addNamespaceDeclarations(NAMESPACES);
        transfer.setAction(Transfer.GET_RESPONSE_URI);
        
        //xpath expression
        final String expression = "//foo/bar";
        //this call ensures the fragment header is initialized
        transfer.setFragmentHeader(expression, null);
        final SOAPHeaderElement fragmentTransferHeader = transfer.getFragmentHeader();
        
        //simulate the server-side DOM model to be used in request
        final List<Object> content = new ArrayList<Object>();
        final Document doc = transfer.newDocument();
        final Element fooElement = doc.createElement("foo");
        final Element barElement = doc.createElement("bar");
        barElement.setTextContent("this is a test of fragments");
        fooElement.appendChild(barElement);
        content.add(fooElement);
        
        //simulate server side request to get the response
        transfer.setFragmentGetResponse(fragmentTransferHeader, content);
        
        //print contents of Transfer
        transfer.prettyPrint(logfile);
        
        final ByteArrayOutputStream bos = new ByteArrayOutputStream();
        transfer.writeTo(bos);
        final TransferExtensions trans = new TransferExtensions(new ByteArrayInputStream(bos.toByteArray()));
        trans.setXmlBinding(binding);
        
        //try to get the fragmenttransfer header
        final SOAPHeaderElement fragmentTransferHeader2 = trans.getFragmentHeader();
        assertNotNull(fragmentTransferHeader2);
        assertEquals(expression, fragmentTransferHeader2.getTextContent());
        assertNull(fragmentTransferHeader.getAttributeValue(TransferExtensions.DIALECT));
    }
    
    /**
     * a fragment transfer becomes a regular transfer if the fragment header is omitted
     */
    public void testNonFragmentGet() throws Exception {
        //setup Transfer object for request
        final TransferExtensions transfer = new TransferExtensions();
        transfer.setXmlBinding(binding);
        transfer.addNamespaceDeclarations(NAMESPACES);
        transfer.setAction(Transfer.GET_ACTION_URI);
        transfer.setReplyTo(Addressing.ANONYMOUS_ENDPOINT_URI);
        transfer.setMessageId(UUID_SCHEME + UUID.randomUUID().toString());
        
        //send request to server
        final Management mgmt = new Management(transfer);
        mgmt.setTo(DESTINATION);
        mgmt.setResourceURI("wsman:test/fragment");
        
        //print contents of Transfer
        mgmt.prettyPrint(logfile);
        
        final Addressing response = HttpClient.sendRequest(mgmt);
        response.prettyPrint(logfile);
        if (response.getBody().hasFault()) {
            fail(response.getBody().getFault().getFaultString());
        }
        final TransferExtensions trans = new TransferExtensions(response);
        
        //try to get the fragmenttransfer header
        final SOAPHeaderElement fragmentTransferHeader = trans.getFragmentHeader();
        assertNull(fragmentTransferHeader);
        
        // this should be the entire response document
        assertNotNull(response.getBody().getFirstChild());
    }
    
    /**
     * Actual Test of the Fragment Get operation
     *
     * @throws Exception
     */
    public void testFragmentGet() throws Exception {
        //setup Transfer object for request
    	
    	TransferMessageValues settings = new TransferMessageValues();
    	settings.setNamespaceMap(NAMESPACES);
    	settings.setFragment("//jb:foo/jb:bar");
    	settings.setFragmentDialect(XPath.NS_URI);
    	settings.setTo(DESTINATION);
    	settings.setResourceUri("wsman:test/fragment");
        settings.setXmlBinding(binding);
    	
    	Transfer xf = TransferUtility.buildMessage(null, settings);

        //print contents of Transfer
        xf.prettyPrint(logfile);
        final Addressing response = HttpClient.sendRequest(xf);
        response.prettyPrint(logfile);
        if (response.getBody().hasFault()) {
            fail(response.getBody().getFault().getFaultString());
        }
        final TransferExtensions trans = new TransferExtensions(response);
        
        //try to get the fragmenttransfer header
        final SOAPHeaderElement fragmentTransferHeader = trans.getFragmentHeader();
        assertNotNull("The fragment header was not returned.",fragmentTransferHeader);
        assertEquals("Values not equal.",settings.getFragment(), fragmentTransferHeader.getTextContent());
        	Attr dialect = fragmentTransferHeader.getAttributeNode(TransferExtensions.DIALECT.getLocalPart());
        assertNotNull("Unable to locate attribute.",dialect);
        assertEquals("Dialect value was not correct.",XPath.NS_URI, dialect.getValue());
    }
    
    /**
     * Visual test of response structure..
     * <p/>
     * Note: Visual tests invoke server-side operations directly and are not
     * meant to be viewed as proper client invocations.
     *
     * @throws Exception
     */
    public void testFragmentDeleteVisual() throws Exception {
        //setup Transfer object for request
        final TransferExtensions transfer = new TransferExtensions();
        transfer.setXmlBinding(binding);
        transfer.addNamespaceDeclarations(NAMESPACES);
        transfer.setAction(Transfer.DELETE_RESPONSE_URI);
        
        //xpath expression
        final String expression = "//foo/bar";
        //this call ensures the fragment header is initialized
        transfer.setFragmentHeader(expression, XPath.NS_URI);
        final SOAPHeaderElement fragmentHeader = transfer.getFragmentHeader();
        
        //simulated Dom model to be modified on server
        final List<Node> content = new ArrayList<Node>();
        final Document doc = transfer.newDocument();
        final Element isElement = doc.createElement("is");
        final Element fooElement = doc.createElement("foo");
        final Element barElement = doc.createElement("bar");
        barElement.setTextContent("this is a test of fragments");
        fooElement.appendChild(barElement);
        isElement.appendChild(fooElement);
        content.add(isElement);
        
        //simulate a server-side request to build the response
        transfer.setFragmentDeleteResponse(fragmentHeader);
        
        //print contents of Transfer
        transfer.prettyPrint(logfile);
        
        final ByteArrayOutputStream bos = new ByteArrayOutputStream();
        transfer.writeTo(bos);
        final TransferExtensions trans = new TransferExtensions(new ByteArrayInputStream(bos.toByteArray()));
        trans.setXmlBinding(binding);
        
        //try to get the fragmenttransfer header
        final SOAPHeaderElement fragmentTransferHeader2 = trans.getFragmentHeader();
        assertNotNull(fragmentTransferHeader2);
        assertEquals(expression, fragmentTransferHeader2.getTextContent());
        assertNotNull(fragmentTransferHeader2.getAttributeValue(TransferExtensions.DIALECT));
    }
    
    /**
     * Actual test of Fragment Delete Operation
     *
     * @throws Exception
     */
    public void testFragmentDelete() throws Exception {
        
        //setup Transfer object for request
        final TransferExtensions transfer = new TransferExtensions();
        transfer.setXmlBinding(binding);
        if (!checkIfBindingIsAvailable(transfer.getXmlBinding())) {
            // skip this test if JAXB is not initialized with the foo.test package
            return;
        }
        transfer.addNamespaceDeclarations(NAMESPACES);
        transfer.setAction(Transfer.DELETE_ACTION_URI);
        transfer.setReplyTo(Addressing.ANONYMOUS_ENDPOINT_URI);
        transfer.setMessageId(UUID_SCHEME + UUID.randomUUID().toString());
        
        //xpath expression
        final String expression = "//jb:foo/jb:bar";
        //this call ensures the fragment header is initialized
        transfer.setFragmentHeader(expression, XPath.NS_URI);
        
        //build XmlFragment for request
        final MixedDataType mixedDataType = Management.FACTORY.createMixedDataType();
        final JAXBElement<MixedDataType> xmlFragment = Management.FACTORY.createXmlFragment(mixedDataType);
        
        //create request body content
        final foo.test.ObjectFactory ob = new foo.test.ObjectFactory();
        final JAXBElement<String> bar = ob.createBar("PUT request value");
        mixedDataType.getContent().add(bar);
        
        //marshall XmlFragment into request body
        binding.marshal(xmlFragment, transfer.getBody());
        
        //send request to server
        final Management mgmt = new Management(transfer);
        mgmt.setTo(DESTINATION);
        mgmt.setResourceURI("wsman:test/fragment");
        //print contents of Transfer
        mgmt.prettyPrint(logfile);
        
        final Addressing response = HttpClient.sendRequest(mgmt);
        response.prettyPrint(logfile);
        if (response.getBody().hasFault()) {
            fail(response.getBody().getFault().getFaultString());
        }
        final TransferExtensions trans = new TransferExtensions(response);
        final SOAPHeaderElement fragmentTransferHeader = trans.getFragmentHeader();
        assertNotNull(fragmentTransferHeader);
        assertEquals(expression, fragmentTransferHeader.getTextContent());
        assertNotNull(fragmentTransferHeader.getAttributeValue(TransferExtensions.DIALECT));
      
    }
    
    /**
     * A test of an expected failed delete request.
     *
     * @throws Exception
     */
    public void testFragmentDeleteFail() throws Exception {
        //setup Transfer object for request
    	TransferMessageValues settings = new TransferMessageValues();
    	settings.setNamespaceMap(NAMESPACES);
    	settings.setFragment("//jb:foo");
    	settings.setFragmentDialect(XPath.NS_URI);
    	settings.setTo(DESTINATION);
    	settings.setResourceUri("wsman:test/fragment");
    	settings.setTransferMessageActionType(Transfer.DELETE_ACTION_URI);
    	settings.setXmlBinding(binding);
    	
    	Transfer xf = TransferUtility.buildMessage(null, settings);
    	
        //print contents of Transfer
        xf.prettyPrint(logfile);
        
        final Addressing response = HttpClient.sendRequest(xf);
        response.prettyPrint(logfile);
        if (response.getBody().hasFault()) {
            assertNotNull(response.getBody().getFault().getFaultString());
        } else {
            fail("A Fault should have been thrown and was not");
        }
    }
    
    /**
     * Visual test of response structure..
     * <p/>
     * Note: Visual tests invoke server-side operations directly and are not
     * meant to be viewed as proper client invocations.
     *
     * @throws Exception
     */
    public void testFragmentPutResponseVisual() throws Exception {
        //setup Transfer object for request
        final TransferExtensions transfer = new TransferExtensions();
        transfer.setXmlBinding(binding);
        transfer.addNamespaceDeclarations(NAMESPACES);
        transfer.setAction(Transfer.PUT_RESPONSE_URI);
        
        //xpath expression
        final String expression = "//foo/bar";
        
        //this call ensures the fragment header is initialized
        transfer.setFragmentHeader(expression, XPath.NS_URI);
        final SOAPHeaderElement fragmentHeader = transfer.getFragmentHeader();
        
        //build simulated Dom model to be modified
        final List<Node> content = new ArrayList<Node>();
        final Document doc = transfer.newDocument();
        final Element isElement = doc.createElement("a");
        final Element fooElement = doc.createElement("foo");
        final Element barElement = doc.createElement("bar");
        barElement.setTextContent("this is a test of fragments");
        fooElement.appendChild(barElement);
        isElement.appendChild(fooElement);
        content.add(isElement);
        
        //build simulated request content
        final List<Object> requestContent = new ArrayList<Object>();
        final Element requestBarElement = doc.createElement("bar");
        requestBarElement.setTextContent("PUT request value");
        requestContent.add(requestBarElement);
        
        //simulate server-side request to get the response
        transfer.setFragmentPutResponse(fragmentHeader, requestContent);
        
        //print contents of Transfer
        transfer.prettyPrint(logfile);
        
        final ByteArrayOutputStream bos = new ByteArrayOutputStream();
        transfer.writeTo(bos);
        final TransferExtensions trans = new TransferExtensions(new ByteArrayInputStream(bos.toByteArray()));
        
        //try to get the fragmenttransfer header
        final SOAPHeaderElement fragmentTransferHeader2 = trans.getFragmentHeader();
        assertNotNull(fragmentTransferHeader2);
        assertEquals(expression, fragmentTransferHeader2.getTextContent());
        assertNotNull(fragmentTransferHeader2.getAttributeValue(TransferExtensions.DIALECT));
    }
    
    /**
     * Actual test of the Fragment Put operation
     *
     * @throws Exception
     */
    public void testFragmentPut() throws Exception {
        //setup Transfer object for request
        final TransferExtensions transfer = new TransferExtensions();
        transfer.setXmlBinding(binding);
        if (!checkIfBindingIsAvailable(transfer.getXmlBinding())) {
            // skip this test if JAXB is not initialized with the foo.test package
            return;
        }
        transfer.addNamespaceDeclarations(NAMESPACES);
        transfer.setAction(Transfer.PUT_ACTION_URI);
        transfer.setReplyTo(Addressing.ANONYMOUS_ENDPOINT_URI);
        transfer.setMessageId(UUID_SCHEME + UUID.randomUUID().toString());
        
        //xpath expression
        final String expression = "//jb:foo/jb:bar";
        //this call ensures the fragment header is initialized
        transfer.setFragmentHeader(expression, XPath.NS_URI);
        
        //build XmlFragment for request
        final MixedDataType mixedDataType = Management.FACTORY.createMixedDataType();
        final JAXBElement<MixedDataType> xmlFragment = Management.FACTORY.createXmlFragment(mixedDataType);
        
        //create request body content
        final foo.test.ObjectFactory ob = new foo.test.ObjectFactory();
        final JAXBElement<String> bar = ob.createBar("PUT request value");
        mixedDataType.getContent().add(bar);
        
        //marshall XmlFragment into request body
        binding.marshal(xmlFragment, transfer.getBody());
        
        //send request to server
        final Management mgmt = new Management(transfer);
        mgmt.setTo(DESTINATION);
        mgmt.setResourceURI("wsman:test/fragment");
        //print contents of Transfer
        mgmt.prettyPrint(logfile);
        
        final Addressing response = HttpClient.sendRequest(mgmt);
        response.prettyPrint(logfile);
        if (response.getBody().hasFault()) {
            fail(response.getBody().getFault().getFaultString());
        }
        final TransferExtensions trans = new TransferExtensions(response);
        final SOAPHeaderElement fragmentTransferHeader = trans.getFragmentHeader();
        assertNotNull(fragmentTransferHeader);
        assertEquals(expression, fragmentTransferHeader.getTextContent());
        assertNotNull(fragmentTransferHeader.getAttributeValue(TransferExtensions.DIALECT));
        
    }
    
    /**
     * Test of an expected failed Fragment Put operation
     *
     * @throws Exception
     */
    public void testFragmentPutFail() throws Exception {
    	//send a request without the fragment in the body
    	TransferMessageValues settings = new TransferMessageValues();
    	settings.setNamespaceMap(NAMESPACES);
    	settings.setFragment("//jb:foo");
    	settings.setFragmentDialect(XPath.NS_URI);
    	settings.setTo(DESTINATION);
    	settings.setResourceUri("wsman:test/fragment");
    	settings.setTransferMessageActionType(Transfer.PUT_ACTION_URI);
        settings.setXmlBinding(binding);
    	
    	Transfer xf = TransferUtility.buildMessage(null, settings);
    	
        //print contents of Transfer
        xf.prettyPrint(logfile);
        
        final Addressing response = HttpClient.sendRequest(xf);
        response.prettyPrint(logfile);
        if (response.getBody().hasFault()) {
            assertNotNull(response.getBody().getFault().getFaultString());
        } else {
            fail("A Fault should have been thrown and was not");
        } 
    }
    
    /**
     * Visual test of response structure..
     * <p/>
     * Note: Visual tests invoke server-side operations directly and are not
     * meant to be viewed as proper client invocations.
     *
     * @throws Exception
     */
    public void testFragmentCreateResponseVisual() throws Exception {
        //setup Transfer object for request
        final TransferExtensions transfer = new TransferExtensions();
        transfer.setXmlBinding(binding);
        transfer.addNamespaceDeclarations(NAMESPACES);
        transfer.setAction(Transfer.CREATE_RESPONSE_URI);
        transfer.setTo(DESTINATION);
        
        //xpath expression
        final String expression = "//foo";
        
        //this call ensures the fragment header is initialized
        transfer.setFragmentHeader(expression, XPath.NS_URI);
        final SOAPHeaderElement fragmentHeader = transfer.getFragmentHeader();
        
        //simulate the DOM model being modified
        final List<Node> content = new ArrayList<Node>();
        final Document doc = transfer.newDocument();
        final Element isElement = doc.createElement("a");
        final Element fooElement = doc.createElement("foo");
        final Element barElement = doc.createElement("bar");
        barElement.setTextContent("this is a test of fragments");
        fooElement.appendChild(barElement);
        isElement.appendChild(fooElement);
        content.add(isElement);
        
        //simulate the request content whci came off the wire
        final List<Object> requestContent = new ArrayList<Object>();
        final Element requestFooElement = doc.createElement("foo");//create a foo with no bar
        requestContent.add(requestFooElement);

        final EndpointReferenceType epr = Addressing.createEndpointReference(transfer.getTo(), null, null, null, null);
        transfer.setFragmentCreateResponse(fragmentHeader, epr);
        
        //print contents of Transfer
        transfer.prettyPrint(logfile);
        
        final ByteArrayOutputStream bos = new ByteArrayOutputStream();
        transfer.writeTo(bos);
        final TransferExtensions trans = new TransferExtensions(new ByteArrayInputStream(bos.toByteArray()));
        
        //try to get the fragmenttransfer header
        final SOAPHeaderElement fragmentTransferHeader2 = trans.getFragmentHeader();
        assertNotNull(fragmentTransferHeader2);
        assertEquals(expression, fragmentTransferHeader2.getTextContent());
        assertNotNull(fragmentTransferHeader2.getAttributeValue(TransferExtensions.DIALECT));
    }
    
    /**
     * Actual test of the Fragment Create operation
     *
     * @throws Exception
     */
    public void testFragmentCreate() throws Exception {
        //setup Transfer object for request
        final TransferExtensions transfer = new TransferExtensions();
        transfer.setXmlBinding(binding);
        if (!checkIfBindingIsAvailable(transfer.getXmlBinding())) {
            // skip this test if JAXB is not initialized with the foo.test package
            return;
        }
        transfer.addNamespaceDeclarations(NAMESPACES);
        transfer.setAction(Transfer.CREATE_ACTION_URI);
        transfer.setReplyTo(Addressing.ANONYMOUS_ENDPOINT_URI);
        transfer.setMessageId(UUID_SCHEME + UUID.randomUUID().toString());
        
        //xpath expression
        final String expression = "//jb:foo";
        //this call ensures the fragment header is initialized
        transfer.setFragmentHeader(expression, XPath.NS_URI);
        
        //build an XmlFragment from XmlBeans for adding request content to
        final MixedDataType mixedDataType = Management.FACTORY.createMixedDataType();
        final JAXBElement<MixedDataType> xmlFragment = Management.FACTORY.createXmlFragment(mixedDataType);
        
        //request body content
        final foo.test.ObjectFactory of = new foo.test.ObjectFactory();
        final Foo foo = of.createFoo();
        mixedDataType.getContent().add(foo);
        
        //marshall content into XmlFragment
        binding.marshal(xmlFragment, transfer.getBody());
        
        //send request
        final Management mgmt = new Management(transfer);
        mgmt.setTo(DESTINATION);
        mgmt.setResourceURI("wsman:test/fragment");
        //print contents of Transfer
        mgmt.prettyPrint(logfile);
        
        final Addressing response = HttpClient.sendRequest(mgmt);
        response.prettyPrint(logfile);
        if (response.getBody().hasFault()) {
            fail(response.getBody().getFault().getFaultString());
        }
        final TransferExtensions trans = new TransferExtensions(response);
        final SOAPHeaderElement fragmentTransferHeader = trans.getFragmentHeader();
        assertNotNull(fragmentTransferHeader);
        assertEquals(expression, fragmentTransferHeader.getTextContent());
        assertNotNull(fragmentTransferHeader.getAttributeValue(TransferExtensions.DIALECT));
        
    }
    
    /**
     * Test of expected failed Fragment Create operation
     *
     * @throws Exception
     */
    public void testFragmentCreateFail() throws Exception {
    	//send a request without the fragment in the body
    	TransferMessageValues settings = new TransferMessageValues();
    	settings.setNamespaceMap(NAMESPACES);
    	settings.setFragment("//jb:foo");
    	settings.setFragmentDialect(XPath.NS_URI);
    	settings.setTo(DESTINATION);
    	settings.setResourceUri("wsman:test/fragment");
    	settings.setTransferMessageActionType(Transfer.CREATE_ACTION_URI);
        settings.setXmlBinding(binding);
    	
    	Transfer xf = TransferUtility.buildMessage(null, settings);
    	
        //print contents of Transfer
        xf.prettyPrint(logfile);
        
        final Addressing response = HttpClient.sendRequest(xf);
        response.prettyPrint(logfile);
        if (response.getBody().hasFault()) {
            assertNotNull(response.getBody().getFault().getFaultString());
        } else {
            fail("A Fault should have been thrown and was not");
        }
    }

    private boolean checkIfBindingIsAvailable(final XmlBinding binding) throws IOException {
        final boolean available = binding.isPackageHandled(JAXB_PACKAGE_FOO_TEST);
        if (!available) {
            final String msg = "Skipping test " + getName() + 
                    " since binding is currently not enabled for package " + 
                    JAXB_PACKAGE_FOO_TEST;
            logfile.write(msg.getBytes());
        }
        return available;
    }
}
