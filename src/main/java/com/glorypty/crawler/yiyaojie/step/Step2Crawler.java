package com.glorypty.crawler.yiyaojie.step;



import org.jsoup.nodes.Document;

import com.glorypty.crawler.base.BaseCrawler;
import com.glorypty.crawler.common.ConstantsCrawler;
import com.glorypty.crawler.utils.MySqlEscape;
import com.glorypty.crawler.yiyaojie.YiYaojieConstants;
import com.glorypty.jdbc.CrawlerService;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.url.WebURL;

/**
 * 信息采集
 * @author yiwen
 *
 */
public class Step2Crawler extends BaseCrawler {

	@Override
	public boolean shouldVisit(Page page, WebURL url) {
		if(super.shouldVisit(page, url)){
			String href = url.getURL();
			String parent = url.getParentUrl();
			if(href.indexOf(YiYaojieConstants.COMMON_URL_CONTENT)!=-1
					 && href.indexOf("html")!=-1){
				if(parent.indexOf(YiYaojieConstants.YYJie_PAGE_URL_HOME_PRE)!=-1
						||parent.indexOf(YiYaojieConstants.YYJie_SJJY_PAGE_URL_HOME_PRE)!=-1
						||parent.indexOf(YiYaojieConstants.YYJie_ZJRY_PAGE_URL_HOME_PRE)!=-1){
					return false;
				}
				return true;
			}
		}
		return false;
	}

	@Override
	public void visit(Page page) {
		try {
			String href = page.getWebURL().getURL();
			Document doc = this.getDocument(page);
			if(doc!=null){
				//剔除图片
				ConstantsCrawler.clearImg(doc, href);
				
				if(href.indexOf(YiYaojieConstants.YYJie_PAGE_URL_HOME_PRE)!=-1){
					visitToContent(doc,href,106);
				}else if(href.indexOf(YiYaojieConstants.YYJie_SJJY_PAGE_URL_HOME_PRE)!=-1||href.indexOf(YiYaojieConstants.YYJie_ZJRY_PAGE_URL_HOME_PRE)!=-1){
					visitToContent(doc,href,107);
				}
			}
		} catch (Exception e) {
			this.logger.error(e);
		}
		
	}
	
	/**
	 * 106  热点话题
	 * http://www.yiyaojie.com/ft/ygzc/
	 * 107  精英访谈
	 * http://www.yiyaojie.com/ft/sjjy/
	 * http://www.yiyaojie.com/ft/zjry/
	 * @param doc
	 * @param href
	 */
	private void visitToContent(Document doc, String href,int groupid) {
		this.logger.info("医药界解析详细页:"+href);
		
		String source = "";
		String sourceHtml = "";
		String title = "";
		String content = "";
		String desc = "";
		
		Integer groupId = new Integer(groupid);
		
		org.jsoup.nodes.Element sourceElement = doc.select("div[class=lh30 f12 grey mt10 n3 mb20 tc]>span").get(1);
		
		if (null!=sourceElement) {
			sourceHtml = sourceElement.html();
			if (sourceHtml.length()>100) {
				source = "医药界";
			}else if(-1!=sourceHtml.indexOf("来源：")){
				source = sourceHtml.substring(sourceHtml.indexOf("来源：")+3, sourceHtml.length());
			}else{
				source = sourceHtml;
			}
		}else{
			source="";
		}
		title =   doc.select("div[class=news_list_l fl]>h4").get(0).html();
		content =   doc.select("div[class=lh24 f14 mt10 t2 aa]").html().replaceAll("div", "span");
		CrawlerService.executeContent(groupId, MySqlEscape.escape(title), 
				MySqlEscape.escape(source), MySqlEscape.escape(href), desc, MySqlEscape.escape(content));
	}
}
