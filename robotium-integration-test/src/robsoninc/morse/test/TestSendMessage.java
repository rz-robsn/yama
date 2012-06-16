package robsoninc.morse.test;

import junit.framework.Assert;

import com.jayway.android.robotium.solo.Solo;

import robsoninc.morse.ActivitySendMessage;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.Smoke;
import robsoninc.morse.R;

public class TestSendMessage extends
		ActivityInstrumentationTestCase2<ActivitySendMessage> {

	private Solo solo;

	private String recipient_user_id = "1111-222-333-4444";

	public TestSendMessage() {
		super("robsoninc.morse.test", ActivitySendMessage.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		Intent i = new Intent(this.getInstrumentation().getContext(), ActivitySendMessage.class);
		i.putExtra("robsoninc.morse.recipient_user_id", this.recipient_user_id);
		this.setActivityIntent(i);		
		solo = new Solo(this.getInstrumentation(), this.getActivity());		
	}

	@Smoke
	public void testTitleNameIncludesRecipientId() throws Exception
	{
		String title = this.getActivity().getTitle().toString();
		Assert.assertTrue("Title does not include recipient id", title.contains(recipient_user_id));
	}

	@Smoke
	public void testSendSOS() throws Exception
	{
		solo.clickOnButton(this.getActivity().getString(R.string.sndmsg_short_button_value));
		solo.clickOnButton(this.getActivity().getString(R.string.sndmsg_short_button_value));
		solo.clickOnButton(this.getActivity().getString(R.string.sndmsg_short_button_value));
		
		solo.clickOnButton(this.getActivity().getString(R.string.sndmsg_space_button_value));
		
		solo.clickOnButton(this.getActivity().getString(R.string.sndmsg_long_button_value));
		solo.clickOnButton(this.getActivity().getString(R.string.sndmsg_long_button_value));
		solo.clickOnButton(this.getActivity().getString(R.string.sndmsg_long_button_value));
		
		solo.clickOnButton(this.getActivity().getString(R.string.sndmsg_space_button_value));
		
		solo.clickOnButton(this.getActivity().getString(R.string.sndmsg_short_button_value));
		solo.clickOnButton(this.getActivity().getString(R.string.sndmsg_short_button_value));
		solo.clickOnButton(this.getActivity().getString(R.string.sndmsg_short_button_value));		
		
		Assert.assertTrue(solo.searchText("sos"));
		
		solo.clickOnButton(this.getActivity().getString(R.string.sndmsg_send_button_value));
		Assert.assertTrue(solo.searchText(this.getActivity().getString(R.string.sndmsg_send_dialog_body, "sos")));
		solo.clickOnButton(this.getActivity().getString(R.string.dialog_ok_button));		
		
		//solo.waitForDialogToClose(20000);		
	}	
	
	@Override	
	protected void tearDown() throws Exception {
		solo.finishOpenedActivities();
	}

}
