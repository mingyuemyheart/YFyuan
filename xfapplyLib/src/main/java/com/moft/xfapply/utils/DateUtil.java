package com.moft.xfapply.utils;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

@SuppressLint("SimpleDateFormat")
public class DateUtil {
    
	public static String getCurrentYear(){
        long time =System.currentTimeMillis();
        
        SimpleDateFormat format = new SimpleDateFormat("yyyy");
        String str = format.format(new Date(time));
        return str;
	}
	
	public static String getCurrentDate(){
        long time =System.currentTimeMillis();
        
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String str = format.format(new Date(time));
        return str;
	}
	
	public static String format(Date d) {
        if (d == null) {
            return "";
        }

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = format.format(d);
        return str;
    }

    public static String format(Long currentTime) {
        if (currentTime == null) {
            return "";
        }
        Date date = new Date(currentTime);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = format.format(date);
        return str;
    }

    public static String format(Date d, String format) {
        if (d == null) {
            return "";
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        String str = dateFormat.format(d);
        return str;
    }
	
	public static String getCurrentTime(){
        long time =System.currentTimeMillis();
        
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = format.format(new Date(time));
        return str;
	}
    
    public static String getCurrentTime(long time){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = format.format(new Date(time));
        return str;
    }

	public static String getCurrentTime2() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSS");
        return dateFormat.format(date);
    }

    public static Date toDate(String strDate, String format) {
        Date retDate = null;
        if(!StringUtil.isEmpty(strDate)) {
            try {
                SimpleDateFormat formatter = new SimpleDateFormat(format);
                retDate = formatter.parse(strDate);
            } catch (ParseException e) {
                e.printStackTrace();
                retDate = null;
            }
        }
        return retDate;
    }
	
    public static Date toDate(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }

    public static Calendar parseDateStr(String dateStr, String fromat) {
        Calendar retDate = Calendar.getInstance();
        if(!StringUtil.isEmpty(dateStr)) {
            try {
                SimpleDateFormat formater = new SimpleDateFormat(fromat);
                retDate.setTime(formater.parse(dateStr));
            } catch (ParseException e) {
                e.printStackTrace();
                retDate = null;
            }
        }
        return retDate;
    }

    public static String formatDate(Calendar date, String fromat) {
        if (date == null) {
            return "";
        }else {
            SimpleDateFormat formater = new SimpleDateFormat(fromat);
            return formater.format(date.getTime());
        }
    }


    public static Date addForCalendar(Date date, int type, int value) {
        if(date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        calendar.setTime(date);
        calendar.add(type, value);
        return calendar.getTime();
    }

    public static String getToday() {
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date zero = calendar.getTime();

        return format(zero);
    }

    public static String getYestoday() {
        Calendar cal = Calendar.getInstance(Locale.CHINA);
        cal.add(Calendar.DATE,-1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        Date time = cal.getTime();
        return format(time);
    }

    public static String getMonthStart(){
        Calendar cal=Calendar.getInstance(Locale.CHINA);
        cal.add(Calendar.MONTH, 0);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        Date time=cal.getTime();
        return format(time);
    }


    public static String getMonthEnd() {
        Calendar cal = Calendar.getInstance(Locale.CHINA);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date time = cal.getTime();
        return format(time);
    }


    /**
     * 获取本周的第一天
     * @return String
     * **/
    public static String getWeekStart(){
        Calendar cal = Calendar.getInstance(Locale.CHINA);
        cal.add(Calendar.WEEK_OF_MONTH, 0);
        cal.set(Calendar.DAY_OF_WEEK, 2);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        Date time = cal.getTime();
        return format(time);
    }
    /**
     * 获取本周的最后一天
     * @return String
     * **/
    public static String getWeekEnd(){
        Calendar cal=Calendar.getInstance(Locale.CHINA);
        cal.set(Calendar.DAY_OF_WEEK, cal.getActualMaximum(Calendar.DAY_OF_WEEK));
        cal.add(Calendar.DAY_OF_WEEK, 1);
        cal.add(Calendar.DATE, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);

        Date time=cal.getTime();
        return format(time);
    }
    /**
     * 获取本年的第一天
     * @return String
     * **/
    public static String getYearStart(){
        return new SimpleDateFormat("yyyy").format(new Date())+"-01-01 00:00:00";
    }

    /**
     * 获取本年的最后一天
     * @return String
     * **/
    public static String getYearEnd(){
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        calendar.set(Calendar.MONTH,calendar.getActualMaximum(Calendar.MONTH));
        calendar.set(Calendar.DAY_OF_MONTH,calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date currYearLast = calendar.getTime();
        return format(currYearLast);
    }

    public static String getPreWeekStart() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -7);

        while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
            calendar.add(Calendar.DATE, -1);
        }

        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date startDate = calendar.getTime();
        return format(startDate);
    }

    public static String getPreWeekEnd() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -7);

        while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
            calendar.add(Calendar.DATE, -1);
        }

        calendar.add(Calendar.DATE, 7-1);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        Date endDate = calendar.getTime();
        return format(endDate);
    }

    public static String getPreMonthStart() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);

        int destYear, destMonth;
        if(month == 0) {
            destYear = year - 1;
            destMonth = 11;
        } else {
            destYear = year;
            destMonth = month - 1;
        }
        calendar.set(Calendar.YEAR, destYear);
        calendar.set(Calendar.MONTH, destMonth);
        calendar.set(Calendar.DATE, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date startDate  = calendar.getTime();
        return format(startDate);
    }

    public static String getPreMonthEnd() {
        Date startDate, endDate;
        Calendar calendar = Calendar.getInstance();

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);

        int destYear, destMonth;
        if(month == 0) {
            destYear = year - 1;
            destMonth = 11;
        } else {
            destYear = year;
            destMonth = month - 1;
        }
        calendar.set(Calendar.YEAR, destYear);
        calendar.set(Calendar.MONTH, destMonth);
        calendar.set(Calendar.DATE, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        startDate  = calendar.getTime();
        System.out.println(startDate.toString());

        int dayCount = calendar.getMaximum(Calendar.DAY_OF_MONTH);
        calendar.setTime(startDate);
        calendar.add(Calendar.DATE, dayCount-1);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);

        endDate = calendar.getTime();
        return format(endDate);
    }

}
