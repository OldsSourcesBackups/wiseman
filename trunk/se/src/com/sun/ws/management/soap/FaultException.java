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
 * $Id: FaultException.java,v 1.1 2005-06-29 19:18:25 akhilarora Exp $
 */

package com.sun.ws.management.soap;

import java.util.List;
import javax.xml.namespace.QName;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public abstract class FaultException extends RuntimeException {
    
    private final QName code;
    private final QName subcode;
    private final String reason;
    private final Node[] details;
    
    public FaultException(final QName code, final QName subcode,
            final String reason, final Node... details) {
        this.code = code;
        this.subcode = subcode;
        this.reason = reason;
        this.details = details;
    }
    
    public QName getCode() {
        return code;
    }
    
    public QName getSubcode() {
        return subcode;
    }
    
    public String getReason() {
        return reason;
    }
    
    public Node[] getDetails() {
        return details;
    }
    
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Fault: \n");
        sb.append(" Code: " + code + "\n");
        sb.append(" Subcode: " + (subcode == null ? "null" : subcode) + "\n");
        sb.append(" Reason: " + reason + "\n");
        if (details != null) {
            for (final Node node : details) {
                sb.append(" Detail: " + node.getNodeName() + "\n");
                final NodeList nl = node.getChildNodes();
                for (int i = 0; i < nl.getLength(); i++) {
                    final Node child = nl.item(i);
                    sb.append("  " + child.getTextContent() + "\n");
                }
            }
        }
        return sb.toString();
    }
}