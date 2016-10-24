package com.ch.wchhuangya.baas.service;

import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.ch.wchhuangya.baas.contentobserver.CallsCO;

/**
 * 通话记录服务
 * Created by wchya on 16/10/18.
 */

public class CallsService extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        CallsCO callsCO = new CallsCO(getApplicationContext(), null);
        getApplicationContext().getContentResolver().registerContentObserver(Uri.parse("content://call_log/calls"), true, callsCO);
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
