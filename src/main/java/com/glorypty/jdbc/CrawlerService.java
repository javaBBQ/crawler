/*
 *Project: glorypty-crawler
 *File: com.glorypty.jdbc.CrawlerService.java <2015年5月15日>
 ****************************************************************
 * 版权所有@2015 国裕网络科技  保留所有权利.
 ***************************************************************/
package com.glorypty.jdbc;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.glorypty.crawler.common.ConstantsCrawler.AllianceTypeEnum;
import com.glorypty.crawler.utils.Utils;

/**
 *
 * @Author hardy
 * @Date 2015年5月15日 下午4:53:50
 * @version 1.0
 */
public class CrawlerService {

	static final Logger logger = Logger.getLogger(CrawlerService.class);

	/**
	 * 内容
	 * 
	 * @Author hardy<2015年5月21日>
	 * @param groupId
	 *            栏目ID
	 * @param title
	 *            标题
	 * @param origin
	 *            来源
	 * @param originurl
	 *            来源URL
	 * @param desc
	 *            简述/摘要/关键字
	 * @param txt
	 *            详情
	 */
	public static void executeContent(Integer groupId, String title,
			String origin, String originurl, String desc, String txt) {
		if (groupId == null || title == null || title.equals(""))
			return;
		logger.info(MessageFormat.format("录入数据》内容({0})=>{1}", groupId, title));
		execute("CALL crawler_insert_proc(\"content\", {0}, \"{1}\", \"{2}\", \"{3}\", \"{4}\", \"{5}\")",
				groupId,
				title.length() > 145 ? title.substring(0, 145) : title, origin,
				originurl, desc.length() > 245 ? desc.substring(0, 245) : desc,
				txt);
	}

	/**
	 * 联盟
	 * 
	 * @Author hardy<2015年5月21日>
	 * @param allianceType
	 *            招商/代理
	 * @param title
	 *            标题
	 * @param txt
	 *            详情
	 */
	public static void executeAlliance(AllianceTypeEnum allianceType,
			String title, String txt) {
		if (title == null || title.equals(""))
			return;
		logger.info(MessageFormat.format("录入数据》联盟({0})=>{1}",
				allianceType.name(), title));
		execute("CALL crawler_insert_proc(\"alliance{0}\", 0, \"{1}\", \"\", \"\", \"\", \"{2}\")",
				allianceType.ordinal(),
				title.length() > 145 ? title.substring(0, 145) : title, txt);
	}

	/**
	 * 资料库
	 * 
	 * @Author hardy<2015年5月21日>
	 * @param groupId
	 *            资料库分组ID
	 * @param title
	 *            名称
	 * @param txt
	 *            详情
	 */
	public static void executeDataBank(Integer groupId, String title, String txt) {
		if (groupId == null || title == null || title.equals(""))
			return;
		logger.info(MessageFormat.format("录入数据》资料库({0})=>{1}", groupId, title));
		execute("CALL crawler_insert_proc(\"databank\", {0}, \"{1}\", \"国家食品药品监督管理局\", \"\", \"\", \"{2}\")",
				groupId,
				title.length() > 145 ? title.substring(0, 145) : title, txt);
	}

	/**
	 * 资料库-药房
	 * @param province 省份
	 * @param type     类型
	 * @param name     名称
	 * @param logo_url  logo地址
	 * @param address    地址
	 * @param business_hours   营业时间
	 * @param business_license  营业执照
	 * @param gsp_code     GSP证号
	 * @param business_certificate  营业许可证
	 * @param business_scope   营业范围
	 */
	public static void addDataBankPharmacy(String province,String type,String name,String logo_url,String address,
			String business_hours,String business_license,String gsp_code,String business_certificate,String business_scope){
		if(province.equals("")||name.equals(""))
			return ;
		logger.info(MessageFormat.format("录入数据》资料库-药房({0})=>{1}", province, name));
		add("insert into cms_databank_pharmacy(province,type,name,logo_url,address,business_hours,business_license,"
			+ "gsp_code,business_certificate,business_scope) values(\"{0}\",\"{1}\",\"{2}\",\"{3}\",\"{4}\",\"{5}\","
			+ "\"{6}\",\"{7}\",\"{8}\",\"{9}\")",
			province,
			type,
			name,
			logo_url,
			address,
			business_hours,
			business_license,
			gsp_code,
			business_certificate,
			business_scope);
	}
	
	/**
	 * 资料库-药企
	 * @param province   省份
	 * @param logo_url   logo地址
	 * @param name   药企名称
	 * @param type   类型（厂商，批发商）
	 * @param business_license   营业执照
	 * @param production_certificate   生产许可证
	 * @param production_scope   生产范围
	 * @param production_address   生产地址
	 * @param phone   电话
	 * @param fax   传真
	 * @param mail   邮件
	 * @param company_address   公司地址
	 * @param company_url   公司网址
	 * @param contacts   联系人
	 * @param txt   描述信息
	 */
	public static void executeDataBankEnterprise(String province,String logo_url,String name,String type,
			String business_license,String production_certificate,String production_scope,String production_address,
			String phone,String fax,String mail,String company_address,String company_url,String contacts,String txt){
		if(province.equals("")||name.equals(""))
			return ;
		logger.info(MessageFormat.format("录入数据》资料库-药企({0})=>{1}", province, name));
		execute("CALL cms_databank_enterprise_proc(\"{0}\", \"{1}\", \"{2}\", \"{3}\", \"{4}\", \"{5}\", "
				+ "\"{6}\", \"{7}\", \"{8}\", \"{9}\", \"{10}\", \"{11}\", \"{12}\", \"{13}\", \"{14}\")",
				province,
				logo_url,
				name,
				type,
				business_license,
				production_certificate,
				production_scope,
				production_address,
				phone,
				fax,
				mail,
				company_address,
				company_url,
				contacts,
				txt);
	}

	/**
	 * 资料库-医院
	 * @param province
	 * @param province   省份
	 * @param logo_url  logo地址
	 * @param name  医院名称
	 * @param hospital_nature   类型（公立，私立）
	 * @param hospital_grade   医院等级
	 * @param phone   电话
	 * @param mail   邮件
	 * @param postcode   邮编
	 * @param address  地址
	 * @param special_subject  特殊专科
	 * @param daily_visits  日门诊量
	 * @param bed_num  床位量
	 * @param equipment  主要设备
	 * @param hospital_url  医院网址
	 * @param traffic  交通指南
	 * @param txt  描述信息
	 */
	public static void executeDataBankHospital(String province,String logo_url,String name,String hospital_nature,String hospital_grade,
			String phone,String mail,String postcode,String address,String special_subject,String daily_visits,String bed_num,
			String equipment,String hospital_url,String traffic,String txt){
		if(province.equals("")||name.equals(""))
			return ;
		logger.info(MessageFormat.format("录入数据》资料库-医院({0})=>{1}", province, name));
		 final String sql = "{call cms_databank_hospital_proc(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";   
         Object[] params = new Object[] {province, logo_url, name, hospital_nature, hospital_grade, phone, mail, 
					postcode, address,special_subject, Integer.parseInt(daily_visits), Integer.parseInt(bed_num), 
					equipment, hospital_url,"", txt};
        insert(sql, params);
	}


	/**
	 * 自动组装插入表
	 * @author Andy
	 * @param tableName  表面
	 * @param result  值
	 * @param platform 平台
	 */
	public static void executeBasedata(String tableName,Map<String, String> result,String platform) {
		if (StringUtils.isEmpty(tableName) || null == result
				|| result.size() == 0) {
			return;
		}
		logger.info(MessageFormat.format("录入数据》数据库{0}-表({1})", platform, tableName));
		/* 组装sql 执行 */
		StringBuffer sql = new StringBuffer();
		StringBuffer field = new StringBuffer();
		StringBuffer value = new StringBuffer("\"");
		sql.append("insert into " + tableName + " (");
		Iterator it = result.keySet().iterator();
		while (it.hasNext()) {
			String key;
			String values;
			key = it.next().toString();
			values = result.get(key);
			field.append(key + ",");
			
			if (values.contains("\\")) {
				values=replaceChars(values,"\\", "\\\\");
			}
			if (values.contains("\"")) {
				values=replaceChars(values,"\"", "\\\"");
			}
			
			value.append(values+"\",\"");
		}
		sql.append(field.substring(0, field.length()-1)+")");
		sql.append(" VALUES (");
		sql.append(value.substring(0, value.length()-2)+")");
//		System.out.println(sql);
		if(platform!=null && platform.equals("cms")){
			execute(sql.toString());
		}else if(platform.equals("cmsgoods")){
			executeToCmsGoods(sql.toString());
		}else{
			executeToMall(sql.toString());
		}
	}
	
	/**
	 * 药监局-SPU标准库
	 * @author Andy
	 * @param name  
	 * @param name_en
	 * @param common_name
	 * @param manufacture
	 * @param manufacture_en
	 * @param chinesedrugyieldly
	 * @param approvalno
	 * @param proddosageformnotext
	 * @param prodspecification
	 * @param prescriptionclasstext
	 * @param busitypetext
	 * @param approvalvalidtime
	 * @param prodvalidtime
	 * @param managementtypename
	 * @param registerno
	 * @param respectivelicense
	 * @param prodstandard
	 * @param indication
	 * @param storageconditiontext
	 * @param drug_standard_code
	 * @param usageanddosage
	 * @param remark
	 * @param groupName
	 */
	public static void executeMallBaseData(String name,					
			String name_en,					
			String common_name,				
			String manufacture,				
			String manufacture_en,			
			String chinesedrugyieldly,		
			String approvalno,				
			String proddosageformnotext,	
			String prodspecification,		
			String prescriptionclasstext,	
			String busitypetext,			
			String approvalvalidtime,		
			String prodvalidtime,			
			String managementtypename,		
			String registerno,			
			String respectivelicense,		
			String prodstandard,			
			String indication,				
			String storageconditiontext,	
			String drug_standard_code,
			String country, String country_en, String chinesedrugyieldly_en, String raw_material,
			/*扩展*/
			String specification, String usageanddosage,
			String notices, String remark,
			/*特有*/
			String pub_hygiene_license, String pub_suitable_people, String pub_unsuitable_people,String pub_functional_composition,
			String bjp_health_function,String bjp_health_composition,		
			String groupName) {
		logger.info(MessageFormat.format("录入数据》商品标准库({0})=>{1}",groupName,name));
		executeToMall("CALL crawler_mall_spu_proc(\"{0}\", \"{1}\", \"{2}\", \"{3}\", \"{4}\", \"{5}\", \"{6}\", \"{7}\", \"{8}\", \"{9}\", \"{10}\",\"{11}\", \"{12}\", \"{13}\", \"{14}\", \"{15}\", \"{16}\", \"{17}\", \"{18}\",\"{19}\","
				+ "\"{20}\", \"{21}\", \"{22}\",\"{23}\","
				/*扩展*/+ "\"{24}\", \"{25}\", \"{26}\",\"{27}\", "
				/*特有*/+ "\"{28}\", \"{29}\", \"{30}\", \"{31}\", "
					+ "\"{32}\", \"{33}\")",
				name,					
				name_en,					
				common_name,				
				manufacture,				
				manufacture_en,			
				chinesedrugyieldly,		
				approvalno,				
				proddosageformnotext,	
				Utils.substring(prodspecification,495),		
				prescriptionclasstext,	
				busitypetext,			
				approvalvalidtime,		
				prodvalidtime,			
				managementtypename,		
				registerno,				
				respectivelicense,		
				Utils.substring(prodstandard,95),			
				Utils.substring(indication,495),				
				storageconditiontext,	
				drug_standard_code,	
				country, country_en, chinesedrugyieldly_en, Utils.substring(raw_material,195),
				/*扩展*/specification,usageanddosage, notices, remark, 
				/*特有*/Utils.substring(pub_hygiene_license,95), Utils.substring(pub_suitable_people,95), Utils.substring(pub_unsuitable_people,95), Utils.substring(pub_functional_composition,95),
				Utils.substring(bjp_health_function,95),Utils.substring(bjp_health_composition,95));
	}
	
	/**
	 * 好药师-商品图片信息
	 * @author Andy
	 */
	public static void executeMallProImageData(String path,Integer id){
		logger.info(MessageFormat.format("录入数据》商品图片-商品=>{0}",id));
		insertToMall("insert into cj_ehaoyao_pic (pic_add,pid) VALUES('"+path+"',"+id+")");
	}
	
	
	/**
	 * 资讯平台查询
	 * @author Andy
	 * @param sql
	 * @return
	 */
	public static Map<String,Object> queryMallWithMap(String sql){
		return queryMallForMap(sql);
	}
	
	
	/**
	 * 商讯-数据写入DB
	 * @author ZhangLujun<2015年12月22日>
	 * @param type
	 * @param area_id
	 * @param title
	 * @param price
	 * @param num
	 * @param spec
	 * @param coname
	 * @param linkman
	 * @param telephone
	 * @param mobile
	 * @param email
	 * @param qq
	 * @param main_picture
	 * @param description
	 * @param add_desc
	 */
	public static void executeDataBankCmsGoods(String type,String area_id,String title,String price,String num,String spec,
			String coname,String linkman,String telephone,String mobile,String email,String qq,String main_picture,String description,String add_desc){
		final String sql = "{call cms_add_data_proc(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";   
        Object[] params = new Object[] {type,area_id,title,price,num,spec,coname,linkman,
 				telephone,mobile,email,qq,main_picture,description,add_desc};
        boolean result = ApplicationContextCmsGoodsHelper.insert(sql, params);
 		logger.info(MessageFormat.format("录入数据》商讯=>{0},{1},{2},{3}","分类 "+type,title,coname,result));

	}
	
	
	/**
	 * 根据参数查询结果条数
	 * @author ZhangLujun<2016年2月22日>
	 * @param type
	 * @param linkman
	 * @return
	 */
	public static int queryCmsGoods(String type,String linkman,String mobile){
		String sql = "select count(0) from CMSG_INFORMATION_TMP where linkman like'%"+linkman+"%' and mobile='"+mobile+"'";
		return ApplicationContextCmsGoodsHelper.query(sql);
	}
	
	
	/**
	 * 交易平台查询
	 * @author Andy
	 * @param sql
	 * @return
	 */
	private static Map<String,Object> queryMallForMap(String sql){
		return ApplicationContextMallHelper.queryWihtMap(sql);
	}
	
	/**
	 * 添加至资讯平台
	 * @Author hardy<2015年5月19日>
	 * @param sql
	 * @param args
	 */
	private static void execute(String sql, Object... args) {
		ApplicationContextHelper.execute(MessageFormat.format(sql, args));
	}
	
	
	/**
	 * 添加至资讯平台
	 * @param sql
	 * @param args
	 */
	private static void add(String sql, Object... args){
		ApplicationContextHelper.insert(MessageFormat.format(sql, args));
	}
	
	
	/**
	 * 添加至资讯平台
	 * @param sql
	 * @param args
	 */
	private static void insert(String sql,Object...args){
		ApplicationContextHelper.insert(sql, args);
	}
	
	
	/**
	 * 添加至交易平台
	 * @author Andy
	 * @param sql
	 * @param args
	 */
	private static void executeToMall(String sql, Object... args) {
		ApplicationContextMallHelper.execute(MessageFormat.format(sql, args));
	}
	
	/**
	 * 添加至交易平台
	 * @author Andy
	 * @param sql
	 * @param args
	 */
	private static void addToMall(String sql, Object... args){
		ApplicationContextMallHelper.insert(MessageFormat.format(sql, args));
	}
	
	
	/**
	 * 添加至交易平台
	 * @author Andy
	 * @param sql
	 * @param args
	 */
	private static void insertToMall(String sql,Object...args){
		ApplicationContextMallHelper.insert(sql, args);
	}
	
	/**
	 * 商讯新增数据操作
	 * @author ZhangLujun<2015年12月22日>
	 * @param sql
	 * @param args
	 */
	private static void executeToCmsGoods(String sql,Object...args){
		ApplicationContextCmsGoodsHelper.execute(MessageFormat.format(sql, args));
	}
	
	
	public static String replaceChars(String sString,String sOld,String sNew) {
		String newString = "";
		try {
			for (int i = 0; i < sString.length(); i ++) {
				if (sString.substring(i, i + sOld.length()).equals(sOld)) {
					sString = sString.substring(0, i) + sNew + sString.substring(i + sOld.length(), sString.length());
					i = i + sNew.length();
				}
			}
			newString = sString;
			return newString;
		} catch(Exception e) {
//	            System.out.println(e.toString());
			return newString;
		}
	}
	
	
	public static void main(String[] args) {
		Map<String, String> result = new HashMap<String, String>();
		String creat_date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());//2015-05-27 09:26:22
		System.out.println(creat_date);
		result.put("perm_no", "国药准字Z11020907");
		result.put("prod_name_cn", "黛蛤\"\\散");
		result.put("prod_name_en", "helloworld");
		result.put("create_date", creat_date);
//		insert into base_drug_cn (prod_name_cn,perm_no,prod_name_en) VALUES ('黛蛤散','国药准字Z11020907','helloworld')
//		CrawlerService.executeBasedata("base_drug_cn", result,"cms");
	}
}
