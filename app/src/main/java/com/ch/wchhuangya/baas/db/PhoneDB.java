package com.ch.wchhuangya.baas.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import com.ch.wchhuangya.baas.util.LogHelper;

/**
 * 电话数据库
 * Created by wchya on 16/10/4.
 */

public class PhoneDB extends SQLiteOpenHelper {
    private static final String DB_NAME = "Phone.db";
    private static final int DB_VERSION = 1;
    private static PhoneDB instance;
    private static SQLiteDatabase mDb;

    /** 通话记录表 */
    private static final String TABLE_NAME_CALLS_RECORD = "CALLS_RECORD";
    /** 系统表与本地表同步记录 */
    public static final String TABLE_NAME_SAVE_RECORD = "SAVE_RECORD";

    private PhoneDB(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CallsRecord.CREATE_TABLE);
        db.execSQL(SaveRecord.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // @ TODO 升级数据库表的代码写在这里
        //db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_CALLS_RECORD);
        //onCreate(db);
    }

    /** 获取 PhoneDB 的单例 */
    public static PhoneDB getInstance(Context context) {
        synchronized (PhoneDB.class) {
            if (instance == null)
                instance = new PhoneDB(context, DB_NAME, null, DB_VERSION);
        }
        return instance;
    }

    /** 检查数据库是否可读写,如果不可读写,让其可读写 */
    private static void checkDB() {
        synchronized (PhoneDB.class) {
            if (mDb == null || !mDb.isOpen())
                mDb = instance.getWritableDatabase();
        }
    }

    public enum CallType {
        TYPE_IDLE("挂断/要拨出"),
        TYPE_RINGING("响铃"),
        TYPE_OFFHOOK("接通");

        private String describe;

        CallType (String describe) {
            this.describe = describe;
        }

        /** 根据 value 返回对应的 describe */
        public static String getDescrie(CallType phoneType) {
            for (CallType type : CallType.values()) {
                if (type == phoneType)
                    return type.describe;
            }
            return CallType.getDescrie(CallType.TYPE_IDLE);
        }
    }

    public enum SyncFlag {
        UNSYNYED("未同步"),
        SYNCED("同步");

        private String describe;

        SyncFlag(String describe) {
            this.describe = describe;
        }

        /** 获取描述 */
        public static String getDescribe(int index) {
            for (SyncFlag sf : SyncFlag.values())
                if (sf.ordinal() == index)
                    return sf.getDescribe();
            return "没有找到匹配的值";
        }

        public String getDescribe() {
            return describe;
        }
    }

    /** 电话记录表 */
    public static abstract class CallsRecord implements BaseColumns {
        /** 手机的 IMEI */
        static final String PHONE_IMEI = "PHONE_IMEI";
        /** 手机的型号 */
        static final String PHONE_MODEL = "PHONE_MODEL";
        /** 电话号码 */
        public static final String PHONE_NUMBER = "PHONE_NUMBER";
        /** 姓名 */
        public static final String NAME = "NAME";
        /** 日期时间(yyyy-MM-dd HH:mm:ss 格式) */
        public static final String DATA_TIME = "DATE_TIME";
        /** 日期时间(毫秒数) */
        public static final String DATA_TIME_MSEC = "DATE_TIME_MSEC";
        /** 呼叫类型 */
        public static final String CALL_TYPE = "CALL_TYPE";
        /** 是否同步过 */
        public static final String SYNC_FLAG = "SYNC_FLAG";

        private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME_CALLS_RECORD + " (" + _ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
                + PHONE_IMEI + " TEXT, " + PHONE_MODEL + " TEXT, " + PHONE_NUMBER + " TEXT, " + NAME + " TEXT, "
                + DATA_TIME + " TEXT, " + DATA_TIME_MSEC + " TEXT, " + CALL_TYPE + " TEXT, " + SYNC_FLAG + " INTEGER)";
    }

    /** 系统表与本地表同步记录表 */
    public static abstract class SaveRecord implements BaseColumns {
        /** 表名 */
        public static final String TABLE_NAME = "TABLE_NAME";
        /** 时间戳 */
        public static final String DATE_TIME_MSEC = "DATE_TIME_MSEC";

        private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME_SAVE_RECORD + " (" + _ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
                + TABLE_NAME + " TEXT, " + DATE_TIME_MSEC + " TEXT)";
    }

    /**
     * 获取所有的数据
     */
    public Cursor findAll() {
        checkDB();
        String sql = "SELECT * FROM " + TABLE_NAME_CALLS_RECORD + " ORDER BY " + CallsRecord.DATA_TIME + " DESC";
        return mDb.rawQuery(sql, null);
    }

    /**
     * 获取所有的数据
     */
    public Cursor findBySearch(String search) {
        checkDB();
        String sql = "SELECT * FROM " + TABLE_NAME_CALLS_RECORD + " where " + search + " ORDER BY " + CallsRecord.DATA_TIME + " DESC";
        return mDb.rawQuery(sql, null);
    }

    /**
     * 保存通话记录
     * @param phoneImei 手机 IMEI
     * @param phoneModel 手机型号
     * @param phoneNumber 手机号码
     * @param dateTime 日期时间(格式: yyyy-MM-dd HH:mm:ss)
     * @param dateTimeMsec 日期时间(时间戳)
     * @param callType 呼叫类型
     * @param syncFlag 同步标识,0-未同步,1-同步
     */
    public long saveCalls(String phoneImei, String phoneModel, String phoneNumber, String name, String dateTime, long dateTimeMsec, String callType, int syncFlag) {
        checkDB();
        /*String sql = "insert into " + TABLE_NAME_CALLS_RECORD + "(" + CallsRecord.PHONE_IMEI + ", " + CallsRecord.PHONE_MODEL + ", "
                    + CallsRecord.PHONE_NUMBER + ", " + CallsRecord.DATA_TIME + ", " + CallsRecord.DATA_TIME_MSEC + ", "
                    + CallsRecord.CALL_TYPE + ", " + CallsRecord.SYNC_FLAG + ") values('" + phoneImei + "', '" + phoneModel + "', '"
                    + phoneNumber + "', '" + dateTime + "', '" + dateTimeMsec + "', '" + callType + "', " + syncFlag + ")";
        mDb.rawQuery(sql, null);*/
        ContentValues contentValues = new ContentValues();
        contentValues.put(CallsRecord.PHONE_IMEI, phoneImei);
        contentValues.put(CallsRecord.PHONE_MODEL, phoneModel);
        contentValues.put(CallsRecord.PHONE_NUMBER, phoneNumber);
        contentValues.put(CallsRecord.NAME, name);
        contentValues.put(CallsRecord.DATA_TIME, dateTime);
        contentValues.put(CallsRecord.DATA_TIME_MSEC, dateTimeMsec);
        contentValues.put(CallsRecord.CALL_TYPE, callType);
        contentValues.put(CallsRecord.SYNC_FLAG, syncFlag);
        return mDb.insert(TABLE_NAME_CALLS_RECORD, null, contentValues);
    }

    /**
     * 修改记录的同步标识
     * @param id 要修改记录的 id
     * @param flag 同步标识值
     * @return 受影响的记录行数
     */
    public int updateSyncFlag(long id, int flag) {
        checkDB();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CallsRecord.SYNC_FLAG, flag);
        return mDb.update(TABLE_NAME_CALLS_RECORD, contentValues, CallsRecord._ID + " = ?", new String[]{id + ""});
    }

    /**
     * 是否需要保存记录
     * @param phoneNumber 拨打/接听的电话号码(可能为空)
     * @param dateTimeMsec 动作发生的时间戳
     * @param type 动作类型
     */
    public boolean needSave(String phoneNumber, long dateTimeMsec, String type) {
        Cursor cursor = null;
        try {
            checkDB();
            String sql = "select " + CallsRecord._ID + " from " + TABLE_NAME_CALLS_RECORD
                    + " where " + dateTimeMsec + " - " + CallsRecord.DATA_TIME_MSEC + " < 2 and " + CallsRecord.CALL_TYPE + " = '" + type
                    + "' and " + CallsRecord.PHONE_NUMBER + " = '" + phoneNumber + "'";
            cursor = mDb.rawQuery(sql, null);
            return cursor.getCount() == 0;
        } catch (Exception e) {
            LogHelper.e("查询是否 Calls 是否需要保存时出错！", e);
            if (cursor != null)
                cursor.close();
        }
        return false;
    }

    /**
     * 获取所有未同步的记录
     */
    public Cursor findUnSyncRecords() {
        checkDB();
        String sql = "select * from " + TABLE_NAME_CALLS_RECORD + " where " + CallsRecord.SYNC_FLAG + " = 0";
        return mDb.rawQuery(sql, null);
    }

    /**
     * 本地表是否与系统表同步过
     * @param tableName 本地表名称
     */
    public boolean ifSaved(String tableName) {
        Cursor cursor = null;
        try {
            checkDB();
            String sql = "select " + SaveRecord._ID + " from " + TABLE_NAME_SAVE_RECORD + " where " + SaveRecord.TABLE_NAME + " = '" + tableName + "'";
            cursor = mDb.rawQuery(sql, null);
            return cursor != null && cursor.getCount() > 0;
        } catch (Exception e) {
            LogHelper.e("查询" + tableName + "表与系统表是否同步时出错！", e);
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return false;
    }

    /**
     * 获取本地表与系统表同步的时间
     * @param tableName 本地表名称
     */
    public String getSaveDatetimeMsec(String tableName) {
        Cursor cursor = null;
        try {
            checkDB();
            String sql = "select " + SaveRecord.DATE_TIME_MSEC + " from " + TABLE_NAME_SAVE_RECORD + " where " + SaveRecord.TABLE_NAME
                    + " = '" + tableName + "'";
            cursor = mDb.rawQuery(sql, null);
            if (cursor.getCount() > 0) {
                if (cursor.moveToFirst())
                    return cursor.getString(cursor.getColumnIndex(SaveRecord.DATE_TIME_MSEC));
            }
        } catch (Exception e) {
            LogHelper.e("查询 " + tableName + " 保存时间时出错！", e);
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * 保存本地表与系统表的同步记录
     * @param tableName 本地表名称
     * @param datetimeMsec 同步记录中，最后一条记录的时间戳
     */
    public long saveSaveRecords(String tableName, long datetimeMsec) {
        checkDB();

        ContentValues contentValues = new ContentValues();
        contentValues.put(SaveRecord.TABLE_NAME, tableName);
        contentValues.put(SaveRecord.DATE_TIME_MSEC, datetimeMsec);

        return mDb.insert(TABLE_NAME_SAVE_RECORD, null, contentValues);
    }

    /**
     * 修改本地表与系统表的同步记录
     * @param tableName 本地表名称
     * @param datetimeMsec 同步记录中，最后一条记录的时间戳
     */
    public int updateSaveRecord(String tableName, long datetimeMsec) {
        checkDB();

        String whereClause = SaveRecord.TABLE_NAME + " = ?";
        String[] whereArgs = {tableName};

        ContentValues contentValues = new ContentValues();
        contentValues.put(SaveRecord.DATE_TIME_MSEC, datetimeMsec);

        return mDb.update(TABLE_NAME_SAVE_RECORD, contentValues, whereClause, whereArgs);
    }
}
