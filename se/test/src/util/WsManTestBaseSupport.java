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
 **Revision 1.12  2007/11/30 14:32:39  denis_rachal
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
 **Revision 1.11  2007/06/18 17:57:28  nbeers
 **Fix for Issue #119 (EnumerationUtility.buildMessage() generates incorrect msg).
 **
 **Revision 1.10  2007/06/04 06:25:12  denis_rachal
 **The following fixes have been made:
 **
 **   * Moved test source to se/test/src
 **   * Moved test handlers to /src/test/src
 **   * Updated logging calls in HttpClient & Servlet
 **   * Fxed compiler warning in AnnotationProcessor
 **   * Added logging files for client junit tests
 **   * Added changes to support Maven builds
 **   * Added JAX-WS libraries to CVS ignore
 **
 **Revision 1.9  2007/05/30 20:30:29  nbeers
 **Add HP copyright header
 **
 ** 
 *
 * $Id: WsManTestBaseSupport.java,v 1.1 2007-12-03 09:15:10 denis_rachal Exp $
 */
package util;

import java.io.IOException;
import java.util.Set;

import javax.xml.bind.JAXBException;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFault;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;


import org.dmtf.schemas.wbem.wsman._1.wsman.OptionType;
import org.dmtf.schemas.wbem.wsman._1.wsman.SelectorType;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xmlsoap.schemas.ws._2004._09.enumeration.EnumerateResponse;
import org.xmlsoap.schemas.ws._2004._09.enumeration.PullResponse;

import com.sun.ws.management.Management;
import com.sun.ws.management.ManagementUtility;
import com.sun.ws.management.addressing.Addressing;
import com.sun.ws.management.enumeration.Enumeration;
import com.sun.ws.management.enumeration.EnumerationMessageValues;
import com.sun.ws.management.enumeration.EnumerationUtility;
import com.sun.ws.management.transfer.Transfer;
import com.sun.ws.management.transfer.TransferMessageValues;
import com.sun.ws.management.transfer.TransferUtility;
import com.sun.ws.management.transport.HttpClient;

public class WsManTestBaseSupport extends TestBase {
	protected static final int TEST_TIMEOUT = 30000;
	private XPath xpath;

	public WsManTestBaseSupport(final String testName) {
		super(testName);

		xpath = XPathFactory.newInstance().newXPath();
	}

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	protected Management sendCreateRequest(String destination,
			String resourceUri, Document propertyDocument)
			throws SOAPException, JAXBException,
			DatatypeConfigurationException, IOException {

		// Build Request Document
		TransferMessageValues settings = TransferMessageValues.newInstance();
		settings.setResourceUri(resourceUri);
		settings.setTo(destination);
		settings.setTransferMessageActionType(Transfer.CREATE_ACTION_URI);
		settings.setXmlBinding(binding);
		final Transfer mgmt = TransferUtility.buildMessage(null, settings);

		mgmt.getBody().addDocument(propertyDocument);

		// Send request
		Addressing response = HttpClient.sendRequest(mgmt);

		// Look for returned faults
		if (response.getBody().hasFault()) {
			SOAPFault fault = response.getBody().getFault();
			throw new SOAPException(fault.getFaultString());
		}

		return new Management(response);
	}

	protected Management sendDeleteRequest(String destination,
			String resourceUri, Set<SelectorType> selectors)
			throws SOAPException, JAXBException,
			DatatypeConfigurationException, IOException {

		// Build Request Document
		TransferMessageValues settings = TransferMessageValues.newInstance();
		settings.setResourceUri(resourceUri);
		settings.setTo(destination);
		settings.setSelectorSet(selectors);
		settings.setTransferMessageActionType(Transfer.DELETE_ACTION_URI);
		settings.setXmlBinding(binding);
		final Transfer mgmt = TransferUtility.buildMessage(null, settings);

		// Send request
		Addressing response = HttpClient.sendRequest(mgmt);

		// Look for returned faults
		if (response.getBody().hasFault()) {
			SOAPFault fault = response.getBody().getFault();
			throw new SOAPException(fault.getFaultString());
		}
		return new Management(response);

	}

	protected Management sendPutRequest(String destination, String resourceUri,
			Set<SelectorType> selectors, Document propertyDocument)
			throws SOAPException, JAXBException,
			DatatypeConfigurationException, IOException {

		// Build Request Document
		TransferMessageValues settings = TransferMessageValues.newInstance();
		settings.setResourceUri(resourceUri);
		settings.setTo(destination);
		settings.setSelectorSet(selectors);
		settings.setTransferMessageActionType(Transfer.PUT_ACTION_URI);
		settings.setXmlBinding(binding);
		final Transfer mgmt = TransferUtility.buildMessage(null, settings);

		mgmt.getBody().addDocument(propertyDocument);

		// Send request
		Addressing response = HttpClient.sendRequest(mgmt);

		// Look for returned faults
		if (response.getBody().hasFault()) {
			SOAPFault fault = response.getBody().getFault();
			throw new SOAPException(fault.getFaultString());
		}
		return new Management(response);
	}

	protected Management sendGetRequest(String destination, String resourceUri,
			Set<SelectorType> selectors, Set<OptionType> options)
			throws SOAPException, JAXBException,
			DatatypeConfigurationException, IOException {

		// Build Request Document
		TransferMessageValues settings = TransferMessageValues.newInstance();
		settings.setResourceUri(resourceUri);
		settings.setTo(destination);
		settings.setSelectorSet(selectors);
		settings.setTransferMessageActionType(Transfer.GET_ACTION_URI);
		settings.setXmlBinding(binding);
		if (options != null) {
			settings.setOptionSet(options);
		}
		final Transfer mgmt = TransferUtility.buildMessage(null, settings);

		// Send the get request to the server
		Addressing response = HttpClient.sendRequest(mgmt);

		// Look for returned faults
		if (response.getBody().hasFault()) {
			SOAPFault fault = response.getBody().getFault();
			throw new SOAPException(fault.getFaultString());
		}

		try {
			ManagementUtility.getAsResourceState(response);

		} catch (Exception e) {
			fail("Unexpected exception: \n" + e.getMessage());
		}
		return new Management(response);
	}

	protected EnumerateResponse sendEnumerateRequest(String destination,
			String resourceUri, String filter) throws SOAPException,
			JAXBException, DatatypeConfigurationException, IOException {

		EnumerationMessageValues settings = EnumerationMessageValues
				.newInstance();
		settings.setFilter(filter);
		settings.setTimeout(6000);
		settings.setTo(destination);
		settings.setResourceUri(resourceUri);
		settings.setXmlBinding(binding);
		final Enumeration enu = EnumerationUtility.buildMessage(null, settings);

		final Addressing response = HttpClient.sendRequest(enu);

		if (response.getBody().hasFault()) {
			fail(response.getBody().getFault().getFaultString());
		}

		final Enumeration enuResponse = new Enumeration(response);
		final EnumerateResponse enr = enuResponse.getEnumerateResponse();
		return enr;
	}

	protected PullResponse sendPullRequest(String destination,
			String resourceUri, Object ctx) throws SOAPException,
			JAXBException, DatatypeConfigurationException, IOException {
		final Addressing response = sendEnumRequest(ctx, destination,
				resourceUri, Enumeration.PULL_ACTION_URI);
		if (response.getBody().hasFault()) {
			fail(response.getBody().getFault().getFaultString());
		}

		final Enumeration enuResponse = new Enumeration(response);
		final PullResponse enr = enuResponse.getPullResponse();
		return enr;

	}

	public Addressing sendEnumRequest(Object ctx, String destination,
			String resourceUri, String action) throws SOAPException,
			JAXBException, DatatypeConfigurationException, IOException {
		EnumerationMessageValues settings = EnumerationMessageValues
				.newInstance();
		settings.setEnumerationMessageActionType(action);
		settings.setTimeout(60000);
		settings.setMaxElements(100);
		settings.setEnumerationContext(ctx);
		settings.setTo(destination);
		settings.setResourceUri(resourceUri);
		settings.setXmlBinding(binding);
		final Enumeration enu = EnumerationUtility.buildMessage(null, settings);

		final Addressing response = HttpClient.sendRequest(enu);
		return response;
	}

	protected EnumerateResponse sendGetStatusRequest() throws SOAPException,
			JAXBException, DatatypeConfigurationException, IOException {
		return null;
	}

	protected EnumerateResponse sendRenewRequest() throws SOAPException,
			JAXBException, DatatypeConfigurationException, IOException {
		return null;
	}

	protected Addressing sendReleaseRequest(String destination,
			String resourceUri, Object ctx) throws SOAPException,
			JAXBException, DatatypeConfigurationException, IOException {
		EnumerationMessageValues settings = EnumerationMessageValues
				.newInstance();
		settings
				.setEnumerationMessageActionType(Enumeration.RELEASE_ACTION_URI);
		settings.setTimeout(60000);
		settings.setEnumerationContext(ctx);
		settings.setTo(destination);
		settings.setResourceUri(resourceUri);
		settings.setXmlBinding(binding);
		final Enumeration enu = EnumerationUtility.buildMessage(null, settings);

		Addressing response = HttpClient.sendRequest(enu);

		return response;

	}

	/**
	 * Returns the element text of the Element pointed to by the provided XPath.
	 * @param xPathExpression
	 * @return A string containing the element text.
	 * @throws XPathExpressionException
	 */
	public String getXPathText(String xPathExpression, Object stateDocument)
			throws XPathExpressionException {
		Object resultOb = xpath.evaluate(xPathExpression, stateDocument,
				XPathConstants.STRING);
		if (resultOb == null)
			return null;
		return (String) resultOb;
	}

	/** 
	 * Returns a list of nodes that match the provided XPath criteria.
	 * 
	 * @param xPathExpression
	 * @return A list of matching nodes.
	 * @throws XPathExpressionException
	 */
	public NodeList getXPathValues(String xPathExpression, Object stateDocument)
			throws XPathExpressionException {
		Object nodes = xpath.evaluate(xPathExpression, stateDocument,
				XPathConstants.NODESET);
		if (nodes == null)
			return null;
		NodeList nodelist = (NodeList) nodes;
		return nodelist;
	}

}
