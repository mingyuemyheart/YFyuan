package com.moft.xfapply.logic.setting;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.moft.xfapply.app.LvApplication;

import java.util.ArrayList;
import java.util.List;

public class PrefUserInfo {

    private static PrefUserInfo instance = null;

    public static final String PREFERENCE_NAME = "local_userinfo";

    private PrefUserInfo() {
    }

    public static PrefUserInfo getInstance() {
        if (instance == null) {
            instance = new PrefUserInfo();
        }
        return instance;
    }

    public void setUserInfo(String str_name, String str_value) {
        String userCode = getRecentUserCode();
        
        SharedPreferences sp = LvApplication.getContext().getSharedPreferences(PREFERENCE_NAME + userCode,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        
        editor.putString(str_name, str_value);
        editor.commit();
    }

    public String getUserInfo(String str_name) {
        String userCode = getRecentUserCode();
        
        SharedPreferences sp = LvApplication.getContext().getSharedPreferences(PREFERENCE_NAME + userCode,
                Context.MODE_PRIVATE);
        
        return sp.getString(str_name, "");
    }

    public String getUserInfo(String str_name, String userCode) {
        SharedPreferences sp = LvApplication.getContext().getSharedPreferences(PREFERENCE_NAME + userCode,
                Context.MODE_PRIVATE);
        
        return sp.getString(str_name, "");
    }

    public void setRecentUserCode(String userCode) {
        SharedPreferences sp = LvApplication.getContext().getSharedPreferences(PREFERENCE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        editor.putString("recentUserCode", userCode);
        editor.commit();
        
        boolean isSaved = false;
        List<String> userList = getDataList("userlist");
        for (String user : userList) {
            if (userCode.equals(user)) {
                isSaved = true;
                break;
            }
        }
        if (!isSaved) {
            userList.add(userCode);
            setDataList("userlist", userList);
        }        
    }

    public void removeRecentUserCode() {
        String userCode = getRecentUserCode();

        boolean isSaved = false;
        List<String> userList = getDataList("userlist");
        for (String user : userList) {
            if (userCode.equals(user)) {
                userList.remove(user);
                isSaved = true;
                break;
            }
        }
        if (isSaved) {
            setDataList("userlist", userList);
        }

        SharedPreferences sp = LvApplication.getContext().getSharedPreferences(PREFERENCE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        editor.putString("recentUserCode", "");
        editor.commit();
    }
    
    public String getRecentUserCode() {
        SharedPreferences sp = LvApplication.getContext().getSharedPreferences(PREFERENCE_NAME,
                Context.MODE_PRIVATE);
        return sp.getString("recentUserCode", "");        
    }
    
    public boolean isOfflineLogin(String userName, final String password) {
        return false;
    }

    public <T> void setDataList(String tag, List<T> datalist) {
        if (null == datalist || datalist.size() <= 0)
            return;
        
        SharedPreferences sp = LvApplication.getContext().getSharedPreferences(PREFERENCE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        Gson gson = new Gson();
        String strJson = gson.toJson(datalist);
//        editor.clear();  // ID:886055 首次自动登录有几率出现，登录失败，或登录我的信息中未显示中队名。 2019-03-05 王旭
        editor.putString(tag, strJson);
        editor.commit();

    }

    public <T> List<T> getDataList(String tag) {
        SharedPreferences sp = LvApplication.getContext().getSharedPreferences(PREFERENCE_NAME,
                Context.MODE_PRIVATE);
        
        List<T> datalist = new ArrayList<T>();

        String strJson = sp.getString(tag, null);
        if (null == strJson) {
            return datalist;
        }

        Gson gson = new Gson();
        datalist = gson.fromJson(strJson, new TypeToken<List<T>>() {
        }.getType());

        return datalist;
    }
}
