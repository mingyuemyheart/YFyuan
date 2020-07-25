package com.moft.xfapply.model.external.params;

public class RestParams {
    public static final String APP_DISTRICT  = "district";  // 城市编码
    public static final String APP_UUID      = "uuid";      // 唯一标识
    public static final String APP_UUIDS     = "uuids";     // 唯一标识数组
    public static final String APP_ID        = "appId";     // 手机端应用标识
    public static final String BELONG_TO_GROUP = "belongtoGroup"; // 所属组
    // Device Authentication
    public static final String DEV_AUTH_DEVID = "devId"; // 设备IMEI
    public static final String DEV_AUTH_APPID = "appId"; // AppIdentity

    // Account Login
    public static final String ACCOUNT_USERNAME = "userName";    // 用户
    public static final String ACCOUNT_PASSWORD = "password";    // 密码
    public static final String PASSWORD_OLD     = "passwordOld"; // 旧密码
    public static final String PASSWORD_NEW     = "passwordNew"; // 新密码

    // Submit Params
    public static final String FORM_SUBMIT_INFO      = "submitInfo";

    public static final String MAINTAINER_FORM   = "maintainerForm";
    public static final String SNAPSHOT_FORM   = "snapshotForm";
}
