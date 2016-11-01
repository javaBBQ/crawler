package com.glorypty.crawler.ehaoyao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * ehaoyao网药品分类抓取
 * @author liuj
 *
 */
public class EHaoYaoConstants {
	
	
	/**ehaoyao主页*/
	public static final String URL_HOME = "http://www.ehaoyao.com";
	
	/**ehaoyao爬虫记录存放目录*/
	public static final String RECORD_DIR = "ehaoyao/";
	
	/**ehaoyao商品列表页每个显示45条商品*/
	public static final Integer PAGE_SIZE = 45;
	
	/**ehaoyao网站各类商品详情的参数属性MAP*/
	public static Map<String,String> deltailParam = new HashMap<String, String>();
	
	
	public static final int WIDTH = 469;
	public static final int HEIGHT = 469;
	
	/**这个是程序运行时的存储路径**/
	public static String OUT_FILE = "F:/cj/";
	
	/**这个是存在数据库的图片路径前缀 后面还要加文件名xx.jpg**/
	public static final String STORE_PATH_PRE = "http://fs.gr158.com/photo/cj/";
	
	/**图片命名后缀**/
	public static final String PIC_SUF = ".jpg";
	
	/**列表页集合*/
	public static List<String> list_page = new ArrayList<String>();
	
	/**详细页集合*/
	public static List<String> detail_page = new ArrayList<String>();
	
	static{
		deltailParam.put("批准文号", "perm_no");
		deltailParam.put("检验合格情况", "qualified");
		deltailParam.put("生产厂家", "production_fac");
		deltailParam.put("执行标准", "standard");
		deltailParam.put("通用名称", "common_name");
		deltailParam.put("生产地址", "production_add");
		deltailParam.put("剂型", "preparation");
		deltailParam.put("存储条件", "stor");
		deltailParam.put("商品规格", "spec");
		deltailParam.put("认证情况", "certification");
		deltailParam.put("有效期", "useful_date");
	}
	
	
}
