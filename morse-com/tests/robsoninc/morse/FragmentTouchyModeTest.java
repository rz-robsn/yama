package robsoninc.morse;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.reset;
import static org.easymock.EasyMock.verify;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import robsoninc.morse.utilities.MorseStringConverter;
import robsoninc.morse.utilities.OnMorseSignalSentListener;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.widget.TextView;

import com.xtremelabs.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class FragmentTouchyModeTest
{
    private OnMorseSignalSentListener mockListener;
    
    private FragmentTouchyMode touchyFragment;
    
    private long downTime;    
    private TextView textView;
    
    @Before
    public void setUp() throws Exception
    {
        // Setting up intent Mock.        
        mockListener = createMock(OnMorseSignalSentListener.class);
        
        touchyFragment = new FragmentTouchyMode();
        touchyFragment.setListener(mockListener);        
        downTime = SystemClock.uptimeMillis();
        textView = new TextView(new Activity());
    }
    
    @Test
    public void shouldCallDit() throws Exception 
    {
    	mockListener.onSignalSent(MorseStringConverter.DIT);
        replay(mockListener);      
                
        // Pressing the same spot for a short time
        touchyFragment.onTouch(textView, MotionEvent.obtain(downTime, downTime, MotionEvent.ACTION_DOWN, 0, 0, 0));
        touchyFragment.onTouch(textView, MotionEvent.obtain(downTime, downTime + 90, MotionEvent.ACTION_UP, 0, 0, 0));
    }
    
    @Test
    public void shouldCallDah() throws Exception 
    {
    	mockListener.onSignalSent(MorseStringConverter.DAH);
        replay(mockListener);      
                
        // Pressing the same spot for a long time
        touchyFragment.onTouch(textView, MotionEvent.obtain(downTime, downTime, MotionEvent.ACTION_DOWN, 0, 0, 0));
        touchyFragment.onTouch(textView, MotionEvent.obtain(downTime, downTime + 110, MotionEvent.ACTION_MOVE, 0, 0, 0));
        touchyFragment.onTouch(textView, MotionEvent.obtain(downTime, downTime + 120, MotionEvent.ACTION_MOVE, 0, 0, 0));
        touchyFragment.onTouch(textView, MotionEvent.obtain(downTime, downTime + 130, MotionEvent.ACTION_MOVE, 0, 0, 0));
        touchyFragment.onTouch(textView, MotionEvent.obtain(downTime, downTime + 140, MotionEvent.ACTION_MOVE, 0, 0, 0));
        touchyFragment.onTouch(textView, MotionEvent.obtain(downTime, downTime + 150, MotionEvent.ACTION_UP, 0, 0, 0));
    }

    @Test
    public void shouldCallOnSpace() throws Exception 
    {
    	mockListener.onSignalSent(MorseStringConverter.SPACE);
        replay(mockListener);      
     
        // Drawing a line
        touchyFragment.onTouch(textView, MotionEvent.obtain(downTime, downTime, MotionEvent.ACTION_DOWN, 0, 0, 0));
        touchyFragment.onTouch(textView, MotionEvent.obtain(downTime, downTime + 90, MotionEvent.ACTION_MOVE, 10, 10, 0));
        touchyFragment.onTouch(textView, MotionEvent.obtain(downTime, downTime + 100, MotionEvent.ACTION_MOVE, 20, 20, 0));
        touchyFragment.onTouch(textView, MotionEvent.obtain(downTime, downTime + 110, MotionEvent.ACTION_MOVE, 30, 30, 0));
        touchyFragment.onTouch(textView, MotionEvent.obtain(downTime, downTime + 120, MotionEvent.ACTION_MOVE, 40, 40, 0));
        touchyFragment.onTouch(textView, MotionEvent.obtain(downTime, downTime + 150, MotionEvent.ACTION_UP, 40, 40, 0));
    }

    
    @After
    public void tearDown() throws Exception 
    {
        verify(mockListener);
        reset(mockListener);
    }
}
