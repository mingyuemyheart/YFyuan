package com.moft.xfapply.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.moft.xfapply.R;
import com.moft.xfapply.base.BaseActivity;
import com.moft.xfapply.logic.setting.PrefSetting;
import com.moft.xfapply.model.common.WebMedia;
import com.moft.xfapply.utils.StringUtil;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import static com.moft.xfapply.app.Constant.ATTACH_NAME;
import static com.moft.xfapply.app.Constant.SERVICE_NAME;
import static com.moft.xfapply.app.Constant.URL_HEAD;
import static com.moft.xfapply.app.Constant.URL_MIDDLE;

public class DisposalPointDetailActivity extends BaseActivity{

	private static final String HTTP_PREFIX = URL_HEAD
			+ PrefSetting.getInstance().getServerIP()
			+ URL_MIDDLE + PrefSetting.getInstance().getServerPort()
			+ SERVICE_NAME + ATTACH_NAME + "/";
	private String name;
	private String disposalPointUrl;

	private WebView webView;
	private TextView tv_title;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_disposal_point);

		Intent intent = getIntent();
		if (intent.hasExtra("url")) {
			String tmp = intent.getStringExtra("url");
			if (!StringUtil.isEmpty(tmp)) {
				disposalPointUrl = tmp;
				if (!disposalPointUrl.startsWith(URL_HEAD)) {
					disposalPointUrl =  HTTP_PREFIX + disposalPointUrl;
				}
			}

		} else {
			finish();
			return;
		}
		name = intent.getStringExtra("name");
		if (StringUtil.isEmpty(disposalPointUrl)) {
			Toast.makeText(this, "无处置要点数据！", Toast.LENGTH_SHORT).show();
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
		if (webView != null) {
			webView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
			webView.clearHistory();

			webView.destroy();
			webView = null;
		}
		super.onDestroy();
	}

	@Override
	protected void onResume() {
		super.onResume();
		webView.onResume();
	}

	private void initView() {
		tv_title = (TextView)findViewById(R.id.tv_title);
		tv_title.setText(name);

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
//        webView.getSettings().setUseWideViewPort(true); // 设置此属性，可任意比例缩放
        webView.getSettings().setLoadWithOverviewMode(true);

		webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
		webView.setBackgroundColor(Color.argb(0, 0, 0, 0));
        webView.requestFocus();
        webView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);

//		webView.getSettings().setBuiltInZoomControls(true);//设置内置的缩放控件。若为false，则该WebView不可缩放
//		webView.getSettings().setDisplayZoomControls(false); //隐藏原生的缩放控件，默认是true。如果不想显示放大和缩小按钮，将该属性设置为false即可
//		webView.getSettings().setSupportZoom(true);  // 缩放开关
		webView.setInitialScale(100);
		webView.addJavascriptInterface(this, "imagelistener"); //

		webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);

		webView.loadUrl(disposalPointUrl);

		webView.setOnTouchListener(new View.OnTouchListener() {
			private double nLenStart = 0;//监听 WebView所用手势

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// code from https://blog.csdn.net/LVXIANGAN/article/details/84932161
				int nCnt = event.getPointerCount();
				if ((event.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_POINTER_DOWN && 2 == nCnt)//<span style="color:#ff0000;">2表示两个手指</span>
				{
					int xlen = Math.abs((int) event.getX(0) - (int) event.getX(1));
					int ylen = Math.abs((int) event.getY(0) - (int) event.getY(1));
					nLenStart = Math.sqrt((double) xlen * xlen + (double) ylen * ylen);
				} else if ((event.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_POINTER_UP && 2 == nCnt) {
					int xlen = Math.abs((int) event.getX(0) - (int) event.getX(1));
					int ylen = Math.abs((int) event.getY(0) - (int) event.getY(1));

					double nLenEnd = Math.sqrt((double) xlen * xlen + (double) ylen * ylen);
					if (nLenEnd > nLenStart)//通过两个手指开始距离和结束距离，来判断放大缩小
					{
						webView.getSettings().setTextSize(increaseSize());
						webView.reload();
//						webView.loadUrl(disposalPointUrl);
					} else if (nLenEnd < nLenStart) {
						webView.getSettings().setTextSize(reduceSize());
						webView.reload();
//						webView.loadUrl(disposalPointUrl);
					}
				}
				return false;
			}
		});

        webView.setWebViewClient(new WebViewClient() {
			@Override
			//@JavascriptInterface
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
				imgReset();//重置webview中img标签的图片大小
				addImageClickListner();
			}

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				view.getSettings().setJavaScriptEnabled(true);

				super.onPageStarted(view, url, favicon);
			}

			public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
				return super.shouldInterceptRequest(view, request);
			}
		});
	}

	private WebSettings.TextSize reduceSize() {
		WebSettings.TextSize currentSize = webView.getSettings().getTextSize();
		if (currentSize == WebSettings.TextSize.SMALLEST) {
			Toast.makeText(DisposalPointDetailActivity.this, "无法继续缩小", Toast.LENGTH_SHORT).show();
			return currentSize;
		}
		if (currentSize == WebSettings.TextSize.SMALLER) {
			return WebSettings.TextSize.SMALLEST;
		}

		if (currentSize == WebSettings.TextSize.NORMAL) {
			return WebSettings.TextSize.SMALLER;
		}
		if (currentSize == WebSettings.TextSize.LARGER) {
			return WebSettings.TextSize.NORMAL;
		}

		if (currentSize == WebSettings.TextSize.LARGEST) {
			return WebSettings.TextSize.LARGER;
		}
		return currentSize;
	}

	private WebSettings.TextSize increaseSize() {
		WebSettings.TextSize currentSize = webView.getSettings().getTextSize();
		if (currentSize == WebSettings.TextSize.SMALLEST) {
			return WebSettings.TextSize.SMALLER;
		}
		if (currentSize == WebSettings.TextSize.SMALLER) {
			return WebSettings.TextSize.NORMAL;
		}

		if (currentSize == WebSettings.TextSize.NORMAL) {
			return WebSettings.TextSize.LARGER;
		}
		if (currentSize == WebSettings.TextSize.LARGER) {
			return WebSettings.TextSize.LARGEST;
		}

		if (currentSize == WebSettings.TextSize.LARGEST) {
			Toast.makeText(DisposalPointDetailActivity.this, "无法继续放大", Toast.LENGTH_SHORT).show();
			return currentSize;
		}
		return currentSize;
	}

	/**
	 * 对图片进行重置大小，宽度就是手机屏幕宽度，高度根据宽度比便自动缩放
	 **/
	private void imgReset() {
		webView.loadUrl("javascript:(function(){" +
				"var objs = document.getElementsByTagName('img'); " +
				"for(var i=0;i<objs.length;i++)  " +
				"{"
				+ "var img = objs[i];   " +
				"    img.style.maxWidth = '100%'; img.style.height = 'auto';  " +
				"}" +
				"})()");
	}

	// 注入js函数监听
	private void addImageClickListner() {
		// 这段js函数的功能就是，遍历所有的img几点，并添加onclick函数，函数的功能是在图片点击的时候调用本地java接口并传递url过去
		webView.loadUrl("javascript:(function(){" +
				"var objs = document.getElementsByTagName(\"img\"); " +
				"for(var i=0;i<objs.length;i++)  " +
				"{"
				+ "    objs[i].onclick=function()  " +
				"    {  "
				+ "        window.imagelistener.openImage(this.src);  " +
				"    }  " +
				"}" +
				"})()");
	}

	@JavascriptInterface
	public void openImage(String imgUrl) {
		if (StringUtil.isEmpty(imgUrl)) {
			Toast.makeText(DisposalPointDetailActivity.this, "图片url解析错误，请至平台重新确认数据", Toast.LENGTH_SHORT).show();
			return;
		}
		Intent intent = new Intent();
		List<WebMedia> list = new ArrayList<WebMedia>();
		WebMedia tmp = new WebMedia();
		tmp.setMediaUrl(converUrl(imgUrl));
		tmp.setMediaDescription(converName(imgUrl));
		tmp.setMediaFormat("IMG");
		list.add(tmp);
		intent.setClass(this, PickBigImagesActivity.class);
		intent.putExtra(PickBigImagesActivity.EXTRA_DATA, (Serializable) list);
		startActivity(intent);
	}

	private String converName(String imgUrl) {
		String name = null;
		String[] names = imgUrl.split("/");
		try {
			String tmp = names[names.length - 1];
			name = URLDecoder.decode(tmp, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return name;
	}

	private String converUrl(String imgUrl) {
		return imgUrl.substring(HTTP_PREFIX.length() - 1);
	}

	public void back(View view) {
		finish();
	}

}