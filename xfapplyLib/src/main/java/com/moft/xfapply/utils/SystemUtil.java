package com.moft.xfapply.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.moft.xfapply.app.LvApplication;

import java.util.List;

/**
 * Created by wangquan on 2016/11/11.
 */

public class SystemUtil {
    public final static void callPhoneNumber(Activity activity, String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        if (intent.resolveActivity(activity.getPackageManager()) != null) {
            activity.startActivity(intent);
        }
    }
    /**
     * 判断GPS是否开启，GPS或者AGPS开启一个就认为是开启的
     * @param context
     * @return true 表示开启
     */
    public static final boolean checkGPS(final Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        // 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        return gps;
    }

    public static final boolean checkNetwork(final Context context) {
        boolean connected = false;
        ConnectivityManager connectManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        // 通过WLAN或移动网络(3G/2G)确定的位置（也称作AGPS，辅助GPS定位。主要用于在室内或遮盖物（建筑群或茂密的深林等）密集的地方定位）
        NetworkInfo info = connectManager.getActiveNetworkInfo();
        if(info != null) {
            connected = info.isConnected();
        }
        return connected;
    }

    public static final boolean checkNetwork(Intent intent) {
        boolean connected = false;
        //获取联网状态的NetworkInfo对象
        NetworkInfo info = intent.getParcelableExtra(ConnectivityManager.EXTRA_NETWORK_INFO);
        if (info != null) {
            //如果当前的网络连接成功并且网络连接可用
            if (NetworkInfo.State.CONNECTED == info.getState() && info.isAvailable()) {
                if (info.getType() == ConnectivityManager.TYPE_WIFI
                        || info.getType() == ConnectivityManager.TYPE_MOBILE) {
                    connected = true;
                }
            } else {

            }
        }
        return connected;
    }

    /**
     * 强制帮用户打开GPS
     * @param context
     */
    public static final void openGPS(Context context) {
        Intent GPSIntent = new Intent();
        GPSIntent.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
        GPSIntent.addCategory("android.intent.category.ALTERNATIVE");
        GPSIntent.setData(Uri.parse("custom:3"));
        try {
            PendingIntent.getBroadcast(context, 0, GPSIntent, 0).send();
        } catch (PendingIntent.CanceledException e) {
            e.printStackTrace();
        }
    }

    /*
 * 获取MAC地址
 */
    public String getMacAddress() {
        WifiManager wifi = (WifiManager) LvApplication.getInstance().getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();

        return info.getMacAddress();
    }

    public final static void closeKeybord(Activity activity, View view) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * 判断应用是否已经启动
     *
     * @param context     一个context
     * @param packageName 要判断应用的包名
     * @return boolean
     */
    public static boolean isAppAlive(Context context, String packageName) {
        boolean isAlive = false;
        ActivityManager activityManager =
                (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> processInfos
                = activityManager.getRunningAppProcesses();
        for (int index = 0; index < processInfos.size(); index++) {
            if (processInfos.get(index).processName.equals(packageName)) {
                isAlive = true;
                break;
            }
        }
        return isAlive;
    }
    /**
     * 用来判断服务是否运行.
     * @param context
     * @param className 判断的服务名字
     * @return true 在运行 false 不在运行
     */
    public static boolean isServiceRunning(Context context,String className) {
        boolean isRunning = false;
        ActivityManager activityManager = (ActivityManager)
                context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> serviceList
                = activityManager.getRunningServices(Integer.MAX_VALUE);
        for (ActivityManager.RunningServiceInfo item : serviceList) {
            if (item.service.getClassName().equals(className)) {
                isRunning = true;
                break;
            }
        }
        return isRunning;
    }

    public static boolean hasServiceRunning(Context context) {
        ActivityManager activityManager = (ActivityManager)
                context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> serviceList
                = activityManager.getRunningServices(Integer.MAX_VALUE);
        return serviceList.size() > 0;
    }

    public static boolean isActivityRunning(Context context, String className) {
        boolean isRunning = false;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> taskList = am.getRunningTasks(Integer.MAX_VALUE);
        if (taskList != null && taskList.size() > 0) {
            for (ActivityManager.RunningTaskInfo item : taskList) {
                ComponentName cpn = item.topActivity;
                if (className.equals(cpn.getClassName())) {
                    isRunning = true;
                    break;
                }
            }

        }
        return isRunning;
    }

    public static boolean isForeground(Context context, String className) {
        boolean ret = false;
        if (!TextUtils.isEmpty(className)) {
            ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(1);
            if (list != null && list.size() > 0) {
                ComponentName cpn = list.get(0).topActivity;
                if (className.equals(cpn.getClassName())) {
                    ret = true;
                }
            }
        }
        return ret;
    }

    public static void moveTaskToFront(Context context, int taskId) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        am.moveTaskToFront(taskId, 0);

        return;
    }

    public static int getVersionCode() {
        int versionCode = 0;
        try {
            // 获取软件版本号，对应AndroidManifest.xml下android:versionCode
            Context context = LvApplication.getInstance().getApplicationContext();
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0);
            versionCode = info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    private static boolean isWebchatAvaliable(Context context) {
        //检测手机上是否安装了微信
        try {
            context.getPackageManager().getPackageInfo("com.tencent.mm", PackageManager.GET_ACTIVITIES);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
