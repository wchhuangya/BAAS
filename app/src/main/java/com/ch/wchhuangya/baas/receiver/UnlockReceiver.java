package com.ch.wchhuangya.baas.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.ch.wchhuangya.baas.util.CommonHelper;

/**
 * 手机解锁广播监听
 * Created by wchya on 16/10/18.
 */

public class UnlockReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(final Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_USER_PRESENT)) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    CommonHelper.callsSaveAndSync(context);
                }
            }).start();
            /*Intent intent1 = new Intent(context, CallsService.class);
            context.startService(intent1);*/
            /*final Context mContext = context;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Cursor cursor = PhoneDB.getInstance(mContext).findUnSyncRecords();
                    TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                    while (cursor.moveToNext()) {
                        try {
                            final CallsRecord callsRecord = new CallsRecord();
                            callsRecord.setPhoneImei(manager.getDeviceId());
                            callsRecord.setPhoneModel(Build.MODEL);
                            callsRecord.setPhoneNumber(cursor.getString(cursor.getColumnIndex(PhoneDB.CallsRecord.PHONE_NUMBER)));
                            callsRecord.setDateTime(cursor.getString(cursor.getColumnIndex(PhoneDB.CallsRecord.DATA_TIME)));
                            callsRecord.setDateTimeMsec(cursor.getLong(cursor.getColumnIndex(PhoneDB.CallsRecord.DATA_TIME_MSEC)));
                            callsRecord.setCallType(cursor.getString(cursor.getColumnIndex(PhoneDB.CallsRecord.CALL_TYPE)));
                            callsRecord.setSyncFlag(1);
                            final long id = cursor.getLong(cursor.getColumnIndex(PhoneDB.CallsRecord._ID));
                            callsRecord.saveCalls(mContext, new SaveListener() {
                                @Override
                                public void onSuccess() {
                                    PhoneDB.getInstance(mContext).updateSyncFlag(id, PhoneDB.SyncFlag.SYNCED.ordinal());
                                }

                                @Override
                                public void onFailure(int i, String s) {
                                    try {
                                        StringBuilder sb = new StringBuilder();
                                        sb.append(FileHelper.LINE_SEPARATOR).append("同步通话记录失败").append(FileHelper.LINE_SEPARATOR)
                                                .append("通话记录详情: ").append(FileHelper.LINE_SEPARATOR)
                                                .append("姓名: ").append(callsRecord.getName())
                                                .append(" 电话号码: ").append(callsRecord.getPhoneNumber())
                                                .append(" 时间: ").append(callsRecord.getDateTime())
                                                .append(" 类型: ").append(callsRecord.getCallType()).append(FileHelper.LINE_SEPARATOR)
                                                .append("原因: ").append(FileHelper.LINE_SEPARATOR)
                                                .append(s);
                                        FileHelper.saveFile(new ByteArrayInputStream(sb.toString().getBytes("UTF-8")),
                                                FileHelper.BAAS_FILE_PATH + FileHelper.FILE_NAME_PHONE_LOG, TimeHelper.getCurDateTimeByFormat("yyyyMMdd"),
                                                "", true);
                                    } catch (Exception ex) {
                                        LogHelper.e("保存失败原因文件时发生错误!", ex);
                                    }
                                }
                            });
                        } catch (Exception ex) {
                            LogHelper.e("保存失败原因文件时发生错误!", ex);
                        }
                    }
                }
            }).start();*/
        }
    }
}
