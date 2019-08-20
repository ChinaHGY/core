package com.xmasq.core.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期工具类，增加一些业务上常用的方法
 * 
 * @see org.apache.commons.lang.time.DateUtils
 * 
 * @author guoyu.huang
 * @version 1.0.0
 */
public class DateUtils extends org.apache.commons.lang.time.DateUtils {

	private final static String MIN_TIME = "00:00:00";
	private final static String MAX_TIME = "23:59:59";

	public static enum FormatType {
		/** 格式：yyyy-MM-dd HH:mm:ss */
		YYYY_MM_DD_HH_MM_SS("yyyy-MM-dd HH:mm:ss"),
		/** 格式：yyyy-MM-dd HH:mm */
		YYYY_MM_DD_HH_MM("yyyy-MM-dd HH:mm"),
		/** 格式：yyyy-MM-dd HH */
		YYYY_MM_DD_HH("yyyy-MM-dd HH"),
		/** 格式：yyyy-MM-dd */
		YYYY_MM_DD("yyyy-MM-dd"),
		/** 格式：yyyy-MM */
		YYYY_MM("yyyy-MM"),
		/** 格式：yyyy */
		YYYY("yyyy"),
		/** 格式：yyyyMMdd */
		YYYYMMDD("yyyyMMdd"),
		/** 格式：yyyyMM */
		YYYYMM("yyyyMM");

		private FormatType(String value) {
			this.value = value;
		}

		public String value;

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}
	}

	public static String getDateStr(Date date, FormatType formatType) {
		return getDateStr(date, formatType.getValue());
	}

	public static String getDateStr(Date date, String formatType) {
		if (date == null) {
			return "";
		} else {
			SimpleDateFormat sdf = new SimpleDateFormat(formatType);
			return sdf.format(date);
		}
	}

	public static String getCurrentDateStr(FormatType formatType) {
		SimpleDateFormat sdf = new SimpleDateFormat(formatType.getValue());
		return sdf.format(new Date());
	}

	public static Date getUniqueTime() {
		return new Date();
	}

	public static Date parseDate(String dateStr, FormatType type) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(type.getValue());
			return sdf.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取date这个日期的最小时间
	 * <p>
	 * 例如：date：2018-03-02 08:35:00，则返回2018-03-02 00:00:00
	 * <p>
	 * 
	 * @param date
	 * @return
	 */
	public static Date getMinDate(Date date) {
		return getSpecialDate(date, MIN_TIME);
	}

	/**
	 * 获取date这个日期的最大时间
	 * <p>
	 * 例如：date：2018-03-02 08:35:00，则返回2018-03-02 23:59:59
	 * <p>
	 * 
	 * @param date
	 * @return
	 */
	public static Date getMaxDate(Date date) {
		return getSpecialDate(date, MAX_TIME);
	}

	/**
	 * 获取指定日期date，指定时间
	 * <p>
	 * 例如：date：2018-03-02 08:35:00，timeStr:12:12:12，则返回2018-03-02 12:12:12
	 * <p>
	 * 
	 * @param date
	 * @param timeStr
	 * @return
	 */
	public static Date getSpecialDate(Date date, String timeStr) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(FormatType.YYYY_MM_DD.value);
		String dateStr = simpleDateFormat.format(date);
		dateStr = dateStr + " " + timeStr;
		return parseDate(dateStr, FormatType.YYYY_MM_DD_HH_MM_SS);
	}

	/**
	 * 获取两个时间的相差天数
	 * 
	 * @param minDate
	 * @param maxDate
	 * @return
	 */
	public static int getDay(Date minDate, Date maxDate) {
		long daySec = 1000 * 60 * 60 * 24;
		int day = (int) ((maxDate.getTime() - minDate.getTime()) / daySec) + 1;
		return day;
	}

}
