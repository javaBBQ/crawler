/*
 *Project: glorypty-crawler
 *File: com.glorypty.excel.DirectoryController.java <2015年4月8日>
 ****************************************************************
 * 版权所有@2015 国裕网络科技  保留所有权利.
 ***************************************************************/
package com.glorypty.excel.sfda;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import cn.xkshow.io.file.FileHelper;

/**
 *
 * @Author hardy
 * @Date 2015年4月8日 上午11:03:05
 */
public class Controller {
	
	public void door(){
		Executor poolThread = Executors.newFixedThreadPool(5);
		
		List<String> lst = FileHelper.listDirectories(Constants.PATH_SOURCE);
		if(null==lst || lst.isEmpty())
			return ;
		for (String pathChildDir : lst) {		
			try {
				poolThread.execute(new FileTask(pathChildDir));
				// 设置线程休眠间隙时间
				Thread.sleep(5*1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}	
	
}
