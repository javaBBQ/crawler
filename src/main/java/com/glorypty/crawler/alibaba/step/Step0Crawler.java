package com.glorypty.crawler.alibaba.step;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.glorypty.crawler.alibaba.AlibabaConstants;
import com.glorypty.crawler.base.BaseCrawler;

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
			List<String> list = AlibabaConstants.getUrlList();
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

	public static void main(String[] args) {
		String ss = "共6822页";
		System.out.println(ss.substring(1, ss.length()-1));
	}
	@Override
	public void visit(Page page) {
			List<String> urlList = new ArrayList<String>();
			String href = page.getWebURL().getURL();
			Document doc = this.getDocument(page);
			if(doc!=null){
				/*如果是登录或者验证码页面*/
				if(href.indexOf(AlibabaConstants.Alibaba_INVALID_HOME)!=-1){
					//登录
					
					
					//识别验证码
				}
				
				
				
				/*div[class=list-item-detail]>div[class=detail-left]>div[class=detail-float-items]>a*/
				Elements elements = doc.select("div[class=page-op]>span[class=total-page]");
				Elements elementsTotalNum = doc.select("#sw_mod_navigatebar>ul>li>em[class=sm-navigatebar-count]");
				if(elements!=null){
					if(href.indexOf(AlibabaConstants.Alibaba_URL_HOME)!=-1){
						String pages= elements.get(0).text();
						if(pages.length()>1){
							pages = pages.substring(1, pages.length()-1);
						}
						
						AlibabaConstants.totalPage = Integer.parseInt(pages);
						
						AlibabaConstants.totalNum = Integer.parseInt(elementsTotalNum.get(0).text());
						
						for (int i = 1; i <= AlibabaConstants.totalPage; i++) {
							String url = "";
							if (href.indexOf(AlibabaConstants.Alibaba_URL_HOME)!=-1) {
								url = AlibabaConstants.Alibaba_PAGE_URL_HOME.replace("{0}", i+"");
							}
							System.out.println("homePage:"+url);
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
