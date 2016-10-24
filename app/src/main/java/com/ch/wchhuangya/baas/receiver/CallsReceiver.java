package com.ch.wchhuangya.baas.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import com.ch.wchhuangya.baas.db.PhoneDB;
import com.ch.wchhuangya.baas.util.TimeHelper;

/**
 * 电话状态广播接收器(拨打电话、呼叫状态改变)
 * Created by wchya on 16/10/7.
 */

public class CallsReceiver extends BroadcastReceiver {
    private TelephonyManager manager;
    private Context mContext;

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if ("android.intent.action.NEW_OUTGOING_CALL".equals(action) // 拨打电话
                || "android.intent.action.PHONE_STATE".equals(action)) { // 电话状态改变
            manager.listen(new StateListener(), PhoneStateListener.LISTEN_CALL_STATE);
        }
        mContext = context;
    }

    class StateListener extends PhoneStateListener {
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            String type = "";
            switch (state) {
                case TelephonyManager.CALL_STATE_IDLE: // 挂断,要拨出
                    type = PhoneDB.CallType.getDescrie(PhoneDB.CallType.TYPE_IDLE);
                    break;
                case TelephonyManager.CALL_STATE_RINGING: // 响铃
                    type = PhoneDB.CallType.getDescrie(PhoneDB.CallType.TYPE_RINGING);
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK: // 接通
                    type = PhoneDB.CallType.getDescrie(PhoneDB.CallType.TYPE_OFFHOOK);
                    break;
            }
            if (PhoneDB.getInstance(mContext).needSave(incomingNumber, TimeHelper.getCurTimstamp(), type))
                new SaveData(mContext, incomingNumber, type).start();
        }
    }

    class SaveData extends Thread {
        private Context mContext;
        private String phoneNumber, type;

        public SaveData(Context context, String phoneNumber, String type) {
            this.phoneNumber = phoneNumber;
            this.type = type;
            mContext = context;
        }

        @Override
        public void run() {
            /*try {
                final CallsRecord callsRecord = new CallsRecord();
                callsRecord.setPhoneImei(manager.getDeviceId());
                callsRecord.setPhoneModel(Build.MODEL);
                callsRecord.setPhoneNumber(phoneNumber);
                callsRecord.setDateTime(TimeHelper.getCurDateTimeByFormat(null));
                callsRecord.setDateTimeMsec(TimeHelper.getTimestamp(TimeHelper.getCurDateTimeByFormat(null), TimeHelper.LONG_DATE_FORMAT));
                callsRecord.setCallType(type);
                callsRecord.setSyncFlag(0);
                final long id = PhoneDB.getInstance(mContext).saveCalls(callsRecord.getPhoneImei(), callsRecord.getPhoneModel(), callsRecord.getPhoneNumber(),
                        "", callsRecord.getDateTime(), callsRecord.getDateTimeMsec(), callsRecord.getCallType(), callsRecord.getSyncFlag());
                if (id != -1) {
                    callsRecord.setSyncFlag(1);
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
                                        .append("电话号码: ").append(callsRecord.getPhoneNumber())
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
                } else {
                    StringBuilder sb = new StringBuilder();
                    sb.append(FileHelper.LINE_SEPARATOR).append("插入通话记录记录失败").append(FileHelper.LINE_SEPARATOR)
                            .append("通话记录详情: ").append(FileHelper.LINE_SEPARATOR)
                            .append("电话号码: ").append(callsRecord.getPhoneNumber())
                            .append(" 时间: ").append(callsRecord.getDateTime())
                            .append(" 类型: ").append(callsRecord.getCallType()).append(FileHelper.LINE_SEPARATOR);
                    FileHelper.saveFile(new ByteArrayInputStream(sb.toString().getBytes("UTF-8")),
                            FileHelper.BAAS_FILE_PATH + FileHelper.FILE_NAME_PHONE_LOG, TimeHelper.getCurDateTimeByFormat("yyyyMMdd"),
                            "", true);
                }
            } catch (Exception ex) {
                LogHelper.e("保存失败原因文件时发生错误!", ex);
            }*/
        }
    }
}
