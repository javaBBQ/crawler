package com.glorypty.crawler.cpi.step;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.Map.Entry;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.glorypty.crawler.base.BaseCrawler;
import com.glorypty.crawler.common.ConstantsCrawler;
import com.glorypty.crawler.cpi.CpiConstants;
import com.glorypty.crawler.utils.MySqlEscape;
import com.glorypty.jdbc.CrawlerService;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.url.WebURL;

/**
 * 信息采集
 * @author Zhanglujun
 *
 */
public class CpiStep2Crawler extends BaseCrawler{
	
	@Override
	public boolean shouldVisit(Page page, WebURL url) {
		if(super.shouldVisit(page, url)){
			String href = url.getURL(); 
			String parent = url.getParentUrl();
			LinkedHashMap<String, String> urlMap= CpiConstants.getMap();
			Set set = urlMap.entrySet();
			for (Iterator iterator = set.iterator(); iterator.hasNext();) {
				Entry<String, String> entry = (Entry<String, String>) iterator.next();
				if (href.startsWith(entry.getKey())){
					if(href.indexOf(CpiConstants.COMMON_CONTENT_TAG)!=-1){
						if(parent.startsWith(CpiConstants.YYBG_YWZLTX_URL_HOME)){
							if(href.indexOf("/qtggtg/")!=-1    //其他公告通知
									||href.indexOf("/zybhnew/")!=-1  //中药保护品种
									||href.indexOf("/ypxzbh/")!=-1){ //药品行政保护
								return true;
							}
						}
						return true;
					}else if(href.indexOf(CpiConstants.YYBG_SDA_URL_CONTETN)!=-1){
						return true;
					}
				}
			}
		}
		return false;
	}
	
	@Override
	public void visit(Page page) {
		String href = page.getWebURL().getURL();
		Document doc = this.getDocument(page);
		if (null != doc) {
			//剔除图片
			ConstantsCrawler.clearImg(doc, href);
			visitToContent(doc,href);
		}
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
	 * 110   未分类
	 * 128   调查报告
	 * 129   商品评测
	 * 130   订阅文章
	 * 131   生意经
	 */
	private void visitToContent(Document doc,String url){
		
		this.logger.info("中医药解析详细页："+url);
		try {
			Integer groupId = null; // 栏目ID
			String title = ""; // 标题
			String origin = ""; // 来源
			String originurl = ""; // 来源URL
			String desc = ""; // 简述/摘要/关键字
			String txt = ""; // 详情
			StringBuffer sp = null;
			
			if(url.indexOf(CpiConstants.YYBG_SDA_URL_CONTETN)!=-1){
				groupId = new Integer(102);
			}else{
				//详细分类
				Elements siteContents = doc.select("div[class=middle fl]>div.place>a");		
				if(null!=siteContents && !siteContents.isEmpty()){
					//行业资讯
					if(url.indexOf("/hyzx/")!=-1||url.indexOf("/hwzx/")!=-1
							||url.indexOf("/yqdt/")!=-1||url.indexOf("/ygdt/")!=-1){
						groupId = new Integer(100);
					}
					//政策法规
					else if(url.indexOf("/bmgz/")!=-1||url.indexOf("/flfg/")!=-1
							||url.indexOf("/zcjd/")!=-1||url.indexOf("/dfxfghgz1/")!=-1
							||url.indexOf("/gfwj/")!=-1){
						groupId = new Integer(101);
					}
					//医药报告
					else if(url.indexOf("/xgzlnwe/")!=-1||url.indexOf("/yjbg/")!=-1
							||url.indexOf("/zlgz/")!=-1||url.indexOf("/yzgl/")!=-1
							||url.indexOf("/tzgg/")!=-1
							||url.indexOf("/qtggtg/")!=-1
							||url.indexOf("/zybhnew/")!=-1
							||url.indexOf("/ypxzbh/")!=-1
							||url.indexOf("/ypgmprz/")!=-1){
						groupId = new Integer(102);
					}
					//科研进展
					else if(url.indexOf("/kjjz/")!=-1){
						groupId = new Integer(103);
					}
					//展会论坛
					else if(url.indexOf("/meeting/")!=-1){
						groupId = new Integer(105);
					}
					//招标信息
					else if(url.indexOf("/zbcg/")!=-1){
						groupId = new Integer(108);
					}
				}
			}
			
			//详细页链接到药监局网页
			if(url.indexOf(CpiConstants.YYBG_SDA_URL_CONTETN)!=-1){
				//标题
				Elements titleContents = doc.select("td.articletitle2>font");
				if(null!=titleContents && !titleContents.isEmpty()){
					Element element = titleContents.get(0);
					title =  MySqlEscape.escape(element.text());
				}
				
				//来源
				origin = "国家食品药品监督管理局";
				originurl = url;
				
				//内容
				Elements content = doc.select("td.articlecontent2");
				if(null!=content && !content.isEmpty()){
					Element element = content.get(0);
					txt =  MySqlEscape.escape(element.html());
				}
				
			}else{
				//内容-标题
				Elements titleContents = doc.select("div[class=middle fl]>div.zx_pro>h2");
				if(null!=titleContents && !titleContents.isEmpty()){
					Element element = titleContents.get(0);
					title =  MySqlEscape.escape(element.text());
				}
				
				//内容-来源
				Elements sourceContents = doc.select("div[class=middle fl]>div.zx_pro>div.source>div[class=sour fl]");
				if(null!=sourceContents && !sourceContents.isEmpty()){
					Element element = sourceContents.get(0);
					String source = element.text();
					if(source.length()>12){
						source = source.substring(12);
					}else{
						source = "CPI";
					}
					origin = source;
					originurl = url;
				}
				
				//内容-摘要
				Elements digestContents = doc.select("div[class=middle fl]>div.zx_pro>div.digest");
				if(null!=digestContents && !digestContents.isEmpty()){
					Element element = digestContents.get(0);
					MySqlEscape.escape(element.text());
				}
				
				//正文
				Elements content = doc.select("div[class=middle fl]>div.zx_pro>p");
				if(null!=content && !content.isEmpty()){
					sp = new StringBuffer();
					for (Element element : content) {
						sp.append("<p>");
						sp.append(element.html());
						sp.append("</p>");
					}
					txt = sp.toString();
				}
	
				//正文-附件下载URL替换
				Elements fujianUrls = doc.select("div[class=middle fl]>div.zx_pro>div.fujian");
				if(null!=fujianUrls && !fujianUrls.isEmpty()){
					sp = new StringBuffer();
					for (Element element : fujianUrls) {
						String fujianurl =  element.html();
						boolean flag = fujianurl.contains("href=\"/");
						if(flag){
							fujianurl = fujianurl.replace("href=\"/", "href=\""+CpiConstants.CPI_URL_HOME+"/");
						}
						sp.append(fujianurl);
					}
					txt +=sp.toString();
				}			
				
				txt = MySqlEscape.escape(txt);
			}
			
			CrawlerService.executeContent(groupId, title, origin, originurl, desc, txt);
			
		} catch (Exception e) {
			this.logger.error("执行：详情页("+ url +")： " + e);
		}
	}
	
}
