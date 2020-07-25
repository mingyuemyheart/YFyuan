package com.moft.xfapply.crash;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.text.format.Time;

import com.moft.xfapply.activity.ReportErrorActivity;
import com.moft.xfapply.app.Constant;
import com.moft.xfapply.task.SendReportAsyncTask;
import com.moft.xfapply.utils.StorageUtil;
import com.moft.xfapply.utils.StringUtil;
import com.moft.xfapply.utils.TelManagerUtil;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.TreeSet;

/**
 * Created by Administrator on 2017/6/15.
 */

public class CrashHandler implements Thread.UncaughtExceptionHandler {

    /** Debug Log tag*/
    public static final String TAG = "CrashHandler";
    /** 程序的Context对象 */
    private Context mContext;
    /** 错误报告文件的扩展名 */
    private static final String CRASH_REPORTER_EXTENSION = ".cr";
    private String reportFilePath = "";

    /** CrashHandler实例 */
    public CrashHandler(Context ctx) {
        mContext = ctx;
    }

    /**
     * 当UncaughtException发生时会转入该函数来处理
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (ex == null) {
            return;
        }
        //保存错误报告文件
        saveCrashInfoToFile(ex);
//        //发送错误报告到服务器
//        sendCrashReportsToServer();

        Intent intent = new Intent(mContext, ReportErrorActivity.class);
        mContext.startActivity(intent);
        crash(mContext);
    }

    public void uncaughtException(Exception e) {
        uncaughtException(Thread.currentThread(), e);
    }

    private boolean crash(Context context) {
        if (context == null) {
            return false;
        }

        if (context instanceof Activity) {
            ((Activity) context).finish();
        }

        android.os.Process.killProcess(android.os.Process.myPid());
        return true;
    }

    /**
     * 保存错误信息到文件中
     * @param ex
     * @return
     */
    private String saveCrashInfoToFile(Throwable ex) {
        String strTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

        //long timestamp = System.currentTimeMillis();
        Time t = new Time("GMT+8");
        t.setToNow(); // 取得系统时间
        int date = t.year * 10000 + t.month * 100 + t.monthDay;
        int time = t.hour * 10000 + t.minute * 100 + t.second;

        reportFilePath = StorageUtil.getAppCachePath() + Constant.CRASH_LOG + "-" + date + "-" + time + CRASH_REPORTER_EXTENSION;
        File file = new File(reportFilePath);
        try
        {
            //往文件中写入数据
            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));
            pw.println(strTime);
            pw.println(appendPhoneInfo());
            ex.printStackTrace(pw);
            pw.close();
        } catch (IOException e1)
        {
            e1.printStackTrace();
        } catch (PackageManager.NameNotFoundException e1)
        {
            e1.printStackTrace();
        }
        return null;
    }

    private String appendPhoneInfo() throws PackageManager.NameNotFoundException {
        PackageManager pm = mContext.getPackageManager();
        PackageInfo pi = pm.getPackageInfo(mContext.getPackageName(), PackageManager.GET_ACTIVITIES);
        StringBuilder sb = new StringBuilder();
        //App版本
        sb.append("App Version: ");
        sb.append(pi.versionName);
        sb.append("_");
        sb.append(pi.versionCode + "\n");

        //Android版本号
        sb.append("OS Version: ");
        sb.append(Build.VERSION.RELEASE);
        sb.append("_");
        sb.append(Build.VERSION.SDK_INT + "\n");

        //手机制造商
        sb.append("Vendor: ");
        sb.append(Build.MANUFACTURER + "\n");

        //手机型号
        sb.append("Model: ");
        sb.append(Build.MODEL + "\n");

        //CPU架构
        sb.append("CPU: ");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            sb.append(Arrays.toString(Build.SUPPORTED_ABIS));
        } else {
            sb.append(Build.CPU_ABI);
        }
        return sb.toString();
    }

    /**
     * 在程序启动时候, 可以调用该函数来发送以前没有发送的报告
     */
    public static void sendPreviousReportsToServer() {
        sendCrashReportsToServer(null);
    }

    public static void sendCrashReportsToServer(SendReportAsyncTask.OnSendReportListener listener) {
        String[] crFiles = getCrashReportFiles();
        if (crFiles != null && crFiles.length > 0) {
            TreeSet<String> sortedFiles = new TreeSet<String>();
            sortedFiles.addAll(Arrays.asList(crFiles));
            List<File> files = new ArrayList<>();
            for (String fileName : sortedFiles) {
                File file = new File(StorageUtil.getAppCachePath(), fileName);
                files.add(file);
            }
            postReport(files, listener);
        }
    }
    private static void postReport(List<File> files, SendReportAsyncTask.OnSendReportListener listener) {
        // TODO 发送错误报告到服务器
        SendReportAsyncTask task = new SendReportAsyncTask(listener);
        task.execute(TelManagerUtil.getInstance().getDeviceId(), files);
    }

    private static String[] getCrashReportFiles() {
        String crashPath = StorageUtil.getAppCachePath();
        if (StringUtil.isEmpty(crashPath)) {
            return null;
        }
        File filesDir = new File(crashPath);
        FilenameFilter filter = new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.endsWith(CRASH_REPORTER_EXTENSION);
            }
        };
        return filesDir.list(filter);
    }
}