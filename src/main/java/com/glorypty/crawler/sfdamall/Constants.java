/*
 *Project: glorypty-crawler
 *File: com.glorypty.crawler.sfda.Constants.java	<2015年4月2日 >
 ****************************************************************
 * 版权所有@2015 国裕秀网络科技  保留所有权利.
 ***************************************************************/
package com.glorypty.crawler.sfdamall;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import com.glorypty.crawler.common.ConstantsCrawler;

/**
 *
 * @Author yiwen
 * @Date 2015年4月2日 下午3:15:45
 */
public class Constants {
	/**
	 * 执行步骤数量(值2|3)：三步可缩减(因每个分类的文章序列均是从1开始)为两步，即中间步骤可省略
	 * 1.http://app1.sfda.gov.cn/datasearch/face3/dir.html
	 * 2.http://app1.sfda.gov.cn/datasearch/face3/base.jsp
	 * 	---->转http://app1.sfda.gov.cn/datasearch/face3/search.jsp?tableId=108&curstart=2
	 * 3.http://app1.sfda.gov.cn/datasearch/face3/content.jsp?tableId=108&tableName=TABLE108&Id=24
	 */
	public static Integer NUMBER_STEP = 3;	
	
	/** 分类执行参数：X>0表示获取指定分类，[仅支持单类型的扩容.容错处理]否则执行所有分类 */
	public static Integer PARAM_URL_GROUP = 0;
	/** (单类型)索引.开始：X>0按指定ID[页码]开始，否则从1开始 */
	public static Integer PARAM_DATAID_START = 0;	
	/** (单类型)索引.结束：X>0按指定ID[页码]结束，否则完整执行 */
	public static Integer PARAM_DATAID_END = 0;
	
	/** 编码格式 */
	public static String HTML_CHARSET = "UTF-8";
	/** 访问主页 */
	public static final String URL_HOME = "http://app1.sfda.gov.cn/datasearch/face3/dir.html";	
	/** 二级访问前缀 */
	public static final String URL_PREFIX_LEV2 = "http://app1.sfda.gov.cn/datasearch/face3/base.jsp?" + (PARAM_URL_GROUP>0 ? "tableId="+PARAM_URL_GROUP : "");
	/** 二级访问前缀：分页 */
	public static final String URL_PREFIX_LEV22 = "http://app1.sfda.gov.cn/datasearch/face3/search.jsp?";	
	/** 三级访问前缀 */
	public static final String URL_PREFIX_LEV3 = "http://app1.sfda.gov.cn/datasearch/face3/content.jsp?";
	public static final String URL_PREFIX_LEV3_PRE = "http://app1.sfda.gov.cn/datasearch/face3/";
	
	/** 爬取时数据存放目录 */
	public static String CRAWL_STORAGE = ConstantsCrawler.CRAWL_STORAGE + "sfdamall/";
	/** 异常爬取记录:错误URL */
	public static final String CRAWL_ERROR_PARSE = CRAWL_STORAGE + "error_parse.txt";
	
	/** 生成页面模板 */
	public static final String CRAWLER_DOWNLOAD_CONTENT = "<html xmlns=\"http://www.w3.org/1999/xhtml\"><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset={0}\"/></head><body>{1}</body></html>";
	
	/**详细链接*/
	public static List<String> detailList = new ArrayList<String>();
	
    /**
     * SPU标准库字段
	 * @author hardy<2015-07-07>
	 * @return
	 */
	private final static Map<String,String> SPU_FIELD = new  Hashtable<String, String>(){
		private static final long serialVersionUID = 1L;
		{
			/*
			 * SPU标准库
			 * */
			put("名称", "name");
			put("名称_EN", "name_en");
			put("通用名", "common_name");
			put("生产企业", "manufacture");
			put("生产企业_EN", "manufacture_en");
			put("生产国", "country");
			put("生产国_EN", "country_en");
			put("产地", "chinesedrugyieldly");
			put("产地_EN", "chinesedrugyieldly_en");
			put("批准文号", "approvalno");
			put("剂型", "proddosageformnotext");	//[数据字典.编码]
			put("规格", "prodspecification");
			put("处方分类", "prescriptionclasstext");//[数据字典.编码]
			put("业务类型", "busitypetext");//[数据字典.编码]
			put("批准文号有效期	", "approvalvalidtime");	
			put("有效期", "prodvalidtime");
			put("管理类别名称", "managementtypename");//[数据字典.编码]
			put("注册号", "registerno");
			put("注册证号", "respectivelicense");
			put("质量标准", "prodstandard");	
			put("适应症/功能", "indication");	
			put("储存条件", "storageconditiontext");	
			put("药品本位码", "drug_standard_code");
			put("配料/材质成分", "raw_material");
			
			/*
			 * SPU标准库-扩展
			 * */
			put("说明书", "specification");
			put("用法用量", "usageanddosage");
			put("注意事项", "notices");
			put("备注", "remark");
			
			/*
			 * SPU标准库特有属性
			 * */
			put("PUB_卫生许可证号", "pub_hygiene_license");
			put("PUB_适宜人群", "pub_suitable_people");
			put("PUB_不适宜人群", "pub_unsuitable_people");
			put("PUB_功效/标志性成份", "pub_functional_composition");
			//保健品
			put("BJP_保健功能", "bjp_health_function");
			put("BJP_营养成分", "bjp_health_composition");			
			
			/*
			 * SPU[基础]商品库
			 * */
		}
	};

	/**
	 * 获取对应类型商品标准库
	 * @author Andy
	 * @rewrite hardy<2015-07-07> 新增及调整部分字段，MAP值获取重构
	 * @param tableId
	 * @return
	 */
	public static Map<String,String> getFiledPrdNormal(String tableId){
		Map<String,String> map = new Hashtable<String, String>();
		//国产保健食品
		if(tableId.equals("30")){
			map.put("产品名称", SPU_FIELD.get("名称"));
			map.put("申请人中文名称", SPU_FIELD.get("生产企业"));
			map.put("申请人地址", SPU_FIELD.get("产地"));
			map.put("保健功能", SPU_FIELD.get("BJP_保健功能"));
			map.put("主要原料", SPU_FIELD.get("配料/材质成分"));
			map.put("适宜人群", SPU_FIELD.get("PUB_适宜人群"));
			map.put("不适宜人群", SPU_FIELD.get("PUB_不适宜人群"));
			map.put("食用方法及食用量", SPU_FIELD.get("用法用量"));
			map.put("产品规格", SPU_FIELD.get("规格"));
			map.put("贮藏方法", SPU_FIELD.get("储存条件"));
			map.put("注意事项", SPU_FIELD.get("注意事项"));
			map.put("备注", SPU_FIELD.get("备注"));
			map.put("批准文号", SPU_FIELD.get("批准文号"));
			map.put("有效期至", SPU_FIELD.get("有效期"));
			map.put("产品技术要求", SPU_FIELD.get("质量标准"));
		}
		//进口保健食品
		else if(tableId.equals("31")){
			map.put("有效期至", SPU_FIELD.get("有效期"));
			map.put("产品中文名称", SPU_FIELD.get("名称"));
			map.put("生产企业中文名称", SPU_FIELD.get("生产企业"));
			map.put("生产企业英文名称", SPU_FIELD.get("生产企业_EN"));
			map.put("生产国（地区）", SPU_FIELD.get("生产国"));
			map.put("地址", SPU_FIELD.get("产地"));
			map.put("保健功能", SPU_FIELD.get("BJP_保健功能"));
			map.put("主要原料", SPU_FIELD.get("配料/材质成分"));
			map.put("适宜人群", SPU_FIELD.get("PUB_适宜人群"));
			map.put("不适宜人群", SPU_FIELD.get("PUB_不适宜人群"));
			map.put("产品规格", SPU_FIELD.get("规格"));
			map.put("贮藏方法", SPU_FIELD.get("储存条件"));
			map.put("注意事项", SPU_FIELD.get("注意事项"));
			map.put("备注", SPU_FIELD.get("备注"));
			map.put("产品英文名称", SPU_FIELD.get("名称_EN"));
			map.put("批准文号", SPU_FIELD.get("批准文号"));
			map.put("食用方法及食用量", SPU_FIELD.get("用法用量"));
			map.put("产品技术要求", SPU_FIELD.get("质量标准"));
		}
		//国产化妆品
		else if(tableId.equals("68")){
			map.put("产品名称", SPU_FIELD.get("名称"));
			map.put("产品类别", SPU_FIELD.get("业务类型"));
			map.put("生产企业", SPU_FIELD.get("生产企业"));
			map.put("生产企业地址", SPU_FIELD.get("产地"));
			map.put("批准文号", SPU_FIELD.get("批准文号"));
			map.put("卫产许可证号", SPU_FIELD.get("PUB_卫生许可证号"));
			map.put("备注", SPU_FIELD.get("备注"));
		}
		//进口化妆品
		else if(tableId.equals("69")){
			map.put("产品名称（中文）", SPU_FIELD.get("名称"));
			map.put("产品名称（英文）", SPU_FIELD.get("名称_EN"));
			map.put("产品类别", SPU_FIELD.get("业务类型"));
			map.put("生产国（地区）", SPU_FIELD.get("生产国"));
			map.put("生产企业（中文）", SPU_FIELD.get("生产企业"));
			map.put("生产企业（英文）", SPU_FIELD.get("生产企业_EN"));
			map.put("生产企业地址", SPU_FIELD.get("产地_EN"));
			map.put("在华责任单位地址", SPU_FIELD.get("产地"));
			map.put("批准文号", SPU_FIELD.get("批准文号"));
			map.put("备注", SPU_FIELD.get("备注"));
		}
		//国产药品
		else if(tableId.equals("25")){
			map.put("批准文号", SPU_FIELD.get("批准文号"));
			map.put("产品名称", SPU_FIELD.get("名称"));
			map.put("英文名称", SPU_FIELD.get("名称_EN"));
			map.put("剂型", SPU_FIELD.get("剂型"));
			map.put("规格", SPU_FIELD.get("规格"));
			map.put("生产单位", SPU_FIELD.get("生产企业"));
			map.put("生产地址", SPU_FIELD.get("产地"));
			map.put("产品类别", SPU_FIELD.get("业务类型"));
			map.put("药品本位码", SPU_FIELD.get("药品本位码"));
		}
		//进口药品
		else if(tableId.equals("36")){
			map.put("注册证号", SPU_FIELD.get("注册证号"));
			map.put("分包装批准文号", SPU_FIELD.get("批准文号"));	
			map.put("产品名称（中文）", SPU_FIELD.get("名称"));
			map.put("产品名称（英文）", SPU_FIELD.get("名称_EN"));
			map.put("商品名（中文）", SPU_FIELD.get("通用名"));
			map.put("剂型（中文）", SPU_FIELD.get("剂型"));
			map.put("规格（中文）", SPU_FIELD.get("规格"));
			map.put("生产厂商（中文）", SPU_FIELD.get("生产企业"));
			map.put("生产厂商（英文）", SPU_FIELD.get("生产企业_EN"));
			map.put("厂商地址（中文）", SPU_FIELD.get("产地"));
			map.put("厂商地址（英文）", SPU_FIELD.get("产地_EN"));
			map.put("厂商国家/地区（中文）", SPU_FIELD.get("生产国"));
			map.put("厂商国家/地区（英文）", SPU_FIELD.get("生产国_EN"));
			map.put("有效期截止日", SPU_FIELD.get("有效期"));
			map.put("产品类别", SPU_FIELD.get("业务类型"));
			map.put("药品本位码", SPU_FIELD.get("药品本位码"));
		}
		//国产器械
		else if(tableId.equals("26")){
			map.put("注册号", SPU_FIELD.get("注册号"));
			map.put("产品名称", SPU_FIELD.get("名称"));
			map.put("生产单位", SPU_FIELD.get("生产企业"));
			map.put("产品性能结构及组成", SPU_FIELD.get("说明书"));
			map.put("产品使用范围", SPU_FIELD.get("适应症/功能"));
			map.put("规格型号", SPU_FIELD.get("规格"));
			map.put("有效期", SPU_FIELD.get("有效期"));
			map.put("生产场所", SPU_FIELD.get("产地"));
			map.put("备注", SPU_FIELD.get("备注"));
		}
		//进口器械
		else if(tableId.equals("27")){
			map.put("注册号", SPU_FIELD.get("注册号"));
			map.put("生产厂商名称（中文）", SPU_FIELD.get("生产企业"));
			map.put("生产厂商名称（英文）", SPU_FIELD.get("生产企业_EN"));
			map.put("生产厂地址（中文）", SPU_FIELD.get("产地"));
			map.put("生产场所", SPU_FIELD.get("产地_EN"));
			map.put("生产国或地区（中文）", SPU_FIELD.get("生产国"));
			map.put("产品名称（中文）", SPU_FIELD.get("名称"));
			map.put("产品名称（英文）", SPU_FIELD.get("名称_EN"));
			map.put("规格型号", SPU_FIELD.get("规格"));
			map.put("产品性能结构及组成", SPU_FIELD.get("说明书"));
			map.put("产品适用范围", SPU_FIELD.get("适应症/功能"));
			map.put("有效期截至日", SPU_FIELD.get("有效期"));
			map.put("备注", SPU_FIELD.get("备注"));
		}
		
		return map;
	}

	
	/**
	 * 剂型 编码映射
	 * @author Andy
	 * @return
	 */
	public static final Map<String,String> FORMULATION_CODE = new Hashtable<String, String>(){
		private static final long serialVersionUID = 1L;
		{
			put("其他", "jx000");
			put("薄膜衣片", "jx001");
			put("保健食品", "jx002");
			put("搽剂", "jx003");
			put("茶剂", "jx004");
			put("滴鼻剂", "jx005");
			put("滴眼剂", "jx006");
			put("酊剂", "jx007");
			put("锭剂", "jx008");
			put("粉针剂", "jx009");
			put("非药对应剂型", "jx010");
			put("干混悬剂", "jx011");
			put("合剂", "jx012");
			put("混悬液", "jx013");
			put("计生", "jx014");
			put("煎膏剂", "jx015");
			put("胶剂", "jx016");
			put("胶囊剂", "jx017");
			put("胶丸剂", "jx018");
			put("酒剂", "jx019");
			put("颗粒剂", "jx020");
			put("口服溶液剂", "jx021");
			put("口服乳剂", "jx022");
			put("露剂", "jx023");
			put("膜剂", "jx024");
			put("凝胶剂", "jx025");
			put("片剂", "jx026");
			put("气雾剂", "jx027");
			put("器械", "jx028");
			put("溶液剂", "jx029");
			put("乳膏剂", "jx030");
			put("软膏剂", "jx031");
			put("散剂", "jx032");
			put("食品", "jx033");
			put("栓剂", "jx034");
			put("糖浆剂", "jx035");
			put("糖衣片", "jx036");
			put("贴膏剂", "jx037");
			put("贴剂", "jx038");
			put("丸剂", "jx039");
			put("吸入剂", "jx040");
			put("洗剂", "jx041");
			put("橡胶膏剂", "jx042");
			put("消字产品", "jx043");
			put("眼膏剂", "jx044");
			put("药材", "jx045");
			put("饮片", "jx046");
			put("原料药", "jx047");
			put("注射剂", "jx048");	
		}
	};

	
	/**
	 * 处方类型 编码映射
	 * @author Andy
	 * @return
	 */
	public static final Map<String,String> PRESCRIPTION_CODE = new Hashtable<String, String>(){
		private static final long serialVersionUID = 1L;
		{
			put("其他", "cf000");
			put("处方药", "cf001");
			put("甲类", "cf002");
			put("乙类", "cf003");
			put("一类器械", "cf004");
			put("二类器械", "cf005");
			put("三类器械", "cf006");
		}
	};
	
	
	/**
	 * 业务类型 编码映射
	 * @author Andy
	 * @return
	 */
	
	public static final Map<String,String> BUSINESS_CODE = new Hashtable<String, String>(){
		private static final long serialVersionUID = 1L;
		{
			put("其它", "yw000");
			put("中药", "yw001");
			put("西药", "yw002");
			put("计生", "yw003");
			put("器械", "yw004");
			put("原料药", "yw005");
		}
	};

	
	/**
	 * 管理类别 编码映射
	 * @author Andy
	 * @return
	 */
	public static final Map<String,String> MANAGE_CODE = new Hashtable<String, String>(){
		private static final long serialVersionUID = 1L;
		{
			put("其它", "gl000");
			put("电子监管", "gl001");
			put("普通药品", "gl002");
			put("特管药", "gl003");
		}
	};

	
	/**
	 * 存储条件  编码映射
	 * @author Andy
	 * @return
	 */
	public static final Map<String,String> STORE_CODE = new Hashtable<String, String>(){
		private static final long serialVersionUID = 1L;
		{
			put("其它", "cc000");
			put("常温", "cc001");
			put("阴凉", "cc002");
			put("冷藏", "cc003");
		}
	};
}
