//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.0-b52-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2006.05.09 at 04:32:30 PM PDT 
//


package org.xmlsoap.schemas.ws._2004._09.enumeration;

import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;


/**
 * <p>Java class for Release element declaration.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;element name="Release">
 *   &lt;complexType>
 *     &lt;complexContent>
 *       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *         &lt;sequence>
 *           &lt;element name="EnumerationContext" type="{http://schemas.xmlsoap.org/ws/2004/09/enumeration}EnumerationContextType"/>
 *         &lt;/sequence>
 *       &lt;/restriction>
 *     &lt;/complexContent>
 *   &lt;/complexType>
 * &lt;/element>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "enumerationContext"
})
@XmlRootElement(name = "Release")
public class Release {

    @XmlElement(name = "EnumerationContext", namespace = "http://schemas.xmlsoap.org/ws/2004/09/enumeration", required = true)
    protected EnumerationContextType enumerationContext;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    /**
     * Gets the value of the enumerationContext property.
     * 
     * @return
     *     possible object is
     *     {@link EnumerationContextType }
     *     
     */
    public EnumerationContextType getEnumerationContext() {
        return enumerationContext;
    }

    /**
     * Sets the value of the enumerationContext property.
     * 
     * @param value
     *     allowed object is
     *     {@link EnumerationContextType }
     *     
     */
    public void setEnumerationContext(EnumerationContextType value) {
        this.enumerationContext = value;
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