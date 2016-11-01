/*
 *Project: glorypty-crawler
 *File: com.glorypty.crawler.common.TempDB.java 
 ****************************************************************
 * 版权所有@2015 国裕秀网络科技  保留所有权利.
 ***************************************************************/ 
package com.glorypty.crawler.sfda;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.select.Elements;

/**
 * 临时数据库
 * @Author hardy(admin@xkshow.cn)
 * @Date 2015年4月4日 下午10:08:58
 */
public class TempDB {

	/** 数据分类(抓取部分)：药监局分类=国瑞分类 */
	@SuppressWarnings("serial")
	public static final Map<String, Integer> DATA_GROUP = new HashMap<String, Integer>(){{
		/* 保健食品-2  */
		put("30", 100);	//国产保健食品(14795)
		put("31", 101);	//进口保健食品(735)
		/* 药品-21  */
		put("25", 102);	//国产药品(169226)
		put("32", 103);	//国产药品商品名(7091)
		put("19", 104);	//药物临床试验机构名单(823)
		put("60", 105);	//进口药品商品名(3920)
		put("6", 106);	//批准的药包材(5365)
		put("22", 107);	//中药保护品种(342)
		put("79", 108);	//基本药物生产企业入网目录(3919)
		
		put("63", 109);	//药品注册补充申请备案情况公示(156523)		
		put("65", 110);	//药品注册相关专利信息公开公示(1932)
		put("36", 111);	//进口药品(4707)
		put("23", 112);	//GMP认证(24114)
		put("16", 113);	//药品注册批件发送信息(112443)
		put("1", 114);	//OTC化学药品说明书范本(1188)
		put("84", 115);	//进口药品电子监管工作代理机构(330)
		
		put("74", 116);	//国家基本药物（2012年版）(520)
		put("5", 117);	//申请人申报受理情况(144603)
		put("34", 118);	//药品生产企业(7159)
		put("41", 119);	//药品经营企业(141809)
		put("24", 120);	//GSP认证(143322)
		put("2", 121);	//OTC中药说明书范本(4621)
		put("102", 122);	//麻醉药品和精神药品品种目录(270)
		/* 化妆品-5  */
		put("68", 123);	//国产化妆品(23304)
		put("93", 124);	//化妆品生产许可获证企业(3880)
		put("69", 125);	//进口化妆品(115001)
		put("108", 126);	//化妆品行政许可检验机构(27)
		put("83", 127);	//国产非特殊用途化妆品备案检验机构(164)
		/* 医疗器械-9  */
		put("26", 128);	//国产器械(103306)
		put("27", 129);	//进口器械(36915)
		put("18", 130);	//医疗器械检测中心受检目录(32933)
		put("94", 131);	//国产器械（历史数据）(40838)
		put("95", 132);	//进口器械（历史数据）(12487)
		put("20", 133);	//医疗器械分类目录(306)
		put("21", 134);	//医疗器械标准目录(700)
		put("105", 135);	//体外诊断试剂分类子目录（2013版）(766)
		put("104", 136);	//第一类医疗器械（含第一类体外诊断试剂）备案信息(3740)
	}};
	
	/** 数据分类(抓取部分)：药监局分类名=药监局分类ID */
	@SuppressWarnings("serial")
	public static final Map<String, String> DATA_GROUP_NAME = new HashMap<String, String>(){{
		/* 保健食品-2  */
		put("国产保健食品", "30");
		put("进口保健食品", "31");
		/* 药品-21  */
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
		/* 化妆品-5  */
		put("国产化妆品", "68");	
		put("化妆品生产许可获证企业", "93");	
		put("进口化妆品", "69");	
		put("化妆品行政许可检验机构", "108");	
		put("国产非特殊用途化妆品备案检验机构", "83");	
		/* 医疗器械-9  */
		put("国产器械", "26");	
		put("进口器械", "27");	
		put("医疗器械检测中心受检目录", "18");	
		put("国产器械（历史数据）", "94");	
		put("进口器械（历史数据）", "95");	
		put("医疗器械分类目录", "20");	
		put("医疗器械标准目录", "21");	
		put("体外诊断试剂分类子目录（2013版）", "105");
		put("第一类医疗器械（含第一类体外诊断试剂）备案信息", "104");
	}};
	
	/**
	 * 获取组装标题(根据药监局表ID)
	 * @Author hardy<2015年5月21日>
	 * @param tableId
	 * @param nodes
	 * @return
	 */
	public static String getTitleByTable(String tableId, Elements nodes){
		// 按分类组装Title
		String title = null;		
		switch (tableId) {		
			/* 保健食品  */
			case "30": title = MessageFormat.format("{0}({1} {2})", nodes.get(1).text(), nodes.get(5).text(), nodes.get(57).text()); break;
			case "31": title = MessageFormat.format("{0}({1})", nodes.get(5).text(), nodes.get(71).text()); break;
			/* 药品 */
			case "25": 
				if(nodes.get(0).text().contains("品种名称")){
					title = MessageFormat.format("{0}({1} {2})", nodes.get(1).text(), nodes.get(3).text(), nodes.get(5).text()); 
				}else if(nodes.get(0).text().contains("保护品种编号")){
					title = MessageFormat.format("{0}({1} {2})", nodes.get(3).text(), nodes.get(7).text(), nodes.get(17).text()); 
				}else{
					title = MessageFormat.format("{0}({1} {2})", nodes.get(3).text(), nodes.get(1).text(), nodes.get(13).text()); 
				}				
				break;
			case "32": title = MessageFormat.format("{0}({1} {2})", nodes.get(9).text(), nodes.get(1).text(), nodes.get(11).text()); break;
			case "19": title = nodes.get(3).text(); break;
			case "60": title = nodes.get(9).text(); break;
			case "6": title = MessageFormat.format("{0}({1} {2})", nodes.get(1).text(), nodes.get(3).text(), nodes.get(5).text()); break;
			case "22": title = MessageFormat.format("{0}({1} {2})", nodes.get(3).text(), nodes.get(7).text(), nodes.get(17).text()); break;
			case "79": title = nodes.get(5).text(); break;
			
			case "63": title = MessageFormat.format("{0}({1})", nodes.get(5).text(), nodes.get(23).text()); break;
			case "65": title = MessageFormat.format("{0}({1} {2})", nodes.get(3).text(), nodes.get(5).text(), nodes.get(7).text()); break;
			case "36": 
				if(nodes.get(0).text().contains("品种名称")){
					title = MessageFormat.format("{0}({1} {2})", nodes.get(1).text(), nodes.get(3).text(), nodes.get(5).text()); 
				}else{
					title = MessageFormat.format("{0}({1})", nodes.get(21).text(), nodes.get(1).text()); 
				}				
				break;
			case "23": title = MessageFormat.format("{0}({1})", nodes.get(5).text(), nodes.get(3).text()); break;
			case "16": title = nodes.get(5).text(); break;
			case "1": title = nodes.get(1).text(); break;
			case "84": title = MessageFormat.format("{0}({1})", nodes.get(3).text(), nodes.get(1).text()); break;
			
			case "74": title = MessageFormat.format("{0}({1})", nodes.get(9).text(), nodes.get(3).text()); break;
			case "5": title = nodes.get(1).text(); break;
			case "34": title = MessageFormat.format("{0}({1})", nodes.get(7).text(), nodes.get(1).text()); break;
			case "41": title = MessageFormat.format("{0}({1})", nodes.get(3).text(), nodes.get(1).text()); break;
			case "24": title = MessageFormat.format("{0}({1})", nodes.get(5).text(), nodes.get(3).text()); break;
			case "2": title = nodes.get(1).text(); break;
			case "102": title = MessageFormat.format("{0}({1})", nodes.get(1).text(), nodes.get(9).text()); break;
			/* 化妆平  */
			case "68": title = MessageFormat.format("{0}({1})", nodes.get(1).text(), nodes.get(5).text()); break;
			case "93": title = MessageFormat.format("{0}({1})", nodes.get(5).text(), nodes.get(7).text()); break;
			
			case "69": title = MessageFormat.format("{0}({1} {2})", nodes.get(1).text(), nodes.get(9).text(), nodes.get(19).text()); break;
			case "108": title = MessageFormat.format("{0}({1})", nodes.get(1).text(), nodes.get(11).text()); break;
			
			case "83": title = nodes.get(1).text(); break;			
			/* 医疗器械  */
			case "26": title = MessageFormat.format("{0}({1})", nodes.get(3).text(), nodes.get(1).text()); break;
			case "27": title = MessageFormat.format("{0}({1})", nodes.get(13).text(), nodes.get(1).text()); break;
			case "18": title = MessageFormat.format("{0}({1})", nodes.get(13).text(), nodes.get(1).text()); break;
			
			case "94": title = MessageFormat.format("{0}({1})", nodes.get(3).text(), nodes.get(1).text()); break;
			case "95": title = MessageFormat.format("{0}({1})", nodes.get(13).text(), nodes.get(1).text()); break;
			case "20": title = nodes.get(9).text(); break;
			
			case "21": title = nodes.get(1).text(); break;
			case "105": title = MessageFormat.format("{0}({1})", nodes.get(7).text(), nodes.get(5).text()); break;
			case "104": title = nodes.get(1).text(); break;
			default:break;
		}
		return title;
	}
	
}
