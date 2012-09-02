package com.yama;

import com.yama.utilities.MorseStringConverter;
import com.yama.utilities.OnMorseSignalSentListener;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.ViewFlipper;

public class ActivitySendMessage extends FragmentActivity implements OnMorseSignalSentListener
{
    private TextView message;
    private TextView morse_message;

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
    public void onWindowFocusChanged(boolean hasFocus)
    {
		super.onWindowFocusChanged(hasFocus);
		
		if (hasFocus)
		{
			// Resizing the height of the ViewFlipper to
	        // reach the top of the footer
	        View footer = this.findViewById(R.id.footer);
	        int[] footerLocations = new int[2];
	        footer.getLocationOnScreen(footerLocations);
	        
	        ViewFlipper flippy = (ViewFlipper) findViewById(R.id.viewFlipper1);
	        int[] flippyLocations = new int[2];
	        flippy.getLocationOnScreen(flippyLocations); 
	        
	        flippy.getLayoutParams().height = footerLocations[1] - flippyLocations[1];	
		}
    }

	@Override
    public void onSignalSent(int morseSignal)
    {
        appendStringToMessage(String.valueOf((char)morseSignal));
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

    private void appendStringToMessage(final String s)
    {
        message.setText(MorseStringConverter.ConvertMorseToText(morse_message.getText() + s));
        morse_message.append(s);
    }
}
