/*
 *Project: glorypty-crawler
 *File: com.glorypty.crawler.common.TempDB.java 
 ****************************************************************
 * 版权所有@2015 国裕秀网络科技  保留所有权利.
 ***************************************************************/
package com.glorypty.crawler.sfdanew;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.select.Elements;

/**
 * 临时数据库
 * 
 * @Author yiwen
 * @Date 2015年6月11日 下午10:08:58
 */
public class TempDB {

	/** 数据分类(抓取部分)：药监局表id=对应国瑞表 */
	@SuppressWarnings("serial")
	public static final Map<String, String> DATA_TABLE = new HashMap<String, String>() {
		{
			/* 保健食品-2 */
			put("30", "base_health_food_cn"); // 国产保健食品(14795)
			put("31", "base_health_food_en"); // 进口保健食品(735)
			/* 药品-21 */
			put("25", "base_drug_cn"); // 国产药品(169226)
			put("6", "base_perm_drug_pack"); // 批准的药包材(5365)
			put("79", "base_drug_produce_comp_net"); // 基本药物生产企业入网目录(3919)
			put("89", "base_food_additive_perm_comp"); // 食品添加剂生产许可获证企业
			put("91", "base_food_perm_comp"); // 食品添加剂生产许可获证企业
			put("36", "base_drug_en"); // 进口药品(4707)
			put("74", "base_national_base_drug"); // 国家基本药物（2012年版）(520)
			put("34", "base_drug_produce_comp"); // 药品生产企业(7159)
			put("41", "base_drug_run_comp"); // 药品经营企业(141809)
			put("24", "base_gsp"); // GSP认证(143322)
			put("102", "base_drug_dire"); // 麻醉药品和精神药品品种目录(270)
			/* 化妆品-5 */
			put("68", "base_makeup_cn"); // 国产化妆品(23304)
			put("93", "base_makup_comp"); // 化妆品生产许可获证企业(3880)
			put("69", "base_makeup_en"); // 进口化妆品(115001)
			/* 医疗器械-9 */
			put("26", "base_instrument_cn"); // 国产器械(103306)
			put("27", "base_instrument_en"); // 进口器械(36915)
			put("20", "base_instrument_categroy_dire"); // 医疗器械分类目录(306)
			put("21", "base_instrument_stand_dire"); // 医疗器械标准目录(700)
			put("105", "base_ivd"); // 体外诊断试剂分类子目录（2013版）(766)
			// ********************
			// put("108", "化妆品行政许可检验机构");

		}
	};

	/** 数据分类(抓取部分)：药监局分类名=药监局分类ID */
	@SuppressWarnings("serial")
	public static final Map<String, String> DATA_GROUP_NAME = new HashMap<String, String>() {
		{
			/* 保健食品-2 */
			put("国产保健食品", "30");
			put("进口保健食品", "31");
			/* 药品-21 */
			put("国产药品", "25");
			put("国产药品商品名", "32");
			put("药物临床试验机构名单", "19");
			put("进口药品商品名", "60");
			put("批准的药包材", "6");
			put("中药保护品种", "22");
			put("基本药物生产企业入网目录", "79");

			put("药品注册补充申请备案情况公示", "63");
			put("药品注册相关专利信息公开公示", "65");
			put("进口药品", "36");
			put("GMP认证", "23");
			put("药品注册批件发送信息", "16");
			put("OTC化学药品说明书范本", "1");
			put("进口药品电子监管工作代理机构", "84");

			put("国家基本药物（2012年版）", "74");
			put("申请人申报受理情况", "5");
			put("药品生产企业", "34");
			put("药品经营企业", "41");
			put("GSP认证", "24");
			put("OTC中药说明书范本", "2");
			put("麻醉药品和精神药品品种目录", "102");
			/* 化妆品-5 */
			put("国产化妆品", "68");
			put("化妆品生产许可获证企业", "93");
			put("进口化妆品", "69");
			put("化妆品行政许可检验机构", "108");
			put("国产非特殊用途化妆品备案检验机构", "83");
			/* 医疗器械-9 */
			put("国产器械", "26");
			put("进口器械", "27");
			put("医疗器械检测中心受检目录", "18");
			put("国产器械（历史数据）", "94");
			put("进口器械（历史数据）", "95");
			put("医疗器械分类目录", "20");
			put("医疗器械标准目录", "21");
			put("体外诊断试剂分类子目录（2013版）", "105");
			put("第一类医疗器械（含第一类体外诊断试剂）备案信息", "104");
		}
	};

	/**
	 * 获取组装标题(根据药监局表ID)
	 * 
	 * @Author hardy<2015年5月21日>
	 * @Rewrite yiwen<2015年6月11日>
	 * @param tableId
	 * @param nodes
	 * @return
	 */
	public static Map<String, String> getFieldByTable(String tableId,
			Elements nodes) {
		// 按分类组装Title
		String title = null;
		String prodType ="";
		String creator ="crawler";
		String create_date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());//2015-05-27 09:26:22
		Map<String, String> result = new HashMap<String, String>();
		switch (tableId) {
		/* 保健食品 */
		// 国产保健食品base_health_food_cn
		case "30":
			// div table[align=center] select的 tr:not(tr[^bgc] -->select td的序列
			// $(this).find("div table[align=center]").find("tr:not(tr[^bgc]").find("td")
			title = MessageFormat.format("{0}({1} {2})", nodes.get(1).text(),
					nodes.get(5).text(), nodes.get(57).text());
			result.put("prod_name", nodes.get(1).text()); // 产品名称
			result.put("transferee_add", nodes.get(3).text());// 受让方地址
			result.put("apply_name", nodes.get(5).text());// 申请人中文名称
			result.put("apply_add", nodes.get(7).text());// 申请人地址
			result.put("health_fun", nodes.get(9).text());// 保健功能
			result.put("content", nodes.get(11).text());// 功效成分/标志性成分含量
			result.put("main_content", nodes.get(13).text());// 主要原料
			result.put("crowds", nodes.get(15).text());// 适宜人群
			result.put("uncrowds", nodes.get(17).text());// 不适宜人群
			result.put("eat_way", nodes.get(19).text());// 食用方法及食用量
			result.put("spec", nodes.get(21).text());// 产品规格
			result.put("useful_date", nodes.get(23).text());// 保质期
			result.put("store_way", nodes.get(25).text());// 贮藏方法
			result.put("attention", nodes.get(27).text());// 注意事项
			result.put("perm_date", nodes.get(29).text());// 批准日期
			result.put("update_date", nodes.get(31).text());// 批准变更日期
			result.put("update_content", nodes.get(33).text());// 变更内容
			result.put("recode_date", nodes.get(35).text());// 备案日期
			result.put("recode_content", nodes.get(37).text());// 备案内容
			result.put("update_content", nodes.get(39).text());// 转让方中文名称
			result.put("transferor_name_cn", nodes.get(41).text());// 转让方英文名称
			result.put("transferor_name_en", nodes.get(43).text());// 转让方地址
			result.put("transferor_add", nodes.get(45).text());// 受让方
			result.put("transferee_name", nodes.get(47).text());// 转让前批准文号
			result.put("old_perm_no", nodes.get(49).text());// 批准转让日期
			result.put("transferor_date", nodes.get(51).text());// 补发日期
			result.put("remark", nodes.get(53).text());// 备注
			result.put("prod_code", nodes.get(55).text());// 产品编号
			result.put("cancel_date", nodes.get(59).text());// 注销日期
			result.put("cancel_cause", nodes.get(61).text());// 注销原因
			result.put("usefaul_date", nodes.get(63).text());// 有效期至
			result.put("prod_tec_demand", nodes.get(65).text());// 产品技术要求
			result.put("creator", creator);// 创建人 crawler
			result.put("create_date", create_date);// 创建日期 当前日期
			break;
		// 进口保健食品 base_health_food_en
		case "31":
			title = MessageFormat.format("{0}({1})", nodes.get(5).text(), nodes
					.get(71).text());
			result.put("no", nodes.get(1).text()); // 产品编号
			result.put("usefaul_date", nodes.get(3).text());// 有效期至
			result.put("prod_name_cn", nodes.get(5).text());// 产品中文名称
			result.put("apply_name_en", nodes.get(7).text());// 申请人英文名称
			result.put("apply_add", nodes.get(9).text());// 申请人地址
			result.put("producer_name_cn", nodes.get(11).text());// 生产企业中文名称
			result.put("producer_name_en", nodes.get(13).text());// 生产企业英文名称
			result.put("areal", nodes.get(15).text());// 生产国（地区）
			result.put("addr", nodes.get(17).text());// 地址
			result.put("health_fun", nodes.get(19).text());// 保健功能
			result.put("content", nodes.get(21).text());// 功效成分/标志性成分含量
			result.put("main_content", nodes.get(23).text());// 主要原料
			result.put("crowds", nodes.get(25).text());// 适宜人群
			result.put("uncrowds", nodes.get(27).text());// 不适宜人群
			result.put("spec", nodes.get(29).text());// 产品规格
			result.put("useful_date", nodes.get(31).text());// 保质期
			result.put("store_way", nodes.get(33).text());// 贮藏方法
			result.put("attention", nodes.get(35).text());// 注意事项 对
			result.put("update_date", nodes.get(37).text());// 批准变更日期
			result.put("update_content", nodes.get(39).text());// 变更内容
			result.put("recode_date", nodes.get(41).text());// 备案日期
			result.put("transferor_name_cn", nodes.get(43).text());// 转让方中文名称
			result.put("transferor_name_en", nodes.get(45).text());// 转让方英文名称
			result.put("transferor_add", nodes.get(47).text());// 转让方地址
			result.put("transferee_name", nodes.get(49).text());// 受让方中文名称
			result.put("transferee_add", nodes.get(51).text());// 受让方地址
			result.put("old_perm_no", nodes.get(53).text());// 转让前批准文号
			result.put("transferor_date", nodes.get(55).text());// 批准转让日期
			result.put("add_date", nodes.get(57).text());// 补发日期
			result.put("remark", nodes.get(59).text());// 备注
			result.put("prod_name_en", nodes.get(61).text());// 产品英文名称
			result.put("perm_date", nodes.get(63).text());// 批准日期
			result.put("apply_name", nodes.get(65).text());// 申请人中文名称
			result.put("cancel_date", nodes.get(67).text());// 注销日期
			result.put("cancel_cause", nodes.get(69).text());// 注销原因
			result.put("perm_no", nodes.get(71).text());// 批准文号
			result.put("eat_way", nodes.get(73).text());// 食用方法及食用量
			result.put("recode_content", nodes.get(75).text());// 备案内容
			result.put("transferee_name_en", nodes.get(77).text());// 受让方英文名称
			result.put("prod_tec_demand", nodes.get(79).text());// 产品技术要求
			result.put("creator", creator);// 创建人 crawler
			result.put("create_date", create_date);// 创建日期 当前日期
			break;
		/* 药品 */
		// 国产药品 base_drug_cn
		case "25":
			if (nodes.get(0).text().contains("品种名称")) {
				title = MessageFormat.format("{0}({1} {2})", nodes.get(1)
						.text(), nodes.get(3).text(), nodes.get(5).text());
			} else if (nodes.get(0).text().contains("保护品种编号")) {
				title = MessageFormat.format("{0}({1} {2})", nodes.get(3)
						.text(), nodes.get(7).text(), nodes.get(17).text());
			} else {
				title = MessageFormat.format("{0}({1} {2})", nodes.get(3)
						.text(), nodes.get(1).text(), nodes.get(13).text());
			}

			result.put("perm_no", nodes.get(1).text()); // 批准文号
			result.put("prod_name_cn", nodes.get(3).text());// 产品名称
			result.put("prod_name_en", nodes.get(5).text());// 英文名称
			result.put("goods_name", nodes.get(7).text());// 商品名
			result.put("model", nodes.get(9).text());// 剂型
			result.put("spec", nodes.get(11).text());// 规格
			result.put("producer", nodes.get(13).text());// 生产单位
			result.put("production_add", nodes.get(15).text());// 生产地址
			prodType = nodes.get(17).text();
			switch (prodType) {
				case "化学药品":
					prodType="1";
					break;
				case "中药":
					prodType="2";
					break;
				case "生物制品":
					prodType="3";
					break;
				case "辅料":
					prodType="4";
					break;
		
				case "国产包材":
					prodType="5";
					break;
				case "中药保护":
					prodType="6";
					break;
				default:
					prodType="-1";
					break;
			}

			result.put("prod_type", prodType);// 产品类别(1:化学药品 2:中药 3:生物制药 4:辅料 5:国产包材 6:中药保护)
			result.put("old_perm_number", nodes.get(19).text());// 原批准文号
			result.put("perm_date", nodes.get(21).text());// 批准日期
			result.put("drug_standard_code", nodes.get(23).text());// 药品本位码
			result.put("drug_standard_code_remark", nodes.get(25).text());// 药品本位码备注
			result.put("creator", creator);// 创建人 crawler
			result.put("create_date", create_date);// 创建日期 当前日期
			break;
		// 批准的药包材base_perm_drug_pack
		case "6":
			title = MessageFormat.format("{0}({1} {2})", nodes.get(1).text(),
					nodes.get(3).text(), nodes.get(5).text());

			result.put("variety_name", nodes.get(1).text()); // 品种名称
			result.put("regist_no", nodes.get(3).text());// 注册证号
			result.put("company_name", nodes.get(5).text());// 企业名称
			result.put("prod_res", nodes.get(7).text());// 产品来源
			result.put("perm_date", nodes.get(9).text());// 批准日期
			result.put("spec", nodes.get(11).text());// 规格
			result.put("creator", creator);// 创建人 crawler
			result.put("create_date", create_date);// 创建日期 当前日期
			break;
		// 基本药物生产企业入网目录 base_drug_produce_comp_net
		case "79":
			title = nodes.get(5).text();
			result.put("city", nodes.get(1).text()); // 省市
			result.put("org_no", nodes.get(3).text());// 组织机构代码
			result.put("company_name", nodes.get(5).text());// 企业名称
			result.put("creator", creator);// 创建人 crawler
			result.put("create_date", create_date);// 创建日期 当前日期

			break;
		// 进口药品 base_drug_en 4744
		case "36":
			if (nodes.get(0).text().contains("品种名称")) {
				title = MessageFormat.format("{0}({1} {2})", nodes.get(1)
						.text(), nodes.get(3).text(), nodes.get(5).text());
			} else {
				title = MessageFormat.format("{0}({1})", nodes.get(21).text(),
						nodes.get(1).text());
			}
			result.put("regist_no", nodes.get(1).text()); // 注册证号
			result.put("old_regist_no", nodes.get(3).text());// 原注册证号
			result.put("regist_no_remark", nodes.get(5).text());// 注册证号备注
			result.put("perm_munber", nodes.get(7).text());// 分包装批准文号
			result.put("producer_name_cn", nodes.get(9).text());// 公司名称（中文）
			result.put("producer_name_en", nodes.get(11).text());// 公司名称（英文）
			result.put("add_cn", nodes.get(13).text());// 地址（中文）
			result.put("add_en", nodes.get(15).text());// 地址（英文）
			result.put("area_cn", nodes.get(17).text());// 国家/地区（中文）
			result.put("area_en", nodes.get(19).text());// 国家/地区（英文）
			result.put("prod_name_cn", nodes.get(21).text());// 产品名称（中文）
			result.put("prod_name_en", nodes.get(23).text());// 产品名称（英文）
			result.put("goods_name_cn", nodes.get(25).text());// 商品名（中文）
			result.put("goods_name_en", nodes.get(27).text());// 商品名（英文）
			result.put("model", nodes.get(29).text());// 剂型（中文）
			result.put("spec", nodes.get(31).text());// 规格（中文）
			result.put("packing_spec", nodes.get(33).text());// 包装规格（中文）
			result.put("producer_nm_cn", nodes.get(35).text());// 生产厂商（中文）
			result.put("producer_nm_en", nodes.get(37).text());// 生产厂商（英文）
			result.put("producer_add_cn", nodes.get(39).text());// 厂商地址（中文）
			result.put("producer_add_en", nodes.get(41).text());// 厂商地址（英文）
			result.put("producer_area_cn", nodes.get(43).text());// 厂商国家/地区（中文）
			result.put("producer_area_en", nodes.get(45).text());// 厂商国家/地区（英文）
			result.put("open_date", nodes.get(47).text());// 发证日期
			result.put("useful_date", nodes.get(49).text());// 有效期截止日
			result.put("company_name", nodes.get(51).text());// 分包装企业名称
			result.put("company_add", nodes.get(53).text());// 分包装企业地址
			result.put("perm_date", nodes.get(55).text());// 分包装文号批准日期
			result.put("perm_userful_date", nodes.get(57).text());// 分包装文号有效期截止日
			result.put("drug_standard_code_remark", nodes.get(59).text());// 药品本位码备注
			result.put("drug_standard_code", nodes.get(63).text());// 药品本位码
			
			prodType = nodes.get(61).text();
			switch (prodType) {
				case "化学药品":
					prodType="1";
					break;
				case "中药":
					prodType="2";
					break;
				case "生物制品":
					prodType="3";
					break;
				case "辅料":
					prodType="4";
					break;
				case "国产包材":
					prodType="5";
					break;
				case "中药保护":
					prodType="6";
					break;
				case "进口包材":
					prodType="7";
					break;
				case "行政保护":
					prodType="8";
					break;
				default:
					prodType="-1";
				break;
			}

			result.put("prod_type", prodType);// 产品类别(1:化学药品 2:中药 3:生物制品4:辅料 5:国产包材 6:中药保护7:进口包材 8:行政保护)
			result.put("creator", creator);// 创建人 crawler
			result.put("create_date", create_date);// 创建日期 当前日期
			break;
		// 国家基本药 base_national_base_drug 520
		case "74":
			title = MessageFormat.format("{0}({1})", nodes.get(9).text(), nodes
					.get(3).text());
			result.put("drug_type", nodes.get(1).text()); // 药品分类
			result.put("level1_category", nodes.get(3).text());// 一级类别
			result.put("level2_category", nodes.get(5).text());// 二级类别
			result.put("level3_categroy", nodes.get(7).text());// 三级类别
			result.put("drug_name", nodes.get(9).text());// 品种名称（药品名称）
			result.put("name_en", nodes.get(11).text());// 英文名称
			result.put("model", nodes.get(13).text());// 剂型/规格
			result.put("model_expl", nodes.get(15).text());// 剂型说明
			result.put("use_range", nodes.get(17).text());// 使用范围
			result.put("remark", nodes.get(19).text());// 备注
			result.put("creator", creator);// 创建人 crawler
			result.put("create_date", create_date);// 创建日期 当前日期
			break;
		// 药品生产企业 base_drug_produce_comp 7161
		case "34":
			title = MessageFormat.format("{0}({1})", nodes.get(7).text(), nodes
					.get(1).text());
			result.put("no", nodes.get(1).text()); // 编号
			result.put("categroy_code", nodes.get(3).text());// 分类码 Y
			result.put("city", nodes.get(5).text());// 省市
			result.put("company_name", nodes.get(7).text());// 企业名称
			result.put("legal_man", nodes.get(9).text());// 法定代表人
			result.put("wheel_man", nodes.get(11).text());// 企业负责人
			result.put("company_type", nodes.get(13).text());// 企业类型
			result.put("regist_add", nodes.get(15).text());// 注册地址
			result.put("produce_add", nodes.get(17).text());// 生产地址
			result.put("produce_rang", nodes.get(19).text());// 生产范围
			result.put("opene_date", nodes.get(21).text());// 发证日期
			result.put("useful_date", nodes.get(23).text());// 有效截止日期
			result.put("remark", nodes.get(25).text());// 备注
			result.put("creator", creator);// 创建人 crawler
			result.put("create_date", create_date);// 创建日期 当前日期
			break;

		// 药品经营企业 base_drug_run_comp
		case "41":
			title = MessageFormat.format("{0}({1})", nodes.get(3).text(), nodes
					.get(1).text());
			result.put("no", nodes.get(1).text()); // 证号
			result.put("company_name", nodes.get(3).text());// 企业名称
			result.put("regist_add", nodes.get(5).text());// 注册地址
			result.put("store_add", nodes.get(7).text());// 仓库地址
			result.put("legal_man", nodes.get(9).text());// 法定代表人
			result.put("wheel_man", nodes.get(11).text());// 企业负责人
			result.put("quality_wheel_man", nodes.get(13).text());// 质量负责人
			result.put("run_type", nodes.get(15).text());// 经营方式
			result.put("run_range", nodes.get(17).text());// 经营范围
			result.put("open_date", nodes.get(19).text());// 发证日期
			result.put("useful_date", nodes.get(21).text());// 有效期至
			result.put("open_dep", nodes.get(23).text());// 发证部门
			result.put("remark", nodes.get(25).text());// 备注
			result.put("creator", creator);// 创建人 crawler
			result.put("create_date", create_date);// 创建日期 当前日期

			break;
		// GSP认证 base_gsp
		case "24":
			title = MessageFormat.format("{0}({1})", nodes.get(5).text(), nodes
					.get(3).text());
			result.put("city", nodes.get(1).text()); // 省市
			result.put("no", nodes.get(3).text());// 证书编号
			result.put("company_name", nodes.get(5).text());// 企业名称
			result.put("addr", nodes.get(7).text());// 地址
			result.put("auth_rang", nodes.get(9).text());// 认证范围
			result.put("open_date", nodes.get(11).text());// 发证时间
			result.put("useful_date", nodes.get(13).text());// 有效期截至日
			result.put("creator", creator);// 创建人 crawler
			result.put("create_date", create_date);// 创建日期 当前日期
			break;
		// 麻醉药品和精神药品品种目录 base_drug_dire
		case "102":
			title = MessageFormat.format("{0}({1})", nodes.get(1).text(), nodes
					.get(9).text());
			result.put("name_cn", nodes.get(1).text()); // 中文名
			result.put("name_en", nodes.get(3).text());// 英文名
			result.put("cas_no", nodes.get(5).text());// CAS号
			result.put("remark", nodes.get(7).text());// 备注
			result.put("drug_type", nodes.get(9).text());// 药品类别
			result.put("dirc_version", nodes.get(11).text());// 目录版本
			result.put("creator", creator);// 创建人 crawler
			result.put("create_date", create_date);// 创建日期 当前日期
			break;
		/* 化妆品 */
		// 国产化妆品 base_makeup_cn
		case "68":
			title = MessageFormat.format("{0}({1})", nodes.get(1).text(), nodes
					.get(5).text());
			result.put("prod_name", nodes.get(1).text()); // 产品名称
			result.put("prod_type", nodes.get(3).text());// 产品类别(1:化学药品 2:中药 3:生物制药 4:辅料 5:国产包材 6:中药保护7:进口包材 8:行政保护)

			result.put("company_name", nodes.get(5).text());// 生产企业
			result.put("company_add", nodes.get(7).text());// 生产企业地址
			result.put("perm_no", nodes.get(9).text());// 批准文号
			result.put("perm_status", nodes.get(11).text());// 批件状态
			result.put("perm_date", nodes.get(13).text());// 批准日期
			result.put("useful_date", nodes.get(15).text());// 批件有效期
			result.put("production_no", nodes.get(17).text());// 卫产许可证号
			result.put("name_remark", nodes.get(19).text());// 产品名称备注
			result.put("remark", nodes.get(21).text());// 备注
			result.put("creator", creator);// 创建人 crawler
			result.put("create_date", create_date);// 创建日期 当前日期
			break;
		// 化妆品生产许可获证企业 base_makup_comp
		case "93":
			title = MessageFormat.format("{0}({1})", nodes.get(5).text(), nodes
					.get(7).text());
			result.put("city", nodes.get(1).text()); // 省份
			result.put("no", nodes.get(3).text());// 证书编号
			result.put("company_name", nodes.get(5).text());// 企业名称
			result.put("prod_name", nodes.get(7).text());// 产品名称
			result.put("home_add", nodes.get(9).text());// 住所
			result.put("produce_add", nodes.get(11).text());// 生产地址
			result.put("detail", nodes.get(13).text());// 明细
			result.put("open_date", nodes.get(15).text());// 发证日期
			result.put("useful_date", nodes.get(17).text());// 有效期
			result.put("creator", creator);// 创建人 crawler
			result.put("create_date", create_date);// 创建日期 当前日期
			break;
		// 进口化妆品 base_makeup_en
		case "69":
			title = MessageFormat.format("{0}({1} {2})", nodes.get(1).text(),
					nodes.get(9).text(), nodes.get(19).text());
			result.put("prod_name_cn", nodes.get(1).text()); // 产品名称（中文）
			result.put("prod_name_en", nodes.get(3).text());// 产品名称（en文）
			result.put("prod_type",  nodes.get(5).text());// 产品类别(1:化学药品 2:中药 3:生物制药 4:辅料 5:国产包材 6:中药保护7:进口包材 8:行政保护)

			result.put("city", nodes.get(7).text());// 生产国（地区）
			result.put("produce_name_cn", nodes.get(9).text());// 生产企业（中文）
			result.put("produce_name_en", nodes.get(11).text());// 生产企业（en文）
			result.put("produce_add", nodes.get(13).text());// 生产企业地址
			result.put("report_dep", nodes.get(15).text());// 在华申报责任单位
			result.put("report_dep_add", nodes.get(17).text());// 在华责任单位地址
			result.put("perm_no", nodes.get(19).text());// 批准文号
			result.put("perm_date", nodes.get(21).text());// 批准日期
			result.put("useful_date", nodes.get(23).text());// 批准有效期
			result.put("remark", nodes.get(25).text());// 备注
			result.put("remark_name", nodes.get(27).text());// 产品名称备注
			result.put("perm_status", nodes.get(29).text());// 批件状态
			result.put("creator", creator);// 创建人 crawler
			result.put("create_date", create_date);// 创建日期 当前日期
			break;
		/* 医疗器械 */
		// 国产器械 base_instrument_cn **************************
		case "26":
			title = MessageFormat.format("{0}({1})", nodes.get(3).text(), nodes
					.get(1).text());
			result.put("no", nodes.get(1).text()); // 注册号
			result.put("prod_name", nodes.get(3).text());// 产品名称
			result.put("producer", nodes.get(5).text());// 生产单位
			result.put("addr", nodes.get(7).text());// 地址
			result.put("prod_standerd", nodes.get(9).text());// 产品标准
			result.put("prod_struct", nodes.get(11).text());// 产品性能结构及组成
			result.put("use_rang", nodes.get(13).text());// 产品使用范围
			result.put("spec", nodes.get(15).text());// 规格型号
			result.put("perm_date", nodes.get(17).text());// 批准日期
			result.put("useful_date", nodes.get(19).text());// 有效期
			result.put("update_date", nodes.get(21).text());// 变更日期
			result.put("production_place", nodes.get(23).text());// 生产场所
			result.put("post_no", nodes.get(25).text());// 邮编
			result.put("attr", nodes.get(27).text());// 附件
			result.put("remark", nodes.get(29).text());// 备注
			result.put("creator", creator);// 创建人 crawler
			result.put("create_date", create_date);// 创建日期 当前日期
			break;
		// 进口器械 base_instrument_en
		case "27":
			title = MessageFormat.format("{0}({1})", nodes.get(13).text(),
					nodes.get(1).text());
			result.put("no", nodes.get(1).text()); // 注册号
			result.put("producer_name_cn", nodes.get(3).text());// 生产厂商名称（中文）
			result.put("producer_name_en", nodes.get(5).text());// 生产厂商名称（en文）
			result.put("producer_add", nodes.get(7).text());// 生产厂地址（中文）
			result.put("production_place", nodes.get(9).text());// 生产场所
			result.put("city", nodes.get(11).text());// 生产国或地区（中文）
			result.put("prod_name_cn", nodes.get(13).text());// 产品名称（中文）
			result.put("prod_name_en", nodes.get(15).text());// 产品名称（英文）
			result.put("spec", nodes.get(17).text());// 规格型号
			result.put("standerd", nodes.get(19).text());// 产品标准
			result.put("prod_struct", nodes.get(21).text());// 产品性能结构及组成
			result.put("use_rang", nodes.get(23).text());// 产品适用范围
			result.put("regist_proxy", nodes.get(25).text());// 注册代理
			result.put("service_org", nodes.get(27).text());// 售后服务机构
			result.put("perm_date", nodes.get(29).text());// 批准日期
			result.put("useful_date", nodes.get(31).text());// 有效期截至日
			result.put("remark", nodes.get(33).text());// 备注
			result.put("update_date", nodes.get(35).text());// 变更日期
			result.put("attr", nodes.get(37).text());// 附件
			result.put("creator", creator);// 创建人 crawler
			result.put("create_date", create_date);// 创建日期 当前日期
			break;
		// 医疗器械分类目录 base_instrument_categroy_dire 306
		case "20":
			title = nodes.get(9).text();
			result.put("code_no", nodes.get(1).text()); // 编码代号
			result.put("categroy_no", nodes.get(3).text());// 分类编号
			result.put("manage_type", nodes.get(5).text());// 管理类别
			result.put("example", nodes.get(7).text());// 品名举例
			result.put("categroy_name", nodes.get(9).text());// 分类名称
			result.put("creator", creator);// 创建人 crawler
			result.put("create_date", create_date);// 创建日期 当前日期

			break;
		// 医疗器械标准目录 base_instrument_stand_dire
		case "21":
			title = nodes.get(1).text();
			result.put("name", nodes.get(1).text()); // 标准名称
			result.put("no", nodes.get(3).text());// 标准编号
			result.put("guikou", nodes.get(5).text());// 归口
			result.put("remark", nodes.get(7).text());// 备注
			result.put("replace_no", nodes.get(9).text());// 代替标准号
			result.put("impl_date", nodes.get(11).text());// 实施日期
			result.put("perm_date", nodes.get(13).text());// 批准日期
			result.put("adopt_condition", nodes.get(15).text());// 采标情况
			result.put("creator", creator);// 创建人 crawler
			result.put("create_date", create_date);// 创建日期 当前日期
			break;
		// 体外诊断试剂分类子目录 base_ivd 766
		case "105":
			title = MessageFormat.format("{0}({1})", nodes.get(7).text(), nodes
					.get(5).text());
			result.put("no", nodes.get(1).text()); // 分类编码
			result.put("manag_type", nodes.get(3).text());// 管理类别

			result.put("prod_type", nodes.get(5).text());// 产品类别(1:化学药品 2:中药 3:生物制药 4:辅料 5:国产包材 6:中药保护7:进口包材 8:行政保护)

			result.put("prod_type_name", nodes.get(7).text());// 产品分类名称
			result.put("intended_use", nodes.get(9).text());// 预期用途
			result.put("dire_version", nodes.get(11).text());// 目录版本
			result.put("creator", creator);// 创建人 crawler
			result.put("create_date", create_date);// 创建日期 当前日期
			break;
		/* 食品 */
		// 食品生产许可证获证企业 base_food_perm_comp 176276
		case "91":
			title = nodes.get(1).text();
			result.put("no", nodes.get(1).text()); // 证书编号
			result.put("company_name", nodes.get(3).text());// 企业名称
			result.put("food_name", nodes.get(5).text());// 产品名称
			result.put("home_add", nodes.get(7).text());// 住所
			result.put("producer_add", nodes.get(9).text());// 生产地址
			result.put("check_type", nodes.get(11).text());// 检验方式
			result.put("open_date", nodes.get(13).text());// 发证日期
			result.put("useful_date", nodes.get(15).text());// 证书有效期
			result.put("open_dep", nodes.get(17).text());// 发证单位
			result.put("creator", creator);// 创建人 crawler
			result.put("create_date", create_date);// 创建日期 当前日期
			break;
		// 食品添加剂生产许可获证企业 base_food_additive_perm_comp
		case "89":
			title = nodes.get(1).text();
			result.put("no", nodes.get(1).text()); // 许可证编号
			result.put("city", nodes.get(3).text());// 省份
			result.put("company_name", nodes.get(5).text());// 企业名称
			result.put("prod_name", nodes.get(7).text());// 产品名称
			result.put("home_add", nodes.get(9).text());// 住所
			result.put("producer_add", nodes.get(11).text());// 生产地址
			result.put("detail", nodes.get(13).text());// 明细
			result.put("open_date", nodes.get(15).text());// 发证日期
			result.put("useful_date", nodes.get(17).text());// 有效期
			result.put("remark", nodes.get(19).text());// 说明
			result.put("report_dep", nodes.get(21).text());// 上报部门
			result.put("creator", creator);// 创建人 crawler
			result.put("create_date", create_date);// 创建日期 当前日期

			break;
		default:
			break;
		}
		return result;
	}

}
