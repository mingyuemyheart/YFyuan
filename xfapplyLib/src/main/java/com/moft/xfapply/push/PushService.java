package com.moft.xfapply.push;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.google.gson.Gson;
import com.moft.xfapply.R;
import com.moft.xfapply.app.Constant;
import com.moft.xfapply.model.common.MsgNoticeInfo;
import com.moft.xfapply.model.common.PushPayload;
import com.moft.xfapply.utils.GsonUtil;
import com.moft.xfapply.utils.LogUtils;
import com.moft.xfapply.utils.StorageUtil;
import com.moft.xfapply.utils.SystemUtil;
import com.moft.xfapply.utils.TelManagerUtil;
import com.moft.xfapply.utils.Utf8Util;

/**
 *
 */
public class PushService extends Service implements IMqttEventListener {
    private static final int INTERVAL = 10 * 1000;
    private static PushService instance = null;
    private String deviceId;

    /**
     * 创建Messenger并传入Handler实例对象
     */
    private final Messenger messenger = new Messenger(new IncomingHandler());
    private Messenger clientMessenger = null;

    /**
     * 用于接收从客户端传递过来的数据
     */
    private class IncomingHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Constant.MSG_REGISTER_CLIENT:
                    clientMessenger = msg.replyTo;
                    Intent initIntent = new Intent(PushService.this, EventReceiver.class);
                    initIntent.setAction(EventReceiver.EVENT_NOTIFY_CHECK_ACTION);
                    sendBroadcast(initIntent);
                    break;
                case Constant.MSG_EXIT_CLIENT:
                    Intent destroyIntent = new Intent(PushService.this, EventReceiver.class);
                    destroyIntent.setAction(EventReceiver.EVENT_NOTIFY_DESTROY_ACTION);
                    sendBroadcast(destroyIntent);
                    break;
                case Constant.MSG_NETWORK_CHANGED:
                    Intent ipIntent = new Intent(PushService.this, EventReceiver.class);
                    ipIntent.setAction(EventReceiver.EVENT_NOTIFY_NETWORK_CHANGED);
                    sendBroadcast(ipIntent);
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }

    //模拟后台推送消息
    //在新进程中启动的Service，负责监听服务器，
    // 收到服务器的信息后将消息广播出去
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return messenger.getBinder();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        clientMessenger = null;
        return super.onUnbind(intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        LogUtils.getBuilder().setDir(StorageUtil.getAppCachePath())
                .setLog2FileSwitch(true)
                .setLogSwitch(true)
                .setAppId("alarm")
                .setTag("alarm").create();
        LogUtils.d("service create");
        /**
         * PendingIntent ：四个参数 : this, 请求码，Intent，标记Flags
         *  Flags : PendingIntent.FLAG_UPDATE_CURRENT  更新当前存在的PendingIntent
         *
         *  是Intent的一种封装载体，用来在出发时，根据Intent唤起目标组件， 如Activity，Service， BroadcastReceiver
         *  eg ： 接收后台推送消息，在通知栏显示，当用户点击，唤醒指定的目标
         */
        //Intent intent = new Intent(this, EventReceiver.class);
        //intent.setAction(EventReceiver.EVENT_NOTIFY_INTERVAL_ACTION);
        //PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        /**
         * 获取系统 AlarmManager 设定一个时间来发广播
         * AlarmManager 的set()方法 用作设置一次性闹钟，
         * 三个参数: 闹钟类型， 响应时间， 响应的动作
         */
        //AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        //am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + INTERVAL, INTERVAL, pendingIntent);
        try {
            deviceId = TelManagerUtil.getInstance().getDeviceId();
        } catch (Exception e) {
            deviceId = null;
        }
        startTimer();

        Intent initIntent = new Intent(this, EventReceiver.class);
        initIntent.setAction(EventReceiver.EVENT_NOTIFY_INIT_ACTION);
        sendBroadcast(initIntent);

        if(!SystemUtil.checkGPS(this)) {
            SystemUtil.openGPS(this);
        }

        Notification notification = NotificationUtil.getServiceNotification(this);
        startForeground(NotificationUtil.NOTIFICATION_SERVICE, notification);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        return super.onStartCommand(intent, flags, startId);
        LogUtils.d("service start");
        return Service.START_STICKY;
    }

    @Override
    public void onDestroy() {
        Intent intent = new Intent(this, EventReceiver.class);
        intent.setAction(EventReceiver.EVENT_NOTIFY_DESTROY_ACTION);
        sendBroadcast(intent);

        stopForeground(true);
        LogUtils.d("service destroy");

        super.onDestroy();
    }

    private void startTimer() {
        Intent intent = new Intent(this, EventReceiver.class);
        intent.setAction(EventReceiver.EVENT_NOTIFY_INTERVAL_ACTION);
        PendingIntent sender = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            am.setWindow(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + INTERVAL, 100, sender);
        } else {
            am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + INTERVAL, INTERVAL, sender);
        }
    }

    public void restartTimer() {
        Intent intent = new Intent(this, EventReceiver.class);
        intent.setAction(EventReceiver.EVENT_NOTIFY_INTERVAL_ACTION);
        PendingIntent sender = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            am.setWindow(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + INTERVAL, 100, sender);
        }
    }

    public static synchronized PushService getInstance() {
        return instance;
    }

    public synchronized Messenger getClientMessenger() {
        return clientMessenger;
    }

    public void initMqttServer(final Context context) {
        MqttManager mqttManager = MqttManager.getInstance();

        mqttManager.initMqttClient(this, deviceId);
    }

    public void connectMqttServer() {
        MqttManager mqttManager = MqttManager.getInstance();
        if(!mqttManager.isConnected()) {
            mqttManager.connectMqttServer();
        }
    }

    public void disconnectMqttServer() {
        MqttManager mqttManager = MqttManager.getInstance();

        mqttManager.disconnectMqttServer();

        updateNotificationOnline(false);
    }

    @Override
    public void onConnectionLost() {
        updateNotificationOnline(false);
    }

    @Override
    public void onMessageArrived(String content) {
        if (content != null && !content.isEmpty()) {
            LogUtils.d(content);
            MsgNoticeInfo info = createNoticeInfo(content);
            info.setId(1);
            //ID:T886079 19-03-01 【推送】App显示界面时，直接推送到界面文言提示，可打开重点单位详细。 王泉 开始
            Messenger client = PushService.getInstance().getClientMessenger();

            if (client != null) {
                try {
                    Message msg = Message.obtain(null, Constant.MSG_NOTIFY_SHARE);

                    Bundle params = new Bundle();
                    params.putSerializable("msg_info", info);
                    msg.setData(params);
                    client.send(msg);
                } catch (Exception e) {

                }
            }
            //ID:T886079 19-03-01 【推送】App显示界面时，直接推送到界面文言提示，可打开重点单位详细。 王泉 结束
            updateNotificationMsg(this, info);
        }
    }

    @Override
    public void onConnectSuccess() {
        updateNotificationOnline(true);
    }

    @Override
    public void onConnectFailure() {
        updateNotificationOnline(false);
    }

    private MsgNoticeInfo createNoticeInfo(String content) {
        Gson gson = GsonUtil.create();
        PushPayload payload = gson.fromJson(content, PushPayload.class);

        MsgNoticeInfo info = new MsgNoticeInfo();
        info.setNoticeType("");
        info.setTitle("重点单位");
        info.setContent("您收到新的重点单位，请查看！");
        info.setNoticeEntityType(payload.getEntityType());
        info.setNoticeEntityUuid(payload.getEntityUuid());
        return info;
    }

    private String getEncodingType(byte[] bstream) {
        String code = "GBK";
        if (Utf8Util.isUtf8(bstream)) {
            code = "UTF-8";
        }
        return code;
    }

    private void updateNotificationOnline(boolean online) {
        Notification notification = NotificationUtil.getUpdateOnlineNotification(online);
        startForeground(NotificationUtil.NOTIFICATION_SERVICE, notification);
    }

    private void updateNotificationMsg(Context context, MsgNoticeInfo info) {
        if (info != null) {
            Intent boradcastIntent = new Intent(context, NotificationReceiver.class);
            boradcastIntent.setAction("msg_action");
            boradcastIntent.putExtra("msg_info", info);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, info.getId(), boradcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
            builder.setContentTitle(info.getTitle())
                    .setContentText(info.getContent())
                    .setTicker("预案应用")
                    .setContentIntent(pendingIntent)
                    .setSmallIcon(R.drawable.pushicon)
                    .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.pushicon))
                    .setWhen(System.currentTimeMillis())
                    .setPriority(Notification.PRIORITY_HIGH)
                    .setOngoing(true)
                    .setAutoCancel(true);
            builder.setDefaults(Notification.DEFAULT_SOUND);

            NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            manager.notify(info.getId(), builder.build());
        }
    }
}
