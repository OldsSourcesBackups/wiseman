//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.0-b11-EA 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2005.06.29 at 11:33:17 PDT 
//


package org.xmlsoap.schemas.ws._2005._02.wsmancat;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.AccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;

@XmlAccessorType(value = AccessType.FIELD)
@XmlType(name = "KeywordListType", namespace = "http://schemas.xmlsoap.org/ws/2005/02/wsmancat")
public class KeywordListType {

    @XmlElement(name = "Keyword", namespace = "http://schemas.xmlsoap.org/ws/2005/02/wsmancat", type = QName.class)
    protected List<QName> keyword;

    protected List<QName> _getKeyword() {
        if (keyword == null) {
            keyword = new ArrayList<QName>();
        }
        return keyword;
    }

    /**
     * Gets the value of the keyword property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the keyword property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getKeyword().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link javax.xml.namespace.QName}
     * 
     */
    public List<QName> getKeyword() {
        return this._getKeyword();
    }

}
