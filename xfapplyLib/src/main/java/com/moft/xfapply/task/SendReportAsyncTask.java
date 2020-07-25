package com.moft.xfapply.task;

import android.os.AsyncTask;

import com.moft.xfapply.utils.DateUtil;
import com.moft.xfapply.utils.FtpUtil;

import java.io.File;
import java.util.Calendar;
import java.util.List;

/**
 * Created by wangquan on 2017/4/25.
 */

public class SendReportAsyncTask extends AsyncTask<Object, Integer, String> {
    private OnSendReportListener listener = null;
    private final String LOG_EXTENSION = ".log";
    private int totalCnt = 0;
    private int totalSuccessCnt = 0;
    private int totalFailureCnt = 0;

    public SendReportAsyncTask(OnSendReportListener listener) {
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
    protected String doInBackground(Object... params) {
        String devId = (String)params[0];
        List<File> files = (List<File>)params[1];
        totalSuccessCnt = 0;
        totalFailureCnt = 0;
        totalCnt = files.size();
        publishProgress(totalSuccessCnt, totalFailureCnt, totalCnt);
        FtpUtil ftp = new FtpUtil();
        try {
            ftp.uploadMultiFile(files, devId, new FtpUtil.FtpProgressListener() {
                @Override
                public void onFtpProgress(int currentStatus, long process, File targetFile) {
                    if(FtpUtil.FTP_UPLOAD_SUCCESS == currentStatus) {
                        totalSuccessCnt++;
                        publishProgress(totalSuccessCnt, totalFailureCnt, totalCnt);
                        if (targetFile.isFile()) {
                            String fileName = targetFile.getName();
                            if(fileName.endsWith(LOG_EXTENSION)) {
                                String strFileDate = targetFile.getName().substring(fileName.length() - (10 + LOG_EXTENSION.length()), (fileName.length() - LOG_EXTENSION.length()));
                                Calendar fileDate = DateUtil.parseDateStr(strFileDate, "yyyy-MM-dd");
                                Calendar newDate = DateUtil.parseDateStr(DateUtil.getCurrentDate(), "yyyy-MM-dd");
                                if(fileDate.compareTo(newDate) < 0) {
                                    targetFile.delete();
                                }
                            } else {
                                targetFile.delete();
                            }
                        }
                    } else if (FtpUtil.FTP_UPLOAD_FAIL == currentStatus) {
                        totalFailureCnt++;
                        publishProgress(totalSuccessCnt, totalFailureCnt, totalCnt);
                    } else if (FtpUtil.FTP_CONNECT_FAIL == currentStatus) {
                        totalFailureCnt = totalCnt;
                        publishProgress(totalSuccessCnt, totalFailureCnt, totalCnt);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... progress) {
        if(listener != null) {
            listener.onProgress(progress);
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

    public interface OnSendReportListener {
        public void onPreExecute();
        public void onProgress(Integer... progress);
        public void onPostExecute();
    }
}
