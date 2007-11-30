package com.sun.ws.management.transfer;

import java.util.Map;
import java.util.Set;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.Duration;
import javax.xml.soap.SOAPException;

import org.dmtf.schemas.wbem.wsman._1.wsman.DialectableMixedDataType;
import org.dmtf.schemas.wbem.wsman._1.wsman.OptionType;
import org.dmtf.schemas.wbem.wsman._1.wsman.SelectorType;

import com.sun.ws.management.Management;
import com.sun.ws.management.ManagementMessageValues;
import com.sun.ws.management.addressing.Addressing;


/**This class is meant to be a container for constants
 * that are used or applied to an Transfer message.
 * No complicated methods or logic should be present.
 * 
 * See following for original settings as ManagemetMessageConstant
 * values apply.
 * {@link ManagementMessageValues}
 *  
 * @author Nancy Beers
 */
public class TransferMessageValues extends ManagementMessageValues {

	
	public TransferMessageValues() throws SOAPException {
		super();
		// TODO Auto-generated constructor stub
	}

	//In event of failure and to initialize these values are defined
	public static final String DEFAULT_UID_SCHEME=ManagementMessageValues.DEFAULT_UID_SCHEME;
	public static long DEFAULT_TIMEOUT_VALUE =ManagementMessageValues.DEFAULT_TIMEOUT;
	public static final String DEFAULT_TRANSFER_MESSAGE_ACTION_TYPE = Transfer.GET_ACTION_URI;
	public static final String DEFAULT_FRAGMENT = "";
	public static final String DEFAULT_FRAGMENT_DIALECT = com.sun.ws.management.xml.XPath.NS_URI;
	public static final String DEFAULT_REPLY_TO = Addressing.ANONYMOUS_ENDPOINT_URI;
	public static final String[] DEFAULT_CUSTOM_XML_BINDINGS = {};
	public static final Map<String, String> DEFAULT_NS_MAP = null;
	public static final Set<OptionType> DEFAULT_OPTION_SET = null;
	private static DatatypeFactory factory = null;
	{//static initialization block
	  try{
		 factory = DatatypeFactory.newInstance();
	  }catch(Exception ex){
		 //eat the exception and move on. This really shouldn't fail. Is fundamental failure   
	  }
	}
	
	//Default property values.
	private String uidScheme = DEFAULT_UID_SCHEME;
	private long defaultTimeout =DEFAULT_TIMEOUT_VALUE;
	private String transferMessageActionType =
		DEFAULT_TRANSFER_MESSAGE_ACTION_TYPE;
	private String fragment = DEFAULT_FRAGMENT;
	private String fragmentDialect = DEFAULT_FRAGMENT_DIALECT;
	private String replyTo = DEFAULT_REPLY_TO;
	private String[] customXmlBindingPackageList =DEFAULT_CUSTOM_XML_BINDINGS;
	private Map<String, String> namespaceMap = DEFAULT_NS_MAP;

	private Set<OptionType> optionSet = DEFAULT_OPTION_SET;
	
	/**The following static factory method should be used to 
	 * retrieve a default instance of teh TransferMessageValues
	 * class with all of the associated defaults applied.
	 * 
	 * NOTE: You should explicitly set the Action for the message as 
	 * it defaults to Transfer.GET_ACTION_URI.
	 * 
	 * @return TransferMessageValues instance with defaults set.
	 * @throws SOAPException 
	 */
	public static TransferMessageValues newInstance() throws SOAPException {
		return new TransferMessageValues();
	}

	public static Duration newDuration(long duration) throws DatatypeConfigurationException{
		Duration value = null;
		if(factory==null){
		  factory = DatatypeFactory.newInstance();
		}
		value = factory.newDuration(duration);
		return value;
	}
	
	public static DialectableMixedDataType newFilter(String expression,
			String dialect) {
      final DialectableMixedDataType filter;
      if ((expression != null) && (expression.length() > 0)) {
          filter = Management.FACTORY.createDialectableMixedDataType();
          if ((dialect != null) && (dialect.length() > 0))
              filter.setDialect(dialect);
          filter.getContent().add(expression);
      } else {
      	filter = null;
      }
      return filter;
	}
	
	//############# GETTERS/SETTER for the default field values
	/**
	 * @return the defaultTimeout
	 */
	public long getDefaultTimeout() {
		return defaultTimeout;
	}

	/**
	 * @param defaultTimeout the defaultTimeout to set
	 */
	public void setDefaultTimeout(long defaultTimeout) {
		this.defaultTimeout = defaultTimeout;
	}

	/**
	 * @return the transferMessageActionType
	 */
	public String getTransferMessageActionType() {
		return transferMessageActionType;
	}

	/**
	 * @param transferMessageActionType the transferMessageActionType to set
	 */
	public void setTransferMessageActionType(String transferMessageType) {
		this.transferMessageActionType = transferMessageType;
	}

	/**
	 * @return the uidScheme
	 */
	public String getUidScheme() {
		return uidScheme;
	}

	/**
	 * @param uidScheme the uidScheme to set
	 */
	public void setUidScheme(String uidScheme) {
		this.uidScheme = uidScheme;
	}


	/**
	 * @return the filter
	 */
	public String getFragment() {
		return fragment;
	}


	/**
	 * @param filter the filter to set
	 */
	public void setFragment(String fragment) {
		this.fragment = fragment;
	}


	/**
	 * @return the filterDialect
	 */
	public String getFragmentDialect() {
		return fragmentDialect;
	}


	/**
	 * @param fragmentDialect the fragmentDialect to set
	 */
	public void setFragmentDialect(String fragmentDialect) {
		this.fragmentDialect = fragmentDialect;
	}



	/**
	 * @return the replyTo
	 */
	public String getReplyTo() {
		return replyTo;
	}


	/**
	 * @param replyTo the replyTo to set
	 */
	public void setReplyTo(String replyTo) {
		this.replyTo = replyTo;
	}


	/**
	 * @return the customXmlBindingPackageList
	 */
	public String[] getCustomXmlBindingPackageList() {
		return customXmlBindingPackageList;
	}

	/**
	 * @param customXmlBindingPackageList the customXmlBindingPackageList to set
	 */
	public void setCustomPackageList(String[] customPackageList) {
		this.customXmlBindingPackageList = customPackageList;
	}


	public Map<String, String> getNamespaceMap() {
		return namespaceMap;
	}

	public void setNamespaceMap(Map<String, String> map) {
		this.namespaceMap = map;
	}

	public Set<OptionType> getOptionSet() {
		return optionSet;
	}

	public void setOptionSet(Set<OptionType> optionSet) {
		this.optionSet = optionSet;
	}
	


}