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
 * $Id: EnumerationExtensionsTest.java,v 1.3 2006-07-24 13:14:58 pmonday Exp $
 */

package management;

import com.sun.ws.management.addressing.Addressing;
import com.sun.ws.management.enumeration.EnumerationExtensions;
import com.sun.ws.management.enumeration.Enumeration;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import javax.xml.bind.JAXBElement;
import javax.xml.datatype.DatatypeFactory;
import org.dmtf.schemas.wbem.wsman._1.wsman.AttributableNonNegativeInteger;
import org.dmtf.schemas.wbem.wsman._1.wsman.EnumerationModeType;
import org.xmlsoap.schemas.ws._2004._08.addressing.EndpointReferenceType;
import org.xmlsoap.schemas.ws._2004._09.enumeration.Enumerate;
import org.xmlsoap.schemas.ws._2004._09.enumeration.FilterType;

/**
 * Unit test for WS-Enumeration extensions in WS-Management
 */
public class EnumerationExtensionsTest extends TestBase {
    
    public EnumerationExtensionsTest(final String testName) {
        super(testName);
    }
    
    public static junit.framework.Test suite() {
        final junit.framework.TestSuite suite = new junit.framework.TestSuite(EnumerationExtensionsTest.class);
        return suite;
    }
    
    public void testEnumerateVisual() throws Exception {
        
        final EnumerationExtensions enu = new EnumerationExtensions();
        enu.setAction(Enumeration.ENUMERATE_ACTION_URI);
        
        final EndpointReferenceType endTo = Addressing.createEndpointReference("http://host/endTo", null, null, null, null);
        final String expires = DatatypeFactory.newInstance().newDuration(300000).toString();
        final FilterType filter = Enumeration.FACTORY.createFilterType();
        filter.setDialect("http://mydomain/my.filter.dialect");
        filter.getContent().add("my/filter/expression");
        final EnumerationExtensions.Mode mode = EnumerationExtensions.Mode.EnumerateObjectAndEPR;
        enu.setEnumerate(endTo, expires, filter, mode);
        
        enu.prettyPrint(logfile);
        
        final ByteArrayOutputStream bos = new ByteArrayOutputStream();
        enu.writeTo(bos);
        final Enumeration e2 = new Enumeration(new ByteArrayInputStream(bos.toByteArray()));
        
        final Enumerate enu2 = e2.getEnumerate();
        assertEquals(expires, enu2.getExpires());
        assertEquals(endTo.getAddress().getValue(), enu2.getEndTo().getAddress().getValue());
        assertEquals(filter.getDialect(), enu2.getFilter().getDialect());
        assertEquals(filter.getContent().get(0), enu2.getFilter().getContent().get(0));
        final EnumerationExtensions.Mode mode2 = EnumerationExtensions.Mode.fromBinding((JAXBElement<EnumerationModeType>) enu2.getAny().get(0));
        assertEquals(mode, mode2);
    }

    public void testEnumerateItemCountEstimateVisual() throws Exception {
        
        final EnumerationExtensions enu = new EnumerationExtensions();
        enu.setAction(Enumeration.ENUMERATE_ACTION_URI);
        
        enu.setEnumerate(null, null, null);

        enu.setRequestTotalItemsCountEstimate();
        
        enu.prettyPrint(logfile);
        
        final ByteArrayOutputStream bos = new ByteArrayOutputStream();
        enu.writeTo(bos);
        final EnumerationExtensions e2 = new EnumerationExtensions(new ByteArrayInputStream(bos.toByteArray()));
        
        assertNotNull(e2.getRequestTotalItemsCountEstimate());
    }

    public void testEnumerateTotalItemCountEstimateVisual() throws Exception {
        totalItemCountEstimateVisual(BigInteger.TEN);
        totalItemCountEstimateVisual(BigInteger.ONE);
        totalItemCountEstimateVisual(BigInteger.ZERO);
        // TODO: does not work yet
        // totalItemCountEstimateVisual(null);
    }
    
    public void totalItemCountEstimateVisual(final BigInteger itemCount) throws Exception {
        
        final EnumerationExtensions enu = new EnumerationExtensions();
        enu.setAction(Enumeration.ENUMERATE_RESPONSE_URI);
        
        enu.setEnumerateResponse(null, null);
        enu.setTotalItemsCountEstimate(itemCount);
        
        enu.prettyPrint(logfile);
        
        final ByteArrayOutputStream bos = new ByteArrayOutputStream();
        enu.writeTo(bos);
        final EnumerationExtensions e2 = new EnumerationExtensions(new ByteArrayInputStream(bos.toByteArray()));
        
        final AttributableNonNegativeInteger count = e2.getTotalItemsCountEstimate();
        assertNotNull(count);
        assertEquals(itemCount, count.getValue());
    }
}