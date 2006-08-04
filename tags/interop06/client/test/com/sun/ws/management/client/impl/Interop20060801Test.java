package com.sun.ws.management.client.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.bind.JAXBException;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPException;
import javax.xml.xpath.XPathExpressionException;

import util.WsManBaseTestSupport;

import com.sun.ws.management.client.EnumerationCtx;
import com.sun.ws.management.client.Resource;
import com.sun.ws.management.client.ResourceFactory;
import com.sun.ws.management.client.ResourceState;
import com.sun.ws.management.client.ServerIdentity;
import com.sun.ws.management.client.exceptions.FaultException;
import com.sun.ws.management.client.exceptions.NoMatchFoundException;
import com.sun.ws.management.xml.XmlBinding;

/**
 *
 * @author wire
 *
 */
public class Interop20060801Test extends WsManBaseTestSupport {

	private static final QName LOWER_THRESHOLD_NON_CRITICAL = new QName("http://www.dmtf.org/wbem/wscim/1/cim-schema/2/CIM_NumericSensor","LowerThresholdNonCritical");
    private static Logger log = Logger.getLogger(Interop20060801Test.class.getName());
    private static String vaildatedSelectorCreationClassName = "ComputerSystem";
    private static String vaildatedSelectorName = "IPMI Controller 32";
    private static boolean validated=true;
	public Interop20060801Test(){

		Handler[] handlers =
	        Logger.getLogger( "" ).getHandlers();
	      for ( int index = 0; index < handlers.length; index++ ) {
	        handlers[index].setLevel( Level.FINE );
	      }

	     Logger loggerXfer = Logger.getLogger(TransferableResourceImpl.class.getName());
	     loggerXfer.setLevel(Level.FINE);
	     Logger loggerEnum = Logger.getLogger(EnumerationResourceImpl.class.getName());
	     loggerEnum.setLevel(Level.FINE);
	     
		if(System.getProperty("wsman.dest")!=null){
			destination=System.getProperty("wsman.dest");
		}
		if(!validated)
			log.info("Wiseman client test against "+destination);
		try {
			validateSelectors();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.severe(e.getMessage());
		}
	}
	
	/**
	 * Attempt an enumeration on 
	 * @throws DatatypeConfigurationException 
	 * @throws FaultException 
	 * @throws IOException 
	 * @throws JAXBException 
	 * @throws SOAPException 
	 * @throws NoMatchFoundException 
	 * @throws XPathExpressionException 
	 *
	 */
	private void validateSelectors(){
		// Enumerate ComputerSystem and search for a valid value for selectorCreationClassName 
		// and selectorName
		if(validated)
			return;
		log.info("*** An inital enumeration is being performed to verify your selectors.");
		validated=true;
		try {
		HashMap<String, String> selectors = null;
		Resource[] numericSensorEnumSet = ResourceFactory.find(destination, resourceURIComputerSystem, operationTimeout, selectors);
		assertTrue(numericSensorEnumSet.length>0);
		Resource numericSensorEnum = numericSensorEnumSet[0];
		numericSensorEnum.setMaxEnvelopeSize(-1);
		numericSensorEnum.setMessageTimeout(operationTimeout);
		EnumerationCtx contextA = numericSensorEnum.enumerate(null, numericSensorEnum.XPATH_DIALECT, false,false,null);
		ResourceState pullResult = numericSensorEnum.pull(contextA, 10000, 1, -1);
		String ccN=pullResult.getValueText("//*[local-name()=\"CreationClassName\"]");
		String N=pullResult.getValueText("//*[local-name()=\"Name\"]");
		if(ccN!=null){
			vaildatedSelectorCreationClassName=ccN;
		}
		if(N!=null){
			vaildatedSelectorName=N;
		}
		log.info("*** It has been determined that CreationClassName="+ccN+" and Name="+N+" is valid on your system.");
		} catch (Throwable e){
			log.severe(e.getMessage());
		}
		log.info("Validate Selectors has completed.");
		
	}
	public static String destination = "http://localhost:8080/wsman/";

	public static String resourceUri = "wsman:auth/user";

	public static long timeoutInMilliseconds = 9400000;

	XmlBinding binding = null;

	// Section 6 Variables
	private String resourceURIComputerSystem="http://schemas.dmtf.org/wbem/wscim/1/cim-schema/2/CIM_ComputerSystem";
	private int maxEnvelopeSize;
	private String selectorCreationClassName;
	private String selectorName;
	private int operationTimeout;
	
	// Section 7 Variables
	private String resourceURINumericSensor;

	protected void setUp() throws Exception {
		super.setUp();

		// Initalize section 6 Variables to default values
		resourceURIComputerSystem = "http://schemas.dmtf.org/wbem/wscim/1/cim-schema/2/CIM_ComputerSystem";
		maxEnvelopeSize = 153600;
		selectorCreationClassName = vaildatedSelectorCreationClassName;
		selectorName = vaildatedSelectorName;
		operationTimeout = 60000;

		// Initalize section 7 Variables to default values
		resourceURINumericSensor="http://schemas.dmtf.org/wbem/wscim/1/cim-schema/2/CIM_NumericSensor";
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * 6.1	Identify (mandatory)
	 * Identify is used to retrieve information about the WS-Management stack.
	 * @throws JAXBException 
	 * @throws IOException 
	 * @throws SOAPException 
	 * @throws NoMatchFoundException 
	 * @throws XPathExpressionException 
	 *
	 */
	public void testIdentify() throws SOAPException, IOException, JAXBException, XPathExpressionException, NoMatchFoundException{
		log.info("_________________________________________________________________________");
		log.info("6.1 Identify");
		ServerIdentity serverInfo = ResourceFactory.getIdentity(destination);
		log.info(""+serverInfo);
		assertNotNull(serverInfo);
		assertNotNull(serverInfo.getProductVendor());
		assertNotNull(serverInfo.getProductVersion());
		assertNotNull(serverInfo.getProtocolVersion());
		//assertNotNull(serverInfo.getSpecVersion());
		//assertNotNull(serverInfo.getBuildId());
		assertEquals(serverInfo.getProtocolVersion(),"http://schemas.dmtf.org/wbem/wsman/1/wsman.xsd");
	}
	
	/**
	 * 6.2	GET instance of CIM_ComputerSystem (mandatory)
	 * In this scenario, the client does a GET request to retrieve 
	 * an instance of CIM_ComputerSystem. Since this class has keys that 
	 * need to be specified, the request contains a selector set that 
	 * contains the values for the keys for the instance being retrieved.
	 * @throws DatatypeConfigurationException 
	 * @throws FaultException 
	 * @throws IOException 
	 * @throws JAXBException 
	 * @throws SOAPException 
	 */
	public void testGetInstance() throws SOAPException, JAXBException, IOException, FaultException, DatatypeConfigurationException{
		log.info("_________________________________________________________________________");
		log.info("6.2 Get Instance of CIM_ComputerSystem");
		ResourceState systemState = getComputerSystemState(resourceURIComputerSystem,selectorCreationClassName,selectorName,maxEnvelopeSize,operationTimeout);
		log.info("PASS");

		// TODO Verify Actual Values
		
	}


	/**
	 * 6.3	GET failure with invalid resource URI (mandatory)
	 * In this scenario, the client does a GET with an invalid resource URI 
	 * resulting in the server returning an error response.
	 * @throws DatatypeConfigurationException 
	 * @throws  
	 * @throws IOException 
	 * @throws JAXBException 
	 * @throws SOAPException 
	 */
	public void testGetInstanceBadURI() throws SOAPException, JAXBException, IOException, DatatypeConfigurationException{
		log.info("_________________________________________________________________________");
		log.info("6.3 GET failure with invalid resource URI");
		resourceURIComputerSystem = "http://www.dmtf.org/wbem/wscim/1/cim-schema/2/cim_computersyste";
		try {
			ResourceState systemState = getComputerSystemState(resourceURIComputerSystem,selectorCreationClassName,selectorName,maxEnvelopeSize,operationTimeout);
		} catch (FaultException e) {
			log.info("PASS");
			return;
		}
		fail("This test is expected to Fault and did not.");
	}
	
	/**
	 * 6.4	Get failure with maxenvelopesize exceeded error (mandatory)
	 * In this scenario, the response exceeds the MaxEnvelopeSize configured on the server resulting 
	 * in an error response.
	 * @throws DatatypeConfigurationException 
	 * @throws IOException 
	 * @throws JAXBException 
	 * @throws SOAPException 
	 */
	public void testGetInstanceFailEnvelopeSize() throws SOAPException, JAXBException, IOException, DatatypeConfigurationException{
		log.info("_________________________________________________________________________");
		log.info("6.4 Get failure with maxenvelopesize exceeded error");
		int maxEnvelopeSize=8;
		try {
			ResourceState systemState = getComputerSystemState(resourceURIComputerSystem,selectorCreationClassName,selectorName,maxEnvelopeSize,operationTimeout);
		} catch (FaultException e) {
			log.info("PASS");
			return;
		}
		fail("This test is expected to Fault and did not.");

	}
	
	/**
	 * 6.5	Get failure with invalid selectors (mandatory)
	 * In this scenario, the client does a GET with a missing selector resulting in the server returning 
	 * an error response.
	 * @throws DatatypeConfigurationException 
	 * @throws IOException 
	 * @throws JAXBException 
	 * @throws SOAPException 
	 */
	public void testGetInstanceMissingSelector() throws SOAPException, JAXBException, IOException, DatatypeConfigurationException{
		log.info("_________________________________________________________________________");
		log.info("6.5 Get failure with invalid selectors");
		String selectorName=null;
		try {
			ResourceState systemState = getComputerSystemState(resourceURINumericSensor,selectorCreationClassName,selectorName,maxEnvelopeSize,operationTimeout);
		} catch (FaultException e) {
			log.info("PASS");
			return;
		}
		fail("This test is expected to Fault and did not.");
		
	}

	/**
	 * 6.6	Get failure with operational timeout (mandatory)
	 * In this scenario, the request fails since the service cannot respond in the 
	 * time specified in the OperationTimeout in the request.
	 */
	public void testGetInstanceTimeoutExceeded() throws SOAPException, JAXBException, IOException, DatatypeConfigurationException{
		log.info("_________________________________________________________________________");
		log.info("6.6	Get failure with operational timeout ");
		int operationTimeout=3;
		try {
			//Microsoft
			//resourceURIComputerSystem="wsman:microsoft.test/testresource/get/timeout";
			ResourceState systemState = getComputerSystemState(resourceURIComputerSystem,selectorCreationClassName,selectorName,maxEnvelopeSize,operationTimeout);
		} catch (FaultException e) {
			log.info("PASS");
			return;
		}
		fail("This test is expected to Fault and did not.");	
	}
	
	/**
	 * 6.7	Fragment Get (optional)
	 * In this scenario, a filter is specified to get a single property within 
	 * an instance of CIM_NumericSensor. XPATH is the recommended filter dialect. 
	 */
	//TODO Optional
	
	
	/**
	 * 7.1	Enumerate instances of CIM_NumericSensor (mandatory)
	 * In this scenario, the client does an enumeration request to retrieve all instances of CIM_NumericSensor class.
	 * 
	 * The sequence of operations is as follows:
	 * �	Client sends enumeration request
	 * �	Server sends response along with enumeration context A
	 * �	Client does a Pull request using enumeration context A
	 * �	Server responds with results along with enumeration context B
	 * �	Client does a Pull request using enumeration context B
	 * �	This continues until Server responds with results and EndOfSequence indicating there 
	 * 			are no more results
	 * @throws DatatypeConfigurationException 
	 * @throws FaultException 
	 * @throws IOException 
	 * @throws JAXBException 
	 * @throws SOAPException 
	 * @throws NoMatchFoundException 
	 * @throws XPathExpressionException 
	 */
	public void testEnumerateInstances() throws SOAPException, JAXBException, IOException, FaultException, DatatypeConfigurationException, XPathExpressionException {
		log.info("_________________________________________________________________________");
		log.info("7.1	Enumerate instances of CIM_NumericSensor");
		QName END_OF_SEQUENCE = new QName("http://schemas.xmlsoap.org/ws/2004/09/enumeration","EndOfSequence");
		HashMap<String, String> selectors = null;
		
		// for Intel openwsman
		//resourceURINumericSensor="http://schemas.dmtf.org/wbem/wscim/1/cim-schema/2/OMC_InitdService";
		
		Resource[] numericSensorEnumSet = ResourceFactory.find(destination, resourceURINumericSensor, operationTimeout, selectors);
		assertTrue(numericSensorEnumSet.length>0);
		Resource numericSensorEnum = numericSensorEnumSet[0];
		numericSensorEnum.setMaxEnvelopeSize(maxEnvelopeSize);
		numericSensorEnum.setMessageTimeout(operationTimeout);
		EnumerationCtx contextA = numericSensorEnum.enumerate(null, numericSensorEnum.XPATH_DIALECT, false,false,null);
		int maxElements = 1;
		int maxCharacters = -1;
		boolean moreResults = true;
		int passcount=0;
		try {
			while(moreResults&&passcount<50){
				ResourceState pullResult = numericSensorEnum.pull(contextA, operationTimeout, maxElements, maxCharacters);
				try{
				if(pullResult.getWrappedValueText(END_OF_SEQUENCE)!=null)
					// End of SEQ found, end iteration.
					moreResults=false;
				} catch(NoMatchFoundException e){
					passcount++;
				}
			}
		} catch (FaultException e){
			fail("A fault occured during iteration: "+e.getMessage());
		}
		assertTrue(passcount<200);
		log.info("PASS");
	}
	
	/**
	 * 7.2	Optmized Enumeration (optional)
	 * In this scenario, the client does an enumeration request to retrieve 
	 * all instances of CIM_NumericSensor class using optimized enumeration.
	 */
	//TODO Optional
	
	/**
	 * 7.3	Enumerate Failure (mandatory)
	 * In this scenario, the client does an enumeration request to retrieve all instances of 
	 * CIM_NumericSensor class, when doing the Pull request, it passes an invalid enumeration 
	 * context which is different from the one received from the server. This results in an 
	 * error being returned by the server.
	 * 
	 * The sequence of operations is as follows:
	 * �	Client sends enumeration request
	 * �	Server sends response along with enumeration context A
	 * �	Client does a Pull request using enumeration context A
	 * �	Server responds with results along with enumeration context B
	 * �	Client does a Pull request using enumeration context C
	 * �	Server returns an error
	 * @throws DatatypeConfigurationException 
	 * @throws FaultException 
	 * @throws IOException 
	 * @throws JAXBException 
	 * @throws SOAPException 
	 * @throws XPathExpressionException 
	 */
	public void testEnumerateFailureBadContext() throws SOAPException, JAXBException, IOException, FaultException, DatatypeConfigurationException, XPathExpressionException{
		String contextBogas="uuid:bogas-context-0000-00000-0000";
		log.info("_________________________________________________________________________");
		log.info("7.3	Enumerate Failure");
		QName END_OF_SEQUENCE = new QName("http://schemas.xmlsoap.org/ws/2004/09/enumeration","EndOfSequence");
		HashMap<String, String> selectors = null;
		
		// for Intel openwsman
		//resourceURINumericSensor="http://schemas.dmtf.org/wbem/wscim/1/cim-schema/2/OMC_InitdService";

		
		Resource[] numericSensorEnumSet = ResourceFactory.find(destination, resourceURINumericSensor, operationTimeout, selectors);
		assertTrue(numericSensorEnumSet.length>0);
		Resource numericSensorEnum = numericSensorEnumSet[0];
		numericSensorEnum.setMaxEnvelopeSize(maxEnvelopeSize);
		numericSensorEnum.setMessageTimeout(operationTimeout);
		EnumerationCtx contextA = numericSensorEnum.enumerate(null, numericSensorEnum.XPATH_DIALECT, false,false,null);
		int maxElements = 1;
		int maxCharacters = -1;
		boolean moreResults = true;
		int passcount=0;
		ResourceState pullResult = null;
		try {
			while(moreResults&&passcount<50){
				if(passcount==1)
					contextA.setContext(contextBogas);
				try {
					pullResult = numericSensorEnum.pull(contextA, operationTimeout, maxElements, maxCharacters);
				} catch (FaultException e){
					if(passcount==1)
						return;//PASSED!
					throw e;
				}
				
				try{
				if(pullResult.getWrappedValueText(END_OF_SEQUENCE)!=null)
					// End of SEQ found, end iteration.
					moreResults=false;
				} catch(NoMatchFoundException e){
					// Not found, Keep looking
					passcount++;
				}
			}
			
		} catch (FaultException e){
			fail("A fault occured during iteration: "+e.getMessage());
		}
		assertTrue(passcount<50);
		log.info("PASS");
	}

	/**
	 * 7.4	Enumerate ObjectAndEPR (optional)
	 * One of the common scenarios is to enumerate object and EPRs for a given resource URI, 
	 * this URI could correspond to a standard CIM class. The returned instances can correspond
	 * to the base class as well as derived classes (if polymorphism is suppored). The EPR 
	 * that�s returned along with the instance, can be used for modifying the instance using a 
	 * Put.
	 */
	//TODO Optional
	
	/**
	 * 7.5	Filtered Enumeration with XPath filter dialect (optional)
	 * In this scenario, an XPATH filter is used to retrieve a subset of instances where 
	 * SensorType = 2. 
	 */
	//TODO Optional
	
	/**
	 * 7.6	Filtered enumeration using Selector filter dialect (optional)
	 * PrevioPrevious scenario (7.5) is repeated with Selector filter dialect.
	 */
	//TODO Optional
	
	/**
	 * 8.1	Invoke ClearLog on an instance of RecordLog class (optional)
	 * In this scenario, the log records in one of the RecordLogs is deleted using the 
	 * ClearLog action.
	 */	
	//TODO Optional
	
	/**
	 * 9.1	Change Threshold on an instance of CIM_NumericSensor (mandatory)
	 * In this scenario, the client changes the value of LowerThresholdNonCritical threshold
	 * on an instance of CIM_NumericSensor class.
	 * @throws DatatypeConfigurationException 
	 * @throws FaultException 
	 * @throws IOException 
	 * @throws JAXBException 
	 * @throws SOAPException 
	 * @throws XPathExpressionException 
	 * @throws NoMatchFoundException 
	 */	
	public void testPutThreshhold() throws SOAPException, JAXBException, IOException, FaultException, DatatypeConfigurationException, XPathExpressionException, NoMatchFoundException{
		log.info("_________________________________________________________________________");
		log.info("9.1	Change Threshold on an instance of CIM_NumericSensor");
		
//		<w:Selector Name="CreationClassName">CIM_NumericSensor</w:Selector>
//		<w:Selector Name="DeviceID">81.0.32</w:Selector>
//		<w:Selector Name="SystemCreationClassName">ComputerSystem</w:Selector>
//		<w:Selector Name="SystemName">IPMI Controller 32</w:Selector>

		HashMap<String, String> selectors1 = new HashMap<String,String>();
		selectors1.put("CreationClassName", "CIM_NumericSensor");
		selectors1.put("DeviceID", "10.0.32");
		selectors1.put("SystemCreationClassName", "ComputerSystem");
		selectors1.put("SystemName", "IPMI Controller 32");

		// Microsoft
//		selectors1.put("CreationClassName", "NumericSensor");
//		selectors1.put("DeviceID", "23.0.32");
//		selectors1.put("SystemCreationClassName", "ComputerSystem");
//		selectors1.put("SystemName", "IPMI Controller 33");
//		resourceURINumericSensor="http://schemas.microsoft.com/wbem/wsman/1/wmi/root/hardware/NumericSensor";
		
		
		Resource[] resources = ResourceFactory.find(destination, resourceURINumericSensor, operationTimeout, selectors1);
		assertTrue(resources.length>0);
		Resource compSysResource = resources[0];
		compSysResource.setMaxEnvelopeSize(32000);
		ResourceState sensorState = compSysResource.get();
		String xPathExpression = "//*[local-name()=\"LowerThresholdNonCritical\"]";
		sensorState.setFieldValues(xPathExpression, "50");
		ResourceState newState = compSysResource.put(sensorState);
		assertEquals("50",newState.getValueText(xPathExpression));
		ResourceState finalState = compSysResource.get();
		
		log.info("PASS");

	}

	/**
	 * 9.2	Fragment Transfer (Optional)
	 * In this scenario, a single property (LowerThresholdNonCritical) of an instance of 
	 * CIM_NUmericSensor is updated using Fragment transfer.
	 */
	//TODO Optional
	
	/**
	 * 10	Eventing (Optional)
	 * In this scenario, Pull based subscription is used to retrieve events.
	 */
	//TODO Optional
	private ResourceState getComputerSystemState(String resourceURI,String selectorCreationClassName,String selectorName,int maxEnvelopeSize,int operationTimeout) throws SOAPException, JAXBException, IOException, FaultException, DatatypeConfigurationException {
		HashMap<String, String> selectors = new HashMap<String,String>();
		if(selectorName!=null)
			selectors.put("Name", selectorName);
		selectors.put("CreationClassName", selectorCreationClassName);
		selectors.put("LUN", "0");
		// Must be in for symlabs to pass timeout test
		//selectors.put("timeout", "timeout");
		Resource[] computerSystemResources = ResourceFactory.find(destination, resourceURI, operationTimeout, selectors);
		assertTrue(computerSystemResources.length>0);
		Resource computerSystemResource = computerSystemResources[0];
		computerSystemResource.setMaxEnvelopeSize(maxEnvelopeSize);
		return computerSystemResource.get();
	}
	public static void main(String[] args) {
		 junit.textui.TestRunner.run(Interop20060801Test.class);
	}
	
//	public void testPutThreshholdOpenWSMan() throws SOAPException, JAXBException, IOException, FaultException, DatatypeConfigurationException, XPathExpressionException, NoMatchFoundException{
//		log.info("_________________________________________________________________________");
//		log.info("9.1	Change Threshold on an instance of CIM_NumericSensor");
//		
//
//		HashMap<String, String> selectors1 = new HashMap<String,String>();
////		selectors1.put("CreationClassName", "CIM_NumericSensor");
////		selectors1.put("DeviceID", "10.0.32");
////		selectors1.put("SystemCreationClassName", "ComputerSystem");
////		selectors1.put("SystemName", "IPMI Controller 32");
//
//		// openwsman
//		selectors1.put("InstanceID", "omc:timezone");
//		resourceURINumericSensor="http://schemas.dmtf.org/wbem/wscim/1/cim-schema/2/OMC_TimeZoneSettingData";
//		
//		//resourceURINumericSensor="";
//		
//		Resource[] resources = ResourceFactory.find(destination, resourceURINumericSensor, operationTimeout, selectors1);
//		assertTrue(resources.length>0);
//		Resource compSysResource = resources[0];
//		compSysResource.setMaxEnvelopeSize(32000);
//		ResourceState sensorState = compSysResource.get();
//		String xPathExpression = "//*[local-name()=\"TimeZone\"]";
//		sensorState.setFieldValues(xPathExpression, "US/Pacific");
//		ResourceState newState = compSysResource.put(sensorState);
//		ResourceState finalState = compSysResource.get();
//		assertEquals("US/Pacific",finalState.getValueText(xPathExpression));
//		
//		log.info("PASS");
//
//	}

}
