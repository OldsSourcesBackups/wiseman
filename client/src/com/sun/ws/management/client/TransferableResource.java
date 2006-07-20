package com.sun.ws.management.client;

import java.io.IOException;

import javax.xml.bind.JAXBException;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.soap.SOAPException;

import org.w3c.dom.Document;

import com.sun.ws.management.AccessDeniedFault;
import com.sun.ws.management.client.exceptions.FaultException;

/**
 * An abstract representation of a WSManagement resource that focuses on 
 * those operation of WS-Transfer. 
 * 
 * @author wire
 * @author spinder
 * 
 */
public interface TransferableResource {

	/** Generates a DELETE request over WS-Man protocol for this resource.
	 * 
	 * @throws SOAPException
	 * @throws JAXBException
	 * @throws IOException
	 * @throws FaultException
	 * @throws DatatypeConfigurationException
	 * @throws AccessDeniedFault
	 */
	public abstract void delete() throws SOAPException, JAXBException,
			IOException, FaultException, DatatypeConfigurationException;

	/** Generates a fragment DELETE request over WS-Man protocol.
	 * @param fragmentRequest 
	 * @param fragmentDialect
	 * @throws SOAPException
	 * @throws JAXBException
	 * @throws IOException
	 * @throws FaultException
	 * @throws DatatypeConfigurationException
	 * @throws AccessDeniedFault
	 */
	public abstract void delete(String fragmentRequest, String fragmentDialect)
			throws SOAPException, JAXBException, IOException, FaultException,
			DatatypeConfigurationException, AccessDeniedFault;
   

	/** Generates a PUT request over WS-Man protocol with contents of Document 
	 *  passed in.
	 * @param content 
	 * @throws SOAPException
	 * @throws JAXBException
	 * @throws IOException
	 * @throws FaultException
	 * @throws DatatypeConfigurationException
	 */
	public abstract void put(Document content) throws SOAPException,
			JAXBException, IOException, FaultException,
			DatatypeConfigurationException;

	/** Generates a fragment PUT request over WS-Man protocol with the fragment
	 *  for update defined by fragmentExpression, using the fragmentDialect to be 
	 *  updated with the contents of the Document passed in,.
	 * @param content 
	 * @param fragmentExpression 
	 * @param fragmentDialect 
	 * @throws SOAPException
	 * @throws JAXBException
	 * @throws IOException
	 * @throws FaultException
	 * @throws DatatypeConfigurationException
	 */
	public abstract void put(Document content, String fragmentExpression,
			String fragmentDialect) throws SOAPException, JAXBException,
			IOException, FaultException, DatatypeConfigurationException;

	/** Generates a PUT request over WS-Man protocol with contents of ResourceState 
	 *  passed in.
	 * @param content
	 * @throws SOAPException
	 * @throws JAXBException
	 * @throws IOException
	 * @throws FaultException
	 * @throws DatatypeConfigurationException
	 */
	public abstract void put(ResourceState newState) throws SOAPException,
			JAXBException, IOException, FaultException,
			DatatypeConfigurationException;

	
	/** Generates a WS-Man GET message and returns the contents of the Resource
	 *  as a ResoruceState instance.
	 * @return Resource contents as a ResourceState object.
	 * @throws SOAPException
	 * @throws JAXBException
	 * @throws IOException
	 * @throws FaultException
	 * @throws DatatypeConfigurationException
	 */
	public abstract ResourceState get() throws SOAPException, JAXBException,
			IOException, FaultException, DatatypeConfigurationException;

	/** Generates a WS-Man fragment GET message with fragmentExpression defining
	 *  content to operate on in the agreed upon dialect and returns the contents 
	 *  of the Resource as a ResoruceState instance.
	 * @return Resource contents as a ResourceState object.
	 * @throws SOAPException
	 * @throws JAXBException
	 * @throws IOException
	 * @throws FaultException
	 * @throws DatatypeConfigurationException
	 */
	public abstract ResourceState get(String fragmentExpression, String dialect)
			throws SOAPException, JAXBException, IOException, FaultException,
			DatatypeConfigurationException;

	public abstract String getResourceUri();

	public abstract String getDestination();

	public abstract long getMessageTimeout();

}