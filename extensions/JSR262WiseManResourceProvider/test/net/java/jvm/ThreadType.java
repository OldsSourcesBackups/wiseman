//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-322 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2007.03.06 at 01:53:47 PM MET 
//


package net.java.jvm;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ThreadType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ThreadType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CurrentThreadCPUTime" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ThreadType", propOrder = {
    "currentThreadCPUTime"
})
public class ThreadType {

    @XmlElement(name = "CurrentThreadCPUTime")
    protected long currentThreadCPUTime;

    /**
     * Gets the value of the currentThreadCPUTime property.
     * 
     */
    public long getCurrentThreadCPUTime() {
        return currentThreadCPUTime;
    }

    /**
     * Sets the value of the currentThreadCPUTime property.
     * 
     */
    public void setCurrentThreadCPUTime(long value) {
        this.currentThreadCPUTime = value;
    }

}
