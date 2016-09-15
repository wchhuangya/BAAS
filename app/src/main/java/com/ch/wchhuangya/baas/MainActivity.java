package com.ch.wchhuangya.baas;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivity = this;
        mIntent = new Intent(mActivity, TestActivity.class);
        startActivity(mIntent);

        finish();
    }
}
