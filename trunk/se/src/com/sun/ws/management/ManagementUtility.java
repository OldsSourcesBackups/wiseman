package com.sun.ws.management;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;

import org.dmtf.schemas.wbem.wsman._1.wsman.MaxEnvelopeSizeType;
import org.dmtf.schemas.wbem.wsman._1.wsman.SelectorSetType;
import org.dmtf.schemas.wbem.wsman._1.wsman.SelectorType;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xmlsoap.schemas.ws._2004._08.addressing.AttributedURI;
import org.xmlsoap.schemas.ws._2004._08.addressing.EndpointReferenceType;
import org.xmlsoap.schemas.ws._2004._08.addressing.ReferenceParametersType;
import org.xmlsoap.schemas.ws._2004._08.addressing.ReferencePropertiesType;
import org.xmlsoap.schemas.ws._2004._09.mex.Metadata;
import org.xmlsoap.schemas.ws._2004._09.mex.MetadataSection;

import com.sun.ws.management.addressing.Addressing;
import com.sun.ws.management.enumeration.EnumerationMessageValues;
import com.sun.ws.management.soap.SOAP;
import com.sun.ws.management.transfer.Transfer;
import com.sun.ws.management.xml.XmlBinding;
import com.sun.xml.fastinfoset.sax.Properties;

/** This class is meant to provide general utility functionality for
 *  Management instances and all of their related extensions.
 * 
 * @author Simeon
 */
public class ManagementUtility {
	
	//These values are final and static so that they can be uniformly used by many classes  
	private static final Logger LOG = Logger.getLogger(ManagementUtility.class.getName());
	private static final org.xmlsoap.schemas.ws._2004._08.addressing.ObjectFactory
	   addressing_factory = new org.xmlsoap.schemas.ws._2004._08.addressing.ObjectFactory();
	private static final String uidScheme ="uuid:";
	private static final long defaultTimeout =30000;
	private static Management defautInst = null;
	private static XmlBinding binding = null;
	{
		try{
		  defautInst = new Management();
		  binding = defautInst.getXmlBinding();
		}catch(Exception ex){
			//eat exception and move on.
		}
	}
	/** Takes an existing SelectorSetType container and a Map<String,String> where
	 *  Key,Value or Name,Value have been supplied are accepted as parameters.  
	 *  A SelectorSetType instance includind the Map values provided are returned.
	 * 
	 * @return SelectorSetType instance.
	 */
	public static SelectorSetType populateSelectorSetType(Map<String,String> selectors,
			SelectorSetType selectorContainer){
			if(selectorContainer==null){
				selectorContainer = new SelectorSetType();
			}
			//Now populate the selectorSetType
		    List<SelectorType> selectorList = selectorContainer.getSelector();
		    
		    // Add a selector to the list
		    for (String key : selectors.keySet()) {
		        SelectorType nameSelector = new SelectorType();
		        nameSelector.setName(key);        
		        nameSelector.getContent().add(selectors.get(key));        
		        selectorList.add(nameSelector);			
			}
	    return selectorContainer;
	}
	
	/**The method takes a SelectorSetType instance and returns the Selectors defined
	 * in a Map<String,String> instance, with Key,Value being the values respectively. 
	 * 
	 * @param selectorContainer
	 * @return Map<String,String> being Selector values
	 */
	public static Map<String,String> extractSelectorsAsMap(SelectorSetType selectorContainer){
		//Create the Map instance to be returned
		Map<String,String> map = new HashMap<String, String>();
		List<SelectorType> selectorsList = null;
		
		//populate the Map with the selectorContainer contents
		if(selectorContainer!=null){
		  selectorsList=selectorContainer.getSelector();
		  map =extractSelectorsAsMap(map, selectorsList);
		}
		
		return map;
	}

	/**The method takes a List<SelectorType> instance and returns the Selectors defined
	 * in a Map<String,String> instance, with Key,Value being the values respectively. 
	 * 
	 * @param map
	 * @param selectorsList
	 */
	public static Map<String,String> extractSelectorsAsMap(Map<String, String> map, 
			List<SelectorType> selectorsList) {
		if(map==null){
			map = new HashMap<String, String>();
		}
		if(selectorsList!=null){
			for (Iterator iter = selectorsList.iterator(); iter.hasNext();) {
				SelectorType element = (SelectorType) iter.next();
				if((element.getName()!=null)
				 &&(element.getContent()!=null)
				 &&(((String)element.getContent().get(0))).trim().length()>0){
				  map.put(element.getName(),
					(String) element.getContent().get(0));
				}
			}
		}
		return map;
	}

	/** Parses the header list to locate the SOAPElement identified by the QName 
	 * passed in.
	 * 
	 * @param headers
	 * @param qualifiedName
	 * @return
	 */
	public static SOAPElement locateHeader(SOAPElement[] headers, QName qualifiedName) {
		SOAPElement located = null;
		if((headers==null)||(qualifiedName == null)){
			return located;
		}else{
			for (int i = 0; i < headers.length; i++) {
				SOAPElement header = headers[i];
				if(qualifiedName.getLocalPart().equals(header.getElementQName().getLocalPart())&&
				   qualifiedName.getNamespaceURI().equals(header.getElementQName().getNamespaceURI())){
					return header;
				}
			}
		}
		return located;
	}
	
	/**Attempts to build a message from the addressing instance passed in and with
	 * the ManagementMessageValues passed in.  Only if the values has not already
	 * been set in the Addressing instance will the values from the constants be
	 * used.
	 * 
	 * @param instance
	 * @param settings
	 * @return
	 * @throws SOAPException
	 * @throws JAXBException 
	 * @throws DatatypeConfigurationException 
	 */
	public static Management buildMessage(Addressing instance,ManagementMessageValues settings) 
		throws SOAPException, JAXBException, DatatypeConfigurationException {
		//return reference
		Management message = null;
		//initialize if not already
		if(instance == null){
			message = new Management();
		}else{//else use Addressing instance passed in.
			message = new Management(instance);
		}
		//initialize if not already
		if(settings == null){
			settings = new ManagementMessageValues();
		}
	    //Now process the settings values passed in.
		//Processing the To value
		if((message.getTo()==null)||(message.getTo().trim().length()==0)){
			//if defaults set then use them otherwise don't
			if((settings.getTo()!=null)&&(settings.getTo().trim().length()>0)){
				message.setTo(settings.getTo());
			}
		}
		
		  //Processing the ResourceURI value
		if((message.getResourceURI()==null)||
				(message.getResourceURI().trim().length()==0)){
			//if defaults set then use them otherwise don't
			if((settings.getResourceUri()!=null)&&
					(settings.getResourceUri().trim().length()>0)){
				message.setResourceURI(settings.getResourceUri());
			}
		}
		  //Processing for xmlBinding
		if(message.getXmlBinding()==null){
		   if(settings.getXmlBinding()!=null){
			  message.setXmlBinding(settings.getXmlBinding()); 
		   }else{ //otherwise use/create default one for Managemetn class
			  if(defautInst!=null){
				 message.setXmlBinding(defautInst.getXmlBinding());  
			  }else{
			     message.setXmlBinding(new Management().getXmlBinding());
			  }
		   }
		}
		
		 //Processing ReplyTo
		if((settings.getReplyTo()!=null)&&
		    settings.getReplyTo().trim().length()>0){
			message.setReplyTo(settings.getReplyTo());
		}else{
			message.setReplyTo(Addressing.ANONYMOUS_ENDPOINT_URI);
		}
		
		 //Processing MessageId component
		if((settings.getUidScheme()!=null)&&
				(settings.getUidScheme().trim().length()>0)){
		   message.setMessageId(settings.getUidScheme() +
				   UUID.randomUUID().toString());
		}else{
			message.setMessageId(EnumerationMessageValues.DEFAULT_UID_SCHEME +
			  UUID.randomUUID().toString());
		}

		
		//Add processing for timeout
		final DatatypeFactory factory = DatatypeFactory.newInstance();
		if(settings.getTimeout()>ManagementMessageValues.DEFAULT_TIMEOUT){
			message.setTimeout(
			factory.newDuration(
					settings.getTimeout()));
		}
		
        //process the selectors passed in.
        if((settings.getSelectorSet()!=null)&&(settings.getSelectorSet().size()>0)){
        	message.setSelectors(settings.getSelectorSet());
        }
        
		 //Processing MaxEnvelopeSize
		if((settings.getMaxEnvelopeSize()!=null)&&
		    settings.getMaxEnvelopeSize().longValue() >0){
	        final MaxEnvelopeSizeType maxEnvSize = Management.FACTORY.createMaxEnvelopeSizeType();
	        maxEnvSize.setValue(settings.getMaxEnvelopeSize());
	        maxEnvSize.getOtherAttributes().put(SOAP.MUST_UNDERSTAND, SOAP.TRUE);
	        message.setMaxEnvelopeSize(maxEnvSize);
		}
        
		 //Processing Locale
		if(settings.getLocale()!=null){
			message.setLocale(settings.getLocale());
		}
		
		//Add processing for other Management components
		if((settings.getAdditionalHeaders()!=null)&&
			(settings.getAdditionalHeaders().size()>0)){
			for (Iterator iter = settings.getAdditionalHeaders().iterator(); iter.hasNext();) {
				ReferenceParametersType element = (ReferenceParametersType) iter.next();
				message.addHeaders(element);
			}
		}

        //process the options passed in.
        if((settings.getOptionSet()!=null)&&(settings.getOptionSet().size()>0)){
        	message.setOptions(settings.getOptionSet());
        }

		return message;
	}
	
	public static Management buildMessage(Management existing, 
			Addressing subMessage,boolean trimAdditionalMetadata) throws SOAPException, JAXBException, 
			DatatypeConfigurationException{
		 //return reference
		 Management message = null;
		 //initialize if not already
		 if(subMessage == null){
			message = new Management();
		 }else{//else use Addressing instance passed in.
			message = new Management(subMessage);
		 }
		 //Populate the new message instance with the values
		 if((existing!=null)&&(existing.getHeaders()!=null)){
			 for(SOAPElement header: existing.getHeaders()){
//				//Don't add the original Action header
//				QName examine = null;
//				if(((examine =header.getElementQName())!=null)&&
//				   examine.getLocalPart().equals(Management.ACTION.getLocalPart())){
//					//Bail out and do not add.
//				   continue;	
//				}
				if(trimAdditionalMetadata){
//				  if(!AnnotationProcessor.isDescriptiveMetadataElement(
//						  header.getElementQName())){
//				     Node located = containsHeader(message.getHeader(),header);	
//					 if(located!=null){
//					   message.getHeader().removeChild(located);  
//					 }
//					 message.getHeader().addChildElement(header);
//				  }
				}else{
//					message.getHeader().addChildElement(header);
				  Node located = containsHeader(message.getHeader(),header);	
				  if(located!=null){
					 message.getHeader().removeChild(located);  
				  }
				  message.getHeader().addChildElement(header);
				}
			}
		 }
		 message = buildMessage(message, ManagementMessageValues.newInstance());
		return message;
	}
	
	/** Attempts to extract Selectors returned from a Management instance including
	 * a CreateResponse type, as a Map<String,String> for convenience. 
	 * 
	 * @param managementMessage
	 * @return
	 * @throws SOAPException
	 * @throws JAXBException
	 */
	public static Map<String, String> extractSelectors(Management 
			managementMessage) throws SOAPException, JAXBException {
		//stores located selectors
		Map<String,String> selectors = new HashMap<String,String>();
		
		//parse the Management instance passed in for ResourceCreated and 
		//  embedded selectors
		if(managementMessage!=null){
			
		 if((managementMessage.getBody()!=null)&&
			(managementMessage.getBody().getFirstChild()!=null)){
			 //Extract dom component
			Node createContent = managementMessage.getBody().getFirstChild();
			JAXBElement<EndpointReferenceType> unmarshal = 
				(JAXBElement<EndpointReferenceType>) 
				binding.unmarshal(createContent);
			EndpointReferenceType crtType = 
				(EndpointReferenceType) unmarshal.getValue();

			if(crtType!=null){
				//extract the CreateResponseType instance
				EndpointReferenceType resCreatedElement = crtType;
			    if((resCreatedElement!=null)&&(resCreatedElement.getReferenceParameters()!=null)&&
			    	(resCreatedElement.getReferenceParameters().getAny()!=null)){
			      List<Object> refContents = 
			    	  resCreatedElement.getReferenceParameters().getAny();
			      if((refContents!=null)&&(refContents.size()>0)){
			    	  for(Object node: refContents){
						JAXBElement eprElement = (JAXBElement) node;
					  //locate the refParameter element that is the selectorSet	
					  if(eprElement.getName().getLocalPart().equals(
							Management.SELECTOR_SET.getLocalPart())){
						Document nod = Management.newDocument();							
							binding.marshal(node, nod );

						JAXBElement<SelectorSetType> selSet = 
							(JAXBElement<SelectorSetType>) binding
										.unmarshal(nod);
						SelectorSetType sels = (SelectorSetType) selSet.getValue();
			    		if(sels!=null){
					      //extract the SelectorSet contents
					     selectors= ManagementUtility.extractSelectorsAsMap(
					    		 selectors,sels.getSelector());	
			    		}
			    	  }//end of if
			    	}
			      }
			    }
			  }
			}
		}
		
		return selectors;
	}
    
	/** Convenience method to locate a specific SOAPElement from within the SOAPHeader
	 * instance.
	 * 
	 * @param header
	 * @param element
	 * @return
	 */
	private static Node containsHeader(SOAPHeader header, SOAPElement element) {
		Node located = null;
		  NodeList chNodes = header.getChildNodes();
		  QName elementNode = element.getElementQName();
		  for (int i = 0; i < header.getChildNodes().getLength(); i++) {
			 Node elem = chNodes.item(i);
			 if((elem.getLocalName().equals(elementNode.getLocalPart()))&&
				(elem.getNamespaceURI().equals(elementNode.getNamespaceURI()))){
				located = elem; 
			 }
		  }
		return located;
	}

	/**Attempts to extract the addressing components from a Management message
	 * as an EPR type.  
	 * 
	 * @param managementMesg
	 * @return
	 * @throws JAXBException
	 * @throws SOAPException
	 */
	public static EndpointReferenceType extractEprType(Management managementMesg) 
	throws JAXBException, SOAPException {
	  EndpointReferenceType epr = null;
	  epr = addressing_factory.createEndpointReferenceType();
	   AttributedURI to = addressing_factory.createAttributedURI();
	   to.setValue(managementMesg.getTo());
	  epr.setAddress(to);
	    //######## REF PARAMETERS		  
	    ReferenceParametersType refParams = 
		  addressing_factory.createReferenceParametersType();
	    //add the resourceUri
	     SOAPElement resourceURI = ManagementUtility.locateHeader(
	    		managementMesg.getHeaders(), Management.RESOURCE_URI);
	     if((resourceURI!=null)&&
	    		(resourceURI.getTextContent().trim().length()>0)){
	    	refParams.getAny().add(resourceURI);
	     }
	    //add the SelectorSet if defined
	     SOAPElement selectorSet = ManagementUtility.locateHeader(
	    		  managementMesg.getHeaders(), Management.SELECTOR_SET);
	     if((selectorSet!=null)&&
	    		 (selectorSet.hasChildNodes())){
	        refParams.getAny().add(selectorSet);
	     }
	     //TODO: add for OptionSet processing.
	    //######## REF PROPERTIES
	    ReferencePropertiesType refProps = 
	    	addressing_factory.createReferencePropertiesType();
	    boolean hasReferenceProperties = false;
	  //Populate the Reference* just populated.
	  if((refParams.getAny()!=null)&&(refParams.getAny().size()>0)){  
	   epr.setReferenceParameters(refParams);
	  }
	  if((refProps.getAny()!=null)&&(refProps.getAny().size()>0)){
	    epr.setReferenceProperties(refProps);
	  }
	 return epr;
	}
	
	
	//###################### GETTERS/SETTERS for instance 
    /* Exposes the default uid scheme for the ManagementUtility instance.
     * 
     */
	public static String getUidScheme() {
		return uidScheme;
	}

	/**
	 * @return the defaultTimeout
	 */
	public static long getDefaultTimeout() {
		return defaultTimeout;
	}
	
}
