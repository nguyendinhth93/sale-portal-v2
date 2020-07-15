package com.tp.util;

import org.joda.time.DateTime;
import org.joda.time.DateTimeComparator;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by vanho on 26/06/2017.
 */
public class DateUtil {

    public static final String FORMAT_DATE_YYYYMMDD = "yyyy-MM-dd";

    public static String parseDateToString(Date date, String format){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(date);
    }

    public static boolean compareDateWithoutTime(Date date1, Date date2){
        /*if(date1 == null || date2 == null)
            return false;

        DateUtils.is*/
        //DateUtils.isSameDay()

        return false;
    }

    public static String date2String(Date value) {
        if (value != null) {
            SimpleDateFormat date = new SimpleDateFormat("yyyy/MM/dd");
            return date.format(value);
        }
        return "";
    }

    public static String dateToString(Date value) {
        if (value != null) {
            SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
            return date.format(value);
        }
        return "";
    }

    public static String dateyyyyMMddHHmmSS(Date value) {
        if (value != null) {
            SimpleDateFormat dateTimeNoSlash = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return dateTimeNoSlash.format(value);
        }
        return "";
    }

    public static String dateToStringddMMyyyy(Date value) {
        if (value != null) {
            SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy");
            return date.format(value);
        }
        return "";
    }

    public static String dateToStringddMMyyyyHHmmss(Date value) {
        if (value != null) {
            SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            return date.format(value);
        }
        return "";
    }

    /**
     * @param value Date
     *
     * @return String
     */
    public static String date2ddMMyyyyHHMMssNoSlash(Date value) {
        if (value != null) {
            SimpleDateFormat dateTimeNoSlash = new SimpleDateFormat("ddMMyyyyHHmmss");
            return dateTimeNoSlash.format(value);
        }
        return "";
    }

    public static String date2yyyyMMddNoSlash(Date value) {
        if (value != null) {
            SimpleDateFormat dateTimeNoSlash = new SimpleDateFormat("yyyyMMdd");
            return dateTimeNoSlash.format(value);
        }
        return "";
    }

    public static boolean compareDateOnly(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            return false;
        }

        DateTime first = new DateTime(date1);
        DateTime second = new DateTime(date2);
        return DateTimeComparator.getDateOnlyInstance().compare(first, second) == 0;
    }

    public static Date stringToDateWithOutTime(String str, String format) {
        if (StringUtil.stringIsNullOrEmty(str))
            return null;
        DateFormat df = new SimpleDateFormat(format);
        try {
            return df.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

}
