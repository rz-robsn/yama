package robsoninc.morse;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

import android.app.ListActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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

				lv.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> parent,
							View view, int position, long id) {

						
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub

					}
				});

				Log.d("nbr of Children",
						Integer.toString(getListView().getChildCount()));
			}
		}.execute(prefs, Constants.USRLIST_URL);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

}
