package robsoninc.morse;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat; 
import static org.easymock.EasyMock.*;

import org.apache.http.HttpRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.xtremelabs.robolectric.Robolectric;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import com.xtremelabs.robolectric.shadows.ShadowIntent;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(RobolectricTestRunner.class)
public class ReceiverC2dmRegistrationTest {
		
	private Intent i ;	
	private String registrationId = "My fake Registration id";
	
	private ReceiverC2dmRegistration receiver ;
	
    @Before
    public void setUp() throws Exception {
		
		// Setting up intent Mock.
    	i = createMock(Intent.class);
    	expect(i.getAction()).andReturn("com.google.android.c2dm.intent.REGISTRATION");
    	expect(i.getStringExtra("registration_id")).andReturn(this.registrationId);
    	replay(i);
    	
    	receiver = new ReceiverC2dmRegistration();
    }
	
	@Test
	public void testSaveRegistrationIdinPreferencesOnSuccessfullRegistration() throws Exception {
		
		Context c = new ContextWrapper(null);
		
		receiver.onReceive(null, i);
			
		SharedPreferences settings = c.getSharedPreferences("UserPref" , Context.MODE_PRIVATE);
		assertThat(settings.getString("c2dm_id", ""), equalTo(this.registrationId));
		
		// Make sure to run the ServerRegistration thread
		Robolectric.getBackgroundScheduler().advanceToLastPostedRunnable();
		Robolectric.getBackgroundScheduler().runOneTask();
		
		HttpRequest server_request = Robolectric.getLatestSentHttpRequest();
    }
}
