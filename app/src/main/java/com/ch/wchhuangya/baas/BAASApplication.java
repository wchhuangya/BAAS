package com.ch.wchhuangya.baas;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import cn.bmob.v3.Bmob;

/**
 * BAAS 项目的 Application 类
 * Created by wchya on 16/9/14.
 */
public class BAASApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Bmob.initialize(this, "1ec4aa2ceae3998de0982fe49ff6659e");

        // 用于 AppVersion 表的初始化,只使用一次,使用多次会添加多条记录
        //BmobUpdateAgent.initAppVersion(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
