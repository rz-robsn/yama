package com.yama;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

public class ActivityMain extends Activity
{
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		findViewById(R.id.button1).setOnClickListener(
		        new View.OnClickListener()
		        {

			        @Override
			        public void onClick(View v)
			        {
				        Intent i = new Intent(v.getContext(),
				                ActivitySendMessage.class);
				        startActivity(i);
			        }
		        });

		findViewById(R.id.tuto_btn).setOnClickListener(
		        new View.OnClickListener()
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
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.send_message_menu, menu);
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item)
	{
		switch (item.getItemId())
		{
			case R.id.ref_menu:

				// Copying 
		        InputStream is = getResources().openRawResource(R.raw.international_morse_code);
		        OutputStream os;
		        
                try
                {                	
	                os = this.openFileOutput("international_morse_code.png", MODE_WORLD_READABLE);
			        byte[] data = new byte[is.available()];
			        is.read(data);
			        os.write(data);
			        is.close();
			        os.close();
                }
                catch (FileNotFoundException e)
                {
	                // TODO Auto-generated catch block
                }
                catch (IOException e)
                {
	                // TODO Auto-generated catch block
                }		
                
				// start intent for viewing the translation table.
				Intent intent = new Intent();
				intent.setAction(Intent.ACTION_VIEW);
				intent.setData(Uri.parse("content://com.yama.contentprovidertranslationtable/"));
				startActivity(intent);
				return true;

			default:
				return super.onOptionsItemSelected(item);
		}
	}
}