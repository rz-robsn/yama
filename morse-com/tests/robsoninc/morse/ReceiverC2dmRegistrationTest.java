package robsoninc.morse;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.*;
import static org.easymock.EasyMock.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import robsoninc.morse.utilities.ThreadServerRegistration;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import com.xtremelabs.robolectric.Robolectric;
import com.xtremelabs.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class ReceiverC2dmRegistrationTest
{

	private Intent i;
	private String registrationId = "My fake Registration id";

	private ReceiverC2dmRegistration receiver;

	@Before
	public void setUp() throws Exception
	{

		// Setting up intent Mock.
		i = createMock(Intent.class);
		expect(i.getAction()).andReturn(
		        "com.google.android.c2dm.intent.REGISTRATION");
		expect(i.getStringExtra("registration_id")).andReturn(
		        this.registrationId);
		expect(i.getStringExtra("error")).andReturn(null);
		expect(i.getStringExtra("unregistered")).andReturn(null);
		replay(i);

		receiver = new ReceiverC2dmRegistration();
	}

	@Test
	public void testSaveRegistrationIdinPreferencesOnSuccessfullRegistration()
	        throws Exception
	{

		receiver.onReceive(new Activity(), i);

		SharedPreferences settings = Robolectric.getShadowApplication()
		        .getSharedPreferences("UserPref", Context.MODE_PRIVATE);
		assertThat(settings.getString("c2dm_id", ""),
		        equalTo(this.registrationId));
	}

	@Test
	public void testSpawnsThreadRegistrationOnSuccessfullRegistration()
	        throws Exception
	{

		int numOfServerThreads = ThreadServerRegistration.activeCount();
		receiver.onReceive(new Activity(), i);
		assertThat("The receiver did not spawn a server registration thread.",
		        ThreadServerRegistration.activeCount(),
		        is(equalTo(numOfServerThreads + 1)));
	}
}
