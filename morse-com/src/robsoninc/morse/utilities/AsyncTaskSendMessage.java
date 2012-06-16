package robsoninc.morse.utilities;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import robsoninc.morse.Constants;
import android.os.AsyncTask;

public class AsyncTaskSendMessage extends AsyncTask<Void, Void, HttpResponse> {

	private String senderId;
	private String recipientId;
	
	private Exception exceptionOccured;

	@Override
	protected HttpResponse doInBackground(Void... arg0) {

		// Create a new HttpClient and Post Header
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(Constants.SEND_MESSAGE_URL);

		try {
			// Add your data
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
			nameValuePairs.add(new BasicNameValuePair("sender_id", senderId));
			nameValuePairs.add(new BasicNameValuePair("recipient_id",
					recipientId));
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

			// Execute HTTP Post Request
			return httpclient.execute(httppost);
						
		} catch (Exception e) {
			this.exceptionOccured = e;
			return null;
		}		
	}

	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}

	public void setRecipientId(String recipientId) {
		this.recipientId = recipientId;
	}

	public Exception getExceptionOccured() {
		return exceptionOccured;
	}

}
