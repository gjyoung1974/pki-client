package com.goyoung.pki.util.Jmeter.Samplers;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;

public class CES_Sampler extends AbstractJavaSamplerClient {
	public SampleResult runTest(JavaSamplerContext context) {
		SampleResult results = new SampleResult();
		results.sampleStart();

		String TemplateName = context.getParameter("TemplateName");
		String SubjectDN = context.getParameter("SubjectDN");
		String dnEmail = context.getParameter("dnEmail");
		String SAN = context.getParameter("SAN");
		String CertFormat = context.getParameter("CertFormat");
		String MEXuRI = context.getParameter("MEXuRI");
		String OU1 = context.getParameter("OU1");
		String OU2 = context.getParameter("OU2");
		String OU3 = context.getParameter("OU3");
		String OU4 = context.getParameter("OU4");
		String OU5 = context.getParameter("OU5");
		String cacerts  = context.getParameter("cacerts_file");
		String cacerts_passwd  = context.getParameter("cacerts_file_passwd");
		String authP12  = context.getParameter("auth_p12");
		String authP12_passwd  = context.getParameter("auth_p12_passwd");
		
		boolean result = false;

		try {
			result = com.goyoung.pki.util.Jmeter.Samplers.CES_Request.main(TemplateName, SubjectDN, dnEmail, SAN, CertFormat, MEXuRI, OU1, OU2, OU3, OU4, OU5, 
					cacerts, cacerts_passwd,  authP12, authP12_passwd);
			
			System.out.println("result: " + result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (result == true) {
			results.setSuccessful(true);
			results.setResponseCodeOK();
			results.setResponseMessage("'myResult:" + result);
		} else {
			results.setSuccessful(false);
		}
		results.sampleEnd();
		return results;
	}

	@Override
	public Arguments getDefaultParameters() {
		Arguments args = new Arguments();
		args.addArgument("cacerts_file","./lib/ext/cacerts.jks");
		args.addArgument("cacerts_file_passwd","changeit");
		args.addArgument("auth_p12","./lib/ext/svc-ces_client.pfx");	
		args.addArgument("auth_p12_passwd","Password1!");
		args.addArgument("TemplateName","ACMEWebServer");
		args.addArgument("SubjectDN", "cn=gordon Young,ou=pki,o=ACME Corp,c=US");
		args.addArgument("dnEmail", "gordon.young@example.local");
		args.addArgument("SAN", "DNS=www.gordon.edu&DNS=www.gordon.org&DNS=www.google.com&DNS=www1.google.com&email=test.test@gmail.com&DNS=www2.google.com");
		args.addArgument("CertFormat", "CER");
		args.addArgument("MEXuRI", "https://goyoung-dc01.corp.gordonjyoung.com/ACME%20Issuing%20CA_CES_Certificate/service.svc");
		args.addArgument("OU1", "corp");
		args.addArgument("OU2", "acme");
		args.addArgument("OU3", "com");
		args.addArgument("OU4", "");
		args.addArgument("OU5", "");

		return args;
	}
}