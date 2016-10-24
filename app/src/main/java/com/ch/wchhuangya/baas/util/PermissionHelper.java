package com.ch.wchhuangya.baas.util;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * 权限工具类
 * Created by wchya on 2016/7/5.
 */
public class PermissionHelper {

    /** 权限请求代码 */
    public static final int PERMISSION_REQ_CODE = 1234;

    private PermissionHelper() {
        throw new UnsupportedOperationException("该类不能被实例化！");
    }

    public interface ShowExplain {
        /** 给用户解释权限说明 */
        void showExplainToUser();
    }

    /**
     * 检查单个权限的方法，注意：调用该方法的activity需要重写onRequestPermissionsResult方法，CODE为本类的常量：PERMISSION_REQ_CODE
     * @param activity 上下文
     * @param permissionName 要检查的权限名称，如：Manifest.permission.ACCESS_CHECKIN_PROPERTIES
     * @param showExplain 如果没有授权，并且用户原来已经拒绝过该权限，给用户弹出的解释权限的对话框。注意：在对话框架的授权按钮的事件中，需要进行申请权限的操作
     * @return 如果权限已经授权，返回true；如果权限没有授权，返回false；
     */
    public static boolean checkSinglePermission(Activity activity, String permissionName, ShowExplain showExplain) {
        if (!PermissionHelper.checkSinglePersission(activity, permissionName)) { // 如果没有授权
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permissionName)) { // 如果需要弹窗解释原因
                if (showExplain != null)
                    showExplain.showExplainToUser();
                else
                    grantPermission(activity, permissionName);
            } else {
                grantPermission(activity, permissionName);
            }
            return false;
        } else
            return true;
    }

    /**
     * 检查单个权限是否已经授权
     * @param activity 上下文
     * @param permissionName 要检查的权限名称，如：Manifest.permission.ACCESS_CHECKIN_PROPERTIES
     * @return 如果已经授权，返回true；如果没有授权，返回false；
     */
    public static boolean checkSinglePersission(Activity activity, String permissionName) {
        return ContextCompat.checkSelfPermission(activity, permissionName) == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * 单个权限受权
     * @param activity 上下文
     * @param permissionName 要授予的权限名称，如：Manifest.permission.ACCESS_CHECKIN_PROPERTIES
     */
    public static void grantPermission(Activity activity, String permissionName) {
        ActivityCompat.requestPermissions(activity, new String[]{permissionName}, PermissionHelper.PERMISSION_REQ_CODE);
    }
}
