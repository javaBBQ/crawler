package com.glorypty.crawler.menet;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 米内网常量公共类
 * @author Zhanglujun
 * 
 */
public class MenetConstants {
	
	/**爬虫爬取首页记录*/
	public static String report_url = "";	
	
	/**行业资讯-医改新闻-主页*/
	public static final String HYZX_YGXW_URL_HOME = "http://www.menet.com.cn/List.aspx?classcode=1962";	
	/**行业资讯-产经要闻-主页*/
	public static final String HYZX_CJYW_URL_HOME = "http://www.menet.com.cn/List.aspx?classcode=202";	
	/**行业资讯-医院资讯-主页*/
	public static final String HYZX_YYZX_URL_HOME = "http://www.menet.com.cn/List.aspx?classcode=1973";	
	/**行业资讯-医药电商-主页*/
	public static final String HYZX_YYDS_URL_HOME = "http://www.menet.com.cn/List.aspx?classcode=1975";	
	/**行业资讯-国际视野-主页*/
	public static final String HYZX_GJSY_URL_HOME = "http://www.menet.com.cn/List.aspx?classcode=1974";		
	/**行业资讯-药店快报-主页*/
	public static final String HYZX_YDKB_URL_HOME = "http://www.menet.com.cn/List.aspx?classcode=207";	
	/**行业资讯-社区基层-主页*/
	public static final String HYZX_SQJC_URL_HOME = "http://www.menet.com.cn/List.aspx?classcode=210";	
	/**行业资讯-终端视野-主页*/
	public static final String HYZX_ZDSY_URL_HOME = "http://www.menet.com.cn/List.aspx?classcode=1976";	
	/**行业资讯-行业研究-主页*/
	public static final String HYZX_HYYY_URL_HOME = "http://www.menet.com.cn/List.aspx?classcode=1600";		
	/**行业资讯-环球视野-主页*/
	public static final String HYZX_HQSY_URL_HOME = "http://www.menet.com.cn/List.aspx?classcode=211";	
	/**行业资讯-延报-主页*/
	public static final String HYZX_YB_URL_HOME = "http://www.menet.com.cn/List.aspx?classcode=1601";	
	/**行业资讯-新闻-主页*/
	public static final String HYZX_NEWS_URL_HOME = "http://www.menet.com.cn/List.aspx?classcode=1598";	
	/**行业资讯-公告-主页*/
	public static final String HYZX_NOTICES_URL_HOME = "http://www.menet.com.cn/List.aspx?classcode=1599";	
	
	/**政策法规-政策解读-主页*/
	public static final String ZCFG_ZCJD_URL_HOME = "http://www.menet.com.cn/List.aspx?classcode=201";	
	/**政策法规-鲜点热评-主页*/
	public static final String ZCFD_XDRP_URL_HOME = "http://www.menet.com.cn/List.aspx?classcode=1964";	
	
	/**最新科研-研发品种-主页*/
	public static final String ZXKY_YFPZ_URL_HOME = "http://www.menet.com.cn/List.aspx?classcode=206";	
	/**最新科研-研发动向-主页*/
	public static final String ZXKY_YFDXZ_URL_HOME = "http://www.menet.com.cn/List.aspx?classcode=71";	
	/**最新科研-审批研报-主页*/
	public static final String ZXKY_SPYB_URL_HOME = "http://www.menet.com.cn/List.aspx?classcode=1991";	
	/**最新科研-新药分析-主页*/
	public static final String ZXKY_XYFX_URL_HOME = "http://www.menet.com.cn/List.aspx?classcode=1990";	
	
	/**商情分析-创新联盟-主页*/
	public static final String SQFX_CXLM_URL_HOME = "http://www.menet.com.cn/List.aspx?classcode=1995";	
	/**商情分析-行业研究报告-主页*/
//	public static final String SQFX_HYYJ_URL_HOME = "http://www.menet.com.cn/ArticleList.aspx?classid=1507";	
	/**商情分析-米内数据-主页*/
	public static final String SQFX_MNSJ_URL_HOME = "http://www.menet.com.cn/ListTitle.aspx?classcode=688";
	
	/**独家话题-热点对话-主页*/
	public static final String DJHT_RDDF_URL_HOME = "http://www.menet.com.cn/List.aspx?classcode=405";
	
	/**生意经-经典案例-主页*/
	public static final String SYJ_JDAL_URL_HOME = "http://www.menet.com.cn/List.aspx?classcode=401";	
	/**生意经-营销技巧-主页*/
	public static final String SYJ_YXJQ_URL_HOME = "http://www.menet.com.cn/List.aspx?classcode=402";	
	
	/**调查报告*/
	public static final String DCBG_URL_HOME = "http://www.menet.com.cn/List.aspx?classID=984";
	
	/**行业资讯-详细内容*/
	public static final String HYZX_YGXW_URL_CONTENT = "http://www.menet.com.cn/Articles/";
	
	/**MENET_URL_HOME*/
	public static final String MENET_URL_HOME = "http://www.menet.com.cn";
	
	/**URL集合*/
	public static ArrayList<String> menetUrlList = new ArrayList<String>();
	
	/**标题-分类 集合*/
	public static HashMap<String,Integer> titleMap = new HashMap<String, Integer>();
	
	/**
	 * 信息抓取有效时间
	 */
	public static int PAST_MONTH = -5;
	
	
	/**
	 * URL集合创建
	 * value URL列表页、URL详细内容页
	 * @return
	 */
	public static ArrayList<String> initList(){
		//行业资讯
		menetUrlList.add(HYZX_CJYW_URL_HOME);
		menetUrlList.add(HYZX_GJSY_URL_HOME);
		menetUrlList.add(HYZX_HYYY_URL_HOME);
		menetUrlList.add(HYZX_HYYY_URL_HOME);
		menetUrlList.add(HYZX_NEWS_URL_HOME);
		menetUrlList.add(HYZX_NOTICES_URL_HOME);
		menetUrlList.add(HYZX_SQJC_URL_HOME);
		menetUrlList.add(HYZX_YB_URL_HOME);
		menetUrlList.add(HYZX_YDKB_URL_HOME);
		menetUrlList.add(HYZX_YGXW_URL_HOME);
		menetUrlList.add(HYZX_YYDS_URL_HOME);
		menetUrlList.add(HYZX_YYZX_URL_HOME);
		menetUrlList.add(HYZX_ZDSY_URL_HOME);
		//政策法规
		menetUrlList.add(ZCFD_XDRP_URL_HOME);
		menetUrlList.add(ZCFG_ZCJD_URL_HOME);
		//最新科研
		menetUrlList.add(ZXKY_SPYB_URL_HOME);
		menetUrlList.add(ZXKY_XYFX_URL_HOME);
		menetUrlList.add(ZXKY_YFDXZ_URL_HOME);
		menetUrlList.add(ZXKY_YFPZ_URL_HOME);
		//商情分析
		menetUrlList.add(SQFX_CXLM_URL_HOME);
		menetUrlList.add(SQFX_MNSJ_URL_HOME);
		//独家话题
		menetUrlList.add(DJHT_RDDF_URL_HOME);
		//生意经
		menetUrlList.add(SYJ_JDAL_URL_HOME);
		menetUrlList.add(SYJ_YXJQ_URL_HOME);
		//调查报告
		menetUrlList.add(DCBG_URL_HOME);
		//详细页
		menetUrlList.add(HYZX_YGXW_URL_CONTENT);
		return menetUrlList;
	}
	
	/**
	 * URL集合获取
	 * @return
	 */
	public static ArrayList<String> getList(){		
		if(menetUrlList==null||menetUrlList.isEmpty()){
			initList();
		}
		return menetUrlList;
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
	 * @return
	 */
	public static HashMap<String,Integer> getTitileMap(){
		if(titleMap==null||titleMap.isEmpty()){
			//行业资讯
			titleMap.put(HYZX_CJYW_URL_HOME, new Integer(100));
			titleMap.put(HYZX_GJSY_URL_HOME, new Integer(100));
			titleMap.put(HYZX_HYYY_URL_HOME, new Integer(100));
			titleMap.put(HYZX_HYYY_URL_HOME, new Integer(100));
			titleMap.put(HYZX_NEWS_URL_HOME, new Integer(100));
			titleMap.put(HYZX_NOTICES_URL_HOME, new Integer(100));
			titleMap.put(HYZX_SQJC_URL_HOME, new Integer(100));
			titleMap.put(HYZX_YB_URL_HOME, new Integer(100));
			titleMap.put(HYZX_YDKB_URL_HOME, new Integer(100));
			titleMap.put(HYZX_YGXW_URL_HOME, new Integer(100));
			titleMap.put(HYZX_YYDS_URL_HOME, new Integer(100));
			titleMap.put(HYZX_YYZX_URL_HOME, new Integer(100));
			titleMap.put(HYZX_ZDSY_URL_HOME, new Integer(100));
			//政策法规
			titleMap.put(ZCFD_XDRP_URL_HOME, new Integer(101));
			titleMap.put(ZCFG_ZCJD_URL_HOME, new Integer(101));
			//最新科研
			titleMap.put(ZXKY_SPYB_URL_HOME, new Integer(103));
			titleMap.put(ZXKY_XYFX_URL_HOME, new Integer(103));
			titleMap.put(ZXKY_YFDXZ_URL_HOME, new Integer(103));
			titleMap.put(ZXKY_YFPZ_URL_HOME, new Integer(103));
			//商情分析
			titleMap.put(SQFX_CXLM_URL_HOME, new Integer(104));
			titleMap.put(SQFX_MNSJ_URL_HOME, new Integer(104));
			//独家话题
			titleMap.put(DJHT_RDDF_URL_HOME, new Integer(106));
			//生意经
			titleMap.put(SYJ_JDAL_URL_HOME, new Integer(131));
			titleMap.put(SYJ_YXJQ_URL_HOME, new Integer(131));
			//调查报告
			titleMap.put(DCBG_URL_HOME, new Integer(128));
		}
		return titleMap;
	}
}
