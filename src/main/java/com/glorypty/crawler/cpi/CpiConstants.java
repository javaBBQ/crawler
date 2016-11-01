package com.glorypty.crawler.cpi;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import com.glorypty.crawler.common.ConstantsCrawler;

/**
 * 中国医药信息网常量类
 * @author Zhanglujun
 *
 */
public class CpiConstants{

	/**CPI_URL_HOME*/
	public static final String CPI_URL_HOME = "www.cpi.ac.cn";	
	
	/**行业资讯-行业资讯-主页*/
	public static final String HYZX_HYZX_URL_HOME = "http://www.cpi.ac.cn/publish/default/hyzx/";	
	public static final String HYZX_HYZX_PAGE_URL_HOME = "http://www.cpi.ac.cn/publish/default/hyzx/index";	
	/**行业资讯-行业资讯-详细内容*/
	public static final String HYZX_HYZX_URL_CONTENT = "http://www.cpi.ac.cn/publish/default/hyzx/content/";
	
	/**行业资讯-海外资讯-主页*/
	public static final String HYZX_HWZX_URL_HOME = "http://www.cpi.ac.cn/publish/default/hwzx/";
	public static final String HYZX_HWZX_PAGE_URL_HOME = "http://www.cpi.ac.cn/publish/default/hwzx/index";
	/**行业资讯-海外资讯-详细内容*/
	public static final String HYZX_HWZX_URL_CONTENT= "http://www.cpi.ac.cn/publish/default/hwzx/content/";
	
	/**行业资讯-药企动态-主页*/
	public static final String HYZX_YQDT_URL_HOME="http://www.cpi.ac.cn/publish/default/yqdt/";
	public static final String HYZX_YQDT_PAGE_URL_HOME="http://www.cpi.ac.cn/publish/default/yqdt/index";
	/**行业资讯-药企动态-详细内容*/
	public static final String HYZX_YQDT_URL_CONTENT="http://www.cpi.ac.cn/publish/default/yqdt/content/";
	
	/**行业资讯-医改动态-主页*/
	public static final String HYZX_YGDT_URL_HOME="http://www.cpi.ac.cn/publish/default/ygdt/";
	public static final String HYZX_YGDT_PAGE_URL_HOME="http://www.cpi.ac.cn/publish/default/ygdt/index";
	/**行业资讯-医改动态-详细内容*/
	public static final String HYZX_YGDT_URL_CONTENT="http://www.cpi.ac.cn/publish/default/ygdt/content/";

	
	/**政策法规-政策法规-主页*/
	public static final String ZCFG_ZCFG_URL_HOME="http://www.cpi.ac.cn/publish/default/bmgz/";
	public static final String ZCFG_ZCFG_PAGE_URL_HOME="http://www.cpi.ac.cn/publish/default/bmgz/index";
	/**政策法规-政策法规-详细内容*/
	public static final String ZCFG_ZCFG_URL_CONTENT="http://www.cpi.ac.cn/publish/default/bmgz/content/";
	
	/**政策法规-部门规章-主页*/
	public static final String ZCFG_BMGZ_URL_HOME="http://www.cpi.ac.cn/publish/default/flfg/";
	public static final String ZCFG_BMGZ_PAGE_URL_HOME="http://www.cpi.ac.cn/publish/default/flfg/index";
	/**政策法规-部门规章-详细内容*/
	public static final String ZCFG_BMGZ_URL_CONTENT="http://www.cpi.ac.cn/publish/default/flfg/content/";
	
	/**政策法规-政策解读-主页*/
	public static final String ZCFG_ZCJD_URL_HOME="http://www.cpi.ac.cn/publish/default/zcjd/";
	public static final String ZCFG_ZCJD_PAGE_URL_HOME="http://www.cpi.ac.cn/publish/default/zcjd/index";
	/**政策法规-政策解读-详细内容*/
	public static final String ZCFG_ZCJD_URL_CONTENT="http://www.cpi.ac.cn/publish/default/zcjd/content/";
	
	/**政策法规-地方法规-主页*/
	public static final String ZCFG_DFFG_URL_HOME="http://www.cpi.ac.cn/publish/default/dfxfghgz1/";
	public static final String ZCFG_DFFG_PAGE_URL_HOME="http://www.cpi.ac.cn/publish/default/dfxfghgz1/index";
	/**政策法规-地方法规-详细内容*/
	public static final String ZCFG_DFFG_URL_CONTENT="http://www.cpi.ac.cn/publish/default/dfxfghgz1/content/";
	
	/**政策法规-规范文件-主页*/
	public static final String ZCFG_GFWJ_URL_HOME="http://www.cpi.ac.cn/publish/default/gfwj/";
	public static final String ZCFG_GFWJ_PAGE_URL_HOME="http://www.cpi.ac.cn/publish/default/gfwj/index";
	/**政策法规-政策法规-详细内容*/
	public static final String ZCFG_GFWJ_URL_CONTENT="http://www.cpi.ac.cn/publish/default/gfwj/content/";
	
	
	/**医药报告-医药资料-主页*/
	public static final String YYBG_YYZL_URL_HOME = "http://www.cpi.ac.cn/publish/default/xgzlnwe/";
	public static final String YYBG_YYZL_PAGE_URL_HOME = "http://www.cpi.ac.cn/publish/default/xgzlnwe/index";
	/**医药报告-医药资料-详细内容*/
	public static final String YYBG_YYZL_URL_CONTENT = "http://www.cpi.ac.cn/publish/default/xgzlnwe/content/";
	
	/**医药报告-研究报告-主页*/
	public static final String YYBG_YJBG_URL_HOME = "http://www.cpi.ac.cn/publish/default/yjbg/";
	public static final String YYBG_YJBG_PAGE_URL_HOME = "http://www.cpi.ac.cn/publish/default/yjbg/index";
	/**医药报告-研究报告-详细内容*/
	public static final String YYBG_YJBG_URL_CONTENT = "http://www.cpi.ac.cn/publish/default/yjbg/content/";
	
	/**医药报告-药物质量关注-主页*/
	public static final String YYBG_YWZLGZ_URL_HOME = "http://www.cpi.gov.cn/publish/default/zlgz/";
	public static final String YYBG_YWZLGZ_PAGE_URL_HOME = "http://www.cpi.gov.cn/publish/default/zlgz/index";
	/**医药报告-药物质量关注-详细内容*/
	public static final String YYBG_YWZLGZ_URL_CONTENT = "http://www.cpi.gov.cn/publish/default/zlgz/content/";
	
	/**医药报告-药物质量地方动态-主页*/
	public static final String YYBG_YWZLDFDT_URL_HOME = "http://www.cpi.ac.cn/publish/default/yzgl/";
	public static final String YYBG_YWZLDFDT_PAGE_URL_HOME = "http://www.cpi.ac.cn/publish/default/yzgl/index";
	/**医药报告-药物质量地方动态-详细内容*/
	public static final String YYBG_YWZLDFDT_URL_CONTENT = "http://www.cpi.ac.cn/publish/default/yzgl/content/";
	
	/**医药报告-药物质量通知公告-主页*/
	public static final String YYBG_YWZLTX_URL_HOME = "http://www.cpi.ac.cn/publish/default/tzgg/";
	public static final String YYBG_YWZLTX_PAGE_URL_HOME = "http://www.cpi.ac.cn/publish/default/tzgg/index";
	/**医药报告-药物质量通知公告-详细内容*/
	public static final String YYBG_YWZLTX_URL_CONTENT = "http://www.cpi.ac.cn/publish/default/tzgg/content/";
	/***医药报告-药监局*/
	public static final String YYBG_SDA_URL_CONTETN = "http://www.sda.gov.cn/WS01/";
	
	/**
	 * GMP认证
	 */
	public static final String YYBG_GMP_URL_HOME = "http://www.cpi.ac.cn/publish/default/ypgmprz/";
	public static final String YYBG_GMP_PAGE_URL_HOME = "http://www.cpi.ac.cn/publish/default/ypgmprz/index";
	public static final String YYBG_GMP_URL_CONTENT = "http://www.cpi.ac.cn/publish/default/ypgmprz/content/";
	

	
	/**最新科研-科研进展-主页*/
	public static final String ZXKY_KYJZ_URL_HOME = "http://www.cpi.ac.cn/publish/default/kjjz/";
	public static final String ZXKY_KYJZ_PAGE_URL_HOME = "http://www.cpi.ac.cn/publish/default/kjjz/index";
	/**最新科研-科研进展-详细内容*/
	public static final String ZXKY_KYJZ_URL_CONTENT = "http://www.cpi.ac.cn/publish/default/kjjz/content/";
	
	/**展会论坛-会议展会-主页*/
	public static final String ZHLT_HYJZ_URL_HOME = "http://www.cpi.ac.cn/publish/default/meeting/";
	public static final String ZHLT_HYJZ_PAGE_URL_HOME = "http://www.cpi.ac.cn/publish/default/meeting/index";
	/**展会论坛-会议展会-详细内容*/
	public static final String ZHLT_HYJZ_URL_CONTENT = "http://www.cpi.ac.cn/publish/default/meeting/content/";
	
	/**招标信息-招标采购-主页*/
	public static final String ZBXX_ZBCG_URL_HOME = "http://www.cpi.ac.cn/publish/default/zbcg/";
	public static final String ZBXX_ZBCG_PAGE_URL_HOME = "http://www.cpi.ac.cn/publish/default/zbcg/index";
	/**招标信息-招标采购-详细内容*/
	public static final String ZBXX_ZBCG_URL_CONTENT = "http://www.cpi.ac.cn/publish/default/zbcg/content/";
	
	
	public static final String COMMON_CONTENT_TAG = "/content/";

	
	/** 爬取时数据存放目录 */
	public static String CRAWL_STORAGE = ConstantsCrawler.CRAWL_STORAGE + "sfda/";
	
	/** 异常爬取记录:错误URL */
	public static final String CRAWL_ERROR_PARSE = CRAWL_STORAGE + "error_parse.txt";
	
	/** 编码格式 */
	public static String HTML_CHARSET = "UTF-8";
	
	/** 生成页面模板 */
	public static final String CRAWLER_DOWNLOAD_CONTENT = "<html xmlns=\"http://www.w3.org/1999/xhtml\"><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset={0}\"/></head><body>{1}</body></html>";
	
	/**URL集合*/
	public static LinkedHashMap<String,String> CPIUrlMap = new LinkedHashMap<String, String>();
	
	/**详细页集合*/
	public static List<String> detail_list = new ArrayList<String>();
	
	/**
	 * 信息抓取有效时间
	 */
	public static int PAST_MONTH = -5;
	
	/**
	 * URL集合创建
	 * key URL列表页 
	 * value  URL详细内容页
	 * @return 
	 */
	public static LinkedHashMap<String,String> initMap(){
		//行业资讯
		CPIUrlMap.put(HYZX_HYZX_URL_HOME, HYZX_HYZX_PAGE_URL_HOME);
		CPIUrlMap.put(HYZX_HWZX_URL_HOME, HYZX_HWZX_PAGE_URL_HOME);
		CPIUrlMap.put(HYZX_YQDT_URL_HOME, HYZX_YQDT_PAGE_URL_HOME);
		CPIUrlMap.put(HYZX_YGDT_URL_HOME, HYZX_YGDT_PAGE_URL_HOME);
		//政策法规
		CPIUrlMap.put(ZCFG_BMGZ_URL_HOME, ZCFG_BMGZ_PAGE_URL_HOME);
		CPIUrlMap.put(ZCFG_DFFG_URL_HOME, ZCFG_DFFG_PAGE_URL_HOME);
		CPIUrlMap.put(ZCFG_GFWJ_URL_HOME, ZCFG_GFWJ_PAGE_URL_HOME);
		CPIUrlMap.put(ZCFG_ZCFG_URL_HOME, ZCFG_ZCFG_PAGE_URL_HOME);
		CPIUrlMap.put(ZCFG_ZCJD_URL_HOME, ZCFG_ZCJD_PAGE_URL_HOME);
		//研究报告
		CPIUrlMap.put(YYBG_YJBG_URL_HOME, YYBG_YJBG_PAGE_URL_HOME);
		CPIUrlMap.put(YYBG_YWZLDFDT_URL_HOME, YYBG_YWZLDFDT_PAGE_URL_HOME);
		CPIUrlMap.put(YYBG_YWZLGZ_URL_HOME, YYBG_YWZLGZ_PAGE_URL_HOME);
		CPIUrlMap.put(YYBG_YWZLTX_URL_HOME, YYBG_YWZLTX_PAGE_URL_HOME);
		CPIUrlMap.put(YYBG_YYZL_URL_HOME, YYBG_YYZL_PAGE_URL_HOME);
		CPIUrlMap.put(YYBG_GMP_URL_HOME, YYBG_GMP_PAGE_URL_HOME);
		//最新科研
		CPIUrlMap.put(ZXKY_KYJZ_URL_HOME, ZXKY_KYJZ_PAGE_URL_HOME);
		//展会论坛
		CPIUrlMap.put(ZHLT_HYJZ_URL_HOME, ZHLT_HYJZ_PAGE_URL_HOME);
		//招标信息
		CPIUrlMap.put(ZBXX_ZBCG_URL_HOME, ZBXX_ZBCG_PAGE_URL_HOME);
		
		return CPIUrlMap;
	}
	
	/**
	 * URL集合获取
	 * @return
	 */
	public static LinkedHashMap<String,String> getMap(){		
		if(CPIUrlMap==null||CPIUrlMap.isEmpty()){
			initMap();
		}
		return CPIUrlMap;
	}
}
