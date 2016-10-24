package com.ch.wchhuangya.baas.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间、日期助手类
 * Created by wchya on 16/10/10.
 */

public class TimeHelper {
    /** 长时间格式 yyyy-MM-dd HH:mm:ss */
    public static final String LONG_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    /** 只有日期的格式1 yyyy-MM-dd */
    public static final String ONLY_DATE_FORMAT1 = "yyyy-MM-dd";
    /** 只有日期的格式2 yyyyMMdd */
    public static final String ONLY_DATE_FORMAT2 = "yyyyMMdd";

    private TimeHelper() {
        throw new UnsupportedOperationException("该类不能被实例化！");
    }

    /**
     * 根据传入的日期时间格式获取当前日期时间
     * @param format 日期时间格
     */
    public static String getCurDateTimeByFormat(String format) {
        SimpleDateFormat sdf;
        if (StringHelper.isEmpty(format))
            sdf = new SimpleDateFormat(LONG_DATE_FORMAT);
        else
            sdf = new SimpleDateFormat(format);
        return sdf.format(new Date());
    }

    /**
     * 获取指定时间的时间戳
     * @param datetime 要转换的日期时间
     * @param format 日期时间格式
     */
    public static long getTimestamp(String datetime, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            Date date = sdf.parse(datetime);
            return date.getTime();
        } catch (Exception ex) {
            LogHelper.e("时间转换时间戳失败!", ex);
        }

        return System.currentTimeMillis();
    }

    /** 获取当前时间的时间戳 */
    public static long getCurTimstamp() {
        return System.currentTimeMillis();
    }

    /**
     * 将时间戳转换为指定格式的时间字符串
     * @param timestamp 时间戳，可以是13位，可以是10位
     * @param format 时间格式
     * @return
     */
    public static String changeTimestampToTime(long timestamp, String format) {
        return new SimpleDateFormat(StringHelper.isEmpty(format) ? LONG_DATE_FORMAT : format)
                .format(new Date(timestamp > 999999999999L ? timestamp : timestamp * 1000));
    }

    /**
     * 根据秒数返回表示友好的字符串
     * @param second 要格式化的秒数
     */
    public static String getFriendlyTime(int second) {
        if (second < 60)
            return second + "秒";
        else if (second >= 60 && second < 3600)
            return second / 60 + "分" + second % 60 + "秒";
        else
            return second / 3600 + "小时" + getFriendlyTime(second % 3600);
    }
}
