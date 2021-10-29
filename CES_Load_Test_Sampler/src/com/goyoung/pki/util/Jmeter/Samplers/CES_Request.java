package com.goyoung.pki.util.Jmeter.Samplers;

import com.goyoung.pki.utl.ws.ces.client.gordon.test.STS_Test_Client;
import com.goyoung.pki.utl.ws.ces.client.gordon.test.Setup_prod;

public class CES_Request {

	public static boolean main(String TemplateName, String SubjectDN, String dnEmail, String SAN, String CertFormat,
			String MEXuRI, String OU1, String OU2, String OU3, String OU4, String OU5, String cacerts_file, String cacerts_file_passwd, String authP12, String authP12_passwd) {
		boolean reqbool;
		try {

			boolean ssl_debug = false;
			boolean proxy_on = false;

			// set some system properties,
			Setup_prod.Go(ssl_debug, proxy_on, cacerts_file, cacerts_file_passwd, authP12, authP12_passwd);

			// generate a CSR
			String CSR = "MIICizCCAXMCAQAwSDELMAkGA1UEBhMCVVMxFDASBgNVBAoTC1dlbGxzIEZhcmdvMQwwCgYDVQQLEwNwa2kxFTATBgNVBAMTDGdvcmRvbiBZb3VuZzCCASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEBAIs+5mM4+u6r8pos+RPddRcwmjyQ2xnCNDop8XD0ih7H+JOnMq08CwweHoFkGYN+m3BfbQd86G7oRs5z4n4TOeuzoossl1fm7x4NHbpLAhBHQQ83c9M+SMSns4DqjsURPUK22BnK0Z8Qn0BK5mRJDSXGINzCU8Newo1jERRx1AEd/YW0HkaG7XsNb90JJRGmW9ZBtLfeTDEyz9zV8v3K2E+8OJv8soLw71dzcwR0fXXz3CqwZz9iLiS+stIfDPnN3bWHX2AE9P5rn4btub1tEi8qC9Tu+vsdHbrhwSKn44B1ENwru20qWt1Bjd78zmwQ1TuTanxB+ITDNh4dWeSQV3ECAwEAATANBgkqhkiG9w0BAQsFAAOCAQEARoShwZNNDT0XsN6/gfQLZS9HnUBxpkcEcrKC0I17XjgEg1AwsMoGdJmG0k8VhalXJ7BUe22KZ70YrBIA6Ugbrk7qwDtBlK4c6vTHX1I20jsl/JQ3mAVLL1sf3tJJv/smbAlr5ajdVGLpXTHF4WggRhJssaPmUsPRMGaWq4EjAAucy33AhQZIeHHP3rDTWY36IOPJKCArOfMo7/SwyNXVG0F20lCpwPX2bKt3KCwj0b6bjWHLf8q7vuFWGiwDDZxEnSMak+S+XpCwtI/w/rVFg7DZoPFUb81aWlVg+AxKVIDfCmSubtMkuPuldcEytl4XboiBj/xxcR1kRV6qdg1AOg==";
			// request a certificate
			@SuppressWarnings("unused")
			String requested_Certificate = STS_Test_Client.Go(CSR, SAN, TemplateName, CertFormat, MEXuRI, "Years", "1", OU1, OU2, OU3, OU4, OU5, dnEmail);

			reqbool = true;
		} catch (Exception e) {
			// TODO Fix return method so that exceptions return false to caller
			e.printStackTrace();
			reqbool = false;
		}
		return reqbool;
	}
}
