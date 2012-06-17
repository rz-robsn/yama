package robsoninc.morse.test;

import junit.framework.Assert;

import com.jayway.android.robotium.solo.Solo;

import robsoninc.morse.ActivitySendMessage;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.Smoke;
import android.widget.TextView;
import robsoninc.morse.R;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

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

		Intent i = new Intent(this.getInstrumentation().getContext(),
				ActivitySendMessage.class);
		i.putExtra("robsoninc.morse.recipient_user_id", this.recipient_user_id);
		this.setActivityIntent(i);
		solo = new Solo(this.getInstrumentation(), this.getActivity());
	}

	@Smoke
	public void testTitleNameIncludesRecipientId() throws Exception {
		String title = this.getActivity().getTitle().toString();
		Assert.assertTrue("Title does not include recipient id",
				title.contains(recipient_user_id));
	}

	@Smoke
	public void testSendSuccessfullySOS() throws Exception {

		// Typing "sos"
		this.clickOnButtonTimes(R.string.sndmsg_short_button_value, 3);
		solo.clickOnButton(this.getActivity().getString(
				R.string.sndmsg_space_button_value));
		this.clickOnButtonTimes(R.string.sndmsg_long_button_value, 3);
		solo.clickOnButton(this.getActivity().getString(
				R.string.sndmsg_space_button_value));
		this.clickOnButtonTimes(R.string.sndmsg_short_button_value, 3);

		// Sending the message
		Assert.assertTrue("The Message to send should contain \"solo\".",
				solo.searchText("sos"));
		solo.clickOnButton(this.getActivity().getString(
				R.string.sndmsg_send_button_value));

		Assert.assertTrue(
				"The confirmation alert box text does not contain the confirmation text expected.",
				solo.searchText(this.getActivity().getString(
						R.string.sndmsg_send_dialog_body, "sos")));
		solo.clickOnButton(this.getActivity().getString(
				R.string.dialog_ok_button));		

		solo.waitForDialogToClose(10);

		Assert.assertTrue(
				"The activity did not show/showed the incorrect sending success confirmation dialog.",
				solo.waitForText(this.getActivity().getString(
						R.string.sndmsg_send_success_message,
						this.recipient_user_id)));
		solo.clickOnButton(this.getActivity().getString(
				R.string.dialog_ok_button));
		
		assertThat(((TextView)this.getActivity().findViewById(R.id.message)).getText().length(), is(equalTo(0)));
		assertThat(((TextView)this.getActivity().findViewById(R.id.morse_message)).getText().length(), is(equalTo(0)));		
	}

	private void clickOnButtonTimes(int resId, int times) {
		for (int i = 0; i < times; i++) {
			solo.clickOnButton(this.getActivity().getString(resId));
		}
	}

	@Override
	protected void tearDown() throws Exception {
		solo.finishOpenedActivities();
	}

}
