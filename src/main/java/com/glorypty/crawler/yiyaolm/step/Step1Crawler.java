/*
 *Project: glorypty-crawler
 *File: com.glorypty.crawler.yiyao.stepStep1Crawler.java
 ****************************************************************
 * 版权所有@2015 国裕网络科技  保留所有权利.
 ***************************************************************/
package com.glorypty.crawler.yiyaolm.step;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.glorypty.crawler.base.BaseCrawler;
import com.glorypty.crawler.yiyaolm.YiYaolmConstants;

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
		if(href.indexOf(YiYaolmConstants.YYYX_PAGE_URL_HOME_PRE)!=-1
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
		this.logger.info("中国医药联盟网解析资讯列表页："+href);
		Document doc = this.getDocument(page);
		if(doc!=null){
			Elements elements = doc.select("div[class=fl-newlist]>ul>li>a");
			if(elements!=null&&!elements.isEmpty()){
				for (Element element : elements) {
					String url = element.getElementsByAttributeValue("class", "new-tit").attr("href");
					YiYaolmConstants.detail_list.add(url);
					}
				}
				
			Elements page_elements = doc.select("div[class=page]>ul>li>a[class=now]");
			int currentPage = Integer.parseInt(page_elements.text());
			if(YiYaolmConstants.totalPage == currentPage){
				try {
					isFinish = true;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		if(isFinish){
			if(YiYaolmConstants.detail_list!=null&&YiYaolmConstants.detail_list.size()>0){
				try {
					new Step2Controller().door(YiYaolmConstants.detail_list);
					YiYaolmConstants.detail_list.clear();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

}

