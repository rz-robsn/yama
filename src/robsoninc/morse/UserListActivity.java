package robsoninc.morse;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class UserListActivity extends ListActivity {

	@Override
	protected void onResume() {
		super.onResume();

		SharedPreferences prefs = getSharedPreferences(Constants.PREF_FILE,
				MODE_PRIVATE);

		new DownloadUserListTask() {

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
								SendMessageActivity.class);
						i.putExtra("robsoninc.morse.recipient_user_id",
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
