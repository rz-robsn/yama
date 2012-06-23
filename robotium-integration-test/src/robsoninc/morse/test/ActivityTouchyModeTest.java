package robsoninc.morse.test;

import com.jayway.android.robotium.solo.Solo;

import junit.framework.Assert;
import robsoninc.morse.ActivitySendMessage;
import robsoninc.morse.ActivityTouchyMode;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.Smoke;

public class ActivityTouchyModeTest extends
		ActivityInstrumentationTestCase2<ActivityTouchyMode> {

	private Solo solo;

	public ActivityTouchyModeTest() {
		super("robsoninc.morse.test", ActivityTouchyMode.class);
	}

	@Override
	protected void setUp() throws Exception {
		solo = new Solo(this.getInstrumentation(), this.getActivity());
	}
	
	@Override
	protected void tearDown() throws Exception {
		solo.finishOpenedActivities();
	}


	@Smoke
	public void shouldWriteSOSByTappingAnywhereOnScreen() throws Exception {
		this.typeSOS();

		Assert.assertTrue(
				"The Activity did not display \"sos\".",
				solo.searchText("sos"));
		Assert.assertTrue(
				"The Activity did not display \"sos\" in morse.",
				solo.searchText("\\.\\.\\.\\-\\-\\-\\.\\.\\."));
	}

	@Smoke
	public void shouldDisplayTypedCodeInSendMessageActivityWhenPressingBack()
			throws Exception {
		this.typeSOS();
		solo.goBack();
		solo.assertCurrentActivity(
				"The SendMessageActivity did not get displayed.",
				ActivitySendMessage.class);
	}

	@Smoke
	public void shouldWriteGapSymbolOnDrawingALineWithFinger()
			throws Exception {
		
		// Typing "s" character
		solo.clickOnScreen(25, 25);
				
		this.typeGap();
		this.typeGap();
		
		// Typing "m" character
		solo.clickLongOnScreen(25, 25, 1);

		Assert.assertTrue(
				"The Activity did not display \"e t\".",
				solo.searchText("e t"));
		Assert.assertTrue(
				"The Activity did not display \"e t\" in morse.",
				solo.searchText("\\. \\-"));	
	}
		
	private void typeSOS() {
		
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
	
	private void typeGap()
	{
		// drawing a line.
		solo.drag(5, 5, 25, 25, 1);
	}
}
