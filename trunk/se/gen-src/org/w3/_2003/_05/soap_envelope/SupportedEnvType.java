//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.0-b11-EA 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2005.06.29 at 11:33:17 PDT 
//


package org.w3._2003._05.soap_envelope;

import javax.xml.bind.annotation.AccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;

@XmlAccessorType(value = AccessType.FIELD)
@XmlType(name = "SupportedEnvType", namespace = "http://www.w3.org/2003/05/soap-envelope")
public class SupportedEnvType {

    @XmlAttribute(name = "qname", namespace = "", required = true)
    protected QName qname;

    /**
     * Gets the value of the qname property.
     * 
     * @return
     *     possible object is
     *     {@link javax.xml.namespace.QName}
     */
    public QName getQname() {
        return qname;
    }

    /**
     * Sets the value of the qname property.
     * 
     * @param value
     *     allowed object is
     *     {@link javax.xml.namespace.QName}
     */
    public void setQname(QName value) {
        this.qname = value;
    }

}
