//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.0-b11-EA 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2005.06.29 at 11:33:17 PDT 
//


package org.xmlsoap.schemas.ws._2004._09.enumeration;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.AccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import com.sun.xml.bind.annotation.W3CDomHandler;
import com.sun.xml.bind.annotation.XmlAnyElement;

@XmlAccessorType(value = AccessType.FIELD)
@XmlType(name = "ItemListType", namespace = "http://schemas.xmlsoap.org/ws/2004/09/enumeration")
public class ItemListType {

    @XmlAnyElement(lax = true, value = W3CDomHandler.class)
    protected List<Object> any;

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

}
