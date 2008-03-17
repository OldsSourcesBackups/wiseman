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
 **Revision 1.16  2007/11/30 14:32:37  denis_rachal
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
 **Revision 1.15  2007/09/18 20:08:55  nbeers
 **Add support for SOAP with attachments.  Issue #136.
 **
 **Revision 1.14  2007/05/30 20:31:05  nbeers
 **Add HP copyright header
 **
 **
 * $Id: Message.java,v 1.16.8.1 2008-03-17 07:24:37 denis_rachal Exp $
 */

package com.sun.ws.management;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.bind.JAXBException;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.AttachmentPart;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
import com.sun.ws.management.addressing.Addressing;
import com.sun.ws.management.enumeration.Enumeration;
import com.sun.ws.management.eventing.Eventing;
import com.sun.ws.management.server.WSManAgent;
import com.sun.ws.management.server.message.WSMessageStatus;
import com.sun.ws.management.soap.FaultException;
import com.sun.ws.management.soap.SOAP;
import com.sun.ws.management.transfer.Transfer;
import com.sun.ws.management.transport.ContentType;
import com.sun.ws.management.xml.XMLSchema;

public abstract class Message {

    public static final String COLON = ":";

    private static final String UNINITIALIZED = "uninitialized";

    private static MessageFactory msgFactory = null;
    private static DocumentBuilderFactory docFactory = null;
    private static DocumentBuilder db = null;

    private ContentType contentType = null;

    private final SOAPMessage msg;
    private SOAPEnvelope env = null;
    private SOAPHeader hdr = null;
    private SOAPBody body = null;
    
    private WSMessageStatus status = new WSMessageStatus();
    
    private ArrayList<AttachmentPart> attachments = null;

    private static Map<String,String> extensionNamespaces =null;

    static {
        try {
            msgFactory = MessageFactory.
                    newInstance(SOAPConstants.SOAP_1_2_PROTOCOL);
            docFactory = DocumentBuilderFactory.newInstance();
            docFactory.setNamespaceAware(true);

            db = docFactory.newDocumentBuilder();
        } catch (Exception pex) {
            pex.printStackTrace();
            throw new RuntimeException("Message static Initialization failed "
                    + pex);
        }
    }

    public static Document newDocument() {
        return db.newDocument();
    }

    public static DocumentBuilder getDocumentBuilder() {
        return db;
    }

    public static Element createElement(final Document doc, final QName qname) {
        return doc.createElementNS(qname.getNamespaceURI(),
                qname.getPrefix() + COLON + qname.getLocalPart());
    }

    public static void setAttribute(final Element element, final QName qname,
            final String value) {
        element.setAttributeNS(qname.getNamespaceURI(),
                qname.getPrefix() + COLON + qname.getLocalPart(), value);
    }

    public Message() throws SOAPException {
        assert msgFactory != null : UNINITIALIZED;
        contentType = ContentType.DEFAULT_CONTENT_TYPE;
        msg = msgFactory.createMessage();
        init();
        addNamespaceDeclarations();
    }

    public Message(final Message message) throws SOAPException {
        assert msgFactory != null : UNINITIALIZED;
        contentType = message.contentType;
        msg = message.msg;
        status = message.getMessageStatus();
        init();
    }

    public Message(final InputStream is) throws SOAPException, IOException {
        assert msgFactory != null : UNINITIALIZED;
        contentType = ContentType.DEFAULT_CONTENT_TYPE;
        MimeHeaders inputSoapMimeHeader = new MimeHeaders();
        inputSoapMimeHeader.setHeader("Content-Type",
        							  "application/soap+xml");
        msg = msgFactory.createMessage(inputSoapMimeHeader, is);
        init();
    }

    public Message(final InputStream is, String inContentType) throws SOAPException, IOException {
        assert msgFactory != null : UNINITIALIZED;
        contentType = ContentType.DEFAULT_CONTENT_TYPE;
        MimeHeaders inputSoapMimeHeader = new MimeHeaders();
        inputSoapMimeHeader.setHeader("Content-Type", inContentType);
        msg = msgFactory.createMessage(inputSoapMimeHeader, is);
        init();
    }
    public Message(final SOAPMessage message) throws SOAPException {
        assert msgFactory != null : UNINITIALIZED;
        String contentT = (String) message.getProperty(SOAPMessage.CHARACTER_SET_ENCODING);
        contentType = contentT == null ? ContentType.DEFAULT_CONTENT_TYPE :
            ContentType.createFromEncoding(contentT);
        msg = message;
        init();
    }

    public ContentType getContentType() {
        return contentType;
    }

    public void setContentType(final ContentType ct) throws SOAPException {
        contentType = ct;
        msg.setProperty(SOAPMessage.CHARACTER_SET_ENCODING, contentType.getEncoding());
        msg.getMimeHeaders().setHeader("Content-Type", contentType.getMimeType());
    }

    public abstract void validate() throws SOAPException, JAXBException, FaultException;

    public void writeTo(final OutputStream os) throws SOAPException, IOException {
        msg.writeTo(os);
    }

    public void prettyPrint(final OutputStream os)
    throws SOAPException, ParserConfigurationException, SAXException, IOException {
        assert db != null : UNINITIALIZED;
        final ByteArrayOutputStream bos = new ByteArrayOutputStream();
        msg.writeTo(bos);
        final byte[] content = bos.toByteArray();
        final ByteArrayInputStream bis = new ByteArrayInputStream(content);
        final Document doc = db.parse(bis);
        final OutputFormat format = new OutputFormat(doc);
        format.setLineWidth(72);
        format.setIndenting(true);
        format.setIndent(2);
        final XMLSerializer serializer = new XMLSerializer(os, format);
        serializer.serialize(doc);
        os.write("\n".getBytes());
    }

    public String toString() {
        final ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            prettyPrint(bos);
        } catch (Exception ex) {
            return null;
        }
        return new String(bos.toByteArray());
    }

    private void init() throws SOAPException {
        assert msg != null : UNINITIALIZED;
        final SOAPPart soap = msg.getSOAPPart();
        hdr = msg.getSOAPHeader();
        env = soap.getEnvelope();
        body = msg.getSOAPBody();
        
		Iterator iterator = msg.getAttachments();
		attachments = new ArrayList<AttachmentPart>();
		while (iterator.hasNext()) {
			attachments.add(((AttachmentPart)(iterator.next())));
		}
		
    }

    private void addNamespaceDeclarations() throws SOAPException {
        // having all the namespace declarations in the envelope keeps
        // JAXB from putting these on every element
        env.addNamespaceDeclaration(XMLSchema.NS_PREFIX, XMLSchema.NS_URI);
        env.addNamespaceDeclaration(SOAP.NS_PREFIX, SOAP.NS_URI);
        env.addNamespaceDeclaration(Addressing.NS_PREFIX, Addressing.NS_URI);
        env.addNamespaceDeclaration(Eventing.NS_PREFIX, Eventing.NS_URI);
        env.addNamespaceDeclaration(Enumeration.NS_PREFIX, Enumeration.NS_URI);
        env.addNamespaceDeclaration(Transfer.NS_PREFIX, Transfer.NS_URI);
        env.addNamespaceDeclaration(Management.NS_PREFIX, Management.NS_URI);

        //Check to see if there are additional namespaces to add
        try{
          if(extensionNamespaces ==null){
//        	  Map<String,String> extensionNamespaces =
		   extensionNamespaces =
			WSManAgent.locateExtensionNamespaces();
          }
         if((extensionNamespaces!=null)&&(extensionNamespaces.size()>0)){
          for(String key: extensionNamespaces.keySet()){
           env.addNamespaceDeclaration(key.trim(), extensionNamespaces.get(key).trim());
          }
         }
        }catch(Exception ex){
          ex.printStackTrace();
          //Eat the exception so as not to take down Message instantiation.
        }
    }

    public void addNamespaceDeclarations(final Map<String, String> ns) throws SOAPException {
        final Iterator<Entry<String, String> > ni = ns.entrySet().iterator();
        while (ni.hasNext()) {
            final Entry<String, String> entry = ni.next();
            env.addNamespaceDeclaration(entry.getKey(), entry.getValue());
        }
    }

    public SOAPMessage getMessage() {
        return msg;
    }

    public SOAPEnvelope getEnvelope() {
        return env;
    }

    public SOAPHeader getHeader() {
        return hdr;
    }

    public SOAPBody getBody() {
        return body;
    }

	public ArrayList<AttachmentPart> getAttachments() {
		return attachments;
	}
	
	public AttachmentPart getAttachment(String contentId) {
		AttachmentPart part = null;
		
		Iterator iterator = attachments.iterator();
		while (iterator.hasNext() && part == null) {
			AttachmentPart attachment = (AttachmentPart)iterator.next();
			String id = attachment.getContentId();
			if (contentId.equals(id)) {
				part = attachment;
			}
		}
		return part;
	}
	
    public void setMessageStatus(final WSMessageStatus status) {
        this.status = status;
    }
    
    public WSMessageStatus getMessageStatus() {
    	return this.status;
    }
}
