//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.0-b11-EA 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2005.06.29 at 11:33:17 PDT 
//


package org.xmlsoap.schemas.ws._2004._09.enumeration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.bind.annotation.AccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import com.sun.xml.bind.annotation.W3CDomHandler;
import com.sun.xml.bind.annotation.XmlAnyAttribute;
import com.sun.xml.bind.annotation.XmlAnyElement;
import org.xmlsoap.schemas.ws._2004._08.addressing.EndpointReferenceType;
import org.xmlsoap.schemas.ws._2004._09.enumeration.FilterType;

@XmlAccessorType(value = AccessType.FIELD)
@XmlType(name = "", propOrder = {
    "endTo",
    "expires",
    "filter",
    "any"
})
@XmlRootElement(name = "Enumerate", namespace = "http://schemas.xmlsoap.org/ws/2004/09/enumeration")
public class Enumerate {

    @XmlElement(name = "EndTo", namespace = "http://schemas.xmlsoap.org/ws/2004/09/enumeration", type = EndpointReferenceType.class)
    protected EndpointReferenceType endTo;
    @XmlElement(name = "Expires", namespace = "http://schemas.xmlsoap.org/ws/2004/09/enumeration", type = String.class)
    protected String expires;
    @XmlElement(name = "Filter", namespace = "http://schemas.xmlsoap.org/ws/2004/09/enumeration", type = FilterType.class)
    protected FilterType filter;
    @XmlAnyElement(lax = true, value = W3CDomHandler.class)
    protected List<Object> any;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    /**
     * Gets the value of the endTo property.
     * 
     * @return
     *     possible object is
     *     {@link org.xmlsoap.schemas.ws._2004._08.addressing.EndpointReferenceType}
     */
    public EndpointReferenceType getEndTo() {
        return endTo;
    }

    /**
     * Sets the value of the endTo property.
     * 
     * @param value
     *     allowed object is
     *     {@link org.xmlsoap.schemas.ws._2004._08.addressing.EndpointReferenceType}
     */
    public void setEndTo(EndpointReferenceType value) {
        this.endTo = value;
    }

    /**
     * Gets the value of the expires property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String}
     */
    public String getExpires() {
        return expires;
    }

    /**
     * Sets the value of the expires property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String}
     */
    public void setExpires(String value) {
        this.expires = value;
    }

    /**
     * Gets the value of the filter property.
     * 
     * @return
     *     possible object is
     *     {@link org.xmlsoap.schemas.ws._2004._09.enumeration.FilterType}
     */
    public FilterType getFilter() {
        return filter;
    }

    /**
     * Sets the value of the filter property.
     * 
     * @param value
     *     allowed object is
     *     {@link org.xmlsoap.schemas.ws._2004._09.enumeration.FilterType}
     */
    public void setFilter(FilterType value) {
        this.filter = value;
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
     * {@link org.w3c.dom.Element}
     * {@link java.lang.Object}
     * 
     */
    public List<Object> getAny() {
        return this._getAny();
    }

    public Map<QName, String> getOtherAttributes() {
        return otherAttributes;
    }

}
