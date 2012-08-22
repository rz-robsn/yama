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
    private ModeListener modeListener;
    private OnTouchListenerTouchyMode touchListener;
    
    private long downTime;
    
    @Before
    public void setUp() throws Exception
    {
        // Setting up intent Mock.
        modeListener = createMock(ModeListener.class);        
        touchListener = new OnTouchListenerTouchyMode(modeListener);
        downTime = SystemClock.uptimeMillis();
    }
    
    @Test
    public void getMorse_shouldCallDit() throws Exception 
    {
        modeListener.onDit();        
        replay(modeListener);      
                
        touchListener.onTouch(new TextView(new Activity()), MotionEvent.obtain(downTime, downTime, MotionEvent.ACTION_DOWN, 0, 0, 0));
        touchListener.onTouch(new TextView(new Activity()), MotionEvent.obtain(downTime, downTime + 90, MotionEvent.ACTION_UP, 0, 0, 0));
    }
    
    @After
    public void tearDown() throws Exception 
    {
        verify(modeListener);
        reset(modeListener);
    }
}
