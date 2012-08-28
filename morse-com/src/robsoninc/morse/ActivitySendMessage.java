package robsoninc.morse;

import robsoninc.morse.utilities.MorseStringConverter;
import robsoninc.morse.utilities.OnMorseSignalSentListener;
import robsoninc.morse.utilities.OnTouchListenerTelegraphMode;
import robsoninc.morse.utilities.OnTouchListenerTouchyMode;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.TextView;
import android.widget.ToggleButton;
import android.widget.ViewFlipper;

public class ActivitySendMessage extends FragmentActivity implements OnMorseSignalSentListener
{
    private TextView message;
    private TextView morse_message;

    private MediaPlayer beepPlayer;
    
    private Handler modeListener = new Handler()
    {    	
        @Override
        public void handleMessage(Message msg)
        {
	        super.handleMessage(msg);
	        
	        if (msg.arg1 != 0)
	        {	        
	        	appendStringToMessage(String.valueOf((char)msg.arg1));
	        }
	        else 
	        {
	        	switch(msg.arg2)
	        	{
	                case MotionEvent.ACTION_DOWN:
	                	// Start Playing beep sound
	                	beepPlayer = MediaPlayer.create(ActivitySendMessage.this, R.raw.bip);
	                	beepPlayer.setLooping(true);
	                	beepPlayer.start();
	                    break;

	                case MotionEvent.ACTION_UP:
	                	beepPlayer.stop();
	                    break;
	        	}
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

        this.setOnClickListenerToAppendStringToMessage(findViewById(R.id.button_short), MorseStringConverter.DIT);
        this.setOnClickListenerToAppendStringToMessage(findViewById(R.id.button_long), MorseStringConverter.DAH);
        this.setOnClickListenerToAppendStringToMessage(findViewById(R.id.button_space), MorseStringConverter.SPACE);

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
        
        this.findViewById(R.id.button_clear).setOnClickListener(new View.OnClickListener()
		{			
			@Override
			public void onClick(View v)
			{
				morse_message.setText("");
				message.setText("");
			}
		});

        findViewById(R.id.textView4).setOnTouchListener(new OnTouchListenerTouchyMode(this.modeListener));
        //findViewById(R.id.button_telegraph).setOnTouchListener(new OnTouchListenerTelegraphMode(this.modeListener));
        
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
    public Handler getHandler()
    {
        return this.modeListener;
    }

    public TextView getMessage()
    {
        return message;
    }

    public TextView getMorse_message()
    {
        return morse_message;
    }
    
    /**
     * Add character to morse message
     * */
    private void setOnClickListenerToAppendStringToMessage(View view, final char s)
    {
        view.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                appendStringToMessage(String.valueOf(s));
            }
        });
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
