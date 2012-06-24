package robsoninc.morse.test;

import com.jayway.android.robotium.solo.Solo;

import junit.framework.Assert;
import robsoninc.morse.ActivitySendMessage;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.Smoke;

public class ActivityTouchyModeTest extends
		ActivityInstrumentationTestCase2<ActivitySendMessage> {

	private Solo solo;

	public ActivityTouchyModeTest() {
		this("robsoninc.morse.test", ActivitySendMessage.class);
	}

	public ActivityTouchyModeTest(Class<ActivitySendMessage> activityClass) {
		this("robsoninc.morse.test", activityClass);
	}

	public ActivityTouchyModeTest(String pkg,
			Class<ActivitySendMessage> activityClass) {
		super(pkg, activityClass);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		Intent i = new Intent(
				this.getInstrumentation().getContext(), 
				ActivitySendMessage.class);
		this.setActivityIntent(i);
		solo = new Solo(this.getInstrumentation(), this.getActivity());
	}
	
	@Override
	protected void tearDown() throws Exception {
		solo.finishOpenedActivities();
	}

	@Smoke
	public void testWriteSOSByTappingAnywhereOnScreen() throws Exception {
		
		solo.clickOnText("Touchy Mode");
		
		this.typeSOS();

		Assert.assertTrue(
				"The Activity did not display \"sos\".",
				solo.searchText("sos"));
		Assert.assertTrue(
				"The Activity did not display \"sos\" in morse.",
				solo.searchText("\\.\\.\\.\\-\\-\\-\\.\\.\\."));
	}

	@Smoke
	public void testSwitchToNormalMode()
			throws Exception {
		
		this.typeSOS();
		solo.clickOnText("Normal Mode");
		
		Assert.assertTrue(
				"The Activity did not switch to normal mode",
				solo.searchButton("(Short)|Long)|(Gap)", 3));
	}

	@Smoke
	public void testWriteGapSymbolOnDrawingALineWithFinger()
			throws Exception {
		
		// Typing "s" character
		solo.clickOnScreen(25, 25);
				
		this.typeGap();
		this.typeGap();
		
		// Typing "t" character
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
	
	private void testSendMessage()
	{
		
	}
}
