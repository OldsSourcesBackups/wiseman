/*
 * Copyright 2006 Hewlett-Packard Development Company, L.P.
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

package com.sun.ws.management.server.handler.wsman.test;

import com.sun.ws.management.Management;
import com.sun.ws.management.addressing.ActionNotSupportedFault;
import com.sun.ws.management.server.NamespaceMap;
import com.sun.ws.management.transfer.Transfer;
import com.sun.ws.management.transfer.TransferExtensions;
import com.sun.ws.management.xml.XPath;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.soap.SOAPHeaderElement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.xmlsoap.schemas.ws._2004._08.addressing.EndpointReferenceType;

/**
 * A test handler for fragment level operations.
 * <p/>
 * Fragement requests should be made against this document
 * <pre>
 * &lt;?xml version="1.0" encoding="UTF-8"?&gt;
 * &lt;jb:this xmlns:jb="http://test.foo" &gt;
 *   &lt;jb:is&gt;
 *     &lt;jb:a&gt;
 *       &lt;jb:foo&gt;
 *         &lt;jb:bar&gt;
 *             this is a test of fragments
 *         &lt;/jb:bar&gt;
 *       &lt;/jb:foo&gt;
 *     &lt;/jb:a&gt;
 *   &lt;/jb:is&gt;
 * &lt;/jb:this&gt;
 * </pre>
 */
public class fragment_Handler extends base_Handler {
    
    public static final String NS_PREFIX = "f";
    public static final String NS_URI = "https://wiseman.dev.java.net/1/fragment";
    
    private static final String CUSTOM_JAXB_PREFIX = "jb";
    private static final String CUSTOM_JAXB_NS = "http://test.foo";
    private static final Map<String, String> NAMESPACES = new HashMap<String, String>();

    static {
        NAMESPACES.put(CUSTOM_JAXB_PREFIX, CUSTOM_JAXB_NS);
    }

    public void handle(final String action, final String resource, final Management request, final Management response) throws Exception {
        
        final Document doc = response.newDocument();
        buildContentDocument(doc);
        
        final TransferExtensions transExtRequest = new TransferExtensions(request);
        final TransferExtensions transExtResponse = new TransferExtensions(response);
        
        final SOAPHeaderElement fragmentHeader = transExtRequest.getFragmentHeader();
        final String expression = fragmentHeader == null ? null : fragmentHeader.getTextContent();
        final String dialect = fragmentHeader == null ? null : fragmentHeader.getAttributeValue(TransferExtensions.DIALECT);
        
        final NamespaceMap map = new NamespaceMap(NAMESPACES);
        
        if (Transfer.GET_ACTION_URI.equals(action)) {
            response.addNamespaceDeclarations(NAMESPACES);
            response.setAction(Transfer.GET_RESPONSE_URI);
            
            if (fragmentHeader == null) {
                // this is a regular transfer: not a fragment transfer, return the entire doc
                response.getBody().addDocument(doc);
            } else {
                transExtResponse.setFragmentGetResponse(fragmentHeader,
                        XPath.filter(doc.getDocumentElement(), expression, dialect, map));
            }
            return;
        }
        
        if (Transfer.PUT_ACTION_URI.equals(action)) {
            response.addNamespaceDeclarations(NAMESPACES);
            response.setAction(Transfer.PUT_RESPONSE_URI);
            
            if (fragmentHeader == null) {
                // this is a regular transfer: not a fragment transfer, update the entire doc
                // TODO
            } else {
                final Node xmlFragmentNode = (Node) transExtRequest.getBody().getChildElements().next();
                final NodeList childNodes = xmlFragmentNode.getChildNodes();
                final List<Node> nodeContent = new ArrayList<Node>();
                for (int i = 0; i < childNodes.getLength(); i++) {
                    nodeContent.add(childNodes.item(i));
                }
                transExtResponse.setFragmentPutResponse(fragmentHeader, nodeContent,
                        expression, XPath.filter(doc.getDocumentElement(), expression, dialect, map));
            }
            return;
        }
        
        if (Transfer.DELETE_ACTION_URI.equals(action)) {
            response.addNamespaceDeclarations(NAMESPACES);
            response.setAction(Transfer.DELETE_RESPONSE_URI);
            
            if (fragmentHeader == null) {
                // this is a regular transfer: not a fragment transfer, delete the entire doc
                // TODO
            } else {
                transExtResponse.setFragmentDeleteResponse(fragmentHeader,
                        XPath.filter(doc.getDocumentElement(), expression, dialect, map));
            }
            return;
        }
        
        if (Transfer.CREATE_ACTION_URI.equals(action)) {
            response.addNamespaceDeclarations(NAMESPACES);
            response.setAction(Transfer.CREATE_RESPONSE_URI);
            
            if (fragmentHeader == null) {
                // this is a regular transfer: not a fragment transfer, create the entire doc
                // TODO
            } else {
                final Node xmlFragmentNode = (Node) transExtRequest.getBody().getChildElements().next();
                final NodeList childNodes = xmlFragmentNode.getChildNodes();
                final List<Node> nodeContent = new ArrayList<Node>();
                for (int i = 0; i < childNodes.getLength(); i++) {
                    nodeContent.add(childNodes.item(i));
                }
                final EndpointReferenceType epr = 
                        transExtRequest.createEndpointReference(transExtRequest.getTo(), 
                        null, null, null, null);
                transExtResponse.setFragmentCreateResponse(fragmentHeader, nodeContent,
                        expression, XPath.filter(doc.getDocumentElement(), expression, dialect, map),
                        epr);
            }
            return;
        }
        
        throw new ActionNotSupportedFault(action);
    }
    
    /**
     * Method to construct the document to be traversed with the fragment
     * request.
     * <p/>
     * In reality this document may originate from some backend, and is itself
     * the backend representation
     *
     * @param doc
     */
    protected void buildContentDocument(final Document doc) {
        final Element thisElement = doc.createElementNS(CUSTOM_JAXB_NS, "this");
        final Element isElement = doc.createElementNS(CUSTOM_JAXB_NS, "is");
        final Element aElement = doc.createElementNS(CUSTOM_JAXB_NS, "a");
        final Element fooElement = doc.createElementNS(CUSTOM_JAXB_NS, "foo");
        final Element barElement = doc.createElementNS(CUSTOM_JAXB_NS, "bar");
        barElement.setTextContent("this is a test of fragments");
        thisElement.appendChild(isElement);
        isElement.appendChild(aElement);
        aElement.appendChild(fooElement);
        fooElement.appendChild(barElement);
        doc.appendChild(thisElement);
    }
}
