package com.ch.wchhuangya.baas.enums;

/**
 * Bmob 错误枚举
 * Created by wchya on 16/9/15.
 */
public enum BmobError {
    ERROR_9001(9001, "AppKey is Null, Please initialize BmobSDK", "Application Id为空，请初始化"),
    ERROR_9002(9002, "Parse data error", "解析返回数据出错"),
    ERROR_9003(9003, "upload file error", "上传文件出错"),
    ERROR_9004(9004, "upload file failure", "文件上传失败"),
    ERROR_9005(9005, "A batch operation can not be more than 50", "批量操作只支持最多50条"),
    ERROR_9006(9006, "objectId is null", "objectId为空"),
    ERROR_9007(9007, "BmobFile File size must be less than 10M", "文件大小超过10M"),
    ERROR_9008(9008, "BmobFile File does not exist", "上传文件不存在"),
    ERROR_9009(9009, "No cache data", "没有缓存数据"),
    ERROR_9010(9010, "The network is not normal.(Time out)", "网络超时"),
    ERROR_9011(9011, "BmobUser does not support batch operations", "BmobUser类不支持批量操作"),
    ERROR_9012(9012, "context is null", "上下文为空"),
    ERROR_9013(9013, "BmobObject Object names(database table name) format is not correct", "BmobObject（数据表名称）格式不正确"),
    ERROR_9014(9014, "第三方账号授权失败", "第三方账号授权失败"),
    ERROR_9015(9015, "其他错误均返回此code", "其他错误均返回此code"),
    ERROR_9016(9016, "The network is not available,please check your network!", "无网络连接，请检查您的手机网络"),
    ERROR_9017(9017, "与第三方登录有关的错误，具体请看对应的错误描述", "与第三方登录有关的错误，具体请看对应的错误描述"),
    ERROR_9018(9018, "参数不能为空", "参数不能为空"),
    ERROR_9019(9019, "格式不正确：手机号码、邮箱地址、验证码", "格式不正确：手机号码、邮箱地址、验证码");

    /** 错误码 */
    private int code;
    /** 英语错误 */
    private String msgForEnglish;
    /** 中文错误 */
    private String msgForChinese;
    /** 没有找到对应 code 的错误 */
    private static final String ERROR_NOT_FOUND = "没有找到对应错误码!";

    BmobError(int code, String msgForEnglish, String msgForChinese) {
        this.code = code;
        this.msgForEnglish = msgForEnglish;
        this.msgForChinese = msgForChinese;
    }

    /** 根据 code 获取英文的错误信息 */
    public static String getEnglishMsg(int code) {
        for (BmobError error : BmobError.values()) {
            if (error.getCode() == code)
                return error.getMsgForEnglish();
        }

        return ERROR_NOT_FOUND;
    }

    /** 根据 code 获取英文的错误信息 */
    public static String getChineseMsg(int code) {
        for (BmobError error : BmobError.values()) {
            if (error.getCode() == code)
                return error.getMsgForChinese();
        }

        return ERROR_NOT_FOUND;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsgForEnglish() {
        return msgForEnglish;
    }

    public void setMsgForEnglish(String msgForEnglish) {
        this.msgForEnglish = msgForEnglish;
    }

    public String getMsgForChinese() {
        return msgForChinese;
    }

    public void setMsgForChinese(String msgForChinese) {
        this.msgForChinese = msgForChinese;
    }
}
