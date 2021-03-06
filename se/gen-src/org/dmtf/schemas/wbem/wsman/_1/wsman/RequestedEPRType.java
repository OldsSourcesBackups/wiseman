//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-382 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2007.05.29 at 01:59:34 PM EDT 
//


package org.dmtf.schemas.wbem.wsman._1.wsman;

import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import org.xmlsoap.schemas.ws._2004._08.addressing.EndpointReferenceType;


/**
 * <p>Java class for RequestedEPRType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RequestedEPRType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;element ref="{http://schemas.xmlsoap.org/ws/2004/08/addressing}EndpointReference"/>
 *         &lt;element ref="{http://schemas.dmtf.org/wbem/wsman/1/wsman.xsd}EPRInvalid"/>
 *         &lt;element ref="{http://schemas.dmtf.org/wbem/wsman/1/wsman.xsd}EPRUnknown"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RequestedEPRType", propOrder = {
    "endpointReference",
    "eprInvalid",
    "eprUnknown"
})
public class RequestedEPRType {

    @XmlElement(name = "EndpointReference", namespace = "http://schemas.xmlsoap.org/ws/2004/08/addressing")
    protected EndpointReferenceType endpointReference;
    @XmlElement(name = "EPRInvalid")
    protected AttributableEmpty eprInvalid;
    @XmlElement(name = "EPRUnknown")
    protected AttributableEmpty eprUnknown;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    /**
     * Gets the value of the endpointReference property.
     * 
     * @return
     *     possible object is
     *     {@link EndpointReferenceType }
     *     
     */
    public EndpointReferenceType getEndpointReference() {
        return endpointReference;
    }

    /**
     * Sets the value of the endpointReference property.
     * 
     * @param value
     *     allowed object is
     *     {@link EndpointReferenceType }
     *     
     */
    public void setEndpointReference(EndpointReferenceType value) {
        this.endpointReference = value;
    }

    /**
     * Gets the value of the eprInvalid property.
     * 
     * @return
     *     possible object is
     *     {@link AttributableEmpty }
     *     
     */
    public AttributableEmpty getEPRInvalid() {
        return eprInvalid;
    }

    /**
     * Sets the value of the eprInvalid property.
     * 
     * @param value
     *     allowed object is
     *     {@link AttributableEmpty }
     *     
     */
    public void setEPRInvalid(AttributableEmpty value) {
        this.eprInvalid = value;
    }

    /**
     * Gets the value of the eprUnknown property.
     * 
     * @return
     *     possible object is
     *     {@link AttributableEmpty }
     *     
     */
    public AttributableEmpty getEPRUnknown() {
        return eprUnknown;
    }

    /**
     * Sets the value of the eprUnknown property.
     * 
     * @param value
     *     allowed object is
     *     {@link AttributableEmpty }
     *     
     */
    public void setEPRUnknown(AttributableEmpty value) {
        this.eprUnknown = value;
    }

    /**
     * Gets a map that contains attributes that aren't bound to any typed property on this class.
     * 
     * <p>
     * the map is keyed by the name of the attribute and 
     * the value is the string value of the attribute.
     * 
     * the map returned by this method is live, and you can add new attribute
     * by updating the map directly. Because of this design, there's no setter.
     * 
     * 
     * @return
     *     always non-null
     */
    public Map<QName, String> getOtherAttributes() {
        return otherAttributes;
    }

}
