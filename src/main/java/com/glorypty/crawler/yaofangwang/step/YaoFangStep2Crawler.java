package com.glorypty.crawler.yaofangwang.step;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.glorypty.crawler.base.BaseCrawler;
import com.glorypty.crawler.utils.HttpMethodSimulate;
import com.glorypty.crawler.yaofangwang.YaoFangConstants;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.url.WebURL;
/**
 * 药房网列表页解析
 * @author zhanglujun
 *
 */
public class YaoFangStep2Crawler extends BaseCrawler {

	@Override
	public boolean shouldVisit(Page page, WebURL url) {
		String href = url.getURL();
		if (href.indexOf(YaoFangConstants.YC_HOME) != -1) {
			Pattern pattern = Pattern.compile("[/](\\w+)[/]");
			Matcher matcher = pattern.matcher(href);
			while (matcher.find()) {
				return true;
			}
		}else if(href.indexOf(YaoFangConstants.YY_HOME)!=-1){
			if(href.indexOf("/hospital_")!=-1){
				return true;
			}
		}else if(href.indexOf(YaoFangConstants.YF_HOME)!=-1){
			if(href.lastIndexOf("shops-")!=-1
					&&href.indexOf("?")==-1){
				return true;
			}
		}
		return false;
	}

	@Override
	public void visit(Page page) {
		String href = page.getWebURL().getURL();
		
		//药企
		if (href.indexOf(YaoFangConstants.YC_HOME) != -1) {
			Pattern pattern = Pattern.compile("[/](\\w+)[/]");
			Matcher matcher = pattern.matcher(href);
			while (matcher.find()) {
				visitToYaoqi(page);
			}
		}
		//医院
		else if(href.indexOf(YaoFangConstants.YY_HOME)!=-1){
			if(href.indexOf("?")==-1){
				visitToYiyuan(page);
			}
		}
		//药房
		else if(href.indexOf(YaoFangConstants.YF_HOME)!=-1){
			visitToYaofang(page);
		}
	}

	/**
	 * 药房-读取每页分页详细
	 * @param page
	 */
	private void visitToYaofang(Page page) {
		boolean isFinish = false;
//		List<String> urlList = new ArrayList<String>();
		String href = page.getWebURL().getURL();
		this.logger.info("解析药房列表页:"+href);
		Document doc = this.getDocument(page);
		if(doc!=null){
			Elements elements = doc.select("ul[class=st1 small clearfix]>li>strong>a");
			if(elements!=null){
				for (Element element : elements) {
					String url = element.attr("href");
					YaoFangConstants.yf_detailUrlList.add(url);
				}
			}
			
			Elements page_elements = doc.select("span.current");
			int currentPage = Integer.parseInt(page_elements.text());
			if(YaoFangConstants.totalPage == currentPage){
				try {
					isFinish = true;
//					new YaoFangStep3Controller().door(YaoFangConstants.yf_detailUrlList);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		if(isFinish){
			if(YaoFangConstants.yf_detailUrlList!=null&&YaoFangConstants.yf_detailUrlList.size()>0){
				try {
					new YaoFangStep3Controller().door(YaoFangConstants.yf_detailUrlList);
					YaoFangConstants.yf_detailUrlList.clear();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 医院-读取每页分页详细
	 * @param page
	 */
	private void visitToYiyuan(Page page) {
		boolean flag = false;
//		List<String> urlList = new ArrayList<String>();
		String href = page.getWebURL().getURL();
//		String province = YaoFangConstants.yy_page_provinceMap.get(href);
		this.logger.info("解析医院列表页:"+href);
//		if (province != null && !"".equals(province)) {
			Document doc = this.getDocument(page);
			if(doc!=null){
				Elements elements = doc.select("div[class=yy clearfix]>div.left>ul.yslist>li>div.img>a");
				if(elements!=null){
					for (Element element : elements) {
						String url = element.attr("href").toString();
//						YaoFangConstants.yy_detail_provinceMap.put(url, province);
//						urlList.add(url);
						YaoFangConstants.yy_detailUrlList.add(url);
					}
				}
				
				Elements cur_page = doc.select("span.current");
				if(cur_page!=null&&!cur_page.isEmpty()){
					int currentPage = Integer.parseInt(cur_page.text());
					if(YaoFangConstants.yy_province_PageMap.get("全国").intValue()==currentPage){
						flag = true;
					}
				}
			}
//		}
		
		if(flag){
			if(YaoFangConstants.yy_detailUrlList!=null&&YaoFangConstants.yy_detailUrlList.size()>0){
				try {
					new YaoFangStep3Controller().door(YaoFangConstants.yy_detailUrlList);
					YaoFangConstants.yy_detailUrlList.clear();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 药企-读取每页分页详细
	 * @param page
	 */
	private void visitToYaoqi(Page page) {
		List<String> ulrList = new ArrayList<String>();
		String href = page.getWebURL().getURL();
		String province = YaoFangConstants.provinceMap.get(href);
		this.logger.info("解析药企列表页："+href+"==="+province);
		Document doc = this.getDocument(page);
		if(doc!=null){
			Elements elements = doc.select("div.container_full>div.yaoqi_table_list>div[class=bg_baise w194]>a");
			for (Element element : elements) {
				String url = element.attr("href");
				YaoFangConstants.url_provinceMap.put(url, province);
				ulrList.add(url);
			}
		}
		
		if(ulrList!=null&&ulrList.size()>0){
			try {
				new YaoFangStep3Controller().door(ulrList);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	// private String cutString(String str,String keyword){
	// return str.substring(str.indexOf(keyword)+keyword.length()+1);
	// }
}
