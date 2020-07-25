package com.moft.xfapply.push;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

import com.moft.xfapply.activity.SplashActivity;
import com.moft.xfapply.app.Constant;
import com.moft.xfapply.model.common.MsgNoticeInfo;
import com.moft.xfapply.utils.SystemUtil;

public class NotificationReceiver extends BroadcastReceiver {

    //点击推送消息后， 进行判断
    //在新进程中注册的BroadcastReceiver，
    // 用来设置点击通知栏通知的动作，打开app中的某个Activity
    @Override
    public void onReceive(Context context, Intent intent) {
        //判断app是否存活
        Messenger client = PushService.getInstance().getClientMessenger();

        if (SystemUtil.isAppAlive(context, "com.moft.xfapply") && (client != null)) {
            Message msg = Message.obtain(null, Constant.MSG_NOTIFY_OPEN_SHARE);
            MsgNoticeInfo info = (MsgNoticeInfo) intent.getSerializableExtra("msg_info");
            Bundle params = new Bundle();
            params.putSerializable("msg_info", info);
            msg.setData(params);
            try {
                client.send(msg);
            }catch (RemoteException e) {
//                Intent mainIntent = new Intent(context, MainActivity.class);
//                //mainIntent.putExtra("complaint_list", intent.getSerializableExtra("complaint_list"));
//                // 开启一个新的栈 有相同就放进去  并清空上面的栈  | 有错就为错 这两个都得实现
//                mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                context.startActivity(mainIntent);
            }
        } else {
            Intent launchIntent = new Intent(context, SplashActivity.class);
            //FLAG_ACTIVITY_RESET_TASK_IF_NEEDED.如果被启动的Activity已经在栈顶，那它就不会被再次启动。
            launchIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(launchIntent);
        }
    }
}

