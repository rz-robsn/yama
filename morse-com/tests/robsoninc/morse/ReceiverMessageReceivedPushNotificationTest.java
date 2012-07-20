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
import android.content.IntentFilter;
import android.os.Bundle;

import com.xtremelabs.robolectric.Robolectric;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import com.xtremelabs.robolectric.shadows.ShadowApplication;
import com.xtremelabs.robolectric.shadows.ShadowHandler;
import com.xtremelabs.robolectric.shadows.ShadowNotificationManager;

@RunWith(RobolectricTestRunner.class)
public class ReceiverMessageReceivedPushNotificationTest
{

    private Intent i;
    private String intentAction = "com.google.android.c2dm.intent.RECEIVE";
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
        expect(i.getAction()).andStubReturn(this.intentAction);
        expect(i.getExtras()).andStubReturn(b);
        replay(i);

        receiver = new ReceiverMessageReceivedPushNotification();
    }

    @Test
    public void testReceiverCalledWhenIntentIsSent() throws Exception
    {
        // Creating mock receiver
        ReceiverMessageReceivedPushNotification mockReceiver = createMock(ReceiverMessageReceivedPushNotification.class);        
        mockReceiver.onReceive(anyObject(Context.class), anyObject(Intent.class));
        replay(mockReceiver);
        
        // Sending Intent
        ShadowApplication application = Robolectric.getShadowApplication();
        application.registerReceiver(mockReceiver, new IntentFilter(this.intentAction));
        application.sendBroadcast(i);
        ShadowHandler.idleMainLooper();
                
        verify(mockReceiver);
    }

    @Test
    public void testAddsNotificationOnReceive() throws Exception
    {
        NotificationManager manager = (NotificationManager) Robolectric.getShadowApplication().getSystemService(
                Context.NOTIFICATION_SERVICE);
        ShadowNotificationManager shadowManager = Robolectric.shadowOf(manager);

        receiver.onReceive(new Activity(), i);

        Notification n = shadowManager.getNotification(Constants.NOTIFICATION_NEW_MESSAGE_ID);
        assertNotNull("The notification has not been created", n);
        assertThat(n.tickerText.toString(), is(equalTo(this.message)));
    }
}
