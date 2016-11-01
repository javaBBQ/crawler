/*
 *Project: glorypty-crawler
 *File: com.glorypty.unwall.taobao.HttpLogin.java <2015年12月23日>
 ****************************************************************
 * 版权所有@2015 国裕网络科技  保留所有权利.
 ***************************************************************/
package com.glorypty.unwall.taobao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.AbstractHttpMessage;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

/**
 *
 * @author hardy
 * @Date 2015年12月23日 下午4:50:01
 * @version 1.0
 */
public class HttpLogin {
	protected final static Logger logger = Logger.getLogger(HttpLogin.class);	

	public static void headerWrapper(AbstractHttpMessage methord) {
		methord.setHeader(
				"user-agent",
				"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.93 Safari/537.36");
		methord.setHeader("accept", "*/*");
		methord.setHeader("accept-language", "zh-CN");
		methord.setHeader("Accept-Encoding", "gzip, deflate");

	}

	public static String getCodeUrl() {
		CloseableHttpClient httpClient = HttpValidataCode
				.createSSLClientDefault(true);
		HttpPost hp = new HttpPost(
				"https://login.taobao.com/member/request_nick_check.do?_input_charset=utf-8");
		headerWrapper(hp);
		hp.setHeader("Accept", "application/json, text/javascript, */*; q=0.01");
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("username",
				TaobaoConstants.TPL_username));
		HttpResponse httpresponse;
		try {
			hp.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
			httpresponse = httpClient.execute(hp);
			HttpEntity entity = httpresponse.getEntity();
			String body = EntityUtils.toString(entity);
			logger.debug(body);
			EntityUtils.consume(entity);
			JSONObject J_obj = JSONObject.fromObject(body);
			boolean isNeed = (Boolean) J_obj.get("needcode");
			logger.debug("needcode:" + isNeed);
			if (isNeed) {
				String code_url = (String) J_obj.get("url");
				logger.debug("code_url:" + code_url);
				return code_url;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}
	
	public static boolean login() {
		String codeUrl = HttpLogin.getCodeUrl();
		Scanner sc = new Scanner(System.in);
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		if(codeUrl != null && !codeUrl.equals("")){
			HttpValidataCode.handleVilidateCode(codeUrl);
			System.out.println("请输入验证码：");
			String TPL_checkcode = sc.next();
			params.add(new BasicNameValuePair("TPL_checkcode", TPL_checkcode)); 
		}
						
		try {
			CloseableHttpClient httpClient = HttpValidataCode.createSSLClientDefault(true);
			HttpPost httpPost = new HttpPost("https://login.taobao.com/member/login.jhtml");
			HttpLogin.headerWrapper(httpPost);
			httpPost.setHeader("accept-Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
			
			params.add(new BasicNameValuePair("TPL_password", TaobaoConstants.TPL_password));  
			params.add(new BasicNameValuePair("TPL_username", TaobaoConstants.TPL_username));
			params.add(new BasicNameValuePair("newlogin", "1"));   
			params.add(new BasicNameValuePair("callback", "1"));  
			httpPost.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));  
			// 发送请求  
			HttpResponse httpresponse = httpClient.execute(httpPost);  
			HttpEntity entity = httpresponse.getEntity();  
			String body = EntityUtils.toString(entity); 
			if(body.contains("\"state\":false")){
				System.out.println("【输入有误】验证失败，请重新拖动滑块完成验证");
				return false;
			}			
			String sessionid = body.substring(body.indexOf("token")+8, body.length()-3);
			System.out.println("sessionID:"+ sessionid);
			
			HttpGet hg1 = new HttpGet("https://passport.alipay.com/mini_apply_st.js?site=0&token="+sessionid+"&callback=vstCallback65");
			HttpLogin.headerWrapper(hg1);
			HttpResponse httpresponse1 = httpClient.execute(hg1);
			HttpEntity entity1 = httpresponse1.getEntity();  
			String body1 = EntityUtils.toString(entity1);
			
			String st = "";
			String regex = "vstCallback65\\((.*)\\)";
			Pattern compile = Pattern.compile(regex);
			Matcher m = compile.matcher(body1);
			while(m.find()){
				String group = m.group(1);
				JSONObject string2Json = JSONObject.fromObject(group);
				try {
					JSONObject object = (JSONObject) string2Json.get("data");
					st = (String) object.get("st");
					System.out.println(st); 
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}  
					
			HttpGet hg2 = new HttpGet("https://login.taobao.com/member/vst.htm?st="+st+"&params=style%3Dminisimple%26sub%3Dtrue%26TPL_username%3D"+TaobaoConstants.TPL_username+"%26loginsite%3D0%26from_encoding%3D%26not_duplite_str%3D%26guf%3D%26full_redirect%3D%26isIgnore%3D%26need_sign%3D%26sign%3D%26from%3Ddatacube%26TPL_redirect_url%3Dhttp%25253A%25252F%25252Fmofang.taobao.com%25252Fs%25252Flogin%26css_style%3D%26allp%3D&_ksTS=1404787873165_78&callback=jsonp79");
			HttpLogin.headerWrapper(hg2);
			HttpResponse httpresponse2 = httpClient.execute(hg2);  
			HttpEntity entity2 = httpresponse2.getEntity();  
			String body2 = EntityUtils.toString(entity2);  
//			System.out.println(body2);  
			sc.close();
			
			CloseableHttpClient commonClient = HttpClients.custom().setDefaultCookieStore(TaobaoConstants.cookiesSSL).build();		
			HttpGet hg4 = new HttpGet("http://i.taobao.com");
			HttpLogin.headerWrapper(hg4);
			HttpResponse httpresponse4 = commonClient.execute(hg4);  
			HttpEntity entity4 = httpresponse4.getEntity();  
			String body4 = EntityUtils.toString(entity4);  
			if(body4.contains("<title>我的淘宝</title>")){
				System.out.println("登录成功");
				return true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("登录失败");
		return false;
	}
	

}
