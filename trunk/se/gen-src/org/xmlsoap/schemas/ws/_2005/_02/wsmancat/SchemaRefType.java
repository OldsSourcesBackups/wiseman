//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.0-b11-EA 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2005.06.29 at 11:33:17 PDT 
//


package org.xmlsoap.schemas.ws._2005._02.wsmancat;

import javax.xml.bind.annotation.AccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.namespace.QName;

@XmlAccessorType(value = AccessType.FIELD)
@XmlType(name = "SchemaRefType", namespace = "http://schemas.xmlsoap.org/ws/2005/02/wsmancat")
public class SchemaRefType {

    @XmlValue
    protected QName value;
    @XmlAttribute(name = "Location", namespace = "")
    protected String location;

    /**
     * Gets the value of the value property.
     * 
     * @return
     *     possible object is
     *     {@link javax.xml.namespace.QName}
     */
    public QName getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     * 
     * @param value
     *     allowed object is
     *     {@link javax.xml.namespace.QName}
     */
    public void setValue(QName value) {
        this.value = value;
    }

    /**
     * Gets the value of the location property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String}
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets the value of the location property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String}
     */
    public void setLocation(String value) {
        this.location = value;
    }

}
