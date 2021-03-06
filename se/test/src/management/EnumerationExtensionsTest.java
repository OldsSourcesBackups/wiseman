/*
 * Copyright 2005 Sun Microsystems, Inc.
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
 ** Copyright (C) 2006, 2007 Hewlett-Packard Development Company, L.P.
 **
 ** Authors: Simeon Pinder (simeon.pinder@hp.com), Denis Rachal (denis.rachal@hp.com),
 ** Nancy Beers (nancy.beers@hp.com), William Reichardt
 **
 **$Log: not supported by cvs2svn $
 **Revision 1.14  2007/11/30 14:32:37  denis_rachal
 **Issue number:  140
 **Obtained from:
 **Submitted by:  jfdenise
 **Reviewed by:
 **
 **WSManAgentSupport and WSEnumerationSupport changed to coordinate their separate threads when handling wsman:OperationTimeout and wsen:MaxTime timeouts. If a timeout now occurs during an enumeration operation the WSEnumerationSupport is notified by the WSManAgentSupport thread. WSEnumerationSupport saves any items collected from the EnumerationIterator in the context so they may be fetched by the client on the next pull. Items are no longer lost on timeouts.
 **
 **Tests were added to correctly test this functionality and older tests were updated to properly test timeout functionality.
 **
 **Additionally some tests were updated to make better use of the XmlBinding object and improve performance on testing.
 **
 **Revision 1.13  2007/05/30 20:30:24  nbeers
 **Add HP copyright header
 **
 **
 * $Id: EnumerationExtensionsTest.java,v 1.15 2007-12-03 09:15:09 denis_rachal Exp $
 */

package management;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.xml.bind.JAXBException;
import javax.xml.datatype.DatatypeFactory;

import org.dmtf.schemas.wbem.wsman._1.wsman.AttributableNonNegativeInteger;
import org.dmtf.schemas.wbem.wsman._1.wsman.OptionType;
import org.w3._2003._05.soap_envelope.Fault;
import org.xmlsoap.schemas.ws._2004._08.addressing.EndpointReferenceType;
import org.xmlsoap.schemas.ws._2004._09.enumeration.Enumerate;
import org.xmlsoap.schemas.ws._2004._09.enumeration.EnumerateResponse;
import org.xmlsoap.schemas.ws._2004._09.enumeration.EnumerationContextType;
import org.xmlsoap.schemas.ws._2004._09.enumeration.PullResponse;

import util.TestBase;

import com.sun.ws.management.TimedOutFault;
import com.sun.ws.management.addressing.Addressing;
import com.sun.ws.management.enumeration.Enumeration;
import com.sun.ws.management.enumeration.EnumerationExtensions;
import com.sun.ws.management.enumeration.EnumerationMessageValues;
import com.sun.ws.management.enumeration.EnumerationUtility;
import com.sun.ws.management.server.EnumerationItem;
import com.sun.ws.management.soap.SOAP;
import com.sun.ws.management.transport.HttpClient;

/**
 * Unit test for WS-Enumeration extensions in WS-Management
 */
public class EnumerationExtensionsTest extends TestBase {

    public EnumerationExtensionsTest(final String testName) throws JAXBException {
        super(testName);
    }

    public static junit.framework.Test suite() {
        final junit.framework.TestSuite suite = new junit.framework.TestSuite(EnumerationExtensionsTest.class);
        return suite;
    }

    public void testEnumerateVisual() throws Exception {


        final EndpointReferenceType endTo = Addressing.createEndpointReference("http://host/endTo", null, null, null, null);
        final String expires = DatatypeFactory.newInstance().newDuration(300000).toString();

        EnumerationMessageValues settings = EnumerationMessageValues.newInstance();
    	settings.setEnumerationMessageActionType(Enumeration.ENUMERATE_ACTION_URI);
    	settings.setReplyTo(Addressing.ANONYMOUS_ENDPOINT_URI);
    	settings.setTo(DESTINATION);
    	settings.setEnumerationMode(EnumerationExtensions.Mode.EnumerateObjectAndEPR);
    	settings.setMaxElements(2);
    	settings.setRequestForOptimizedEnumeration(true);
    	settings.setRequestForTotalItemsCount(false);
    	settings.setXmlBinding(binding);
    	settings.setExpires(300000);
    	settings.setEndTo("http://host/endTo");

        settings.setFilterDialect("http://mydomain/my.filter.dialect");
        settings.setFilter("my/filter/expression");


    	final Enumeration enu = EnumerationUtility.buildMessage(null, settings);

        enu.prettyPrint(logfile);

        final ByteArrayOutputStream bos = new ByteArrayOutputStream();
        enu.writeTo(bos);
        final EnumerationExtensions e2 = new EnumerationExtensions(new ByteArrayInputStream(bos.toByteArray()));
        e2.setXmlBinding(binding);

        e2.prettyPrint(logfile);
        final Enumerate enu2 = e2.getEnumerate();
        assertEquals(expires, enu2.getExpires());
        assertEquals(endTo.getAddress().getValue(), enu2.getEndTo().getAddress().getValue());
        assertEquals(settings.getFilterDialect(), e2.getWsmanFilter().getDialect());
        assertEquals(settings.getFilter(), e2.getWsmanFilter().getContent().get(0));
        assertEquals(settings.getEnumerationMode(), e2.getMode());
    }

    public void testEnumerateItemCountEstimateVisual() throws Exception {

        final EnumerationExtensions enu = new EnumerationExtensions();
        enu.setXmlBinding(binding);
        enu.setAction(Enumeration.ENUMERATE_ACTION_URI);

        enu.setEnumerate(null, false, false, 0, null, null, null);

        enu.setRequestTotalItemsCountEstimate();

        enu.prettyPrint(logfile);

        final ByteArrayOutputStream bos = new ByteArrayOutputStream();
        enu.writeTo(bos);
        final EnumerationExtensions e2 = new EnumerationExtensions(new ByteArrayInputStream(bos.toByteArray()));
        e2.setXmlBinding(binding);

        assertNotNull(e2.getRequestTotalItemsCountEstimate());
    }

    public void testEnumerateTotalItemCountEstimateVisual() throws Exception {
        totalItemCountEstimateVisual(BigInteger.TEN);
        totalItemCountEstimateVisual(BigInteger.ONE);
        totalItemCountEstimateVisual(BigInteger.ZERO);
        // totalItemCountEstimateVisual(null);
    }

    /**
     * Test ability to return EPRs in an enumeration
     * rather than objects
     */
    public void testEnumerateEPR() throws Exception {
        enumerateTestWithEPRs(false);
    }


    /**
     * Test ability to return objects and their associated Endpoint References
     * in an enumeration
     */
    public void testEnumerateObjectAndEPR() throws Exception {
        enumerateTestWithEPRs(true);
    }

    public void totalItemCountEstimateVisual(final BigInteger itemCount) throws Exception {

        final EnumerationExtensions enu = new EnumerationExtensions();
        enu.setXmlBinding(binding);
        enu.setAction(Enumeration.ENUMERATE_RESPONSE_URI);

        enu.setEnumerateResponse(null, null, null, null, true);
        enu.setTotalItemsCountEstimate(itemCount);

        enu.prettyPrint(logfile);

        final ByteArrayOutputStream bos = new ByteArrayOutputStream();
        enu.writeTo(bos);
        final EnumerationExtensions e2 = new EnumerationExtensions(new ByteArrayInputStream(bos.toByteArray()));
        e2.setXmlBinding(binding);

        final AttributableNonNegativeInteger count = e2.getTotalItemsCountEstimate();
        assertNotNull(count);
        assertEquals(itemCount, count.getValue());
    }

    /**
     * Workhorse method for the enumeration test with EPRs
     * @param includeObjects determines whether the objects should be included
     * in addition to the EPRs themselves
     */
    private void enumerateTestWithEPRs(final boolean includeObjects) throws Exception {

        final String RESOURCE = "wsman:test/java/system/properties";
        // final String RESOURCE = "wsman:test/pull_source";
        // prepare the test

        EnumerationMessageValues settings = EnumerationMessageValues.newInstance();
    	settings.setEnumerationMessageActionType(Enumeration.ENUMERATE_ACTION_URI);
    	settings.setReplyTo(Addressing.ANONYMOUS_ENDPOINT_URI);
    	settings.setTo(DESTINATION);
    	settings.setResourceUri(RESOURCE);

    	if (includeObjects) {
    		settings.setEnumerationMode(EnumerationExtensions.Mode.EnumerateObjectAndEPR);
    	} else {
    		settings.setEnumerationMode(EnumerationExtensions.Mode.EnumerateEPR);

    	}
    	settings.setXmlBinding(binding);
    	settings.setExpires(60000);

    	final Enumeration enu = EnumerationUtility.buildMessage(null, settings);

        enu.prettyPrint(logfile);

        // retrieve the response
        final Addressing response = HttpClient.sendRequest(enu);
        response.setXmlBinding(binding);
        response.prettyPrint(logfile);
        if (response.getBody().hasFault()) {
            response.prettyPrint(System.err);
            // this test fails with an AccessDenied fault if the server is
            // running in the sun app server with a security manager in
            // place (the default), which disallows enumeration of
            // system properties
            fail(response.getBody().getFault().getFaultString());
        }

        // Prepare response objects
        final EnumerationExtensions enuResponse = new EnumerationExtensions(response);
        final EnumerateResponse enr = enuResponse.getEnumerateResponse();
        String context = (String) enr.getEnumerationContext().getContent().get(0);

        // walk the response
        boolean done = false;
        do {
        	settings.setEnumerationMessageActionType(Enumeration.PULL_ACTION_URI);
        	settings.setEnumerationContext(context);
        	settings.setMaxCharacters(0);
        	settings.setMaxElements(3);
        	settings.setTimeout(30000);

            // Set up a request to pull the next item of the enumeration

        	final Enumeration enuPull = EnumerationUtility.buildMessage(null, settings);
        	enuPull.prettyPrint(logfile);
            final Addressing praddr = HttpClient.sendRequest(enuPull);
            praddr.setXmlBinding(binding);
            praddr.prettyPrint(logfile);

            // Fail if response is an error
            if (praddr.getBody().hasFault()) {
                praddr.prettyPrint(System.err);
                fail(praddr.getBody().getFault().getFaultString());
            }

            // Check the response for appropriate EPRs
            final EnumerationExtensions pullResponse = new EnumerationExtensions(praddr);
            final PullResponse pr = pullResponse.getPullResponse();

            // update context for the next pull (if any)
            if (pr.getEnumerationContext() != null) {
                context = (String) pr.getEnumerationContext().getContent().get(0);
            }

            /*
             * The returned items are in one of two forms, if we are doing
             * an enumeration of the form EnumerateEPR, then every item will
             * be an EPR, as in:
             * <wsa:EndpointReference> ... </wsa:EndpointReference>
             *
             * If the enumeration is of the form EnumerateObjectAndEPR, then
             * every item will be of the form
             * <payloadobject>...</payloadobject>
             * <wsa:EndpointReference> ... </wsa:EndpointReference>
             * Basically, the EPRs will go right after the payload objects
             *
             * We will verify that the proper sequence of EPRs
             * are in place.  We will <i>not</i> validate that the EPRs are
             * usable
             */
            List<EnumerationItem> items = pullResponse.getItems();
            assertNotNull(items);
            final Iterator<EnumerationItem> itemsIterator = items.iterator();
            while (itemsIterator.hasNext()) {
                EnumerationItem item = itemsIterator.next();
                assertNotNull(item);
                if (includeObjects)
                   assertNotNull(item.getItem());
                else
                	assertNull(item.getItem());
                assertNotNull(item.getEndpointReference());
                final EndpointReferenceType epr = item.getEndpointReference();

                // validate that the element is an EndpointReference
                final String address = epr.getAddress().getValue();
                assertEquals(address, DESTINATION);
            }

            if (pr.getEndOfSequence() != null) {
                done = true;
            }
        } while (!done);
    }

    public void testOptimizedEnumeration() throws Exception {

    	final String resource = "wsman:test/java/system/properties";;

    	// first do the tests with EPRs turned off
        optimizedEnumerationTest(null, 2, resource, null, null, null);

        // do not specify MaxElements, letting it default to its implied value of 1
        optimizedEnumerationTest(null, -1, resource, null, null, null);

        // now repeat the same tests with EPRs turned on
        optimizedEnumerationTest(EnumerationExtensions.Mode.EnumerateObjectAndEPR, 2, resource, null, null, null);

        // do not specify MaxElements, letting it default to its implied value of 1
        optimizedEnumerationTest(EnumerationExtensions.Mode.EnumerateObjectAndEPR, -1, resource, null, null, null);

        // finally, repeat the same tests with only EPRs (no items)
        optimizedEnumerationTest(EnumerationExtensions.Mode.EnumerateEPR, 2, resource, null, null, null);

        // do not specify MaxElements, letting it default to its implied value of 1
        optimizedEnumerationTest(EnumerationExtensions.Mode.EnumerateEPR, -1, resource, null, null, null);
    }

    public void optimizedEnumerationTest(
    		final EnumerationExtensions.Mode mode,
            final int maxElements,
            final String resource,
    		final String dialect,
    		final String expression,
    		final Set<OptionType> options) throws Exception {


    	EnumerationMessageValues settings = EnumerationMessageValues.newInstance();
    	settings.setEnumerationMessageActionType(Enumeration.ENUMERATE_ACTION_URI);
    	settings.setReplyTo(Addressing.ANONYMOUS_ENDPOINT_URI);
    	settings.setTo(DESTINATION);
    	settings.setResourceUri(resource);
    	settings.setEnumerationMode(mode);
    	settings.setMaxElements(maxElements);
    	settings.setRequestForOptimizedEnumeration(true);
    	settings.setRequestForTotalItemsCount(true);
    	settings.setXmlBinding(binding);

        // Process any filter
         if ((expression != null) && (expression.length() > 0)) {
            if ((dialect != null) && (dialect.length() > 0))
            {
                settings.setFilterDialect(dialect);
            }
            settings.setFilter(expression);
         }

    	if (options != null) {
    		settings.setOptionSet(options);
    	}
    	final Enumeration enu = EnumerationUtility.buildMessage(null, settings);

        final Addressing response = HttpClient.sendRequest(enu);
        response.prettyPrint(logfile);
        if (response.getBody().hasFault()) {
            response.prettyPrint(System.err);
            // this test fails with an AccessDenied fault if the server is
            // running in the sun app server with a security manager in
            // place (the default), which disallows enumeration of
            // system properties
            fail(response.getBody().getFault().getFaultString());
        }

        final EnumerationExtensions enuResponse = new EnumerationExtensions(response);
        final EnumerateResponse enr = enuResponse.getEnumerateResponse();
        String context = (String) enr.getEnumerationContext().getContent().get(0);
        for (final EnumerationItem item : enuResponse.getItems()) {
            assertMode(mode, item);
        }

        final AttributableNonNegativeInteger ee = enuResponse.getTotalItemsCountEstimate();
        assertNotNull(ee);
        assertTrue(ee.getValue().intValue() > 0);

        boolean done = enuResponse.isEndOfSequence();
        while (!done) {

        	settings.setEnumerationMessageActionType(Enumeration.PULL_ACTION_URI);
        	settings.setEnumerationContext(context);
        	settings.setMaxCharacters(0);
        	settings.setMaxElements(3);
        	settings.setTimeout(30000);
        	settings.setTo(DESTINATION);
        	settings.setResourceUri(resource);
        	settings.setRequestForTotalItemsCount(true);

            // Set up a request to pull the next item of the enumeration

        	final Enumeration enuPull = EnumerationUtility.buildMessage(null, settings);

        	enuPull.prettyPrint(logfile);
            final Addressing praddr = HttpClient.sendRequest(enuPull);
            praddr.prettyPrint(logfile);
            if (praddr.getBody().hasFault()) {
                praddr.prettyPrint(System.err);
                fail(praddr.getBody().getFault().getFaultString());
            }

            final EnumerationExtensions pullResponse = new EnumerationExtensions(praddr);
            final PullResponse pr = pullResponse.getPullResponse();
            // update context for the next pull (if any)
            if (pr.getEnumerationContext() != null) {
                context = (String) pr.getEnumerationContext().getContent().get(0);
            }
            for (final EnumerationItem item : pullResponse.getItems()) {
                assertMode(mode, item);
            }
            if (pr.getEndOfSequence() != null) {
                done = true;
            }

            final AttributableNonNegativeInteger pe = pullResponse.getTotalItemsCountEstimate();
            assertNotNull(pe);
            assertTrue(pe.getValue().intValue() > 0);
        }
    }

    private static void assertMode(final EnumerationExtensions.Mode mode,
            final EnumerationItem item) {

        final Object elt = item.getItem();
        final EndpointReferenceType epr = item.getEndpointReference();

        if (mode == null) {
            assertNotNull(elt);
            assertNull(epr);
        } else if (EnumerationExtensions.Mode.EnumerateObjectAndEPR.equals(mode)) {
            assertNotNull(elt);
            assertNotNull(epr);
        } else if (EnumerationExtensions.Mode.EnumerateEPR.equals(mode)) {
            assertNull(elt);
            assertNotNull(epr);
        } else {
            fail("invalid mode");
        }
    }
       
    public void testOperationTimeout() throws Exception {
    	
    	EnumerationMessageValues settings = EnumerationMessageValues.newInstance();
    	settings.setEnumerationMessageActionType(Enumeration.ENUMERATE_ACTION_URI);
    	settings.setReplyTo(Addressing.ANONYMOUS_ENDPOINT_URI);
    	settings.setTo(DESTINATION);
    	settings.setResourceUri("wsman:test/timeout");
    	settings.setMaxElements(5);
    	settings.setRequestForOptimizedEnumeration(true);
    	settings.setRequestForTotalItemsCount(true);
    	settings.setTimeout(1000);
    	settings.setXmlBinding(binding);

    	final Enumeration request = EnumerationUtility.buildMessage(null, settings);

    	request.prettyPrint(logfile);
        final GregorianCalendar start = new GregorianCalendar();
        final Addressing response = HttpClient.sendRequest(request);
        final GregorianCalendar end = new GregorianCalendar();
        
        response.prettyPrint(logfile);
        if (response.getBody().hasFault() == false) {
            response.prettyPrint(System.err);
            fail("Expected TimedOutFault");
        }

        final Fault fault = response.getFault();
        assertEquals(SOAP.RECEIVER, fault.getCode().getValue());
        assertEquals(TimedOutFault.TIMED_OUT, fault.getCode().getSubcode().getValue());
        assertEquals(TimedOutFault.TIMED_OUT_REASON, fault.getReason().getText().get(0).getValue());
        
        long diff = end.getTimeInMillis()-start.getTimeInMillis();
        assertTrue("Timeout was " + diff + " milliseconds, expected 1000 milliseconds.",(diff >= 1000));
        assertTrue("Timeout was " + diff + " milliseconds, expected 1000 milliseconds.",(diff<= 3000));
    }
    
    public void testOperationTimeoutPull() throws Exception {
    	
    	EnumerationMessageValues settings = EnumerationMessageValues.newInstance();
    	settings.setEnumerationMessageActionType(Enumeration.ENUMERATE_ACTION_URI);
    	settings.setReplyTo(Addressing.ANONYMOUS_ENDPOINT_URI);
    	settings.setTo(DESTINATION);
    	settings.setResourceUri("wsman:test/timeout");
    	settings.setRequestForOptimizedEnumeration(false);
    	settings.setRequestForTotalItemsCount(true);
    	settings.setTimeout(2000);
    	settings.setXmlBinding(binding);

    	final Enumeration request = EnumerationUtility.buildMessage(null, settings);

    	request.prettyPrint(logfile);
        Addressing response = HttpClient.sendRequest(request);
        
        response.prettyPrint(logfile);
        
        if (response.getBody().hasFault() == true) {
            response.prettyPrint(System.err);
            fail(response.getBody().getFault().getFaultString());
        }
        
        final EnumerationExtensions enuResponse = new EnumerationExtensions(response);
        final EnumerateResponse enr = enuResponse.getEnumerateResponse();
        
        boolean done = enuResponse.isEndOfSequence();
        assertFalse("EOS was unexpectedly set.", done);
        
        EnumerationContextType ctx = enr.getEnumerationContext();
        assertNotNull("Enumeration Context was not set.", ctx);
        String context = (String) ctx.getContent().get(0);
        
    	settings.setEnumerationMessageActionType(Enumeration.PULL_ACTION_URI);
    	settings.setEnumerationContext(context);
    	settings.setMaxElements(10);
    	settings.setTimeout(5100);
    	settings.setTo(DESTINATION);
    	settings.setResourceUri("wsman:test/timeout");
    	settings.setRequestForTotalItemsCount(true);

        // Set up a request to pull the next item of the enumeration
    	Enumeration enuPull = EnumerationUtility.buildMessage(null, settings);

    	enuPull.prettyPrint(logfile);
        GregorianCalendar start = new GregorianCalendar();
        response = HttpClient.sendRequest(enuPull);
        GregorianCalendar end = new GregorianCalendar();
        
        response.setXmlBinding(binding);
        response.prettyPrint(logfile);
        if (response.getBody().hasFault() == false) {
            response.prettyPrint(System.err);
            fail("Expected TimedOutFault");
        }
		final Fault fault = response.getFault();
		assertEquals(SOAP.RECEIVER, fault.getCode().getValue());
		assertEquals(TimedOutFault.TIMED_OUT, fault.getCode().getSubcode().getValue());
		assertEquals(TimedOutFault.TIMED_OUT_REASON, fault.getReason().getText().get(0).getValue());
        
        long diff = end.getTimeInMillis() - start.getTimeInMillis();
        assertTrue("Timeout was " + diff + " milliseconds, expected 5100 milliseconds.",(diff >= 5100));
        assertTrue("Timeout was " + diff + " milliseconds, expected 5100 milliseconds.",(diff <= 7000));
        
        // Try the pull again, but with more time. We should get all 10 items.
        settings.setTimeout(6000);
    	enuPull = EnumerationUtility.buildMessage(null, settings);

    	enuPull.prettyPrint(logfile);
        start = new GregorianCalendar();
        response = HttpClient.sendRequest(enuPull);
        end = new GregorianCalendar();

        response.setXmlBinding(binding);
        response.prettyPrint(logfile);
        
        // We don't expect a timeout here
        if (response.getBody().hasFault()) {
        	response.prettyPrint(System.err);
            fail(response.getBody().getFault().getFaultString());
        }

        final EnumerationExtensions pullResponse = new EnumerationExtensions(response);
        final PullResponse pr = pullResponse.getPullResponse();
        // Check if more items are available. should only be 10.
        assertTrue("Expected EOS missing.", (pr.getEndOfSequence() != null));
        assertTrue("Unexpected context returned.", (pr.getEnumerationContext() == null));
        assertEquals("Expected number of items received.", 10, pullResponse.getItems().size());

        final AttributableNonNegativeInteger pe = pullResponse.getTotalItemsCountEstimate();
        assertNotNull(pe);
        assertEquals("Expected TotalItemsCountEstimate", 10, pe.getValue().intValue());

    }
}