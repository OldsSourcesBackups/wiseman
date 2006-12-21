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
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for ResourceType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ResourceType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ResourceURI" type="{http://www.w3.org/2001/XMLSchema}anyURI"/>
 *         &lt;element name="Notes" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Vendor" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DisplayName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Keywords" type="{http://schemas.xmlsoap.org/ws/2005/06/wsmancat}KeywordListType" minOccurs="0"/>
 *         &lt;element name="Access" type="{http://schemas.xmlsoap.org/ws/2005/06/wsmancat}AccessType" maxOccurs="unbounded"/>
 *         &lt;element name="Relationships" type="{http://schemas.xmlsoap.org/ws/2005/06/wsmancat}RelationshipListType" minOccurs="0"/>
 *         &lt;any/>
 *       &lt;/sequence>
 *       &lt;attribute name="lang" type="{http://www.w3.org/2001/XMLSchema}language" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ResourceType", propOrder = {
    "resourceURI",
    "notes",
    "vendor",
    "displayName",
    "keywords",
    "access",
    "relationships",
    "any"
})
public class ResourceType {

    @XmlElement(name = "ResourceURI", namespace = "http://schemas.xmlsoap.org/ws/2005/06/wsmancat", required = true)
    protected String resourceURI;
    @XmlElement(name = "Notes", namespace = "http://schemas.xmlsoap.org/ws/2005/06/wsmancat")
    protected String notes;
    @XmlElement(name = "Vendor", namespace = "http://schemas.xmlsoap.org/ws/2005/06/wsmancat")
    protected String vendor;
    @XmlElement(name = "DisplayName", namespace = "http://schemas.xmlsoap.org/ws/2005/06/wsmancat")
    protected String displayName;
    @XmlElement(name = "Keywords", namespace = "http://schemas.xmlsoap.org/ws/2005/06/wsmancat")
    protected KeywordListType keywords;
    @XmlElement(name = "Access", namespace = "http://schemas.xmlsoap.org/ws/2005/06/wsmancat", required = true)
    protected List<AccessType> access;
    @XmlElement(name = "Relationships", namespace = "http://schemas.xmlsoap.org/ws/2005/06/wsmancat")
    protected RelationshipListType relationships;
    @XmlAnyElement(lax = true)
    protected List<Object> any;
    @XmlAttribute
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String lang;

    /**
     * Gets the value of the resourceURI property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResourceURI() {
        return resourceURI;
    }

    /**
     * Sets the value of the resourceURI property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResourceURI(String value) {
        this.resourceURI = value;
    }

    /**
     * Gets the value of the notes property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNotes() {
        return notes;
    }

    /**
     * Sets the value of the notes property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNotes(String value) {
        this.notes = value;
    }

    /**
     * Gets the value of the vendor property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVendor() {
        return vendor;
    }

    /**
     * Sets the value of the vendor property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVendor(String value) {
        this.vendor = value;
    }

    /**
     * Gets the value of the displayName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Sets the value of the displayName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDisplayName(String value) {
        this.displayName = value;
    }

    /**
     * Gets the value of the keywords property.
     * 
     * @return
     *     possible object is
     *     {@link KeywordListType }
     *     
     */
    public KeywordListType getKeywords() {
        return keywords;
    }

    /**
     * Sets the value of the keywords property.
     * 
     * @param value
     *     allowed object is
     *     {@link KeywordListType }
     *     
     */
    public void setKeywords(KeywordListType value) {
        this.keywords = value;
    }

    /**
     * Gets the value of the access property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the access property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAccess().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AccessType }
     * 
     * 
     */
    public List<AccessType> getAccess() {
        if (access == null) {
            access = new ArrayList<AccessType>();
        }
        return this.access;
    }

    /**
     * Gets the value of the relationships property.
     * 
     * @return
     *     possible object is
     *     {@link RelationshipListType }
     *     
     */
    public RelationshipListType getRelationships() {
        return relationships;
    }

    /**
     * Sets the value of the relationships property.
     * 
     * @param value
     *     allowed object is
     *     {@link RelationshipListType }
     *     
     */
    public void setRelationships(RelationshipListType value) {
        this.relationships = value;
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
     * Gets the value of the lang property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLang() {
        return lang;
    }

    /**
     * Sets the value of the lang property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLang(String value) {
        this.lang = value;
    }

}