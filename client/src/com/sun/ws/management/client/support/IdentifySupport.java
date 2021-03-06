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
 ** 
 *
 * $Id: IdentifySupport.java,v 1.3 2007-05-30 20:30:29 nbeers Exp $
 */
package com.sun.ws.management.client.support;

import java.util.logging.Logger;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFault;
import javax.xml.soap.SOAPMessage;

import org.dmtf.schemas.wbem.wsman.identity._1.wsmanidentity.IdentifyResponseType;
import org.dmtf.schemas.wbem.wsman.identity._1.wsmanidentity.IdentifyType;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.sun.ws.management.Management;
import com.sun.ws.management.addressing.Addressing;
import com.sun.ws.management.client.ResourceState;
import com.sun.ws.management.client.exceptions.FaultException;
import com.sun.ws.management.client.impl.ResourceStateImpl;
import com.sun.ws.management.client.impl.TransferableResourceImpl;
import com.sun.ws.management.identify.Identify;
import com.sun.ws.management.transport.HttpClient;
import com.sun.ws.management.xml.XmlBinding;

/**
 * Provides simple message support classes for Identify functionality.
 * 
 * @author spinder
 */
public class IdentifySupport {
	
	//Factory for JAXB Identify operations.
    private static org.dmtf.schemas.wbem.wsman.identity._1.wsmanidentity.ObjectFactory identifyFactory 
				= new org.dmtf.schemas.wbem.wsman.identity._1.wsmanidentity.ObjectFactory();
    
    //JAXB entity for binding operations. Only needs to know about following package.
    private static String[] bindPackageList = {"org.dmtf.schemas.wbem.wsman.identity._1.wsmanidentity"};
    private static XmlBinding binding = null;
    //static initialization of bindings
    static{ 
      try {
		binding = new XmlBinding(null,IdentifySupport.bindPackageList);
	  } catch (JAXBException e) {
		e.printStackTrace();
	  }
    }
    
	/**Returns the IdentifyResponse object as a familiar ResourceState instance complete with XPath processing.
	 * @param identifyResponse
	 * @return ResourceState enclosing response from Identify request.
	 * @throws FaultException
	 * @throws JAXBException
	 */
	public static ResourceState retrieveIdentifyResponse(final Addressing identifyResponse) throws FaultException, JAXBException {
		//Convert response to familiar JAXB types.
		IdentifyResponseType retrievedIdentify = null;
		//Check for fault in message passed in.
		if (identifyResponse.getBody().hasFault()) {
			SOAPFault fault = identifyResponse.getBody().getFault();
			throw new FaultException(fault);
		}else{
			Node identifyChildNode = null;
			identifyChildNode = identifyResponse.getBody().getFirstChild();
			if(identifyChildNode == null){
				throw new IllegalArgumentException("Not able to retrieve child element.");
			}
			JAXBElement<IdentifyResponseType> ob = (JAXBElement<IdentifyResponseType>)binding.unmarshal(identifyChildNode);
			retrievedIdentify = (IdentifyResponseType)ob.getValue();
		}
		//Now Create the familiar ResourceState document
        Document identifyRespDocument = Management.newDocument();
        JAXBElement<IdentifyResponseType> identElement = 
        	identifyFactory.createIdentifyResponse(retrievedIdentify);        
		binding.marshal(identElement, identifyRespDocument);
		//Construct the ResourceState instance.
		ResourceState identResp = new ResourceStateImpl(identifyRespDocument);
		
//        Identify id;
//		try {
//			id = new Identify(identifyResponse);
//			final SOAPElement idr = id.getIdentifyResponse();
//			identResp = new ResourceStateImpl(idr.getOwnerDocument());	
//		} catch (SOAPException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		return identResp;
	}

	/** Creates a simple SOAPMessage instance with only the essentials. The returned SOAPMessage instance can then be
	 *  directed to relevant servers.  
	 * @return SOAPMessage instance for identifyRequest with no other typical headers set as per spec recommendations.
	 * @throws SOAPException
	 * @throws JAXBException
	 */
	public static SOAPMessage buildIdentifyRequest() throws SOAPException, JAXBException {
	   final Identify identify = new Identify();
	   identify.setIdentify();
	   
		return identify.getMessage();
	}
	
}
