package robsoninc.morse.utilities;

import org.json.JSONArray;

import android.os.AsyncTask;

public class AsyncTaskDownloadUserList extends AsyncTask<Object, Void, JSONArray> {

	@Override
	protected JSONArray doInBackground(Object... params) {

		/*
		 * SharedPreferences prefs = (SharedPreferences)params[0]; String url =
		 * (String) params[1];
		 * 
		 * // Create a new HttpClient and Post Header HttpClient httpclient =
		 * new DefaultHttpClient(); HttpPost httppost = new HttpPost(url);
		 * 
		 * try { // Add your data List<NameValuePair> nameValuePairs = new
		 * ArrayList<NameValuePair>(2); nameValuePairs.add(new
		 * BasicNameValuePair("registration_id",
		 * prefs.getString(Constants.C2DM_ID_KEY, ""))); nameValuePairs.add(new
		 * BasicNameValuePair("user_id", prefs.getString(Constants.USER_ID_KEY,
		 * ""))); httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		 * 
		 * // Execute HTTP Post Request and retrieve response HttpResponse r =
		 * httpclient.execute(httppost); HttpEntity resEntity = r.getEntity();
		 * String users = EntityUtils.toString(resEntity);
		 * 
		 * return new JSONArray(users);
		 * 
		 * } catch (ClientProtocolException e) { // TODO Auto-generated catch
		 * block } catch (IOException e) { // TODO Auto-generated catch block }
		 * catch (JSONException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 */

		JSONArray users = new JSONArray();
		users.put("11111111-1111-1111-1111-111111111111");
		users.put("22222222-2222-2222-2222-222222222222");
		users.put("33333333-3333-3333-3333-333333333333");
		users.put("44444444-4444-4444-4444-444444444444");
		users.put("55555555-5555-5555-5555-555555555555");

		return users;
	}

}
