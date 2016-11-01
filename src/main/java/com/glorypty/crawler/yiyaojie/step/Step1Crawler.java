/*
 *Project: glorypty-crawler
 *File: com.glorypty.crawler.yiyao.stepStep1Crawler.java
 ****************************************************************
 * 版权所有@2015 国裕网络科技  保留所有权利.
 ***************************************************************/
package com.glorypty.crawler.yiyaojie.step;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.glorypty.crawler.base.BaseCrawler;
import com.glorypty.crawler.yiyaojie.YiYaojieConstants;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.url.WebURL;

/**
 * @author yiwen
 * @date 2015年6月24日
 * @version 1.0
 */
public class Step1Crawler extends BaseCrawler {
	
	@Override
	public boolean shouldVisit(Page page, WebURL url) {
//		return super.shouldVisit(page, url);
		return true;
	}

	@Override
	public void visit(Page page) {
		String href = page.getWebURL().getURL();
		if(href.indexOf(YiYaojieConstants.YYJie_PAGE_URL_HOME_PRE)!=-1
				||href.indexOf(YiYaojieConstants.YYJie_SJJY_PAGE_URL_HOME_PRE)!=-1
				||href.indexOf(YiYaojieConstants.YYJie_ZJRY_PAGE_URL_HOME_PRE)!=-1
				){
			visitToCommentPage(page);
		}
	}

	/**
	 * @author sunny
	 * @param doc
	 * @param href
	 */
	private void visitToCommentPage(Page page) {
		boolean isFinish = false;
		String href = page.getWebURL().getURL();
		this.logger.info("医药界解析资讯列表页："+href);
		Document doc = this.getDocument(page);
		if(doc!=null){
			Elements elements = doc.select("div[class=news_list_l fl]>ul>li>a");
			if(elements!=null&&!elements.isEmpty()){
				for (Element element : elements) {
					String url = element.getElementsByAttributeValue("class", "fl").attr("href");
					YiYaojieConstants.detail_list.add(YiYaojieConstants.URL_CONTENT_PRE+url);
					}
				}
				int nowlist = YiYaojieConstants.detail_list.size();
				if (YiYaojieConstants.totalNum<=nowlist) {
					isFinish = true;
				}
				}
		
		if(isFinish){
			if(YiYaojieConstants.detail_list!=null&&YiYaojieConstants.detail_list.size()>0){
				try {
					new Step2Controller().door(YiYaojieConstants.detail_list);
					YiYaojieConstants.detail_list.clear();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

}

