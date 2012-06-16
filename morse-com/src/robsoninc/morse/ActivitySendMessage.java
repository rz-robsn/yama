package robsoninc.morse;

import org.apache.http.HttpResponse;

import robsoninc.morse.utilities.AsyncTaskSendMessage;
import robsoninc.morse.utilities.MorseStringConverter;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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
				getString(R.string.sndmsg_send_message_activity_title),
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
					.setTitle(getString(R.string.sndmsg_send_dialog_title))
					.setPositiveButton(getString(R.string.dialog_ok_button),
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {

									// Send message push instruction to
									// webserver
									AsyncTaskSendMessage sendTask = new AsyncTaskSendMessage() {

										@Override
										protected void onPostExecute(
												HttpResponse result) {
											super.onPostExecute(result);

											if (result.getStatusLine()
													.getStatusCode() == 200) {
												Toast.makeText(
														ActivitySendMessage.this,
														R.string.sndmsg_send_success_message,
														Toast.LENGTH_SHORT);
												emptyMessageBoxes();
											} else {
												Toast.makeText(
														ActivitySendMessage.this,
														R.string.sndmsg_send_fail_message,
														Toast.LENGTH_LONG);
											}
										}

										@Override
										protected void onProgressUpdate(
												Void... values) {
											// TODO Auto-generated method stub
											super.onProgressUpdate(values);
										}
									};
									SharedPreferences settings = getSharedPreferences(
											Constants.PREF_FILE, MODE_PRIVATE);
									sendTask.setRecipientId(recipientId);
									sendTask.setSenderId(settings.getString(
											Constants.USER_ID_KEY, ""));

									sendTask.execute((Void[]) null);
								}
							})
					.setNegativeButton(
							getString(R.string.dialog_cancel_button),
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
			alert.setMessage(dialog.getContext().getString(
					R.string.sndmsg_send_dialog_body, this.message.getText()));
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

	public String getRecipientId() {
		return recipientId;
	}

	public TextView getMessage() {
		return message;
	}

	public TextView getMorse_message() {
		return morse_message;
	}

	private void emptyMessageBoxes() {
		((TextView) this.findViewById(R.id.message)).setText("");
		((TextView) this.findViewById(R.id.morse_message)).setText("");
	}
}
