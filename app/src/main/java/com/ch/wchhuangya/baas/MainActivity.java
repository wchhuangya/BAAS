package com.ch.wchhuangya.baas;

import android.os.Bundle;
import android.view.View;

import com.ch.wchhuangya.baas.components.SideSlip;

import cn.bmob.v3.update.BmobUpdateAgent;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

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
    }
}
