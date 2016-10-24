package com.ch.wchhuangya.baas;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;

/**
 * Activity 的基类
 * Created by wchya on 16/9/15.
 */
public class BaseActivity extends FragmentActivity {

    /** 公用的 Activity 变量 */
    protected Activity mActivity = this;
    /** 公用的 Intent 变量 */
    protected Intent mIntent;
}
