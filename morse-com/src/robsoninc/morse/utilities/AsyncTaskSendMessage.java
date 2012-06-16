package robsoninc.morse.utilities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import robsoninc.morse.Constants;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;

public class AsyncTaskSendMessage extends AsyncTask<Void, Void, Integer> {

	private String url;
	private String senderId;
	private String recipientId;

	@Override
	protected Integer doInBackground(Void... arg0) {

		// Create a new HttpClient and Post Header
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(url);

		try {
			// Add your data
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
			nameValuePairs.add(new BasicNameValuePair("sender_id", senderId));
			nameValuePairs.add(new BasicNameValuePair("recipient_id",
					recipientId));
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

			// Execute HTTP Post Request
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity resEntity = response.getEntity();

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
		} catch (IOException e) {
			// TODO Auto-generated catch block
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}

	public void setRecipientId(String recipientId) {
		this.recipientId = recipientId;
	}

}
