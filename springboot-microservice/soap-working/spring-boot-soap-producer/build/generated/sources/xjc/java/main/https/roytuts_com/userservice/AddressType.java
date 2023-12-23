
package https.roytuts_com.userservice;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Java class for addressType.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * &lt;pre&gt;
 * &amp;lt;simpleType name="addressType"&amp;gt;
 *   &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&amp;gt;
 *     &amp;lt;enumeration value="PERMANENT"/&amp;gt;
 *     &amp;lt;enumeration value="COMMUNICATION"/&amp;gt;
 *     &amp;lt;enumeration value="OFFICIAL"/&amp;gt;
 *   &amp;lt;/restriction&amp;gt;
 * &amp;lt;/simpleType&amp;gt;
 * &lt;/pre&gt;
 * 
 */
@XmlType(name = "addressType")
@XmlEnum
public enum AddressType {

    PERMANENT,
    COMMUNICATION,
    OFFICIAL;

    public String value() {
        return name();
    }

    public static AddressType fromValue(String v) {
        return valueOf(v);
    }

}
