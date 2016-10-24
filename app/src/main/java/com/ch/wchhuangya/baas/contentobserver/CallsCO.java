package com.ch.wchhuangya.baas.contentobserver;

import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;

import com.ch.wchhuangya.baas.util.LogHelper;

/**
 * 通话记录内容观察者
 * Created by wchya on 16/10/18.
 */

public class CallsCO extends ContentObserver {
    private Context mContext;
    /**
     * Creates a content observer.
     *
     * @param handler The handler to run {@link #onChange} on, or null if none.
     */
    public CallsCO(Context context, Handler handler) {
        super(handler);
        this.mContext = context;
    }

    /** 该方法在 API 16 以下调用 */
    @Override
    public void onChange(boolean selfChange) {
        LogHelper.i("进来了");
        saveAndSync.start();
    }

    /** 该方法仅仅只能在 API 16 及以上才能被调用 */
    @Override
    public void onChange(boolean selfChange, Uri uri) {
        LogHelper.i("进来喽");
        saveAndSync.start();
    }

    private Thread saveAndSync = new Thread(new Runnable() {
        @Override
        public void run() {
            /*try {
                Cursor cursor = mContext.getContentResolver().query(Uri.parse("content://call_log/calls"), null, null, null, CallLog.Calls.DEFAULT_SORT_ORDER);
                for (int i = 0; i < 1; i++) {
                    TelephonyManager manager = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);

                    cursor.moveToNext();
                    long datetimeMsec = cursor.getLong(cursor.getColumnIndex(CallLog.Calls.DATE));
                    int type = cursor.getInt(cursor.getColumnIndex(CallLog.Calls.TYPE));
                    int duration = cursor.getInt(cursor.getColumnIndex(CallLog.Calls.DURATION));

                    CallsRecord callsRecord = new CallsRecord();
                    callsRecord.setPhoneImei(manager.getDeviceId());
                    callsRecord.setPhoneModel(Build.MODEL);
                    callsRecord.setPhoneNumber(cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER)));
                    callsRecord.setName(cursor.getString(cursor.getColumnIndex(CallLog.Calls.CACHED_NAME)));
                    callsRecord.setDateTime(TimeHelper.changeTimestampToTime(datetimeMsec, null));
                    callsRecord.setDateTimeMsec(datetimeMsec);
                    callsRecord.setCallType(StringHelper.getCallType(type, duration));
                    callsRecord.setSyncFlag(PhoneDB.SyncFlag.UNSYNYED.ordinal());

                    final long id = PhoneDB.getInstance(mContext).saveCalls(callsRecord.getPhoneImei(), callsRecord.getPhoneModel(),
                            callsRecord.getPhoneNumber(), callsRecord.getName(), callsRecord.getDateTime(), callsRecord.getDateTimeMsec(),
                            callsRecord.getCallType(), callsRecord.getSyncFlag());

                    callsRecord.setSyncFlag(PhoneDB.SyncFlag.SYNCED.ordinal());
                    callsRecord.saveCalls(mContext, new SaveListener() {
                        @Override
                        public void onSuccess() {
                            // 同步成功后，更改本地数据库状态
                            PhoneDB.getInstance(mContext).updateSyncFlag(id, PhoneDB.SyncFlag.SYNCED.ordinal());
                        }

                        @Override
                        public void onFailure(int i, String s) {
                            // 同步失败，记录失败原因
                            StringBuilder sb = new StringBuilder();
                            sb.append(FileHelper.LINE_SEPARATOR)
                                    .append(TimeHelper.getCurDateTimeByFormat(null)).append(FileHelper.LINE_SEPARATOR)
                                    .append("Calls 记录同步失败：").append(FileHelper.LINE_SEPARATOR)
                                    .append("原因：").append(s).append(FileHelper.LINE_SEPARATOR);
                            ByteArrayInputStream is = new ByteArrayInputStream(sb.toString().getBytes());
                            FileHelper.saveFile(is, FileHelper.BAAS_FILE_PATH + FileHelper.FILE_NAME_PHONE_LOG,
                                    TimeHelper.getCurDateTimeByFormat("yyyyMMdd"), "", true);
                        }
                    });
                }
            } catch (Exception ex) {
                // 操作失败，记录失败原因
                StringBuilder sb = new StringBuilder();
                sb.append(FileHelper.LINE_SEPARATOR)
                        .append(TimeHelper.getCurDateTimeByFormat(null)).append(FileHelper.LINE_SEPARATOR)
                        .append("记录、同步 Calls 记录失败：").append(FileHelper.LINE_SEPARATOR)
                        .append("原因：").append(ex.getMessage()).append(FileHelper.LINE_SEPARATOR);
                ByteArrayInputStream is = new ByteArrayInputStream(sb.toString().getBytes());
                FileHelper.saveFile(is, FileHelper.BAAS_FILE_PATH + FileHelper.FILE_NAME_PHONE_LOG,
                        TimeHelper.getCurDateTimeByFormat("yyyyMMdd"), "", true);
            }*/
        }
    });
}
