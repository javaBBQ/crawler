package com.glorypty.crawler.cpi.step;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.glorypty.crawler.base.BaseCrawler;
import com.glorypty.crawler.cpi.CpiConstants;
import com.glorypty.crawler.utils.HttpMethodSimulate;
import com.glorypty.crawler.utils.ProcessDate;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.url.WebURL;

public class CpiStep0Crawler extends BaseCrawler {

	/**
	 * 分页页数
	 */
	public int page = 1;
	
	/**分页链接集合*/
	public static List<String> list = null;
	
	@Override
	public boolean shouldVisit(Page page, WebURL url) {
		String href = url.getURL().toLowerCase(); 
		LinkedHashMap<String, String> urlMap= CpiConstants.getMap();
		Set set = urlMap.entrySet();
		for (Iterator iterator = set.iterator(); iterator.hasNext();) {
			Entry<String, String> entry = (Entry<String, String>) iterator.next();
			if (href.equals(entry.getKey())){
				return true;
			}	
		}
		return false;
	}

	@Override
	public void visit(Page page) {
		try {
			List<String> urlList = new ArrayList<String>();
			String href = page.getWebURL().getURL();
			Document doc = this.getDocument(page);
			int pageNum = 0;
			if (null != doc) {	
				Elements elements = doc.select("div[class=middle fl]>div.yellow");
				if(elements!=null&&!elements.isEmpty()){
					String str = elements.text();
					String[] arr = str.split(" ");
					pageNum = Integer.parseInt(arr[1].substring(1,arr[1].length()-1));
				}
				urlList = getCommnetList_(href, pageNum);
				
				if(urlList!=null && urlList.size()>0){
					new CpiStep1Controller().door(urlList);
				}
				
//				list = new ArrayList<String>();
//				getCommnetList(href, pageNum, doc);
//				if(list!=null&&list.size()>0){
//					new CpiStep2Controller().door(list);
//				}
			}
		} catch (Exception e) {
			this.logger.error(e);
		}
	}

	
	/**
	 * 
	 * @param href
	 * @param pageNum
	 * @param doc
	 */
	private void getCommnetList(String href, int pageNum, Document doc) {
		boolean isCompare = true;
		
		String page_url = CpiConstants.getMap().get(href);
		if(page_url!=null){
			href = page_url+".htm";
		}else{
			page_url = href.substring(0, href.indexOf("_"));
		}
		list.add(href);
		
		if(doc!=null){
			Elements elements = doc.select("div[class=middle fl]>div.channel>div.source>div[class=sour fr]");
			if(elements!=null&&!elements.isEmpty()){
				for (Element element : elements) {
					String str = element.text();
					if(str.indexOf("-")!=-1){
						int f_index = str.indexOf("-");
						str = str.substring(f_index-4, f_index+6);
						if(!ProcessDate.compareDate(str,"yyyy-MM-DD", CpiConstants.PAST_MONTH)){
							isCompare = false;
							break;
						}
					}
				}
				
				if( page< pageNum && isCompare){
					++page;
					String nextUrl = page_url+"_"+page+".htm";
					Document document = HttpMethodSimulate.getReqSimulateByStream(nextUrl.toString());
					getCommnetList(nextUrl,pageNum,document);
				}
			}
		}
		
	}
	
	/**
	 * 获取列表页
	 * @author Andy
	 * @param href
	 * @param pageNum
	 * @return
	 */
	private List<String> getCommnetList_(String href, int pageNum) {
		List<String> list = new ArrayList<String>();
		String pre_url = CpiConstants.getMap().get(href);
		if(pageNum>0){
			for (int i = 1; i <= pageNum; i++) {
				if(i>1)
					list.add(pre_url+"_"+i+".htm");
				else
					list.add(pre_url+".htm");
			}
		}
		return list;
	}

}
