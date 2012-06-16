package robsoninc.morse;

import robsoninc.morse.utilities.MorseStringConverter;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ActivitySendMessage extends Activity {

	private String recipientId;

	private TextView message;
	private TextView morse_message;

	private static final int DIALOG_SEND_CONFIRM = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.send_message);

		message = (TextView) this.findViewById(R.id.message);
		morse_message = (TextView) this.findViewById(R.id.morse_message);

		this.setOnClickListenerToAppendStringToMessage(
				findViewById(R.id.button_short), MorseStringConverter.SHORT);
		this.setOnClickListenerToAppendStringToMessage(
				findViewById(R.id.button_long), MorseStringConverter.LONG);
		this.setOnClickListenerToAppendStringToMessage(
				findViewById(R.id.button_space), MorseStringConverter.GAP);

		this.findViewById(R.id.button_send_message).setOnClickListener(
				new View.OnClickListener() {

					@Override
					public void onClick(View arg0) {
						ActivitySendMessage.this
								.showDialog(DIALOG_SEND_CONFIRM);
					}
				});
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

	@Override
	protected Dialog onCreateDialog(int id) {

		switch (id) {
		case DIALOG_SEND_CONFIRM:
			AlertDialog.Builder builder = new AlertDialog.Builder(
					ActivitySendMessage.this);
			builder.setCancelable(false)
					.setMessage("")
					.setTitle("Confirm Send Message")
					.setPositiveButton("Yes",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// Send data
								}
							})
					.setNegativeButton("Cancel",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									dialog.cancel();
								}
							});
			return builder.create();

		default:
			return super.onCreateDialog(id);
		}
	}

	@Override
	protected void onPrepareDialog(int id, Dialog dialog) {

		super.onPrepareDialog(id, dialog);

		switch (id) {
		case DIALOG_SEND_CONFIRM:
			AlertDialog alert = (AlertDialog) dialog;
			alert.setMessage(String
					.format("You are about to send the following message :\n\"%s\" \nConfirm ? ",
							this.message.getText()));
			alert.show();
			break;

		default:
			break;
		}
	}

	/**
	 * Add character to morse message
	 * */
	private void setOnClickListenerToAppendStringToMessage(View view,
			final String s) {
		view.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				message.setText(MorseStringConverter
						.ConvertMorseToText(morse_message.getText() + s));
				morse_message.append(s);
			}
		});
	}

}
