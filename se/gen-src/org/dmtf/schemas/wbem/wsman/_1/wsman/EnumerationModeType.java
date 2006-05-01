//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.0-hudson-3037-ea3 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2006.04.21 at 02:48:53 PM PDT 
//


package org.dmtf.schemas.wbem.wsman._1.wsman;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import org.dmtf.schemas.wbem.wsman._1.wsman.EnumerationModeType;


/**
 * <p>Java class for EnumerationModeType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="EnumerationModeType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="EnumerateEPR"/>
 *     &lt;enumeration value="EnumerateObjectAndEPR"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlEnum
public enum EnumerationModeType {

    @XmlEnumValue("EnumerateEPR")
    ENUMERATE_EPR("EnumerateEPR"),
    @XmlEnumValue("EnumerateObjectAndEPR")
    ENUMERATE_OBJECT_AND_EPR("EnumerateObjectAndEPR");
    private final String value;

    EnumerationModeType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static EnumerationModeType fromValue(String v) {
        for (EnumerationModeType c: EnumerationModeType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v.toString());
    }

}
