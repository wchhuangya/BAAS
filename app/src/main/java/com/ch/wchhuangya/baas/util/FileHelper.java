package com.ch.wchhuangya.baas.util;

import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * 文件助手类
 * Created by wchya on 16/10/12.
 */

public class FileHelper {
    /** 项目外部存储上的根目录 */
    public static final String BAAS_ROOT_FILE = "baas" + File.separator;
    /** SD 卡路径 */
    public static final String SDCARD_ROOT_PATH = Environment.getExternalStorageDirectory().getPath() + File.separator;
    /** 项目外部存储根目录的路径 */
    public static final String BAAS_FILE_PATH = SDCARD_ROOT_PATH + BAAS_ROOT_FILE;
    /** 记录日志的目录 */
    public static final String FILE_NAME_LOG = "log" + File.separator;
    /** 记录通话日志的目录 */
    public static final String FILE_NAME_PHONE_LOG = "log" + File.separator + "c_log" + File.separator;
    /** 换行符 */
    public static final String LINE_SEPARATOR = "\r\n";

    /** 应用启动时,检查和初始化目录的方法 */
    public static void initDir() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                if (isExternalStorageWritable()) {
                    // 检查初始化外部存储根目录
                    File file = new File(BAAS_FILE_PATH);
                    if (!file.exists())
                        file.mkdirs();

                    // 检查初始化外部存储日志目录
                    file = new File(BAAS_FILE_PATH + FILE_NAME_LOG);
                    if (!file.exists())
                        file.mkdirs();

                    // 检查初始化外部存储通话日志目录
                    file = new File(BAAS_FILE_PATH + FILE_NAME_PHONE_LOG);
                    if (!file.exists())
                        file.mkdirs();
                } else {
                    LogHelper.e("当前手机的外部存储不可用!");
                }
            }
        }).start();
    }

    /* 检查外部存储是否可用、可读写 */
    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /* 检查外部存储是否可用、可读 */
    public static boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    public static void saveTextFile(String[] text, String path, String fileName, boolean append) {
        try {
            File file = new File(path + fileName);
            FileInputStream fis = new FileInputStream(file);
            FileOutputStream fos = new FileOutputStream(file, append);
            for (String s : text) {
                byte[] txt = new byte[1024];
                int number = 0;
                while ((number = fis.read(txt)) != -1) {
                    fos.write(txt, 0, number);
                    fos.flush();
                }
            }
        } catch (Exception ex) {
            LogHelper.e("保存文本信息时发生错误!", ex);
        }
    }

    /**
     * 把 InputStream 保存到文件中
     * @param is 要保存的 InputStream
     * @param path 文件保存的路径
     * @param fileName 文件名称
     * @param suffic 文件后缀(带.)
     * @param append 是否追回,true-追回;false-覆盖;
     */
    public static void saveFile(InputStream is, String path, String fileName, String suffic, boolean append) {
        FileOutputStream fos = null;
        try {
            if (is != null) {
                File file = new File(path);
                if (!file.exists()) // 检查文件存放的目录,不存在,就创建
                    file.mkdirs();
                file = new File(path + fileName + suffic);

                fos = new FileOutputStream(file, append);

                int length = 0;
                byte[] read = new byte[1024];
                while ((length = is.read(read)) != -1) {
                    fos.write(read, 0, length);
                    fos.flush();
                }
            }
        } catch (Exception ex) {
            LogHelper.e("保存文件时发生错误!", ex);
        } finally {

            if (is != null)
                try {
                        is.close();
                } catch (Exception e) {
                    LogHelper.e("保存文件关闭输入流时发生错误!", e);
                }
            if (fos != null)
                try {
                    if (fos != null)
                        fos.close();
                } catch (Exception e) {
                    LogHelper.e("保存文件关闭输出流时发生错误!", e);
                }
        }
    }
}
