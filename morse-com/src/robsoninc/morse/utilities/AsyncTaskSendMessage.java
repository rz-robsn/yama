package robsoninc.morse.utilities;

import android.os.AsyncTask;

public class AsyncTaskSendMessage extends AsyncTask<Void, Void, Integer> {

	private String senderId;
	private String recipientId;
	private String message;

	private Exception exceptionOccured;

	@Override
	protected Integer doInBackground(Void... arg0) {

//		// Create a new HttpClient and Post Header
//		HttpClient httpclient = new DefaultHttpClient();
//		HttpPost httppost = new HttpPost(Constants.SEND_MESSAGE_URL);
//
//		try {
//			// Add your data
//			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
//			nameValuePairs.add(new BasicNameValuePair("sender_id", senderId));
//			nameValuePairs.add(new BasicNameValuePair("recipient_id",
//					recipientId));
//			nameValuePairs.add(new BasicNameValuePair("message", message));
//			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
//
//			// Execute HTTP Post Request
//			return httpclient.execute(httppost);
//
//		} catch (Exception e) {
//			this.exceptionOccured = e;
//			return null;
//		}
		
//		HttpResponse mockResponse = createMock(HttpResponse.class);
//		expect(mockResponse.getStatusLine().getStatusCode()).andStubReturn(200);
//		replay(mockResponse);
//		return mockResponse;

		return Integer.valueOf(200);
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

	public String getMessage() {
		return message;
	}

}
