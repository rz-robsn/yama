package robsoninc.morse;

import java.util.UUID;

import android.app.Application;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class ApplicationMorse extends Application {

	@Override
	public void onCreate() {

		super.onCreate();

		SharedPreferences settings = getSharedPreferences(Constants.PREF_FILE,
				MODE_PRIVATE);
		if (settings.getString(Constants.USER_ID_KEY, "").length() == 0) {
			// Create new User id
			String uuid = UUID.randomUUID().toString();
			Editor editor = settings.edit();
			editor.putString(Constants.USER_ID_KEY, uuid);
			editor.commit();
		}

		// Registering the app to c2dm messaging
		Intent registrationIntent = new Intent(
				"com.google.android.c2dm.intent.REGISTER");
		registrationIntent.putExtra("app",
				PendingIntent.getBroadcast(this, 0, new Intent(), 0)); // boilerplate
		registrationIntent.putExtra("sender", Constants.C2DM_EMAIL);
		startService(registrationIntent);

		if (!(settings.getString(Constants.C2DM_ID_KEY, "").length() == 0)) {

			// Send the registration ID to the 3rd party site that is sending
			// the messages in a separate thread
			ThreadServerRegistration regThread = new ThreadServerRegistration(
					settings.getString(Constants.C2DM_ID_KEY, ""),
					settings.getString(Constants.USER_ID_KEY, ""));
			regThread.start();
		}
	}
}
