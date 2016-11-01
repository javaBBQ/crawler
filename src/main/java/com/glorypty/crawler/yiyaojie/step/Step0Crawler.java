package com.glorypty.crawler.yiyaojie.step;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.glorypty.crawler.base.BaseCrawler;
import com.glorypty.crawler.yiyao.YiYaoConstants;
import com.glorypty.crawler.yiyaojie.YiYaojieConstants;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.url.WebURL;

/**
 * 列表页抓取
 * @author yiwen
 *
 */
public class Step0Crawler extends BaseCrawler {

	public int page1 = 1;
	
	public static List<String> list = null;
	@Override
	public boolean shouldVisit(Page page, WebURL url) {
		if(super.shouldVisit(page, url)){
			String href = url.getURL();
			List<String> list = YiYaojieConstants.getUrlList();
			if(list!=null&&list.size()>0){
				for (String str : list) {
					if(href.indexOf(str)!=-1){
						return true;
					}
				}
			}
		}
		return false;
	}

	@Override
	public void visit(Page page) {
			List<String> urlList = new ArrayList<String>();
			String href = page.getWebURL().getURL();
			Document doc = this.getDocument(page);
			if(doc!=null){
				Elements elements = doc.select("div[class=pages mt20]>span[class=pageinfo]>strong");
				if(elements!=null){
					if(href.indexOf(YiYaojieConstants.YYJie_URL_HOME)!=-1||href.indexOf(YiYaojieConstants.YYJie_SJJY_URL_HOME)!=-1||href.indexOf(YiYaojieConstants.YYJie_ZJRY_URL_HOME)!=-1){
						String pages= elements.get(0).text();
						YiYaojieConstants.totalPage = Integer.parseInt(elements.get(0).text());
						YiYaojieConstants.totalNum = Integer.parseInt(elements.get(1).text());
						for (int i = 1; i <= YiYaojieConstants.totalPage; i++) {
							String url = "";
							if (href.indexOf(YiYaojieConstants.YYJie_URL_HOME)!=-1) {
								url = YiYaojieConstants.YYJie_PAGE_URL_HOME.replace("{0}", i+"");
							}else if(href.indexOf(YiYaojieConstants.YYJie_SJJY_URL_HOME)!=-1){
								url = YiYaojieConstants.YYJie_SJJY_PAGE_URL_HOME.replace("{0}", i+"");
							}else if(href.indexOf(YiYaojieConstants.YYJie_ZJRY_URL_HOME)!=-1){
								url = YiYaojieConstants.YYJie_ZJRY_PAGE_URL_HOME.replace("{0}", i+"");
							}
							urlList.add(url);
						}
//						YiYaojieConstants.totalNum=urlList.size();
						if(urlList!=null&&urlList.size()>0){
							try {
								this.logger.info(MessageFormat.format("爬虫预爬取数据汇总：共{0}条", urlList.size()));
								new Step1Controller().door(urlList);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
					}
					
			}
			
			
		}
	
	
	
	
}
