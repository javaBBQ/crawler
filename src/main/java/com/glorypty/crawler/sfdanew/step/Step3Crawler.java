/*
 *Project: glorypty-crawler
 *File: com.glorypty.crawler.sfda.step.Step3Crawler.java	<2015年4月2日>
 ****************************************************************
 * 版权所有@2015 国裕网络科技  保留所有权利.
 ***************************************************************/
package com.glorypty.crawler.sfdanew.step;

import java.text.MessageFormat;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import cn.xkshow.io.file.FileHelper;
import cn.xkshow.net.URLHelper;

import com.glorypty.crawler.sfdanew.Constants;
import com.glorypty.crawler.sfdanew.StepCrawler;
import com.glorypty.crawler.sfdanew.TempDB;
import com.glorypty.jdbc.CrawlerService;

import edu.uci.ics.crawler4j.crawler.Page;

/**
 * 采集详情页内容 by 详情页
 * @Author yiwen
 * @Date 2015年4月2日 下午3:55:22
 */
public class Step3Crawler extends StepCrawler {
	
	private static final String TXT = "<table width='100%' align='center'>{0}</table>";

	@Override
	public void visit(Page page) {
		String url = page.getWebURL().getURL();
		try {
			Document doc = this.getDocument(page);
			// 仅解析三级[详情]页面
			if (null != doc && url.startsWith(Constants.URL_PREFIX_LEV3)) {
				Elements contents = doc.select("div table[align=center]");
				if (null != contents && !contents.isEmpty()) {
					Element element = contents.get(0);
					String groupName = element.select("div.zs2").text();
					// groupName 国产药品
					if (StringUtils.isEmpty(groupName)) {
						this.logger.error(MessageFormat.format(
								"解析({0})：详情页 : {1}", groupName, url));
						return;
					}
					this.saveDB(URLHelper.getValue(url, "tableId"), groupName,element);
				}
			}
		} catch (Exception e) {
			this.logger.error("执行：内容页码(" + url + ")： " + e);
		}
	}

	/**
	 * 插入数据库
	 * 
	 * @Author hardy<2015年5月14日>
	 */
	private void saveDB(String tableId, String groupName, Element element) {
		Elements nodesTR = element.select("tr:not(tr[^bgc])");
		if (null == nodesTR || nodesTR.isEmpty() || nodesTR.size() < 2) {
			return;
		}
		// 按分类组装字段
		Map<String, String> result = TempDB.getFieldByTable(tableId,nodesTR.select("td"));
//		System.out.println("result:  " + result);
		if (null ==TempDB.DATA_TABLE.get(tableId)||"".equals(TempDB.DATA_TABLE.get(tableId))||0==result.size()) {
			return;
		}
		CrawlerService.executeBasedata(TempDB.DATA_TABLE.get(tableId), result,"cms");
	}

	/**
	 * 生成本地文件
	 * 
	 * @Author hardy<2015年5月14日>
	 * @param url
	 * @param contents
	 */
	@SuppressWarnings("unused")
	private void saveFile(Long fileId, String groupName, Element element) {
		String filename = fileId + ".htm";
		String path = MessageFormat.format("{0}step3/data/{1}/",
				Constants.CRAWL_STORAGE, groupName);

		// 文件不存在，且创建成功时写入文件
		if (!FileHelper.isExist(path + filename)
				&& FileHelper.createAuto(path, filename)) {
			this.logger.debug("生成：详情页 : " + path + filename);
			FileHelper.write(
					path + filename,
					MessageFormat.format(Constants.CRAWLER_DOWNLOAD_CONTENT,
							Constants.HTML_CHARSET, element.toString())
							.getBytes());
		}
	}

}
