//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.0-b52-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2006.05.09 at 04:32:30 PM PDT 
//


package org.xmlsoap.schemas.ws._2004._09.enumeration;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.Duration;
import javax.xml.namespace.QName;
import org.w3c.dom.Element;


/**
 * <p>Java class for Pull element declaration.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;element name="Pull">
 *   &lt;complexType>
 *     &lt;complexContent>
 *       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *         &lt;sequence>
 *           &lt;element name="EnumerationContext" type="{http://schemas.xmlsoap.org/ws/2004/09/enumeration}EnumerationContextType"/>
 *           &lt;element name="MaxTime" type="{http://schemas.xmlsoap.org/ws/2004/09/enumeration}PositiveDurationType" minOccurs="0"/>
 *           &lt;element name="MaxElements" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" minOccurs="0"/>
 *           &lt;element name="MaxCharacters" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" minOccurs="0"/>
 *           &lt;any/>
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
    "enumerationContext",
    "maxTime",
    "maxElements",
    "maxCharacters",
    "any"
})
@XmlRootElement(name = "Pull")
public class Pull {

    @XmlElement(name = "EnumerationContext", namespace = "http://schemas.xmlsoap.org/ws/2004/09/enumeration", required = true)
    protected EnumerationContextType enumerationContext;
    @XmlElement(name = "MaxTime", namespace = "http://schemas.xmlsoap.org/ws/2004/09/enumeration")
    protected Duration maxTime;
    @XmlElement(name = "MaxElements", namespace = "http://schemas.xmlsoap.org/ws/2004/09/enumeration")
    protected BigInteger maxElements;
    @XmlElement(name = "MaxCharacters", namespace = "http://schemas.xmlsoap.org/ws/2004/09/enumeration")
    protected BigInteger maxCharacters;
    @XmlAnyElement(lax = true)
    protected List<Object> any;
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
     * Gets the value of the maxTime property.
     * 
     * @return
     *     possible object is
     *     {@link Duration }
     *     
     */
    public Duration getMaxTime() {
        return maxTime;
    }

    /**
     * Sets the value of the maxTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link Duration }
     *     
     */
    public void setMaxTime(Duration value) {
        this.maxTime = value;
    }

    /**
     * Gets the value of the maxElements property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getMaxElements() {
        return maxElements;
    }

    /**
     * Sets the value of the maxElements property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setMaxElements(BigInteger value) {
        this.maxElements = value;
    }

    /**
     * Gets the value of the maxCharacters property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getMaxCharacters() {
        return maxCharacters;
    }

    /**
     * Sets the value of the maxCharacters property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setMaxCharacters(BigInteger value) {
        this.maxCharacters = value;
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
        if (any == null) {
            any = new ArrayList<Object>();
        }
        return this.any;
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
