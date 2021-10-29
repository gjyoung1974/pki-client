package com.goyoung.pki.utl.ws.ces.client.gordon.test;

public class Setup_local_sandbox {

	/**
	 * @param boolean ssl_debug
	 * @param boolean proxy
	 */
	
	static void Go(boolean ssl_debug, boolean proxy) {
		
		if (proxy !=false) {
			//Enable these to deal with proxy issues:
			System.setProperty("http.proxyHost", "proxy.gordonjyoung..com");
			System.setProperty("http.proxyPort", "80");
		    System.setProperty("http.noProxyHosts", "localhost|somehost.corp.gordonjyoung.com");
		}

		//should never need to allow unsafe renego- tune the server instead.
		//java.lang.System.setProperty("sun.security.ssl.allowUnsafeRenegotiation", "true");
		//System.setProperty("sun.security.ssl.allowUnsafeRenegotiation", "true");
		// set the trust and keystores to local admin.jks file
        System.setProperty("javax.net.ssl.trustStore", "./cacerts.jks");
		System.setProperty("javax.net.ssl.trustStoreType", "jks");
		System.setProperty("net.ssl.trustStorePassword", "changeit");
		
		System.setProperty("javax.net.ssl.keyStore", "./svc-ces_client.pfx");
        System.setProperty("javax.net.ssl.keyStoreType", "PKCS12");
		System.setProperty("javax.net.ssl.keyStorePassword", "Password1!");
			
		//Turn on SSL Debug:
		if(ssl_debug !=false){
		//System.setProperty("javax.net.debug", "SSL,handshake,trustmanager");
			System.setProperty("javax.net.debug", "all");
		}
	}
}
