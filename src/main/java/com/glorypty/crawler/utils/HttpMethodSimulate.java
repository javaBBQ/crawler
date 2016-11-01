package com.glorypty.crawler.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;
import org.jsoup.parser.XmlTreeBuilder;

/**
 * Http请求方式模拟(POST、GET)
 * @author Andy
 *
 */
public class HttpMethodSimulate {
	
	protected static Logger logger = Logger.getLogger(HttpMethodSimulate.class);
	
	/*** 请求允许时间20秒 */
	public static int SC_OUTTIME =  20000; 
	
	/** 如果timeOut，允许次数不能超过3次*/
	public static int timeOutMaxNum = 3;
	public static int timeOutNum = 0;
 	
	private static HttpClient httpClient = new HttpClient();
	
	/**
	 * 根据列表页首页及post参数，通过模拟POST提交方式，返回Document
	 * @param href 
	 * @param paramsMap
	 * @return Document
	 */
	@Deprecated
	public static Document postReqSimulate(String href,Map<String,String> paramsMap){
		Document document = null;
//		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(20000).setConnectTimeout(20000).build();
        //请求post方法
        PostMethod postMethod = new PostMethod(href);
        
        //post请求参数
        if(paramsMap!=null){
        	for (Iterator iterator = paramsMap.entrySet().iterator(); iterator.hasNext();) {
				Entry<String, String> entry = (Entry) iterator.next();
				postMethod.addParameter(entry.getKey(),entry.getValue());
			}
        }
        
        //执行，返回状态码
        int statusCode = 0;
        try {
        	httpClient.setTimeout(SC_OUTTIME);
        	httpClient.setConnectionTimeout(SC_OUTTIME);
            statusCode = httpClient.executeMethod(postMethod);
	        //针对状态码进行处理
	        if(statusCode == HttpStatus.SC_OK) {
	        	timeOutNum = 0;
	        	String response = postMethod.getResponseBodyAsString();
	        	//将html字符串转换为Docment
	        	document= Jsoup.parse(response,"",new Parser(new XmlTreeBuilder()));
	        }
        } catch (HttpException e) {
        	if(e.getMessage().toLowerCase().contains("timed out")){
        		if(timeOutNum<timeOutMaxNum){
        			if(timeOutNum==1){
        				logger.error(" request timed out(twice) :"+href);
        			}else if(timeOutNum==2){
        				logger.error(" request timed out(three) :"+href);
        			}
        			postReqSimulate(href, paramsMap);
        			timeOutNum++;
        		}else{
        			timeOutNum = 0;
        			logger.error(href+":"+e);
        		}
        	}
        } catch (IOException e) {
        	logger.error(href+":"+e);
        }
        return document;
	}
	
	/**
	 * 根据列表页首页及post参数，通过模拟POST提交方式，返回Document
	 * @param href 
	 * @param paramsMap  参数
	 * @param charset 字符编码
	 * @return Document
	 */
	@Deprecated
	public static Document postReqSimulate(String href,Map<String,String> paramsMap,String charset){
		Document document = null;
        //请求post方法
        PostMethod postMethod = new PostMethod(href);
        
        //post请求参数
        if(paramsMap!=null){
        	for (Iterator iterator = paramsMap.entrySet().iterator(); iterator.hasNext();) {
				Entry<String, String> entry = (Entry) iterator.next();
				postMethod.addParameter(entry.getKey(),entry.getValue());
			}
        }
        
        //执行，返回状态码
        int statusCode = 0;
        try {
        	httpClient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, charset);
        	httpClient.setTimeout(SC_OUTTIME);
        	httpClient.setConnectionTimeout(SC_OUTTIME);
            statusCode = httpClient.executeMethod(postMethod);
	        //针对状态码进行处理
	        if(statusCode == HttpStatus.SC_OK) {
	        	timeOutNum = 0;
	        	String response = postMethod.getResponseBodyAsString();
	        	//将html字符串转换为Docment
	        	document= Jsoup.parse(response,"",new Parser(new XmlTreeBuilder()));
	        }
        } catch (HttpException e) {
        	if(e.getMessage().toLowerCase().contains("timed out")){
        		if(timeOutNum<timeOutMaxNum){
        			if(timeOutNum==1){
        				logger.error(" request timed out(twice) :"+href);
        			}else if(timeOutNum==2){
        				logger.error(" request timed out(three) :"+href);
        			}
        			postReqSimulate(href, paramsMap,charset);
        			timeOutNum++;
        		}else{
        			timeOutNum = 0;
        			logger.error(href+":"+e);
        		}
        	}
        } catch (IOException e) {
        	logger.error(href+":"+e);
        }
        return document;
	}
	
	/**
	 * 根据列表页首页及post参数，通过模拟POST提交方式，返回Document
	 * @param href 
	 * @param paramsMap
	 * @return Document
	 */
	public static Document postReqSimulateByStream(String href,Map<String,String> paramsMap){
		Document document = null;
		//请求post方法
		PostMethod postMethod = new PostMethod(href);
		
		//post请求参数
		if(paramsMap!=null){
			for (Iterator iterator = paramsMap.entrySet().iterator(); iterator.hasNext();) {
				Entry<String, String> entry = (Entry) iterator.next();
				postMethod.addParameter(entry.getKey(),entry.getValue());
			}
		}
		
		//执行，返回状态码
		int statusCode = 0;
		try {
			httpClient.setTimeout(SC_OUTTIME);
			httpClient.setConnectionTimeout(SC_OUTTIME);
			statusCode = httpClient.executeMethod(postMethod);
			//针对状态码进行处理
			if(statusCode == HttpStatus.SC_OK) {
				timeOutNum = 0;
				InputStream response = postMethod.getResponseBodyAsStream();
				//将Stream转换为Docment
				document= Jsoup.parse(response,"UTF-8","",new Parser(new XmlTreeBuilder()));
				response.close();
			}
		} catch (HttpException e) {
			if(e.getMessage().toLowerCase().contains("timed out")){
				if(timeOutNum<timeOutMaxNum){
					if(timeOutNum==1){
        				logger.error(" request timed out(twice) :"+href);
        			}else if(timeOutNum==2){
        				logger.error(" request timed out(three) :"+href);
        			}
					postReqSimulateByStream(href, paramsMap);
					timeOutNum++;
				}else{
					timeOutNum = 0;
					logger.error(href+":"+e);
				}
			}
		} catch (IOException e) {
			logger.error(href+":"+e);
		}
		return document;
	}
	
	/**
	 * 根据列表页首页及post参数，通过模拟POST提交方式，返回Document
	 * @param href 
	 * @param paramsMap
	 * @param charset 字符编码
	 * @return Document
	 */
	public static Document postReqSimulateByStream(String href,Map<String,String> paramsMap,String charset){
		Document document = null;
		//请求post方法
		PostMethod postMethod = new PostMethod(href);
		
		//post请求参数
		if(paramsMap!=null){
			for (Iterator iterator = paramsMap.entrySet().iterator(); iterator.hasNext();) {
				Entry<String, String> entry = (Entry) iterator.next();
				postMethod.addParameter(entry.getKey(),entry.getValue());
			}
		}
		
		//执行，返回状态码
		int statusCode = 0;
		try {
			httpClient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, charset);
			httpClient.setTimeout(SC_OUTTIME);
			httpClient.setConnectionTimeout(SC_OUTTIME);
			statusCode = httpClient.executeMethod(postMethod);
			//针对状态码进行处理
			if(statusCode == HttpStatus.SC_OK) {
				timeOutNum = 0;
				InputStream response = postMethod.getResponseBodyAsStream();
				//将Stream转换为Docment
				document= Jsoup.parse(response,charset,"",new Parser(new XmlTreeBuilder()));
				response.close();
			}
		} catch (HttpException e) {
			if(e.getMessage().toLowerCase().contains("timed out")){
				if(timeOutNum<timeOutMaxNum){
					if(timeOutNum==1){
        				logger.error(" request timed out(twice) :"+href);
        			}else if(timeOutNum==2){
        				logger.error(" request timed out(three) :"+href);
        			}
					postReqSimulateByStream(href, paramsMap,charset);
					timeOutNum++;
				}else{
					timeOutNum = 0;
					logger.error(href+":"+e);
				}
			}
		} catch (IOException e) {
			logger.error(href+":"+e);
		}
		return document;

	}
	
	/**
	 * 根据列表页首页，通过模拟GET提交方式，返回Document
	 * @param href 
	 * @return Document
	 */
	@Deprecated
	public static Document getReqSimulate(String href){
			Document document = null;
	        //请求get方法
	        GetMethod getMethod = new GetMethod(href);
	              
	        //执行，返回状态码
	        int statusCode = 0;
	        try {
	        	httpClient.setTimeout(SC_OUTTIME);
	        	httpClient.setConnectionTimeout(SC_OUTTIME);
	            statusCode = httpClient.executeMethod(getMethod);
		        //针对状态码进行处理
		        if(statusCode == HttpStatus.SC_OK) {
		        	timeOutNum = 0;
		        	String response = getMethod.getResponseBodyAsString();
		        	//将html字符串转换为Docment
		        	document= Jsoup.parse(response,"",new Parser(new XmlTreeBuilder()));
		        }
	        } catch (HttpException e) {
	        	if(e.getMessage().toLowerCase().contains("timed out")){
	        		if(timeOutNum<timeOutMaxNum){
	        			if(timeOutNum==1){
	        				logger.error(" request timed out(twice) :"+href);
	        			}else if(timeOutNum==2){
	        				logger.error(" request timed out(three) :"+href);
	        			}
	        			getReqSimulate(href);
	        			timeOutNum++;
	        		}else{
	        			timeOutNum = 0;
	        			logger.error(href+":"+e);
	        		}
	        	}
	        } catch (IOException e) {
	        	logger.error(href+":"+e);
	        }finally{
	        	getMethod.releaseConnection();
	        }
	        return document;
	 }
	
	
	/**
	 * 根据列表页首页，通过模拟GET提交方式，返回Document
	 * @param href 
	 * @param charSet 字符编码
	 * @return Document
	 */
	@Deprecated
	public static Document getReqSimulate(String href,String charSet){
		Document document = null;
        //请求get方法
        GetMethod getMethod = new GetMethod(href);
              
        //执行，返回状态码
        int statusCode = 0;
        try {
        	httpClient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, charSet);
        	httpClient.setTimeout(SC_OUTTIME);
        	httpClient.setConnectionTimeout(SC_OUTTIME);
            statusCode = httpClient.executeMethod(getMethod);
	        //针对状态码进行处理
	        if(statusCode == HttpStatus.SC_OK) {
	        	timeOutNum = 0;
	        	String response = getMethod.getResponseBodyAsString();
	        	//将html字符串转换为Docment
	        	document= Jsoup.parse(response,"",new Parser(new XmlTreeBuilder()));
	        }
        } catch (HttpException e) {
        	if(e.getMessage().toLowerCase().contains("timed out")){
        		if(timeOutNum<timeOutMaxNum){
        			if(timeOutNum==1){
        				logger.error(" request timed out(twice) :"+href);
        			}else if(timeOutNum==2){
        				logger.error(" request timed out(three) :"+href);
        			}
        			getReqSimulate(href,charSet);
        			timeOutNum++;
        		}else{
        			timeOutNum = 0;
        			logger.error(href+":"+e);
        		}
        	}
        } catch (IOException e) {
        	logger.error(href+":"+e);
        }finally{
        	getMethod.releaseConnection();
        }
        return document;
	
	 }
	
	
	/**
	 * 根据列表页首页，通过模拟GET提交方式，返回Document
	 * @param href 
	 * @return Document
	 */
	public static Document getReqSimulateByStream(String href){
		
		Document document = null;
        //请求get方法
        GetMethod getMethod = new GetMethod(href);
              
        //执行，返回状态码
        int statusCode = 0;
        try {
        	httpClient.setTimeout(SC_OUTTIME);
        	httpClient.setConnectionTimeout(SC_OUTTIME);
            statusCode = httpClient.executeMethod(getMethod);
	        //针对状态码进行处理
	        if(statusCode == HttpStatus.SC_OK) {
	        	timeOutNum = 0;
	        	InputStream response = getMethod.getResponseBodyAsStream();
	        	//将String转换为Docment
	        	document = Jsoup.parse(response, "UTF-8", "", new Parser(new XmlTreeBuilder()));
	        }
        } catch (HttpException e) {
        	if(e.getMessage().toLowerCase().contains("timed out")){
        		if(timeOutNum<timeOutMaxNum){
        			if(timeOutNum==1){
        				logger.error(" request timed out(twice) :"+href);
        			}else if(timeOutNum==2){
        				logger.error(" request timed out(three) :"+href);
        			}
        			try {
						Thread.sleep(SC_OUTTIME);
						getReqSimulateByStream(href);
	        			timeOutNum++;
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
        		}else{
        			timeOutNum = 0;
        			logger.error(href+":"+e);
        		}
        	} 
        } catch (IOException e) {
        	logger.error(href+":"+e);
        }finally{
        	getMethod.releaseConnection();
        }
        
        return document;
	}
	
	
	/**
	 * 根据列表页首页，通过模拟GET提交方式，返回Document
	 * @param href 
	 * @param charSet 字符编码
	 * @return Document
	 */
	public static Document getReqSimulateByStream(String href,String charSet){
		Document document = null;
        //请求get方法
        GetMethod getMethod = new GetMethod(href);
              
        //执行，返回状态码
        int statusCode = 0;
        try {
    		httpClient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, charSet);
        	httpClient.setTimeout(SC_OUTTIME);
        	httpClient.setConnectionTimeout(SC_OUTTIME);
            statusCode = httpClient.executeMethod(getMethod);
	        //针对状态码进行处理
	        if(statusCode == HttpStatus.SC_OK) {
	        	timeOutNum = 0;
	        	InputStream response = getMethod.getResponseBodyAsStream();
	        	//将String转换为Docment
	        	document = Jsoup.parse(response, charSet, "", new Parser(new XmlTreeBuilder()));
	        }
        } catch (HttpException e) {
        	if(e.getMessage().toLowerCase().contains("timed out")){
        		if(timeOutNum<timeOutMaxNum){
        			if(timeOutNum==1){
        				logger.error(" request timed out(twice) :"+href);
        			}else if(timeOutNum==2){
        				logger.error(" request timed out(three) :"+href);
        			}
        			try {
						Thread.sleep(SC_OUTTIME);
						getReqSimulateByStream(href,charSet);
	        			timeOutNum++;
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
        		}else{
        			timeOutNum = 0;
        			logger.error(href+":"+e);
        		}
        	} 
        } catch (IOException e) {
        	logger.error(href+":"+e);
        }finally{
        	getMethod.releaseConnection();
        }
        
        return document;

	}
}
