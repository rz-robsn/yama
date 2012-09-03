package com.yama;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

public class ActivityMain extends Activity
{
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
        
        findViewById(R.id.tuto_btn).setOnClickListener(new View.OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				Intent i = new Intent();
				i.setAction(Intent.ACTION_VIEW);
				i.setData(Uri.parse(Constants.TUTO_VID_ADDRESS));
				
				startActivity(i);
			}
		});
    }

    @Override
    protected void onResume()
    {
        super.onResume();
    }

}