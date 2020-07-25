package com.moft.xfapply.task;

import android.os.AsyncTask;
import android.util.Log;

import com.moft.xfapply.utils.FileUtil;
import com.moft.xfapply.utils.StorageUtil;
import com.moft.xfapply.utils.ZipUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by wangquan on 2017/4/25.
 */

public class DBTransferAsyncTask extends AsyncTask<String, Integer, Boolean> {
    private OnDBTransferListener listener;
    public DBTransferAsyncTask(OnDBTransferListener listener) {
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
    protected Boolean doInBackground(String... params) {
        Boolean ret = true;
        try {
            String zipPath = params[0];
            if(FileUtil.isFileExists(zipPath)) {
                String dbPath = StorageUtil.getTempPath() + params[1];
                List<File> list = ZipUtils.unzipFile(zipPath, StorageUtil.getTempPath());
                if(list != null && list.size() > 0) {
                    if (FileUtil.isFileExists(dbPath)) {
                        FileUtil.deleteFile(dbPath);
                    }
                    FileUtil.renameFile(list.get(0).getPath(), dbPath);
                }
            } else {
                Log.d("DBTransfer", "doInBackground Error");
                ret = false;
            }
        } catch (IOException e) {
            Log.d("DBTransfer", e.getMessage());
            e.printStackTrace();
            ret = false;
        }

        return ret;
    }

    @Override
    protected void onProgressUpdate(Integer... progress) {
    }

    @Override
    protected void onPostExecute(Boolean result) {
        if(listener != null) {
            listener.onPostExecute(result);
        }
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }

    public interface OnDBTransferListener {
        public void onPreExecute();
        public void onPostExecute(boolean success);
    }
}
