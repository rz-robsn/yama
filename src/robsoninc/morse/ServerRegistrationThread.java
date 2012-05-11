package robsoninc.morse;

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

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

/**
 * Thread for registering the app into the webserver
 * */
public class ServerRegistrationThread extends Thread {

	private String registration_id;
	private String user_id;

	private Handler mHandlerObserver;

	public void setmHandlerObserver(Handler mHandlerObserver) {
		this.mHandlerObserver = mHandlerObserver;
	}

	public ServerRegistrationThread(String registration_id, String user_id) {
		super();
		this.registration_id = registration_id;
		this.user_id = user_id;
	}

	@Override
	public void run() {
		super.run();

		// Create a new HttpClient and Post Header
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(Constants.REGISTRATION_URL);

		try {
			// Add your data
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
			nameValuePairs.add(new BasicNameValuePair("registration_id",
					this.registration_id));
			nameValuePairs.add(new BasicNameValuePair("user_id", this.user_id));
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

			// Execute HTTP Post Request
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity resEntity = response.getEntity();

			Bundle b = new Bundle();
			b.putString("response", EntityUtils.toString(resEntity));
			Message message = Message.obtain(mHandlerObserver);
			message.setData(b);

			message.sendToTarget();

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
		} catch (IOException e) {
			// TODO Auto-generated catch block
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
