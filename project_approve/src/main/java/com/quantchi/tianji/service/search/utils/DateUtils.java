package com.quantchi.tianji.service.search.utils;

import com.quantchi.core.utils.AssertUtil;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author whuang
 * @date 2019/6/26
 */
@Slf4j
public class DateUtils {

    private static ThreadLocal<DateFormat> yyyyMMddDateFormat = new ThreadLocal<DateFormat>(){
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    };

    private static ThreadLocal<DateFormat> yyyyMMDateFormat = new ThreadLocal<DateFormat>(){
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM");
        }
    };

    public static String addOneMonth(String date) {
        Date result = null;
        try {
            result = yyyyMMDateFormat.get().parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cl = Calendar.getInstance();
        cl.setTime(result);
        cl.add(Calendar.MONTH, 1);
        result = cl.getTime();
        return yyyyMMDateFormat.get().format(result);
    }

    public static String format2yyyyMMdd(Date date) throws ParseException {
        String format = yyyyMMddDateFormat.get().format(date);
        return format;
    }

    public static String transformDate2yyyyMM(String date) throws ParseException {
        Date parse = yyyyMMDateFormat.get().parse(date);
        String format = yyyyMMDateFormat.get().format(parse);
        return format;
    }

    public static boolean checkDateFormatWithyyyyMMdd(String date) {
        try {
            yyyyMMddDateFormat.get().parse(date);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static boolean checkDateFormatWithyyyyMM(String date) {
        try {
            yyyyMMDateFormat.get().parse(date);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /***
     * 日期月份减一个月
     *
     * @param datetime
     *            日期(2014-11)
     * @return 2014-10
     */
    public static String dateFormat(String datetime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Date date = null;
        try {
            date = sdf.parse(datetime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cl = Calendar.getInstance();
        cl.setTime(date);
        cl.add(Calendar.MONTH, -1);
        date = cl.getTime();
        return sdf.format(date);
    }

    public static String dateFormat(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        return sdf.format(date);
    }


    public static String getFormatDate(String pattern, Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(date);
    }

    /**
     * 字符串yyyy/MM/dd HH:mm:ss格式时间转date
     *
     * @param str
     * @return
     */
    public static Date getDateYMD(String str) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return date;

    }

    /**
     * 日期加一天
     *
     * @param date
     * @return
     * @throws Exception
     */
    public static String DateDayChange(String date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dt = null;
        try {
            dt = sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(dt);
        rightNow.add(Calendar.DAY_OF_YEAR, 1);// 日期加1天
        Date dt1 = rightNow.getTime();
        String reStr = sdf.format(dt1);
        return reStr;
    }

    /**
     * 日期加一天
     *
     * @param date
     * @return
     * @throws Exception
     */
    public static String DateDayChangeNew(String date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dt = null;
        try {
            dt = sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(dt);
        rightNow.add(Calendar.DAY_OF_YEAR, 1);// 日期加1天
        Date dt1 = rightNow.getTime();
        String reStr = sdf.format(dt1);
        return reStr;
    }

    /**
     * 日期加减天数
     *
     * @param date
     * @return
     * @throws Exception
     */
    public static String DateChangeByDays(String date, int days, Boolean containHour){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        if(containHour) {
            sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
        Date dt = null;
        try {
            dt = sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(dt);
        rightNow.add(Calendar.DAY_OF_YEAR, days);
        Date dt1 = rightNow.getTime();
        String reStr = sdf.format(dt1);
        return reStr;
    }

    /**
     * 两个日期相差的天数
     *
     * @param bdate 大日期
     * @param smdate 小日期
     * @return int 相差的天数
     */
    public static int getIntervalDays(String smdate, String bdate){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");

        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();

        try {
            cal1.setTime(sdf.parse(smdate));

            cal2.setTime(sdf.parse(bdate));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long time1 = cal1.getTimeInMillis();

        long time2 = cal2.getTimeInMillis();

        long between_days=(time2-time1)/(1000*3600*24);

        return Integer.parseInt(String.valueOf(between_days))+1;

    }

    /**
     * 计算两个日期之间相差的天数
     *
     * @param smdate 较小的时间
     * @param bdate 较大的时间
     * @return 相差天数
     * @throws ParseException
     */
    public static int daysBetween(Date smdate, Date bdate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date smdateNew = null;
        Date bdateNew = null;
        try {
            smdateNew = sdf.parse(sdf.format(smdate));
            bdateNew = sdf.parse(sdf.format(bdate));
        } catch (ParseException e) {
            return 0;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdateNew);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdateNew);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);

        return Integer.parseInt(String.valueOf(between_days));
    }

    public static Date getDateYYYYMMddHHMMSS(String str) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return date;
    }

    public static Date getDateYYYYMMdd2(String str) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return date;

    }

    public static Date getDateYYYYMM(String str) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return date;

    }

    /**
     * 获取传入时间的零点时间
     * @return String
     */
    public static Date getDayAm(Date now){
        String str = getFormatDate("yyyy-MM-dd",now);
        return getDateYYYYMMddHHMMSS(str + " 00:00:00");
    }


    /**
     * 获取传入日期的23点59分59秒时间
     * @return String
     */
    public static Date getDayPm(Date now) {
        String str = getFormatDate("yyyy-MM-dd",now);
        return getDateYYYYMMddHHMMSS(str + " 23:59:59");
    }

    /**
     * 获取上月的第一天
     * @param date
     * @return
     */
    public static String getEarlyMonth(String date) {
        SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date nowData =sf.parse(date);
            Calendar calendar=Calendar.getInstance();
            calendar.setTime(nowData);
            calendar.add(Calendar.MONTH, -1);
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            Date dt1 = calendar.getTime();
            String reStr = sf.format(getDayAm(dt1));
            return reStr;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取上月的最后一天
     * @param date
     * @return
     */
    public static String getlateMonth(String date) {
        SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date nowData =sf.parse(date);
            Calendar calendar=Calendar.getInstance();
            calendar.setTime(nowData);
            int month=calendar.get(Calendar.MONTH);
            calendar.set(Calendar.MONTH, month-1);
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
            Date dt1 = calendar.getTime();
            String reStr = sf.format(getDayAm(dt1));
            return reStr;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String DateChangeType(String date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dt = null;
        try {
            dt = sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String reStr = sdf.format(dt);
        return reStr;
    }

    /**
     * 获取天数
     * @param date
     * @return
     */
    public static String getDay(Date date) {
        SimpleDateFormat sf=new SimpleDateFormat("dd");
        Calendar calendar=Calendar.getInstance();
        Date dt1 = calendar.getTime();
        String reStr = sf.format(getDayAm(dt1));
        return reStr;
    }

    /**
     * 获取本周的第一天
     * @return String
     * **/
    public static Date getWeekStart(){
        Calendar cal=Calendar.getInstance();
        cal.add(Calendar.WEEK_OF_MONTH, 0);
        cal.set(Calendar.DAY_OF_WEEK, 2);
        Date time=cal.getTime();
        return getDateYYYYMMddHHMMSS(new SimpleDateFormat("yyyy-MM-dd").format(time)+" 00:00:00");
    }

    /**
     * 获取传入日期所在月的第一天
     * @param date
     * @return
     */
    public static String getFirstDayDateOfMonth(String date) {

        Date date1 = getDateYYYYMMddHHMMSS(date);
        final Calendar cal = Calendar.getInstance();
        cal.setTime(date1);
        final int last = cal.getActualMinimum(Calendar.DAY_OF_MONTH);
        cal.set(Calendar.DAY_OF_MONTH, last);

        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cal.getTime());
    }

    /**
     * 获取传入日期所在月的最后一天
     * @param date
     * @return
     */
    public static String getLastDayOfMonth(String date) {
        Date date1 = getDateYYYYMMddHHMMSS(date);
        final Calendar cal = Calendar.getInstance();
        cal.setTime(date1);
        final int last = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        cal.set(Calendar.DAY_OF_MONTH, last);

        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cal.getTime());
    }

    public static String dateFormatYear (Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        return sdf.format(date);
    }

    public static String dateFormatDay (Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd");
        return sdf.format(date);
    }

    public static String changeFormatHour (Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        return sdf.format(date);
    }

    public static String changeFormatDateYYYYMM (Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        return sdf.format(date);
    }

    public static String changeFormatDate (Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    public static String changeFormatDateToSec (Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }

    /**
     * Description: 判断一个时间是否在一个时间段内 </br>
     *
     * @param nowTime 当前时间 </br>
     * @param beginTime 开始时间 </br>
     * @param endTime 结束时间 </br>
     */
    public static boolean belongCalendar(Date nowTime, Date beginTime, Date endTime) {
        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);

        Calendar begin = Calendar.getInstance();
        begin.setTime(beginTime);

        Calendar end = Calendar.getInstance();
        end.setTime(endTime);

        return date.after(begin) && date.before(end);
    }

    public static Calendar getCalendar(Date date){
        if(date==null)
        {
            return null;
        }
        DateFormat df = DateFormat.getDateInstance();
        df.format(date);
        return df.getCalendar();
    }

    // 获取本周的开始时间
    @SuppressWarnings("unused")
    public static Date getBeginDayOfWeek() {
        Date date = new Date();
        if (date == null) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int dayofweek = cal.get(Calendar.DAY_OF_WEEK);
        if (dayofweek == 1) {
            dayofweek += 7;
        }
        cal.add(Calendar.DATE, 2 - dayofweek);
        return getDayStartTime(cal.getTime());
    }

    // 获取本周的结束时间
    public static Date getEndDayOfWeek() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getBeginDayOfWeek());
        cal.add(Calendar.DAY_OF_WEEK, 6);
        Date weekEndSta = cal.getTime();
        return getDayEndTime(weekEndSta);
    }

    // 获取上周的开始时间
    @SuppressWarnings("unused")
    public static Date getBeginDayOfLastWeek() {
        Date date = new Date();
        if (date == null) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int dayofweek = cal.get(Calendar.DAY_OF_WEEK);
        if (dayofweek == 1) {
            dayofweek += 7;
        }
        cal.add(Calendar.DATE, 2 - dayofweek - 7);
        return getDayStartTime(cal.getTime());
    }

    // 获取上周的结束时间
    public static Date getEndDayOfLastWeek() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getBeginDayOfLastWeek());
        cal.add(Calendar.DAY_OF_WEEK, 6);
        Date weekEndSta = cal.getTime();
        return getDayEndTime(weekEndSta);
    }

    // 获取某个日期的开始时间
    public static Timestamp getDayStartTime(Date d) {
        Calendar calendar = Calendar.getInstance();
        if (null != d)
            calendar.setTime(d);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return new Timestamp(calendar.getTimeInMillis());
    }

    // 获取某个日期的结束时间
    public static Timestamp getDayEndTime(Date d) {
        Calendar calendar = Calendar.getInstance();
        if (null != d)
            calendar.setTime(d);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return new Timestamp(calendar.getTimeInMillis());
    }

    /**
     * 获取时间的小时
     * @param date
     * @return
     */
    public static Integer getHour(Date date){
        if(date==null)
        {
            return null;
        }
        return getCalendar(date).get(Calendar.HOUR_OF_DAY);
    }

    public static int getDaysOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    public static String dealTimeData(String time) {

        return time + " " + "12:00:00";

    }

    static class Person {
        private int id;
        private String name;
        private int age;
        private String gender;

        public Person(int id, String name, int age, String gender) {
            this.id = id;
            this.name = name;
            this.age = age;
            this.gender = gender;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }
    }

    public static void main(String[] args) {
        System.out.println(dateFormatYear(new Date()));

    }

}
