package robsoninc.morse.test;

import junit.framework.Assert;

import com.jayway.android.robotium.solo.Solo;

import robsoninc.morse.ActivitySendMessage;
import android.app.Instrumentation;
import android.os.SystemClock;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.Smoke;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import robsoninc.morse.R;

public class ActivitySendMessageTest extends ActivityInstrumentationTestCase2<ActivitySendMessage>
{

    private Solo solo;

    private String controlViewText = "controls";

    public ActivitySendMessageTest()
    {
        super("robsoninc.morse.test", ActivitySendMessage.class);
    }

    @Override
    protected void setUp() throws Exception
    {
        super.setUp();
        solo = new Solo(this.getInstrumentation(), this.getActivity());
    }

    @Smoke
    public void testSendSuccessfullySOS() throws Exception
    {

        // Typing "sos"
        this.clickOnButtonTimes(R.string.sndmsg_short_button_value, 3);
        solo.clickOnButton("Gap");
        this.clickOnButtonTimes(R.string.sndmsg_long_button_value, 3);
        solo.clickOnButton("Gap");
        this.clickOnButtonTimes(R.string.sndmsg_short_button_value, 3);

        this.sendMessage("sos");
    }

    @Smoke
    public void testSwitchToTouchyModeWhenTappingButton() throws Exception
    {
        this.switchToTouchyMode();
    }

    @Smoke
    public void testSendSOSWithTouchyMode() throws Exception
    {

        this.switchToTouchyMode();
        this.typeSOSOnTouchyMode();

        Assert.assertTrue("The Activity did not display \"sos\".", solo.searchText("sos"));
        Assert.assertTrue("The Activity did not display \"sos\" in morse.",
                solo.searchText("\\.\\.\\.\\+\\-\\-\\-\\+\\.\\.\\."));

        this.sendMessage("sos");
    }

    @Smoke
    public void testSwitchToNormalMode() throws Exception
    {

        this.switchToTouchyMode();
        this.typeSOSOnTouchyMode();
        solo.clickOnText("Normal Mode");

        Assert.assertTrue("The Activity did not switch to normal mode", solo.searchButton("(Short)|(Long)|(Gap)", 3));
    }

    @Smoke
    public void testWriteGapSymbolOnDrawingALineWithFinger() throws Exception
    {

        this.switchToTouchyMode();

        // Typing "s" character
        solo.clickOnText(this.controlViewText);

        this.typeGap();
        this.typeGap();

        // Typing "t" character
        solo.clickLongOnText(this.controlViewText);

        Assert.assertTrue("The Activity did not display \"e t\".", solo.searchText("e t"));
        Assert.assertTrue("The Activity did not display \"e t\" in morse.", solo.searchText("\\.\\+\\+\\-"));
    }

    private void typeSOSOnTouchyMode() throws Exception
    {
        // Typing "s" character
        solo.clickOnText(this.controlViewText);
        solo.clickOnText(this.controlViewText);
        solo.clickOnText(this.controlViewText);

        this.typeGap();

        // Typing "o" character
        solo.clickLongOnText(this.controlViewText);
        solo.clickLongOnText(this.controlViewText);
        solo.clickLongOnText(this.controlViewText);

        this.typeGap();

        // Typing "s" character
        solo.clickOnText(this.controlViewText);
        solo.clickOnText(this.controlViewText);
        solo.clickOnText(this.controlViewText);
    }

    private void typeGap() throws Exception
    {
        int[] topLeftControlsCoordinate = new int[2];
        
        // The following is not the black box way to retrieve
        // the controls textView, but the solo#getText method gives 
        // false negative results.
        solo.getView(R.id.textView4).getLocationOnScreen(topLeftControlsCoordinate);
        
        try
        {                        
            /* Simulates the Gesture for Drawing a line. */
            long downTime = SystemClock.uptimeMillis();
            Instrumentation inst = this.getInstrumentation();

            float xStart = topLeftControlsCoordinate[0] + 20 ;
            float xEnd = topLeftControlsCoordinate[0] + 70;
            float yStart = topLeftControlsCoordinate[1] + 20;

            inst.sendPointerSync(MotionEvent.obtain(downTime, SystemClock.uptimeMillis(), MotionEvent.ACTION_DOWN, xStart, yStart, 0));
            
            int step_size = 10;
            for (int i = 1; i < 9; i++)
            {
                // event time MUST be retrieved only by this way!
                long eventTime = SystemClock.uptimeMillis();
                MotionEvent event = MotionEvent.obtain(downTime, eventTime, MotionEvent.ACTION_MOVE, xStart + i*step_size, yStart, 0);
                inst.sendPointerSync(event);
            }
            
            inst.sendPointerSync(MotionEvent.obtain(downTime, downTime, MotionEvent.ACTION_UP, xEnd, yStart, 0));

        }
        catch (Exception ignored)
        {
            // Handle exceptions if necessary
        }
    }

    private void sendMessage(String message) throws Exception
    {
        Assert.assertTrue(String.format("The Message to send should contain \"%s\".", message),
                solo.searchText(message));
    }

    private void switchToTouchyMode() throws Exception
    {
        solo.clickOnButton("Switch Mode");
        Assert.assertFalse("The activity did not switch to touchy mode.",
                solo.searchButton("(Short)|(Long)|(Gap)", true));
        Assert.assertTrue("The activity did not switch to touchy mode.", solo.searchText("controls :", true));
    }

    private void clickOnButtonTimes(int resId, int times) throws Exception
    {
        for (int i = 0; i < times; i++)
        {
            solo.clickOnButton(this.getActivity().getString(resId));
        }
    }

    @Override
    protected void tearDown() throws Exception
    {
        solo.finishOpenedActivities();
    }

}
