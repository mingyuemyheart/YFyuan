package com.moft.xfapply.task;

import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.moft.xfapply.utils.DbUtil;
import com.moft.xfapply.utils.FileUtil;
import com.moft.xfapply.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangquan on 2017/4/25.
 */

public class UpdateIncOfflineDBAsyncTask extends AsyncTask<Object, Integer, List<String>> {
    private OnUpdateIncOfflineDBListener listener;

    public UpdateIncOfflineDBAsyncTask(OnUpdateIncOfflineDBListener listener) {
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
    protected List<String> doInBackground(Object... params) {
        List<String> incSqlList = (List<String>)params[0];
        String dbName = (String)params[1];
        List<String> exceptions = new ArrayList<>();
        SQLiteDatabase db = DbUtil.getInstance().getRawDB(dbName);

        try {
            exceptions = DbUtil.getInstance().executeSQLBatch(db, incSqlList);
        } catch (Exception e) {
            exceptions.add(e.getMessage());
            LogUtils.d(String.format("UpdateIncOfflineDBAsyncTask doInBackground exception = %s", e.getMessage()));
        } finally {
            DbUtil.getInstance().closeRawDB(db);
        }
        return exceptions;
    }

    @Override
    protected void onProgressUpdate(Integer... progress) {
    }

    @Override
    protected void onPostExecute(List<String> exceptions) {
        if(listener != null) {
            listener.onPostExecute(exceptions);
        }
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }

    public interface OnUpdateIncOfflineDBListener {
        public void onPreExecute();
        public void onPostExecute(List<String> exceptions);
    }
}
