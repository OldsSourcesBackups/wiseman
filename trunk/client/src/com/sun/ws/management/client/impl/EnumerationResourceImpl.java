package com.sun.ws.management.client.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Vector;
import java.util.logging.Logger;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.Duration;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFault;
import javax.xml.xpath.XPathExpressionException;

import org.dmtf.schemas.wbem.wsman._1.wsman.MixedDataType;
import org.dmtf.schemas.wbem.wsman._1.wsman.SelectorSetType;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xmlsoap.schemas.ws._2004._08.addressing.EndpointReferenceType;
import org.xmlsoap.schemas.ws._2004._09.enumeration.EnumerateResponse;
import org.xmlsoap.schemas.ws._2004._09.enumeration.EnumerationContextType;
import org.xmlsoap.schemas.ws._2004._09.enumeration.FilterType;
import org.xmlsoap.schemas.ws._2004._09.enumeration.ItemListType;

import com.sun.ws.management.Management;
import com.sun.ws.management.addressing.Addressing;
import com.sun.ws.management.client.EnumerationCtx;
import com.sun.ws.management.client.EnumerationResourceState;
import com.sun.ws.management.client.Resource;
import com.sun.ws.management.client.ResourceState;
import com.sun.ws.management.client.exceptions.FaultException;
import com.sun.ws.management.client.exceptions.NoMatchFoundException;
import com.sun.ws.management.enumeration.Enumeration;
import com.sun.ws.management.enumeration.EnumerationExtensions;
import com.sun.ws.management.enumeration.EnumerationExtensions.Mode;
import com.sun.ws.management.server.EnumerationItem;
import com.sun.ws.management.transfer.Transfer;
import com.sun.ws.management.transport.HttpClient;
import com.sun.ws.management.xml.XPath;

public class EnumerationResourceImpl extends TransferableResourceImpl  {
	private static final String XPATH_TO_EPRS = "//*[namespace-uri()=\"http://schemas.xmlsoap.org/ws/2004/08/addressing\" and local-name()=\"EndpointReference\"]";

	public ArrayList<String> reqResList = new ArrayList<String>();
	
	protected long itemCount = 0;
	
	protected List<EnumerationItem> enumItems = null;

//	protected static final String UUID_SCHEME = "uuid:";

	private static Logger log = Logger.getLogger(EnumerationResourceImpl.class
			.getName());

	public EnumerationResourceImpl(String destination, String resourceURI,
			 SelectorSetType selectors) throws SOAPException,
			JAXBException {
		super(destination, resourceURI, selectors);
	}

	public EnumerationResourceImpl(Element eprElement, String endpointUrl)
			throws SOAPException, JAXBException {
		super(eprElement, endpointUrl);
	}

	// attributes
	// ******************* WS Enumeration *************************************
	
	/**
	 * Starts an enumeration transaction by obtaining an enumeration context.
	 * This is a ticket which must be used in all future calls to access this
	 * enumeration.
	 * 
	 * @param filters
	 *            and array of filter expressions to be applied to the
	 *            enumeration.
	 * @param dialect
	 *            The dialect to be used in filter expressions. XPATH_DIALECT
	 *            can be used for XPath.
	 * @param useEprs
	 *            useEprs sets the EnumerateEpr Element causing subsequent pulls
	 *            to contain erps only
	 * @param timeout
	 * 			  Timeout value for the enumerate request in milliseconds
	 * @return An enumeration context
	 * @throws SOAPException
	 * @throws JAXBException
	 * @throws IOException
	 * @throws FaultException
	 * @throws DatatypeConfigurationException
	 */
	public EnumerationCtx enumerate(String[] filters, String dialect,
			boolean useEprs, boolean useObjects, long timeout)
			throws SOAPException, JAXBException, IOException, FaultException,
			DatatypeConfigurationException 
	{
		
		// Set the timeout on the base class
		this.setMessageTimeout(timeout);
		return enumerate(filters, dialect, useEprs, useObjects);
		
	}
	
	/**
	 * Starts an enumeration transaction by obtaining an enumeration context.
	 * This is a ticket which must be used in all future calls to access this
	 * enumeration.
	 * 
	 * @param filters
	 *            and array of filter expressions to be applied to the
	 *            enumeration.
	 * @param dialect
	 *            The dialect to be used in filter expressions. XPATH_DIALECT
	 *            can be used for XPath.
	 * @param useEprs
	 *            useEprs sets the EnumerateEpr Element causing subsequent pulls
	 *            to contain erps only
	 * @param timeout
	 * 			  Timeout value for the enumerate request in milliseconds
	 * @param getItemCount 
	 * 			  TRUE = return an estimated count of the items in the enumeration
	 * @return An enumeration context
	 * @throws SOAPException
	 * @throws JAXBException
	 * @throws IOException
	 * @throws FaultException
	 * @throws DatatypeConfigurationException
	 */
	public EnumerationCtx enumerate(String[] filters, String dialect,
			boolean useEprs, boolean useObjects, long timeout, boolean getItemCount)
			throws SOAPException, JAXBException, IOException, FaultException,
			DatatypeConfigurationException 
	{
		
		// Set the timeout on the base class
		this.setMessageTimeout(timeout);
		return enumerate(filters, dialect, useEprs, useObjects, getItemCount, null, null, false, 0);
		
	}
	/**
	 * Starts an enumeration transaction by obtaining an enumeration context.
	 * This is a ticket which must be used in all future calls to access this
	 * enumeration.
	 * 
	 * @param filters
	 *            and array of filter expressions to be applied to the
	 *            enumeration.
	 * @param dialect
	 *            The dialect to be used in filter expressions. XPATH_DIALECT
	 *            can be used for XPath.
	 * @param useEprs
	 *            useEprs sets the EnumerateEpr Element causing subsequent pulls
	 *            to contain erps only
	 * @param timeout
	 * 			  Timeout value for the enumerate request in milliseconds
	 * @return An enumeration context
	 * @throws SOAPException
	 * @throws JAXBException
	 * @throws IOException
	 * @throws FaultException
	 * @throws DatatypeConfigurationException
	 */
	public EnumerationCtx enumerate(String[] filters, String dialect,
			boolean useEprs, boolean useObjects, long timeout, Object... params)
			throws SOAPException, JAXBException, IOException, FaultException,
			DatatypeConfigurationException 
	{
		
		// Set the timeout on the base class
		this.setMessageTimeout(timeout);
		return enumerate(filters, dialect, useEprs, useObjects, false, null, null, false, 0, params);
		
	}	

	
	/**
	 * Starts an enumeration transaction by obtaining an enumeration context.
	 * This is a ticket which must be used in all future calls to access this
	 * enumeration.
	 * 
	 * @param filters
	 *            and array of filter expressions to be applied to the
	 *            enumeration.
	 * @param dialect
	 *            The dialect to be used in filter expressions. XPATH_DIALECT
	 *            can be used for XPath.
	 * @param useEprs
	 *            useEprs sets the EnumerateEpr Element causing subsequent pulls
	 *            to contain erps only
	 * @param timeout
	 * 			  Timeout value for the enumerate request in milliseconds
	 * @param getItemCount 
	 * 			  TRUE = return an estimated count of the items in the enumeration
	 * @return An enumeration context
	 * @throws SOAPException
	 * @throws JAXBException
	 * @throws IOException
	 * @throws FaultException
	 * @throws DatatypeConfigurationException
	 */
	public EnumerationCtx enumerate(String[] filters, String dialect,
			boolean useEprs, boolean useObjects, long timeout, boolean getItemCount, Object... params)
			throws SOAPException, JAXBException, IOException, FaultException,
			DatatypeConfigurationException 
	{
		
		// Set the timeout on the base class
		this.setMessageTimeout(timeout);
		return enumerate(filters, dialect, useEprs, useObjects, getItemCount, null, null, false, 0, params);
		
	}	
	/**
	 * Starts an enumeration transaction by obtaining an enumeration context.
	 * This is a ticket which must be used in all future calls to access this
	 * enumeration.
	 * 
	 * @param filters
	 *            and array of filter expressions to be applied to the
	 *            enumeration.
	 * @param dialect
	 *            The dialect to be used in filter expressions. XPATH_DIALECT
	 *            can be used for XPath.
	 * @param useEprs
	 *            useEprs sets the EnumerateEpr Element causing subsequent pulls
	 *            to contain erps only
	 * @return An enumeration context
	 * @throws SOAPException
	 * @throws JAXBException
	 * @throws IOException
	 * @throws FaultException
	 * @throws DatatypeConfigurationException
	 */
	public EnumerationCtx enumerate(String[] filters, String dialect,
			boolean useEprs, boolean useObjects)
			throws SOAPException, JAXBException, IOException, FaultException,
			DatatypeConfigurationException {
		return enumerate(filters, dialect, useEprs, useObjects, false, null, null, false, 0, new Object[0]);
	}
	/**
	 * Starts an enumeration transaction by obtaining an enumeration context.
	 * This is a ticket which must be used in all future calls to access this
	 * enumeration.
	 * 
	 * @param filters
	 *            and array of filter expressions to be applied to the
	 *            enumeration.
	 * @param dialect
	 *            The dialect to be used in filter expressions. XPATH_DIALECT
	 *            can be used for XPath.
	 * @param useEprs
	 *            useEprs sets the EnumerateEpr Element causing subsequent pulls
	 *            to contain erps only
	 * @param getItemCount 
	 * 			  TRUE = return an estimated count of the items in the enumeration
	 * @return An enumeration context
	 * @throws SOAPException
	 * @throws JAXBException
	 * @throws IOException
	 * @throws FaultException
	 * @throws DatatypeConfigurationException
	 */
	public EnumerationCtx enumerate(String[] filters, String dialect,
			boolean useEprs, boolean useObjects, boolean getItemCount, 
			String fragmentRequest, String fragmentDialect, boolean optimized, long maxElem, Object... params)
			throws SOAPException, JAXBException, IOException, FaultException,
			DatatypeConfigurationException {
		
		String enumerationContextId = "";

		final Transfer xf = setTransferProperties(Enumeration.ENUMERATE_ACTION_URI);		
		final Management mgmt = setManagementProperties(xf);
		addOptionSetHeader(mgmt);
		final EnumerationExtensions enu = new EnumerationExtensions(mgmt);
		
		// Enum Mode
		Mode enumerationMode = null;
		if (useEprs) {
			enumerationMode = EnumerationExtensions.Mode.EnumerateEPR;
		}

		if (useEprs && useObjects) {
			enumerationMode = EnumerationExtensions.Mode.EnumerateObjectAndEPR;
		}

	    // If xpathExpression is not null then generate fragment GET 
	    if((fragmentRequest!=null)&&(fragmentRequest.trim().length()>0)){
	    	// Add the Fragement Header
	    	setFragmentHeader(fragmentRequest,fragmentDialect,mgmt);
	    }
	    
	    // If this is an optimized enumeration request, add the appropriate headers
	    if (optimized) {
	    	mgmt.addHeaders(Addressing.createReferencePropertyType(EnumerationExtensions.OPTIMIZE_ENUMERATION, "true"));
	    	mgmt.addHeaders(Addressing.createReferencePropertyType(EnumerationExtensions.MAX_ELEMENTS, new Long(maxElem).toString()));
	    }
	    	
	    //final DatatypeFactory factory = DatatypeFactory.newInstance();
		final FilterType filterType = Enumeration.FACTORY.createFilterType();
		final EndpointReferenceType endTo = Addressing.createEndpointReference(
				"http://host/endTo", null, null, null, null);
		
		if (filters != null) {
			String filter = "";
			for (int i = 0; i < filters.length; i++) {
				filter += filters[i] + "\n";
			}
			if(dialect==null)
				filterType.setDialect(XPath.NS_URI);
			else
				filterType.setDialect(dialect);
			filterType.getContent().add(filter);
			
			if (enumerationMode != null) {
				ArrayList<Object> anys = new ArrayList<Object>(params.length + 1);
				anys.add((Object)enumerationMode.toBinding());
				for (int i = 0; i < params.length; i++)
					anys.add(params[i]);
				enu.setEnumerate(endTo, null, filter == null ? null
					: filterType, anys.toArray());
			} else {
				enu.setEnumerate(endTo, null, filter == null ? null
						: filterType, params);
			}
			
		} else {
			if (enumerationMode != null){
				ArrayList<Object> anys = new ArrayList<Object>(params.length + 1);
				anys.add((Object)enumerationMode.toBinding());
				for (int i = 0; i < params.length; i++)
					anys.add(params[i]);
				enu.setEnumerate(null, null, null, anys.toArray());
			} else {
				enu.setEnumerate(null, null, null, params);				
			}
				
		}

		if (getItemCount) {
			enu.setRequestTotalItemsCountEstimate();
		}

		// store away request and response for display purposes only
		reqResList = new ArrayList<String>();
		reqResList.add(mgmt.toString());
		log.info("REQUEST:\n" + mgmt + "\n");
		final Addressing response = HttpClient.sendRequest(mgmt);

		// Check for fault during message generation
		if (response.getBody().hasFault()) {
			log.severe("FAULT:\n" + response + "\n");
			SOAPFault fault = response.getBody().getFault();
			throw new FaultException(fault.getFaultString());
		}
		log.info("RESPONSE:\n" + response + "\n");

		final EnumerationExtensions enuResponse = new EnumerationExtensions(response);
		
		reqResList.add(enuResponse.toString());
		final EnumerateResponse enr = enuResponse.getEnumerateResponse();

		enumerationContextId = (String) enr.getEnumerationContext()
				.getContent().get(0);
		
		if (getItemCount) {
			itemCount = enuResponse.getTotalItemsCountEstimate().getValue().longValue();
		}
		
		if (optimized) {
			enumItems = new ArrayList<EnumerationItem>();
			
			// Save the returned values from the optimized enumeration
			 List <EnumerationItem> items = new ArrayList<EnumerationItem>();

     		 items = enuResponse.getItems();
			 Iterator iter = items.iterator();
			 while (iter.hasNext()) {
				 Object item = iter.next();
				 enumItems.add((EnumerationItem)item);
			 }
		}
		return new EnumerationCtx(enumerationContextId);
	}

	/**
	 * Assumes the return type will contain EPR's. Each EPR that is found in the
	 * returned resource state will be converted into its own Resource
	 * implementation. This is very useful when the response is a collection of
	 * EPR's such as when the UseEpr element is set.
	 * 
	 * @param enumerationContext
	 *            the context create in your call to enumerate
	 * @param maxTime
	 *            The maxium timeout you are willing to wait for a response
	 * @param maxElements
	 *            the maximum number of elements which should be returned
	 * @param maxCharacters
	 *            the total size of the characters to be contained in
	 * @param endpointUrl
	 * @return
	 * @throws SOAPException
	 * @throws JAXBException
	 * @throws IOException
	 * @throws FaultException
	 * @throws DatatypeConfigurationException
	 * @throws NoMatchFoundException
	 * @throws XPathExpressionException
	 * @throws XPathExpressionException
	 * @throws NoMatchFoundException
	 */
	public Resource[] pullResources(EnumerationCtx enumerationContext,
			int maxTime, int maxElements, int maxCharacters, String endpointUrl)
			throws SOAPException, JAXBException, IOException, FaultException,
			DatatypeConfigurationException, XPathExpressionException,
			NoMatchFoundException {
		ResourceState resState = pull(enumerationContext, maxTime, maxElements,
				maxCharacters);
		NodeList eprNodes = null;
		eprNodes = resState.getValues(XPATH_TO_EPRS);
		Vector<ResourceImpl> ret = new Vector<ResourceImpl>();
		for (int index = 0; index < eprNodes.getLength(); index++) {
			Element eprElement = (Element) eprNodes.item(index);
			ret.add(new ResourceImpl(eprElement, endpointUrl));
		}
		return (Resource[]) ret.toArray(new Resource[] {});
	}

	// TODO wire must implement EnumerateObjectAndEpr
	/**
	 * Requests a list of erps or objects. If you request EPRs or some fragment
	 * of the state of an object this version of pull will just return them as a
	 * resource state and you will have to extract the EPRs yourself. Use
	 * pullResources for better access to EPRs.
	 * 
	 * @param enumerationContext
	 *            The context created from a previous enumerate call.
	 * @param maxTime
	 *            The maxium timeout you are willing to wait for a response
	 * @param maxElements
	 *            the maximum number of elements which should be returned
	 * @param maxCharacters
	 *            the total size of the characters to be contained in the
	 *            response
	 * @return A resource state representing the returned complex type of the
	 *         pull. Often this state will contain multiple entries from a
	 *         number of resources.
	 * 
	 * @throws SOAPException
	 * @throws JAXBException
	 * @throws IOException
	 * @throws FaultException
	 * @throws DatatypeConfigurationException
	 */
	public EnumerationResourceState pull(EnumerationCtx enumerationContext, int maxTime,
			int maxElements, int maxCharacters) throws SOAPException,
			JAXBException, IOException, FaultException,
			DatatypeConfigurationException {

		return pull(enumerationContext, maxTime, maxElements, maxCharacters, false, null, null);
	}

	/**
	 * Requests a list of erps or objects. If you request EPRs or some fragment
	 * of the state of an object this version of pull will just return them as a
	 * resource state and you will have to extract the EPRs yourself. Use
	 * pullResources for better access to EPRs.
	 * 
	 * @param enumerationContext
	 *            The context created from a previous enumerate call.
	 * @param maxTime
	 *            The maxium timeout you are willing to wait for a response
	 * @param maxElements
	 *            the maximum number of elements which should be returned
	 * @param maxCharacters
	 *            the total size of the characters to be contained in the
	 *            response
	 * @return A resource state representing the returned complex type of the
	 *         pull. Often this state will contain multiple entries from a
	 *         number of resources.
	 * 
	 * @throws SOAPException
	 * @throws JAXBException
	 * @throws IOException
	 * @throws FaultException
	 * @throws DatatypeConfigurationException
	 */
	public EnumerationResourceState pull(EnumerationCtx enumerationContext, int maxTime,
			int maxElements, int maxCharacters, String fragmentRequest, String fragmentDialect) throws SOAPException,
			JAXBException, IOException, FaultException,
			DatatypeConfigurationException {

		return pull(enumerationContext, maxTime, maxElements, maxCharacters, false, fragmentRequest, fragmentDialect);
	}	/**
	 * Requests a list of erps or objects. If you request EPRs or some fragment
	 * of the state of an object this version of pull will just return them as a
	 * resource state and you will have to extract the EPRs yourself. Use
	 * pullResources for better access to EPRs.
	 * 
	 * @param enumerationContext
	 *            The context created from a previous enumerate call.
	 * @param maxTime
	 *            The maxium timeout you are willing to wait for a response
	 * @param maxElements
	 *            the maximum number of elements which should be returned
	 * @param maxCharacters
	 *            the total size of the characters to be contained in the
	 *            response
	 * @param getItemCount
	 * 			Retrieve the total number of enumerated items
	 * @return A resource state representing the returned complex type of the
	 *         pull. Often this state will contain multiple entries from a
	 *         number of resources.
	 * 
	 * @throws SOAPException
	 * @throws JAXBException
	 * @throws IOException
	 * @throws FaultException
	 * @throws DatatypeConfigurationException
	 */
	public EnumerationResourceState pull(EnumerationCtx enumerationContext, int maxTime,
			int maxElements, int maxCharacters, boolean getItemCount, String fragmentRequest, String fragmentDialect) throws SOAPException,
			JAXBException, IOException, FaultException,
			DatatypeConfigurationException {

		// Now generate the request for an EnumCtxId with parameters passed in
		final EnumerationExtensions enu = setEnumerationProperties(Enumeration.PULL_ACTION_URI);
		Duration timeout = null;
		if(maxTime>0)
			timeout = DatatypeFactory.newInstance().newDuration(maxTime);
		enu.setPull(enumerationContext.toString(), maxCharacters, maxElements,
				timeout);
		final Management mgmt = setManagementProperties(enu);
		
	    // Add any user defined options to the header
		addOptionSetHeader(mgmt);
		
		if (getItemCount){
			enu.setRequestTotalItemsCountEstimate();
		}
		
	    // If xpathExpression is not null then generate fragment pull 
	    if((fragmentRequest!=null)&&(fragmentRequest.trim().length()>0)){
	    	// Add the Fragement Header
	    	setFragmentHeader(fragmentRequest,fragmentDialect,mgmt);
	    }
	    
	    reqResList = new ArrayList<String>();
		reqResList.add(mgmt.toString());
		log.info("REQUEST:\n" + mgmt + "\n");
		final Addressing response = HttpClient.sendRequest(mgmt);

		// Check for fault during message generation
		if (response.getBody().hasFault()) {
			log.severe("FAULT:\n" + response + "\n");
			SOAPFault fault = response.getBody().getFault();
			throw new FaultException(fault.getFaultString());
		}

		log.info("RESPONSE:\n" + response + "\n");

		final EnumerationExtensions enuResponse = new EnumerationExtensions(response);
		reqResList.add(enuResponse.toString());

		updateEnumContext(enuResponse, enumerationContext);

		SOAPBody body = response.getBody();

		if (getItemCount) {
			itemCount = enuResponse.getTotalItemsCountEstimate().getValue().longValue();
		}
			
		return new EnumerationResourceStateImpl(body.extractContentAsDocument());
	}
	
	private EnumerationExtensions setEnumerationProperties(String action) throws JAXBException, SOAPException {
		final EnumerationExtensions enu= new EnumerationExtensions();
		
		enu.setAction(action);
		enu.setReplyTo(Addressing.ANONYMOUS_ENDPOINT_URI);
		enu.setMessageId("uuid:" + UUID.randomUUID().toString());
		return enu;
	}

	/**
	 * The spec indicates that the Enumeration Context representation can be
	 * changed during each subsequent pull. To support this we must check for an
	 * EnumerationContext element present in the PullResponse and update our
	 * token class enumerationContext so that it remain consistant to the client
	 * user as well as the server.
	 * 
	 * @param enuResponse
	 * @param enumerationContext
	 * @throws SOAPException
	 * @throws JAXBException
	 */
	private void updateEnumContext(Enumeration enuResponse,
			EnumerationCtx enumerationContext) throws JAXBException,
			SOAPException {
		EnumerationContextType pullEnumContext = enuResponse.getPullResponse()
				.getEnumerationContext();
		if (pullEnumContext == null || pullEnumContext.getContent() == null)
			return;
		List<Object> content = pullEnumContext.getContent();
		if (content.size() == 0)
			return;
		enumerationContext.setContext((String) content.get(0));

	}

	/**
	 * Releases a context for enumeration.
	 * 
	 * @param enumerationContext
	 *            Nameof the context to release
	 * @throws DatatypeConfigurationException
	 * @throws FaultException
	 * @throws IOException
	 * @throws JAXBException
	 * @throws SOAPException
	 */
	public void release(EnumerationCtx enumerationContext)
			throws SOAPException, JAXBException, IOException, FaultException,
			DatatypeConfigurationException {

		// Now generate the request for an EnumCtxId with parameters passed in
		final Enumeration enu = setEnumerationProperties(Enumeration.RELEASE_ACTION_URI);
		enu.setRelease(enumerationContext.toString());

		final Management mgmt = setManagementProperties(enu);

	       // Add any user defined options to the header
		addOptionSetHeader(mgmt);
		
		log.info("REQUEST:\n" + mgmt + "\n");
		final Addressing response = HttpClient.sendRequest(mgmt);

		// Check for fault during message generation
		if (response.getBody().hasFault()) {
			SOAPFault fault = response.getBody().getFault();
			throw new FaultException(fault.getFaultString());
		}
		log.info("RESPONSE:\n" + response + "\n");
	}

	/**
	 * Releases a context for enumeration.
	 * 
	 * @param enumerationContext
	 *            Nameof the context to release
	 * @throws DatatypeConfigurationException
	 * @throws FaultException
	 * @throws IOException
	 * @throws JAXBException
	 * @throws SOAPException
	 */
	public void renew(EnumerationCtx enumerationContext) throws SOAPException,
			JAXBException, IOException, FaultException,
			DatatypeConfigurationException {

		// Now generate the request for an EnumCtxId with parameters passed in
		final Enumeration enu =setEnumerationProperties(Enumeration.RENEW_ACTION_URI);
		enu.setRelease(enumerationContext);

		final Management mgmt = setManagementProperties(enu);

	       // Add any user defined options to the header
		addOptionSetHeader(mgmt);
		
		final Addressing response = HttpClient.sendRequest(mgmt);

		// Check for fault during message generation
		if (response.getBody().hasFault()) {
			SOAPFault fault = response.getBody().getFault();
			throw new FaultException(fault.getFaultString());
		}
	}


	/**
	 * @return Returns the reqResList.
	 */
	public ArrayList getReqResList() {
		return reqResList;
	}

	public long getItemCount() {
		return itemCount;
	}

	public List<EnumerationItem> getEnumItems() {
		return enumItems;
	}
	
	/**
	 * @return Returns the destination.
	 */
//	public String getDestination() {
//		return destination;
//	}

	/**
	 * @param destination
	 *            The destination to set.
	 */
//	public void setDestination(String destination) {
//		this.destination = destination;
//	}

	/**
	 * @return Returns the resourceURI.
	 */
//	public String getResourceUri() {
//		return resourceURI;
//	}
//
	/**
	 * @param resourceURI
	 *            The resourceURI to set.
	 */
//	public void setResourceUri(String resourceURI) {
//		this.resourceURI = resourceURI;
//	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.ws.management.client.impl.ResourceImpl#getMessageTimeout()
	 */
	// @Override
//	public long getMessageTimeout() {
//		return this.messageTimeout;
//	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.ws.management.client.impl.ResourceImpl#getSelectorSet()
	 */
	// @Override
//	public SelectorSetType getSelectorSet() {
//		return this.selectorSet;
//	}

}
