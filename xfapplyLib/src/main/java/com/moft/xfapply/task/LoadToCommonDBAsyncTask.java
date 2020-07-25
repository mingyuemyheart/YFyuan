package com.moft.xfapply.task;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.moft.xfapply.activity.bussiness.CommonBussiness;

/**
 * Created by wangquan on 2017/4/25.
 */

public class LoadToCommonDBAsyncTask extends AsyncTask<String, String, String> {
    private ProgressDialog dialog;
    private OnLoadToCommonDBListener listener;
    public LoadToCommonDBAsyncTask(OnLoadToCommonDBListener listener) {
        super();
        this.listener = listener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if(listener != null) {
            listener.onPreExecute();
        }
    }

    @Override
    protected String doInBackground(String... params) {
        CommonBussiness.getInstance().loadToCommonDB(params[0]);

        return "";
    }

    @Override
    protected void onProgressUpdate(String... progress) {
        String name = progress[0];
        String totalCnt = progress[1];
        String curCnt = progress[2];
        if(listener != null) {
            listener.onProgressUpdate("正在加载离线数据包...\n" + name + "[" + curCnt +  "/" + totalCnt + "]");
        }
    }

    @Override
    protected void onPostExecute(String result) {
        if(listener != null) {
            listener.onPostExecute();
        }
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }

    public interface OnLoadToCommonDBListener {
        public void onPreExecute();
        public void onProgressUpdate(String msg);
        public void onPostExecute();
    }
}