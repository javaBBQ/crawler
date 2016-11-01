package com.glorypty.crawler.menet.step;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.glorypty.crawler.base.BaseCrawler;
import com.glorypty.crawler.common.ConstantsCrawler;
import com.glorypty.crawler.menet.MenetConstants;
import com.glorypty.crawler.utils.MySqlEscape;
import com.glorypty.jdbc.CrawlerService;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.url.WebURL;

/**
 * 信息采集
 * 
 * @author Zhanglujun
 *
 */
public class MenetStep1Crawler extends BaseCrawler {

	@Override
	public boolean shouldVisit(Page page, WebURL url) {
		if (super.shouldVisit(page, url)) {
			String href = url.getURL();
			if (href.startsWith(MenetConstants.HYZX_YGXW_URL_CONTENT)
					&& (href.indexOf(".html") != -1 || href.indexOf(".shtml") != -1)
					&& !href.matches("(.*)[_](.*)[_](.*)")) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void visit(Page page) {
		String href = page.getWebURL().getURL();
		Document doc = this.getDocument(page);
		this.logger.info("抓取详细页:"+href);
		
//		this.logger.info("report_url:"+MenetConstants.report_url);
		// http://www.menet.com.cn/Articles/information/201505/201505140934253425_124550.shtml-div
		// http://www.menet.com.cn/Articles/IEconomy/201201/201201060938433843_59697.html-iframe table
		// http://www.menet.com.cn/Articles/IEconomy/201203/201203301156585658_64215.html-table
		// http://www.menet.com.cn/Articles/information/201301/20130128084901491_88277.shtml
		// http://www.menet.com.cn/Articles/information/201301/201301280851175117_88279.shtml
		//http://www.menet.com.cn/Articles/information/201211/201211150830153015_83094.html;
		if (null != doc) {
			//剔除图片
			ConstantsCrawler.clearImg(doc, href);
			
			String s = doc.select("body").html();
			if (s.indexOf("<table") != -1) {
				if (s.indexOf("<iframe") != -1 && s.indexOf("#f8fcfe") != -1) {
					visitIframeContent(doc, href);
				} else {
					visitTableContent(doc, href);
				}
			} else {
				visitDivContent(doc, href);
			}
		}
	}

	/**
	 * 数据解析及处理--IFRAME
	 * 
	 * @param doc
	 * @param url
	 */
	private void visitIframeContent(Document doc, String url) {
		try {
			Integer groupId = null; // 栏目ID
			String title = ""; // 标题
			String origin = ""; // 来源
			String originurl = ""; // 来源URL
			String desc = ""; // 简述/摘要/关键字
			String txt = ""; // 详情
		
			// 标题-分类
			String key =  MenetConstants.report_url;
			groupId = MenetConstants.getTitileMap().get(key);
					
			// 标题置于table
			Elements btitleContents = doc.select("td.nrdbt");
			if (btitleContents != null && !btitleContents.isEmpty()) {
				Element element = btitleContents.get(0);
				title = MySqlEscape.escape(element.text());
			}
			
			// 来源置于table
			Elements bsourceContents = doc
					.select("table[bgcolor=#c5d5df]>tbody>tr>td>table[align=center]>tbody>tr>td[align=right]>table>tbody>tr>td");
			if (bsourceContents != null && !bsourceContents.isEmpty()) {
				Element element = bsourceContents.get(1);
				origin = element.text();
				originurl = url;
			}
			
			// 内容置于table
			Elements bcontents = doc.select("td[bgcolor=#f8fcfe]>table>tbody>tr>td[valign=top]>table");
			if (bcontents != null && !bcontents.isEmpty()) {
				Element element = bcontents.get(0);
				txt = MySqlEscape.escape(element.html());
			}
			
			CrawlerService.executeContent(groupId, title, origin, originurl, desc, txt);
		} catch (Exception e){
			this.logger.error("执行：详情页("+ url +")： " + e);
		}
	}
		
	/**
	 * 数据解析及处理--TABLE
	 * 
	 * @param doc
	 * @param url
	 */
	private void visitTableContent(Document doc, String url) {
		try{
			Integer groupId = null; // 栏目ID
			String title = ""; // 标题
			String origin = ""; // 来源
			String originurl = ""; // 来源URL
			String desc = ""; // 简述/摘要/关键字
			String txt = ""; // 详情
			
			// 标题-分类
			String key =  MenetConstants.report_url;
			groupId = MenetConstants.getTitileMap().get(key);
			
			// 标题
			Elements titleContents = doc.select("div.STYLE64");
			if (titleContents != null && !titleContents.isEmpty()) {
				Element element = titleContents.get(0);
				title = MySqlEscape.escape(element.text());
			}
			
			// 来源
			Elements sourceContents = doc.select("td[align=left]>span.STYLE67");
			if (sourceContents != null && !sourceContents.isEmpty()) {
				Element element = sourceContents.get(0);
				origin = element.text();
				originurl = url;
				if (sourceContents.size() > 1) {
					// 关键字
					element = sourceContents.get(1);
					desc = MySqlEscape.escape(element.text());
				}
			}
			
			// 正文
			Elements contents = doc.select("td.STYLE68>p");
			if (contents != null && !contents.isEmpty()) {
				StringBuffer sp = new StringBuffer();
				for (Element element : contents) {
					sp.append("<p>");
					sp.append(element.html());
					sp.append("</p>");
				}
				txt = sp.toString();
			}
			
			Elements weicontents = doc.select("span.STYLE68");
			if (weicontents != null && !weicontents.isEmpty()) {
				Element element = weicontents.get(0);
				txt += element.html();
			}
		
			txt = MySqlEscape.escape(txt);
			
			CrawlerService.executeContent(groupId, title, origin, originurl, desc, txt);
		} catch (Exception e) {
			this.logger.error("执行：详情页("+ url +")： " + e);
		}
	}

	/**
	 * 数据解析及处理--DIV
	 * 
	 * @param doc
	 * @param url
	 */
	private void visitDivContent(Document doc, String url) {
		try {
			Integer groupId = null; // 栏目ID
			String title = ""; // 标题
			String origin = ""; // 来源
			String originurl = ""; // 来源URL
			String desc = ""; // 简述/摘要/关键字
			String txt = ""; // 详情
			
			// 分类
			String key =  MenetConstants.report_url;
			groupId = MenetConstants.getTitileMap().get(key);
	
			// 标题
			Elements titleContents = doc.select("div.newsLeftContent>div.newsDetailTitle>h1");
			if (titleContents != null && !titleContents.isEmpty()) {
				Element element = titleContents.get(0);
				title = MySqlEscape.escape(element.text());
			}
	
			// 来源
			Elements sourceContents = doc.select("div.newsLeftContent>div.newsDetailTitle>ul>li");
			if (sourceContents != null && !sourceContents.isEmpty()) {
				Element element = sourceContents.get(1);
				origin = element.text();
				originurl = url;
			}
	
			// 摘要
			Elements digstContents = doc.select("div.newsLeftContent>div.newsKeyword>ul");
			if (digstContents != null && !digstContents.isEmpty()) {
				Element element = digstContents.get(0);
				desc = MySqlEscape.escape(element.text());
			}
	
			// 正文
			Elements contents = doc.select("div.newsLeftContent>div.menetNewsDetail");
			if (contents != null && !contents.isEmpty()) {
				Element element = contents.get(0);
				txt = element.html();
			}
			
			// 标题另置一层DIV
			Elements titleContents1 = doc.select("div.newsLeftContent>div.newsDetail>dl>dt");
			if (titleContents1 != null && !titleContents1.isEmpty()) {
				Element element = titleContents1.get(0);
				title = MySqlEscape.escape(element.text());
			}
	
			// 来源另置一层DIV
			Elements sourceContents1 = doc.select("div.newsLeftContent>div.newsDetail>dl>dd");
			if (sourceContents1 != null && !sourceContents1.isEmpty()) {
				Element element = sourceContents1.get(1);
				origin = element.text();
				originurl = url;
			}
			
			// 关键字另置一层DIV
			Elements digstContents1 = doc.select("div.newsLeftContent>div.newsDetail>span.key");
			if (digstContents1 != null && !digstContents1.isEmpty()) {
				Element element = digstContents1.get(0);
				desc = MySqlEscape.escape(element.text());
			}
	
			// 内容置于另外一层DIV
			Elements contents1 = doc.select("div.newsLeftContent>div.newsDetail>ul[id=sidebar]>p");
			if (contents1 != null && !contents1.isEmpty()) {
				StringBuffer sp = new StringBuffer();
				for (Element element : contents1) {
					sp.append("<p>");
					sp.append(element.html());
					sp.append("</p>");
				}
				txt = sp.toString();
			}
	
			// http://www.menet.com.cn/Articles/yixie/201301/201301181025282528_87851.shtml
			Elements weicontents1 = doc.select("div.newsLeftContent>div.newsDetail>ul");
			if (weicontents1 != null && !weicontents1.isEmpty()
					&& weicontents1.size() > 1) {
				StringBuffer sp = new StringBuffer();
				Element element = weicontents1.get(1);
				sp = new StringBuffer();
				sp.append("<p>");
				sp.append(element.text());
				sp.append("</p>");
				txt += sp.toString();
			}
	
			// http://www.menet.com.cn/Articles/information/201302/201302040910221022_88696.shtml
			Elements weicontents2 = doc.select("div.newsLeftContent>div.zebian");
			if (weicontents2 != null && !weicontents2.isEmpty()) {
				StringBuffer sp = new StringBuffer();
				Element element = weicontents2.get(0);
				sp = new StringBuffer();
				sp.append("<p>");
				sp.append(element.text());
				sp.append("</p>");
				txt += sp.toString();
			}
			
			txt = MySqlEscape.escape(txt);
			
			CrawlerService.executeContent(groupId, title, origin, originurl, desc, txt);
		} catch (Exception e) {
			this.logger.error("执行：详情页("+ url +")： " + e);
		}	
	}

}
