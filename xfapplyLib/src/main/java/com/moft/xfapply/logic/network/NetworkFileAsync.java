package com.moft.xfapply.logic.network;

import com.moft.xfapply.activity.indoor.util.ScreenUtils;
import com.moft.xfapply.logic.download.Downloader;
import com.moft.xfapply.logic.download.LoadInfo;
import com.moft.xfapply.utils.LogUtils;
import com.moft.xfapply.utils.StringUtil;

import android.os.Handler;
import android.os.Message;

public class NetworkFileAsync {
    private OnNetworkFileResultListener listener;
    private String url;
    private String key;
    private LoadInfo loadInfo = null;
    private Downloader downloader = null;
    private boolean isCompleted = false;
    
    public NetworkFileAsync(String url, OnNetworkFileResultListener listener) {  
        this.listener = listener;
        this.url = url;
    }  

    public NetworkFileAsync(String url, String key, OnNetworkFileResultListener listener) {  
        this.listener = listener;
        this.url = url;
        this.key = key;
    }  
    
    public void pause() {
        if (downloader == null) {
            return;
        }
        downloader.pause();
    } 
    
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0: // 初始化成功
                    LogUtils.d(String.format("NetworkFileAsync Handler msg = %d", msg.what));
                    loadInfo = (LoadInfo) msg.obj;
                    downloader.download();
                    break;
                case 1: // 下载进度更新
                    int length = msg.arg1;
                    loadInfo.increaseComplete(length);
                    int progress = loadInfo.getPercent();

                    if (listener != null) {
                        listener.onProgress(progress);
                    }
                    break;
                case 2: // 失败
                    LogUtils.d(String.format("NetworkFileAsync Handler msg = %d, info = %s", msg.what, loadInfo != null ? loadInfo.toString() : "null"));
                    if (listener != null) {
                        listener.onFailure((String) msg.obj);
                    }
                    break;
                case 3:  // 成功
                    LogUtils.d(String.format("NetworkFileAsync Handler msg = %d, completed = %b, info = %s", msg.what, isCompleted, loadInfo != null ? loadInfo.toString() : "null"));
                    if (listener != null && loadInfo.isComplete() && !isCompleted) {
                        listener.onSuccess((String) msg.obj);
                        isCompleted = true;
                        if(!StringUtil.isEmpty(url)) {
                            downloader.delete(url);
                        }
                    }
                    break;
            }
        }
    };
    
    public void execute(String localFile) {
        downloader = new Downloader(url, localFile, 6, mHandler);
        //重新下载前删除之前的续传信息
//        downloader.delete(url);
        loadInfo = downloader.getDownloaderInfors(); 
        downloader.download();
        isCompleted = false;
        return;
    }

    public void stop() {
        downloader.reset();
        if(!StringUtil.isEmpty(url)) {
            downloader.delete(url);
        }
    }

    public interface OnNetworkFileResultListener{
        void onSuccess(String result);
        void onFailure(String msg);
        void onProgress(int progress);
    }
}
