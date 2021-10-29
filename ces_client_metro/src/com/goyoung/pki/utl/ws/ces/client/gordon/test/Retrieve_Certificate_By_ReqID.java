package com.goyoung.pki.utl.ws.ces.client.gordon.test;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.URISyntaxException;
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

import org.xml.sax.SAXException;

import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import com.sun.xml.ws.api.security.trust.WSTrustException;
import com.sun.xml.wss.impl.misc.Base64;

/**
 * This class demonstrates a request for a previously issued certificate 
 * This requests calls the CES to download a certificate by Microsoft's MS-WSTEP <RequestID> element
 * @author Gordon Young Invoke the test client to create a dispatch, and parse
 *         the response
 * 		   See STS_Retrieve_By_ReqID.java to see a sample SecurityTokenService where certs are retrieved
 * 		   by  the "RequestID"
 * 
 */

public class Retrieve_Certificate_By_ReqID {
	
	public static void main(String[] args) throws InvalidKeyException,
			NoSuchAlgorithmException, NoSuchProviderException,
			SignatureException, SOAPException, IOException,
			ParserConfigurationException, SAXException, WSTrustException, URISyntaxException, XMLStreamException, Base64DecodingException, CertificateException {

		// the ssl_debug enables or disables ssl debugging.., 
		// proxy_on=true enables proxy settings
		boolean ssl_debug = true;
		boolean proxy_on = true;
		
		//set some system properties,
		Setup_prod.Go(ssl_debug, proxy_on);
		
		//Set RequestID to a certifcate RequestID that you want to download:
		String RequestID ="23";
		
		//get SecurityTokenService port, binding, service names, 
		//Endpoint URI and other info via WS-MetaData-Exchange aka MEX
		String MEXuRI = "https://goyoung-dc01.corp.gordonjyoung.com/ACME%20Issuing%20CA_CES_Certificate/service.svc";
		
		//Set this to CER or P7B, CER is the certificate only, P7B is the PKCS#7 chainfile containing root, subCA and certificate
		String CertFormat = "CER";
		String requested_Certificate = STS_Retrieve_By_ReqID.Go(RequestID, MEXuRI, CertFormat);
		
		//decode the returned token
		byte[] cert = Base64.decode(requested_Certificate);
		
		//dump the binary certificate to a file
	    DataOutputStream os = new DataOutputStream(new FileOutputStream("./cert.crt"));
	    os.write(cert);
	    os.close();		
	    
	    InputStream inStream = new FileInputStream("./cert.crt");
	    CertificateFactory cf = CertificateFactory.getInstance("X.509");
	    X509Certificate clientCert = (X509Certificate) cf.generateCertificate(inStream);
	    inStream.close();
	    
	    //get certificate serial#
	    BigInteger serialNum = clientCert.getSerialNumber();
	    String serialS = serialNum.toString(16);
	    System.out.println("Serial #: "  + serialS);
	    System.out.println("subject: " + clientCert.getSubjectDN().toString());

	}
}