//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.0-06/22/2005 01:29 PM(ryans)-EA2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2005.10.27 at 11:49:42 AM PDT 
//


package org.xmlsoap.schemas.ws._2004._09.enumeration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.bind.annotation.AccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import org.w3c.dom.Element;
import org.xmlsoap.schemas.ws._2004._09.enumeration.EnumerationContextType;


/**
 * Java class for EnumerateResponse element declaration.
 *  <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;element name="EnumerateResponse">
 *   &lt;complexType>
 *     &lt;complexContent>
 *       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *         &lt;sequence>
 *           &lt;element name="Expires" type="{http://schemas.xmlsoap.org/ws/2004/09/enumeration}ExpirationType" minOccurs="0"/>
 *           &lt;element name="EnumerationContext" type="{http://schemas.xmlsoap.org/ws/2004/09/enumeration}EnumerationContextType"/>
 *           &lt;any/>
 *         &lt;/sequence>
 *       &lt;/restriction>
 *     &lt;/complexContent>
 *   &lt;/complexType>
 * &lt;/element>
 * </pre>
 * 
 */
@XmlAccessorType(AccessType.FIELD)
@XmlType(name = "", propOrder = {
    "expires",
    "enumerationContext",
    "any"
})
@XmlRootElement(name = "EnumerateResponse")
public class EnumerateResponse {

    @XmlElement(name = "Expires", namespace = "http://schemas.xmlsoap.org/ws/2004/09/enumeration")
    protected String expires;
    @XmlElement(name = "EnumerationContext", namespace = "http://schemas.xmlsoap.org/ws/2004/09/enumeration")
    protected EnumerationContextType enumerationContext;
    @XmlAnyElement(lax = true)
    protected List<Object> any;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    /**
     * Gets the value of the expires property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExpires() {
        return expires;
    }

    /**
     * Sets the value of the expires property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExpires(String value) {
        this.expires = value;
    }

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

    protected List<Object> _getAny() {
        if (any == null) {
            any = new ArrayList<Object>();
        }
        return any;
    }

    /**
     * Gets the value of the any property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the any property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAny().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Element }
     * {@link Object }
     * 
     * 
     */
    public List<Object> getAny() {
        return this._getAny();
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
