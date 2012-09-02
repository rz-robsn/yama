package com.yama;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

import com.yama.utilities.AsyncTaskDownloadUserList;

import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ActivityUserList extends ListActivity {

	@Override
	protected void onResume() {
		super.onResume();

		SharedPreferences prefs = getSharedPreferences(Constants.PREF_FILE,
				MODE_PRIVATE);

		new AsyncTaskDownloadUserList() {

			@Override
			protected void onPostExecute(JSONArray users) {
				// TODO Auto-generated method stub
				super.onPostExecute(users);

				List<String> usrList = new ArrayList<String>(users.length());
				for (int i = 0; i < users.length(); i++) {
					usrList.add(users.optString(i));
				}

				ListView lv = getListView();

				ArrayAdapter<String> userAdapt = new ArrayAdapter<String>(
						lv.getContext(), R.layout.list_item, usrList);
				setListAdapter(userAdapt);

				lv.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> adapter, View view,
							int arg2, long arg3) {
						// TODO Auto-generated method stub
						TextView textView = (TextView) view;

						Intent i = new Intent(view.getContext(),
								ActivitySendMessage.class);
						i.putExtra("com.yama.recipient_user_id",
								textView.getText().toString());
						startActivity(i);
					}
				});

			}
		}.execute(prefs, Constants.USRLIST_URL);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

}
