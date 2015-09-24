package com.thd.base.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;

import org.apache.log4j.Logger;

/**
 * @Author:jilongliang
 * @Date :2012-8-19
 * @Project:JAVA7
 * @Description:时间处理类
 */
@SuppressWarnings("all")
public class DateUtil {
	static Logger logger = LoggerUtil.getLogger();

	public static Date calculateStartOfTomorrow(Date taskCrtDttm) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(taskCrtDttm);
		cal.add(Calendar.DATE, 1);
		return calculateStartOfDay(cal.getTime());
	}

	public static Date calculateStartOfDay(Date taskCrtDttm) {
		SimpleDateFormat sdf = new SimpleDateFormat(Constant.DATE_PATTERN_yyyy_MM_dd);
		try {
			return sdf.parse(sdf.format(taskCrtDttm) + Constant.START_TIME);
		} catch (ParseException e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}

	/**
	 * 字符串转换为java.util.Date<br>
	 * 支持格式为 yyyy.MM.dd G 'at' hh:mm:ss z Example:'2002-1-1 AD at 22:10:59 PSD'<br>
	 * yy/MM/dd HH:mm:ss 如 '2002/1/1 17:55:00'<br>
	 * yy/MM/dd HH:mm:ss pm 如 '2002/1/1 17:55:00 pm'<br>
	 * yy-MM-dd HH:mm:ss 如 '2002-1-1 17:55:00' <br>
	 * yy-MM-dd HH:mm:ss am 如 '2002-1-1 17:55:00 am' <br>
	 * 
	 * @param time
	 *            String 字符串<br>
	 * @return Date 日期<br>
	 */
	public static Date getStringToDate(String time) {
		SimpleDateFormat formatter;
		int tempPos = time.indexOf("AD");
		time = time.trim();
		formatter = new SimpleDateFormat("yyyy.MM.dd G 'at' hh:mm:ss z");
		if (tempPos > -1) {
			time = time.substring(0, tempPos) + "公元" + time.substring(tempPos + "AD".length());// china
			formatter = new SimpleDateFormat("yyyy.MM.dd G 'at' hh:mm:ss z");
		}
		tempPos = time.indexOf("-");
		if (tempPos > -1 && (time.indexOf(" ") < 0)) {
			formatter = new SimpleDateFormat("yyyyMMddHHmmssZ");
		} else if ((time.indexOf("/") > -1) && (time.indexOf(" ") > -1)) {
			formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		} else if ((time.indexOf("-") > -1) && (time.indexOf(" ") > -1)) {
			formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		} else if ((time.indexOf("/") > -1) && (time.indexOf("am") > -1) || (time.indexOf("pm") > -1)) {
			formatter = new SimpleDateFormat("yyyy-MM-dd KK:mm:ss a");
		} else if ((time.indexOf("-") > -1) && (time.indexOf("am") > -1) || (time.indexOf("pm") > -1)) {
			formatter = new SimpleDateFormat("yyyy-MM-dd KK:mm:ss a");
		}
		ParsePosition pos = new ParsePosition(0);
		Date date = formatter.parse(time, pos);
		return date;
	}

	/**
	 * 将java.util.Date 格式转换为字符串格式'yyyy-MM-dd HH:mm:ss'(24小时制)<br>
	 * 如Sat May 11 17:24:21 CST 2002 to '2002-05-11 17:24:21'<br>
	 * 
	 * @param time
	 *            Date 日期<br>
	 * @return String 字符串<br>
	 */
	public static String getDateToString(Date time) {
		SimpleDateFormat formatter;
		formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String Time = formatter.format(time);
		return Time;
	}

	/**
	 * 将java.util.Date 格式转换为字符串格式'yyyy-MM-dd HH:mm:ss a'(12小时制)<br>
	 * 如Sat May 11 17:23:22 CST 2002 to '2002-05-11 05:23:22 下午'<br>
	 * 
	 * @param time
	 *            Date 日期<br>
	 * @param x
	 *            int 任意整数如：1<br>
	 * @return String 字符串<br>
	 */
	public static String getDateToString(Date time, int x) {
		SimpleDateFormat formatter;
		formatter = new SimpleDateFormat("yyyy-MM-dd KK:mm:ss a");
		String date = formatter.format(time);
		return date;
	}

	/**
	 * 取系统当前时间:返回只值为如下形式 2002-10-30 20:24:39
	 * 
	 * @return String
	 */
	public static String getNow() {
		return getDateToString(new Date());
	}

	/**
	 * 取系统当前时间:返回只值为如下形式 2002-10-30 08:28:56 下午
	 * 
	 * @param hour
	 *            为任意整数
	 * @return String
	 */
	public static String getNow(int hour) {
		return getDateToString(new Date(), hour);
	}

	/**
	 * 获取小时
	 * 
	 * @return
	 */
	public static String getHour() {
		SimpleDateFormat formatter;
		formatter = new SimpleDateFormat("H");
		String hour = formatter.format(new Date());
		return hour;
	}

	/**
	 * 获取当前日日期返回 <return>Day</return>
	 */
	public static String getDay() {
		SimpleDateFormat formatter;
		formatter = new SimpleDateFormat("d");
		String day = formatter.format(new Date());
		return day;
	}

	/**
	 * 获取周
	 * @return
	 */
	public static String getWeek() {
		SimpleDateFormat formatter;
		formatter = new SimpleDateFormat("E");
		String week = formatter.format(new Date());
		return week;
	}

	/**
	 * 获取月份
	 * 
	 * @return
	 */
	public static String getMonth() {
		SimpleDateFormat formatter;
		formatter = new SimpleDateFormat("M");
		String month = formatter.format(new Date());
		return month;
	}

	/**
	 * 获取年
	 * 
	 * @return
	 */
	public static String getYear() {
		SimpleDateFormat formatter;
		formatter = new SimpleDateFormat("yyyy");
		String year = formatter.format(new Date());
		return year;
	}

	/**
	 * 对日期格式的转换成("yyyy-MM-dd)格式的方法
	 * 
	 * @param str
	 * @return
	 */
	public static java.sql.Date Convert(String str) {
		SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
		try {
			java.util.Date d = sdf.parse(str);
			java.sql.Date d1 = new java.sql.Date(d.getTime());
			return d1;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	/**
	 * 获取当前年、月、日：
	 * 
	 * @return
	 */
	public static String getYYMMDD() {
		Date date = new Date();
		int year = date.getYear() + 1900;// thisYear = 2003
		int month = date.getMonth() + 1;// thisMonth = 5
		int day = date.getDate();// thisDate = 30

		return "" + year + month + day;
	}

	/**
	 * 取系统当前时间:返回值为如下形式 2002-10-30
	 * 
	 * @return String
	 */
	public static String getYYYY_MM_DD() {
		return getDateToString(new Date()).substring(0, 10);
	}

	/**
	 * 取系统给定时间:返回值为如下形式 2002-10-30
	 * 
	 * @return String
	 */
	public static String getYYYY_MM_DD(String date) {
		return date.substring(0, 10);
	}

	/**
	 * 在jsp页面中的日期格式和sqlserver中的日期格式不一样，怎样统一? 
	 * 在页面上显示输出时，用下面的函数处理一下
	 * 
	 * @param date
	 * @return
	 */
	public static String getFromateDate(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
		String strDate = formatter.format(date);
		return strDate;
	}

	/**
	 * 获取当前时间是本年的第几周
	 * @return
	 */
	public static String getWeeK_OF_Year() {
		Calendar cal = Calendar.getInstance();
		Date date = new Date();
		cal.setTime(date);
		int week = cal.get(Calendar.WEEK_OF_YEAR);
		return "当日是本年的第" + week + "周";
	}

	/**
	 * 获取当日是本年的的第几天
	 * @return
	 */
	public static String getDAY_OF_YEAR() {
		Calendar cal = Calendar.getInstance();
		Date date = new Date();
		cal.setTime(date);
		int day = cal.get(Calendar.DAY_OF_YEAR);
		return "当日是本年的第" + day + "天";
	}

	/**
	 * 获取本周是在本个月的第几周
	 * @return
	 */
	public static String getDAY_OF_WEEK_IN_MONTH() {
		Calendar cal = Calendar.getInstance();
		Date date = new Date();
		cal.setTime(date);
		/*
		 * 这里这个值可以完全看JDK里面调用一下 或者点一下调用运行看看结果,看看里面的 English说明就知道它是干嘛的
		 */
		int week = cal.get(Calendar.DAY_OF_WEEK_IN_MONTH);
		return "本月第" + week + "周";
	}

	/**
	 *阳历转阴历农历:http://zuoming.iteye.com/blog/1554001
	 * GregorianCalendar使用： http://zmx.iteye.com/blog/409465
	 * GregorianCalendar 类提供处理日期的方法。
	 * 一个有用的方法是add().使用add()方法，你能够增加象年
	 * 月数，天数到日期对象中。要使用add()方法，你必须提供要增加的字段
	 * 要增加的数量。一些有用的字段是DATE, MONTH, YEAR, 和 WEEK_OF_YEAR
	 * 下面的程序使用add()方法计算未来80天的一个日期.
	 * 在Jules的<环球80天>是一个重要的数字，使用这个程序可以计算
	 * Phileas Fogg从出发的那一天1872年10月2日后80天的日期：
	 */
	public static void getGregorianCalendarDate() {
		GregorianCalendar worldTour = new GregorianCalendar(1872, Calendar.OCTOBER, 2);
		worldTour.add(GregorianCalendar.DATE, 80);
		Date d = worldTour.getTime();
		DateFormat df = DateFormat.getDateInstance();
		String s = df.format(d);
		System.out.println("80 day trip will end " + s);
	}

	/**
	 * 用来处理时间转换格式的方法
	 * @param formate
	 * @param time
	 * @return
	 */
	private static String getConvertDateFormat(String formate, Date time) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(formate);
		String date = dateFormat.format(time);
		return date;
	}

	/** 
	* 获得指定日期的前一天 
	* @param specifiedDay 
	* @return 
	* @throws Exception 
	*/
	public static String getSpecifiedDayBefore(String specifiedDay) {
		// SimpleDateFormat simpleDateFormat = new
		// SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		Date date = null;
		try {
			date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		c.setTime(date);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day - 1);

		String dayBefore = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
		return dayBefore;
	}

	/** 
	* 获得指定日期的后一天 
	* @param specifiedDay 
	* @return 
	*/
	public static String getSpecifiedDayAfter(String specifiedDay) {
		Calendar c = Calendar.getInstance();
		Date date = null;
		try {
			date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		c.setTime(date);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day + 1);

		String dayAfter = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
		return dayAfter;
	}

	/** 
	* 获得指定日期的前N天 的日期
	* @param specifiedDay 
	* @return 
	* @throws Exception 
	*/
	public static String getSpecifiedDayByNum(String specifiedDay, int num) {
		Calendar c = Calendar.getInstance();
		Date date = null;
		try {
			date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		c.setTime(date);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day + num);

		String dayBefore = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
		return dayBefore;
	}

	/**  
	 * 得到本月的第一天  
	 * @return  
	 */
	public static String getCurrentMonthFirstDay() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		return getConvertDateFormat("yyyy-MM-dd", calendar.getTime());
	}

	/**  
	 * 得到本月的最后一天  
	 *   
	 * @return  
	 */
	public static String getCurrentMonthLastDay() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		return getConvertDateFormat("yyyy-MM-dd", calendar.getTime());
	}

	/**
	 * 
	 * 获取上个月的第一天
	 * 
	 * @return
	 */
	public static String getBeforeMonthFirstDay() {
		Calendar cal = Calendar.getInstance();
		Date date = new Date();
		cal.setTime(date);
		int year = 0;
		int month = cal.get(Calendar.MONTH); // 上个月月份
		int day = cal.getActualMinimum(Calendar.DAY_OF_MONTH);// 起始天数

		if (month == 0) {
			year = cal.get(Calendar.YEAR) - 1;
			month = 12;
		} else {
			year = cal.get(Calendar.YEAR);
		}
		String endDay = year + "-" + month + "-" + day;
		return endDay;
	}

	/**
	 * 获取上个月的最一天
	 * 
	 * @return
	 */
	public static String getBeforeMonthLastDay() {
		// 实例一个日历单例对象
		Calendar cal = Calendar.getInstance();
		Date date = new Date();
		cal.setTime(date);
		int year = 0;
		int month = cal.get(Calendar.MONTH); // 上个月月份
		// int day1 = cal.getActualMinimum(Calendar.DAY_OF_MONTH);//起始天数
		int day = cal.getActualMaximum(Calendar.DAY_OF_MONTH); // 结束天数

		if (month == 0) {
			// year = cal.get(Calendar.YEAR) - 1;
			month = 12;
		} else {
			year = cal.get(Calendar.YEAR);
		}
		String endDay = year + "-" + month + "-" + day;
		return endDay;
	}

	/**
	 * 
	 * 获取下月的第一天
	 * 
	 * @return
	 */
	public static String getNextMonthFirstDay() {
		Calendar cal = Calendar.getInstance();
		Date date = new Date();
		cal.setTime(date);
		int year = 0;
		int month = cal.get(Calendar.MONDAY) + 2; // 下个月月份
		/*
		 * 如果是这样的加一的话代表本月的第一天 int month = cal.get(Calendar.MONDAY)+1; int month
		 * = cal.get(Calendar.MONTH)+1
		 */
		int day = cal.getActualMinimum(Calendar.DAY_OF_MONTH);// 起始天数

		if (month == 0) {
			year = cal.get(Calendar.YEAR) - 1;
			month = 12;
		} else {
			year = cal.get(Calendar.YEAR);
		}
		String Day = year + "-" + month + "-" + day;
		return Day;
	}

	/**
	 * 获取下个月的最一天
	 * 
	 * @return
	 */
	public static String getNextMonthLastDay() {
		// 实例一个日历单例对象
		Calendar cal = Calendar.getInstance();
		Date date = new Date();
		cal.setTime(date);
		int year = 0;
		int month = cal.get(Calendar.MONDAY) + 2; // 下个月份
		// int day1 = cal.getActualMinimum(Calendar.DAY_OF_MONTH);//起始天数
		int day = cal.getActualMaximum(Calendar.DAY_OF_MONTH); // 结束天数

		if (month == 0) {
			// year = cal.get(Calendar.YEAR) - 1;
			month = 12;
		} else {
			year = cal.get(Calendar.YEAR);
		}
		String endDay = year + "-" + month + "-" + day;
		return endDay;
	}

	/**
	 * 本地时区输出当前日期 GMT时间
	 */
	public static String getLocalDate() {
		Date date = new Date();
		return date.toLocaleString();// date.toGMTString();
	}

	/**
	 * 判断客户端输入的是闰年Leap还是平年Average
	 * @return
	 */
	public static String getLeapOrAverage(int year) {

		if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
			return year + "闰年";
		} else {
			return year + "平年";
		}
	}

	/**
	 * 数字不足位数左补0
	 * 
	 * @param str
	 *            源字符串
	 * @param len
	 *            目标长度
	 */
	public static String padLeft(String str, int len) {
		int strLen = str.length();
		if (strLen < len) {
			while (strLen < len) {
				StringBuffer sb = new StringBuffer();
				sb.append("0").append(str);// 左补0
				// sb.append(str).append("0");//右补0
				str = sb.toString();
				strLen = str.length();
			}
		}
		return str;
	}

	/**
	 * 当前yyyyMMdd+数字不足位数左补0
	 * 
	 * @param str
	 *            源字符串
	 * @param len
	 *            目标长度
	 */
	public static String padLeftAddDate(String str, int len) {
		int strLen = str.length();
		if (strLen < len) {
			while (strLen < len) {
				StringBuffer sb = new StringBuffer();
				sb.append("0").append(str);// 左补0
				// sb.append(str).append("0");//右补0
				str = sb.toString();
				strLen = str.length();
			}
		}
		str = getYYMMDD() + str;
		return str;
	}

	/**
	 * 数字不足位数右补0
	 * 
	 * @param str
	 *            源字符串
	 * @param len
	 *            目标长度
	 */
	public static String padRight(String str, int len) {
		int strLen = str.length();
		if (strLen < len) {
			while (strLen < len) {
				StringBuffer sb = new StringBuffer();
				// sb.append("0").append(str);//左补0
				sb.append(str).append("0");// 右补0
				str = sb.toString();
				strLen = str.length();
			}
		}
		return str;
	}

	/**
	* 返回一个随机数
	* 
	* @param i
	* @return
	*/
	public static String getRandom(int i) {
		Random jjj = new Random();
		// int suiJiShu = jjj.nextInt(9);
		if (i == 0) {
			return "";
		}
		String jj = "";
		for (int k = 0; k < i; k++) {
			jj = jj + jjj.nextInt(9);
		}
		return jj;
	}

	/**
	 * 返回一个 相差几个月的时间字符串
	 * @param date 时间
	 * @param num 相差几个月
	 * @return
	 */
	public static String getDateByNumMonth(Date date, int num) {
		Calendar c = Calendar.getInstance();// 获得一个日历的实例
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd ");
		c.setTime(date);// 设置日历时间
		c.add(Calendar.MONTH, num);// 在日历的月份上增加6个月
		return sdf.format(c.getTime());
	}

	public static String getOrderNo() {
		long No = 0;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String nowdate = sdf.format(new Date());
		No = Long.parseLong(nowdate) * 1000;// 这里如果一天订单多的话可以用一万或更大
		return No + "";
	}

	/**
	 * 测试
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		int i = getMonths("2012-11-23", "2013-2-13");
		System.out.println(i);
	}

	/**
	 * 一个日期加上多少分钟，算出之后的日期,返回的是String类型
	 */
	public static String addMinutesToDate(String timeStr, int minutes) throws ParseException {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		long timeLong = 0;
		long timeAdded = 0;

		date = df.parse(timeStr);
		timeLong = date.getTime();
		System.out.println("long:" + timeLong);
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MINUTE, minutes);
		timeAdded = c.getTimeInMillis();
		System.out.println("Added time:" + c.getTime());
		System.out.println("Added 40 minutes:" + timeAdded);
		String dayBefore = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(c.getTime());
		String dayAfter = new SimpleDateFormat("yyyy-MM-dd").format(timeAdded);
		System.out.println("---要返回的数据：dayBefore=" + dayBefore);
		return dayBefore;
	}

	/**
	 * 获取两个时间之间的月份数
	 * @param beginDate 2012-02-03/2012-2-3
	 * @param endDate 2012-05-30/2012-5-30
	 * @return
	 */
	public static Integer getMonths(String beginDate, String endDate) {
		int months = 0;
		int beginYear = Integer.parseInt(beginDate.substring(0, 4));
		int endYear = Integer.parseInt(endDate.substring(0, 4));
		int beginMonth = 0;
		if (beginDate.substring(5, 7).indexOf("-") == -1) {
			beginMonth = Integer.parseInt(beginDate.substring(5, 7));
		} else {
			beginMonth = Integer.parseInt(beginDate.substring(5, 6));
		}
		int endMonth = 0;
		if (endDate.substring(5, 7).indexOf("-") == -1) {
			endMonth = Integer.parseInt(endDate.substring(5, 7));
		} else {
			endMonth = Integer.parseInt(endDate.substring(5, 6));
		}

		int years = endYear - beginYear;
		if (years == 0) {
			months = endMonth - beginMonth + 1;
		} else {
			months = 12 * years + endMonth - beginMonth + 1;
		}
		return months;
	}
}