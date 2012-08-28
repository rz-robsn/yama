package robsoninc.morse.utilities;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.reset;
import static org.easymock.EasyMock.verify;
import static org.easymock.EasyMock.expect;
import static testingutilites.MorseSignalMessage.morseMessageSignal;
import static testingutilites.MotionEventMessage.aMotionEventMessage;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.app.Activity;
import android.os.Handler;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.widget.TextView;

import com.xtremelabs.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class OnTouchListenerTouchyModeTest
{
    private Handler modeListener;
    private OnTouchListenerTouchyMode touchListener;
    
    private long downTime;
    
    private TextView textView;
    
    @Before
    public void setUp() throws Exception
    {
        // Setting up intent Mock.
        modeListener =  createMock(Handler.class);        
        touchListener = new OnTouchListenerTouchyMode(modeListener);
        downTime = SystemClock.uptimeMillis();
        textView = new TextView(new Activity());
    }
    
    @Test
    public void shouldCallDit() throws Exception 
    {
    	expect(modeListener.sendMessage(aMotionEventMessage(MotionEvent.ACTION_DOWN))).andReturn(true);
    	expect(modeListener.sendMessage(morseMessageSignal(MorseStringConverter.DIT))).andReturn(true);
    	expect(modeListener.sendMessage(aMotionEventMessage(MotionEvent.ACTION_UP))).andReturn(true);
        replay(modeListener);      
                
        // Pressing the same spot for a short time
        touchListener.onTouch(textView, MotionEvent.obtain(downTime, downTime, MotionEvent.ACTION_DOWN, 0, 0, 0));
        touchListener.onTouch(textView, MotionEvent.obtain(downTime, downTime + 90, MotionEvent.ACTION_UP, 0, 0, 0));
    }
    
    @Test
    public void shouldCallDah() throws Exception 
    {
    	expect(modeListener.sendMessage(aMotionEventMessage(MotionEvent.ACTION_DOWN))).andReturn(true);
    	expect(modeListener.sendMessage(morseMessageSignal(MorseStringConverter.DAH))).andReturn(true);
    	expect(modeListener.sendMessage(aMotionEventMessage(MotionEvent.ACTION_UP))).andReturn(true);
        replay(modeListener);      
                
        // Pressing the same spot for a long time
        touchListener.onTouch(textView, MotionEvent.obtain(downTime, downTime, MotionEvent.ACTION_DOWN, 0, 0, 0));
        touchListener.onTouch(textView, MotionEvent.obtain(downTime, downTime + 110, MotionEvent.ACTION_MOVE, 0, 0, 0));
        touchListener.onTouch(textView, MotionEvent.obtain(downTime, downTime + 120, MotionEvent.ACTION_MOVE, 0, 0, 0));
        touchListener.onTouch(textView, MotionEvent.obtain(downTime, downTime + 130, MotionEvent.ACTION_MOVE, 0, 0, 0));
        touchListener.onTouch(textView, MotionEvent.obtain(downTime, downTime + 140, MotionEvent.ACTION_MOVE, 0, 0, 0));
        touchListener.onTouch(textView, MotionEvent.obtain(downTime, downTime + 150, MotionEvent.ACTION_UP, 0, 0, 0));
    }

    @Test
    public void shouldCallOnSpace() throws Exception 
    {
    	expect(modeListener.sendMessage(aMotionEventMessage(MotionEvent.ACTION_DOWN))).andReturn(true);
        expect(modeListener.sendMessage(morseMessageSignal(MorseStringConverter.SPACE))).andReturn(true);
    	expect(modeListener.sendMessage(aMotionEventMessage(MotionEvent.ACTION_UP))).andReturn(true);
        replay(modeListener);      
     
        // Drawing a line
        touchListener.onTouch(textView, MotionEvent.obtain(downTime, downTime, MotionEvent.ACTION_DOWN, 0, 0, 0));
        touchListener.onTouch(textView, MotionEvent.obtain(downTime, downTime + 90, MotionEvent.ACTION_MOVE, 10, 10, 0));
        touchListener.onTouch(textView, MotionEvent.obtain(downTime, downTime + 100, MotionEvent.ACTION_MOVE, 20, 20, 0));
        touchListener.onTouch(textView, MotionEvent.obtain(downTime, downTime + 110, MotionEvent.ACTION_MOVE, 30, 30, 0));
        touchListener.onTouch(textView, MotionEvent.obtain(downTime, downTime + 120, MotionEvent.ACTION_MOVE, 40, 40, 0));
        touchListener.onTouch(textView, MotionEvent.obtain(downTime, downTime + 150, MotionEvent.ACTION_UP, 40, 40, 0));
    }

    
    @After
    public void tearDown() throws Exception 
    {
        verify(modeListener);
        reset(modeListener);
    }
}
