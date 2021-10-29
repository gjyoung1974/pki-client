package com.goyoung.pki.utl.ws.ces.client.gordon.test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.util.List;
import java.util.UUID;

import javax.xml.namespace.QName;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFactory;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.Dispatch;
import javax.xml.ws.Service;
import javax.xml.ws.WebServiceException;

import org.xml.sax.SAXException;

import com.sun.xml.ws.mex.client.MetadataClient;
import com.sun.xml.ws.mex.client.PortInfo;
import com.sun.xml.ws.mex.client.schema.Metadata;

public class STS_Test_Client {

	/**
	 * Create an STS client, create dispatch Invoke dispatch, return response
	 * 
	 * @param args
	 * @return
	 * @throws SOAPException
	 * @throws IOException
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 */

	public static String Go(String CSR, String SAN, String TemplateName, String CertFormat,
			String MEXuRI, String ValidUnit, String ValidValue, String OU1, String OU2, String OU3, String OU4, String OU5, String dnEmail) throws SOAPException, IOException,
			ParserConfigurationException, SAXException {

		MetadataClient mexClient = new MetadataClient();
		
		//the MEX URI is the service URL +/MEX
		Metadata metadata = mexClient.retrieveMetadata(MEXuRI + "/MEX");
		metadata.getOtherAttributes();

		QName serviceInfo = null;
		QName portName = null;
		String Address = null;
		//String namespace = null;

		List<PortInfo> ports = mexClient.getServiceInformation(metadata);
		for (PortInfo port : ports) {

			serviceInfo = port.getServiceName();
			portName = port.getPortName();
			Address = port.getAddress();
			//namespace = port.getPortNamespaceURI();

		}

		//an instance of SecurityTokenService
		Service STSS = Service.create(new URL(MEXuRI), serviceInfo);

		//a dispatch of SOAPMessage
		Dispatch<SOAPMessage> dispatch = STSS.createDispatch(portName, SOAPMessage.class, Service.Mode.MESSAGE);

		// Message factor instance of SOAP 1.2 protcol
		MessageFactory factory = MessageFactory.newInstance(SOAPConstants.SOAP_1_2_PROTOCOL);

		// Create SOAPMessage Request
		SOAPMessage request = factory.createMessage();

		// Request Header
		SOAPHeader header = request.getSOAPHeader();

		// Soap Factory
		SOAPFactory factory1 = SOAPFactory.newInstance();

		// Enable WS-Addressing and Add the "To:" endpoint
		SOAPElement To = factory1.createElement("To", "","http://www.w3.org/2005/08/addressing");
		To.addTextNode(Address);

		// add the Microsoft MS-STEP Action Element:
		SOAPElement ActionElem = factory1.createElement("Action", "","http://www.w3.org/2005/08/addressing");
		ActionElem.addTextNode("http://schemas.microsoft.com/windows/pki/2009/01/enrollment/RST/wstep");

		// Add a unique message ID "UUID"
		SOAPElement MessageID = factory1.createElement("MessageID", "","http://www.w3.org/2005/08/addressing");
		MessageID.addTextNode("uuid:" + UUID.randomUUID());

		// add all the required SOAP header items:
		header.addChildElement(To);
		header.addChildElement(ActionElem);
		header.addChildElement(MessageID);

		// create a "Request Body" to hold request elements:
		SOAPBody body = request.getSOAPBody();

		// Compose the soap:Body payload "RequestSecurityToken" as the body type
		QName payloadName = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512","RequestSecurityToken", "");
		SOAPBodyElement payload = body.addBodyElement(payloadName);

		// Add the WS-Trust TokenType and RequestType elements:
		payload.addChildElement("TokenType").addTextNode("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509v3");
		payload.addChildElement("RequestType").addTextNode("http://docs.oasis-open.org/ws-sx/ws-trust/200512/Issue");

		// add the BinarySecurityToken type
		SOAPElement BinarySecurityToken = factory1.createElement("BinarySecurityToken","","http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd");

		// set BinarySecurityToken type as PKCS10 also known as a CSR..
		BinarySecurityToken.setAttribute("ValueType","http://schemas.microsoft.com/windows/pki/2009/01/enrollment#PKCS10");

		// Set the EncodingType to base64binary"
		BinarySecurityToken.setAttribute("EncodingType","http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd#base64binary");

		// add the WSS wssSecurity namespace:
		BinarySecurityToken.addNamespaceDeclaration("a","http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd");

		// The CSR is Base64 encoded PKCS10 CSR without the "Begin" and "End" tags
		// add the CSR as a "TextNode"
		BinarySecurityToken.addTextNode(CSR);

		// add the BinarySecurityToken element to Soap pay load
		payload.addChildElement(BinarySecurityToken);

		// Create "AdditionalContext" Element for additional context items such as policy file name, etc..
		QName AdditionalContextName = new QName("http://schemas.xmlsoap.org/ws/2006/12/authorization","AdditionalContext", "");
		SOAPBodyElement AdditionalContext = body.addBodyElement(AdditionalContextName);

		// Create a ContextItem to specify the CertificateTemplate name, get
		// element from "TemplateName" argument
		SOAPElement ContextItem1 = AdditionalContext.addChildElement("ContextItem");
		ContextItem1.setAttribute("Name", "CertificateTemplate");
		SOAPElement Value = AdditionalContext.addChildElement("Value");
		Value.setTextContent(TemplateName);
		ContextItem1.addChildElement(Value);
		
		// Create a ContextItem to specify the value of "Other" context item
		// element from "TemplateName" argument
		SOAPElement ContextItem2 = AdditionalContext.addChildElement("ContextItem");
		ContextItem2.setAttribute("Name", "OU1");
		SOAPElement Value2 = AdditionalContext.addChildElement("Value");
		Value2.setTextContent(OU1);
		ContextItem2.addChildElement(Value2);
		
		// Create a ContextItem to specify the rmd (remote server) name
		SOAPElement ContextItem3 = AdditionalContext.addChildElement("ContextItem");
		ContextItem3.setAttribute("Name", "rmd");
		SOAPElement Value3 = AdditionalContext.addChildElement("Value");
		// Get application server FQDNS hostname
		InetAddress addr = InetAddress.getLocalHost();
		Value3.setTextContent(addr.getCanonicalHostName());
		ContextItem3.addChildElement(Value3);
		
		
		//Request specific validity period:
		
		//to enable client side to set validity period you must enable:
		//certutil -setreg Policy\EditFlags + EDITF_ATTRIBUTEENDDATE
		// on the CA!
		// Create a ContextItem to specify the rmd (remote server) name
		SOAPElement ContextItem4 = AdditionalContext.addChildElement("ContextItem");
		ContextItem4.setAttribute("Name", "ValidityPeriod");
		SOAPElement Value4 = AdditionalContext.addChildElement("Value");
		//Units can be "Seconds", "Minutes", "Hours", "Days", "Weeks", "Months", "Years"
		Value4.setTextContent(ValidUnit);
		ContextItem4.addChildElement(Value4);
		
		// Create a ContextItem to specify the rmd (remote server) name
		SOAPElement ContextItem5 = AdditionalContext.addChildElement("ContextItem");
		ContextItem5.setAttribute("Name", "ValidityPeriodUnits");
		SOAPElement Value5 = AdditionalContext.addChildElement("Value");
		Value5.setTextContent(ValidValue);
		ContextItem5.addChildElement(Value5);
		

		// Create a ContextItem to specify the value of "Other" context item
		// element from "TemplateName" argument
		SOAPElement ContextItem7 = AdditionalContext.addChildElement("ContextItem");
		ContextItem7.setAttribute("Name", "OU2");
		SOAPElement Value7 = AdditionalContext.addChildElement("Value");
		Value7.setTextContent(OU2);
		ContextItem7.addChildElement(Value7);
		
		
		// Create a ContextItem to specify the value of "Other" context item
		// element from "TemplateName" argument
		SOAPElement ContextItem8 = AdditionalContext.addChildElement("ContextItem");
		ContextItem8.setAttribute("Name", "OU3");
		SOAPElement Value8 = AdditionalContext.addChildElement("Value");
		Value8.setTextContent(OU3);
		ContextItem8.addChildElement(Value8);
		
		// Create a ContextItem to specify the value of "Other" context item
		// element from "TemplateName" argument
		SOAPElement ContextItem9 = AdditionalContext.addChildElement("ContextItem");
		ContextItem9.setAttribute("Name", "OU4");
		SOAPElement Value9 = AdditionalContext.addChildElement("Value");
		Value9.setTextContent(OU4);
		ContextItem9.addChildElement(Value9);
		
		// Create a ContextItem to specify the value of "Other" context item
		// element from "TemplateName" argument
		SOAPElement ContextItem10 = AdditionalContext.addChildElement("ContextItem");
		ContextItem10.setAttribute("Name", "OU5");
		SOAPElement Value10 = AdditionalContext.addChildElement("Value");
		Value10.setTextContent(OU5);
		ContextItem10.addChildElement(Value10);
		
		
		//KeyUsage=0xa0
		//SOAPElement ContextItem15 = AdditionalContext.addChildElement("ContextItem");
		//ContextItem15.setAttribute("Name", "CertificateUsage");
		//SOAPElement Value15 = AdditionalContext.addChildElement("Value");
		//Value15.setTextContent("1.3.6.1.5.5.7.3.1,1.3.6.1.5.5.7.3.2");
		//ContextItem15.addChildElement(Value15);
		
		
		//TODO:
		//let's try this an see what it does:
		//CertificateUsage
		
		//alternatively we can specify a specific end date:
		//ExpirationDate = L"Tue, 21 Nov 2000 01:06:53 GMT"
		
		// add the ContextItem child to the soap payload:
		AdditionalContext.addChildElement(ContextItem1);
				
		//TODO: document this, rename the context items
		AdditionalContext.addChildElement(ContextItem2);
		AdditionalContext.addChildElement(ContextItem3);
		//AdditionalContext.addChildElement(ContextItem4);
		//AdditionalContext.addChildElement(ContextItem5);
		AdditionalContext.addChildElement(ContextItem7);
		AdditionalContext.addChildElement(ContextItem8);
		AdditionalContext.addChildElement(ContextItem9);
		AdditionalContext.addChildElement(ContextItem10);
		//AdditionalContext.addChildElement(ContextItem15);
		
		// Create a ContextItem to specify the value of "Other" context item
		//element from "TemplateName" argument
		if (dnEmail !=null){
		SOAPElement ContextItem11 = AdditionalContext.addChildElement("ContextItem");
		ContextItem11.setAttribute("Name", "dnEmail");
		SOAPElement Value11 = AdditionalContext.addChildElement("Value");
		Value11.setTextContent(dnEmail);
		ContextItem11.addChildElement(Value11);
		AdditionalContext.addChildElement(ContextItem11);
		}
		
		//if SAN extention is specified add the ContextItem
		if (SAN !=null){
		// Create a ContextItem to specify the rmd (remote server) name
		SOAPElement ContextItem6 = AdditionalContext.addChildElement("ContextItem");
		ContextItem6.setAttribute("Name", "SAN");
		SOAPElement Value6 = AdditionalContext.addChildElement("Value");
		Value6.setTextContent(SAN);
		ContextItem6.addChildElement(Value6);
		AdditionalContext.addChildElement(ContextItem6);
		}
		
		payload.addChildElement(AdditionalContext);

		// place holder for RequestSecurityToken response soap message
		SOAPMessage reply = null;

		// Invoke the end point operation synchronously
		try {
			// and read response
			reply = dispatch.invoke(request);
		} catch (WebServiceException wse) {
			wse.printStackTrace();
		}

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		reply.writeTo(baos);
		
		//to view the reply via STD-Out:
		// System.out.println(baos);
		StringBuffer ressponseSB = new StringBuffer();
		String resonseS = baos.toString();

		ressponseSB.append(resonseS);
		StringBuilder sBuilder = RequestTokenResponseParser.ParseResponse(ressponseSB, CertFormat);

		String Certs = sBuilder.toString();

		return Certs;
	}
}
