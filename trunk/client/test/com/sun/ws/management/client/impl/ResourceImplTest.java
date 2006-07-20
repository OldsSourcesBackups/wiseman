package com.sun.ws.management.client.impl;

import java.io.IOException;
import java.io.StringWriter;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.soap.SOAPException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPathExpressionException;

import org.dmtf.schemas.wbem.wsman._1.wsman.SelectorSetType;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import util.WsManBaseTestSupport;

import com.hp.examples.ws.wsman.user.ObjectFactory;
import com.hp.examples.ws.wsman.user.UserType;
import com.sun.ws.management.Management;
import com.sun.ws.management.client.Resource;
import com.sun.ws.management.client.ResourceFactory;
import com.sun.ws.management.client.ResourceState;
import com.sun.ws.management.client.exceptions.FaultException;
import com.sun.ws.management.client.exceptions.NoMatchFoundException;
import com.sun.ws.management.client.impl.EnumerationResourceImpl;
import com.sun.ws.management.client.impl.TransferableResourceImpl;
import com.sun.ws.management.transfer.InvalidRepresentationFault;
import com.sun.ws.management.xml.XPath;
import com.sun.ws.management.xml.XmlBinding;

/**
 * This class tests basic WS Transfer behavior and also demonstrates the
 * capabilies of the client library.
 *
 * @author wire
 *
 */
public class ResourceImplTest extends WsManBaseTestSupport {

	private static ObjectFactory userFactory = new ObjectFactory();

	public static String destUrl = "http://localhost:8080/hpwsman/";

	public static String resourceUri = "wsman:auth/user";

	public static long timeoutInMilliseconds = 9400000;

	XmlBinding binding = null;

	protected void setUp() throws Exception {
		super.setUp();
		try {
			new Management();
		} catch (SOAPException e) {
			fail("Can't init wiseman");
		}
		try {
			binding = new XmlBinding(null,"com.hp.examples.ws.wsman.user");
		} catch (JAXBException e) {
			fail(e.getMessage());
		}
		userFactory = new ObjectFactory();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/*
	 * Test method for
	 * 'com.sun.ws.management.client.impl.TransferableResourceImpl.get(Map<String,
	 * Object>)'
	 */
	@SuppressWarnings("unchecked")
	public void testGet() throws JAXBException, SOAPException, IOException,
			DatatypeConfigurationException, TransformerException,
			FaultException {

//		String className ="com.sun.ws.management.server.ReflectiveRequestDispatcher";
//		 locateClass(className);
//		 className ="com.sun.tools.xjc.reader.Util";
//		 locateClass(className);

		// now build Create XML body contents.
		String fName = "Get";
		String lName = "Guy";
		String address = "Smoky Lane";

		// Create a JAXB type representing a User's internal state
		UserType user = userFactory.createUserType();
		user.setLastname(lName);
		user.setFirstname(fName);
		user.setAddress(address);
		user.setCity("Mount Laurel");
		user.setState("NJ");
		user.setZip("08054");
		user.setAge(16);

		// Convert this into a state XML document dom
		Document content = Management.newDocument();
		JAXBElement<UserType> userElement = userFactory.createUser(user);
		binding.marshal(userElement, content);

		Resource resource = ResourceFactory.create(ResourceImplTest.destUrl,
				ResourceImplTest.resourceUri,
				ResourceImplTest.timeoutInMilliseconds, content,
				ResourceFactory.LATEST);

		// pull out Epr and parse for selectors
		// EndpointReferenceType retrievedEpr = created.getResourceEpr();
		ResourceState resourceState = resource.get();

		assertNotNull("Retrieved resource is NULL.", resourceState);

		Document resourceStateDom = resourceState.getDocument();
		JAXBElement<UserType> unmarshal = (JAXBElement<UserType>) binding
						.unmarshal(resourceStateDom);
		JAXBElement<UserType> userReturnedElement = unmarshal;
		UserType returnedUser = (UserType) userReturnedElement.getValue();

		// Compare the created state to the returned state
		assertEquals(returnedUser.getLastname(), user.getLastname());
		assertEquals(returnedUser.getFirstname(), user.getFirstname());
		assertEquals(returnedUser.getAddress(), user.getAddress());
		assertEquals(returnedUser.getCity(), user.getCity());
		assertEquals(returnedUser.getState(), user.getState());
		assertEquals(returnedUser.getZip(), user.getZip());
		assertEquals(returnedUser.getAge(), user.getAge());

	}

	/*
	 * Test method for 'com.sun.ws.management.client.impl.TransferableResourceImpl.get(Map<String,
	 * Object>)'
	 */
	public void testFragmentGet() throws JAXBException, SOAPException, IOException,
			DatatypeConfigurationException, TransformerException, FaultException {

		// now build Create XML body contents.
		String fName = "Get";
		String lName = "Guy";
		String address = "Smoky Lane";

		// Create a JAXB type representing a User's internal state
		UserType user = userFactory.createUserType();
		user.setLastname(lName);
		user.setFirstname(fName);
		user.setAddress(address);
		user.setCity("Mount Laurel");
		user.setState("NJ");
		user.setZip("08054");
		user.setAge(16);

		// Convert this into a state XML document dom
		Document content = Management.newDocument();
		JAXBElement<UserType> userElement =userFactory.createUser(user);
		binding.marshal(userElement,content);

		Resource resource = ResourceFactory.create(ResourceImplTest.destUrl,
				ResourceImplTest.resourceUri,
				ResourceImplTest.timeoutInMilliseconds,
				content,ResourceFactory.LATEST);

		//Now build the XPath expression for fragment GET
		String xPathReq = "//*[local-name()='age']";
		//TODO: write test using /text() method as currently fails on the server.
//		String xPathReq = "//*[local-name()='age']/text()";
		ResourceState resourceState = resource.get(xPathReq,null);

		assertNotNull("Retrieved resource is NULL.", resourceState);

		//Retrieve content
		String fragmentContent = "";
		try {
		  fragmentContent = resourceState.getValueText("*");
		} catch (XPathExpressionException e) {
			fail(e.getMessage());
		} catch (NoMatchFoundException e) {
			fail("Bad XPath expression:"+e.getMessage());
		}
		//Test expected age content
		assertTrue(fragmentContent.indexOf(user.getAge()+"")>-1);
//		System.out.println("RsState:"+resourceState.toString());
		//Test that no other part of the fragment, like fName was returned.
		assertTrue(resourceState.toString().indexOf(fName)==-1);

	}

	/*
	 * Test method for
	 * 'com.sun.ws.management.client.impl.TransferableResourceImpl.create(Map<QName,
	 * String>)'
	 */
	public void testCreate() throws JAXBException, SOAPException, IOException,
			DatatypeConfigurationException, FaultException {

		// Now create an instance and test it's contents
		String dest = ResourceImplTest.destUrl;
		String resource = ResourceImplTest.resourceUri;

		UserType user = userFactory.createUserType();
		user.setLastname("Finkle");
		user.setFirstname("Joe");
		user.setAddress("6000 Irwin Drive");
		user.setCity("Mount Laurel");
		user.setState("NJ");
		user.setZip("08054");
		user.setAge(16);

		Document stateDocument = Management.newDocument();
		JAXBElement<UserType> userElement = userFactory.createUser(user);
		binding.marshal(userElement, stateDocument);

		Resource newResource = ResourceFactory.create(ResourceImplTest.destUrl,
				ResourceImplTest.resourceUri,
				ResourceImplTest.timeoutInMilliseconds, stateDocument,
				ResourceFactory.LATEST);

		assertEquals("Values not identical.", dest, newResource
				.getDestination());
		assertEquals("Values not identical.", resource, newResource
				.getResourceUri());
		assertEquals("Values not identical.",
				ResourceImplTest.timeoutInMilliseconds, newResource
						.getMessageTimeout());

		// test contents of the selectors returned. Anything beyond EPR is
		// Xml:any though
		assertNotNull("SelectorSet null!", newResource.getSelectorSet());
		assertTrue("Incorrect number of Selectors returned.", (newResource
				.getSelectorSet().getSelector().size() == 2));
		assertEquals("Values not same.", "Finkle", newResource.getSelectorSet()
				.getSelector().get(0).getContent().get(0));
		assertEquals("Values not same.", "Joe", newResource.getSelectorSet()
				.getSelector().get(1).getContent().get(0));

	}

	/*
	 * Test method for 'com.sun.ws.management.client.impl.TransferableResourceImpl.create(Map<QName,
	 * String>)'
	 */
	public void testFragmentCreate() throws JAXBException, SOAPException, IOException,
			 DatatypeConfigurationException, FaultException {

		// Now create an instance and test it's contents
		String dest = ResourceImplTest.destUrl;
		String resource = ResourceImplTest.resourceUri;

		String lastName ="Finkle-Fragment";
		UserType user = userFactory.createUserType();
		user.setLastname(lastName);
		user.setFirstname("Joe");
		user.setAddress("6000 Irwin Drive");
		user.setCity("Mount Laurel");
		user.setState("NJ");
		user.setZip("08054");
//		user.setAge(16);

		Document stateDocument = Management.newDocument();
		JAXBElement<UserType> userElement =userFactory.createUser(user);
		binding.marshal(userElement,stateDocument);

		Resource newResource = ResourceFactory.create(ResourceImplTest.destUrl, ResourceImplTest.resourceUri,
				ResourceImplTest.timeoutInMilliseconds, stateDocument,ResourceFactory.LATEST);

		assertEquals("Values not identical.", dest, newResource.getDestination());
		assertEquals("Values not identical.", resource, newResource
				.getResourceUri());
		assertEquals("Values not identical.", ResourceImplTest.timeoutInMilliseconds, newResource
				.getMessageTimeout());

		// DONE: test contents of the selectors returned. Anything beyond EPR is
		// Xml:any though
		assertNotNull("SelectorSet null!", newResource.getSelectorSet());
		assertTrue("Incorrect number of Selectors returned.", (newResource
				.getSelectorSet().getSelector().size() == 2));
		assertEquals("Values not same.", lastName, newResource.getSelectorSet()
				.getSelector().get(0).getContent().get(0));
		assertEquals("Values not same.", "Joe", newResource.getSelectorSet()
				.getSelector().get(1).getContent().get(0));

		//TODO: test that Age field was never set
		ResourceState resourceState = newResource.get();
//		System.out.println("resState:"+resourceState.toString());
		assertNotNull("Retrieved resource is NULL.", resourceState);

		Document resourceStateDom = resourceState.getDocument();
		JAXBElement<UserType> userReturnedElement =
			(JAXBElement<UserType>)binding.unmarshal(resourceStateDom);
		UserType returnedUser = (UserType)userReturnedElement.getValue();

		// Compare the created state to the returned state
		assertEquals(returnedUser.getLastname(),user.getLastname());
		assertEquals(returnedUser.getFirstname(),user.getFirstname());
		assertNull("Age field should be null:"+returnedUser.getAge(),returnedUser.getAge());

		//TODO: build the fragment create method and object changes
		String fragmentRequest = "//*[local-name()='age']";
		  stateDocument = Management.newDocument();
		    // Insert the root element node
		    Element element =
		    	stateDocument.createElementNS("http://examples.hp.com/ws/wsman/user","ns9:age");
	    int number = 10;
		    element.setTextContent(""+number);
		    stateDocument.appendChild(element);
		  userElement =userFactory.createUser(user);
//		 binding.marshal(userElement,stateDocument);
		Resource newFragResource = ResourceFactory.createFragment(ResourceImplTest.destUrl,
				ResourceImplTest.resourceUri,
				newResource.getSelectorSet(),
				ResourceImplTest.timeoutInMilliseconds,
				stateDocument,ResourceFactory.LATEST,
				fragmentRequest,
				XPath.NS_URI);

		assertEquals("Values not identical.", dest, newResource.getDestination());
		assertEquals("Values not identical.", resource, newResource
				.getResourceUri());
		assertEquals("Values not identical.", ResourceImplTest.timeoutInMilliseconds, newResource
				.getMessageTimeout());

		resourceState = newResource.get();
		assertNotNull("Retrieved resource is NULL.", resourceState);

		resourceStateDom = resourceState.getDocument();
		userReturnedElement =
			(JAXBElement<UserType>)binding.unmarshal(resourceStateDom);
		returnedUser = (UserType)userReturnedElement.getValue();

		// Compare the created state to the returned state
		assertEquals(returnedUser.getLastname(),user.getLastname());
		assertEquals(returnedUser.getFirstname(),user.getFirstname());
		assertEquals("Value not retrieved correctly.",(int)returnedUser.getAge(),number);

	}

	/*
	 * Test method for
	 * 'com.sun.ws.management.client.impl.TransferableResourceImpl.delete()'
	 */
	public void testDelete() throws JAXBException, SOAPException, IOException,
			FaultException, DatatypeConfigurationException {
		String dest = ResourceImplTest.destUrl;
		String resource = ResourceImplTest.resourceUri;
		long timeoutInMilliseconds = ResourceImplTest.timeoutInMilliseconds;
		Document content = null;

		// now build Create XML body contents.
		String fName = "Delete";
		String lName = "Guy";
		String address = "Smoky Lane";
		UserType user = userFactory.createUserType();
		user.setLastname(lName);
		user.setFirstname(fName);
		user.setAddress(address);
		user.setCity("Mount Laurel");
		user.setState("NJ");
		user.setZip("08054");
		user.setAge(16);
		content = Management.newDocument();

		content = loadUserTypeToDocument(content, user, binding);

		Resource created = ResourceFactory.create(dest, resource,
				timeoutInMilliseconds, content, ResourceFactory.LATEST);
		// pull out Epr and parse for selectors
		// EndpointReferenceType retrievedEpr = created.getResourceEpr();

		SelectorSetType selectorSet = null;
		selectorSet = created.getSelectorSet();
		assertNotNull("SelectorSet is null.", selectorSet);

		created.delete();
		try {
			created.get();
			fail("Able to retrieve resource that should have been deleted.");
		} catch (InvalidRepresentationFault df) {
			assertTrue(true);
		} catch (FaultException fex) {
			assertTrue(true);
		}

	}

	public void testFragmentDelete() throws JAXBException, SOAPException, IOException,
			FaultException, DatatypeConfigurationException {
		String dest = ResourceImplTest.destUrl;
		String resource = ResourceImplTest.resourceUri;
		long timeoutInMilliseconds = ResourceImplTest.timeoutInMilliseconds;

		Document content = null;
		// now build Create XML body contents.
		String fName = "Delete";
		String lName = "Guy";
		String address = "Smoky Lane";
		UserType user = userFactory.createUserType();
		user.setLastname(lName);
		user.setFirstname(fName);
		user.setAddress(address);
		user.setCity("Mount Laurel");
		user.setState("NJ");
		user.setZip("08054");
		user.setAge(16);
		content = Management.newDocument();

		content = loadUserTypeToDocument(content, user, binding);

//		Resource created = ResourceFactory.create(dest, resource,
		TransferableResourceImpl created = (TransferableResourceImpl) ResourceFactory.create(dest, resource,
				timeoutInMilliseconds, content,ResourceFactory.LATEST);
		// pull out Epr and parse for selectors
		// EndpointReferenceType retrievedEpr = created.getResourceEpr();

		SelectorSetType selectorSet = null;
		selectorSet = created.getSelectorSet();
		assertNotNull("SelectorSet is null.", selectorSet);

		String xPathReq = "//*[local-name()='age']";
		created.delete(xPathReq,null);
		try {
			ResourceState res = created.get();
			assertTrue(true);
//			System.out.println("res"+res.toString());
		} catch (InvalidRepresentationFault df) {
			fail("Unable to retrieve resource that should NOT have been deleted.");
		} catch (FaultException fex) {
			fail("Unable to retrieve resource that should NOT have been deleted.");
		}

	}


	public void testPut() throws SOAPException, JAXBException, IOException,
			FaultException, DatatypeConfigurationException {
		// Now create an instance and test it's contents
		String dest = ResourceImplTest.destUrl;
		String resource = ResourceImplTest.resourceUri;
		long timeoutInMilliseconds = ResourceImplTest.timeoutInMilliseconds;
		Document content = null;

		// now build Create XML body contents.
		String fName = "Joe";
		String lName = "Finkle";
		String address = "6000 Irwin Drive";
		String addressModified = address + "Modified";

		UserType user = userFactory.createUserType();
		user.setLastname(lName);
		user.setFirstname(fName);
		user.setAddress(address);
		user.setCity("Mount Laurel");
		user.setState("NJ");
		user.setZip("08054");
		user.setAge(16);
		content = Management.newDocument();

		JAXBElement<UserType> userElement = userFactory.createUser(user);
		try {
			binding.marshal(userElement, content);
		} catch (JAXBException e1) {
			fail(e1.getMessage());
		}
		Resource created = ResourceFactory.create(dest, resource,
				timeoutInMilliseconds, content, ResourceFactory.LATEST);

		user.setAddress(addressModified);

		content = Management.newDocument();
		userElement = userFactory.createUser(user);
		binding.marshal(userElement, content);

		// SelectorSetType existingId = created.getSelectorSet();
		created.put(content);

		// DONE: write code to retrieve the payload from Resource
		ResourceState retrieved = created.get();

		assertNotNull("Retrieved resource is NULL.", retrieved);
		Document payLoad = retrieved.getDocument();

		Element node = payLoad.getDocumentElement();
		user = userFactory.createUserType();
		// Init the user by transfering the fields from
		Node userChildNode = node;
		JAXBElement<UserType> ob = (JAXBElement<UserType>) binding
				.unmarshal(userChildNode);
		user = (UserType) ob.getValue();

		assertEquals("Values not equal.", addressModified, user.getAddress());

	}

//	public void testFragmentPut() throws SOAPException, JAXBException, IOException, FaultException, DatatypeConfigurationException{
//		// Now create an instance and test it's contents
//		String dest = ResourceImplTest.destUrl;
//		String resource = ResourceImplTest.resourceUri;
//		long timeoutInMilliseconds = ResourceImplTest.timeoutInMilliseconds;
//		Document content = null;
//
//		// now build Create XML body contents.
//		String fName = "Fragment";
//		String lName = "Put";
//		String state = "NJ";
//		String stateUpdated = "New Jersey";
//		String address = "6000 Irwin Drive";
////		String addressModified = address + "FragmentPut";
//
//		UserType user = userFactory.createUserType();
//		user.setLastname(lName);
//		user.setFirstname(fName);
//		user.setAddress(address);
//		user.setCity("Mount Laurel");
//		user.setState(state);
//		user.setZip("08054");
//		user.setAge(16);
//		content = Management.newDocument();
//
//		JAXBElement<UserType> userElement = userFactory.createUser(user);
//		try {
//			binding.marshal(userElement, content);
//		} catch (JAXBException e1) {
//			fail(e1.getMessage());
//		}
//		TransferableResource created =(TransferableResource) ResourceFactory.create(dest, resource,
//				timeoutInMilliseconds, content,ResourceFactory.LATEST);
//
//		//DONE: create fragment request to update state field only.
//		String fragmentRequest = "//*[local-name()='state']";
//		  content = Management.newDocument();
//		    // Insert the root element node
//		    Element element =
//		    	content.createElementNS("http://examples.hp.com/ws/wsman/user","ns9:state");
//		    element.setTextContent(stateUpdated);
//		    content.appendChild(element);
//		    created.put(content,
//		    		fragmentRequest,
//					XPath.NS_URI);
//
//		ResourceState retrieved = created.get();
//
//		assertNotNull("Retrieved resource is NULL.", retrieved);
//		Document payLoad = retrieved.getDocument();
//
//		Element node = payLoad.getDocumentElement();
//		user = userFactory.createUserType();
//		// Init the user by transfering the fields from
//		Node userChildNode = node;
//		JAXBElement<UserType> ob = (JAXBElement<UserType>) binding
//				.unmarshal(userChildNode);
//		user = (UserType) ob.getValue();
//
//		assertEquals("Values not equal.", stateUpdated, user.getState());
//
//	}


	public void testFind() throws SOAPException, JAXBException, IOException,
			FaultException, DatatypeConfigurationException {

		// test that Find works to retrieve the Enumeration instance.
		String dest = "";
		String resUri = "";
		Resource[] enumerableResources = ResourceFactory.find(
				ResourceImplTest.destUrl, ResourceImplTest.resourceUri,
				ResourceImplTest.timeoutInMilliseconds, null);
		assertEquals("Expected one resource.", 1, enumerableResources.length);
		// TODO: write test for specific resource retrieval.
		// Source src = null;
		// JAXBSource.sourceToInputSource(src);

	}

//	 public void testTest(){
//		 loadUsersFromFile();
//	 }
//	
//	 public void testEnumeration() throws SOAPException, JAXBException,
//		 IOException, FaultException, DatatypeConfigurationException{
////		 String resourceUri = "wsman:hp/employee";
//		 String resourceUri = "wsman:auth/userenum";
////		 String className ="com.sun.ws.management.server.ReflectiveRequestDispatcher";
////		 locateClass(className);
////		 className ="com.sun.tools.xjc.reader.Util";
////		 locateClass(className);
//		 //test that Find works to retrieve the Enumeration instance.
//		 Resource[] enumerableResources = ResourceFactory.find(
//		 ResourceImplTest.destUrl,
////		 ResourceImplTest.resourceUri,
//		 resourceUri,
//		 ResourceImplTest.timeoutInMilliseconds,
//		 null);
//		 
//		 assertEquals("Expected one resource.",1,enumerableResources.length);
//		 Resource retrieved = enumerableResources[0];
//		 assertTrue(retrieved instanceof EnumerationResourceImpl);
//		
//		 //Build the filters
//		 // String xpathFilter = "/employee[@mail='simeon.pinder@hp.com']";
//		 String xpathFilter = "/employee[@sn='kramer']";
//		 String[] filters = new String[]{xpathFilter};
//		 String enumContext = retrieved.enumerate(filters,XPath.NS_URI,false);
//		 assertNotNull("Enum context retrieval problem.",enumContext);
//		 assertTrue("Context id is empty.",(enumContext.trim().length()>0));
//		
//		 //DONE: now test the pull mechanism
//		 int maxTime =1000*60*15;
//		 int maxElements = 5;
//		 int maxChar = 20000; //random limit. NOT currently enforced.
//		 // Object[] retrievedValues = retrieved.pull(enumContext,maxTime,
//		 ResourceState retrievedValues = retrieved.pull(enumContext,maxTime,
//		 maxElements,maxChar);
//		 //Navigate down to retrieve Items children
//		 //Document Children
//		 NodeList rootChildren = retrievedValues.getDocument().getChildNodes();
//		 //PullResponse node
//		 Node child = rootChildren.item(0);
//		 //Items node
//		 NodeList children = child.getChildNodes().item(1).getChildNodes();
//		
//		 for(int i=0;i<children.getLength();i++){
//		 Node node = children.item(i);
//		 if(node.getNodeName().indexOf("EnumerationContext")>-1){
//		 //ignore
//		 System.out.println("This line mattered!!!");
//		 }else{
//		 UserType user = null;
//		 XmlBinding empBinding = new XmlBinding("com.hp.examples.ws.wsman.user");
//		 JAXBElement<UserType> ob =
//		 (JAXBElement<UserType>)empBinding.unmarshal(node);
//		 user=(UserType)ob.getValue();
//		 System.out.println("F:"+user.getFirstname());
//		 System.out.println("L:"+user.getLastname());
//		 }
//		 }
////		 EmployeeType user = null;
////		 XmlBinding empBinding = new XmlBinding("com.hp.examples.ws.wsman.emp");
////		 JAXBElement<EmployeeType> ob =
////		 (JAXBElement<EmployeeType>)empBinding.unmarshal(node);
////		 user=(EmployeeType)ob.getValue();
////		 System.out.println("F:"+user.getFirstname());
////		 System.out.println("L:"+user.getLastname());
////		 }
////		 }
//		
//		 retrievedValues = retrieved.pull(enumContext,maxTime,
//		 maxElements,maxChar);
//		 assertNotNull("No pull results obtained.",retrievedValues);
//		
//		 //Now do a find again to make sure that correct context is retrieved.
//		 Resource[] enumResources = ResourceFactory.find(
//		 ResourceImplTest.destUrl,
//		 resourceUri,
//		 ResourceImplTest.timeoutInMilliseconds,
//		 null);
//		 Resource retEnumRes = enumResources[0];
//		 retrievedValues = retEnumRes.pull(enumContext,maxTime,
//		 maxElements,maxChar);
//		
//		 //TODO: test release
//		 retrieved.release(enumContext);
//		
//		 retrieved.enumerate(filters,XPath.NS_URI,false);
//		 // try{
//		 // retrieved.pull(enumContext,maxTime,
//		 // maxElements,maxChar);
//		 // fail("Should be no such context.");
//		 // }catch(InvalidEnumerationContextFault iecf){
//		 // assertTrue(true);
//		 // }
//	
//	 }

	public static String xmlToString(Node node) {
		try {
			Source source = new DOMSource(node);
			StringWriter stringWriter = new StringWriter();
			Result result = new StreamResult(stringWriter);
			TransformerFactory factory = TransformerFactory.newInstance();
			Transformer transformer = factory.newTransformer();
			transformer.transform(source, result);
			return stringWriter.getBuffer().toString();
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * Convenience method that loads a given UserType into Document.
	 */
	public static Document loadUserTypeToDocument(Document content,
			UserType user, XmlBinding binding) throws JAXBException {

		content = Management.newDocument();
		JAXBElement<UserType> userElement = userFactory.createUser(user);
		binding.marshal(userElement, content);

		return content;
	}

	// Build a UserType instance from the Document passed in.
	public static UserType loadUserTypeFromDocument(UserType user,
			Document content, ResourceState retrieved, XmlBinding binding)
			throws JAXBException {

		user = userFactory.createUserType();
		Document payLoad = retrieved.getDocument();
		Element node = payLoad.getDocumentElement();

		Node userChildNode = node;
		JAXBElement<UserType> ob = (JAXBElement<UserType>) binding
				.unmarshal(userChildNode);
		user = (UserType) ob.getValue();

		return user;
	}

	public void locateClass(String className){
		 if (!className.startsWith("/")) {
	         className = "/" + className;
	       }
	       className = className.replace('.', '/');
	       className = className + ".class";
	      java.net.URL classUrl =
	        new EnumerationResourceImpl().getClass().getResource(className);
	      if (classUrl != null) {
	        System.out.println("\nClass '" + className +
	          "' found in \n'" + classUrl.getFile() + "'");
	      } else {
	        System.out.println("\nClass '" + className +
	          "' not found in \n'" +
	          System.getProperty("java.class.path") + "'");
	      }
	}
	
//	private static final String div = "################# User Type Divider ################";
//	public void loadUsersFromFile(){
//		String userStoreSource="com/hp/management/wsman/test/models/users.store";
//		InputStream is 
//		  =EnumerationUserHandler.class.getClassLoader().getResourceAsStream(userStoreSource);
//		//TODO: cycle through input stream and load the UserType instances
//		BufferedReader br = new BufferedReader(new InputStreamReader(is));
//		String line = "";
//		String lineInBuffer = "";
//		ArrayList allUsers = new ArrayList();
//	  try{	
//		while((line=br.readLine())!=null){
//			if((line.indexOf(div)==-1)&&(line.trim().length()>0)){
//				// create a new user class and add it to the list
//				UserModelObject userObject = new UserModelObject();
//				line = line.trim();
//				System.out.println("line:"+line);
//				Document newDocument = Management.newDocument();
//				Node node =newDocument.createTextNode(line);
//					newDocument.adoptNode(node);
//				System.out.println("node:"+node+" "+node.getNodeType()+" "+node.getNodeValue());
//				System.out.println("nodeName "+node.getNodeName()+" "+node.getTextContent());
//				System.out.println("toString() "+node.toString());
//				System.out.println("NodeAsString:"+xmlToString(node.getFirstChild())+":");
//				System.out.println("NodeAsString:"+xmlToString(node.getLastChild())+":");
//			    System.out.println("NodeCnt:"+node.getChildNodes().getLength());
//				System.out.println("NodeAsString:"+xmlToString(node.getChildNodes().item(1))+":");
//			    // Insert the root element node
////			    Element element =
////			    	content.createElementNS("http://examples.hp.com/ws/wsman/user","ns9:state");
////			    element.setTextContent(stateUpdated);
////			    content.appendChild(element);
////			    created.put(content,
////			    		fragmentRequest,
////						XPath.NS_URI);
////				//Normal processing to create a new UserObject
////				Node userChildNode = body.getFirstChild();
//				UserType user=null;
//				try {
//					JAXBElement<UserType> ob = (JAXBElement<UserType>)binding.unmarshal(node);
////					JAXBElement<UserType> ob = (JAXBElement<UserType>)binding.unmarshal(newDocument);
//					user=(UserType)ob.getValue();
//					
//				} catch (JAXBException e) {
//					System.out.println("e:"+e.getMessage());
//					throw new InvalidRepresentationFault(InvalidRepresentationFault.Detail.INVALID_VALUES);
//				}
//
//			}
//		}//end of while
//	  }catch(Exception ex){
//		  System.out.println("ex:"+ex.getMessage());
//	  }
//	}
}