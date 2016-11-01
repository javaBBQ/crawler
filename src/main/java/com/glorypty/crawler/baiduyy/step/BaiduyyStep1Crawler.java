package com.glorypty.crawler.baiduyy.step;

import org.apache.commons.lang.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.glorypty.crawler.baiduyy.BaiduyyConstants;
import com.glorypty.crawler.base.BaseCrawler;
import com.glorypty.crawler.common.ConstantsCrawler;
import com.glorypty.crawler.common.ConstantsCrawler.AllianceTypeEnum;
import com.glorypty.crawler.utils.HttpMethodSimulate;
import com.glorypty.crawler.utils.MySqlEscape;
import com.glorypty.jdbc.CrawlerService;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.url.WebURL;

public class BaiduyyStep1Crawler extends BaseCrawler {

	@Override
	public boolean shouldVisit(Page page, WebURL url) {
		boolean flag = false;
//		String parent = page.getWebURL().getParentUrl();
		if(super.shouldVisit(page, url)){
			String href = url.getURL();
			do{
				// 招标新闻,新品抢鲜,警示平台,企业之窗,药价动态,政策法规
				if(href.indexOf(BaiduyyConstants.XPQX_URL_CONTENT)!=-1){
					flag =  true;
					break;
				}
				// 招标信息
				if(href.indexOf(BaiduyyConstants.ZBXX_URL_CONTENT_PRE)!=-1 ){
					flag =  true;
					break;
				}
				// 代理信息
				if(href.indexOf(BaiduyyConstants.DLXX_URL_CONTENT_PRE)!=-1){
					flag = true;
					break;
				}
				// 求购信息
				if(href.indexOf(BaiduyyConstants.QGXX_URL_CONTENT_PRE)!=-1){
					flag =  true;
					break;
				}
				// 药价信息
				if(href.indexOf(BaiduyyConstants.YJWJ_URL_CONTENT_PRE)!=-1){
					flag =  true;
					break;
				}
				// 招商信息
				if(href.indexOf(BaiduyyConstants.ZSXX_URL_CONTENT_PRE)!=-1){
					flag =  true;
					break;
				}
				// 展会论坛
				if(href.indexOf(BaiduyyConstants.YYZH_URL_CONTENT)!=-1){
					flag =  true;
					break;
				}
			}while(false);
		}
		return flag;
	}

	@Override
	public void visit(Page page) {
		
		String href = page.getWebURL().getURL();
		String parent = page.getWebURL().getParentUrl();
		this.logger.info("抓取信息启动:"+href);
		
		if(parent!=null&&!"".equals(parent)){
			do{
				// 招标新闻,新品抢鲜,警示平台,企业之窗,药价动态,政策法规
				if(parent.indexOf(BaiduyyConstants.XPQX_PAGE_URL_HOME_PRE) != -1 
						&& href.startsWith(BaiduyyConstants.XPQX_URL_CONTENT)){
					visitToContent(page,parent);
					break;
				}
				//招标信息
				if(parent.indexOf(BaiduyyConstants.ZBXX_PAGE_URL_HOME_PRE) != -1
						&& href.startsWith(BaiduyyConstants.ZBXX_URL_CONTENT_PRE)){
					visitZbxxToContent(page,parent);
					break;
				}
				//求购信息
				if(parent.indexOf(BaiduyyConstants.QGXX_PAGE_URL_HOME_PRE) != -1
						&& href.startsWith(BaiduyyConstants.QGXX_URL_CONTENT_PRE)){
					visitQgxxToContent(page,parent);
					break;
				}
				//药价文件
				if(parent.indexOf(BaiduyyConstants.YJWJ_PAGE_URL_HOME_PRE) != -1
						&& href.startsWith(BaiduyyConstants.YJWJ_URL_CONTENT_PRE)){
					visitYjwjToContent(page,parent);
					break;
				}
				//招商信息
				if(parent.indexOf(BaiduyyConstants.ZSXX_PAGE_URL_HOME_PRE) != -1
						&& href.startsWith(BaiduyyConstants.ZSXX_URL_CONTENT_PRE)){
					visitZsxxToContent(page,parent);
					break;
				}
				//代理信息
				if(href.indexOf(BaiduyyConstants.DLXX_URL_CONTENT_PRE)!=-1){
					visitDlxxToContent(href);
					break;
				}
				// 展会论坛
				if(href.indexOf(BaiduyyConstants.YYZH_URL_CONTENT)!=-1){
					visitYyzhToContent(page);
					break;
				}
				
			}while(false);
		}
			
	}
	
	/**
	 * 展会论坛
	 * @param page
	 */
	private void visitYyzhToContent(Page page) {
		Document doc = this.getDocument(page);
		if(doc!=null){
			//剔除图片
			ConstantsCrawler.clearImg(doc, page.getWebURL().getURL());
			String title = doc.select("td[class=font_14cu]").get(0).html();
			Integer groupId = 105;
			String source = "百度虫";
			String content = doc.select("form table").get(4).child(0).child(0).child(0).html();
			
			CrawlerService.executeContent(groupId, MySqlEscape.escape(title), 
					MySqlEscape.escape(source), page.getWebURL().getURL(), "", MySqlEscape.escape(content));
		}

	}

	/**
	 * 招商信息
	 * @param page
	 * @param parent
	 */
	private void visitZsxxToContent(Page page, String parent) {
		
		Document doc = this.getDocument(page);
		if(doc !=  null){
			//剔除图片
			ConstantsCrawler.clearImg(doc, page.getWebURL().getURL());
			
			String title = "";
			String content = "";
			
			title = doc.getElementById("kk").html();
			
//			//处理图片
//			Elements elements = doc.getElementById("gjhy").child(0).child(0).child(1).select("img");
//			for (Element e : elements) {
//				e.attr("src", BaiduyyConstants.ZSXX_WEB_SETTING + e.attr("src"));
//			}
			
			content = doc.select("td[class=font_20lv]").get(0).parent().parent().parent().html();
			
			CrawlerService.executeAlliance(AllianceTypeEnum.AGENT, MySqlEscape.escape(title),MySqlEscape.escape(content));
		}
		
	}

	private void visitYjwjToContent(Page page, String parent) {
		Document doc = this.getDocument(page);
		if(doc !=  null){
			//剔除图片
			ConstantsCrawler.clearImg(doc, page.getWebURL().getURL());
			
			String source = "";
			String title = "";
			String content = "";
			Integer groupId = 101;
			
			source = doc.select("td[class=font_14hei]").get(0).select("tr td").html();
			title = doc.select("span[class=taitou]").get(0).html();
			
			//处理文件
			Elements elements =  doc.select("td[class=font_14hei]").get(1).select("a");
			if(elements != null){
				for (Element e : elements) {
					e.attr("href", BaiduyyConstants.YJWJ_WEB_SETTING + e.attr("href"));
				}
			}
			content = doc.select("td[class=font_14hei]").get(1).html().replaceAll("div", "span");
			
			CrawlerService.executeContent(groupId, MySqlEscape.escape(title), 
					MySqlEscape.escape(source), page.getWebURL().getURL(), "", MySqlEscape.escape(content));
		}
	}

	private void visitQgxxToContent(Page page, String parent) {
		Document doc = this.getDocument(page);
		if(doc !=  null){
			//剔除图片
			ConstantsCrawler.clearImg(doc, page.getWebURL().getURL());
			
			String source = "";
			String title = "";
			String content = "";
			Integer groupId = 109;
			
			title = doc.select("span strong").html();
			
			Elements elements = doc.select("img");
			for (Element e : elements) {
				e.attr("src",BaiduyyConstants.QGXX_URL_FOR_IMAGE + e.attr("src"));
			}
			
			content = doc.select("table").get(2).child(0).child(0).child(0).html();
			
			CrawlerService.executeContent(groupId, MySqlEscape.escape(title), 
					MySqlEscape.escape(source), page.getWebURL().getURL(), "", MySqlEscape.escape(content));
		}
	}

	/**
	 * 代理信息
	 * @param href
	 */
	//TODO...分页到详情页是一个异步请求
	private void visitDlxxToContent(String href) {
		href = BaiduyyConstants.DLXX_URL_CONTENT_PRE_TRUE+href.substring(href.indexOf("?"),href.lastIndexOf("&"));
		Document doc = HttpMethodSimulate.getReqSimulate(href);
		
		if(doc !=  null){
			//剔除图片
			ConstantsCrawler.clearImg(doc, href);
			
			String title = "";
			String content = "";
			title = doc.getElementById("subject").child(0).html();
			String company = doc.getElementById("company").html();
			content = doc.select("table").get(1).html() + "\n企业名称：" + company;
			
			CrawlerService.executeAlliance(AllianceTypeEnum.AGENT, MySqlEscape.escape(title),MySqlEscape.escape(content));
		}
		
	}

	private void visitZbxxToContent(Page page, String parent) {
		Document doc = this.getDocument(page);
		if(doc !=  null){
			//剔除图片
			ConstantsCrawler.clearImg(doc, page.getWebURL().getURL());
			
			String source = "";
			String title = "";
			String content = "";
			Integer groupId = 108;
			
			title = doc.select("td[class=biaoti]").get(0).html();
			
			source = doc.select("span[class=zbxx_zt]").get(0).html();
			content = doc.getElementById("table1").child(0).child(5).child(0).html().replaceAll("div", "span");
			
//			logger.info(title);
//			logger.info(StringUtils.isEmpty(source) ? "百度虫" : source);
//			logger.info(content);
			
			CrawlerService.executeContent(groupId, MySqlEscape.escape(title), 
					MySqlEscape.escape(StringUtils.isEmpty(source) ? "百度虫" : source), page.getWebURL().getURL(), "", MySqlEscape.escape(content));
		}
	}

	/***/
	private void visitToContent(Page page, String parent) {
		String source = "";
		String title = "";
		String content = "";
		Integer groupId = getGroupId(parent);
		
		Document doc = this.getDocument(page);
		if(doc !=  null){
			//剔除图片
			ConstantsCrawler.clearImg(doc, page.getWebURL().getURL());
			
			title = doc.getElementById("title").html();
			source = doc.getElementById("ly").html();
			content = doc.select("table[width=630]").get(0).select("td[colspan=4]").get(2).html().replaceAll("div", "span");
			
//			logger.info(groupId);
//			logger.info(title);
//			logger.info(source);
//			logger.info(content);
			
			CrawlerService.executeContent(groupId, MySqlEscape.escape(title), 
					MySqlEscape.escape(source), page.getWebURL().getURL(), "", MySqlEscape.escape(content));
		}
	}

	private Integer getGroupId(String parent) {
		Integer groupId = null;
		do{
			if(parent.indexOf("uid=7")!=-1){//新品抢鲜
				groupId = 100;
				break;
			}
			if(parent.indexOf("uid=5")!=-1){//警示平台
				groupId = 100;
				break;
			}
			if(parent.indexOf("uid=4")!=-1){//企业之窗
				groupId = 100;
				break;
			}
			if(parent.indexOf("uid=3")!=-1){//药价动态
				groupId = 104;
				break;
			}
			if(parent.indexOf("uid=2")!=-1){//政策法规
				groupId = 101;
				break;
			}
			if(parent.indexOf("uid=1")!=-1){//招标新闻
				groupId = 108;
				break;
			}
		}while(false);
		return groupId;
	}
	
}
