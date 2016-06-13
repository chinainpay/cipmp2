package com.chinainpay.apps.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	private static SimpleDateFormat format = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	// 输出结果：
	// timeStamp=1417792627
	// date=2014-12-05 23:17:07
	// 1417792627
	public static void main(String[] args) {
		String timeStamp = timeStamp();
		System.out.println("timeStamp=" + timeStamp);

		String date = timeStamp2Date(timeStamp, "yyyy-MM-dd HH:mm:ss");
		System.out.println("date=" + date);

		String timeStamp2 = date2TimeStamp(date);
		System.out.println(timeStamp2);
	}

	/**
	 * 时间戳转换成日期格式字符串
	 * 
	 * @param seconds
	 *            精确到秒的字符串
	 * @param formatStr
	 * @return
	 */
	public static String timeStamp2Date(String seconds, String format) {
		if (seconds == null || seconds.isEmpty() || seconds.equals("null")) {
			return "";
		}
		if (format == null || format.isEmpty())
			format = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(new Date(Long.valueOf(seconds + "000")));
	}

	/**
	 * 日期格式字符串转换成时间戳
	 * 
	 * @param date
	 *            字符串日期
	 * @param format
	 *            如：yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String date2TimeStamp(String date_str) {
		try {
			Date date=format.parse(date_str);
			return String.valueOf(date.getTime() / 1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 取得当前时间戳（精确到秒）
	 * 
	 * @return
	 */
	public static String timeStamp() {
		long time = System.currentTimeMillis();
		String t = String.valueOf(time / 1000);
		return t;
	}
	
	/**
	 * 往后加几天
	 * @param tianShu
	 * @return
	 */
	public static String dateAdd(int tianShu){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();//使用默认时区和语言环境获得一个日历。    
		cal.add(Calendar.DAY_OF_MONTH, +tianShu);//取当前日期的后一天. 
		String returnTime=format.format(cal.getTime());  
		return returnTime;
	}
	
	/**
	 * 往后加几天
	 * @param tianShu
	 * @return
	 */
	public static String dateJian(int tianShu){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();//使用默认时区和语言环境获得一个日历。    
		cal.add(Calendar.DAY_OF_MONTH, -tianShu);//取当前日期的后一天. 
		String returnTime=format.format(cal.getTime());  
		return returnTime;
	}
	
	/**
	 * 数据库日期和当前日期比较
	 * 查看数据库实际是否大于当前日期
	 * true 为小于当前时间
	 * false为大于当前时间
	 * @param time
	 * @return
	 * @throws ParseException 
	 */
	public static boolean compareToCurrentTime(String time) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String s1=time;
		String s2 = sdf.format(new Date());
		Date date1 = sdf.parse(s1);
		long dateTime1=date1.getTime();
		Date date2 = sdf.parse(s2);
		long dateTime2=date2.getTime();
		boolean result=dateTime1<dateTime2;
		return result;
	}
}
