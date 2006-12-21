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
 * $Id: EventingExtensionsTest.java,v 1.8 2006-05-03 19:35:46 akhilarora Exp $
 */

package management;

import com.sun.ws.management.Management;
import com.sun.ws.management.addressing.Addressing;
import com.sun.ws.management.enumeration.Enumeration;
import com.sun.ws.management.eventing.Eventing;
import com.sun.ws.management.eventing.EventingExtensions;
import com.sun.ws.management.transport.HttpClient;
import com.sun.ws.management.xml.XPath;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.util.UUID;
import javax.xml.bind.JAXBElement;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.Duration;
import javax.xml.namespace.QName;
import org.dmtf.schemas.wbem.wsman._1.wsman.AttributableAny;
import org.dmtf.schemas.wbem.wsman._1.wsman.AttributableDuration;
import org.dmtf.schemas.wbem.wsman._1.wsman.AttributablePositiveInteger;
import org.dmtf.schemas.wbem.wsman._1.wsman.ConnectionRetryType;
import org.dmtf.schemas.wbem.wsman._1.wsman.MaxEnvelopeSizeType;
import org.dmtf.schemas.wbem.wsman._1.wsman.PolicyType;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xmlsoap.schemas.ws._2004._08.eventing.DeliveryType;
import org.xmlsoap.schemas.ws._2004._08.eventing.FilterType;
import org.xmlsoap.schemas.ws._2004._08.eventing.Subscribe;
import org.xmlsoap.schemas.ws._2004._08.eventing.SubscribeResponse;
import org.xmlsoap.schemas.ws._2004._09.enumeration.PullResponse;

/**
 * Unit test for WS-Eventing extensions in WS-Management
 */
public class EventingExtensionsTest extends TestBase {
    
    public EventingExtensionsTest(final String testName) {
        super(testName);
    }
    
    public static junit.framework.Test suite() {
        final junit.framework.TestSuite suite = new junit.framework.TestSuite(EventingExtensionsTest.class);
        return suite;
    }
    
    public void testSubscribeVisual() throws Exception {
        final EventingExtensions evtx = new EventingExtensions();
        evtx.setAction(Eventing.SUBSCRIBE_ACTION_URI);
        
        final ConnectionRetryType retry = new ConnectionRetryType();
        final int retryCount = 3;
        retry.setTotal(new BigInteger(Integer.toString(retryCount)));
        final Duration retryInterval = DatatypeFactory.newInstance().newDuration(5000);
        retry.setValue(retryInterval);
        
        final Duration heartbeatsInterval = DatatypeFactory.newInstance().newDuration(15000);
        
        final Boolean sendBookmarks = Boolean.TRUE;
        
        final AttributableAny bookmark = new AttributableAny();
        final Document bookmarkDoc = evtx.newDocument();
        final Element bookmarkElement = bookmarkDoc.createElement("bookmark-element");
        bookmarkElement.setTextContent("1234");
        bookmark.getAny().add(bookmarkElement);
        
        final MaxEnvelopeSizeType maxEnvelopeSize = new MaxEnvelopeSizeType();
        final int maxEnvSize = 1024;
        maxEnvelopeSize.setValue(new BigInteger(Integer.toString(maxEnvSize)));
        final PolicyType policy = PolicyType.fromValue(EventingExtensions.SKIP_POLICY);
        maxEnvelopeSize.setPolicy(policy);
        
        final Long maxElements = new Long(7);
        
        final Duration maxTime = DatatypeFactory.newInstance().newDuration(3000);
        
        evtx.setSubscribe(null, null, null, null, null,
                retry, heartbeatsInterval, sendBookmarks, bookmark,
                maxEnvelopeSize, maxElements, maxTime);
        
        evtx.prettyPrint(logfile);
        final ByteArrayOutputStream bos = new ByteArrayOutputStream();
        evtx.writeTo(bos);
        final EventingExtensions evtx2 = new EventingExtensions(new ByteArrayInputStream(bos.toByteArray()));
        
        final Subscribe sub2 = evtx2.getSubscribe();
        final DeliveryType delivery2 = sub2.getDelivery();
        
        final ConnectionRetryType retry2 = ((JAXBElement<ConnectionRetryType>) delivery2.getContent().get(0)).getValue();
        assertEquals(retryCount, retry2.getTotal().intValue());
        assertEquals(retryInterval, retry2.getValue());
        
        assertEquals(heartbeatsInterval, ((JAXBElement<AttributableDuration>) delivery2.getContent().get(1)).getValue().getValue());
        assertEquals(EventingExtensions.SEND_BOOKMARKS, ((JAXBElement<QName>) delivery2.getContent().get(2)).getName());
        
        final AttributableAny bookmark2 = ((JAXBElement<AttributableAny>) delivery2.getContent().get(3)).getValue();
        assertEquals(bookmark.getAny().size(), bookmark2.getAny().size());
        assertEquals(bookmarkElement.getNodeName(), ((Element) bookmark2.getAny().get(0)).getNodeName());
        assertEquals(bookmarkElement.getTextContent(), ((Element) bookmark2.getAny().get(0)).getTextContent());
        
        final MaxEnvelopeSizeType maxEnvelopeSize2 = ((JAXBElement<MaxEnvelopeSizeType>) delivery2.getContent().get(4)).getValue();
        assertEquals(maxEnvelopeSize.getValue(), maxEnvelopeSize2.getValue());
        assertEquals(maxEnvelopeSize.getPolicy(), maxEnvelopeSize2.getPolicy());
        
        assertEquals(maxElements.longValue(), ((JAXBElement<AttributablePositiveInteger>) delivery2.getContent().get(5)).getValue().getValue().longValue());
        assertEquals(maxTime, ((JAXBElement<AttributableDuration>) delivery2.getContent().get(6)).getValue().getValue());
    }
    
    public void testPullMode() throws Exception {
        pullModeTest(null);
        pullModeTest("/log:event3");
    }
    
    private void pullModeTest(final String filter) throws Exception {
        final EventingExtensions evtx = new EventingExtensions();
        evtx.setReplyTo(Addressing.ANONYMOUS_ENDPOINT_URI);
        evtx.setMessageId(UUID_SCHEME + UUID.randomUUID().toString());
        evtx.setTo(DESTINATION);
        evtx.setAction(Eventing.SUBSCRIBE_ACTION_URI);
        final FilterType filterType = Eventing.FACTORY.createFilterType();
        filterType.setDialect(XPath.NS_URI);
        filterType.getContent().add(filter);
        evtx.setSubscribe(null, EventingExtensions.PULL_DELIVERY_MODE, null, null,
                filter == null ? null : filterType);
        
        final Management mgmt = new Management(evtx);
        mgmt.setResourceURI("wsman:test/pull_source");
        
        mgmt.prettyPrint(logfile);
        Addressing response = HttpClient.sendRequest(mgmt);
        response.prettyPrint(logfile);
        if (response.getBody().hasFault()) {
            fail(response.getBody().getFault().getFaultString());
        }
        
        String context = null;
        final EventingExtensions evtx2 = new EventingExtensions(response);
        final SubscribeResponse subr = evtx2.getSubscribeResponse();
        for (final Object obj : subr.getAny()) {
            if (obj instanceof Element) {
                final Element contextElement = (Element) obj;
                if (Enumeration.ENUMERATION_CONTEXT.getLocalPart().equals(contextElement.getLocalName()) &&
                        Enumeration.ENUMERATION_CONTEXT.getNamespaceURI().equals(contextElement.getNamespaceURI())) {
                    context = contextElement.getTextContent();
                    break;
                }
            }
        }
        assertNotNull(context);
        
        boolean done = false;
        do {
            final Enumeration en = new Enumeration();
            en.setReplyTo(Addressing.ANONYMOUS_ENDPOINT_URI);
            en.setMessageId(UUID_SCHEME + UUID.randomUUID().toString());
            en.setTo(DESTINATION);
            en.setAction(Enumeration.PULL_ACTION_URI);
            final Duration pullDuration = DatatypeFactory.newInstance().newDuration(5000);
            en.setPull(context, -1, 2, pullDuration);
            
            final Management mgmt2 = new Management(en);
            mgmt2.setResourceURI("wsman:test/pull_source");
            
            mgmt2.prettyPrint(logfile);
            Addressing response2 = HttpClient.sendRequest(mgmt2);
            response2.prettyPrint(logfile);
            if (response2.getBody().hasFault()) {
                fail(response2.getBody().getFault().getFaultString());
            }
            
            final Enumeration pullResponse = new Enumeration(response2);
            final PullResponse pr = pullResponse.getPullResponse();
            // update context for the next pull (if any)
            if (pr.getEnumerationContext() != null) {
                context = (String) pr.getEnumerationContext().getContent().get(0);
            }
            for (Object obj : pr.getItems().getAny()) {
                final Element el = (Element) obj;
                // commented to reduce clutter: uncomment to see the output
                // System.out.println(el.getNodeName() + " = " + el.getTextContent());
            }
            if (pr.getEndOfSequence() != null) {
                done = true;
            }
        } while (!done);
    }
}
