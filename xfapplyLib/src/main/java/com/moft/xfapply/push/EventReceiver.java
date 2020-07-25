package com.moft.xfapply.push;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

import com.moft.xfapply.utils.SystemUtil;
import com.moft.xfapply.utils.Utils;

public class EventReceiver extends BroadcastReceiver {
    public static final String EVENT_NOTIFY_INTERVAL_ACTION = "EVENT_NOTIFY_INTERVAL";
    public static final String EVENT_NOTIFY_INIT_ACTION = "EVENT_NOTIFY_INIT";
    public static final String EVENT_NOTIFY_DESTROY_ACTION = "EVENT_NOTIFY_DESTROY";
    public static final String EVENT_NOTIFY_CHECK_ACTION = "EVENT_NOTIFY_CHECK";
    public static final String EVENT_NOTIFY_NETWORK_CHANGED = "EVENT_NOTIFY_NETWORK_CHANGED";

    //模拟接收后台PushService推送的消息
    //设置点击通知栏的动作为启动另外一个广播

    //在新进程中注册的BroadcastReceiver，
    // 收到PushService发的消息后，会在通知栏弹出通知
    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            Intent i = new Intent(Intent.ACTION_RUN);
            i.setClass(context, PushService.class);
            context.startService(i);

            return;
        }

        PushService pushService = PushService.getInstance();
        if(pushService == null) {
            return;
        }

        if(EVENT_NOTIFY_DESTROY_ACTION.equals(intent.getAction())) {
            pushService.disconnectMqttServer();
//            LvApplication.getInstance().startPushService();
        } else if(EVENT_NOTIFY_INIT_ACTION.equals(intent.getAction())) {
            pushService.initMqttServer(context);
            pushService.connectMqttServer();
        } else if(EVENT_NOTIFY_INTERVAL_ACTION.equals(intent.getAction())) {
            pushService.restartTimer();
            if(SystemUtil.checkNetwork(context)) {
                pushService.connectMqttServer();
            }
        } else if(ConnectivityManager.CONNECTIVITY_ACTION.equals((intent.getAction()))) {
            if(SystemUtil.checkNetwork(context)) {
                pushService.connectMqttServer();
            }
        } else if (EVENT_NOTIFY_CHECK_ACTION.equals(intent.getAction())) {
            if(SystemUtil.checkNetwork(context)) {
                pushService.connectMqttServer();
            }
        } else if (EVENT_NOTIFY_NETWORK_CHANGED.equals(intent.getAction())) {
//            if(SystemUtil.checkNetwork(context)) {
//                pushService.connectMqttServer();
//            }
        }
    }
}
