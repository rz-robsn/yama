package robsoninc.morse.utilities;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.reset;
import static org.easymock.EasyMock.verify;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.app.Activity;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.widget.TextView;

import com.xtremelabs.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class OnTouchListenerTouchyModeTest
{
    private MorseSignal modeListener;
    private OnTouchListenerTouchyMode touchListener;
    
    private long downTime;
    
    private TextView textView;
    
    @Before
    public void setUp() throws Exception
    {
        // Setting up intent Mock.
        modeListener = createMock(MorseSignal.class);        
        touchListener = new OnTouchListenerTouchyMode(modeListener);
        downTime = SystemClock.uptimeMillis();
        textView = new TextView(new Activity());
    }
    
    @Test
    public void getMorse_shouldCallDit() throws Exception 
    {
        modeListener.onDit();        
        replay(modeListener);      
                
        // Pressing the same spot for a short time
        touchListener.onTouch(textView, MotionEvent.obtain(downTime, downTime, MotionEvent.ACTION_DOWN, 0, 0, 0));
        touchListener.onTouch(textView, MotionEvent.obtain(downTime, downTime + 90, MotionEvent.ACTION_UP, 0, 0, 0));
    }
    
    @Test
    public void getMorse_shouldCallDah() throws Exception 
    {
        modeListener.onDah();        
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
    public void getMorse_shouldCallOnSpace() throws Exception 
    {
        modeListener.onSpace();        
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
