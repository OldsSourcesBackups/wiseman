//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.0-b52-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2006.05.09 at 04:32:30 PM PDT 
//


package org.xmlsoap.schemas.ws._2004._09.transfer;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;
import org.xmlsoap.schemas.ws._2004._08.addressing.EndpointReferenceType;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.xmlsoap.schemas.ws._2004._09.transfer package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _ResourceCreated_QNAME = new QName("http://schemas.xmlsoap.org/ws/2004/09/transfer", "ResourceCreated");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.xmlsoap.schemas.ws._2004._09.transfer
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link AnyXmlType }
     * 
     */
    public AnyXmlType createAnyXmlType() {
        return new AnyXmlType();
    }

    /**
     * Create an instance of {@link AnyXmlOptionalType }
     * 
     */
    public AnyXmlOptionalType createAnyXmlOptionalType() {
        return new AnyXmlOptionalType();
    }

    /**
     * Create an instance of {@link CreateResponseType }
     * 
     */
    public CreateResponseType createCreateResponseType() {
        return new CreateResponseType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EndpointReferenceType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.xmlsoap.org/ws/2004/09/transfer", name = "ResourceCreated")
    public JAXBElement<EndpointReferenceType> createResourceCreated(EndpointReferenceType value) {
        return new JAXBElement<EndpointReferenceType>(_ResourceCreated_QNAME, EndpointReferenceType.class, null, value);
    }

}
