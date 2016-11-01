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
import com.glorypty.crawler.common.ConstantsCrawler;
import com.glorypty.unwall.taobao.HttpLogin;
import com.glorypty.unwall.taobao.TaobaoConstants;

import edu.uci.ics.crawler4j.crawler.CrawlController;

/**
 * 
 * @author yiwen
 *
 */
public class Step2Controller extends BaseController {

	@Override
	public void door(List<String> lstUrls) throws Exception {
		/*if(null==lstUrls || lstUrls.isEmpty())
			return;*/
		/*List<String>  lstUrls2 = new ArrayList<String>();
		lstUrls2.add("http://sxjlyy.1688.com/?spm=0.0.0.0.nry7wj");*/
		
		AlibabaConstants.totalPage = 68;
		AlibabaConstants.totalNum = 2016;
		
		
		List<String>  lstUrls2 = new ArrayList<String>();
		TaobaoConstants.TPL_username="devxkshow"; 
		TaobaoConstants.TPL_password="h123456"; 
		
		while(!HttpLogin.login()){
			System.out.println("重新登录......");
		}
		
		for (int i = 1; i <= AlibabaConstants.totalPage; i++) {
			CloseableHttpClient commonClient = HttpClients.custom().setDefaultCookieStore(TaobaoConstants.cookiesSSL).build();		
			String url = AlibabaConstants.Alibaba_PAGE_URL_HOME.replace("{0}", i+"");
			System.out.println("homePage:"+url);

				HttpGet hg4 = new HttpGet(url);
				HttpLogin.headerWrapper(hg4);
				HttpResponse httpresponse4 = commonClient.execute(hg4);  
				HttpEntity entity4 = httpresponse4.getEntity();  
				String body4 = EntityUtils.toString(entity4);  
				List<String> items = RegexUtil.findMatchContents("<a class=\"list-item-title-text\".*</a>", body4);
				
				for (String item : items) {
					lstUrls2.add(item.substring(item.indexOf("href=\""), item.indexOf("\" gotodetail=\"2\"")).replace("href=\"", ""));
				}
				
				/*lstUrls2.add("http://plastiflexsz.1688.com/?spm=a261y.7663282.0.0.1NfHRi");*/
				CrawlController crawlController = this.getController(0);
				for(String link : lstUrls2){
					crawlController.addSeed(link);
				}
				lstUrls2.clear();
				crawlController.start(Step2Crawler.class, 1);
		}
	}

	@Override
	protected void doorError() {
		
	}

}
