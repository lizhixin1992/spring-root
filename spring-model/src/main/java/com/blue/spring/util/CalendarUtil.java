package com.blue.spring.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class CalendarUtil {
    private static final Logger log = LoggerFactory.getLogger(CalendarUtil.class);
    public static final String CREDIT_CARD_DATE_FORMAT = "MM/yyyy";
    public static final String SHORT_DATE_FORMAT = "yyyy-MM-dd";
    public static final String SHORT_DATE_FORMAT_YYYY_MM = "yyyy-MM";
    public static final String SHORT_DATE_DOT_FORMAT = "yyyy.MM.dd";
    public static final String SHORT_DATE_FORMAT_NO_DASH = "yyyyMMdd";
    public static final String SIMPLE_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String SIMPLE_DATE_FORMAT_NO_DASH = "yyyyMMddHHmmss";
    public static final String LOG_DATE_FORMAT = "yyyyMMdd_HH00";
    public static final String ZONE_DATE_FORMAT = "EEE yyyy-MM-dd HH:mm:ss zzz";
    public static final String DATE_FORMAT = "yyyy/MM/dd EEE";
    public static final String TIME_FORMAT = "HH:mm";
    public static final String SHORT_DATA_FORMAT_YYYY_MMDD = "yyyy/MMdd";


    /**
     *@description: 获取下一天的时间
     *@params: 
     *@author: lizhixin
     *@createDate: 14:56 2017/11/23 
    */
    public static Calendar startOfDayTomorrow() {
        Calendar calendar = Calendar.getInstance();
        truncateDay(calendar);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        return calendar;
    }

    /**
     * 获取下一天的时间
     * create a calendar for start of day yesterday.
     * 
     * @return
     */
    public static Calendar startOfDayYesterday() {
        Calendar yesterday = Calendar.getInstance();
        truncateDay(yesterday);
        yesterday.add(Calendar.DAY_OF_MONTH, -1);
        return yesterday;
    }

    /**
     * 将时间的时分秒归零
     *
     * Truncate the calendar's Calendar.HOUR, Calendar.MINUTE, Calendar.SECOND, Calendar.MILLISECOND to ZERO.
     * 
     * @param calendar
     * @return
     */
    public static Calendar truncateDay(Calendar calendar) {
        if (calendar == null) {
            throw new IllegalArgumentException("input is null");
        }
        calendar.set(Calendar.AM_PM, Calendar.AM);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }

    /**
     * 将日期转换成SimpleDateFormat默认的格式（例：17-9-11 上午12:00）
     *
     * format a calendar using {@link SimpleDateFormat}, with default pattern. if a null calendar is passed
     * in, empty string is returned.
     * 
     * @param calendar
     * @return
     */
    public static String format(Calendar calendar) {
        String formatted = "";
        if (calendar != null) {
            formatted = new SimpleDateFormat().format(calendar.getTime());
        }
        return formatted;
    }

    /**
     * 获取时间的时和分（12:00）
     *
     * format a Time using {@link SimpleDateFormat}, with default pattern. if a null calendar is passed in,
     * empty string is returned.
     * 
     * @param time
     * @return
     */
    public static String format(Time time) {
        String formatted = "";
        if (time != null) {
            formatted = new SimpleDateFormat(TIME_FORMAT).format(time.getTime());
        }
        return formatted;
    }

    /**
     * 将Calendar中的时间转换成传入的时间格式
     *
     * Return the String representation of the Calendar against the given format.
     * 
     * @param date the date to format, such as 'yyyy-MM-dd HH:mm:ss' for long date format with 24H
     * @param format the date format pattern
     * @return the format Date String.
     */
    public static String getDateString(Calendar calendar, String format) {
        if (calendar == null) {
            return null;
        }
        return getDateString(calendar.getTime(), format);
    }

    /**
     * 获得默认格式化时间 'yyyy-MM-dd HH:mm:ss'
     * 
     * @param date
     * @return
     */
    public static String getDefaultDateString(Date date) {
        if (null == date) {
            return "";
        }
        return getDateString(date, SIMPLE_DATE_FORMAT);
    }

    /**
     * 获得默认格式化时间 'yyyy-MM-dd'
     * 
     * @param date
     * @return
     */
    public static String getShortDateString(Date date) {
        if (null == date) {
            return "";
        }
        return getDateString(date, SHORT_DATE_FORMAT);
    }
    
    /**
     * 根据自定义格式获取时间
     * @param date
     * @param format
     * @return
     */
    public static String getFormatDateString(Date date, String format) {
        if (null == date) {
            return "";
        }
        return getDateString(date, format);
    }

    /**
     * 根据传入的 date 和 时间格式 转换 String
     *
     * Return the String representation of the Date against the given format.
     * 
     * @param date the date to format
     * @param format the date format pattern
     * @return the format Date String.
     */
    public static String getDateString(Date date, String format) {
        if (date == null) {
            return null;
        }

        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    /**
     * 根据传入的 date 和 时间格式 和 时区 转换 Strng
     *
     * Return the String representation of the Date against the given format.
     * 
     * @param date the date to format
     * @param format the date format pattern
     * @return the format Date String.
     */
    public static String getDateString(Date date, String format, Locale locale) {
        if (date == null) {
            return null;
        }

        SimpleDateFormat sdf = new SimpleDateFormat(format, locale);
        return sdf.format(date);
    }

    /**
     * 根据传入的 String类型时间 和 时间格式 转换 成 date
     *
     * Parses the <tt>Date</tt> from the given date string and the format pattern.
     * 
     * @param dateString
     * @param pattern the date format
     * @throws {@link IllegalArgumentException} if date format error
     * @return
     */
    public static Date parseDate(String dateString, String pattern) {
        Date date = null;
        try {
            DateFormat format = new SimpleDateFormat(pattern);
            date = format.parse(dateString);
        } catch (ParseException ex) {
            log.error("Invalid date string: " + dateString, ex);
            throw new IllegalArgumentException("Invalid date string: " + dateString, ex);
        }

        return date;
    }

    /**
     * 将string转换成date类型数据
     * 
     * @param dateString
     * @return
     */
    public static Date parseDefaultDate(String dateString) {
        if (!StringUtils.hasText(dateString)) {
            return null;
        }
        Date date = null;
        try {
            DateFormat format = new SimpleDateFormat(SIMPLE_DATE_FORMAT);
            date = format.parse(dateString);
        } catch (ParseException ex) {
            log.error("Invalid date string: " + dateString, ex);
            throw new IllegalArgumentException("Invalid date string: " + dateString, ex);
        }

        return date;
    }

    /**
     *@description: 根据 时间格式 和 时区 string转换成date类型数据
     *@params:
     *@author: lizhixin
     *@createDate: 15:51 2017/11/23
    */
    public static Date parse(String str, String pattern, Locale locale) {
      if(str == null || pattern == null) {
          return null;
      }
      try {
          return new SimpleDateFormat(pattern, locale).parse(str);
      } catch (ParseException e) {
          log.error(e.getMessage());
      }
      return null;
  }
    
    /**
     *@description: 将 String型的日期先转成date类型（yyyy-MM-dd） ，在放到Calendar中返回
     *@params:
     *@author: lizhixin
     *@createDate: 15:54 2017/11/23
    */
    public static Calendar parseCalendarShort(String dateString) {
        if (!StringUtils.hasText(dateString)) {
            return null;
        }
        return parseCalendar(dateString, SHORT_DATE_FORMAT);
    }

    /**
     *@description: 将 String型的日期先转成date类型（yyyy-MM-dd HH:mm:ss） ，在放到Calendar中返回
     *@params:
     *@author: lizhixin
     *@createDate: 16:27 2017/11/23
    */
    public static Calendar parseCalendar(String dateString) {
        if (!StringUtils.hasText(dateString)) {
            return null;
        }
        return parseCalendar(dateString, SIMPLE_DATE_FORMAT);
    }

    /**
     * int 转时间
     * 
     * @param time
     * @return
     */
    public static String parseIntegerToDate(Integer time) {
        if(time ==null){
            return "00:00";
        }
        StringBuilder sb = new StringBuilder("");
        int xs = time / 3600;
        if(xs>0){
        if (xs < 10) {
            sb.append("0");
        }
        
        sb.append(xs);
        sb.append(":");
        }
        int fen = (time - 3600 * xs) / 60;
        if (fen < 10) {
            sb.append("0");
        }
        sb.append(fen);
        sb.append(":");
        int second = time - xs * 3600 - fen * 60;
        if (second < 10) {
            sb.append("0");
        }
        sb.append(second);
        return sb.toString();
    }

    /**
     * 将 String型的日期先转成 指定的时间格式 的date类型，在放到Calendar中返回
     *
     * Parses the <tt>Date</tt> from the given date string and the format pattern and return it as a
     * {@link Calendar} instance.
     * 
     * @param dateString
     * @param pattern the date format
     * @throws {@link IllegalArgumentException} if date format error
     * @return
     */
    public static Calendar parseCalendar(String dateString, String pattern) {
        Date date = parseDate(dateString, pattern);

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c;
    }

    /**
     * 将 String类型的时间 转换成 yyyy-MM-dd
     *
     * Parses the <tt>Date</tt> from the given date string with the format pattern 'yyyy-MM-dd'.
     * 
     * @param dateString
     * @throws {@link IllegalArgumentException} if date format error
     * @return
     */
    public static Date parseShortDate(String dateString) {
        if (!StringUtils.hasText(dateString)) {
            return null;
        }
        Date date = null;
        try {
            DateFormat format = new SimpleDateFormat(SHORT_DATE_FORMAT);
            date = format.parse(dateString);
        } catch (ParseException ex) {
            log.error("Invalid date string: " + dateString, ex);
            throw new IllegalArgumentException("Invalid date string: " + dateString, ex);
        }

        return date;
    }

    /**
     *获取所传时间的 前一天时间（只是天数减少了一天）
     *
     * return one day before the given date.
     * 
     * @param date the given date
     * @return the adjusted date.
     */
    public static Calendar backOneDay(Calendar date) {
        Calendar cal = (Calendar) date.clone();
        cal.add(Calendar.DATE, -1);
        return cal;
    }

    /**
     * 获取当前月份的天数
     *
     * Get how many days in current month.
     */
    public static int daysForCurrentMonth() {
        Calendar c = Calendar.getInstance();
        int days = c.getActualMaximum(Calendar.DAY_OF_MONTH);

        return days;
    }

    /**
     * 将传入的date放到Calendar中，返回Calendar
     *
     * Return the Calendar for the give date.
     * 
     * @param date
     * @return
     */
    public static Calendar fromDate(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);

        return c;
    }

    /**
     * 获取 1970-01-01 00:00:00
     *
     * return the EPOCH = "1970-01-01 00:00:00"
     * 
     * @return
     */
    public static Calendar epoch() {

        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(0);
        return c;
    }

    /**
     *@description: 根据Long型的时间戳 获取时间格式为yyyy-MM-dd的时间戳
     *@params:
     *@author: lizhixin
     *@createDate: 16:35 2017/11/23
    */
    public static long getSimpleDateTimeMillis(long timeMillis) {
        Date date = new Date(timeMillis);
        String dateStr = getDateString(date, SHORT_DATE_FORMAT);
        Date transformDate = parseDate(dateStr, SHORT_DATE_FORMAT);
        return transformDate.getTime();
    }

    /**
     * 根据传入时间(Date) 向后 加上 传入的向后添加的天数
     *
     * get the date from a day with days
     * 
     * @author <a href="mailto:wang-shuai@letv.com">Ousui</a>
     * @param from which day
     * @param days interval days, 0: today; positive: future; negative: history.
     * @return
     */
    public static Calendar getDateFromDate(Date from, long days) {
        long froml = from.getTime();
        // 时间间隔。
        long interval = days * 24l * 60l * 60l * 1000l;
        long millis = froml + interval;
        Calendar now = Calendar.getInstance();
        now.setTimeInMillis(millis);
        return now;
    }

    /**
     * 根据传入时间(Date) 和 时间格式 向后 加上 传入的向后添加的天数
     *
     * get the date from a day with days
     * 
     * @author <a href="mailto:wang-shuai@letv.com">Ousui</a>
     * @param from which day
     * @param days interval days, 0: today; positive: future; negative: history.
     * @return String
     */
    public static String getDateFromDate(Date from, long days, String format) {
        Calendar c = CalendarUtil.getDateFromDate(from, days);
        return CalendarUtil.getDateString(c, format);
    }

    /**
     *@description: 根据传入时间(String) 和 时间格式 向后 加上 传入的向后添加的天数
     *@params:
     *@author: lizhixin
     *@createDate: 17:12 2017/11/23
    */
    public static String getDateFromDate(String from, long days, String format) {
        Date d = CalendarUtil.parseDate(from, format);
        Calendar c = CalendarUtil.getDateFromDate(d, days);
        return CalendarUtil.getDateString(c, format);
    }

    /**
     *@description: 获取当天的开始时间（例：2017-11-23 00:00:00）
     *@params:
     *@author: lizhixin
     *@createDate: 17:15 2017/11/23
    */
    public static Calendar getDayStart() {
        Calendar cal = Calendar.getInstance();
        cal = truncateDay(cal);
        return cal;
    }

    /**
     *@description: 获取当天的开始时间（例：2017-11-23 00:00:00）//和上面那个方法一样
     *@params:
     *@author: lizhixin
     *@createDate: 17:17 2017/11/23
    */
    public static Date getDayBegin(){
    	return parseDate(getShortDateString(new Date())+" 00:00:00", SIMPLE_DATE_FORMAT);
    }

    /**
     *@description: 获取本周的开始时间（周一）（例：2017-11-20 00:00:00）
     *@params:
     *@author: lizhixin
     *@createDate: 17:20 2017/11/23
    */
    public static Calendar getWeekDayStart() {
        Calendar cal = Calendar.getInstance();
        int day_of_week = cal.get(Calendar.DAY_OF_WEEK) - 2;
        cal.add(Calendar.DATE, -day_of_week);
        cal = truncateDay(cal);
        return cal;
    }

    /**
     *@description: 获取本月的开始时间（例：2017-11-01 00:00:00）
     *@params:
     *@author: lizhixin
     *@createDate: 17:21 2017/11/23
    */
    public static Calendar getMonthDayStart() {
        Calendar cal = Calendar.getInstance();
        int day_of_month = cal.get(Calendar.DAY_OF_MONTH) - 1;
        cal.add(Calendar.DATE, -day_of_month);
        cal = truncateDay(cal);
        return cal;
    }
    
    /**
     * n为正数时：返回n天以后的date
     * n为负数时：返回n天以前的date
     */
    public static Date addOrBeforeNDay(Date date, int n) {  
        if (null == date) {  
            return date;  
        }  
        Calendar c = Calendar.getInstance();  
        c.setTime(date);   //设置当前日期  
        c.add(Calendar.DATE, n); //日期加n天  
        date = c.getTime();  
        return date;  
    }

    /**
     *@description: 计算两个时间的间隔天数（以天为单位,不足一天24小时的不算一天）
     *@params:
     *@author: lizhixin
     *@createDate: 14:53 2017/11/23
     */
    public static int daysBetween(Calendar startTime, Calendar endTime) {
        if (startTime == null) {
            throw new IllegalArgumentException("startTime is null");
        }
        if (endTime == null) {
            throw new IllegalArgumentException("endTime is null");
        }
        if (startTime.compareTo(endTime) > 0) {
            throw new IllegalArgumentException("endTime is before the startTime");
        }
        return (int) ((endTime.getTimeInMillis() - startTime.getTimeInMillis()) / (1000 * 60 * 60 * 24));
    }

    /**
     *@description: 两个时间的时间差（例：1天1小时）
     *@params:
     *@author: lizhixin
     *@createDate: 17:22 2017/11/23
    */
    public static String dateBetween(Date btime,Date etime){
        if(btime == null || etime == null){
            return "";
        }
        return CalendarUtil.dateBetween(btime.getTime(),etime.getTime());
    }

    public static String dateBetween(Long btime,Long etime){
        StringBuilder sb = new StringBuilder("");
        if(btime == null || etime == null){
            return "00:00";
        }
        long between = etime-btime;
        if(between < 0){
            return "00:00";
        }
        long day = between / (24 * 60 * 60 * 1000);
        long hour = (between / (60 * 60 * 1000) - day * 24);
        long min = ((between / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long s = (between / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        long ms = (between - day * 24 * 60 * 60 * 1000 - hour * 60 * 60 * 1000 - min * 60 * 1000 - s * 1000);
        if(day != 0){
            sb.append(day+"天");
        }
        if(hour != 0){
            sb.append(hour+"小时");
        }
        if(min != 0){
            sb.append(min+"分");
        }
        if(s != 0){
            sb.append(s+"秒");
        }
        if(ms != 0){
            sb.append(ms+"毫秒");
        }
        return sb.toString();
    }

    /**
     * 获取上周一到周天的日期
     *
     * @return {1：'2017-07-24',2:'2017-07-25'...}
     */
    public static Map<Integer,String> getBeforWeekDate(){

        Map<Integer,String> weekMap = new HashMap<>();
        // 日期格式转换
        SimpleDateFormat format = new SimpleDateFormat(CalendarUtil.SHORT_DATE_FORMAT);
        // 当前日期
        Calendar instance = Calendar.getInstance();
        // 调整到上周
        instance.add(Calendar.WEDNESDAY, -1);
        // 调整到上周1
        instance.set(Calendar.DAY_OF_WEEK, 2);
        //循环输出
        for (int i = 1; i <= 7; i++) {
            weekMap.put(i,format.format(instance.getTime()));
//            System.out.println("星期" + i + ":" + format.format(instance.getTime()));
            instance.add(Calendar.DAY_OF_WEEK, 1);
        }
        return weekMap;
    }

    /**
     *@description: 获取当前的时间戳
     *@params:
     *@author: lizhixin
     *@createDate: 17:30 2017/11/23
    */
    public static Timestamp currentTimestamp(){
        return new Timestamp(System.currentTimeMillis());
    }

    /**
     *@description: 获取本年的开始时间
     *@params:
     *@author: lizhixin
     *@createDate: 17:30 2017/11/23
    */
    public static java.util.Date getBeginDayOfYear() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, getNowYear());
        // cal.set
        cal.set(Calendar.MONTH, Calendar.JANUARY);
        cal.set(Calendar.DATE, 1);

        return getDayStartTime(cal.getTime());
    }

    /**
     *@description: 获取本年的结束时间
     *@params:
     *@author: lizhixin
     *@createDate: 17:30 2017/11/23
    */
    public static java.util.Date getEndDayOfYear() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, getNowYear());
        cal.set(Calendar.MONTH, Calendar.DECEMBER);
        cal.set(Calendar.DATE, 31);
        return getDayEndTime(cal.getTime());
    }

    /**
     *@description: 获取某个日期的开始时间
     *@params:
     *@author: lizhixin
     *@createDate: 17:31 2017/11/23
    */
    public static Timestamp getDayStartTime(Date d) {
        Calendar calendar = Calendar.getInstance();
        if (null != d) calendar.setTime(d);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return new Timestamp(calendar.getTimeInMillis());
    }

    /**
     *@description: 获取某个日期的结束时间
     *@params:
     *@author: lizhixin
     *@createDate: 17:31 2017/11/23
    */
    public static Timestamp getDayEndTime(Date d) {
        Calendar calendar = Calendar.getInstance();
        if (null != d) calendar.setTime(d);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return new Timestamp(calendar.getTimeInMillis());
    }

    /**
     *@description: 获取今年是哪一年
     *@params:
     *@author: lizhixin
     *@createDate: 17:31 2017/11/23
    */
    public static Integer getNowYear() {
        Date date = new Date();
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(date);
        return Integer.valueOf(gc.get(1));
    }

    /**
     *@description: 获取某年的开始时间
     *@params:
     *@author: lizhixin
     *@createDate: 17:31 2017/11/23
    */
    public static java.util.Date getBeginDayOfYear(Integer year) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        // cal.set
        cal.set(Calendar.MONTH, Calendar.JANUARY);
        cal.set(Calendar.DATE, 1);

        return getDayStartTime(cal.getTime());
    }

    /**
     *@description: 获取某年的结束时间
     *@params:
     *@author: lizhixin
     *@createDate: 17:31 2017/11/23
    */
    public static java.util.Date getEndDayOfYear(Integer year) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, Calendar.DECEMBER);
        cal.set(Calendar.DATE, 31);
        return getDayEndTime(cal.getTime());
    }


    public static void main(String args[]) {
        //_log.info(CalendarUtil.parseIntegerToDate(60));
//        parseDefaultDate("Tue Dec 02 14:24:31 CST 2014");
//        Date date = parse("Tue Dec 02 14:24:31 CST 2014", "EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
//        log.info(date.toString());

        Date date1 = parseDefaultDate("2017-09-11 12:00:00");
        Date date2 = parseDefaultDate("2017-09-12 13:00:00");
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        calendar1.setTime(date1);
        calendar2.setTime(date2);
//        System.out.println(daysBetween(calendar1,calendar2));
//
//        System.out.println(getDateString(startOfDayTomorrow(),SIMPLE_DATE_FORMAT));
//
//        System.out.println(getDateString(startOfDayYesterday(),SIMPLE_DATE_FORMAT));

//        System.out.println(getDateString(truncateDay(calendar1),SIMPLE_DATE_FORMAT));

//        System.out.println(format(calendar1));

//        Time time = new Time(date1.getTime());
//        System.out.println(format(time));

//        System.out.println(getDateString(calendar1,SIMPLE_DATE_FORMAT));

//        System.out.println(getFormatDateString(date1,SHORT_DATE_FORMAT));

//        System.out.println(getDateString(date1,SIMPLE_DATE_FORMAT,Locale.US));

//        System.out.println(parseCalendarShort(date1.toString()));

//        System.out.println(getDateString(backOneDay(calendar1),SIMPLE_DATE_FORMAT));

//        System.out.println(daysForCurrentMonth());

//        System.out.println(getSimpleDateTimeMillis(System.currentTimeMillis()));

//        System.out.println(getDateString(getDateFromDate(date1,2),SIMPLE_DATE_FORMAT));

//        System.out.println(getDateString(getDayStart(),SIMPLE_DATE_FORMAT));

//        System.out.println(getDateString(getDayBegin(),SIMPLE_DATE_FORMAT));

//        System.out.println(getDateString(getWeekDayStart(),SIMPLE_DATE_FORMAT));

//        System.out.println(getDateString(getMonthDayStart(),SIMPLE_DATE_FORMAT));

        System.out.println(dateBetween(date1,date2));

    }
}
