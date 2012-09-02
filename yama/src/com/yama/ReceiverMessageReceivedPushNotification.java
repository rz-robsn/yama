package com.yama;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ReceiverMessageReceivedPushNotification extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {

		String message = intent.getExtras().getString("message");
		String senderId = intent.getExtras().getString("sender_id");
		
		// Posting a notification
		NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);	
		Notification n = new Notification(
				R.drawable.ic_launcher, 
				message, 
				System.currentTimeMillis());
		
		String noticeTitle = context.getResources().getString(R.string.new_msg_notif_title);
		
		Intent notificationIntent = new Intent(context, ActivityMain.class);
		notificationIntent.putExtras(intent.getExtras());
		
		PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);
		n.setLatestEventInfo(
				context, 
				noticeTitle, 
				context.getResources().getString(R.string.new_msg_notif_body, senderId, message), 
				contentIntent);
		
		manager.notify(Constants.NOTIFICATION_NEW_MESSAGE_ID, n);
	}

}
