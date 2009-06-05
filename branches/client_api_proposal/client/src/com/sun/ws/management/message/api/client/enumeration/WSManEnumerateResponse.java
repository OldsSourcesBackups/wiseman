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

package com.sun.ws.management.message.api.client.enumeration;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBElement;

import org.dmtf.schemas.wbem.wsman._1.wsman.AnyListType;
import org.dmtf.schemas.wbem.wsman._1.wsman.AttributableEmpty;
import org.dmtf.schemas.wbem.wsman._1.wsman.AttributableNonNegativeInteger;
import org.xmlsoap.schemas.ws._2004._08.addressing.EndpointReferenceType;
import org.xmlsoap.schemas.ws._2004._09.enumeration.EnumerateResponse;

import com.sun.ws.management.message.api.client.soap.SOAPResponse;
import com.sun.ws.management.message.api.constants.WSManEnumerationConstants;
import com.sun.ws.management.server.EnumerationItem;
import com.sun.ws.management.xml.XmlBinding;

public class WSManEnumerateResponse extends WSManEnumerationResponse {

	private boolean enumerateResponseRead;
	private EnumerateResponse enumerateResponse;
	private boolean itemsCountRead;
	private BigInteger itemsCount;
	private boolean itemsRead;
	private List<EnumerationItem> items;
	private boolean isEosRead;
	private boolean isEos;

	private final EndpointReferenceType epr;
	private final Map<String, ?> context;
	private final XmlBinding binding;
	private int maxElements = -1;

	WSManEnumerateResponse() {
		super(null);
		this.epr = null;
		this.context = null;
		this.binding = null;
	}

	public WSManEnumerateResponse(final SOAPResponse response) {
		super(response);
		this.epr = null;
		this.context = null;
		this.binding = null;
	}

	protected WSManEnumerateResponse(final SOAPResponse response,
			final EndpointReferenceType epr, final Map<String, ?> context,
			final XmlBinding binding, final int maxElements) {
		super(response);
		this.epr = epr;
		this.context = context;
		this.binding = binding;
		this.maxElements = maxElements;
	}

	public EnumerateResponse getEnumerateResponse() throws Exception {
		if (!enumerateResponseRead) {
			enumerateResponseRead = true;
			final Object payload = getPayload();
			if ((payload != null) && (payload instanceof EnumerateResponse))
				enumerateResponse = (EnumerateResponse) payload;
		}
		return enumerateResponse;
	}

	public BigInteger getTotalItemsCountEstimate() throws Exception {
		if (!itemsCountRead) {
			itemsCountRead = true;
			final Object header = getHeader(WSManEnumerationConstants.TOTAL_ITEMS_COUNT_ESTIMATE);
			if (header != null) {
				if ((header instanceof JAXBElement)
						&& (((JAXBElement) header).getDeclaredType()
								.equals(AttributableNonNegativeInteger.class))) {
					itemsCount = ((JAXBElement<AttributableNonNegativeInteger>) header)
							.getValue().getValue();
				}
			}
		}
		return itemsCount;
	}

	public List<EnumerationItem> getItems() throws Exception {
		if (!itemsRead) {
			itemsRead = true;

			final EnumerateResponse enumerateResponse = getEnumerateResponse();
			if (enumerateResponse != null) {
				final AnyListType anyList = (AnyListType) getAnyObject(
						enumerateResponse.getAny(), AnyListType.class,
						WSManEnumerationConstants.ITEMS);
				if (anyList != null) {
					items = WSManPullResponse.getEnumerationIteList(anyList
							.getAny());
				}
			}
		}
		return items;
	}

	public String getEnumerationContext() throws Exception {
		return (String) getEnumerateResponse().getEnumerationContext()
				.getContent().get(0);
	}

	public boolean isEndOfSequence() throws Exception {
		if (!isEosRead) {
			isEosRead = true;
			final EnumerateResponse enumerateResponse = getEnumerateResponse();

			if (enumerateResponse != null) {
				final Object eos = getAnyObject(enumerateResponse.getAny(),
						AttributableEmpty.class,
						WSManEnumerationConstants.END_OF_SEQUENCE);
				isEos = (null != eos);
			} else {
				isEos = false;
			}
		}
		return isEos;
	}

	public WSManPullRequest createPullRequest() throws Exception {
		if (isEndOfSequence())
			return null;

		final WSManPullRequest request = new WSManPullRequest(epr, context,
				binding);
		request.setPull(getEnumerationContext(), 0, this.maxElements, null);
		return request;
	}

	public WSManPullRequest createPullRequest(final int maxElements)
			throws Exception {
		if (isEndOfSequence())
			return null;

		final WSManPullRequest request = new WSManPullRequest(epr, context,
				binding);
		request.setPull(getEnumerationContext(), 0, maxElements, null);
		return request;
	}

	public WSManReleaseResponse release() throws Exception {
		if (isEndOfSequence())
			return null;

		final WSManReleaseRequest release = new WSManReleaseRequest(epr,
				context, binding);
		release.setRelease(getEnumerationContext());
		return new WSManReleaseResponse(release.invoke());
	}
}