package com.moft.xfapply.activity.bussiness;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import com.google.gson.Gson;
import com.moft.xfapply.R;
import com.moft.xfapply.app.Constant;
import com.moft.xfapply.app.LvApplication;
import com.moft.xfapply.logic.network.OkHttpClientManager;
import com.moft.xfapply.logic.setting.PrefUserInfo;
import com.moft.xfapply.model.external.AppDefs;
import com.moft.xfapply.model.external.dto.AccessTokenDTO;
import com.moft.xfapply.model.external.dto.AuthUserDTO;
import com.moft.xfapply.model.external.params.RestParams;
import com.moft.xfapply.model.external.result.SimpleRestResult;
import com.moft.xfapply.utils.DbUtil;
import com.moft.xfapply.utils.GsonUtil;
import com.moft.xfapply.utils.StorageUtil;
import com.moft.xfapply.utils.StringUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Request;

public class UserBussiness {
    private static UserBussiness instance = null;
    private Context context = null;
    private OnLoginListener mListener = null;
    private boolean isAllowOffline = true;

    private UserBussiness() {
        context = LvApplication.getContext();
    }

    public static UserBussiness getInstance() {
        if (instance == null) {
            instance = new UserBussiness();
        }
        return instance;
    }

    public void setListener(OnLoginListener mListener) {
        this.mListener = mListener;
    }

    public boolean isAllowOffline() {
        return isAllowOffline;
    }

    public void setAllowOffline(boolean isAllowOffline) {
        this.isAllowOffline = isAllowOffline;
    }

    public void login(final String userName, final String password, final Context context) {

        Map<String, String> params = new HashMap<String, String>();

        params.put(RestParams.ACCOUNT_USERNAME, userName);
        params.put(RestParams.ACCOUNT_PASSWORD, password);
        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage("登录中...");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.show();

        OkHttpClientManager http = OkHttpClientManager.getInstance();
        http.postAsyn(http.convertToURL(Constant.METHOD_LOGIN), new OkHttpClientManager.ResultCallback<SimpleRestResult>() {
            @Override
            public void onError(Request request, Exception e) {
                dialog.dismiss();
                String code = offlineLogin(userName, password);
                handlerRequestError(code, "请确认是否网络异常");
            }

            @Override
            public void onResponse(SimpleRestResult result) {
                if (result != null && result.isSuccess()) {
                    Gson gson = GsonUtil.create();
                    String jsonStr = gson.toJson(result.getData());
                    AuthUserDTO authInfo = gson.fromJson(jsonStr, AuthUserDTO.class);
                    saveMyInfo(authInfo, userName, password);
                    doSuccess(false);
                } else {
                    int errCode = getErrorCode(result);
                    if (AppDefs.ResultMessage.MSG_PASSWORD_INVALID.getCode() == errCode) {
                        // 密码错误
                        PrefUserInfo.getInstance().removeRecentUserCode();
                        handlerRequestError(null, "账号或密码错误");
                    } else {
                        String code = offlineLogin(userName, password);
                        handlerRequestError(code, "账号或密码错误");
                    }
                }
                dialog.dismiss();
            }
        }, params);
    }

    private int getErrorCode(SimpleRestResult result) {
        if (result == null) {
            return 0;
        }
        return result.getErrorCode()== null ? 0 : result.getErrorCode();
    }

    public void updatePassword(final String oldPassword, final String password, final Context context) {
        Map<String, String> params = new HashMap<String, String>();
        params.put(RestParams.PASSWORD_OLD, oldPassword);
        params.put(RestParams.PASSWORD_NEW, password);
        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage("正在修改密码...");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.show();

        OkHttpClientManager http = OkHttpClientManager.getInstance();
        http.putAsyn(http.convertToURL(String.format(Constant.METHOD_USER_UPDATE_PASSWORD, PrefUserInfo.getInstance().getUserInfo("account_uuid"))),
                new OkHttpClientManager.ResultCallback<SimpleRestResult>() {
                    @Override
                    public void onError(Request request, Exception e) {
                        dialog.dismiss();
                        String msg = "请确认是否网络异常";
                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                        if (mListener != null) {
                            mListener.onFailure(msg);
                        }
                    }

                    @Override
                    public void onResponse(SimpleRestResult result) {
                        if (result != null && result.isSuccess()) {
                            Toast.makeText(context, "密码修改成功", Toast.LENGTH_SHORT).show();
                            PrefUserInfo.getInstance().setUserInfo("password", password);
                            if (mListener != null) {
                                mListener.onSuccess();
                            }

                        } else {
                            String msg = "密码修改失败";
                            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                            if (mListener != null) {
                                mListener.onFailure(msg);
                            }
                        }
                        dialog.dismiss();
                    }
                }, params);
    }

    public void updateFireFighterInfo(final String key, final String value, final Context context, final  OnMyUserInfoListener userInfoListener) {
        Map<String, String> params = new HashMap<String, String>();
        params.put(key, value);

        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage("正在修改用户信息...");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.show();

        OkHttpClientManager http = OkHttpClientManager.getInstance();

        http.putAsyn(http.convertToURL(String.format(Constant.METHOD_USER_UPDATE_FIREFIGHTERS, PrefUserInfo.getInstance().getUserInfo("fire_fighter_uuid"))),
                new OkHttpClientManager.ResultCallback<SimpleRestResult>() {
                    @Override
                    public void onError(Request request, Exception e) {
                        dialog.dismiss();
                        String msg = "请确认是否网络异常";
                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                        if (userInfoListener != null) {
                            userInfoListener.onFailure(msg);
                        }
                    }

                    @Override
                    public void onResponse(SimpleRestResult result) {
                        if (result != null && result.isSuccess()) {
                            Toast.makeText(context, "用户信息修改成功", Toast.LENGTH_SHORT).show();
                            PrefUserInfo.getInstance().setUserInfo(key, value);
                            if (userInfoListener != null) {
                                userInfoListener.onSuccess();
                            }

                        } else {
                            String msg = "用户信息修改失败";
                            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                            if (userInfoListener != null) {
                                userInfoListener.onFailure(msg);
                            }
                        }
                        dialog.dismiss();
                    }
                }, params);
    }

    private String offlineLogin(String userName, String password) {
        PrefUserInfo pui = PrefUserInfo.getInstance();
        
        List<String> userCodeList = pui.getDataList("userlist");
        
        for (String userCode : userCodeList) {
            String name = pui.getUserInfo("username", userCode);
            String pwd = pui.getUserInfo("password", userCode);
            if (userName.equals(name) && password.equals(pwd)) {
                return userCode;
            }
        }
        return null;
    }

    private void saveMyInfo(AuthUserDTO authInfo, String userName, String password) {
        PrefUserInfo pui = PrefUserInfo.getInstance();
        pui.setRecentUserCode(authInfo.getUuid());

        pui.setUserInfo("username", userName);
        pui.setUserInfo("password", password);

        if(authInfo.getSysDepartmentDTO() != null) {
            pui.setUserInfo(Constant.DB_NAME_USER, userName);
            pui.setUserInfo("district_code", authInfo.getSysDepartmentDTO().getDistrictCode());
            pui.setUserInfo("department", authInfo.getSysDepartmentDTO().getName());  // ID:886055 首次自动登录有几率出现，登录失败，或登录我的信息中未显示中队名。2019-03-05 王旭
            pui.setUserInfo("department_uuid", authInfo.getSysDepartmentDTO().getUuid());
            pui.setUserInfo("department_code", authInfo.getSysDepartmentDTO().getCode());
            pui.setUserInfo("department_grade", StringUtil.get(authInfo.getSysDepartmentDTO().getGrade()));
        }

        if(authInfo.getFirefighterDTO() != null) {
            pui.setUserInfo("fire_fighter_uuid", authInfo.getFirefighterDTO().getUuid());
            pui.setUserInfo("real_name", authInfo.getFirefighterDTO().getName());

            pui.setUserInfo("presonal_code", authInfo.getFirefighterDTO().getPersonalCode());
            pui.setUserInfo("duty", authInfo.getFirefighterDTO().getDuty());

            pui.setUserInfo("sex", authInfo.getFirefighterDTO().getSex());
            pui.setUserInfo("contactMobile", authInfo.getFirefighterDTO().getContactMobile());
            pui.setUserInfo("address", authInfo.getFirefighterDTO().getAddress());
            pui.setUserInfo("remark", authInfo.getFirefighterDTO().getRemark());
        }

        pui.setUserInfo("account_uuid", authInfo.getAccountUuid());
        AccessTokenDTO tokenDTO = authInfo.getAccessTokenDTO();
        if(tokenDTO != null) {
            pui.setUserInfo("token", String.format("%s %s", tokenDTO.getToken_type(), tokenDTO.getAccess_token()));
        }
        return;
    }
    
    private void doSuccess(boolean isOfflineLogin) {
        // 初始化用户主数据库
        PrefUserInfo pui = PrefUserInfo.getInstance();
        String userKey = pui.getUserInfo(Constant.DB_NAME_USER);
        String userName = pui.getUserInfo("username");
        String dbName = "main_" + userName;
        DbUtil.getInstance().loadDB(userKey, StorageUtil.getPersonDbPath(userKey), dbName, R.raw.main);

        if (mListener != null) {
            if(isOfflineLogin) {
             mListener.onSuccessOffline();
            } else {
                mListener.onSuccess();
            }
        }
    }

    private void handlerRequestError(String code, String msg) {
        if (isAllowOffline && !StringUtil.isEmpty(code)) {
            Toast.makeText(context, "进入离线登录状态", Toast.LENGTH_SHORT).show();
            PrefUserInfo.getInstance().setRecentUserCode(code);
            doSuccess(true);
        } else {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
            if (mListener != null) {
                mListener.onFailure(msg);
            }
        }
    }

    public interface OnLoginListener{
        void onSuccess();
        void onSuccessOffline();
        void onFailure(String msg);
    }

    public interface OnMyUserInfoListener{
        void onSuccess();
        void onFailure(String msg);
    }
}
