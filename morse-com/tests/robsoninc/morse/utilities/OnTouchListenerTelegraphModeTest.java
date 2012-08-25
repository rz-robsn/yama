package robsoninc.morse.utilities;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.reset;
import static org.easymock.EasyMock.verify;
import static testingutilites.MorseSignalMessage.morseMessageSignal;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.app.Activity;
import android.os.Handler;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.widget.TextView;

import com.xtremelabs.robolectric.Robolectric;
import com.xtremelabs.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class OnTouchListenerTelegraphModeTest
{
    private Handler modeListener;
    private OnTouchListenerTelegraphMode touchListener;
    
    private long downTime;
    private TextView textView;
    
    @Before
    public void setUp() throws Exception
    {
        // Setting up intent Mock.
        modeListener =  createMock(Handler.class);        
        touchListener = new OnTouchListenerTelegraphMode(modeListener);
        downTime = SystemClock.uptimeMillis();
        textView = new TextView(new Activity());
    }
    
    @Test
    public void shouldCallDit() throws Exception 
    {
    	expect(modeListener.sendMessage(morseMessageSignal(MorseStringConverter.SHORT))).andReturn(true);   
        replay(modeListener);      
                
        // Pressing the same spot for a short time
        touchListener.onTouch(textView, MotionEvent.obtain(downTime, downTime, MotionEvent.ACTION_DOWN, 0, 0, 0));
        touchListener.onTouch(textView, MotionEvent.obtain(downTime, downTime + 90, MotionEvent.ACTION_UP, 0, 0, 0));
    }
    
    @Test
    public void shouldCallDah() throws Exception 
    {
    	expect(modeListener.sendMessage(morseMessageSignal(MorseStringConverter.LONG))).andReturn(true);
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
    public void shouldCallDitAndOneSpace() throws Exception 
    {
    	expect(modeListener.sendMessage(morseMessageSignal(MorseStringConverter.SHORT))).andReturn(true);
        expect(modeListener.sendMessage(morseMessageSignal(MorseStringConverter.GAP))).andReturn(true);
        replay(modeListener);      
     
        // Pressing the same spot for a short time
        touchListener.onTouch(textView, MotionEvent.obtain(downTime, downTime, MotionEvent.ACTION_DOWN, 0, 0, 0));
        touchListener.onTouch(textView, MotionEvent.obtain(downTime, downTime + 90, MotionEvent.ACTION_UP, 0, 0, 0));        
        this.waitFor(500);
    }    
    
    @Test
    public void shouldCallDitAndTwoSpaces() throws Exception 
    {
    	expect(modeListener.sendMessage(morseMessageSignal(MorseStringConverter.SHORT))).andReturn(true);
    	expect(modeListener.sendMessage(morseMessageSignal(MorseStringConverter.GAP))).andReturn(true);
    	expect(modeListener.sendMessage(morseMessageSignal(MorseStringConverter.GAP))).andReturn(true);
        replay(modeListener);      
     
        // Pressing the same spot for a short time
        touchListener.onTouch(textView, MotionEvent.obtain(downTime, downTime, MotionEvent.ACTION_DOWN, 0, 0, 0));
        touchListener.onTouch(textView, MotionEvent.obtain(downTime, downTime + 90, MotionEvent.ACTION_UP, 0, 0, 0));        
        this.waitFor(1600);
    }

    private void waitFor(long millis) throws InterruptedException
    {
    	synchronized (Robolectric.application)
    	{
    		Robolectric.application.wait(millis);
    	}
    }
    
    @After
    public void tearDown() throws Exception 
    {
        verify(modeListener);
        reset(modeListener);
    }
}
