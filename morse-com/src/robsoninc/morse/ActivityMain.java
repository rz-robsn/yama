package robsoninc.morse;

import robsoninc.morse.utilities.ThreadServerRegistration;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Toast;

public class ActivityMain extends Activity
{

    private class OnResponseReceivedHandler extends Handler
    {

        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);
            Toast.makeText(ActivityMain.this, msg.getData().getString("response"), Toast.LENGTH_SHORT).show();
        }
    }

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(v.getContext(), ActivitySendMessage.class);
                startActivity(i);

            }
        });

        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {

                SharedPreferences settings = getSharedPreferences(Constants.PREF_FILE, MODE_PRIVATE);

                // Send the registration ID to the 3rd party site that
                // is sending
                // the messages in a separate thread
                ThreadServerRegistration regThread = new ThreadServerRegistration(settings.getString(
                        Constants.C2DM_ID_KEY, ""), settings.getString(Constants.USER_ID_KEY, ""));
                regThread.setmHandlerObserver(new OnResponseReceivedHandler());
                regThread.start();
            }
        });
    }

    @Override
    protected void onResume()
    {
        super.onResume();
    }

}