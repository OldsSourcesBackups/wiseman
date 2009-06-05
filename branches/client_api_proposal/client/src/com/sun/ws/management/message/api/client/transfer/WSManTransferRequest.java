/*
 * Copyright 2005-2008 Sun Microsystems, Inc.
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
 ** Copyright (C) 2006, 2007, 2008 Hewlett-Packard Development Company, L.P.
 **
 ** Authors: Simeon Pinder (simeon.pinder@hp.com), Denis Rachal (denis.rachal@hp.com),
 ** Nancy Beers (nancy.beers@hp.com), William Reichardt
 **
 *
 */

package com.sun.ws.management.message.api.client.transfer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.soap.SOAPException;

import org.dmtf.schemas.wbem.wsman._1.wsman.DialectableMixedDataType;
import org.xmlsoap.schemas.ws._2004._08.addressing.EndpointReferenceType;
import org.xmlsoap.schemas.ws._2004._09.transfer.ObjectFactory;

import com.sun.ws.management.Management;
import com.sun.ws.management.message.api.client.wsman.WSManRequest;
import com.sun.ws.management.soap.SOAP;
import com.sun.ws.management.xml.XPath;
import com.sun.ws.management.xml.XmlBinding;

public class WSManTransferRequest extends WSManRequest {

	public static final String NS_PREFIX = "wxf";
	public static final String NS_URI = "http://schemas.xmlsoap.org/ws/2004/09/transfer";

	public static final ObjectFactory FACTORY = new ObjectFactory();

	public WSManTransferRequest(final EndpointReferenceType epr,
			final String action, final Map<String, ?> context,
			final XmlBinding binding) throws Exception {
		super(epr, action, context, binding);
		addNamespaceDeclarations();
	}

	public void setFragmentHeader(final String xpath) throws SOAPException,
			JAXBException {
		setFragmentHeader(xpath, null);
	}

	public void setFragmentHeader(final String xpath,
			final Map<String, String> namespaces) throws SOAPException,
			JAXBException {
		final List<Object> expression = new ArrayList<Object>();
		expression.add(xpath);
		setFragmentHeader(XPath.NS_URI, expression, namespaces);
	}

	public void setFragmentHeader(final String dialect,
			final List<Object> expression, final Map<String, String> namespaces)
			throws SOAPException, JAXBException {
		if (expression == null)
			return;

		final DialectableMixedDataType dialectableMixedDataType = Management.FACTORY
				.createDialectableMixedDataType();
		if (dialect != null) {
			dialectableMixedDataType.setDialect(dialect);
		}

		dialectableMixedDataType.getOtherAttributes().put(SOAP.MUST_UNDERSTAND,
				Boolean.TRUE.toString());

		// add the query string to the content of the FragmentTransfer Header
		dialectableMixedDataType.getContent().add(expression);

		final JAXBElement<DialectableMixedDataType> fragmentTransfer = Management.FACTORY
				.createFragmentTransfer(dialectableMixedDataType);

		// We add any namspaces to the object.
		// This is needed for namespaces used in the expression itself, e.g.
		// the XPath expression can have namespace qualifiers.
		if (namespaces != null) {
			addNamespaceDeclarations(namespaces);
		}
		addHeader(fragmentTransfer);
	}

	private void addNamespaceDeclarations() throws SOAPException {
		// Having the namespace declarations in the envelope keeps
		// JAXB from putting these on every element
		addNamespaceDeclaration(NS_PREFIX, NS_URI);
	}
}