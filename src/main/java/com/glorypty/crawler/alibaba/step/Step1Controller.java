/*
 *Project: glorypty-crawler
 *File: com.glorypty.crawler.yiyao.stepStep1Controller.java
 ****************************************************************
 * 版权所有@2015 国裕网络科技  保留所有权利.
 ***************************************************************/
package com.glorypty.crawler.alibaba.step;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import cn.xkshow.util.regex.RegexUtil;

import com.glorypty.crawler.alibaba.AlibabaConstants;
import com.glorypty.crawler.base.BaseController;
import com.glorypty.unwall.taobao.HttpLogin;
import com.glorypty.unwall.taobao.TaobaoConstants;

import edu.uci.ics.crawler4j.crawler.CrawlController;

/**
 * @author yiwen
 * @date 2015年6月24日
 * @version 1.0
 */
public class Step1Controller extends BaseController {
	
	@Override
	public void door(List<String> lstUrls) throws Exception {
		
		
		AlibabaConstants.totalPage = 68;
		AlibabaConstants.totalNum = 2016;
		
		
		List<String>  lstUrls2 = new ArrayList<String>();
		for (int i = 1; i <= AlibabaConstants.totalPage; i++) {
			String url = AlibabaConstants.Alibaba_PAGE_URL_HOME.replace("{0}", i+"");
			System.out.println("homePage:"+url);
			lstUrls2.add(url);
		}
		
		TaobaoConstants.TPL_username="devxkshow"; 
		TaobaoConstants.TPL_password="h123456"; 

		while(!HttpLogin.login()){
			System.out.println("重新登录......");
		}
		
		CloseableHttpClient commonClient = HttpClients.custom().setDefaultCookieStore(TaobaoConstants.cookiesSSL).build();		
		HttpGet hg4 = new HttpGet("http://s.1688.com/company/-D2BDD2A9A1A2B1A3D1F8.html?spm=0.0.0.0.bfVtRU");
		HttpLogin.headerWrapper(hg4);
		HttpResponse httpresponse4 = commonClient.execute(hg4);  
		HttpEntity entity4 = httpresponse4.getEntity();  
		String body4 = EntityUtils.toString(entity4);  
		List<String> items = RegexUtil.findMatchContents("<a class=\"list-item-title-text\".*</a>", body4);
		System.out.println(items.size());
		for (String item : items) {
			System.out.println(item);
			System.out.println(item.substring(item.indexOf("href=\""), item.indexOf("\" gotodetail=\"2\"")).replace("href=\"", ""));
		}
		
		/*List<String>  lstUrls2 = new ArrayList<String>();
		lstUrls2.add("http://s.1688.com/company/-D2BDD2A9A1A2B1A3D1F8.html?spm=0.0.0.0.bfVtRU&pageSize=30&offset=3&beginPage=1");*/
		CrawlController crawlController = this.getController(0);
		for(String link : lstUrls2){
			crawlController.addSeed(link);
		}
		lstUrls2.clear();

		crawlController.start(Step1Crawler.class, 1);
	}

	@Override
	protected void doorError() {
		// TODO Auto-generated method stub

	}

}

