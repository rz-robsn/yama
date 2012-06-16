package robsoninc.morse.test;

import junit.framework.Assert;

import com.jayway.android.robotium.solo.Solo;

import robsoninc.morse.ActivitySendMessage;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.Smoke;

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
		solo.clickOnButton(this.getActivity().getString(robsoninc.morse.R.string.short_value));
		solo.clickOnButton(this.getActivity().getString(robsoninc.morse.R.string.short_value));
		solo.clickOnButton(this.getActivity().getString(robsoninc.morse.R.string.short_value));
		
		solo.clickOnButton(this.getActivity().getString(robsoninc.morse.R.string.space_value));
		
		solo.clickOnButton(this.getActivity().getString(robsoninc.morse.R.string.long_value));
		solo.clickOnButton(this.getActivity().getString(robsoninc.morse.R.string.long_value));
		solo.clickOnButton(this.getActivity().getString(robsoninc.morse.R.string.long_value));
		
		solo.clickOnButton(this.getActivity().getString(robsoninc.morse.R.string.space_value));
		
		solo.clickOnButton(this.getActivity().getString(robsoninc.morse.R.string.short_value));
		solo.clickOnButton(this.getActivity().getString(robsoninc.morse.R.string.short_value));
		solo.clickOnButton(this.getActivity().getString(robsoninc.morse.R.string.short_value));
		
		solo.clickOnButton(this.getActivity().getString(robsoninc.morse.R.string.send));		
		solo.clickOnButton("^Yes$");
	}	
	
	@Override	
	protected void tearDown() throws Exception {
		solo.finishOpenedActivities();
	}

}