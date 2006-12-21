//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.0-b52-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2006.05.09 at 04:32:30 PM PDT 
//


package org.xmlsoap.schemas.ws._2005._06.wsmancat;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Catalog element declaration.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;element name="Catalog">
 *   &lt;complexType>
 *     &lt;complexContent>
 *       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *         &lt;sequence>
 *           &lt;element name="Resource" type="{http://schemas.xmlsoap.org/ws/2005/06/wsmancat}ResourceType" maxOccurs="unbounded" minOccurs="0"/>
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
    "resource"
})
@XmlRootElement(name = "Catalog")
public class Catalog {

    @XmlElement(name = "Resource", namespace = "http://schemas.xmlsoap.org/ws/2005/06/wsmancat", required = true)
    protected List<ResourceType> resource;

    /**
     * Gets the value of the resource property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the resource property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getResource().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ResourceType }
     * 
     * 
     */
    public List<ResourceType> getResource() {
        if (resource == null) {
            resource = new ArrayList<ResourceType>();
        }
        return this.resource;
    }

}