//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.0-06/22/2005 01:29 PM(ryans)-EA2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2005.10.27 at 11:49:42 AM PDT 
//


package org.xmlsoap.schemas.ws._2004._08.addressing;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;
import org.xmlsoap.schemas.ws._2004._08.addressing.AttributedQName;
import org.xmlsoap.schemas.ws._2004._08.addressing.AttributedURI;
import org.xmlsoap.schemas.ws._2004._08.addressing.EndpointReferenceType;
import org.xmlsoap.schemas.ws._2004._08.addressing.ReferenceParametersType;
import org.xmlsoap.schemas.ws._2004._08.addressing.ReferencePropertiesType;
import org.xmlsoap.schemas.ws._2004._08.addressing.Relationship;
import org.xmlsoap.schemas.ws._2004._08.addressing.ReplyAfterType;
import org.xmlsoap.schemas.ws._2004._08.addressing.ServiceNameType;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.xmlsoap.schemas.ws._2004._08.addressing package. 
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

    private final static QName _EndpointReference_QNAME = new QName("http://schemas.xmlsoap.org/ws/2004/08/addressing", "EndpointReference");
    private final static QName _ReplyAfter_QNAME = new QName("http://schemas.xmlsoap.org/ws/2004/08/addressing", "ReplyAfter");
    private final static QName _RelatesTo_QNAME = new QName("http://schemas.xmlsoap.org/ws/2004/08/addressing", "RelatesTo");
    private final static QName _MessageID_QNAME = new QName("http://schemas.xmlsoap.org/ws/2004/08/addressing", "MessageID");
    private final static QName _FaultTo_QNAME = new QName("http://schemas.xmlsoap.org/ws/2004/08/addressing", "FaultTo");
    private final static QName _From_QNAME = new QName("http://schemas.xmlsoap.org/ws/2004/08/addressing", "From");
    private final static QName _ReplyTo_QNAME = new QName("http://schemas.xmlsoap.org/ws/2004/08/addressing", "ReplyTo");
    private final static QName _Action_QNAME = new QName("http://schemas.xmlsoap.org/ws/2004/08/addressing", "Action");
    private final static QName _To_QNAME = new QName("http://schemas.xmlsoap.org/ws/2004/08/addressing", "To");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.xmlsoap.schemas.ws._2004._08.addressing
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ReferencePropertiesType }
     * 
     */
    public ReferencePropertiesType createReferencePropertiesType() {
        return new ReferencePropertiesType();
    }

    /**
     * Create an instance of {@link ReplyAfterType }
     * 
     */
    public ReplyAfterType createReplyAfterType() {
        return new ReplyAfterType();
    }

    /**
     * Create an instance of {@link ReferenceParametersType }
     * 
     */
    public ReferenceParametersType createReferenceParametersType() {
        return new ReferenceParametersType();
    }

    /**
     * Create an instance of {@link EndpointReferenceType }
     * 
     */
    public EndpointReferenceType createEndpointReferenceType() {
        return new EndpointReferenceType();
    }

    /**
     * Create an instance of {@link AttributedQName }
     * 
     */
    public AttributedQName createAttributedQName() {
        return new AttributedQName();
    }

    /**
     * Create an instance of {@link AttributedURI }
     * 
     */
    public AttributedURI createAttributedURI() {
        return new AttributedURI();
    }

    /**
     * Create an instance of {@link ServiceNameType }
     * 
     */
    public ServiceNameType createServiceNameType() {
        return new ServiceNameType();
    }

    /**
     * Create an instance of {@link Relationship }
     * 
     */
    public Relationship createRelationship() {
        return new Relationship();
    }

    /**
     * Create an instance of {@link JAXBElement<EndpointReferenceType> }}
     * 
     */
    @XmlElementDecl(name = "EndpointReference", namespace = "http://schemas.xmlsoap.org/ws/2004/08/addressing")
    public JAXBElement<EndpointReferenceType> createEndpointReference(EndpointReferenceType value) {
        return new JAXBElement<EndpointReferenceType>(_EndpointReference_QNAME, ((Class) EndpointReferenceType.class), null, value);
    }

    /**
     * Create an instance of {@link JAXBElement<ReplyAfterType> }}
     * 
     */
    @XmlElementDecl(name = "ReplyAfter", namespace = "http://schemas.xmlsoap.org/ws/2004/08/addressing")
    public JAXBElement<ReplyAfterType> createReplyAfter(ReplyAfterType value) {
        return new JAXBElement<ReplyAfterType>(_ReplyAfter_QNAME, ((Class) ReplyAfterType.class), null, value);
    }

    /**
     * Create an instance of {@link JAXBElement<Relationship> }}
     * 
     */
    @XmlElementDecl(name = "RelatesTo", namespace = "http://schemas.xmlsoap.org/ws/2004/08/addressing")
    public JAXBElement<Relationship> createRelatesTo(Relationship value) {
        return new JAXBElement<Relationship>(_RelatesTo_QNAME, ((Class) Relationship.class), null, value);
    }

    /**
     * Create an instance of {@link JAXBElement<AttributedURI> }}
     * 
     */
    @XmlElementDecl(name = "MessageID", namespace = "http://schemas.xmlsoap.org/ws/2004/08/addressing")
    public JAXBElement<AttributedURI> createMessageID(AttributedURI value) {
        return new JAXBElement<AttributedURI>(_MessageID_QNAME, ((Class) AttributedURI.class), null, value);
    }

    /**
     * Create an instance of {@link JAXBElement<EndpointReferenceType> }}
     * 
     */
    @XmlElementDecl(name = "FaultTo", namespace = "http://schemas.xmlsoap.org/ws/2004/08/addressing")
    public JAXBElement<EndpointReferenceType> createFaultTo(EndpointReferenceType value) {
        return new JAXBElement<EndpointReferenceType>(_FaultTo_QNAME, ((Class) EndpointReferenceType.class), null, value);
    }

    /**
     * Create an instance of {@link JAXBElement<EndpointReferenceType> }}
     * 
     */
    @XmlElementDecl(name = "From", namespace = "http://schemas.xmlsoap.org/ws/2004/08/addressing")
    public JAXBElement<EndpointReferenceType> createFrom(EndpointReferenceType value) {
        return new JAXBElement<EndpointReferenceType>(_From_QNAME, ((Class) EndpointReferenceType.class), null, value);
    }

    /**
     * Create an instance of {@link JAXBElement<EndpointReferenceType> }}
     * 
     */
    @XmlElementDecl(name = "ReplyTo", namespace = "http://schemas.xmlsoap.org/ws/2004/08/addressing")
    public JAXBElement<EndpointReferenceType> createReplyTo(EndpointReferenceType value) {
        return new JAXBElement<EndpointReferenceType>(_ReplyTo_QNAME, ((Class) EndpointReferenceType.class), null, value);
    }

    /**
     * Create an instance of {@link JAXBElement<AttributedURI> }}
     * 
     */
    @XmlElementDecl(name = "Action", namespace = "http://schemas.xmlsoap.org/ws/2004/08/addressing")
    public JAXBElement<AttributedURI> createAction(AttributedURI value) {
        return new JAXBElement<AttributedURI>(_Action_QNAME, ((Class) AttributedURI.class), null, value);
    }

    /**
     * Create an instance of {@link JAXBElement<AttributedURI> }}
     * 
     */
    @XmlElementDecl(name = "To", namespace = "http://schemas.xmlsoap.org/ws/2004/08/addressing")
    public JAXBElement<AttributedURI> createTo(AttributedURI value) {
        return new JAXBElement<AttributedURI>(_To_QNAME, ((Class) AttributedURI.class), null, value);
    }

}
