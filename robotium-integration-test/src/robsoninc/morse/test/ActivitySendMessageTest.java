package robsoninc.morse.test;

import junit.framework.Assert;

import com.jayway.android.robotium.solo.Solo;
import com.yama.ActivitySendMessage;
import com.yama.R;

import android.app.Instrumentation;
import android.os.SystemClock;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.Smoke;
import android.view.MotionEvent;

public class ActivitySendMessageTest extends ActivityInstrumentationTestCase2<ActivitySendMessage>
{

    private Solo solo;

    private String controlViewText = "controls";

    public ActivitySendMessageTest()
    {
        super("com.yama.test", ActivitySendMessage.class);
    }

    @Override
    protected void setUp() throws Exception
    {
        super.setUp();
        solo = new Solo(this.getInstrumentation(), this.getActivity());
    }

    @Smoke
    public void testSendSOSWithTelegraphMode() throws Exception
    {
        solo.waitForView(solo.getView(R.id.button_telegraph));
                
        this.typeShortOnTelegraphMode();
        this.typeShortOnTelegraphMode();
        this.typeShortOnTelegraphMode();
        this.waitFor(500);
        this.typeLongOnTelegraphMode();
        this.typeLongOnTelegraphMode();
        this.typeLongOnTelegraphMode();
        this.waitFor(500);
        this.typeShortOnTelegraphMode();
        this.typeShortOnTelegraphMode();
        this.typeShortOnTelegraphMode();
        
        this.sendMessage("sos");
    }

    
    @Smoke
    public void testSendSOSWithButtonMode() throws Exception
    {
        this.switchToButtonMode();
        this.typeSOSOnButtonMode();
        this.sendMessage("sos");
    }

    @Smoke
    public void testSendSOSWithTouchyMode() throws Exception
    {

        this.switchToTouchyMode();
        this.typeSOSOnTouchyMode();

        Assert.assertTrue("The Activity did not display \"sos\".", solo.searchText("sos"));
        Assert.assertTrue("The Activity did not display \"sos\" in morse.",
                solo.searchText("\\.\\.\\. \\-\\-\\- \\.\\.\\."));

        this.sendMessage("sos");
    }

    @Smoke
    public void testWriteGapSymbolOnDrawingALineWithFingerWithTelegraphMode() throws Exception 
    {
        //typing "et e"
        this.typeShortOnTelegraphMode();
        this.waitFor(500);
        this.typeLongOnTelegraphMode();
        this.waitFor(1600);
        this.typeShortOnTelegraphMode();
        
        this.sendMessage("et e");
    }
    
    @Smoke
    public void testWriteGapSymbolOnDrawingALineWithFingerWithTouchyMode() throws Exception
    {
        this.switchToTouchyMode();

        // Typing "s" character
        solo.clickOnText(this.controlViewText);

        this.typeGapOnTouchyMode();
        this.typeGapOnTouchyMode();

        // Typing "t" character
        solo.clickLongOnText(this.controlViewText);

        Assert.assertTrue("The Activity did not display \"e t\".", solo.searchText("e t"));
        Assert.assertTrue("The Activity did not display \"e t\" in morse.", solo.searchText("\\.  \\-"));
    }

    @Smoke
    public void testDeleteCharacters() throws Exception
    {
        this.switchToTouchyMode();
        this.typeSOSOnTouchyMode();

        this.sendMessage("sos");
        
        // Deleting two characters
        solo.clickOnView(solo.getView(R.id.button_clear));
        
       Assert.assertFalse("The Message to send should not contain \"sos\"",
                solo.searchText("sos"));
    }    

    private void typeShortOnTelegraphMode() throws Exception
    {
        int[] topLeftControlsCoordinate = new int[2];
        solo.getView(R.id.button_telegraph).getLocationOnScreen(topLeftControlsCoordinate);
        float x = topLeftControlsCoordinate[0] + 20 ;
        float y = topLeftControlsCoordinate[1] + 20;
        
        /* Simulates the Gesture for Drawing a line. */
        long downTime = SystemClock.uptimeMillis();        
        Instrumentation inst = this.getInstrumentation();
        
        inst.sendPointerSync(MotionEvent.obtain(downTime, downTime, MotionEvent.ACTION_DOWN, x, y, 0));        
        inst.sendPointerSync(MotionEvent.obtain(downTime, downTime + 90, MotionEvent.ACTION_UP, x, y, 0));
        this.waitFor(50);
    }

    private void typeLongOnTelegraphMode() throws Exception
    {
        int[] topLeftControlsCoordinate = new int[2];
        solo.getView(R.id.button_telegraph).getLocationOnScreen(topLeftControlsCoordinate);
        float x = topLeftControlsCoordinate[0] + 20 ;
        float y = topLeftControlsCoordinate[1] + 20;
        
        /* Simulates the Gesture for Drawing a line. */
        long downTime = SystemClock.uptimeMillis();        
        Instrumentation inst = this.getInstrumentation();
        
        inst.sendPointerSync(MotionEvent.obtain(downTime, downTime, MotionEvent.ACTION_DOWN, x, y, 0));
        inst.sendPointerSync(MotionEvent.obtain(downTime, downTime + 90, MotionEvent.ACTION_MOVE, x, y, 0));
        inst.sendPointerSync(MotionEvent.obtain(downTime, downTime + 100, MotionEvent.ACTION_MOVE, x, y, 0));
        inst.sendPointerSync(MotionEvent.obtain(downTime, downTime + 110, MotionEvent.ACTION_MOVE, x, y, 0));
        inst.sendPointerSync(MotionEvent.obtain(downTime, downTime + 120, MotionEvent.ACTION_MOVE, x, y, 0));        
        inst.sendPointerSync(MotionEvent.obtain(downTime, downTime + 150, MotionEvent.ACTION_UP, x, y, 0));
        this.waitFor(50);
    }
    
    private void typeSOSOnButtonMode() throws Exception
    {
        // Typing "sos"
        this.clickOnButtonTimes("\\.", 3);
        solo.clickOnButton("^\\s*$");
        this.clickOnButtonTimes("\\-", 3);
        solo.clickOnButton("^\\s*$");
        this.clickOnButtonTimes("\\.", 3);
    }
    
    private void typeSOSOnTouchyMode() throws Exception
    {
        // Typing "s" character
        solo.clickOnText(this.controlViewText);
        solo.clickOnText(this.controlViewText);
        solo.clickOnText(this.controlViewText);

        this.typeGapOnTouchyMode();

        // Typing "o" character
        solo.clickLongOnText(this.controlViewText);
        solo.clickLongOnText(this.controlViewText);
        solo.clickLongOnText(this.controlViewText);

        this.typeGapOnTouchyMode();

        // Typing "s" character
        solo.clickOnText(this.controlViewText);
        solo.clickOnText(this.controlViewText);
        solo.clickOnText(this.controlViewText);
    }
    
    private void switchToButtonMode() throws Exception
    {
        solo.clickOnText("Switch Mode");
        Assert.assertTrue("The activity did not switch to Button mode.",
                solo.searchButton("(\\.)|(\\-)|(^\\s*$)", true));
    }

    private void switchToTouchyMode() throws Exception
    {
        solo.clickOnButton("Switch Mode");
        solo.clickOnButton("Switch Mode");
        Assert.assertTrue("The activity did not switch to touchy mode.", solo.searchText("controls :", true));
    }    
    
    private void typeGapOnTouchyMode() throws Exception
    {
        int[] topLeftControlsCoordinate = new int[2];
        
        // The following is not the black box way to retrieve
        // the controls textView, but the solo#getText method gives 
        // false negative results.
        solo.getView(R.id.touchy_fragment).getLocationOnScreen(topLeftControlsCoordinate);
        
        // event time MUST be retrieved only by this way!
        long downTime = SystemClock.uptimeMillis();
        Instrumentation inst = this.getInstrumentation();

        float xStart = topLeftControlsCoordinate[0] + 20 ;
        float xEnd = topLeftControlsCoordinate[0] + 70;
        float yStart = topLeftControlsCoordinate[1] + 20;

        /* Simulates the Gesture for Drawing a line. */
        inst.sendPointerSync(MotionEvent.obtain(downTime, downTime, MotionEvent.ACTION_DOWN, xStart, yStart, 0));
        int step_size = 10;
        for (int i = 1; i < 9; i++)
        {
            MotionEvent event = MotionEvent.obtain(downTime, downTime + i*100, MotionEvent.ACTION_MOVE, xStart + i*step_size, yStart, 0);
            inst.sendPointerSync(event);
        }
        inst.sendPointerSync(MotionEvent.obtain(downTime, downTime + 1100, MotionEvent.ACTION_UP, xEnd, yStart, 0));
    }

    private void sendMessage(String message) throws Exception
    {
        Assert.assertTrue(String.format("The Message to send should contain \"%s\".", message),
                solo.searchText(message));
    }

    private void clickOnButtonTimes(String buttonName, int times) throws Exception
    {
        for (int i = 0; i < times; i++)
        {
            solo.clickOnButton(buttonName);
        }
    }
    
    private void waitFor(long millis) throws Exception
    {
        synchronized (solo)
        {
            solo.wait(millis);
        }
    }

    @Override
    protected void tearDown() throws Exception
    {
        solo.finishOpenedActivities();
    }

}
