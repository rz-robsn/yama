package robsoninc.morse;

import static org.easymock.EasyMock.createMockBuilder;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.xtremelabs.robolectric.Robolectric;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import com.xtremelabs.robolectric.shadows.ShadowNotificationManager;

@RunWith(RobolectricTestRunner.class)
public class ReceiverMessageReceivedPushNotificationTest {
	
	private Intent i;
	private String message = "where are you";
	private ReceiverMessageReceivedPushNotification receiver;
			
	@Before
	public void setUp() throws Exception
	{
		// Setting up intent Mock.
		i = createMockBuilder(Intent.class).createMock();
		expect(i.getAction()).andReturn(
		        "com.google.android.c2dm.intent.RECEIVE");
		replay(i);

		receiver = new ReceiverMessageReceivedPushNotification();
	}
	
	@Test
	public void testAddsNotificationOnReceive() throws Exception
	{
		receiver.onReceive(new Activity(), i);
		ShadowNotificationManager manager = (ShadowNotificationManager)Robolectric.getShadowApplication().getSystemService(Context.LOCATION_SERVICE);
		TextView text = (TextView) manager.getNotification(0).contentView.apply(new Activity(), null);
		assertThat(text.getText().toString(), is(equalTo(this.message)));
	}
}
