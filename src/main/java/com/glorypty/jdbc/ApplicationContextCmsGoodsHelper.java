/*
 *Project: glorypty-crawler
 *File: com.glorypty.jdbc.ApplicationContextCmsGoodsHelper.java <2015年12月22日>
 ****************************************************************
 * 版权所有@2015 国裕网络科技  保留所有权利.
 ***************************************************************/
package com.glorypty.jdbc;

import java.util.Map;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author ZhangLujun 
 * @Date 2015年12月22日 上午10:14:42
 * @version 1.0
 */
public class ApplicationContextCmsGoodsHelper {
	public static final JdbcTemplate jdbcTemplate = (JdbcTemplate)new ClassPathXmlApplicationContext("applicationContextCmsGoods.xml").getBean("jdbcTemplate");
	
	private ApplicationContextCmsGoodsHelper(){}
	
	/**
	 * SQL execute, typically a DDL statement.
	 * @Author hardy<2015年5月19日>
	 * @param sql
	 */
	public static void execute(String sql) {
		if(!sql.isEmpty())
			jdbcTemplate.execute(sql);
	}
	
	/**
	 * SQL update operation (such as an insert, update or delete statement).
	 * @Author hardy<2015年5月19日>
	 * @param sql
	 * @return
	 */
	public static boolean insert(String sql) {
		return jdbcTemplate.update(sql)>0 ? true : false;
	}
	
	/**
	 * SQL update operation(such as an insert, update or delete statement) via a prepared statement
	 * @Author hardy<2015年5月19日>
	 * @param sql
	 * @param args参数
	 * @return
	 */
	public static boolean insert(String sql, Object... args) {
		return jdbcTemplate.update(sql, args)>0 ? true : false;
	}
	
	/**
	 * SQL update operation(such as an insert, update or delete statement) via a prepared statement
	 * @Author hardy<2015年5月19日>
	 * @param sql
	 * @param args参数
	 * @param argTypes参数类型
	 * @return
	 */
	public static boolean insert(String sql, Object[] args, int[] argTypes) {
		return jdbcTemplate.update(sql, args, argTypes)>0 ? true : false;
	}
	
	/**
	 * 查询
	 * @author Andy
	 * @param sql
	 * @return
	 */
	public static Map<String, Object> queryWihtMap(String sql) {
		return jdbcTemplate.queryForMap(sql);
	}
	
	/**
	 * 查询结果数
	 * @author ZhangLujun<2016年2月22日>
	 * @param sql
	 * @return
	 */
	public static int query(String sql){
		return jdbcTemplate.queryForInt(sql);
	}
}
