package com.moft.xfapply.activity.bussiness;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.widget.Toast;

import com.google.gson.Gson;
import com.moft.xfapply.app.Constant;
import com.moft.xfapply.app.LvApplication;
import com.moft.xfapply.logic.network.OkHttpClientManager;
import com.moft.xfapply.logic.setting.PrefSetting;
import com.moft.xfapply.model.external.AppDefs;
import com.moft.xfapply.model.external.dto.DeviceAuthInfoDTO;
import com.moft.xfapply.model.external.params.RegisterDeviceForm;
import com.moft.xfapply.model.external.params.RestParams;
import com.moft.xfapply.model.external.result.SimpleRestResult;
import com.moft.xfapply.utils.GsonUtil;
import com.moft.xfapply.utils.StringUtil;
import com.moft.xfapply.utils.TelManagerUtil;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Request;

/**
 * Created by wangquan on 2017/4/20.
 */

public class DeviceAuthBussiness {
    private static DeviceAuthBussiness instance = null;
    private Context context = null;
//    private OnDeviceAuthListener listener;

    private DeviceAuthBussiness() {
        context = LvApplication.getContext();
    }

    public static DeviceAuthBussiness getInstance() {
        if (instance == null) {
            instance = new DeviceAuthBussiness();
        }
        return instance;
    }

    public void deviceAuthenticate(final OnDeviceAuthListener listener) {
        if(StringUtil.isEmpty(TelManagerUtil.getInstance().getDeviceId())) {
            if (listener != null) {
                listener.onError("终端号错误");
            }
            return;
        }
        Map<String, String> params = new HashMap<String, String>();
        params.put(RestParams.DEV_AUTH_DEVID, TelManagerUtil.getInstance().getDeviceId());
        params.put(RestParams.DEV_AUTH_APPID, AppDefs.DeviceAppIdentity.YY.toString());
        OkHttpClientManager http = OkHttpClientManager.getInstance();
        http.postAsyn(http.convertToURL(Constant.METHOD_DEVICE_AUTH), new OkHttpClientManager.ResultCallback<SimpleRestResult>() {
            @Override
            public void onError(Request request, Exception e) {
                if (listener != null) {
                    if (!"已授权".equals(PrefSetting.getInstance().getAuthenticate())) {
                        listener.onError(PrefSetting.getInstance().getAuthenticate());
                    } else {
                        listener.onAuthCompleted();
                    }
                }
            }

            @Override
            public void onResponse(SimpleRestResult result) {
                if(result != null) {
                    if (result.isSuccess()) {
                        Gson gson = GsonUtil.create();
                        String jsonStr = gson.toJson(result.getData());
                        DeviceAuthInfoDTO deviceInfo = gson.fromJson(jsonStr, DeviceAuthInfoDTO.class);

                        if (deviceInfo.getHasAuthorized()) {
                            PrefSetting.getInstance().setAuthenticate("已授权");
                        }
                        PrefSetting.getInstance().setExpires(deviceInfo.getExpiredDate().getTime());
                        //ID:T868558 19-03-01 【预案监管】追加终端当前使用的版本信息。 王泉
                        requestSyncAppVersion();

                        long exporesDate = PrefSetting.getInstance().getExpires();
                        long nowDate = System.currentTimeMillis();
                        if(exporesDate < nowDate) {
                            PrefSetting.getInstance().setAuthenticate("授权过期");
                            if (listener != null) {
                                listener.onError(PrefSetting.getInstance().getAuthenticate());
                            }
                        } else {
                            if (listener != null) {
                                listener.onAuthCompleted();
                            }
                        }
                    } else {
                        if (result.getErrorCode() == AppDefs.ResultMessage.MSG_DEVICE_NOTEXIST.getCode()) {
                            requestDeviceRegister(listener);
                            PrefSetting.getInstance().setAuthenticate("未授权");
                        } else if(result.getErrorCode() == AppDefs.ResultMessage.MSG_DEVICE_NOTANTHORIZED.getCode()) {
                            PrefSetting.getInstance().setAuthenticate("未授权");
                        } else if (result.getErrorCode() == AppDefs.ResultMessage.MSG_DEVICE_EXPIRED.getCode()) {
                            PrefSetting.getInstance().setAuthenticate("授权过期");
                            Gson gson = GsonUtil.create();
                            String jsonStr = gson.toJson(result.getData());
                            DeviceAuthInfoDTO deviceInfo = gson.fromJson(jsonStr, DeviceAuthInfoDTO.class);
                            if(deviceInfo != null) {
                                if(deviceInfo.getExpiredDate() != null) {
                                    PrefSetting.getInstance().setExpires(deviceInfo.getExpiredDate().getTime());
                                }
                            }
                        }
                        if (!"已授权".equals(PrefSetting.getInstance().getAuthenticate())) {
                            listener.onError(PrefSetting.getInstance().getAuthenticate());
                        }
                    }
                } else {
                    if (!"已授权".equals(PrefSetting.getInstance().getAuthenticate())) {
                        listener.onError(PrefSetting.getInstance().getAuthenticate());
                    }
                }
            }
        }, params);
    }

    private void requestDeviceRegister(final OnDeviceAuthListener listener) {
        Gson gson = GsonUtil.create();
        RegisterDeviceForm deviceForm = new RegisterDeviceForm();
        deviceForm.setAppId(AppDefs.DeviceAppIdentity.YY.toString());
        deviceForm.setIdentity(TelManagerUtil.getInstance().getDeviceId());
        deviceForm.setSimNo(TelManagerUtil.getInstance().getSimSerialNumber());
        deviceForm.setDeviceType(Build.MODEL);
        deviceForm.setDeviceSpec(Build.PRODUCT);

        OkHttpClientManager http = OkHttpClientManager.getInstance();
        http.postAsyn(http.convertToURL(Constant.METHOD_DEVICE_REGISTER), new OkHttpClientManager.ResultCallback<SimpleRestResult>() {
            @Override
            public void onError(Request request, Exception e) {
            }

            @Override
            public void onResponse(SimpleRestResult result) {
                if (result != null && result.isSuccess()) {
                    PrefSetting.getInstance().setAuthenticate("授权中");
                    if (listener != null) {
                        listener.onError(PrefSetting.getInstance().getAuthenticate());
                    }
                }
            }
        }, gson.toJson(deviceForm));
    }

    //ID:T868558 19-03-01 【预案监管】追加终端当前使用的版本信息。 王泉
    private void requestSyncAppVersion() {
        Gson gson = GsonUtil.create();
        Map<String, String> params = new HashMap<>();
        params.put("identity", TelManagerUtil.getInstance().getDeviceId());
        params.put("appIdentity", AppDefs.DeviceAppIdentity.YY.toString());
        params.put("appVersion", getVersion());
        params.put("osVersion", Build.VERSION.RELEASE + "_" + Build.VERSION.SDK_INT);
        params.put("vendor", Build.MANUFACTURER);
        params.put("model", Build.MODEL);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            params.put("cpu", Arrays.toString(Build.SUPPORTED_ABIS));
        } else {
            params.put("cpu", Build.CPU_ABI);
        }

        OkHttpClientManager http = OkHttpClientManager.getInstance();
        http.postAsyn(http.convertToURL(Constant.METHOD_UPDATE_APP_VERSION), new OkHttpClientManager.ResultCallback<SimpleRestResult>() {
            @Override
            public void onError(Request request, Exception e) {
            }

            @Override
            public void onResponse(SimpleRestResult result) {
            }
        }, gson.toJson(params));
    }

    private String getVersion() {
        String version = "";
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo packinfo = pm.getPackageInfo(context.getPackageName(), 0);
            version = packinfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return version;
    }

    public interface OnDeviceAuthListener{
        void onAuthCompleted();
        void onError(String err);
    }
}
