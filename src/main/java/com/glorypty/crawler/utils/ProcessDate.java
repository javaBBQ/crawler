package com.glorypty.crawler.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间比较工具类
 * 
 * @author Zhanglujun
 *
 */
public class ProcessDate {

	/**
	 * 时间格式转换
	 * 
	 * @param time
	 *            时间字符串
	 * @param format
	 *            时间格式 时间字符串和时间格式必须对应起来 例如：time 2015/05/28 09:20:17 format
	 *            yyyy/MM/dd HH:mm:ss
	 * @return Date
	 */
	public static Date convertToDate(String time, String format) {
		SimpleDateFormat formate = new SimpleDateFormat(format);
		Date date = null;
		if (time != null && !"".equals(time)) {
			try {
				date = formate.parse(time);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return date;
	}
	
	/**
	 * 时间比较
	 * @author Andy
	 * @param limitDate  限定日期
	 * @param sourceDate 页面读取日期 
	 * @param formate    日期格式
	 * @return
	 * true 在特定时间范围内
	 * false 未在特定时间范围内
	 */
	public static boolean compareDate(String limitDate,String sourceDate,String formate){
		boolean b = false;
		long limit = 0;
		long source = 0;
		Calendar calendar = null;
		
		String limitArr[] = null;
		if(limitDate.indexOf("-")!=-1){
			limitArr = limitDate.split("-");
		}else if(limitDate.indexOf(".")!=-1){
			limitArr = limitDate.split("\\.");
		}else if(limitDate.indexOf("/")!=-1){
			limitArr = limitDate.split("/");
		}
		
		String sourceArr[] = null;
		if(sourceDate.indexOf("-")!=-1){
			sourceArr = sourceDate.split("-");
		}else if(sourceDate.indexOf(".")!=-1){
			sourceArr = sourceDate.split("\\.");
		}else if(sourceDate.indexOf("/")!=-1){
			sourceArr = sourceDate.split("/");
		}
		
		if(formate.length() == 10 ){
			if(limitArr!=null){
				calendar = Calendar.getInstance();
				if(limitArr.length>2){
					calendar.set(Integer.parseInt(limitArr[0]), Integer.parseInt(limitArr[1]), Integer.parseInt(limitArr[2]));
				}else{
					calendar.set(Integer.parseInt(limitArr[0]), Integer.parseInt(limitArr[1]), 1);
				}
				limit = calendar.getTimeInMillis();
			}	
			
			if(sourceArr!=null){
				calendar = Calendar.getInstance();
				if(sourceArr.length>2){
					calendar.set(Integer.parseInt(sourceArr[0]), Integer.parseInt(sourceArr[1]), Integer.parseInt(sourceArr[2]));
				}else{
					calendar.set(Integer.parseInt(sourceArr[0]), Integer.parseInt(sourceArr[1]), 1);
				}
				source = calendar.getTimeInMillis();
			}	
			
		}else{
			Date lm = new Date(limitDate);
			Date so = new Date(sourceDate);
			limit = lm.getTime();
			source = so.getTime();
		}	
		
		if((source-limit)>0){
			b = true;
		}
		return b;
	}

	/**
	 * 时间比较
	 * 
	 * @param date
	 *            需比较的时间
	 * @param month
	 *            月，正整数为未来时间，负整数为过去时间
	 * @return boolean
	 */
	public static boolean compareDate(Date date, int month) {
		boolean b = false;
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, month);
		if (month < 0) {
			if (date != null) {
				Date past = calendar.getTime();
				if ((date.getTime() - past.getTime()) >= 0) {
					b = true;
				}
			}
		}

		return b;
	}
	
	/**
	 * 时间比较（天数）
	 * @author ZhangLujun<2016年2月23日>
	 * @param date 需比较的时间
	 * @param day 日，正整数为未来时间，负整数为过去时间
	 * @return
	 */
	public static boolean compareDateDay(Date date,int day){
		boolean b = false;
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, day);
		if (day < 0) {
			if (date != null) {
				Date past = calendar.getTime();
				if ((date.getTime() - past.getTime()) >= 0) {
					b = true;
				}
			}
		}

		return b;
	}
	
	
	/**
	 * 
	 * @param time
	 * @param format
	 * @param month
	 * @return 
	 * true 在特定时间范围内
	 * false 未在特定时间范围内
	 */
	public static boolean compareDate(String time, String format , int month) {
		boolean b = false;
		Date date =  null;
		Date pastDate = null;
		
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, month);
		pastDate = calendar.getTime();
		
		if(format.length() == 10 ){
			String arr[] = null;
			if(time.indexOf("-")!=-1){
				arr = time.split("-");
			}else if(time.indexOf(".")!=-1){
				arr = time.split("\\.");
			}else if(time.indexOf("/")!=-1){
				arr = time.split("/");
			}
			
			if(arr!=null){
				Calendar calendar_now = Calendar.getInstance();
				if(arr.length>2){
					calendar_now.set(Integer.parseInt(arr[0]), Integer.parseInt(arr[1]), Integer.parseInt(arr[2]));
				}else{
					calendar_now.set(Integer.parseInt(arr[0]), Integer.parseInt(arr[1]), 1);
				}
				date = calendar_now.getTime();
			}
		}else if(format.length() == 8){
			String arr[] = null;
			if(time.indexOf("-")!=-1){
				arr = time.split("-");
			}else if(time.indexOf(".")!=-1){
				arr = time.split("\\.");
			}else if(time.indexOf("/")!=-1){
				arr = time.split("/");
			}
			String now = new Date().getYear()+"";
			if(arr!=null){
				Calendar calendar_now = Calendar.getInstance();
				calendar_now.set(Integer.parseInt(now.substring(0, 2)+arr[0]), Integer.parseInt(arr[1]), Integer.parseInt(arr[2]));
				date = calendar_now.getTime();
			}
		}
		else{
//			date = convertToDate(time,format);
			date = new Date(time);
		}
		
		if (month < 0) {
			if (date != null) {
				long now = date.getTime();
				long past = pastDate.getTime();
//				System.out.println(now+"----"+past);
				if ((now - past) >= 0) {
					b = true;
				}
			}
		}
		
		return b;
	}

	public static void main(String[] args) {
//		System.out.println(compareDate("2015/06/14 14:29:33","yyyy/MM/DD HH:mm:ss",-1));
//		System.out.println(Date.parse("2015/06/14 14:29:33"));
//		System.out.println(new Date("2015/06/14 14:29:33"));
//		String now = new Date().toLocaleString();
//		System.out.println( now );
		System.out.println(convertToDate("2016-02-05","yyyy-MM-dd"));
//		System.out.println(compareDateDay(new Date("2016/02/21 14:29:33"),-1));
	}
}
