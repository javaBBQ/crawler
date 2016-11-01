/*
 *Project: glorypty-crawler
 *File: com.glorypty.crawler.common.CrawlerSourceEnum.java <2015-04-06>
 ****************************************************************
 * 版权所有@2015 国裕秀网络科技  保留所有权利.
 ***************************************************************/ 
package com.glorypty.crawler.common;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.glorypty.crawler.utils.CommonAreaSingleton;

/**
 * 爬虫枚举类
 * @Author hardy(admin@xkshow.cn)
 * @Date 2015年4月6日 上午11:20:07
 */
public class ConstantsCrawler {
	
	/**地区单例*/
	public static CommonAreaSingleton commonAreaSingleton = CommonAreaSingleton.getSingleton();
	
	/** 爬取时数据存放目录 */
	public static String CRAWL_STORAGE = "target/crawl/";
	
	/**
	 * 爬虫数据源枚举
	 * @Author hardy 2015年4月6日 上午11:24:46
	 */
	public enum CrawlerSourceEnum{
		/**国家食品药品监督管理总局*/SFDA,
		/**国家食品药品监督管理总局*/SFDACASE,
		/**国家食品药品监督管理总局*/SFDANEW,
		/**国家食品药品监督管理总局*/SFDAMALL,
		/**中国医药信息网-行业资讯*/CPI_HYZX,
		/**米内网*/MENET,
		/**百度虫*/BAIDUYY,
		/**100医药网*/YIYAO100,
		/**药房网*/YAOFANGWANG,
		/**医药联盟*/YIYAOLM,
		/**医药公信网*/YAO1,
		/**环球医药网*/QGYYZS,
		/**医药界*/YIYAOJIE,
		/**39健康网*/DRUG39,
		/**ehaoyao网*/EHAOYAO,
		/**聪慧网供应*/HC360,
		/**聪慧网求购*/HC360QG,
		/**阿里巴巴网*/ALIBABA,
		/**药路通*/YAOLUTONG
		;
	}	
	
	/**
	 * 联盟类型
	 * @Author hardy<2015年5月21日>
	 */
	public enum AllianceTypeEnum{
		/**招商*/MERCHANTS,
		/**代理*/AGENT
	}
	
	/**
	 * 剔除文本内容中的img
	 * @author Andy
	 * @param doc
	 * @param href
	 */
	public static void clearImg(Document doc,String href){
		Elements elements = doc.select("img");
		if(elements!=null && !elements.isEmpty()){
			for (Element element : elements) {
				element.remove();
			}
		}
	}
}
