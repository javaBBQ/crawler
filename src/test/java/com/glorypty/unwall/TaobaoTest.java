/*
 *Project: glorypty-crawler
 *File: com.glorypty.unwall.TaobaoTest.java <2015年12月23日>
 ****************************************************************
 * 版权所有@2015 国裕网络科技  保留所有权利.
 ***************************************************************/
package com.glorypty.unwall;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Before;
import org.junit.Test;

import cn.xkshow.util.regex.RegexUtil;

import com.glorypty.crawler.alibaba.AlibabaConstants;
import com.glorypty.unwall.taobao.HttpLogin;
import com.glorypty.unwall.taobao.TaobaoConstants;

/**
 *
 * @author hardy 
 * @Date 2015年12月23日 下午5:01:25
 * @version 1.0
 */
public class TaobaoTest {
	
	@Before
	public void setUp() throws Exception {
		TaobaoConstants.TPL_username="devxkshow"; 
		TaobaoConstants.TPL_password="h123456";  	
	}
	
	@Test
	public void test() throws ParseException, IOException {
		while(!HttpLogin.login()){
			System.out.println("重新登录......");
		}
		
		for (int i = 1; i <= 68; i++) {
			System.out.println(MessageFormat.format("正在请求第{0}页.....", i));
			String url = AlibabaConstants.Alibaba_PAGE_URL_HOME.replace("{0}", i+"");
			CloseableHttpClient commonClient = HttpClients.custom().setDefaultCookieStore(TaobaoConstants.cookiesSSL).build();		
			HttpGet hg4 = new HttpGet(url);
			HttpLogin.headerWrapper(hg4);
			HttpResponse httpresponse4 = commonClient.execute(hg4);  
			HttpEntity entity4 = httpresponse4.getEntity();  
			String body4 = EntityUtils.toString(entity4);  
			System.out.println(body4);
			List<String> items = RegexUtil.findMatchContents("<a class=\"list-item-title-text\".*</a>", body4);
			System.out.println(items.size());
			for (String item : items) {
//				System.out.println(item);
				System.out.println(item.substring(item.indexOf("href=\""), item.indexOf("\" gotodetail=\"2\"")).replace("href=\"", ""));
			}
			
			try {
				Thread.sleep(60*1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	}

}
