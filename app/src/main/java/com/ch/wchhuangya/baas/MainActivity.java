package com.ch.wchhuangya.baas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ch.wchhuangya.baas.activity.dbdatashow.PhoneDBShowActivity;
import com.ch.wchhuangya.baas.components.SideSlip;
import com.ch.wchhuangya.baas.util.Constants;

import cn.bmob.push.BmobPush;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobInstallation;
import cn.bmob.v3.update.BmobUpdateAgent;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);


        // 初始化BmobSDK
        Bmob.initialize(this, Constants.BMOB_APP_KEY);
        // 使用推送服务时的初始化操作
        BmobInstallation.getCurrentInstallation(this).save();
        // 启动推送服务
        BmobPush.startWork(this);
        // 检测更新
        BmobUpdateAgent.update(this);
        // 在任何网络下都检测更新
        BmobUpdateAgent.setUpdateOnlyWifi(false);

        init();
    }

    private void init() {
        findViewById(R.id.main_content_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((SideSlip) findViewById(R.id.main_sideslip)).leftToggleShow(true);
            }
        });
        findViewById(R.id.main_content_btn).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent = new Intent(mActivity, PhoneDBShowActivity.class);
                startActivity(intent);
                return true;
            }
        });
    }
}
