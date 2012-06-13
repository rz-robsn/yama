package robsoninc.morse;

import robsoninc.morse.utilities.MorseStringConverter;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ActivitySendMessage extends Activity {

	private String recipientId;

	private TextView message;
	private TextView morse_message;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.send_message);

		message = (TextView) this.findViewById(R.id.message);
		morse_message = (TextView) this.findViewById(R.id.morse_message);

		this.findViewById(R.id.button_short).setOnClickListener(
				new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						appendStringToMessage(MorseStringConverter.SHORT);
					}
				});

		this.findViewById(R.id.button_long).setOnClickListener(
				new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						appendStringToMessage(MorseStringConverter.LONG);
					}
				});

		this.findViewById(R.id.button_space).setOnClickListener(
				new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						appendStringToMessage(MorseStringConverter.GAP);
					}
				});

	}

	/**
	 * Add character to morse
	 * */
	private void appendStringToMessage(String s) {
		this.message.setText(MorseStringConverter
				.ConvertMorseToText(this.morse_message.getText() + s));
		this.morse_message.append(s);
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
