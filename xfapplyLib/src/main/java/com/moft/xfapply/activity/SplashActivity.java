package com.moft.xfapply.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.anthonycr.grant.PermissionsManager;
import com.anthonycr.grant.PermissionsResultAction;
import com.moft.xfapply.R;
import com.moft.xfapply.app.LvApplication;
import com.moft.xfapply.base.BaseActivity;


public class SplashActivity extends BaseActivity {
	private static final int sleepTime = 2000;
	private boolean isRequestPermission = true;
	private final String[] permission = {
			Manifest.permission.CAMERA,
			Manifest.permission.READ_EXTERNAL_STORAGE,
			Manifest.permission.WRITE_EXTERNAL_STORAGE,
			Manifest.permission.RECORD_AUDIO,
			Manifest.permission.READ_PHONE_STATE,
			Manifest.permission.ACCESS_FINE_LOCATION
	};

	@Override
	protected void onCreate(Bundle arg0) {
	    final View view = View.inflate(this, R.layout.activity_splash, null);
		setContentView(view);
		super.onCreate(arg0);
		 
		AlphaAnimation animation = new AlphaAnimation(0.3f, 1.0f);
		animation.setDuration(1500);
		view.startAnimation(animation);
		isRequestPermission = !requestSysPermission();
	}

	@Override
	protected void onStart() {
		super.onStart();
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				if (!isRequestPermission) {
					startupApp();
				}
			}
		}, sleepTime);
	}

	private boolean requestSysPermission() {
		boolean ret = PermissionsManager.getInstance().hasAllPermissions(this, permission);
		if(!ret) {
			PermissionsManager.getInstance().requestPermissionsIfNecessaryForResult(this, permission, new PermissionsResultAction() {
				@Override
				public void onGranted() {
					LvApplication.getInstance().initBDMapSDK();
					startupApp();
				}

				@Override
				public void onDenied(String permission) {
					finish();
					Toast.makeText(SplashActivity.this, "应用程序的权限未启用，程序退出",
							Toast.LENGTH_SHORT).show();
				}
			});
		}
		return ret;
	}

	private void startupApp() {
		onLoginSuccess();
	}

	public void onLoginSuccess() {
		startActivity(new Intent(SplashActivity.this, PrepareActivity.class));
		finish();
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		//把申请权限的回调交由EasyPermissions处理
		PermissionsManager.getInstance().notifyPermissionsChange(permissions, grantResults);
	}
}
