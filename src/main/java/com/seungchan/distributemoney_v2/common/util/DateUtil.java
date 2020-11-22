package com.seungchan.distributemoney_v2.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    /**
     * 디폴트 시간 형식
     */
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private static Date testDate = null;

    public static Date getCurrentDate() {
        if (testDate == null) {
            return new Date();
        } else {
            return testDate;
        }
    }

    public static void setTestDate(Date testDate) {
        DateUtil.testDate = testDate;
    }

    /**
     * 문자열 -> Date 변환
     * @param date
     * @param format
     * @return
     */
    public static Date stringToDate(String date, String format) {
        if (date == null)
            return null;
        Date parsed = null;
        try {
            parsed = new SimpleDateFormat(format).parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return parsed;
    }

    public static Date stringToDate(String date) {
        return stringToDate(date, DateUtil.DEFAULT_DATE_FORMAT);
    }

    public static Date getDateAfterMillis(Date date, long millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MILLISECOND, (int) millis);
        return calendar.getTime();
    }

    public static Date getDateAfterMillis(long millis) {
        return getDateAfterMillis(getCurrentDate(), millis);
    }

    public static Date getDateAfterSeconds(Date date, int sec) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.SECOND, sec);
        return calendar.getTime();
    }

    public static Date getDateAfterSeconds(int sec) {
        return getDateAfterSeconds(getCurrentDate(), sec);
    }

    public static Date getDateAfterMinutes(Date date, int minutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, minutes);
        return calendar.getTime();
    }

    public static Date getDateAfterMinutes(int minutes) {
        return getDateAfterMinutes(getCurrentDate(), minutes);
    }

    public static Date getDateAfterHours(Date date, int hours) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR, hours);
        return calendar.getTime();
    }

    public static Date getDateAfterHours(int hours) {
        return getDateAfterHours(getCurrentDate(), hours);
    }

    public static Date getDateAfterDays(Date date, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, days);
        return calendar.getTime();
    }

    public static Date getDateAfterDays(int days) {
        return getDateAfterDays(getCurrentDate(), days);
    }
}
