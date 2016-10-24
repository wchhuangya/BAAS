package com.ch.wchhuangya.baas.model;

/**
 * 电话记录表实体
 * Created by wchya on 16/10/4.
 */

public class CallsRecord extends BaseModel {
    /** 手机电话号码,可能为空 */
    private String phoneNumber;
    /** 手机型号 */
    private String phoneModel;
    /** 手机 IMEI */
    private String phoneImei;
    /** 姓名 */
    private String name;
    /** 日期时间(yyyy-MM-dd HH:mm:ss 格式) */
    private String dateTime;
    /** 日期时间(毫秒数) */
    private Long dateTimeMsec;
    /** 呼叫类型 */
    private String callType;
    /** 是否同步,0-未同步,1-同步 */
    private Integer syncFlag;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneModel() {
        return phoneModel;
    }

    public void setPhoneModel(String phoneModel) {
        this.phoneModel = phoneModel;
    }

    public String getPhoneImei() {
        return phoneImei;
    }

    public void setPhoneImei(String phoneImei) {
        this.phoneImei = phoneImei;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public Long getDateTimeMsec() {
        return dateTimeMsec;
    }

    public void setDateTimeMsec(Long dateTimeMsec) {
        this.dateTimeMsec = dateTimeMsec;
    }

    public String getCallType() {
        return callType;
    }

    public void setCallType(String callType) {
        this.callType = callType;
    }

    public Integer getSyncFlag() {
        return syncFlag;
    }

    public void setSyncFlag(Integer syncFlag) {
        this.syncFlag = syncFlag;
    }
}
