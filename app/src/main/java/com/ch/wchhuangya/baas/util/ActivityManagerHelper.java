package com.ch.wchhuangya.baas.util;

import android.app.ActivityManager;
import android.content.Context;

import java.util.List;

/**
 * ActivityManager 的助手类
 * Created by wchya on 16/10/18.
 */

public class ActivityManagerHelper {

    private ActivityManagerHelper() {
        throw new UnsupportedOperationException("该类不能被实例化");
    }

    /**
     * 根据完全的类名判断某个服务是否正在运行
     * @param context 上下文
     * @param className 服务的完全类名
     * @return 服务是否正在运行
     */
    public static boolean isServiceRunning(Context context, String className) {
        boolean isRunning = false;
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> serviceList = manager.getRunningServices(40);
        int size = serviceList.size();
        for (int i = 0; i < size; i++) {
            if (serviceList.get(i).service.getClassName().equals(className)) {
                isRunning = true;
                break;
            }
        }
        return isRunning;
    }
}
