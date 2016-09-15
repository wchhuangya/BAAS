package com.ch.wchhuangya.baas;

import android.content.Intent;
import android.os.Bundle;

import cn.bmob.v3.update.BmobUpdateAgent;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 检测更新
        BmobUpdateAgent.update(this);
        // 在任何网络下都检测更新
        BmobUpdateAgent.setUpdateOnlyWifi(false);

        mActivity = this;
        mIntent = new Intent(mActivity, TestActivity.class);
        startActivity(mIntent);

        finish();
    }
}
