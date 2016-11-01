package com.glorypty.crawler.alibaba;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.glorypty.crawler.utils.CommonAreaSingleton;

/**阿里巴巴
 * 供应信息-医药、保养
 * http://s.1688.com/company/-D2BDD2A9A1A2B1A3D1F8.html?spm=0.0.0.0.bfVtRU
 * @author yiwen
 *
 */
public class AlibabaConstants {
	public static CommonAreaSingleton commonAreaSingleton = CommonAreaSingleton.getSingleton();
	//验证码/登录  页面http://sec.1688.com/query.htm?smApp=searchweb2&smCharset=GBK&smPolicy=searchweb2-company-anti_Spider-html-checklogin&smReturn=http%3A%2F%2Fs.1688.com%2Fcompany%2F-D2BDD2A9A1A2B1A3D1F8.html&smSign=ZDVrja5tbGjpt%2BxB%2Fgb7oQ%3D%3D&smTag=MjAyLjE3MC4xMzkuMjI5LCw1NzFjYWZhZWFjMzE0NzY3ODRjOWU4M2Q0ODRhMDA2YQ%3D%3D
	//http://login.1688.com/member/signin.htm?Done=http%3A%2F%2Fsec.1688.com%2Fquery.htm%3Faction%3DQueryAction%26event_submit_do_login%3Dok%26smApp%3Dsearchweb2%26smPolicy%3Dsearchweb2-company-anti_Spider-html-checklogin%26smCharset%3DGBK%26smTag%3DMjAyLjE3MC4xMzkuMjI5LCwwYWNmYjg3ZjRhNTE0ZTJjYjBhM2ExZWQzMGEzNjMwZg%253D%253D%26smReturn%3Dhttp%253A%252F%252Fs.1688.com%252Fcompany%252F-D2BDD2A9A1A2B1A3D1F8.html%253FbeginPage%253D1%2526offset%253D3%2526pageSize%253D30%2526spm%253D0.0.0.0.bfVtRU%26smSign%3DLtRZ4RNggAVB6IpaDpY6Mg%253D%253D&from=sm
	//http://err.taobao.com/error2.html
	public static final String Alibaba_INVALID_HOME = "http://sec.1688.com/query.htm?smApp=searchweb2&smCharset=GBK&smPolicy=searchweb2-company-anti_Spider-html-checklogin";
	//http://alisec.1688.com/checkcodev3.php 无法处理你的请求
	//首页http://page.1688.com/
	//
	public static final String Alibaba_URL_HOME = "http://s.1688.com/company/-D2BDD2A9A1A2B1A3D1F8.html?spm=0.0.0.0.bfVtRU";
	public static final String Alibaba_URL = "http://s.1688.com/company/-D2BDD2A9A1A2B1A3D1F8.html";
	/*http://s.1688.com/company/-D2BDD2A9A1A2B1A3D1F8.html?spm=0.0.0.0.bfVtRU&pageSize=30&offset=3&beginPage=3*/
	public static final String Alibaba_PAGE_URL_HOME = "http://s.1688.com/company/-D2BDD2A9A1A2B1A3D1F8.html?spm=0.0.0.0.bfVtRU&pageSize=30&offset=3&beginPage={0}";
	
	
	/*&pageNow=4*/
	/*http://sbh73.1688.com/page/offerlist.htm?spm=0.0.0.0.Kywdxj&tracelog=p4p&pageNum=2*/
	public static final String Alibaba_PAGE_URL_HOME_PRE = "http://s.1688.com/company/-D2BDD2A9A1A2B1A3D1F8.html?spm=0.0.0.0.bfVtRU";
	public static final String Alibaba_DETAIL_PAGE_URL_PRE = "http://detail.1688.com/offer/";
	public static final String Alibaba_DOMAIN = "1688.com";
	
	public static final String HOMR_URL = "http://s.1688.com/company/-D2BDD2A9A1A2B1A3D1F8.html";
	public static int fixedNum = 106;
	public static int totalPage = 0;
	public static int productTotalPage = 0;
	public static int totalNum = 0;
	public static int productTotalNum = 0;
	
	public static int count = 0;
	
	/**详细页面*/
	public static final String COMMON_URL_CONTENT = "http://www.1688.com/company/";
	/**
	 * 访问第一页url集合
	 */
	public static Map<String,String> urlMap = new HashMap<String, String>();
	
	/**
	 * 访问第一页url集合
	 */
	public static List<String> urlList = new ArrayList<String>();
	
	/**
	 * 访问第二页url集合
	 */
	public static List<String> urlCompanyList = new ArrayList<String>();
	public static List<String> urlCompanyContactList = new ArrayList<String>();
	
	/**详细页集合*/
	public static List<String> detail_list = new ArrayList<String>();
	
	/**
	 * 信息抓取有效时间
	 */
	public static int PAST_MONTH = -5;
	
	public static List<String> getUrlList(){
		if(urlList==null||urlList.isEmpty()){
			urlList.add(Alibaba_URL_HOME);
			/*urlList.add("http://detail.0.com/");
			urlList.add("http://s.1688.com/company/");*/
		}
		return urlList;
	}
	
	
	
	public static Map<String,String> getUrlMap(){
		if(urlMap==null||urlMap.isEmpty()){
			urlMap.put(Alibaba_URL_HOME, Alibaba_PAGE_URL_HOME);
		}
		return urlMap;
	}
}
