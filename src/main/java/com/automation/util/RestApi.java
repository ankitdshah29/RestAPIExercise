package com.automation.util;

import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * @author Ankit Shah
 *
 *         This class is used to call Https rest API request
 */
public class RestApi {

	/**
	 * Make https request and get the JSON response
	 * 
	 * @param reqUrl
	 *            - URL
	 * @return - JSON Response
	 * @throws Exception
	 */
	public String request(String reqUrl) throws Exception {
		TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return null;
			}

			public void checkClientTrusted(X509Certificate[] certs, String authType) {
			}

			public void checkServerTrusted(X509Certificate[] certs, String authType) {
			}

		} };

		SSLContext sc = SSLContext.getInstance("SSL");
		sc.init(null, trustAllCerts, new java.security.SecureRandom());
		HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

		// Create all-trusting host name verifier
		HostnameVerifier allHostsValid = new HostnameVerifier() {

			public boolean verify(String hostname, SSLSession session) {
				return true;
			}
		};
		// Install the all-trusting host verifier
		HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
		/*
		 * end of the fix
		 */

		URL url = new URL(reqUrl);
		URLConnection con = url.openConnection();
		Reader reader = new InputStreamReader(con.getInputStream());
		String json = "";
		while (true) {
			int ch = reader.read();
			if (ch == -1) {
				break;
			}
			json += (char) ch;
			System.out.print((char) ch);
		}
		return json;
	}
}
