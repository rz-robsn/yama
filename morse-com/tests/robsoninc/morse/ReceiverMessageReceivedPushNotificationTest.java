package robsoninc.morse;

import static org.easymock.EasyMock.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.xtremelabs.robolectric.Robolectric;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import com.xtremelabs.robolectric.shadows.ShadowNotificationManager;

@RunWith(RobolectricTestRunner.class)
public class ReceiverMessageReceivedPushNotificationTest {
	
	private Intent i;
	private String message = "where are you";
	private String senderId = "fake-sender";
	
	private ReceiverMessageReceivedPushNotification receiver;
			
	@Before
	public void setUp() throws Exception
	{
		// Setting up intent Mock.
		Bundle b = new Bundle();
		b.putString("sender_id", senderId);
		b.putString("message", message);
				
		i = createMock(Intent.class);
		expect(i.getAction()).andStubReturn(
		        "com.google.android.c2dm.intent.RECEIVE");
		expect(i.getExtras()).andStubReturn(b);
		replay(i);

		receiver = new ReceiverMessageReceivedPushNotification();
	}
	
	@Test
	public void testAddsNotificationOnReceive() throws Exception
	{
		NotificationManager manager = (NotificationManager) Robolectric.getShadowApplication().getSystemService(Context.NOTIFICATION_SERVICE);
		ShadowNotificationManager shadowManager = Robolectric.shadowOf(manager);
		
		receiver.onReceive(new Activity(), i);
		
		Notification n = shadowManager.getNotification(Constants.NOTIFICATION_NEW_MESSAGE_ID);  
		assertNotNull("The notification has not been created", n);
		assertThat(n.tickerText.toString(), is(equalTo(this.message)));
	}
}
