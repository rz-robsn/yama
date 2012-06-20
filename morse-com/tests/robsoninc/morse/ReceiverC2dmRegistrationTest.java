package robsoninc.morse;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.*; 
import static org.easymock.EasyMock.*;
import static testingutilites.matchers.IsServerRegistrationHttpRequest.serverRegistrationHttpRequest;

import org.apache.http.HttpRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import com.xtremelabs.robolectric.Robolectric;
import com.xtremelabs.robolectric.RobolectricTestRunner;

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
    	expect(i.getStringExtra("error")).andReturn(null);
    	expect(i.getStringExtra("unregistered")).andReturn(null);
    	replay(i);
    	
    	receiver = new ReceiverC2dmRegistration();
    	Robolectric.setDefaultHttpResponse(200, "OK");
    }
	
	@Test
	public void testSaveRegistrationIdinPreferencesOnSuccessfullRegistration() throws Exception {
				
		Context c = new Activity();		
		receiver.onReceive(c, i);			
		verify(i);
		
		SharedPreferences settings = c.getSharedPreferences("UserPref" , Context.MODE_PRIVATE);
		assertThat(settings.getString("c2dm_id", ""), equalTo(this.registrationId));
		
		// Make sure to run the ServerRegistration thread
		Robolectric.idleMainLooper(10000);
		
		HttpRequest server_request = Robolectric.getSentHttpRequest(0);
		assertThat(server_request, is(notNull()));
		assertThat(server_request, is(serverRegistrationHttpRequest(registrationId)));
    }
}
