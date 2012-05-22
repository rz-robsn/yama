package robsoninc.morse;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

public class ReceiverC2dmRegistration extends BroadcastReceiver {	

	@Override
	public void onReceive(Context context, Intent intent) {
		if (intent.getAction().equals(
				"com.google.android.c2dm.intent.REGISTRATION")) {
			handleRegistration(context, intent);
		} else if (intent.getAction().equals(
				"com.google.android.c2dm.intent.RECEIVE")) {
			handleMessage(context, intent);
		}
	}

	private void handleRegistration(Context context, Intent intent) {
		String registration = intent.getStringExtra("registration_id");
		if (intent.getStringExtra("error") != null) {
			// Registration failed, should try again later.
			Log.d("c2dm", "registration failed");
			String error = intent.getStringExtra("error");
			if (error == "SERVICE_NOT_AVAILABLE") {
				Log.d("c2dm", "SERVICE_NOT_AVAILABLE");
			} else if (error == "ACCOUNT_MISSING") {
				Log.d("c2dm", "ACCOUNT_MISSING");
			} else if (error == "AUTHENTICATION_FAILED") {
				Log.d("c2dm", "AUTHENTICATION_FAILED");
			} else if (error == "TOO_MANY_REGISTRATIONS") {
				Log.d("c2dm", "TOO_MANY_REGISTRATIONS");
			} else if (error == "INVALID_SENDER") {
				Log.d("c2dm", "INVALID_SENDER");
			} else if (error == "PHONE_REGISTRATION_ERROR") {
				Log.d("c2dm", "PHONE_REGISTRATION_ERROR");
			}
		} else if (intent.getStringExtra("unregistered") != null) {
			// unregistration done, new messages from the authorized sender will
			// be rejected
			Log.d("c2dm", "unregistered");

		} else if (registration != null) {
			Log.d("c2dm", registration);

			/* saving registration key */
			SharedPreferences settings = context.getSharedPreferences(
					Constants.PREF_FILE, Context.MODE_PRIVATE);
			Editor editor = settings.edit();
			editor.putString(Constants.C2DM_ID_KEY, registration);
			editor.commit();
			
			// Send the registration ID to the 3rd party site that is sending
			// the messages in a separate thread
			ThreadServerRegistration regThread = new ThreadServerRegistration(
					settings.getString(Constants.C2DM_ID_KEY, ""),
					settings.getString(Constants.USER_ID_KEY, ""));
			regThread.start();

		} else {
			Log.d("c2dm", "registration=null.");
		}
	}

	private void handleMessage(Context context, Intent intent) {
		// Do whatever you want with the message
	}
}
