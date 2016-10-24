package com.ch.wchhuangya.baas.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import cn.bmob.push.PushConstants;

/**
 * Bmob 推送服务接收器
 * Created by wchya on 16/10/20.
 */

public class BmobPushReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(PushConstants.ACTION_MESSAGE)) {
            Toast.makeText(context, "好像来消息了~", Toast.LENGTH_LONG).show();
        }
    }
}
