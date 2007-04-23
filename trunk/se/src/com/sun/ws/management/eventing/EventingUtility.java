package com.sun.ws.management.eventing;

import java.util.UUID;
import java.util.logging.Logger;

import javax.xml.bind.JAXBException;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.soap.SOAPException;

import org.xmlsoap.schemas.ws._2004._08.eventing.FilterType;

import com.sun.ws.management.Management;
import com.sun.ws.management.ManagementUtility;
import com.sun.ws.management.enumeration.Enumeration;
import com.sun.ws.management.enumeration.EnumerationMessageValues;
import com.sun.ws.management.enumeration.EnumerationUtility;

/** This class is meant to provide general utility functionality for
 *  Eventing message instances and all of their related extensions.
 *  To enable messages ready for submission, Management instances are 
 *  returned from some of the methods below. 
 * 
 *  The following default values are defined when newer values have not 
 *  been supplied:
 *  	UID_SCHEME = uuid:
 *  	DEFAULT_TIMEOUT = 30000
 *      ACTION = Enumeration.ENUMERATE_ACTION_URI 
 *  	FILTER_TYPE.DIALECT = XPATH
 *  	REPLY_TO = Anonymous URI
 *  
 * @author Nancy Beers
 */
public class EventingUtility {
	
	private static final Logger LOG = 
		Logger.getLogger(EnumerationUtility.class.getName());
	
	public static Eventing buildMessage(Eventing existingEvent,
			EventingMessageValues settings) 
		throws SOAPException, JAXBException, DatatypeConfigurationException{
		
		if(existingEvent == null){//build default instances
			Management mgmt = ManagementUtility.buildMessage(null, settings);
		   existingEvent = new Eventing(mgmt);
		}
		if(settings ==null){//grab a default instance if 
			settings = EventingMessageValues.newInstance();
		}
		
	    //Process the EventingMessageValues instance passed in.
		 //Processing endTo for the message
		
		if (settings.getEventingMessageActionType() == Eventing.SUBSCRIBE_ACTION_URI) {
			if (settings.getFilter() == null || settings.getFilter().length() <= 0) {
				existingEvent.setSubscribe(settings.getEndTo(), settings.getDeliveryMode(), settings.getNotifyTo(), 
					   settings.getExpires(), null);
			} else {
				final FilterType filter = Eventing.FACTORY.createFilterType();
		        filter.setDialect(settings.getFilterDialect());
		        filter.getContent().add(settings.getFilter());
				existingEvent.setSubscribe(settings.getEndTo(), settings.getDeliveryMode(), settings.getNotifyTo(), 
						   settings.getExpires(), filter);
				
			}
		} else if (settings.getEventingMessageActionType() == Eventing.SUBSCRIBE_RESPONSE_URI) {
			existingEvent.setSubscribeResponse(settings.getEndTo(), settings.getExpires());
		} else if (settings.getEventingMessageActionType() == Eventing.RENEW_ACTION_URI) {
			existingEvent.setRenew(settings.getExpires());
		} else if (settings.getEventingMessageActionType() == Eventing.RENEW_RESPONSE_URI) {
			existingEvent.setRenewResponse(settings.getExpires());
		} else if (settings.getEventingMessageActionType() == Eventing.GET_STATUS_ACTION_URI) {
			existingEvent.setGetStatus();
		} else if (settings.getEventingMessageActionType() == Eventing.GET_STATUS_RESPONSE_URI) {
			existingEvent.setGetStatusResponse(settings.getExpires());
		} else if (settings.getEventingMessageActionType() == Eventing.UNSUBSCRIBE_ACTION_URI) {
			existingEvent.setUnsubscribe();
		} else if (settings.getEventingMessageActionType() == Eventing.SUBSCRIPTION_END_ACTION_URI) {
			existingEvent.setSubscriptionEnd(settings.getEndTo(), settings.getStatus(), settings.getReason());
		}
		
		
		 //Processing MessageId component
		if((settings.getUidScheme()!=null)&&
				(settings.getUidScheme().trim().length()>0)){
			existingEvent.setMessageId(settings.getUidScheme() +
				   UUID.randomUUID().toString());
		}else{
			existingEvent.setMessageId(EnumerationMessageValues.DEFAULT_UID_SCHEME +
			  UUID.randomUUID().toString());
		}
		
		if (settings.getEventingMessageActionType() != null &&
				settings.getEventingMessageActionType().trim().length() > 0){
			existingEvent.setAction(settings.getEventingMessageActionType());
		} else {
			existingEvent.setAction(Eventing.SUBSCRIBE_ACTION_URI);
		}
		
		if (settings.getNamespaceMap() != null &&
				settings.getNamespaceMap().size() > 0){
			existingEvent.addNamespaceDeclarations(settings.getNamespaceMap());
		} 
		
		return existingEvent;
	}
	

}
