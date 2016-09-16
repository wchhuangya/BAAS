package com.ch.wchhuangya.baas.util;

import android.util.Log;

/**
 * 日志助手类
 * Created by wchya on 16/9/14.
 */
public class LogHelper {

    private LogHelper() {
        throw new UnsupportedOperationException("该类不能被实例化!");
    }

    public static boolean LOG_SWITCH = true;
    private static final String LOG_TAG = "BAAS";

    /**
     * 打印 DEBUG 级别日志
     * @param msg 日志信息
     */
    public static void d(String msg) {
        if (LOG_SWITCH)
            Log.d(LOG_TAG, msg);
    }

    /**
     * 打印 DEBUG 级别日志
     * @param msg 日志信息
     */
    public static void d(String msg, Throwable tr) {
        if (LOG_SWITCH)
            Log.d(LOG_TAG, msg, tr);
    }

    /**
     * 打印 ERROR 级别日志
     * @param msg 日志信息
     */
    public static void e(String msg) {
        if (LOG_SWITCH)
            Log.e(LOG_TAG, msg);
    }

    /**
     * 打印 ERROR 级别日志
     * @param msg 日志信息
     */
    public static void e(String msg, Throwable tr) {
        if (LOG_SWITCH)
            Log.e(LOG_TAG, msg, tr);
    }

    /**
     * 打印 INFO 级别日志
     * @param msg 日志信息
     */
    public static void i(String msg) {
        if (LOG_SWITCH)
            Log.i(LOG_TAG, msg);
    }

    /**
     * 打印 INFO 级别日志
     * @param msg 日志信息
     */
    public static void i(String msg, Throwable tr) {
        if (LOG_SWITCH)
            Log.i(LOG_TAG, msg, tr);
    }

    /**
     * 打印 WARNING 级别日志
     * @param msg 日志信息
     */
    public static void w(String msg) {
        if (LOG_SWITCH)
            Log.w(LOG_TAG, msg);
    }

    /**
     * 打印 WARNING 级别日志
     * @param msg 日志信息
     */
    public static void w(String msg, Throwable tr) {
        if (LOG_SWITCH)
            Log.w(LOG_TAG, msg, tr);
    }

    /**
     * 打印 VERBOSE 级别日志
     * @param msg 日志信息
     */
    public static void v(String msg) {
        if (LOG_SWITCH)
            Log.v(LOG_TAG, msg);
    }

    /**
     * 打印 VERBOSE 级别日志
     * @param msg 日志信息
     */
    public static void v(String msg, Throwable tr) {
        if (LOG_SWITCH)
            Log.v(LOG_TAG, msg, tr);
    }
}
