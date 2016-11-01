package com.glorypty.crawler.menet.step;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.glorypty.crawler.base.BaseCrawler;
import com.glorypty.crawler.menet.MenetConstants;
import com.glorypty.crawler.utils.HttpMethodSimulate;
import com.glorypty.crawler.utils.ProcessDate;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.url.WebURL;

/**
 * 列表分页处理
 * @author Zhanglujun
 *
 */
public class MenetStep0Crawler extends BaseCrawler {
	
	/**列表分页总页数*/
	public int pageTotal = 0;
	public boolean isCompare = true;
	
	@Override
	public boolean shouldVisit(Page page, WebURL url) {
		if(super.shouldVisit(page, url)){
			String href = url.getURL();
			ArrayList<String> menetUrlList = MenetConstants.getList();
			for (String str : menetUrlList) {
				if (href.startsWith(str)){
					return true;
				}else if(href.startsWith(MenetConstants.HYZX_YGXW_URL_CONTENT)){
					return false;
				}
			}
		}
		return false;
	}

	@Override
	public void visit(Page page) {
		isCompare = true;
		List<String> urlList = new ArrayList<String>();
		
		String href = page.getWebURL().getURL();
		MenetConstants.report_url = href;
		this.logger.info("抓取列表首页:"+href);
		
		Document doc = this.getDocument(page);
		if(doc!=null){
			//获取列表页第一页详细URL
			urlList = fetchUrl(doc, urlList, href);
			//解析其他列表页,抓取详细RUL
			if(isCompare){
				visitToPage(doc,href,urlList);
			}
		}
	}

	/**
	 * 解析其他列表页,抓取详细RUL
	 * @param doc  列表页首页Document
	 * @param href 列表页首页URL
	 * @param urlList 详细URL集合
	 */
	private void visitToPage(Document doc, String href,List<String> urlList) {
		try {
			//post参数集合
			Map<String,String> paramersMap = new HashMap<String, String>();
			
			//获取总页数
			Elements pageContents = doc.select("div.newsList>ul>div>a");
			String value = pageContents.get(pageContents.size()-1).attr("href");
			value = value.split(",")[1];
			pageTotal = Integer.valueOf(value.substring(1,value.length()-2));
			
			//参数一
			Elements _viewState = doc.select("input[name=__VIEWSTATE]");
			if(_viewState!=null){
				String stateVlue = _viewState.attr("value");
				paramersMap.put("__VIEWSTATE", stateVlue);
			}
			//参数二 -AspNetPager1
			paramersMap.put("__EVENTTARGET", "AspNetPager1");
	
			//销毁doc
			doc = null;
			isCompare = true;
			
			if(pageTotal>1){
				for (int i = 2; i <= pageTotal; i++) {
					//参数三-页数
					if(isCompare){
						paramersMap.put("__EVENTARGUMENT", i+"");
						Document docment = HttpMethodSimulate.postReqSimulate(href, paramersMap);
						urlList = fetchUrl(docment,urlList,href);
					}
				}
			}
			
			this.logger.info(href+"的详细页数:"+urlList.size());
			new MenetStep1Controller().door(urlList);
			
		} catch (Exception e) {
			this.logger.error(e);
		}
	}
	
	/**
	 * 解析列表页，抓取URL链接
	 * @param doc 列表页Document
	 * @param urlList URL集合
	 * @return urlList URL集合
	 */
	private List<String> fetchUrl(Document docment,List<String> urlList,String href){
		if(docment!=null){
			Elements dtElements= docment.select("div.newsList>dl>dt");
			if(dtElements!=null&&!dtElements.isEmpty()){
				if(href.indexOf(MenetConstants.DJHT_RDDF_URL_HOME)!=-1
						||href.indexOf(MenetConstants.SYJ_JDAL_URL_HOME)!=-1
						||href.indexOf(MenetConstants.SYJ_YXJQ_URL_HOME)!=-1
						||href.indexOf(MenetConstants.DCBG_URL_HOME)!=-1){
					for (Element element : dtElements) {
						String value = element.html();
						value = value.substring(value.indexOf("href="));
						value = value.substring(6);
						value = value.substring(0,value.indexOf("\""));
						
						if(value!=null && !"".equals(value)){
							if(value.indexOf("menet.com.cn")!=-1){
								urlList.add(value);
							}else{
								urlList.add(MenetConstants.MENET_URL_HOME+value);
							}
						}
					}
				}else{
					for (Element element : dtElements) {
						String str =  element.text();
						int f_index = str.indexOf(".");
						str = str.substring(f_index-4, f_index+6);
						if(!ProcessDate.compareDate(str,"yyyy.MM.DD", MenetConstants.PAST_MONTH)){
							isCompare = false;
							break;
						}else{
							String value = element.html();
							value = value.substring(value.indexOf("href="));
							value = value.substring(6);
							value = value.substring(0,value.indexOf("\""));
							
							if(value!=null && !"".equals(value)){
								if(value.indexOf("menet.com.cn")!=-1){
									urlList.add(value);
								}else{
									urlList.add(MenetConstants.MENET_URL_HOME+value);
								}
							}
//						this.logger.info(MenetConstants.MENET_URL_HOME+value);
						}
					}
				}
			}
		}
		return urlList;
	}

}
