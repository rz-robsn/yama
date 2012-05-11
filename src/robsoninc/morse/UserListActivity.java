package robsoninc.morse;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

import android.app.ListActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;

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

				ArrayAdapter<String> userAdapt = new ArrayAdapter<String>(
						getListView().getContext(), R.layout.list_item, usrList);
				setListAdapter(userAdapt);
			}
		}.execute(prefs, Constants.USRLIST_URL);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

}
