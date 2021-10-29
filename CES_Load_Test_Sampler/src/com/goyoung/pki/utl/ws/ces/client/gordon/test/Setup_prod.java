package com.goyoung.pki.utl.ws.ces.client.gordon.test;

public class Setup_prod {

	/**
	 * @param authP12_passwd 
	 * @param authP122 
	 * @param boolean ssl_debug
	 * @param boolean proxy
	 */
	
	public static void Go(boolean ssl_debug, boolean proxy, String cacerts_file, String cacerts_file_passwd, String authP12, String authP12_passwd) {
		
		if (proxy !=false) {
			//Enable these to deal with proxy issues:
			System.setProperty("http.proxyHost", "proxy-az.example.org");
			System.setProperty("http.proxyPort", "80");
			System.setProperty("http.noProxyHosts", "localhost|enca01.ent.acme.dev");
		}
		
		// set the trust and keystores to local admin.jks file
		System.setProperty("javax.net.ssl.trustStore", cacerts_file);
		System.setProperty("javax.net.ssl.trustStoreType", "jks");
		System.setProperty("net.ssl.trustStorePassword", cacerts_file_passwd);
		
		System.setProperty("javax.net.ssl.keyStore", authP12);
		System.setProperty("javax.net.ssl.keyStoreType", "PKCS12");
		System.setProperty("javax.net.ssl.keyStorePassword", authP12_passwd);
			
		//Turn on SSL Debug:
		//if(ssl_debug !=false){
		//System.setProperty("javax.net.debug", "all");
		//ssl,handshake or all
		//}
	}
}
