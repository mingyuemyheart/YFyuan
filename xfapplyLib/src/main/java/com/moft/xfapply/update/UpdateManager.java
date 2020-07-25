package com.moft.xfapply.update;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import com.moft.xfapply.R;
import com.moft.xfapply.app.Constant;
import com.moft.xfapply.app.LvApplication;
import com.moft.xfapply.logic.setting.PrefSetting;
import com.moft.xfapply.utils.LogUtils;
import com.moft.xfapply.utils.StorageUtil;
import com.moft.xfapply.utils.Utils;
import com.moft.xfapply.widget.dialog.CustomAlertDialog;
import com.moft.xfapply.widget.dialog.CustomAlertDialog.OnDoingListener;
import com.moft.xfapply.widget.dialog.ProgressDialog;
import com.moft.xfapply.widget.dialog.ProgressDialog.OnProgressCancelListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

public class UpdateManager {
	/* 下载中 */
	private static final int DOWNLOAD = 1;
	/* 下载结束 */
	private static final int DOWNLOAD_FINISH = 2;
	/* 下载错误 */
	private static final int DOWNLOAD_ERROR = 3;

	private static final int MSG_UPDATE_VERSION = 0;
	private static final int MSG_CHECK_VERSION = 1;

	/* 保存解析的XML信息 */
	HashMap<String, String> mHashMap;
	/* 下载保存路径 */
	private String mSavePath;
	/* 记录进度条数量 */
	private int progress;
	/* 是否取消更新 */
	private boolean cancelUpdate = false;
	private boolean downloadFinish = false;

	private Context mContext;
	private OnUpdateListener listener;
	private OnUpdateProgressListener listenerProgess;
    private int serviceCode;
	private String versionName;

	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			// 正在下载
			case DOWNLOAD:
				// 设置进度条位置
			    ProgressDialog.setProgress(progress);
				break;
			case DOWNLOAD_FINISH:
				// 安装文件
				downloadFinish = true;
				installApk();
				break;
			default:
				break;
			}
		};
	};

	private Handler mHandlerNew = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
				// 正在下载
				case DOWNLOAD:
					// 设置进度条位置
					if (listenerProgess != null) {
						listenerProgess.onProgress(progress);
					}
					break;
				case DOWNLOAD_FINISH:
					// 安装文件
					downloadFinish = true;
					if (listenerProgess != null) {
						listenerProgess.onProgress(100);
					}
					installApk();
					break;
				// 下载错误
				case DOWNLOAD_ERROR:
					if (listenerProgess != null) {
						listenerProgess.onError();
					}
					break;
				default:
					break;
			}
		};
	};

	public UpdateManager(Context context) {
		this.mContext = context;
	}

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            // 检查软件更新
			UpgradeVersionInfo info = getUpgradeVersionInfo();

            Message msg = Message.obtain();
			msg.what = MSG_UPDATE_VERSION;
			msg.obj = info;
            handler.sendMessage(msg);
        }
    };

    /**
     * 检查软件更新
     */
    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
			UpgradeVersionInfo info = (UpgradeVersionInfo)msg.obj;

			switch (msg.what) {
				case MSG_CHECK_VERSION:
					if(listener != null) {
						listener.onUpdateStatus(info);
					}
					break;
				case MSG_UPDATE_VERSION:
					if (info.isUpdate) {
						showNoticeDialog();
					} else {
						Toast.makeText(mContext, R.string.soft_update_no, Toast.LENGTH_LONG).show();
					}
					break;
				default:
					break;
			}

        }
    };
    
	/**
	 * 检测软件更新
	 */
	public void checkUpdate() {
        new Thread(runnable).start();
	}

	public void checkVersion(OnUpdateListener listener) {
		this.listener = listener;
		new Thread(new Runnable() {
			@Override
			public void run() {
				// 检查软件更新
				UpgradeVersionInfo info = getUpgradeVersionInfo();

				Message msg = Message.obtain();
				msg.what = MSG_CHECK_VERSION;
				msg.obj = info;
				handler.sendMessage(msg);
			}
		}).start();
	}

	/**
	 * 检查软件是否有更新版本
	 * 
	 * @return
	 */
	private UpgradeVersionInfo getUpgradeVersionInfo() {
		UpgradeVersionInfo info = new UpgradeVersionInfo();
		// 获取当前软件版本
		int versionCode = getVersionCode(mContext);
		InputStream inStream = null;
		try {
			/* 服务器xml文件路径 */
			String versionUrl = convertToURL(Constant.APPURL_STRING);
			inStream = getInputStreamFromUrl(versionUrl);
		} catch (Exception e) {
			e.printStackTrace();
			return info;
		}
		// 解析XML文件。 由于XML文件比较小，因此使用DOM方式进行解析
		ParseXmlService service = new ParseXmlService();
		try {
			mHashMap = service.parseXml(inStream);
		} catch (Exception e) {
			e.printStackTrace();
			return info;
		}
		if (null != mHashMap) {
			String value = mHashMap.get("version");
			if (value != null) {
				info.verCode = value;
				serviceCode = Utils.ToInteger(value);
				// 版本判断
				if (serviceCode > versionCode) {
					info.isUpdate = true;
				}
			}
			value = mHashMap.get("versionName");
			if (value != null) {
				versionName = Utils.ToString(value);
			}
			value = mHashMap.get("force_update");
			if (value != null) {
				Boolean forceUpdate = Utils.ToBoolean(value);
				if(forceUpdate != null) {
					info.isForceUpdate = forceUpdate;
				}
			}
			value = mHashMap.get("prompt");
			if (value != null) {
				info.prompt = value;
			}
		}
		return info;
	}

    public int getServiceCode() {
        return serviceCode;
    }

	public String getVersionName() {
		return versionName;
	}

	private String convertToURL(String name) {
		String url = "http://" + PrefSetting.getInstance().getServerIP() + ":" +
				PrefSetting.getInstance().getServerPort() + Constant.SERVICE_NAME + Constant.UPDATE_NAME + name;
		return url;
	}

	/**
	 * 获取软件包名
	 * 
	 * @return
	 * @throws NameNotFoundException
	 */
	private String getPackageName() throws NameNotFoundException {
		Context context = LvApplication.getInstance().getApplicationContext();
		PackageInfo info = context.getPackageManager().getPackageInfo(
				context.getPackageName(), 0);
		String packageNames = info.packageName;

		return packageNames;
	}

	/**
	 * 获取软件版本号
	 * 
	 * @param context
	 * @return
	 */
	public int getVersionCode(Context context) {
		int versionCode = 0;
		try {
			// 获取软件版本号，对应AndroidManifest.xml下android:versionCode
			versionCode = context.getPackageManager().getPackageInfo(
					getPackageName(), 0).versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return versionCode;
	}

	/**
	 * 获取软件名称
	 *
	 * @param context
	 * @return
	 */
	public String getVersionName(Context context) {
		String versionName = "";
		try {
			// 获取软件版本名称，对应AndroidManifest.xml下android:versionName
			versionName = context.getPackageManager().getPackageInfo(
					getPackageName(), 0).versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return versionName;
	}

	/**
	 * 显示软件更新对话框
	 */
	public void showNoticeDialog() {

		if (mContext != null && !((Activity) mContext).isFinishing()) {

	        String content = "检测到新版本，是否立即更新？";
	        
	        CustomAlertDialog.show(mContext, content, new OnDoingListener() {

	            @Override
	            public void onOK() {
                    // 显示下载对话框
                    showDownloadDialog();
	            }

	            @Override
	            public void onNG() {
	            }                
	        });
		}
	}

	/**
	 * 显示软件下载对话框
	 */
	private void showDownloadDialog() {
	    ProgressDialog.show(mContext, "正在更新", "", new OnProgressCancelListener() {
            @Override
            public void onCancel() {
                // 设置取消状态
                cancelUpdate = true;
            }
	    });
		// 现在文件
		downloadApk();
	}

	/**
	 * 显示软件下载对话框扩展
	 */
	public void showDownloadDialogEx(final OnProgressCancelListener listener) {
		ProgressDialog.show(mContext, "正在更新", "", new OnProgressCancelListener() {
			@Override
			public void onCancel() {
				// 设置取消状态
				cancelUpdate = true;
				// finish the activity
				if (listener != null && !downloadFinish) {
					listener.onCancel();
				}
			}
		});
		// 现在文件
		downloadApk();
	}

	/**
	 * 下载apk文件
	 */
	private void downloadApk() {
		// 启动新线程下载软件
		downloadFinish = false;
		new downloadApkThread().start();
	}

	public void doUpdateApk(OnUpdateProgressListener listener) {
		// 启动新线程下载软件
		downloadFinish = false;
		listenerProgess = listener;
		new downloadApkThreadNew().start();
	}

	/**
	 * 下载文件线程
	 */
	private class downloadApkThreadNew extends Thread {
		private int cnt = 0;

		@Override
		public void run() {
			try {
				// 判断SD卡是否存在，并且是否具有读写权限
				if (StorageUtil.isExternalMemoryAvailable()) {
					mSavePath = StorageUtil.getTempPath();
					URL url = new URL(convertToURL(mHashMap.get("url")));
					// 创建连接
					HttpURLConnection conn = (HttpURLConnection) url
							.openConnection();
					conn.connect();
					// 获取文件大小
					int length = conn.getContentLength();
					// 创建输入流
					InputStream is = conn.getInputStream();

					File apkFile = new File(mSavePath, mHashMap.get("name"));
					FileOutputStream fos = new FileOutputStream(apkFile);
					int count = 0;
					// 缓存
					byte buf[] = new byte[1024];
					// 写入到文件中
					do {
						int numread = is.read(buf);
						count += numread;
						// 计算进度条位置
						progress = (int) (((float) count / length) * 100);
						// 更新进度
						mHandlerNew.sendEmptyMessage(DOWNLOAD);
						if (numread <= 0) {
							// 下载完成
							mHandlerNew.sendEmptyMessage(DOWNLOAD_FINISH);
							break;
						}
						// 写入文件
						fos.write(buf, 0, numread);
					} while (!cancelUpdate);// 点击取消就停止下载.
					fos.close();
					is.close();
				}
			} catch (MalformedURLException e) {
				mHandlerNew.sendEmptyMessage(DOWNLOAD_ERROR);
				e.printStackTrace();
			} catch (IOException e) {
				mHandlerNew.sendEmptyMessage(DOWNLOAD_ERROR);
				e.printStackTrace();
			}
			cancelUpdate = false;
		}
	};

	/**
	 * 下载文件线程
	 */
	private class downloadApkThread extends Thread {
	    private int cnt = 0;
	    
		@Override
		public void run() {
			try {
				// 判断SD卡是否存在，并且是否具有读写权限
				if (StorageUtil.isExternalMemoryAvailable()) {
					mSavePath = StorageUtil.getTempPath();
					URL url = new URL(convertToURL(mHashMap.get("url")));
					// 创建连接
					HttpURLConnection conn = (HttpURLConnection) url
							.openConnection();
					conn.connect();
					// 获取文件大小
					int length = conn.getContentLength();
					// 创建输入流
					InputStream is = conn.getInputStream();

					File apkFile = new File(mSavePath, mHashMap.get("name"));
					FileOutputStream fos = new FileOutputStream(apkFile);
					int count = 0;
					// 缓存
					byte buf[] = new byte[1024];
					// 写入到文件中
					do {
						int numread = is.read(buf);
						count += numread;
						// 计算进度条位置
						progress = (int) (((float) count / length) * 100);
						// 更新进度
						mHandler.sendEmptyMessage(DOWNLOAD);
						if (numread <= 0) {
							// 下载完成
							mHandler.sendEmptyMessage(DOWNLOAD_FINISH);
							break;
						}
						// 写入文件
						fos.write(buf, 0, numread);
					} while (!cancelUpdate);// 点击取消就停止下载.
					fos.close();
					is.close();
				}
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			// 取消下载对话框显示
			ProgressDialog.dismiss();
			cancelUpdate = false;
		}
	};

	/**
	 * 安装APK文件
	 */
	private void installApk() {
		File apkfile = new File(mSavePath, mHashMap.get("name"));
		if (!apkfile.exists()) {
			return;
		}
		Intent i = new Intent(Intent.ACTION_VIEW);
		if (Build.VERSION.SDK_INT >= 24) {
			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
			Uri contentUri = FileProvider.getUriForFile(mContext, "com.moft.xfapply.provider", apkfile);
			i.setDataAndType(contentUri, "application/vnd.android.package-archive");
		} else {
			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			i.setDataAndType(Uri.parse("file://" + apkfile.toString()), "application/vnd.android.package-archive");
		}
		mContext.startActivity(i);
	}

	/**
	 * 根据URL得到输入流
	 */
	public InputStream getInputStreamFromUrl(String urlStr)
			throws MalformedURLException, IOException {
		URL url = new URL(urlStr);
		HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
		InputStream inputStream = null;
		inputStream = urlConn.getInputStream();
		
		return inputStream;
	}

	public interface OnUpdateListener{
		void onUpdateStatus(UpgradeVersionInfo info);
	}

	public interface OnUpdateProgressListener{
		void onProgress(int progress);
		void onError();
	}

	public class UpgradeVersionInfo {
		public boolean isUpdate;
		public boolean isForceUpdate;
		public String verCode;
		public String prompt;

		public UpgradeVersionInfo() {
			isUpdate = false;
			isForceUpdate = false;
			verCode = "";
			prompt = "";
		}
	}
}
