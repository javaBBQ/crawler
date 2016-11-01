/*
 *Project: glorypty-crawler
 *File: com.glorypty.crawler.SfdaCrawler.java	<2015年4月2日>
 ****************************************************************
 * 版权所有@2015 国裕网络科技  保留所有权利.
 ***************************************************************/
package com.glorypty.crawler.sfdanew.step;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import cn.xkshow.net.URLHelper;

import com.glorypty.crawler.base.BaseCrawler;
import com.glorypty.crawler.common.PageParam;
import com.glorypty.crawler.sfdanew.Constants;
import com.glorypty.crawler.sfdanew.TempDB;

import edu.uci.ics.crawler4j.crawler.Page;

/**
 * 采集分类.内容[页码]URL by 主页
 * @Author hardy
 * @Date 2015年4月2日 上午11:29:59
 */
public class Step1Crawler extends BaseCrawler {

	@Override
	public void visit(Page page) {
		Document doc = this.getDocument(page);
		if (null != doc) {	
			switch (Constants.NUMBER_STEP) {
				case 2:		
					this.visitToStep3(doc);
					break;	
				case 3:			
					this.visitToStep2(doc);
					break;	
				default:
					break;
			}
		}
	}
	
	/**
	 * 下一步访问：列表页
	 * @Author hardy<2015年4月14日>
	 * @param doc
	 */
	private void visitToStep2(Document doc) {
		Elements contents = doc.select("td.new_datafont1 a");
		String tableId/*分类*/, linkParam/*参数*/, totalFont/*总数*/;
		int dataCount;
		PageParam pageParam = new PageParam(15);
		List<String> lstUrls = new ArrayList<String>();
		long dataCountAll = 0;
		for (Element element : contents) {
			// 鉴定执行分类
			if(null!=Constants.PARAM_URL_GROUP && Constants.PARAM_URL_GROUP>0){
				if(!element.attr("href").startsWith("base.jsp?tableId=" + Constants.PARAM_URL_GROUP+"&")){					
					continue;
				}
				tableId = String.valueOf(Constants.PARAM_URL_GROUP);
				linkParam = "tableId=" + Constants.PARAM_URL_GROUP + "&" + URLHelper.getParam(element.attr("href"), "tableName");	
			}
			else{
				tableId = URLHelper.getValue(element.attr("href"), "tableId");				
				linkParam = MessageFormat.format("tableId={0}&tableName={1}", tableId, URLHelper.getValue(element.attr("href"), "tableName"));
			}
			/*
				if(!element.attr("href").startsWith("base.jsp?tableId=")){					
					continue;
				}
				tableId = URLHelper.getValue(element.attr("href"), "tableId");				
				linkParam = MessageFormat.format("tableId={0}&tableName={1}", tableId, URLHelper.getValue(element.attr("href"), "tableName"));
			*/
			
			
			// 鉴定分类有效性
			if(StringUtils.isNotEmpty(tableId) && StringUtils.isNotEmpty(linkParam)){
				// 是否匹配:预抓取分类
				//
				if(!TempDB.DATA_TABLE.containsKey(tableId)){
					continue;
				}
				// 内容总数 by 分类
				totalFont = element.select("font.new_gray").text().trim();
				if(StringUtils.isEmpty(totalFont) && totalFont.length() <= 2){
					continue;
				}
				dataCount = Constants.PARAM_DATAID_END >0
							? Constants.PARAM_DATAID_END
							: Integer.valueOf(totalFont.substring(1, totalFont.length()-1));
				dataCountAll += dataCount;
				
				// 分页组件
				pageParam.setTotal(dataCount);
				pageParam.executeCalculate();
								
				logger.info(MessageFormat.format("构建：分类.内容页码({0}条.{1}页)>Start: {2}", dataCount, pageParam.getPageTotal(), element.attr("href")));					
				for (int idx = Constants.PARAM_DATAID_START>0?Constants.PARAM_DATAID_START:1; idx <= pageParam.getPageTotal(); idx++) {
					lstUrls.add(Constants.URL_PREFIX_LEV22 + linkParam + "&curstart="+ idx);
				}					
			}
			
			if(null!=lstUrls && !lstUrls.isEmpty()){
				try {
					this.logger.info(MessageFormat.format("爬虫预爬取数据汇总：共{0}条，共{1}页", dataCountAll, lstUrls.size()));
					new Step2Controller().door(lstUrls);
				} catch (Exception e) {
					this.logger.error(e);
				}
			}
		}
	}
	
	/**
	 * 下一步访问：详情页
	 * @Author hardy<2015年4月14日>
	 * @param doc
	 */
	@Deprecated
	private void visitToStep3(Document doc) {
		Elements contents = doc.select("td.new_datafont1 a");
		String linkParam, totalFont;
		int dataCount;
		List<String> lstUrls = new ArrayList<String>();
		for (Element element : contents) {				
			// 鉴定执行分类
			if(null!=Constants.PARAM_URL_GROUP && Constants.PARAM_URL_GROUP>0){
				if(!element.attr("href").startsWith("base.jsp?tableId=" + Constants.PARAM_URL_GROUP)){
					continue;
				}
				linkParam = "tableId=" + Constants.PARAM_URL_GROUP + "&" + URLHelper.getParam(element.attr("href"), "tableName");	
			}else{
				linkParam = URLHelper.getParam(element.attr("href"), "tableId") + "&" + URLHelper.getParam(element.attr("href"), "tableName");
			}
			// 鉴定分类有效性
			if(StringUtils.isNotEmpty(linkParam)){
				// 内容总数 by 分类
				totalFont = element.select("font.new_gray").text().trim();
				if(StringUtils.isEmpty(totalFont) && totalFont.length() <= 2){
					continue;
				}	
				dataCount = Constants.PARAM_DATAID_END >0
							? Constants.PARAM_DATAID_END
							: Integer.valueOf(totalFont.substring(1, totalFont.length()-1));
				
				logger.info(MessageFormat.format("构建：分类.内容详情页({0}条)>Start: {2}", dataCount, element.attr("href")));
				for (int idx = Constants.PARAM_DATAID_START>0?Constants.PARAM_DATAID_START:1; idx <= dataCount; idx++) {
					lstUrls.add(Constants.URL_PREFIX_LEV3 + linkParam + "&Id="+ idx);
				}
			}
		}
		if(null!=lstUrls && !lstUrls.isEmpty()){
			try {
				this.logger.info(MessageFormat.format("爬虫预爬取数据汇总：共{0}条", lstUrls.size()));
				new com.glorypty.crawler.sfdanew.step.Step3Controller().door(lstUrls);
//				new Step3Controller().doorError();
			} catch (Exception e) {
				this.logger.error(e);
			}
		}
	}
	
}
