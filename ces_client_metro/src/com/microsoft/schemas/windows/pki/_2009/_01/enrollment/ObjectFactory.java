
package com.microsoft.schemas.windows.pki._2009._01.enrollment;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.microsoft.schemas.windows.pki._2009._01.enrollment package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _DispositionMessage_QNAME = new QName("http://schemas.microsoft.com/windows/pki/2009/01/enrollment", "DispositionMessage");
    private final static QName _RequestID_QNAME = new QName("http://schemas.microsoft.com/windows/pki/2009/01/enrollment", "RequestID");
    private final static QName _CertificateEnrollmentWSDetail_QNAME = new QName("http://schemas.microsoft.com/windows/pki/2009/01/enrollment", "CertificateEnrollmentWSDetail");
    private final static QName _CertificateEnrollmentWSDetailTypeBinaryResponse_QNAME = new QName("http://schemas.microsoft.com/windows/pki/2009/01/enrollment", "BinaryResponse");
    private final static QName _CertificateEnrollmentWSDetailTypeInvalidRequest_QNAME = new QName("http://schemas.microsoft.com/windows/pki/2009/01/enrollment", "InvalidRequest");
    private final static QName _CertificateEnrollmentWSDetailTypeErrorCode_QNAME = new QName("http://schemas.microsoft.com/windows/pki/2009/01/enrollment", "ErrorCode");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.microsoft.schemas.windows.pki._2009._01.enrollment
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link CertificateEnrollmentWSDetailType }
     * 
     */
    public CertificateEnrollmentWSDetailType createCertificateEnrollmentWSDetailType() {
        return new CertificateEnrollmentWSDetailType();
    }

    /**
     * Create an instance of {@link DispositionMessageType }
     * 
     */
    public DispositionMessageType createDispositionMessageType() {
        return new DispositionMessageType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DispositionMessageType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/windows/pki/2009/01/enrollment", name = "DispositionMessage")
    public JAXBElement<DispositionMessageType> createDispositionMessage(DispositionMessageType value) {
        return new JAXBElement<DispositionMessageType>(_DispositionMessage_QNAME, DispositionMessageType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/windows/pki/2009/01/enrollment", name = "RequestID")
    public JAXBElement<String> createRequestID(String value) {
        return new JAXBElement<String>(_RequestID_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CertificateEnrollmentWSDetailType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/windows/pki/2009/01/enrollment", name = "CertificateEnrollmentWSDetail")
    public JAXBElement<CertificateEnrollmentWSDetailType> createCertificateEnrollmentWSDetail(CertificateEnrollmentWSDetailType value) {
        return new JAXBElement<CertificateEnrollmentWSDetailType>(_CertificateEnrollmentWSDetail_QNAME, CertificateEnrollmentWSDetailType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/windows/pki/2009/01/enrollment", name = "BinaryResponse", scope = CertificateEnrollmentWSDetailType.class)
    public JAXBElement<String> createCertificateEnrollmentWSDetailTypeBinaryResponse(String value) {
        return new JAXBElement<String>(_CertificateEnrollmentWSDetailTypeBinaryResponse_QNAME, String.class, CertificateEnrollmentWSDetailType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/windows/pki/2009/01/enrollment", name = "RequestID", scope = CertificateEnrollmentWSDetailType.class)
    public JAXBElement<String> createCertificateEnrollmentWSDetailTypeRequestID(String value) {
        return new JAXBElement<String>(_RequestID_QNAME, String.class, CertificateEnrollmentWSDetailType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/windows/pki/2009/01/enrollment", name = "InvalidRequest", scope = CertificateEnrollmentWSDetailType.class)
    public JAXBElement<Boolean> createCertificateEnrollmentWSDetailTypeInvalidRequest(Boolean value) {
        return new JAXBElement<Boolean>(_CertificateEnrollmentWSDetailTypeInvalidRequest_QNAME, Boolean.class, CertificateEnrollmentWSDetailType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/windows/pki/2009/01/enrollment", name = "ErrorCode", scope = CertificateEnrollmentWSDetailType.class)
    public JAXBElement<Integer> createCertificateEnrollmentWSDetailTypeErrorCode(Integer value) {
        return new JAXBElement<Integer>(_CertificateEnrollmentWSDetailTypeErrorCode_QNAME, Integer.class, CertificateEnrollmentWSDetailType.class, value);
    }

}
