package com.moft.xfapply.base;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.moft.xfapply.activity.bussiness.CommonBussiness;
import com.moft.xfapply.app.LvApplication;
import com.moft.xfapply.crash.CrashHandler;

public class BaseActivity extends Activity {

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        //注册未caught异常处理器
        Thread.setDefaultUncaughtExceptionHandler(new CrashHandler(this));
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("cityName", LvApplication.getInstance().getCityName());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if(savedInstanceState != null) {
            LvApplication.getInstance().setCityName(savedInstanceState.getString("cityName"));
        }
    }

    public void back(View view) {
        finish();
    }
}
