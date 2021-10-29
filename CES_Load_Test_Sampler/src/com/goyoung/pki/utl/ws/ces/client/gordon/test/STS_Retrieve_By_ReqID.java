package com.goyoung.pki.utl.ws.ces.client.gordon.test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
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

public class STS_Retrieve_By_ReqID {

	/**
	 * Create an STS client, create dispatch Invoke dispatch, return response
	 * 
	 * @param args
	 * @return
	 * @throws SOAPException
	 * @throws IOException
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 * 
	 * This class produces this message:
<s:Envelope xmlns:a="http://www.w3.org/2005/08/addressing" xmlns:s="http://www.w3.org/2003/05/soap-envelope">
<s:Header>
		<a:Action s:mustUnderstand="1">http://schemas.microsoft.com/windows/pki/2009/01/enrollment/RST/wstep</a:Action>
		<a:MessageID>urn:uuid:ce330bb2-0ca2-473b-a29a-19e9264666ff
		</a:MessageID>
		<a:ReplyTo>
			<a:Address>http://www.w3.org/2005/08/addressing/anonymous</a:Address>
		</a:ReplyTo>
	</s:Header>
	<s:Body xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
		xmlns:xsd="http://www.w3.org/2001/XMLSchema">
		<RequestSecurityToken
			xmlns="http://docs.oasis-open.org/ws-sx/ws-trust/200512">
			<TokenType>http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509v3
			</TokenType>
			<RequestType>http://schemas.microsoft.com/windows/pki/2009/01/enrollment/QueryTokenStatus
			</RequestType>
			<RequestID
				xmlns="http://schemas.microsoft.com/windows/pki/2009/01/enrollment">66</RequestID>
		</RequestSecurityToken>
	</s:Body>
</s:Envelope>
	 * 
	 * 
	 * 
	 */

	public static String Go(String sRequestID, String MEXuRI, String CertFormat) throws SOAPException, IOException,
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
		
		// Add a unique message ID "UUID"
		//SOAPElement ReplyToAddress = factory1.createElement("ReplyTo", "","");
		QName ReplyToName = new QName("a", "ReplyTo");
		QName ReplyToAddressName = new QName("http://www.w3.org/2005/08/addressing/anonymous","Address", "a");
		SOAPElement ReplyTo = factory1.createElement(ReplyToName);
		SOAPElement ReplyToAddress = factory1.createElement(ReplyToAddressName);
	    ReplyTo.addChildElement(ReplyToAddress);
		
		// add all the required SOAP header items:
		header.addChildElement(To);
		header.addChildElement(ActionElem);
		header.addChildElement(MessageID);
		//header.addChildElement(ReplyTo);

		// create a "Request Body" to hold request elements:
		SOAPBody body = request.getSOAPBody();

		// Compose the soap:Body payload "RequestSecurityToken" as the body type
		QName payloadName = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512","RequestSecurityToken", "");
		SOAPBodyElement payload = body.addBodyElement(payloadName);

		// Add the WS-Trust TokenType and RequestType elements:
		payload.addChildElement("TokenType").addTextNode("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509v3");
		payload.addChildElement("RequestType").addTextNode("http://schemas.microsoft.com/windows/pki/2009/01/enrollment/QueryTokenStatus");
		
		QName RequestIDName = new QName("http://schemas.microsoft.com/windows/pki/2009/01/enrollment","RequestID", "");
		payload.addChildElement(RequestIDName).addTextNode(sRequestID);

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
		StringBuffer ressponseSB = new StringBuffer();
		String resonseS = baos.toString();
		ressponseSB.append(resonseS);
		StringBuilder sBuilder = RequestTokenResponseParser.ParseResponse(ressponseSB, CertFormat);

		String Certs = sBuilder.toString();

		return Certs;
	}
}
