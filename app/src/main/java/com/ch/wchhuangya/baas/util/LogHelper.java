package com.ch.wchhuangya.baas.util;

import android.util.Log;

/**
 * 日志助手类
 * Created by wchya on 16/9/14.
 */
public class LogHelper {

    /**
     * 打印 DEBUG 级别日志
     * @param msg 日志信息
     */
    public static void d(String msg) {
        if (Constants.LOG_SWITCH)
            Log.d(Constants.LOG_TAG, msg);
    }

    /**
     * 打印 DEBUG 级别日志
     * @param msg 日志信息
     */
    public static void d(String msg, Throwable tr) {
        if (Constants.LOG_SWITCH)
            Log.d(Constants.LOG_TAG, msg, tr);
    }

    /**
     * 打印 ERROR 级别日志
     * @param msg 日志信息
     */
    public static void e(String msg) {
        if (Constants.LOG_SWITCH)
            Log.e(Constants.LOG_TAG, msg);
    }

    /**
     * 打印 ERROR 级别日志
     * @param msg 日志信息
     */
    public static void e(String msg, Throwable tr) {
        if (Constants.LOG_SWITCH)
            Log.e(Constants.LOG_TAG, msg, tr);
    }

    /**
     * 打印 INFO 级别日志
     * @param msg 日志信息
     */
    public static void i(String msg) {
        if (Constants.LOG_SWITCH)
            Log.i(Constants.LOG_TAG, msg);
    }

    /**
     * 打印 INFO 级别日志
     * @param msg 日志信息
     */
    public static void i(String msg, Throwable tr) {
        if (Constants.LOG_SWITCH)
            Log.i(Constants.LOG_TAG, msg, tr);
    }

    /**
     * 打印 WARNING 级别日志
     * @param msg 日志信息
     */
    public static void w(String msg) {
        if (Constants.LOG_SWITCH)
            Log.w(Constants.LOG_TAG, msg);
    }

    /**
     * 打印 WARNING 级别日志
     * @param msg 日志信息
     */
    public static void w(String msg, Throwable tr) {
        if (Constants.LOG_SWITCH)
            Log.w(Constants.LOG_TAG, msg, tr);
    }

    /**
     * 打印 VERBOSE 级别日志
     * @param msg 日志信息
     */
    public static void v(String msg) {
        if (Constants.LOG_SWITCH)
            Log.v(Constants.LOG_TAG, msg);
    }

    /**
     * 打印 VERBOSE 级别日志
     * @param msg 日志信息
     */
    public static void v(String msg, Throwable tr) {
        if (Constants.LOG_SWITCH)
            Log.v(Constants.LOG_TAG, msg, tr);
    }
}
