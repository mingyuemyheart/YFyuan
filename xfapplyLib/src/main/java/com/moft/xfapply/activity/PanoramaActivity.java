package com.moft.xfapply.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.moft.xfapply.R;
import com.moft.xfapply.activity.bussiness.GeomEleBussiness;
import com.moft.xfapply.app.Constant;
import com.moft.xfapply.base.BaseActivity;
import com.moft.xfapply.model.base.IMediaInfo;
import com.moft.xfapply.model.common.EleCondition;
import com.moft.xfapply.model.entity.KeyUnitMediaDBInfo;
import com.moft.xfapply.model.external.AppDefs;
import com.moft.xfapply.utils.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class PanoramaActivity extends BaseActivity{
	private String panoUrl;
	private WebView webView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_panorama);

		Intent intent = getIntent();
		if (intent.hasExtra("pano_url")) {
			panoUrl = intent.getStringExtra("pano_url");
		} else {
			finish();
			return;
		}
		if (StringUtil.isEmpty(panoUrl)) {
			Toast.makeText(this, "无全景漫游！", Toast.LENGTH_SHORT).show();
			finish();
			return;
		}

		initView();
	}

	@Override
	protected void onPause() {
		super.onPause();
		webView.onPause();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		webView.destroy();
	}

	@Override
	protected void onResume() {
		super.onResume();
		webView.onResume();
	}

	private void initView() {
	    webView = (WebView) findViewById(R.id.web_view);

        webView.getSettings().setDefaultTextEncodingName("utf-8");
        webView.getSettings().setJavaScriptEnabled(true);
        //解决H5新特性localStorage，启动webview的html5的本地存储功能。
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setAppCacheMaxSize(1024 * 1024 * 8);
        String appCachePath = getApplicationContext().getCacheDir().getAbsolutePath();
        webView.getSettings().setAppCachePath(appCachePath);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setAppCacheEnabled(true);
        //******************************************************
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.setBackgroundColor(Color.argb(0, 0, 0, 0));
        webView.requestFocus();
        webView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);

		webView.loadUrl(panoUrl);

        webView.setWebViewClient(new WebViewClient() {
			@Override
			//@JavascriptInterface
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}

			public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
				return super.shouldInterceptRequest(view, request);
			}
		});
	}
}