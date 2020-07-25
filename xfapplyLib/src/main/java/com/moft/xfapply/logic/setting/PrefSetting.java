package com.moft.xfapply.logic.setting;

import android.content.Context;
import android.content.SharedPreferences;

import com.moft.xfapply.app.Constant;
import com.moft.xfapply.app.LvApplication;
import com.moft.xfapply.utils.PreferenceUtils;

import java.util.ArrayList;
import java.util.List;

public class PrefSetting {
    private static PrefSetting instance = null;

	public static final String PREFERENCE_NAME = "settingInfo";

    private static final String SERVERIP = "serverip";
    private static final String SERVERPORT = "serverport";

    private static final String LOAD_DIS = "load_dis";
    private static final String AUTO_LOGIN = "auto_login";
    private static final String OFFLINE_LOGIN = "offline_login";
    private static final String OFFLINE_DOWNLOAD = "offline_download";
    private static final String RECORD_SUBMIT = "record_submit";
    private static final String PAGE_NUM = "page_num";
    private static final String EXPIRES_IN = "expires_in";
    private static final String AUTHENTICATE = "authenticate";
    private static final String CHANGE_USER = "change_user";
    private static final String UPDATE_OFFLINE_DATA_MODE = "update_offline_data_mode";

    public static final String MAP_TYPE_STR = "MAP_TYPE_STR";
    public static final int MAP_NONE = 0;
    public static final int MAP_SATELITE = 1;
    public static final int MAP_NORMAL = 2;
    public static final int MAP_3D = 3;

    public static final String BAIDU_LUKUANG_STR = "BAIDU_LUKUANG_STR";
    public static final String MAP_WZBZ_STR = "MAP_WZBZ_STR";
    public static final String MAP_WZBZ_QW_STR = "MAP_WZBZ_QW_STR";
    public static final String MAP_SHOW_POI = "MAP_SHOW_POI";
    public static final String DISASTER_MODEL = "DISASTER_MODEL";
    public static final String PUSH_MODEL = "PUSH_MODEL";

	private PrefSetting() {
	}

	public static synchronized PrefSetting getInstance() {
		if (instance == null) {
            instance = new PrefSetting();
		}
		
		return instance;
	}

    public void setChangeUser(boolean value) {
        SharedPreferences sp = LvApplication.getContext().getSharedPreferences(
                PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        editor.putBoolean(CHANGE_USER, value);
        editor.commit();
    }

    public boolean getChangeUser() {
        SharedPreferences sp = LvApplication.getContext().getSharedPreferences(
                PREFERENCE_NAME, Context.MODE_PRIVATE);

        return sp.getBoolean(CHANGE_USER, false);
    }


    public void setLoadDis(String value) {
        SharedPreferences sp = LvApplication.getContext().getSharedPreferences(
                PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        
        editor.putString(LOAD_DIS, value);
        editor.commit();
    }
    
    public String getLoadDis() {
        SharedPreferences sp = LvApplication.getContext().getSharedPreferences(
                PREFERENCE_NAME, Context.MODE_PRIVATE);
        
        return sp.getString(LOAD_DIS, "0.5");
    }

    public int getLoadDisNumber() {
        int maxDis = 200;
        String code = PrefSetting.getInstance().getLoadDis();
        if ("0.2".equals(code)) {
            maxDis = 200;
        } else if ("0.5".equals(code)) {
            maxDis = 500;
        } else if ("1".equals(code)) {
            maxDis = 1000;
        } else if ("3".equals(code)) {
            maxDis = 3000;
        } else if ("5".equals(code)) {
            maxDis = 5000;
        } else if ("10".equals(code)) {
            maxDis = 10000;
        }
        return maxDis;
    }

    public List<Integer> getLoadDisNumberList() {
        List<Integer> disList = new ArrayList<>();
        String code = PrefSetting.getInstance().getLoadDis();
        if ("0.2".equals(code)) {
            disList.add(200);
        } else if ("0.5".equals(code)) {
            disList.add(200);
            disList.add(500);
        } else if ("1".equals(code)) {
            disList.add(200);
            disList.add(500);
            disList.add(1000);
        } else if ("3".equals(code)) {
            disList.add(200);
            disList.add(500);
            disList.add(1000);
            disList.add(3000);
        } else if ("5".equals(code)) {
            disList.add(200);
            disList.add(500);
            disList.add(1000);
            disList.add(3000);
            disList.add(5000);
        } else if ("10".equals(code)) {
            disList.add(200);
            disList.add(500);
            disList.add(1000);
            disList.add(3000);
            disList.add(5000);
            disList.add(10000);
        }
        return disList;
    }
    
    public void setAutoLogin(boolean value) {
        SharedPreferences sp = LvApplication.getContext().getSharedPreferences(
                PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        
        editor.putBoolean(AUTO_LOGIN, value);
        editor.commit();
    }
    
    public boolean getAutoLogin() {
        SharedPreferences sp = LvApplication.getContext().getSharedPreferences(
                PREFERENCE_NAME, Context.MODE_PRIVATE);
        
        return sp.getBoolean(AUTO_LOGIN, true); // ID:892636 追加默认设置我-自动登录功能。 2019-02-25  王旭 ，false->true
    }
    
    public void setOfflineLogin(boolean value) {
        SharedPreferences sp = LvApplication.getContext().getSharedPreferences(
                PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        
        editor.putBoolean(OFFLINE_LOGIN, value);
        editor.commit();
    }
    
    public boolean getOfflineLogin() {
        SharedPreferences sp = LvApplication.getContext().getSharedPreferences(
                PREFERENCE_NAME, Context.MODE_PRIVATE);
        
        return sp.getBoolean(OFFLINE_LOGIN, true);
    }
    
    public boolean getOfflineDownload() {
        SharedPreferences sp = LvApplication.getContext().getSharedPreferences(
                PREFERENCE_NAME, Context.MODE_PRIVATE);
        
        return sp.getBoolean(OFFLINE_DOWNLOAD, true);
    }
    
    public void setOfflineDownload(boolean value) {
        SharedPreferences sp = LvApplication.getContext().getSharedPreferences(
                PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        
        editor.putBoolean(OFFLINE_DOWNLOAD, value);
        editor.commit();
    }

    public boolean getDisasterModel() {
        SharedPreferences sp = LvApplication.getContext().getSharedPreferences(
                PREFERENCE_NAME, Context.MODE_PRIVATE);

        return sp.getBoolean(DISASTER_MODEL, true);
    }

    public void setDisasterModel(boolean value) {
        SharedPreferences sp = LvApplication.getContext().getSharedPreferences(
                PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        editor.putBoolean(DISASTER_MODEL, value);
        editor.commit();
    }

    public boolean getRecordSubmit() {
        SharedPreferences sp = LvApplication.getContext().getSharedPreferences(
                PREFERENCE_NAME, Context.MODE_PRIVATE);
        
        return sp.getBoolean(RECORD_SUBMIT, true);
    }
    
    public void setRecordSubmit(boolean value) {
        SharedPreferences sp = LvApplication.getContext().getSharedPreferences(
                PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        
        editor.putBoolean(RECORD_SUBMIT, value);
        editor.commit();
    }
    
    public void setPageNum(int value) {
        SharedPreferences sp = LvApplication.getContext().getSharedPreferences(
                PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        
        editor.putInt(PAGE_NUM, value);
        editor.commit();
    }
    
    public int getPageNum() {
        SharedPreferences sp = LvApplication.getContext().getSharedPreferences(
                PREFERENCE_NAME, Context.MODE_PRIVATE);
        
        return sp.getInt(PAGE_NUM, 100);
    }
	
	public void setServerIP(String ip) {
	    SharedPreferences sp = LvApplication.getContext().getSharedPreferences(
	            PREFERENCE_NAME, Context.MODE_PRIVATE);
	    SharedPreferences.Editor editor = sp.edit();
	    
	    editor.putString(SERVERIP, ip);
        editor.commit();
	}

    public String getServerIP() {
        SharedPreferences sp = LvApplication.getContext().getSharedPreferences(
                PREFERENCE_NAME, Context.MODE_PRIVATE);
        
        return sp.getString(SERVERIP, Constant.IP);
    }
    
    public void setServerPort(String port) {        
        SharedPreferences sp = LvApplication.getContext().getSharedPreferences(
                PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        
        editor.putString(SERVERPORT, port);
        editor.commit();
    }

    public String getServerPort() {
        SharedPreferences sp = LvApplication.getContext().getSharedPreferences(
                PREFERENCE_NAME, Context.MODE_PRIVATE);
        
        return sp.getString(SERVERPORT, Constant.PORT);
    }

    public void setExpires(long timestamp) {
        SharedPreferences sp = LvApplication.getContext().getSharedPreferences(
                PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        editor.putLong(EXPIRES_IN, timestamp);
        editor.commit();
    }

    public long getExpires() {
        SharedPreferences sp = LvApplication.getContext().getSharedPreferences(
                PREFERENCE_NAME, Context.MODE_PRIVATE);

        return sp.getLong(EXPIRES_IN, 0);
    }

    public void setAuthenticate(String content) {
        SharedPreferences sp = LvApplication.getContext().getSharedPreferences(
                PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        editor.putString(AUTHENTICATE, content);
        editor.commit();
    }

    public String getAuthenticate() {
        SharedPreferences sp = LvApplication.getContext().getSharedPreferences(
                PREFERENCE_NAME, Context.MODE_PRIVATE);

        return sp.getString(AUTHENTICATE, "未授权");
    }
    public void setMapType(int type) {
        PreferenceUtils.setPrefInt(LvApplication.getContext(), MAP_TYPE_STR, type);
    }

    public int getMapType() {
        return PreferenceUtils.getPrefInt(LvApplication.getContext(), MAP_TYPE_STR, MAP_NORMAL);
    }

    public void setBaiduLuKuang(boolean isOpen) {
        PreferenceUtils.setPrefBoolean(LvApplication.getContext(), BAIDU_LUKUANG_STR, isOpen);
    }

    public boolean getBaiduLuKuang() {
        return PreferenceUtils.getPrefBoolean(LvApplication.getContext(), BAIDU_LUKUANG_STR, false);
    }

    public void setMapShowPoi(boolean isOpen) {
        PreferenceUtils.setPrefBoolean(LvApplication.getContext(), MAP_SHOW_POI, isOpen);
    }

    public boolean getMapShowPoi() {
        return PreferenceUtils.getPrefBoolean(LvApplication.getContext(), MAP_SHOW_POI, true);
    }

    public void setWzbz(boolean isOpen) {
        PreferenceUtils.setPrefBoolean(LvApplication.getContext(), MAP_WZBZ_STR, isOpen);
    }

    public boolean getWzbz() {
        return PreferenceUtils.getPrefBoolean(LvApplication.getContext(), MAP_WZBZ_STR, true);
    }

    public void setWzbzQw(boolean isOpen) {
        PreferenceUtils.setPrefBoolean(LvApplication.getContext(), MAP_WZBZ_QW_STR, isOpen);
    }

    public boolean getWzbzQw() {
        return PreferenceUtils.getPrefBoolean(LvApplication.getContext(), MAP_WZBZ_QW_STR, true);
    }

    public void setUpdateOfflineDataMode(String value) {
        SharedPreferences sp = LvApplication.getContext().getSharedPreferences(
                PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        editor.putString(UPDATE_OFFLINE_DATA_MODE, value);
        editor.commit();
    }

    public String getUpdateOfflineDataMode() {
        SharedPreferences sp = LvApplication.getContext().getSharedPreferences(
                PREFERENCE_NAME, Context.MODE_PRIVATE);

        String mode = sp.getString(UPDATE_OFFLINE_DATA_MODE, String.valueOf(30 * 60 * 1000));  // 默认返回30分钟对应的毫秒数
        return mode;
    }
}
