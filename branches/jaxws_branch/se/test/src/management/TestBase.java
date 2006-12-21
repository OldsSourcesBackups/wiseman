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
 * $Id: TestBase.java,v 1.9.2.1 2006-12-21 08:39:40 jfdenise Exp $
 */

package management;

import com.sun.ws.management.Message;
import com.sun.ws.management.soap.SOAP;
import junit.framework.*;
import com.sun.ws.management.transport.HttpClient;
import com.sun.ws.management.xml.XmlBinding;
import java.io.FileOutputStream;

/**
 * Base class for Unit tests
 */
public abstract class TestBase extends TestCase {
    
    public static final String UUID_SCHEME = "uuid:";
    public static final String DESTINATION =
            System.getProperty("wsman.dest", "http://localhost:8080/wsman/");
    
    protected static final String NS_URI = "http://schemas.company.com/model";
    protected static final String NS_PREFIX = "model";
    
    protected FileOutputStream logfile = null;
    
    public TestBase(final String testName) {
        super(testName);
    }
    
    protected void setUp() throws java.lang.Exception {
        logfile = new FileOutputStream(getClass().getCanonicalName() + "." +
                getName() + ".Output.xml");

        final String basicAuth = System.getProperty("wsman.basicauthentication");
        if ("true".equalsIgnoreCase(basicAuth)) {
            HttpClient.setAuthenticator(new transport.BasicAuthenticator());
        }
    }
    
    protected void tearDown() throws java.lang.Exception {
        logfile.close();
    }
    
    public void log(final Message msg) throws Exception {
        msg.prettyPrint(logfile);
    }
}