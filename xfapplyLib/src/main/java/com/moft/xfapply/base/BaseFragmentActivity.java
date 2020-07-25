package com.moft.xfapply.base;

import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.FragmentActivity;

import com.moft.xfapply.crash.CrashHandler;

public class BaseFragmentActivity extends FragmentActivity {

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

    public void back(View view) {
        finish();
    }
}
