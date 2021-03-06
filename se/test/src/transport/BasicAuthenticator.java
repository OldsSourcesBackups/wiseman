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
 * $Id: BasicAuthenticator.java,v 1.2 2007-12-03 09:15:10 denis_rachal Exp $
 */

package transport;

import java.net.Authenticator;
import java.net.PasswordAuthentication;

public final class BasicAuthenticator extends Authenticator {
    
    protected PasswordAuthentication getPasswordAuthentication() {
        final String user = System.getProperty("wsman.user", "wsman");
        final String password = System.getProperty("wsman.password", "secret");
        return new PasswordAuthentication(user, password.toCharArray());
    }
}
