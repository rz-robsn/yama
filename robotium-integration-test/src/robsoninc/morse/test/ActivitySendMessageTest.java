package robsoninc.morse.test;

import junit.framework.Assert;

import com.jayway.android.robotium.solo.Solo;

import robsoninc.morse.ActivitySendMessage;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.Smoke;
import robsoninc.morse.R;

public class ActivitySendMessageTest extends
		ActivityInstrumentationTestCase2<ActivitySendMessage> {

	private Solo solo;

	private String recipient_user_id = "1111-222-333-4444";

	public ActivitySendMessageTest() {
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
		solo.clickOnButton("Gap");
		this.clickOnButtonTimes(R.string.sndmsg_long_button_value, 3);
		solo.clickOnButton("Gap");
		this.clickOnButtonTimes(R.string.sndmsg_short_button_value, 3);

		this.sendMessage("sos");
	}

	@Smoke
	public void testSwitchToTouchyModeWhenTappingButton() throws Exception {
		this.switchToTouchyMode();
	}

	@Smoke
	public void testSendSOSWithTouchyMode() throws Exception {

		this.switchToTouchyMode();
		this.typeSOSOnTouchyMode();

		Assert.assertTrue("The Activity did not display \"sos\".",
				solo.searchText("sos"));
		Assert.assertTrue("The Activity did not display \"sos\" in morse.",
				solo.searchText("\\.\\.\\.\\-\\-\\-\\.\\.\\."));
		
		this.sendMessage("sos");
	}

	@Smoke
	public void testSwitchToNormalMode() throws Exception {

		this.switchToTouchyMode();
		this.typeSOSOnTouchyMode();
		solo.clickOnText("Normal Mode");

		Assert.assertTrue("The Activity did not switch to normal mode",
				solo.searchButton("(Short)|Long)|(Gap)", 3));
	}

	@Smoke
	public void testWriteGapSymbolOnDrawingALineWithFinger() throws Exception {

		this.switchToTouchyMode();

		// Typing "s" character
		solo.clickOnScreen(25, 25);

		this.typeGap();
		this.typeGap();

		// Typing "t" character
		solo.clickLongOnScreen(25, 25, 1);

		Assert.assertTrue("The Activity did not display \"e t\".",
				solo.searchText("e t"));
		Assert.assertTrue("The Activity did not display \"e t\" in morse.",
				solo.searchText("\\. \\-"));
	}

	private void typeSOSOnTouchyMode() throws Exception {

		// Typing "s" character
		solo.clickOnScreen(25, 25);
		solo.clickOnScreen(50, 50);
		solo.clickOnScreen(50, 50);

		this.typeGap();

		// Typing "o" character
		solo.clickLongOnScreen(25, 25, 1);
		solo.clickLongOnScreen(50, 50, 1);
		solo.clickLongOnScreen(50, 50, 1);

		this.typeGap();

		// Typing "s" character
		solo.clickOnScreen(25, 25);
		solo.clickOnScreen(50, 50);
		solo.clickOnScreen(50, 50);
	}

	private void typeGap() throws Exception {
		// drawing a line.
		solo.drag(5, 5, 25, 25, 1);
	}

	private void sendMessage(String message) throws Exception {
		Assert.assertTrue(String.format(
				"The Message to send should contain \"%s\".", message), 
				solo.searchText(message));
		solo.clickOnButton("Send");

		Assert.assertTrue(
				"The confirmation alert box text does not contain the confirmation text expected.",
				solo.searchText(this.getActivity().getString(
						R.string.sndmsg_send_dialog_body, message)));
		solo.clickOnButton("OK");

		solo.waitForDialogToClose(10);

		Assert.assertTrue(
				"The activity did not show or showed the incorrect sending success confirmation dialog.",
				solo.waitForText(this.getActivity().getString(
						R.string.sndmsg_send_success_message,
						this.recipient_user_id)));
		Assert.assertFalse("The message box did not empty itself",
				solo.searchText(message));

	}

	private void switchToTouchyMode() throws Exception {
		solo.clickOnButton("Touchy Mode");
		Assert.assertFalse("The activity did not switch to touchy mode.",
				solo.searchButton("(Short)|(Long)|(Gap)", true));
		Assert.assertTrue("The activity did not switch to touchy mode.",
				solo.searchText("controls :", true));		
	}

	private void clickOnButtonTimes(int resId, int times) throws Exception {
		for (int i = 0; i < times; i++) {
			solo.clickOnButton(this.getActivity().getString(resId));
		}
	}

	@Override
	protected void tearDown() throws Exception {
		solo.finishOpenedActivities();
	}

}
