package com.ch.wchhuangya.baas.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.ch.wchhuangya.baas.util.CommonHelper;

/**
 * 开机广播接收器
 * Created by wchya on 16/10/18.
 */

public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(final Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            /*Intent intent1 = new Intent(context, CallsCO.class);
            context.startService(intent1);*/
            new Thread(new Runnable() {
                @Override
                public void run() {
                    CommonHelper.callsSaveAndSync(context);
                }
            }).start();
        }
    }
}
