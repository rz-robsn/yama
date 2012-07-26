package robsoninc.morse;

import robsoninc.morse.utilities.AsyncTaskSendMessage;
import robsoninc.morse.utilities.MorseStringConverter;
import robsoninc.morse.utilities.TouchyModeOnTouchListener;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.ViewFlipper;

public class ActivitySendMessage extends Activity {

	private TextView message;
	private TextView morse_message;

	private static final int DIALOG_SEND_CONFIRM = 1;
	private static final int DIALOG_SEND_IN_PROGRESS = 2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.send_message);

		message = (TextView) this.findViewById(R.id.message);
		morse_message = (TextView) this.findViewById(R.id.morse_message);

		this.setTitle(getString(R.string.sndmsg_send_message_activity_title));
		
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
					}
				});

		findViewById(R.id.textView4).setOnTouchListener(new TouchyModeOnTouchListener() {

            @Override
            public void onShortTouch()
            {
                appendStringToMessage(MorseStringConverter.SHORT);
            }

            @Override
            public void onLongTouch()
            {
                appendStringToMessage(MorseStringConverter.LONG);
            }

            @Override
            public void onLineTouch()
            {
                appendStringToMessage(MorseStringConverter.GAP);
            }
		});
		
		this.findViewById(R.id.toggleButton1).setOnClickListener(
				new View.OnClickListener() {

					@Override
					public void onClick(View v) {

						ToggleButton toggle = (ToggleButton) v;
						ViewFlipper flippy = (ViewFlipper) findViewById(R.id.viewFlipper1);

						if (toggle.isChecked()) {
							flippy.showNext();
						} else {
							flippy.showPrevious();
						}
					}
				});
	}

	@Override
	protected void onResume() {
		super.onResume();		
	}

	@Override
	protected Dialog onCreateDialog(int id) {

		switch (id) {
		default:
			return super.onCreateDialog(id);
		}
	}

	@Override
	protected void onPrepareDialog(int id, Dialog dialog) {

		super.onPrepareDialog(id, dialog);
		switch (id) {
		case DIALOG_SEND_CONFIRM:
		case DIALOG_SEND_IN_PROGRESS:
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
				appendStringToMessage(s);
			}
		});
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

    private void appendStringToMessage(final String s)
    {
        message.setText(MorseStringConverter
        		.ConvertMorseToText(morse_message.getText() + s));
        morse_message.append(s);
    }
}
