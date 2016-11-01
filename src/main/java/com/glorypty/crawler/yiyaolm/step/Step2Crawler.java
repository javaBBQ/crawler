package com.glorypty.crawler.yiyaolm.step;



import org.jsoup.nodes.Document;

import com.glorypty.crawler.base.BaseCrawler;
import com.glorypty.crawler.common.ConstantsCrawler;
import com.glorypty.crawler.utils.MySqlEscape;
import com.glorypty.crawler.yiyaolm.YiYaolmConstants;
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
			if(href.indexOf(YiYaolmConstants.COMMON_URL_CONTENT)!=-1
					 && href.indexOf("shtml")!=-1){
				if(parent.indexOf(YiYaolmConstants.YYYX_PAGE_URL_HOME_PRE)!=-1){
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
				
				if(href.indexOf(YiYaolmConstants.COMMON_URL_CONTENT)!=-1){
					visitToContent(doc,href);
				}
			}
		} catch (Exception e) {
			this.logger.error(e);
		}
		
	}
	
	/**
	 * 131  生意经 
	 * @param doc
	 * @param href
	 */
	private void visitToContent(Document doc, String href) {
		this.logger.info("中国医药联盟解析详细页:"+href);
		
		String source = "";
		String sourceHtml = "";
		String title = "";
		String content = "";
		String desc = "";
		Integer groupId = new Integer(131);
		
		org.jsoup.nodes.Element sourceElement = doc.select("div[class=arti-atttibute]>span").get(1);
		
		
		
		if (null!=sourceElement) {
			sourceHtml = sourceElement.html();
			if (sourceHtml.length()>100) {
				source = "中国医药联盟";
			}else if(-1!=sourceHtml.indexOf("来源：")){
				source = sourceHtml.substring(sourceHtml.indexOf("来源：")+3, sourceHtml.length());
			}else{
				source = sourceHtml;
			}
		}else{
			source="";
		}
		title =   doc.select("div[class=content-wrap  fll-new]>h1").get(0).html();
		org.jsoup.nodes.Element descElement = doc.getElementById("ctrlfssummary");
		if (null!=descElement) {
			desc = descElement.html();
		}
		content = doc.getElementById("ctrlfscont").html().replaceAll("div", "span");
		
		CrawlerService.executeContent(groupId, MySqlEscape.escape(title), 
				MySqlEscape.escape(source), MySqlEscape.escape(href), desc, MySqlEscape.escape(content));
	}
	
//	public static void main(String[] args) {
//		String sourceHtml = "来源：药店经营周报";
//		String a = sourceHtml.substring(sourceHtml.indexOf("来源：")+3, sourceHtml.length());
//		System.out.println(a);
//	}

}
