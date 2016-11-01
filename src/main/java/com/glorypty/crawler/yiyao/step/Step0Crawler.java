package com.glorypty.crawler.yiyao.step;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.glorypty.crawler.base.BaseCrawler;
import com.glorypty.crawler.utils.HttpMethodSimulate;
import com.glorypty.crawler.utils.ProcessDate;
import com.glorypty.crawler.yiyao.YiYaoConstants;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.url.WebURL;

/**
 * 列表页抓取
 * @author zhanglujun
 *
 */
public class Step0Crawler extends BaseCrawler {

	public int page = 1;
	
	public static List<String> list = null;
	@Override
	public boolean shouldVisit(Page page, WebURL url) {
		if(super.shouldVisit(page, url)){
			String href = url.getURL();
			List<String> list = YiYaoConstants.getUrlList();
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

	@Override
	public void visit(Page page) {
		try {
			List<String> urlList = new ArrayList<String>();
			String href = page.getWebURL().getURL();
			this.logger.info("100医药网抓取列表链接启动:"+href);
			
			int pageNum = 0;
			Document doc = this.getDocument(page);
			if(doc!=null){
				if(href.indexOf(YiYaoConstants.ZCFG_URL_HOME)!=-1
						||href.indexOf(YiYaoConstants.SHGZ_URL_HOME)!=-1
						||href.indexOf(YiYaoConstants.YYXW_URL_HOME)!=-1
						||href.indexOf(YiYaoConstants.YYQYXW_URL_HOME)!=-1
						||href.indexOf(YiYaoConstants.YYZTB_URL_HOME)!=-1
						||href.indexOf(YiYaoConstants.ZH_URL_HOME)!=-1
						||href.indexOf(YiYaoConstants.YYYX_URL_HOME)!=-1
						||href.indexOf(YiYaoConstants.YYZBZB_URL_HOME)!=-1){
					
					Elements elements = doc.select("div.page>b");
					if(elements!=null&&!elements.isEmpty()){
						pageNum = Integer.parseInt(elements.get(1).text());
					}
					urlList = getCommnetList_(href, pageNum);
					if(urlList!=null && urlList.size()>0){
						new Step1Controller().door(urlList);
					}
					
//					list = new ArrayList<String>();
//					getCommnetList(href, pageNum, doc);
//					if(list!=null&&list.size()>0){
//						new Step1Controller().door(list);
//					}
							
				}else if(href.indexOf(YiYaoConstants.DLXX_URL_HOME)!=-1){
					Elements elements = doc.select("div[id=list]>dl>dd.xl>span.black>a");
					if(elements!=null&&!elements.isEmpty()){
						String str = elements.get(elements.size()-1).attr("href");
						str = str.substring(str.lastIndexOf("/")+1, str.lastIndexOf("."));
						pageNum = Integer.parseInt(str);
					}
					urlList = getDlOrZsist_(href, pageNum);
					if(urlList!=null && urlList.size()>0){
						new Step1Controller().door(urlList);
					}
//					list = new ArrayList<String>();
//					getDlxxList(href, pageNum, doc);
//					if(list!=null&&list.size()>0){
//						new Step1Controller().door(list);
//					}
					
				}else if(href.indexOf(YiYaoConstants.YYZS_URL_HOME)!=-1){
					Elements elements = doc.select("div.list>h6>b");
					if(elements!=null&&!elements.isEmpty()){
						pageNum = Integer.parseInt(elements.get(1).text());
					}
					
					urlList = getDlOrZsist_(href, pageNum);
					if(urlList!=null && urlList.size()>0){
						new Step1Controller().door(urlList);
					}
//					list = new ArrayList<String>();
//					getYyzsList(href,pageNum,doc);
//					if(list!=null&&list.size()>0){
//						new Step1Controller().door(list);
//					}
				}
			}
		
		} catch (Exception e) {
			this.logger.error(e);
		}
	}

	/**
	 * 资讯
	 * @param href
	 * @param pageNum
	 * @param doc
	 */
	private void getCommnetList(String href, int pageNum, Document doc) {
		boolean isCompare = true;
		
		String page_url = YiYaoConstants.getUrlMap().get(href);
		if(page_url!=null){
			href = YiYaoConstants.getUrlMap().get(href).replace("{0}",1+"");
		}else{
			if(href.lastIndexOf(",")!=-1){
				page_url = href.substring(0, href.lastIndexOf(",")+1)+"{0}"+href.substring(href.lastIndexOf("."));
			}else{
				page_url = href.substring(0, href.lastIndexOf("/")+1)+"{0}"+href.substring(href.lastIndexOf("."));
			}
		}
		list.add(href);
		
		if(doc!=null){
			Elements elements = doc.select("div.list>div.ttjx>ul>li");
			if(elements!=null&&!elements.isEmpty()){
				for (Element element : elements) {
					String str = element.text();
					if(str.indexOf("-")!=-1){
						int f_index = str.indexOf("-");
						str = str.substring(f_index-4, f_index+6);
						if(!ProcessDate.compareDate(str,"yyyy-MM-DD", YiYaoConstants.PAST_MONTH)){
							isCompare = false;
							break;
						}
					}
				}
				
				if( page< pageNum && isCompare){
					++page;
					String nextUrl = page_url.replace("{0}",page+"");
					try {
						Document document = HttpMethodSimulate.getReqSimulateByStream(nextUrl.toString());
						getCommnetList(nextUrl,pageNum,document);
					} catch (Exception e) {
						++page;
						nextUrl = page_url.replace("{0}",page+"");
						Document document = HttpMethodSimulate.getReqSimulateByStream(nextUrl.toString());
						getCommnetList(nextUrl,pageNum,document);
					}
				}
			}
		}
	}
	
	public List<String> getCommnetList_(String href, int pageNum){
		List<String> list = new ArrayList<String>();
		if(pageNum > 0){
			String url = YiYaoConstants.getUrlMap().get(href);
			for (int i = 1; i <= pageNum; i++) {
				String page_url = url.replace("{0}", i+"");
				list.add(page_url);
			}
		}
		return list;
	} 

	/**
	 * 代理
	 * @param href  访问url 
	 * @param pageNum   总页数
	 * @param doc     首页document
	 * @return  list
	 */
	private void getDlxxList(String href,int pageNum, Document doc) {
		boolean isCompare = true;
		
		if(YiYaoConstants.DLXX_URL_HOME.equals(href)){
			href = YiYaoConstants.DLXX_PAGE_URL_HOME_PRE+"1.html";
		}
		list.add(href);
		
		if(doc!=null){
			Elements elements = doc.select("table.yd>tbody>tr>td[bgcolor=#FFFFFF]");
			if(elements!=null&&!elements.isEmpty()){
				for (Element element : elements) {
					String str = element.text();
					if(str.indexOf("-")!=-1){
						if(!ProcessDate.compareDate(str,"yyyy-MM-DD", YiYaoConstants.PAST_MONTH)){
							isCompare = false;
							break;
						}
					}
				}
				
				if( page< pageNum && isCompare){
					++page;
					String nextUrl = YiYaoConstants.DLXX_PAGE_URL_HOME.replace("{0}",page+"");
					try {
						Document document = HttpMethodSimulate.getReqSimulateByStream(nextUrl.toString());
						getDlxxList(nextUrl,pageNum,document);
					} catch (Exception e) {
						++page;
						nextUrl = YiYaoConstants.DLXX_PAGE_URL_HOME.replace("{0}",page+"");
						Document document = HttpMethodSimulate.getReqSimulateByStream(nextUrl.toString());
						getDlxxList(nextUrl,pageNum,document);
					}
				}
			}
		}
	}

	private List<String> getDlOrZsist_(String href,int pageNum) {
		List<String> list = new ArrayList<String>();
		if(pageNum > 0){
			String url = "";
			if(YiYaoConstants.DLXX_URL_HOME.equals(href)){
				url = YiYaoConstants.DLXX_PAGE_URL_HOME;
				if(pageNum>4000)
					pageNum = YiYaoConstants.fixedNum;
			}else if(YiYaoConstants.YYZS_URL_HOME.equals(href)){
				url = YiYaoConstants.YYZS_PAGE_URL_HOME;
				if(pageNum>4000)
					pageNum = YiYaoConstants.fixedNum/2;
			}
			
			for (int i = 1; i <= pageNum; i++) {
				 String page_url = url.replace("{0}", i+"");
				list.add(page_url);
			}
		}
		return list;
	}
	
	/**
	 * 招商   获取一定条件分页链接集合
	 * @param href
	 * @param pageNum
	 * @param doc
	 * @return
	 */
	private List<String> getYyzsList(String href, int pageNum, Document doc) {
		boolean isCompare = true;
		if(YiYaoConstants.YYZS_URL_HOME.equals(href)){
			href = YiYaoConstants.YYZS_URL_HOME+"1,1.html";
		}
		list.add(href);
		
		if(doc!=null){
			Elements elements = doc.select("div.list>ul>li>p>span.ptime");
			if(elements!=null&&!elements.isEmpty()){
				for (Element element : elements) {
					String str = element.text();
					str = str.substring(str.length()-19);
					if(!ProcessDate.compareDate(str,"yyyy/MM/dd HH:mm:ss", YiYaoConstants.PAST_MONTH)){
						isCompare = false;
						break;
					}
				}
				
				if( page< pageNum && isCompare){
					++page;
					String nextUrl = YiYaoConstants.YYZS_PAGE_URL_HOME.replace("{0}",page+"");
					try {
						Document document = HttpMethodSimulate.getReqSimulateByStream(nextUrl.toString());
						getYyzsList(nextUrl,pageNum,document);
					} catch (Exception e) {
						++page;
						nextUrl = YiYaoConstants.YYZS_PAGE_URL_HOME.replace("{0}",page+"");
						Document document = HttpMethodSimulate.getReqSimulateByStream(nextUrl.toString());
						getYyzsList(nextUrl,pageNum,document);
					}
				}
			}
		}
		return list;
	}
	
}
