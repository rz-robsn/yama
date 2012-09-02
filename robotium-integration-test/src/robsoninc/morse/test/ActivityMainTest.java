package robsoninc.morse.test;

import com.jayway.android.robotium.solo.Solo;
import com.yama.ActivityMain;
import com.yama.ActivitySendMessage;

import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.Smoke;

public class ActivityMainTest extends ActivityInstrumentationTestCase2<ActivityMain>
{
    public Solo solo;

    public ActivityMainTest()
    {
        super("com.yama.test", ActivityMain.class);
    }

    @Override
    protected void setUp() throws Exception
    {
        super.setUp();
        solo = new Solo(this.getInstrumentation(), this.getActivity());
    }
    
    @Smoke
    public void testClickOnCreateMorseRedirectsToSendMessageActivity() throws Exception
    {
        solo.clickOnButton("Create Morse Code");
        solo.assertCurrentActivity("The Create Morse Code Button did not redirect to SendMessage Activity",
                ActivitySendMessage.class);
    }
    
    @Smoke
    public void testTestRegistrationButtonDoesNotAppear() throws Exception
    {
        assertFalse("The Test Registration button should not appear anymore", 
                solo.searchButton("Test Registration"));        
    }
    
    @Override
    protected void tearDown() throws Exception
    {
        solo.finishOpenedActivities();
    }

}
