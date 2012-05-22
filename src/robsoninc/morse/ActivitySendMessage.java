package robsoninc.morse;

import android.app.Activity;
import android.os.Bundle;

public class ActivitySendMessage extends Activity {

	private String recipientId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.send_message);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		recipientId = getIntent().getExtras().getString(
				"robsoninc.morse.recipient_user_id");
		this.setTitle(String.format("%s %s",
				getString(R.string.send_message_activity_title),
				this.recipientId));
	}

}
