package com.ch.wchhuangya.baas.util;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.provider.CallLog;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;

import com.ch.wchhuangya.baas.db.PhoneDB;
import com.ch.wchhuangya.baas.model.CallsRecord;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import cn.bmob.v3.listener.SaveListener;

/**
 * 一般通用助手类（也就是把不知道如何分类的方法放在这个类中）
 * Created by wchya on 16/10/21.
 */

public class CommonHelper {

    private CommonHelper() {
        throw new UnsupportedOperationException("该类不能被实例化！");
    }

    /**
     * 1. 把未保存的通话记录保存到本地数据库
     * 2. 把本地未同步的通话记录同步到云端
     */
    public static void callsSaveAndSync(final Context context) {
        try {
            // 如果没有读取通讯录、读取通话记录的权限，直接退出 TODO 最后添加发送消息推送的代码
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED)
                return;

            // 从 CallLog 内容提供器中获取数据（查找范围：今天）
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 1);

            SimpleDateFormat sdf = new SimpleDateFormat(TimeHelper.LONG_DATE_FORMAT);
            long datetimeMsec = TimeHelper.getTimestamp(sdf.format(calendar.getTime()), TimeHelper.LONG_DATE_FORMAT);
            TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            long datetimeMsec1 = -1;

            // 查询与系统表的同步情况
            String dm = PhoneDB.getInstance(context).getSaveDatetimeMsec(PhoneDB.TABLE_NAME_SAVE_RECORD);
            if (StringHelper.isNotEmpty(dm)) // 如果有记录，把记录中的时间取出来
                datetimeMsec1 = Long.parseLong(dm);

            // 拼凑查询条件：如果已经保存过，查找大于上次保存时间的记录；如果没有保存过，查找今天的记录；
            String selection = null;
            if (datetimeMsec1 != -1)
                selection = CallLog.Calls.DATE + " > " + datetimeMsec1;
            else
                selection = CallLog.Calls.DATE + " > " + datetimeMsec;

            // 查询系统表的新记录
            Cursor cursor2 = context.getContentResolver().query(CallLog.Calls.CONTENT_URI, null, selection, null, CallLog.Calls.DATE + " ASC");
            long date = -1;

            // 把新的记录同步到本地表
            if (cursor2 != null) {
                while (cursor2.moveToNext()) {
                    try {
                        int type = cursor2.getInt(cursor2.getColumnIndex(CallLog.Calls.TYPE)),
                                duration = cursor2.getInt(cursor2.getColumnIndex(CallLog.Calls.DURATION));
                        date = cursor2.getLong(cursor2.getColumnIndex(CallLog.Calls.DATE));
                        CallsRecord callsRecord = new CallsRecord();

                        callsRecord.setPhoneImei(manager.getDeviceId());
                        callsRecord.setPhoneModel(Build.MODEL);
                        callsRecord.setPhoneNumber(cursor2.getString(cursor2.getColumnIndex(CallLog.Calls.NUMBER)));
                        callsRecord.setName(cursor2.getString(cursor2.getColumnIndex(CallLog.Calls.CACHED_NAME)));
                        callsRecord.setDateTime(TimeHelper.changeTimestampToTime(date, TimeHelper.LONG_DATE_FORMAT));
                        callsRecord.setDateTimeMsec(date);
                        callsRecord.setCallType(StringHelper.getCallType(type, duration));
                        callsRecord.setSyncFlag(PhoneDB.SyncFlag.UNSYNYED.ordinal());

                        PhoneDB.getInstance(context).saveCalls(callsRecord.getPhoneImei(), callsRecord.getPhoneModel(), callsRecord.getPhoneNumber(),
                                callsRecord.getName(), callsRecord.getDateTime(), callsRecord.getDateTimeMsec(), callsRecord.getCallType(),
                                callsRecord.getSyncFlag());
                    } catch (Exception e) {
                        LogHelper.e("保存通话记录失败", e);
                        StringBuilder sb = new StringBuilder();
                        sb.append(FileHelper.LINE_SEPARATOR)
                                .append("保存通话记录失败    ").append(TimeHelper.getCurDateTimeByFormat(null)).append(FileHelper.LINE_SEPARATOR)
                                .append("原因：").append(FileHelper.LINE_SEPARATOR)
                                .append(e.getMessage());
                        FileHelper.saveFile(new ByteArrayInputStream(sb.toString().getBytes("UTF-8")),
                                FileHelper.BAAS_FILE_PATH + FileHelper.FILE_NAME_PHONE_LOG,
                                TimeHelper.getCurDateTimeByFormat(TimeHelper.ONLY_DATE_FORMAT2), "", true);
                    }
                }
            }

            if (date != -1) { // 添加/修改本地表与系统表的同步记录
                if (!PhoneDB.getInstance(context).ifSaved(PhoneDB.TABLE_NAME_SAVE_RECORD))
                    PhoneDB.getInstance(context).saveSaveRecords(PhoneDB.TABLE_NAME_SAVE_RECORD, date);
                else
                    PhoneDB.getInstance(context).updateSaveRecord(PhoneDB.TABLE_NAME_SAVE_RECORD, date);
            }

            // 把未同步的记录同步到服务器
            Cursor cursor = PhoneDB.getInstance(context).findUnSyncRecords();
            if (cursor != null)
                while (cursor.moveToNext()) {
                    final long id = cursor.getLong(cursor.getColumnIndex(PhoneDB.CallsRecord._ID));
                    CallsRecord callsRecord = new CallsRecord();

                    callsRecord.setPhoneImei(manager.getDeviceId());
                    callsRecord.setPhoneModel(Build.MODEL);
                    callsRecord.setPhoneNumber(cursor.getString(cursor.getColumnIndex(PhoneDB.CallsRecord.PHONE_NUMBER)));
                    callsRecord.setName(cursor.getString(cursor.getColumnIndex(PhoneDB.CallsRecord.NAME)));
                    callsRecord.setDateTime(cursor.getString(cursor.getColumnIndex(PhoneDB.CallsRecord.DATA_TIME)));
                    callsRecord.setDateTimeMsec(cursor.getLong(cursor.getColumnIndex(PhoneDB.CallsRecord.DATA_TIME_MSEC)));
                    callsRecord.setCallType(cursor.getString(cursor.getColumnIndex(PhoneDB.CallsRecord.CALL_TYPE)));
                    callsRecord.setSyncFlag(PhoneDB.SyncFlag.SYNCED.ordinal());

                    callsRecord.save(context, new SaveListener() {
                        @Override
                        public void onSuccess() {
                            PhoneDB.getInstance(context).updateSyncFlag(id, PhoneDB.SyncFlag.SYNCED.ordinal());
                        }

                        @Override
                        public void onFailure(int i, String s) {
                            LogHelper.e("同步通话记录失败：" + s);
                            try {
                                StringBuilder sb = new StringBuilder();
                                sb.append(FileHelper.LINE_SEPARATOR)
                                        .append("同步通话记录失败    ").append(TimeHelper.getCurDateTimeByFormat(null)).append(FileHelper.LINE_SEPARATOR)
                                        .append("原因：").append(FileHelper.LINE_SEPARATOR)
                                        .append(s);
                                FileHelper.saveFile(new ByteArrayInputStream(sb.toString().getBytes("UTF-8")),
                                        FileHelper.BAAS_FILE_PATH + FileHelper.FILE_NAME_PHONE_LOG,
                                        TimeHelper.getCurDateTimeByFormat(TimeHelper.ONLY_DATE_FORMAT2), "", true);
                            } catch (UnsupportedEncodingException e) {
                                LogHelper.e("保存通话记录失败：" + s);
                            }
                        }
                    });
                }
        } catch (Exception e) {
            LogHelper.e("保存、同步通话记录失败", e);
        }
    }
}
