
package com.microsoft.schemas.windows.pki._2009._01.enrollment;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CertificateEnrollmentWSDetailType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CertificateEnrollmentWSDetailType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="BinaryResponse" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ErrorCode" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="InvalidRequest" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="RequestID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CertificateEnrollmentWSDetailType", propOrder = {
    "binaryResponse",
    "errorCode",
    "invalidRequest",
    "requestID"
})
public class CertificateEnrollmentWSDetailType {

    @XmlElementRef(name = "BinaryResponse", namespace = "http://schemas.microsoft.com/windows/pki/2009/01/enrollment", type = JAXBElement.class)
    protected JAXBElement<String> binaryResponse;
    @XmlElementRef(name = "ErrorCode", namespace = "http://schemas.microsoft.com/windows/pki/2009/01/enrollment", type = JAXBElement.class)
    protected JAXBElement<Integer> errorCode;
    @XmlElementRef(name = "InvalidRequest", namespace = "http://schemas.microsoft.com/windows/pki/2009/01/enrollment", type = JAXBElement.class)
    protected JAXBElement<Boolean> invalidRequest;
    @XmlElementRef(name = "RequestID", namespace = "http://schemas.microsoft.com/windows/pki/2009/01/enrollment", type = JAXBElement.class)
    protected JAXBElement<String> requestID;

    /**
     * Gets the value of the binaryResponse property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getBinaryResponse() {
        return binaryResponse;
    }

    /**
     * Sets the value of the binaryResponse property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setBinaryResponse(JAXBElement<String> value) {
        this.binaryResponse = ((JAXBElement<String> ) value);
    }

    /**
     * Gets the value of the errorCode property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public JAXBElement<Integer> getErrorCode() {
        return errorCode;
    }

    /**
     * Sets the value of the errorCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     
     */
    public void setErrorCode(JAXBElement<Integer> value) {
        this.errorCode = ((JAXBElement<Integer> ) value);
    }

    /**
     * Gets the value of the invalidRequest property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     *     
     */
    public JAXBElement<Boolean> getInvalidRequest() {
        return invalidRequest;
    }

    /**
     * Sets the value of the invalidRequest property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     *     
     */
    public void setInvalidRequest(JAXBElement<Boolean> value) {
        this.invalidRequest = ((JAXBElement<Boolean> ) value);
    }

    /**
     * Gets the value of the requestID property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getRequestID() {
        return requestID;
    }

    /**
     * Sets the value of the requestID property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setRequestID(JAXBElement<String> value) {
        this.requestID = ((JAXBElement<String> ) value);
    }

}
