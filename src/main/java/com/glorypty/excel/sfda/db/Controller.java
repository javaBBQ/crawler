/*
 *Project: glorypty-crawler
 *File: com.glorypty.excel.sfda.db.Controller.java <2015年5月21日>
 ****************************************************************
 * 版权所有@2015 国裕网络科技  保留所有权利.
 ***************************************************************/
package com.glorypty.excel.sfda.db;

import java.text.MessageFormat;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import cn.xkshow.commons.logging.LoggerBase;
import cn.xkshow.io.file.FileHelper;

import com.glorypty.crawler.sfda.TempDB;
import com.glorypty.excel.sfda.Constants;


/**
 *
 * @Author hardy 
 * @Date 2015年5月21日 下午4:24:48
 * @version 1.0
 */
public class Controller extends LoggerBase {
	public void door(){
		Executor pool = Executors.newFixedThreadPool(3);		
		
		List<String> lst = FileHelper.listDirectories(Constants.PATH_SOURCE);
		if(null==lst || lst.isEmpty())
			return ;
		String childDir = null;
		for (String pathChildDir : lst) {	
			// 分类信息匹配：文件夹=>药监局分类=>药监局分类ID
			childDir = pathChildDir.substring(pathChildDir.lastIndexOf("\\")+1);
			if(!TempDB.DATA_GROUP_NAME.containsKey(childDir)){
				this.logger.error(MessageFormat.format("此分类信息有误 >>{0}" , childDir));
				continue;
			}
			pool.execute(new FileTask(pathChildDir));
		}
	}	
}
