package com.it.xushuai.baseapp.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
/** 
 * 字符串操作工具包
 * @author liux (http://my.oschina.net/liux)
 * @version 1.0
 * @created 2012-3-21
 */
public class StringUtils 
{
	private final static Pattern emailer = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
	
	/**
	 * 判断给定字符串是否空白串。
	 * 空白串是指由空格、制表符、回车符、换行符组成的字符串
	 * 若输入字符串为null或空字符串，返回true
	 * @param input
	 * @return boolean
	 */
	public static boolean isEmpty( String input ) 
	{
		if ( input == null || "".equals( input ) )
			return true;
		
		for ( int i = 0; i < input.length(); i++ ) 
		{
			char c = input.charAt( i );
			if ( c != ' ' && c != '\t' && c != '\r' && c != '\n' )
			{
				return false;
			}
		}
		return true;
	}

	/**
	 * 判断是不是一个合法的电子邮件地址
	 * @param email
	 * @return
	 */
	public static boolean isEmail(String email){
		if(email == null || email.trim().length()==0) 
			return false;
	    return emailer.matcher(email).matches();
	}
	/**
	 * 字符串转整数
	 * @param str
	 * @param defValue
	 * @return
	 */
	public static int toInt(String str, int defValue) {
		try{
			return Integer.parseInt(str);
		}catch(Exception e){}
		return defValue;
	}
	/**
	 * 对象转整数
	 * @param obj
	 * @return 转换异常返回 0
	 */
	public static int toInt(Object obj) {
		if(obj==null) return 0;
		return toInt(obj.toString(),0);
	}
	/**
	 * 对象转整数
	 * @param obj
	 * @return 转换异常返回 0
	 */
	public static long toLong(String obj) {
		try{
			return Long.parseLong(obj);
		}catch(Exception e){}
		return 0;
	}
	/**
	 * 字符串转布尔值
	 * @param b
	 * @return 转换异常返回 false
	 */
	public static boolean toBool(String b) {
		try{
			return Boolean.parseBoolean(b);
		}catch(Exception e){}
		return false;
	}
	
	/**
	 * Converts a Last.fm boolean result string to a boolean.
	 *
	 * @param resultString A Last.fm boolean result string.
	 * @return <code>true</code> if the given String represents a true, <code>false</code> otherwise.
	 */
	public static boolean convertToBoolean(String resultString) {
		return "1".equals(resultString);
	}

	/**
	 * Converts from a boolean to a Last.fm boolean result string.
	 * 
	 * @param value A boolean value.
	 * @return A string representing a Last.fm boolean.
	 */
	public static String convertFromBoolean(boolean value) {
		if (value) {
			return "1";
		} else {
			return "0";
		}
	}
	
	/**
	 * Creates a Map out of an array with Strings.
	 *
	 * @param strings input strings, key-value alternating
	 * @return a parameter map
	 */
	public static Map<String, String> map(String... strings) {
		if (strings.length % 2 != 0)
			throw new IllegalArgumentException("strings.length % 2 != 0");
		Map<String, String> mp = new HashMap<String, String>();
		for (int i = 0; i < strings.length; i += 2) {
			mp.put(strings[i], strings[i + 1]);
		}
		return mp;
	}
	
	/**
	 * URL Encodes the given String <code>s</code> using the UTF-8 character encoding.
	 *
	 * @param s a String
	 * @return url encoded string
	 */
	public static String encode(String s) {
		if(s == null)
			return null;
		try {
			return URLEncoder.encode(s, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// utf-8 always available
		}
		return null;
	}

	/**
	 * Decodes an URL encoded String <code>s</code> using the UTF-8 character encoding.
	 *
	 * @param s an encoded String
	 * @return the decoded String
	 */
	public static String decode(String s) {
		if(s == null)
			return null;
		try {
			return URLDecoder.decode(s, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// utf-8 always available
		}
		return null;
	}

	/**
	 * Strips all characters from a String, that might be invalid to be used in file names.
	 * By default <tt>: / \ < > | ? " *</tt> are all replaced by <tt>-</tt>.
	 * Note that this is no guarantee that the returned name will be definately valid.
	 *
	 * @param s the String to clean up
	 * @return the cleaned up String
	 */
	public static String cleanUp(String s) {
		return s.replaceAll("[*:/\\\\?|<>\"]", "-");
	}

}
