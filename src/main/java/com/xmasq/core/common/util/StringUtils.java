package com.xmasq.core.common.util;

import java.util.regex.Pattern;

/**
 * 字符串通用类，在common-lang的StringUtils基础上增加了业务上的通用类
 * 
 * @see org.apache.commons.lang.StringUtils
 * @author guoyu.huang
 * @version 1.0.0
 */
public class StringUtils extends org.apache.commons.lang.StringUtils {

	/** 正则表达式：验证密码 （字母数字6-16位） */
	private static final String REGEX_PASSWORD = "^[a-zA-Z0-9]{6,16}$";
	/** 正则表达式：验证手机号 （范围订广一点，只要是1开头，11位数即可） */
	private static final String REGEX_MOBILE = "^1\\d{10}$";
	/** 正则表达式：验证邮箱 */
	private static final String REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
	/** 正则表达式：验证身份证 */
	private static final String REGEX_ID_CARD = "(^\\d{18}$)|(^\\d{15}$)";
	/** 正则表达式：验证URL */
	private static final String REGEX_URL = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";
	/** 正则表达式：验证IP地址 */
	private static final String REGEX_IP_ADDR = "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)";
	/** 小数点 */
	private static final String DECIMAL_POINT = ".";
	/** IP段数，一共为4段 */
	private static final int IP_SEGMENT_LENGTH = 4;

	public static boolean isInteger(String value) {
		if (isNotBlank(value)) {
			if (value.indexOf(DECIMAL_POINT) >= 0) {
				value = value.substring(0, value.indexOf("."));
			}
			try {
				Integer.parseInt(value);
				return true;
			} catch (NumberFormatException var2) {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * 校验密码（字母数字任意组合6-16位）
	 * 
	 * <p>
	 * StringUtils.isPassword("012345") = true StringUtils.isPassword("aabbcc") =
	 * true StringUtils.isPassword("AABBCC") = true StringUtils.isPassword("aBc123")
	 * = true StringUtils.isPassword("a1234") = false
	 * StringUtils.isPassword("a1234567890123456") = false
	 * <p>
	 * 
	 * @param password
	 * @return 校验通过返回true，否则返回false
	 */
	public static boolean isPassword(String password) {
		return Pattern.matches(REGEX_PASSWORD, password);
	}

	/**
	 * 验证手机号 （范围订广一点，只要是1开头，11位数即可）
	 * 
	 * <p>
	 * StringUtils.isMobile("a1234567890") = false
	 * StringUtils.isMobile("12345678901") = true
	 * StringUtils.isMobile("23456789012") = false StringUtils.isMobile("123456") =
	 * false
	 * <p>
	 * 
	 * @param mobile
	 * @return 校验通过返回true，否则返回false
	 */
	public static boolean isMobile(String mobile) {
		return Pattern.matches(REGEX_MOBILE, mobile);
	}

	/**
	 * 验证邮箱
	 * 
	 * <p>
	 * StringUtils.isEmail("123456@163.com") = true
	 * StringUtils.isEmail("12345678901") = false
	 * <p>
	 * 
	 * @param email
	 * @return 校验通过返回true，否则返回false
	 */
	public static boolean isEmail(String email) {
		return Pattern.matches(REGEX_EMAIL, email);
	}

	/**
	 * 验证身份证
	 * 
	 * <p>
	 * StringUtils.isIdCard("a12345678901234") = false
	 * StringUtils.isIdCard("123456789012345") = true
	 * StringUtils.isIdCard("123456789012345678") = true
	 * StringUtils.isIdCard("1234567890123456") = false
	 * <p>
	 * 
	 * @param idCard
	 * @return 校验通过返回true，否则返回false
	 */
	public static boolean isIdCard(String idCard) {
		return Pattern.matches(REGEX_ID_CARD, idCard);
	}

	/**
	 * 验证URL（必须以http或者https开头）
	 * 
	 * <p>
	 * StringUtils.isUrl("https://www.imeng.com") = true
	 * StringUtils.isUrl("http://www.imeng.com") = true
	 * StringUtils.isUrl("www.imeng.com") = false
	 * StringUtils.isUrl("http://imeng.com") = true
	 * <p>
	 * 
	 * @param url
	 * @return 校验通过返回true，否则返回false
	 */
	public static boolean isUrl(String url) {
		return Pattern.matches(REGEX_URL, url);
	}

	/**
	 * 验证IP地址
	 * 
	 * <p>
	 * StringUtils.isIpAddr("255.255.255.255") = true
	 * StringUtils.isIpAddr("256.1.1.1") = false StringUtils.isIpAddr("0.1.1.1") =
	 * false StringUtils.isIpAddr("1.1.1") = false
	 * <p>
	 * 
	 * @param ipAddr
	 * @return 校验通过返回true，否则返回false
	 */
	public static boolean isIpAddr(String ipAddr) {
		String[] element = ipAddr.split("\\.");
		if (element.length != IP_SEGMENT_LENGTH) {
			return false;
		}
		for (String string : element) {
			if (string.length() == 1 && string.startsWith("0")) {
				return false;
			}
			if (!Pattern.matches(REGEX_IP_ADDR, string)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 获取长度，中文为2，英文数字为1
	 * 
	 * @param content
	 * @return
	 */
	public static int getLength(String content) {
		int length = 0;
		String chinese = "[\u4e00-\u9fa5]";
		for (int i = 0; i < content.length(); i++) {
			String temp = content.substring(i, i + 1);
			if (temp.matches(chinese)) {
				length += 2;
			} else {
				length += 1;
			}
		}
		return length;
	}

	/**
	 * 下划线转驼峰
	 *
	 * @param underlineString
	 * @return
	 */
	public static String underlineToHump(String underlineString){
		String[] strList = underlineString.split("_");
		String result = "";
		for(int i = 0; i < strList.length; i++){
			String str = strList[i].toLowerCase();
			if(i > 0){
				result += str.substring(0,1).toUpperCase() + str.substring(1);
			}else{
				result += str;
			}
		}
		return result;
	}

	public static void main(String[] args) {
		System.out.println(underlineToHump("CHECK_OBJEC_IS_NULL"));
	}
}