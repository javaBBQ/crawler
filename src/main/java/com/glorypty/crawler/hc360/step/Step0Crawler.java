/*
 *Project: glorypty-crawler
 *File: com.glorypty.crawler.hc360.step.Step0Crawler.java <2015年12月17日>
 ****************************************************************
 * 版权所有@2015 国裕网络科技  保留所有权利.
 ***************************************************************/
package com.glorypty.crawler.hc360.step;

import java.util.LinkedList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.glorypty.crawler.base.BaseCrawler;
import com.glorypty.crawler.hc360.Hc360Constants;
import com.glorypty.crawler.utils.Utils;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.url.WebURL;

/**
 * 爬虫入口程序：爬取列表首页，获取列表分页链接
 * @author ZhangLujun 
 * @Date 2015年12月17日 上午11:02:03
 * @version 1.0
 */
public class Step0Crawler extends BaseCrawler {
	
	public int page = 1;
	public static List<String> list = null;
	
	@Override
	public boolean shouldVisit(Page page, WebURL url) {
		if(super.shouldVisit(page, url)){
			String href = url.getURL();
			List<String> list = Hc360Constants.HOME_LIST_URL;
			if(list!=null && list.size()>0){
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
			String href = page.getWebURL().getURL();
			this.logger.info("聪慧网抓取列表首页链接:"+href);
			
			int pageNum = 0;
			Document doc = this.getDocument(page);
			if(doc!=null){
				List<String> urlList = new LinkedList<String>();
				//获取列表页总共页数
				Elements elements = doc.select("div.s-mod-page>span.total");
			
				//保健品
				if(href.indexOf(Hc360Constants.BJP_HOME)!=-1){
					if(elements!=null&&!elements.isEmpty()){
						String value = elements.text();
						pageNum = Integer.parseInt(String.valueOf(Utils.getNumber(value).get(0)));
						//获取指定页面参数
						int setPageNum = Hc360Constants.getPageNum();
						if(setPageNum>0){
							pageNum = setPageNum;
						}
						Hc360Constants.bjp_total = pageNum;
					}
					urlList = getCommnetList(Hc360Constants.BJP_PRD_LIST_URL, pageNum,"?af=");
				}
				//医疗器械
				else if(href.indexOf(Hc360Constants.YLQX_HOME)!=-1){
					if(elements!=null&&!elements.isEmpty()){
						String value = elements.text();
						pageNum = Integer.parseInt(String.valueOf(Utils.getNumber(value).get(0)));
						//获取指定页面参数
						int setPageNum = Hc360Constants.getPageNum();
						if(setPageNum>0){
							pageNum = setPageNum;
						}
						Hc360Constants.ylqx_total = pageNum;
					}
					urlList = getCommnetList(Hc360Constants.YLQX_PRD_LIST_URL, pageNum,"?af=");
				}
				//药品
				else if(href.indexOf(Hc360Constants.YP_HOME)!=-1){
					if(elements!=null&&!elements.isEmpty()){
						String value = elements.text();
						pageNum = Integer.parseInt(String.valueOf(Utils.getNumber(value).get(0)));
						//获取指定页面参数
						int setPageNum = Hc360Constants.getPageNum();
						if(setPageNum>0){
							pageNum = setPageNum;
						}
						Hc360Constants.yp_total = pageNum;
					}
					urlList = getCommnetList(Hc360Constants.YP_PRD_LIST_URL, pageNum,"&af=");
				}
				
				new Step1Controller().door(urlList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 构建分页
	 * @author ZhangLujun<2015年12月18日>
	 * @param href
	 * @param pageNum
	 * @param suffix
	 * @return
	 */
	private List<String> getCommnetList(String href,int pageNum,String suffix){
		List<String> list = new LinkedList<String>();
		for (int i = 1; i <= pageNum; i++) {
			String url = href.replace("{0}",i+"");
			list.add(url);
			String  url_pre = url;
			url = url_pre + suffix +"1";
			list.add(url);
			url = url_pre + suffix +"2";
			list.add(url);
		}
		return list;
	}
	
}
