package com.glorypty.crawler.yiyao.step;


import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.glorypty.crawler.base.BaseCrawler;
import com.glorypty.crawler.common.ConstantsCrawler;
import com.glorypty.crawler.common.ConstantsCrawler.AllianceTypeEnum;
import com.glorypty.crawler.utils.MySqlEscape;
import com.glorypty.crawler.yiyao.YiYaoConstants;
import com.glorypty.jdbc.CrawlerService;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.url.WebURL;

/**
 * 信息采集
 * @author ZhangLujun
 *
 */
public class Step2Crawler extends BaseCrawler {

	@Override
	public boolean shouldVisit(Page page, WebURL url) {
		if(super.shouldVisit(page, url)){
			String href = url.getURL();
			String parent = url.getParentUrl();
			if(href.indexOf(YiYaoConstants.COMMON_URL_CONTENT)!=-1
					 && href.indexOf("php")==-1){
				if(parent.indexOf(YiYaoConstants.DLXX_PAGE_URL_HOME_PRE)!=-1){
					return false;
				}
				return true;
			}else if(href.indexOf(YiYaoConstants.YYZBZB_PAGE_URL_CONTENT)!=-1
					 && href.indexOf("php")==-1){
				return true;
			}else  if(href.indexOf(YiYaoConstants.YYZS_PAGE_URL_CONTENT)!=-1
					&& href.indexOf(",")==-1 && href.indexOf("php")==-1){
				return true;
			}else if(href.indexOf(YiYaoConstants.DLXX_PAGE_URL_HOME_PRE)!=-1
					&&href.indexOf(",")==-1&&href.indexOf(",")==href.lastIndexOf(",")
					&&href.indexOf("php")==-1){
				return true;
			}else if(href.indexOf(YiYaoConstants.DLXX_PAGE_URL_CONTENT)!=-1
					&& href.indexOf(",")==-1 && href.indexOf("php")==-1){
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
				
				if(href.indexOf(YiYaoConstants.COMMON_URL_CONTENT)!=-1){
					visitToContent(doc,href);
				}else if(href.indexOf(YiYaoConstants.YYZBZB_PAGE_URL_CONTENT)!=-1){
					visitToZbContent(doc,href);
				}else if(href.indexOf(YiYaoConstants.YYZS_PAGE_URL_CONTENT)!=-1){
					visitToZsContent(doc,href);
				}else if(href.indexOf(YiYaoConstants.DLXX_PAGE_URL_CONTENT)!=-1){
					visitToDlContent(doc,href);
				}
			}
		} catch (Exception e) {
			this.logger.error(e);
		}
		
	}

	/**
	 * 代理
	 * @param doc
	 * @param href
	 */
	private void visitToDlContent(Document doc, String href) {
		
		this.logger.info("100医药网代理解析详细页:"+href);
		
		String title = "";
		String content = "";
		
//		String time =  doc.select("table.12>tbody>tr>td[bgcolor=#FFFFFF]").get(3).text();
//		if(ProcessDate.compareDate(time, "yyyy-MM-DD" ,-1)){
		content = doc.select("table[class=12]").toString();
		String contactInfo  = doc.select("table[class=lineh30] strong").get(1).html();
		content += " <br/>联系人：" + contactInfo;
		title = doc.select("table[class=12] strong").get(1).html();
		
		CrawlerService.executeAlliance(AllianceTypeEnum.AGENT, MySqlEscape.escape(title),MySqlEscape.escape(content));
//		System.out.println(title+"\n"+content+"\n");
//			}
		}

	/**
	 * 招商
	 * @param doc
	 * @param href
	 */
	private void visitToZsContent(Document doc, String href) {
		
		this.logger.info("100医药网招商解析详细页:"+href);
		
		String title = "";
		String content = "";
		
//		String time =  doc.select("div.tag>ul>li.gx>span>strong.f12").text();
//		if(ProcessDate.compareDate(time, "yyyy-MM-DD" ,-1)){
			title = doc.select("h1").html();
	//		source = "100医药网";
			//我要代理的连接  href 高为#
			Elements elements = doc.select("div[class=texttop] a");
			if(elements != null){
				for (Element e : elements) {
					if("我要代理".equals(e.html())){
						e.attr("href","#");
						e.removeAttr("target");
						break;
					}
				}
			}
			content = doc.select("div[class=texttop]").html().replaceAll("div", "span");
			
			CrawlerService.executeAlliance(AllianceTypeEnum.MERCHANTS, MySqlEscape.escape(title),MySqlEscape.escape(content));
//		}
//		System.out.println(title+"\n"+content+"\n");
	}

	/**
	 * 招标中标
	 * @param doc
	 * @param href
	 */
	private void visitToZbContent(Document doc, String href) {
		
		this.logger.info("100医药网资讯解析详细页:"+href);
		String source = "";
		String title = "";
		String content = "";
		Integer groupId = null;
		
		String sourceHtml = doc.select("div[class=ct01]").html();
		source = sourceHtml.substring(sourceHtml.indexOf("信息来源") + 5, sourceHtml.indexOf(" "));
		title =  doc.select("h1").html();
		content = doc.getElementById("remark_view").child(0).html().replaceAll("div", "span");
		groupId =  new Integer(108);
		
		
		CrawlerService.executeContent(groupId, MySqlEscape.escape(title), 
				MySqlEscape.escape(source), href, "", MySqlEscape.escape(content));
//		System.out.println(source+"\n"+title+"\n"+content+"\n");
	}

	
	/**
	 * 标题-分类 集合获取
	 * 100  行业资讯 
	 * 101  政策法规 
	 * 102  医药报告 
	 * 103  最新科研 
	 * 104  商情分析 
	 * 105  展会论坛 
	 * 106  热点话题 
	 * 107  精英访谈
	 * 108  招标信息 
	 * 109  采购信息 
	 * 110  未分类
	 * 128  调查报告
	 * 129  商品评测
	 * 130  订阅文章
	 * 131  生意经
	 * @param doc
	 * @param href
	 */
	private void visitToContent(Document doc, String href) {
		this.logger.info("100医药网资讯解析详细页:"+href);
		
		String source = "";
		String title = "";
		String content = "";
		Integer groupId = null;
		
		//分类
		String str = doc.select("div.current").text().trim();
		//行业资讯
		if(str.indexOf("> 医药政策法规")!=-1
				||str.indexOf("> 社会关注")!=-1
				||str.indexOf("> 医药企业新闻")!=-1
				||str.indexOf("> 医药新闻")!=-1){
			groupId = new Integer(100);
		}
		//展会论坛
		else if(str.indexOf("> 医药展会")!=-1){
			groupId = new Integer(105);
		}
		//生意经
		else if(str.indexOf("> 医药营销")!=-1){
			groupId = new Integer(131);
		}
		//招标信息
		else if(str.indexOf("> 医药招投标")!=-1){
			groupId = new Integer(108);
		}
		
		source = doc.select("div[class=ct01] a").get(0).html();
		title =  doc.select("h1").html();
		content = doc.getElementById("remark_view").child(0).html().replaceAll("div", "span");
		
		CrawlerService.executeContent(groupId, MySqlEscape.escape(title), 
				MySqlEscape.escape(source), href, "", MySqlEscape.escape(content));
//		System.out.println(source+"\n"+title+"\n"+content+"\n");
	}

}
