package com.ch.wchhuangya.baas;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.ch.wchhuangya.baas.util.FileHelper;

/**
 * BAAS 项目的 Application 类
 * Created by wchya on 16/9/14.
 */
public class BAASApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // 用于 AppVersion 表的初始化,只使用一次,使用多次会添加多条记录
        //BmobUpdateAgent.initAppVersion(this);

        // 初始化服务
        /*Intent intent = new Intent(getApplicationContext(), CallsService.class);
        startService(intent);*/

        // 检查+初始化外部存储的目录
        FileHelper.initDir();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
