package com.moft.xfapply.push;

import android.app.Notification;
import android.content.Context;
import android.graphics.BitmapFactory;

import androidx.core.app.NotificationCompat;

import com.moft.xfapply.R;
import com.moft.xfapply.utils.SystemUtil;

public class NotificationUtil {
	private static NotificationCompat.Builder builder;
	private static Context mContext;
	public static int NOTIFICATION_SERVICE = -1;


	public static Notification getServiceNotification(Context context) {
		if (builder == null) {
			mContext = context;
			builder = new NotificationCompat.Builder(context);
			builder.setContentTitle(context.getString(R.string.app_name))
					.setContentText(getContent(context))
					.setTicker(context.getString(R.string.app_name))
					.setSmallIcon(R.drawable.offline)
					.setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.ic_launcher))
					.setWhen(System.currentTimeMillis())
					.setPriority(Notification.PRIORITY_DEFAULT)
					.setOngoing(true);
		} else {
			builder = builder.setContentText(getContent(context));
		}

		Notification notification = builder.build();
		return notification;
	}

	public static Notification getUpdateOnlineNotification(boolean isOnline) {
		Notification notification = null;
		if(builder != null) {
			builder.setContentText(getContent(mContext));
			if(isOnline) {
				builder.setContentTitle(String.format("%s", "在线"));
				builder.setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.ic_launcher));
				builder.setSmallIcon(R.drawable.online);
			} else {
				builder.setContentTitle(String.format("%s", "离线"));
				builder.setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.ic_launcher));
				builder.setSmallIcon(R.drawable.offline);
			}
			notification = builder.build();
		}
		return notification;
	}

	public static String getContent(Context context) {
		String result = "网络: internet, GPS: gps";
		boolean gpsStatus = false;
		boolean wifiStatus = false;

		gpsStatus = SystemUtil.checkGPS(context);
		if (gpsStatus) {
			result = result.replace("gps", "开");
		} else {
			result = result.replace("gps", "关");
		}

		wifiStatus = SystemUtil.checkNetwork(context);
		if (wifiStatus) {
			result = result.replace("internet", "开");
		} else {
			result = result.replace("internet", "关");
		}

		return result;
	}
}
