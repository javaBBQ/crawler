/*
 *Project: glorypty-crawler
 *File: com.glorypty.unwall.hc360.HcConstants.java <2016年2月22日>
 ****************************************************************
 * 版权所有@2016 国裕网络科技  保留所有权利.
 ***************************************************************/
package com.glorypty.unwall.hc360;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;

import org.apache.http.client.CookieStore;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

/**
 *
 * @author hardy 
 * @Date 2016年2月22日 下午2:54:27
 * @version 1.0
 */
public class HcConstants {
	public static CloseableHttpClient httpClient = null;
	public static CookieStore cookiesSSL = new BasicCookieStore();
	public static CookieStore cookies = new BasicCookieStore();
	
	public static String URL_LOGIN_PAGE = "https://sso.hc360.com/ssologin";
	public static String URL_LOGIN = "https://sso.hc360.com/ssologin";
	public static String URL_WELCOME = "http://my.b2b.hc360.com/my/turbine/template/corcenter,company,salemain.html";
	public static String URL_VCODE = "http://detail.b2b.hc360.com/detail/JumblyValidateImage.jsp?seed={0}";
	
	public static String accID="l1192821822";	
	public static String accPWD="hc2015";	
	public static String URI_VCODE = "target/vcode/hc360/{0}.jpeg"; // 验证码输出路径
	
	public static CloseableHttpClient createSSLClientDefault(boolean isSSL){
		if(isSSL){
			SSLContext sslContext = null;
			try {
				sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
					//信任所有
					public boolean isTrusted(X509Certificate[] chain,
							String authType) throws CertificateException {
						return true;
					}
				}).build();
			} catch (KeyManagementException e) { 
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (KeyStoreException e) {
				e.printStackTrace();
			}
			if(null != sslContext){
				SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);
				return HttpClients.custom().setSSLSocketFactory(sslsf).setDefaultCookieStore(cookiesSSL).build();
			}else{
				return  HttpClients.custom().setDefaultCookieStore(cookies).build();
			}
		}else{
			return  HttpClients.custom().setDefaultCookieStore(cookies).build();
		}
	}
	

}
