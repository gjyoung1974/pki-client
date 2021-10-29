package com.goyoung.pki.utl.ws.ces.client.gordon.test;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class RequestTokenResponseParser {

	/**
	 * @param args
	 * @throws ParserConfigurationException 
	 * @throws IOException 
	 * @throws SAXException 
	 */
	
	public static StringBuilder ParseResponse(StringBuffer response, String CertFormat) throws ParserConfigurationException, SAXException, IOException {
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(new InputSource(new StringReader(response.toString())));
		doc.getDocumentElement().normalize();

		NodeList nodeLst = doc.getElementsByTagName("RequestSecurityTokenResponse");
		StringBuilder builder = new StringBuilder();
		
		for (int s = 0; s < nodeLst.getLength(); s++) {

			Node fstNode = nodeLst.item(s);

			if (fstNode.getNodeType() == Node.ELEMENT_NODE) {

				Element fstElmnt = (Element) fstNode;
				NodeList fstNmElmntLst = fstElmnt.getElementsByTagName("RequestID");
				Element fstNmElmnt = (Element) fstNmElmntLst.item(0);
				NodeList fstNm = fstNmElmnt.getChildNodes();
				System.out.println("RequestID : " + ((Node) fstNm.item(0)).getNodeValue());

				NodeList lstNmElmntLst = fstElmnt.getElementsByTagName("DispositionMessage");
				Element lstNmElmnt = (Element) lstNmElmntLst.item(0);
				NodeList lstNm = lstNmElmnt.getChildNodes();
				System.out.println("Disposition Message : " + ((Node) lstNm.item(0)).getNodeValue());
				
				int CertType;
				
				if (CertFormat.equals("CER")){
					CertType =1;
				}
				else{
					CertType =0;
				}

				NodeList ReqElmntLst = fstElmnt.getElementsByTagName("BinarySecurityToken");
				Element ReqNmElmnt = (Element) ReqElmntLst.item(CertType);
				NodeList ReqList = ReqNmElmnt.getChildNodes();
		        
		        //nodevalue is PKCS7 certificate chain
		        builder.append(((Node) ReqList.item(0)).getNodeValue());				
			}
		}
		
		return builder;
		// TODO Auto-generated method stub
	}
}
