package robsoninc.morse;

import robsoninc.morse.utilities.MorseSignal;
import robsoninc.morse.utilities.MorseStringConverter;
import robsoninc.morse.utilities.OnTouchListenerTelegraphMode;
import robsoninc.morse.utilities.OnTouchListenerTouchyMode;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;
import android.widget.ToggleButton;
import android.widget.ViewFlipper;

public class ActivitySendMessage extends Activity
{

    private TextView message;
    private TextView morse_message;

    private static final int DIALOG_SEND_CONFIRM = 1;
    private static final int DIALOG_SEND_IN_PROGRESS = 2;
    
    private Handler modeListener = new Handler()
    {    	
        @Override
        public void handleMessage(Message msg)
        {
	        super.handleMessage(msg);
	        switch(msg.arg1)
	        {
                case MorseSignal.DIT:
                	appendStringToMessage(MorseStringConverter.SHORT);        
                    break;
                case MorseSignal.DAH:
                	appendStringToMessage(MorseStringConverter.LONG);        
                    break;
                case MorseSignal.SPACE:
                	appendStringToMessage(MorseStringConverter.GAP);        
                    break;
	        }
        }
    }; 

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_message);

        message = (TextView) this.findViewById(R.id.message);
        morse_message = (TextView) this.findViewById(R.id.morse_message);

        this.setTitle(getString(R.string.sndmsg_send_message_activity_title));

        this.setOnClickListenerToAppendStringToMessage(findViewById(R.id.button_short), MorseStringConverter.SHORT);
        this.setOnClickListenerToAppendStringToMessage(findViewById(R.id.button_long), MorseStringConverter.LONG);
        this.setOnClickListenerToAppendStringToMessage(findViewById(R.id.button_space), MorseStringConverter.GAP);

        this.findViewById(R.id.button_send_message).setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View arg0)
            {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

                // Add data to the intent, the receiving app will decide what to
                // do with it.
                intent.putExtra(Intent.EXTRA_SUBJECT, "morse-com");
                intent.putExtra(Intent.EXTRA_TEXT, String.format("Did you know the morse code for \n %s \n is \n %s",
                        message.getText(), morse_message.getText()));
                startActivity(Intent.createChooser(intent, "Complete action using"));

                emptyMessageBoxes();
            }
        });

        findViewById(R.id.textView4).setOnTouchListener(new OnTouchListenerTouchyMode(this.modeListener));
        findViewById(R.id.button_telegraph).setOnTouchListener(new OnTouchListenerTelegraphMode(this.modeListener));
        
        this.findViewById(R.id.button_switch_mode).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ViewFlipper flippy = (ViewFlipper) findViewById(R.id.viewFlipper1);
                flippy.showNext();
            }
        });
    }

    @Override
    protected void onResume()
    {
        super.onResume();
    }

    @Override
    protected Dialog onCreateDialog(int id)
    {

        switch (id)
            {
                default:
                    return super.onCreateDialog(id);
            }
    }

    @Override
    protected void onPrepareDialog(int id, Dialog dialog)
    {

        super.onPrepareDialog(id, dialog);
        switch (id)
            {
                case DIALOG_SEND_CONFIRM:
                case DIALOG_SEND_IN_PROGRESS:
                default:
                    break;
            }
    }

    /**
     * Add character to morse message
     * */
    private void setOnClickListenerToAppendStringToMessage(View view, final String s)
    {
        view.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                appendStringToMessage(s);
            }
        });
    }

    public TextView getMessage()
    {
        return message;
    }

    public TextView getMorse_message()
    {
        return morse_message;
    }

    private void emptyMessageBoxes()
    {
        ((TextView) this.findViewById(R.id.message)).setText("");
        ((TextView) this.findViewById(R.id.morse_message)).setText("");
    }

    private void appendStringToMessage(final String s)
    {
        message.setText(MorseStringConverter.ConvertMorseToText(morse_message.getText() + s));
        morse_message.append(s);
    }
}
