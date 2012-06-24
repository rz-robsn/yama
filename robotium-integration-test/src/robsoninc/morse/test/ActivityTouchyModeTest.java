package robsoninc.morse.test;

import com.jayway.android.robotium.solo.Solo;

import junit.framework.Assert;
import robsoninc.morse.ActivitySendMessage;
import robsoninc.morse.R;
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
}
