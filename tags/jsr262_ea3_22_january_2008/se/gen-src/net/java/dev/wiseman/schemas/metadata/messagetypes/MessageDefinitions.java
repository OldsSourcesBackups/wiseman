//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-382 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2007.05.29 at 01:59:34 PM EDT 
//


package net.java.dev.wiseman.schemas.metadata.messagetypes;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="schemas" type="{http://schemas.wiseman.dev.java.net/metadata/messagetypes}SchemasType"/>
 *         &lt;element name="operations" type="{http://schemas.wiseman.dev.java.net/metadata/messagetypes}OperationsType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "schemas",
    "operations"
})
@XmlRootElement(name = "message-definitions")
public class MessageDefinitions {

    @XmlElement(required = true)
    protected SchemasType schemas;
    @XmlElement(required = true)
    protected OperationsType operations;

    /**
     * Gets the value of the schemas property.
     * 
     * @return
     *     possible object is
     *     {@link SchemasType }
     *     
     */
    public SchemasType getSchemas() {
        return schemas;
    }

    /**
     * Sets the value of the schemas property.
     * 
     * @param value
     *     allowed object is
     *     {@link SchemasType }
     *     
     */
    public void setSchemas(SchemasType value) {
        this.schemas = value;
    }

    /**
     * Gets the value of the operations property.
     * 
     * @return
     *     possible object is
     *     {@link OperationsType }
     *     
     */
    public OperationsType getOperations() {
        return operations;
    }

    /**
     * Sets the value of the operations property.
     * 
     * @param value
     *     allowed object is
     *     {@link OperationsType }
     *     
     */
    public void setOperations(OperationsType value) {
        this.operations = value;
    }

}
