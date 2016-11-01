/**
 *Project: glorypty-crawler
 *File: com.glorypty.excel.ControllerTest.java
 ****************************************************************
 *note:
 ***************************************************************/
package com.glorypty.excel;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import cn.xkshow.io.file.FileHelper;

import com.glorypty.excel.common.ConstantsExcel.HtmlSourceEnum;
import com.glorypty.excel.sfda.Constants;
import com.glorypty.excel.sfda.FileTask;

/**
 *
 * @Author hardy
 * @Date 2015年4月8日 下午4:06:59
 */
public class ControllerTest {
	private static long TIME_EXECUTE = 0l;
	
	@Before
	public void setUp() throws Exception {
		TIME_EXECUTE = System.currentTimeMillis();
	}

	@After
	public void tearDown() throws Exception {
		TIME_EXECUTE -= System.currentTimeMillis();
		System.out.println("执行完毕：耗时(ss)：" + Math.abs(TIME_EXECUTE)/1000);
	}

//	@Ignore
	@Test
	public void allTest() {
		ExcelFactory.door(HtmlSourceEnum.DB_SFDA);
	}
	
	@Ignore
	@Test
	public void test() {
		List<String> lst = FileHelper.listDirectories(Constants.PATH_SOURCE);
		if(null==lst || lst.isEmpty())
			return ;
		for (String pathChildDir : lst) {			
//			if(pathChildDir.indexOf("进口器械7") > -1)
				new FileTask(pathChildDir).run();
		}
	}

}
