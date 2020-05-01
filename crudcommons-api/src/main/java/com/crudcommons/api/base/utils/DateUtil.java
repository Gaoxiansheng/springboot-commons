package com.crudcommons.api.base.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * 时间日期工具类
 * @author macx
 * @date 2017年4月18日
 */
public class DateUtil {

    /** 时间日期格式化字符串 */
    private static final String FORMAT_STR = "0000-00-00 00:00";
    
    /** 标准的19位日期时间格式字符串 */
    public static final String FORMAT_STANDARD_19 = "yyyy-MM-dd HH:mm:ss";
    
    /** 标准的17位日期时间格式字符串 */
    public static final String FORMAT_STANDARD_16 = "yyyy-MM-dd HH:mm";    
    
    /** 标准日期格式字符串*/
    public static final String FORMAT_DATE = "yyyy-MM-dd";  
    
    /** 线程安全的多线程缓存日期格式转换对象 */
    private static ThreadLocal<SimpleDateFormat> threadLocal = new ThreadLocal<SimpleDateFormat>(){
        protected synchronized SimpleDateFormat initialValue() {
            return new SimpleDateFormat(FORMAT_STANDARD_19);
        }
    };
    
    /**创建java.sql.Timestamp对象
     * @return Timestamp对象
     */
    public static Timestamp getTimestamp(){
    	Date d = new Date();
    	return getTimestamp(d);
    }
    
    /**创建java.sql.Timestamp对象
     * @param d 日期对象
     * @return Timestamp对象
     */
    public static Timestamp getTimestamp(Date d){
    	Timestamp time = new Timestamp(d.getTime());
    	return time;
    }
    
    /**
     * 将日期转换为标准的19位日期格式字符串
     * @param date 要转换的日期
     * @return 转换成的日期格式字符串（如果date为null，那么结果是""）
     */
    public static String getStandard19DateAndTime(Date date){
        String result = "";
        if(date!=null){
            result = threadLocal.get().format(date);
        }
        return result;
    }
    
    /**
     * 将标准的19为日期格式字符串转换成日期对象
     * @param dateStr 要转换的日期字符串
     * @return 转换后的日期
     */
    public static Date getByStandard19DateAndTime(String dateStr){
        try {   
            return threadLocal.get().parse(dateStr);   
        } catch (ParseException e) {   
            System.out.println("日期格式转换出现异常："+e);
        }   
        return null; 
    }

    /**
     * 获取当前系统日期
     * @return 日期字符串
     */
    public static String getDate() {
        return getDate("yyyy-MM-dd");
    }

    /**
     * 获取指定输出格式的当前系统日期
     * @param format 格式字符串
     * @return 当前系统日期
     */
    public static String getDate(String format) {
        String date = "";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            date = sdf.format(new Date());
        } catch(Exception e) {
            System.out.println("日期格式转换出现异常："+e);
        }
        return date;
    }

    /**
     * 将日期按指定的格式字符串格式化
     * @param date 日期
     * @param format 格式化字符串
     * @return 日期字符串
     */
    public static String getDate(Date date, String format) {
        String rs = "";
        if(date != null) {
            SimpleDateFormat f = new SimpleDateFormat(format);
            try {
                rs = f.format(date);
            } catch(Exception e) {
                rs = FORMAT_STR;
            }
        }
        return rs;
    }

    /**
     * 得到指定日期的格式化字符串
     * @param d 日期
     * @return 格式化日期
     */
    public static String getDate(Date d) {
        String date = "";
        if(d!=null){
            DateFormat df = DateFormat.getDateInstance();
            try {
                date = df.format(d);
            } catch(Exception e) {
                System.out.println("日期格式转换出现异常："+e);
            }
        }
        return date;
    }

    /**
     * 获取指定日期的中文显示名
     * @param d 日期
     * @return 中文日期
     */
    public static String getLocalDate(Date d) {
        String date = "";
        if(d!=null){
            Locale l = new Locale("zh", "CN");
            DateFormat df = DateFormat.getDateInstance(DateFormat.FULL, l);
            try {
                date = df.format(d);
            } catch(Exception e) {
                System.out.println("日期格式转换出现异常："+e);
            }
        }
        return date;
    }

    /**
     * 获取当前日期的中文显示名
     * @return 中文显示名
     */
    public static String getLocalDate() {
        String date = "";
        Locale l = new Locale("zh", "CN");
        DateFormat df = DateFormat.getDateInstance(DateFormat.FULL, l);
        try {
            date = df.format(new Date());
        } catch(Exception e) {
            System.out.println("日期格式转换出现异常："+e);
        }
        return date;
    }

    /**
     * 获取当前时间的中文显示名
     * @return 中文显示时间
     */
    public static String getLocalTime() {
        Locale l = new Locale("zh", "CN");
        DateFormat df = DateFormat.getTimeInstance(DateFormat.LONG, l);
        String time = "";
        try {
            time = df.format(new Date());
        } catch(Exception e) {
            System.out.println("日期格式转换出现异常："+e);
        }
        return time;
    }

    /**
     * 获取指定时间的中文显示名
     * @param d 时间
     * @return 中文时间
     */
    public static String getLocalTime(Date d) {
        Locale l = new Locale("zh", "CN");
        DateFormat df = DateFormat.getTimeInstance(DateFormat.LONG, l);
        String time = "";
        try {
            time = df.format(d);
        } catch(Exception e) {
            System.out.println("日期格式转换出现异常："+e);
        }
        return time;
    }

    /**
     * 获取格式化时间
     * @param d 时间
     * @return 格式化时间
     */
    public static String getTime(Date d) {
        String time = "";
        if(d!=null){
            DateFormat df = DateFormat.getTimeInstance();
            try {
                time = df.format(d);
            } catch(Exception e) {
                System.out.println("日期格式转换出现异常："+e);
            }
        }
        return time;
    }

    /**
     * 获取系统时间的格式化时间
     * @return 格式化时间
     */
    public static String getTime() {
        DateFormat df = DateFormat.getTimeInstance();
        String time = "";
        try {
            time = df.format(new Date());
        } catch(Exception e) {
            System.out.println("日期格式转换出现异常："+e);
        }
        return time;
    }

    /**
     * 得到以yyyy-MM-dd HH:mm:ss显示的当前系统时间
     * @return 系统时间字符串
     */
    public static String get19DateAndTime() {
        return getDate("yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 获取系统时间的格式化日期与时间
     * @return 格式化日期与时间
     */
    public static String getDateAndTime() {
//        String date = "";
//        DateFormat df = DateFormat.getDateTimeInstance();
//        try {
//            date = df.format(new Date());
//        } catch(Exception e) {
//            System.out.println("日期格式转换出现异常："+e);
//        }
//        return date;
        return getDate();
    }

    /**
     * 获取指定Date的格式化日期与时间
     * @param d 时间
     * @return 格式化日期与时间
     */
    public static String getDateAndTime(Date d) {
        return getDate(d, FORMAT_STANDARD_19);
    }
    
    /**
     * 获取指定Date的格式化日期与时间
     * @param d 时间
     * @return 格式化日期与时间
     */
    public static String getDateAndTime16(Date d) {
        return getDate(d, FORMAT_STANDARD_16);
    }

    /**
     * 获取系统时间的格式化日期与时间
     * @return 中文日期与时间
     */
    public static String getLocalDateAndTime() {
        Locale l = new Locale("zh", "CN");
        DateFormat df = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.SHORT, l);
        String dateAndTime = "";
        try {
            dateAndTime = df.format(new Date());
        } catch(Exception e) {
            System.out.println("日期格式转换出现异常："+e);
        }
        return dateAndTime;
    }

    /**
     * 获取指定Date的中文日期与时间
     * @param d 时间
     * @return 中文日期与时间
     */
    public static String getLocalDateAndTime(Date d) {
        String dateAndTime = "";
        if(d!=null){
            Locale l = new Locale("zh", "CN");
            DateFormat df = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.SHORT, l);
            try {
                dateAndTime = df.format(d);
            } catch(Exception e) {
                System.out.println("日期格式转换出现异常："+e);
            }
        }
        return dateAndTime;
    }

    /**
     * 得到当前年
     * @return 年
     */
    public static int getYear() {
        return new GregorianCalendar().get(Calendar.YEAR);
    }
    
    /**根据日期对象得到当前年
     * @param d 日期对象
     * @return 年
     */
    public static int getYear(Date d) {
    	Calendar c = Calendar.getInstance();
    	c.setTime(d);
        return c.get(Calendar.YEAR);
    }

    /**
     * 得到当前月
     * @return 月
     */
    public static int getMonth() {
        return new GregorianCalendar().get(Calendar.MONTH) + 1;
    }
    
    /**根据日期对象得到当前月份
     * @param d 日期对象
     * @return 月份
     */
    public static int getMonth(Date d) {
    	Calendar c = Calendar.getInstance();
    	c.setTime(d);
        return c.get(Calendar.MONTH)+1;
    }

    /**
     * 得到当前日
     * @return 日
     */
    public static int getDay() {
        return new GregorianCalendar().get(Calendar.DAY_OF_MONTH);
    }
    
    /**根据日期对象得到当前日
     * @param d 日期对象
     * @return 当前月份对应的日
     */
    public static int getDay(Date d) {
    	Calendar c = Calendar.getInstance();
    	c.setTime(d);
        return c.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 将字符串转换为指定格式的时间
     * @param date 时间字符串
     * @param pattern 格式
     * @return 时间对象
     */
    public static Date toDate(String date, String pattern) {
        if(date!=null){
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            try {
                return sdf.parse(date);
            } catch(Exception e) {
                System.out.println("日期格式转换出现异常："+e);
            }
        }
        return null;
    }
}
