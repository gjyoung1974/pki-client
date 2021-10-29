package com.goyoung.pki.utl.ws.ces.client.gordon.test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.security.SignatureException;
import java.util.Vector;

import javax.security.auth.x500.X500Principal;

import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.DERObjectIdentifier;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DEROutputStream;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DERSet;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.asn1.x509.GeneralName;
import org.bouncycastle.asn1.x509.GeneralNames;
import org.bouncycastle.asn1.x509.X509Extension;
import org.bouncycastle.asn1.x509.X509Extensions;
import org.bouncycastle.jce.PKCS10CertificationRequest;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import com.sun.xml.wss.impl.misc.Base64;

public class ManualGenCSR {

	/**
	 * @param args
	 * @throws NoSuchAlgorithmException 
	 * @throws IOException 
	 * @throws SignatureException 
	 * @throws NoSuchProviderException 
	 * @throws InvalidKeyException 
	 */
	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws NoSuchAlgorithmException, IOException, InvalidKeyException, NoSuchProviderException, SignatureException {
	

		KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
		keyGen.initialize(2048);
		KeyPair pair = keyGen.genKeyPair();

		// put some DN in the CSR..
		X500Principal subjectName = new X500Principal("cn=gordon Young,ou=pki,o=Acme Corp,c=US");
		Security.addProvider(new BouncyCastleProvider());

		ASN1EncodableVector altnameattr = new ASN1EncodableVector();
		altnameattr.add(PKCSObjectIdentifiers.pkcs_9_at_extensionRequest);

		GeneralName name = new GeneralName(GeneralName.dNSName, "test.test.com");
		GeneralNames san = new GeneralNames(name);// CertTools.getGeneralNamesFromAltName("dNSName=foo1.bar.com");
		ByteArrayOutputStream extOut = new ByteArrayOutputStream();
		DEROutputStream derOut = new DEROutputStream(extOut);

		derOut.writeObject(san);
		derOut.close();

		Vector<DERObjectIdentifier> oidvec = new Vector<DERObjectIdentifier>();
		oidvec.add(X509Extensions.SubjectAlternativeName);
		Vector<X509Extension> valuevec = new Vector<X509Extension>();
		valuevec.add(new X509Extension(false, new DEROctetString(extOut.toByteArray())));
		X509Extensions exts = new X509Extensions(oidvec, valuevec);
		altnameattr.add(new DERSet(exts));

		ASN1EncodableVector v = new ASN1EncodableVector();

		v.add(new DERSequence(altnameattr));
		//DERSet attributes = new DERSet(v);
																														   //attributes
		PKCS10CertificationRequest PKCS10 = new PKCS10CertificationRequest("SHA256WithRSA", subjectName, pair.getPublic(), null,
		pair.getPrivate());

		// Return the CSR as base64 String
		String CSR = Base64.encode(PKCS10.getDEREncoded());
		System.out.println(CSR);
		
	}

}
