package com.glorypty.crawler.baiduyy.step;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.glorypty.crawler.baiduyy.BaiduyyConstants;
import com.glorypty.crawler.base.BaseCrawler;
import com.glorypty.crawler.utils.HttpMethodSimulate;
import com.glorypty.crawler.utils.ProcessDate;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.url.WebURL;

public class BaiduyyStep0Crawler extends BaseCrawler {
	
	/**论坛会展 分页起始页*/
	public int pageNum = 2;

	@Override
	public boolean shouldVisit(Page page, WebURL url) {
		boolean flag = false;
		if(super.shouldVisit(page, url)){
			String href = url.getURL();
			List<String> list = BaiduyyConstants.getUrlList();
			for (String home : list) {
				if(href.startsWith(home)){
					flag = true;
					break;
				}
			}
		}
		return flag;
	}

	@Override
	public void visit(Page page) {
		List<String> urlList = new ArrayList<String>();
		String href = page.getWebURL().getURL();
//		System.out.println("首页href:"+href);
		this.logger.info("百度虫首页解析："+href);
		
		Document doc = this.getDocument(page);
		if(doc!=null){
			do{
				//解析分页并访问详细页
				
				//  招标新闻,新品抢鲜,警示平台,企业之窗,药价动态,政策法规
				if(href.indexOf("uid")!=-1){
					visitToPage(doc,href,urlList);
					break;
				}
				//招标信息
				if(href.indexOf(BaiduyyConstants.ZBXX_URL_HOME) != -1){
					visitZbxxToPage(doc,href,urlList);
					break;
				}
				//代理信息
				if(href.indexOf(BaiduyyConstants.DLXX_URL_HOME) != -1){
					visitDlxxToPage(doc,href,urlList);
					break;
				}
				//求购信息
				if(href.indexOf(BaiduyyConstants.QGXX_URL_HOME) != -1){
					visitQgxxToPage(doc,href,urlList);
					break;
				}
				//药价文件
				if(href.indexOf(BaiduyyConstants.YJWJ_URL_HOME) != -1){
					visitYjwjToPage(doc,href,urlList);
					break;
				}
				//招商信息
				if(href.indexOf(BaiduyyConstants.ZSXX_URL_HOME) != -1){
					visitZsxxToPage(doc,href,urlList);
					break;
				}
				//展会论坛
				if(href.indexOf(BaiduyyConstants.YYZH_URL_HOME) != -1){
					//为了和首页  url 不一致
					visitYyzhToPage(doc,href+"?page=1",urlList);
					break;
				}
			}while(false);
			
			try {
				logger.info(urlList);
				new BaiduyyStep1Controller().door(urlList);
//				for (int i = 0; i <urlList.size(); i++) {
//					System.out.println(urlList.get(i));
//				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 展会论坛分页链接获取
	 * @param doc
	 * @param href
	 * @param urlList
	 */
	private void visitYyzhToPage(Document doc, String href, List<String> urlList) {
		try {	
			//总页数
			int pageTotal = Integer.valueOf(doc.select("span[id=PageCount]").text());
			String __VIEWSTATE = URLEncoder.encode(doc.getElementById("__VIEWSTATE").val(),"utf-8");
			String __EVENTTARGET = "Nextpage";
			String __EVENTVALIDATION = URLEncoder.encode(doc.getElementById("__EVENTVALIDATION").val(),"utf-8");
			urlList.add(href);
			if(pageNum<=pageTotal && pageNum <= BaiduyyConstants.MAX_PAGE_SIZE){
				StringBuffer nextUrl = new StringBuffer(BaiduyyConstants.YYZH_URL_HOME);
				nextUrl.append("?__VIEWSTATE=").append(__VIEWSTATE);
				nextUrl.append("&__EVENTTARGET=").append(__EVENTTARGET);
				nextUrl.append("&__EVENTVALIDATION=").append(__EVENTVALIDATION);
				pageNum++;
				Document document = HttpMethodSimulate.getReqSimulate(nextUrl.toString());
				if(!judgeYyzhDate(document)){
					urlList.add(nextUrl.toString());
				}else{
					visitYyzhToPage(document,nextUrl.toString(),urlList);
				}
			}
			
		} catch (UnsupportedEncodingException e) {
			this.logger.error(e);
		}
	}


	private void visitZsxxToPage(Document doc, String href, List<String> urlList) {
		String pageStr = doc.getElementById("topage").html();
		pageStr = pageStr.substring(0, pageStr.indexOf("页"));
		String [] pageArray = pageStr.split("/");
		
		
		//最大页数
		int totalPage = Integer.parseInt(pageArray[1]);
		
		for(int i = 1 ; i <= totalPage && i<= BaiduyyConstants.MAX_PAGE_SIZE; i++){
			urlList.add(BaiduyyConstants.ZSXX_PAGE_URL_HOME_PRE + i);
			if(!judgeIsNull(urlList.get(urlList.size() - 1), BaiduyyConstants.ZSXX_URL_HOME)){
				break;
			}
		}
	}

	/** 目前为止： 招商信息十页之后是空页  */
	private boolean judgeIsNull(String href, String homePage) {
		boolean flag = true;
		Document doc = HttpMethodSimulate.getReqSimulate(href);
		if(doc != null){
			Elements elements = doc.select("a[class=l-bs]");
			if(elements == null || elements.size() == 0){
				flag = false;
			}
		}else{
			flag = false;
		}
		return flag;
	}

	private void visitYjwjToPage(Document doc, String href, List<String> urlList) {
		
		String pageStr = doc.select("form[action=yaojiafile.asp]").select("b").get(0).html();
		//最大页数
		int totalPage = Integer.parseInt(pageStr);
		
		for(int i = 1 ; i <= totalPage && i <= BaiduyyConstants.MAX_PAGE_SIZE; i++){
			urlList.add(BaiduyyConstants.YJWJ_PAGE_URL_HOME_PRE + i);
			if(!judgeDate(urlList.get(urlList.size() - 1), BaiduyyConstants.YJWJ_URL_HOME)){
				break;
			}
		}
	}

	private void visitQgxxToPage(Document doc, String href, List<String> urlList) {
		String pageStr = doc.select("p b").get(0).html();
		//最大页数
		int totalPage = Integer.parseInt(pageStr);
		for(int i = 1 ; i <= totalPage && i <= BaiduyyConstants.MAX_PAGE_SIZE; i++){
			urlList.add(BaiduyyConstants.QGXX_PAGE_URL_HOME_PRE + i);
			if(!judgeDate(urlList.get(urlList.size() - 1), BaiduyyConstants.QGXX_URL_HOME)){
				break;
			}
		}
	}

	private void visitDlxxToPage(Document doc, String href, List<String> urlList) {
		
		String jicount = doc.select("span[id=topage] a").get(0).attr("href");
		jicount = jicount.substring(jicount.lastIndexOf("&"));
		String pageStr = doc.getElementById("topage").html();
		pageStr = pageStr.substring(1, pageStr.indexOf("页"));
		
		//最大页数
		int totalPage = Integer.parseInt(pageStr);
		for(int i = 1 ; i <= totalPage && i <= BaiduyyConstants.MAX_PAGE_SIZE; i++){
			urlList.add(BaiduyyConstants.DLXX_PAGE_URL_HOME_PRE + "page=" + i + jicount);
			if(!judgeDate(urlList.get(urlList.size() - 1), BaiduyyConstants.DLXX_URL_HOME)){
				break;
			}
		}
	}

	private void visitZbxxToPage(Document doc, String href, List<String> urlList) {
		String pageStr = doc.select("form b").get(0).html();
		
		//最大页数
		int totalPage = Integer.parseInt(pageStr);
		for(int i = 1 ; i <= totalPage && i <= BaiduyyConstants.MAX_PAGE_SIZE;  i++){
			urlList.add(BaiduyyConstants.ZBXX_PAGE_URL_HOME_PRE + i);
			if(!judgeDate(BaiduyyConstants.ZBXX_PAGE_URL_HOME_PRE + i, BaiduyyConstants.ZBXX_URL_HOME)){
				break;
			}
		}
	}

	private void visitToPage(Document doc, String href, List<String> urlList) {
		
		//参数字符串
		href = href.substring(0,href.indexOf("?"));
		int pageTotal = 0;
		String param = "";
		
		//总页数
		Elements pageContents = doc.select("table>tbody>tr>td>span[id=topage]");
		if(pageContents!=null&&!pageContents.isEmpty()){
			String element = pageContents.get(0).html().trim().split("&nbsp;")[0];
			pageTotal = Integer.valueOf(element.substring(1,element.length()-1));
		}
		
		//其他参数
		Elements paramrs = doc.select("table>tbody>tr>td>span[id=topage]>input[name=page1]");
		if(paramrs!=null&&!paramrs.isEmpty()){
			Element element = paramrs.get(0);
			param = element.attr("value").trim();
		}
		
		if(pageTotal>0){
			StringBuffer sb = null;
			for (int i = 1; i <= pageTotal &&  i <= BaiduyyConstants.MAX_PAGE_SIZE; i++) {
				//参数三-页数
				sb = new StringBuffer();
				sb.append(href);
				sb.append("?page="+i);
				sb.append(param);
				urlList.add(sb.toString());
				if(!judgeDate(sb.toString(), BaiduyyConstants.XPQX_URL_HOME)){
					break;
				}
			}
		}
		
	}

	/** 过滤掉 6个月前的资讯数据 
	 *  6个月以内的数据返回true
	 *  否则返回false
	 * */
	private boolean judgeDate(String href, String type) {
		boolean flag = true;
		Document doc = HttpMethodSimulate.getReqSimulate(href);
		String publishDate = null;
		if(doc != null){
			Elements elements = null;
			switch(type){
			case BaiduyyConstants.XPQX_URL_HOME:
				elements = doc.select("td[class=lv12][height=21]");
				if(elements != null){
					for (Element element : elements) {
						publishDate = element.html();
						flag = ProcessDate.compareDate(publishDate.substring(5,13), "yy-MM-dd", BaiduyyConstants.EFFECTIVE_MONTH);
						if(!flag){
							break;
						}
					}
				}else{
					flag = false;
				}
				break;
				
				//招标信息
			case BaiduyyConstants.ZBXX_URL_HOME:
				elements = doc.select("td[align=center][height=23]");
				if(elements != null){
					for (Element element : elements) {
						publishDate = element.html().replaceAll("[\\[\\]]", "");
						flag = ProcessDate.compareDate(publishDate, "yyyy/M/d", BaiduyyConstants.EFFECTIVE_MONTH);
						if(!flag){
							break;
						}
					}
				}else{
					flag = false;
				}
				break;
				
				//代理信息
			case BaiduyyConstants.DLXX_URL_HOME:
				elements = doc.select("td[align=center][width=80]");
				if(elements != null){
					for (Element element : elements) {
						publishDate = element.child(0).html();
						flag = ProcessDate.compareDate(publishDate, "yyyy-MM-dd", BaiduyyConstants.EFFECTIVE_MONTH);
						if(!flag){
							break;
						}
					}
				}else{
					flag = false;
				}
				break;
				
				//求购信息
			case BaiduyyConstants.QGXX_URL_HOME:
				elements = doc.select("td[align=center][class=hui_dls]");
				if(elements != null){
					for (Element element : elements) {
						publishDate = element.html();
						flag = ProcessDate.compareDate(publishDate, "yyyy-M-d", BaiduyyConstants.EFFECTIVE_MONTH);
						if(!flag){
							break;
						}
					}
				}else{
					flag = false;
				}
				break;
				
				//药价文件
			case BaiduyyConstants.YJWJ_URL_HOME:
				elements = doc.select("td[width=104][class=yd_bt]");
				if(elements != null){
					for (Element element : elements) {
						publishDate = element.html();
						flag = ProcessDate.compareDate(publishDate, "yyyy-M-d", BaiduyyConstants.EFFECTIVE_MONTH);
						if(!flag){
							break;
						}
					}
				}else{
					flag = false;
				}
				break;
				
			}
		}
		return flag;
	}	
	
	/** 医药展会 */
	private boolean judgeYyzhDate(Document doc) {
		boolean flag = true;
		String publishDate = null;
		if(doc != null){
			Elements elements = doc.select("table[id=zhView] tbody tr");
			if(elements != null){
				for (Element element : elements) {
					publishDate = element.child(0).html();
//					publishDate = publishDate.substring(0,5) + publishDate.substring(11, 16);
//					flag = ProcessDate.compareDate(publishDate, "yyyy.MM-dd", BaiduyyConstants.EFFECTIVE_MONTH);
					publishDate = publishDate.substring(0,10);
					flag = ProcessDate.compareDate(publishDate, "yyyy.MM.dd", BaiduyyConstants.EFFECTIVE_MONTH);
					if(!flag){
						break;
					}
				}
			}else{
				flag = false;
			}
		}else{
			flag = false;
		}
		return flag;
	}
	
}
