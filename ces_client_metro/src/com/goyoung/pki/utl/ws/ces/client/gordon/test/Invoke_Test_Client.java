package com.goyoung.pki.utl.ws.ces.client.gordon.test;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.URISyntaxException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SignatureException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.SOAPException;
import javax.xml.stream.XMLStreamException;

import org.bouncycastle.util.encoders.Base64;
import org.xml.sax.SAXException;

import com.sun.xml.ws.api.security.trust.WSTrustException;

/**
 * @author Gordon Young Invoke the test client to create a dispatch, and parse
 *         the response
 * 		   Observe STS_Test_Client to see a sample SecurityTokenService client
 *
 */

//TODO: set up the EV_SSL CA
//TODO: test ACL when using the "TrueExtender"
//TODO: can we set perms on the policy module registry keys for CAO user group only?

public class Invoke_Test_Client {

	public static void main(String[] args) throws InvalidKeyException,
			NoSuchAlgorithmException, NoSuchProviderException,
			SignatureException, SOAPException, IOException,
			ParserConfigurationException, SAXException, WSTrustException, URISyntaxException, XMLStreamException, CertificateException, InvalidAlgorithmParameterException {

		// the ssl_debug enables or disables ssl debugging.., 
		// proxy_on=true enables proxy settings (use proxy server to get XML Schema)
		boolean ssl_debug = true;
		boolean proxy_on = true;
		
		//set some system properties,
		Setup_prod.Go(ssl_debug, proxy_on);
		
		//name of certificate enrollment template
		String TemplateName = "ACMEWebServer";
		String SubjectDN = 	"cn=gordon Young,ou=pki,o=ACME Corp,c=US";
		//subjectDN email:
		String dnEmail = "test@test.local";
		
		//generate a CSR
		String CSR = GenCSR.Go(SubjectDN);
		
		//put a bunch of SAN:dns fields in the cert..
		//These are read by the policy module to be added to the certificate subject DN
		String SAN = "DNS=www.gordon.edu&DNS=www.gordon.org&DNS=www.google.com&DNS=www1.google.com&email=test.test@gmail.com&DNS=www2.google.com";
		
		//Certificate Format to return from request
		//CER = Certificate P7B = PKCS7 chain of certificates
		String CertFormat = "CER";
		
		//get SecurityTokenService port, binding, service names, 
		//Endpoint URI and other info via WS-MetaData-Exchange aka MEX
		String MEXuRI = "https://goyoung-dc01.corp.gordonjyoung.com/ACME%20Issuing%20CA_CES_Certificate/service.svc";
		
		//"Request Attribute" names and values
		//These are read by the policy module to be added to the certificate subject DN
		String OU1 = "ent";
		String OU2 = "cps";
		String OU3 = "pki";
		String OU4 = null;//"CC_PKI_Groups";
		String OU5 = null;//"CA_Admins";
				
		//request a certificate
		String requested_Certificate = STS_Test_Client.Go(CSR, SAN, TemplateName, CertFormat, MEXuRI, "Years", "2", OU1, OU2, OU3, OU4, OU5, dnEmail);
		
		//decode the returned token
		byte[] cert = Base64.decode(requested_Certificate);
		
		//dump the binary certificate to a file
	    DataOutputStream os = new DataOutputStream(new FileOutputStream("./cert.crt"));
	    os.write(cert);
	    os.close();		
	    
	    //do something useful with the issued certificate
	    //like evaluate RDN Components and Extensions..
	    InputStream inStream = new FileInputStream("./cert.crt");
	    CertificateFactory cf = CertificateFactory.getInstance("X.509");
	    X509Certificate clientCert = (X509Certificate) cf.generateCertificate(inStream);
	    inStream.close();
	    
	    //get certificate serial#
	    BigInteger serialNum = clientCert.getSerialNumber();
	    String serialS = serialNum.toString(16);
	    System.out.println("Serial #: "  + serialS);
	    
	}
}