package robsoninc.morse;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.reset;
import static org.easymock.EasyMock.verify;
import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import robsoninc.morse.FragmentTelegraphMode;
import robsoninc.morse.utilities.MorseStringConverter;
import robsoninc.morse.utilities.OnMorseSignalSentListener;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.SystemClock;
import android.support.v4.app.FragmentActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import com.xtremelabs.robolectric.Robolectric;
import com.xtremelabs.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class FragmentTelegraphModeTest
{
    private OnMorseSignalSentListener mockListener;
    private FragmentTelegraphMode telegraphFragment;
    
    private long downTime;
    private View button;
    
    @Before
    public void setUp() throws Exception
    {
        // Setting up intent Mock.    
        mockListener = createMock(OnMorseSignalSentListener.class);
        
        telegraphFragment = new FragmentTelegraphMode();
        telegraphFragment.setListener(mockListener);
        telegraphFragment.setBeepSound(new SoundPool(0, 0, 0));

        downTime = SystemClock.uptimeMillis();
        button = new Button(new Activity());
    }
    
    @Test
    public void shouldCallDit() throws Exception 
    {
    	mockListener.onSignalSent(MorseStringConverter.DIT);
        replay(mockListener);      
                
        // Pressing the same spot for a short time
        telegraphFragment.onTouch(button, MotionEvent.obtain(downTime, downTime, MotionEvent.ACTION_DOWN, 0, 0, 0));
        telegraphFragment.onTouch(button, MotionEvent.obtain(downTime, downTime + 90, MotionEvent.ACTION_UP, 0, 0, 0));
    }
    
    @Test
    public void shouldCallDah() throws Exception 
    {
    	mockListener.onSignalSent(MorseStringConverter.DAH);
        replay(mockListener);                
                
        // Pressing the same spot for a long time
        telegraphFragment.onTouch(button, MotionEvent.obtain(downTime, downTime, MotionEvent.ACTION_DOWN, 0, 0, 0));
        telegraphFragment.onTouch(button, MotionEvent.obtain(downTime, downTime + 110, MotionEvent.ACTION_MOVE, 0, 0, 0));
        telegraphFragment.onTouch(button, MotionEvent.obtain(downTime, downTime + 120, MotionEvent.ACTION_MOVE, 0, 0, 0));
        telegraphFragment.onTouch(button, MotionEvent.obtain(downTime, downTime + 130, MotionEvent.ACTION_MOVE, 0, 0, 0));
        telegraphFragment.onTouch(button, MotionEvent.obtain(downTime, downTime + 140, MotionEvent.ACTION_MOVE, 0, 0, 0));
        telegraphFragment.onTouch(button, MotionEvent.obtain(downTime, downTime + 150, MotionEvent.ACTION_UP, 0, 0, 0));
    }

    @Test
    public void shouldCallDitAndOneSpace() throws Exception 
    {
    	mockListener.onSignalSent(MorseStringConverter.DIT);
    	
    	// Assert that an "onSpace" Runnable has been called.     	
        replay(mockListener);
     
        // Pressing the same spot for a short time
        telegraphFragment.onTouch(button, MotionEvent.obtain(downTime, downTime, MotionEvent.ACTION_DOWN, 0, 0, 0));
        telegraphFragment.onTouch(button, MotionEvent.obtain(downTime, downTime + 90, MotionEvent.ACTION_UP, 0, 0, 0));        
        this.waitFor(500);
    }    
    
    @Test
    public void shouldCallDitAndTwoSpaces() throws Exception 
    {
    	mockListener.onSignalSent(MorseStringConverter.DIT);
    	
        replay(mockListener);  
     
        // Pressing the same spot for a short time        
        telegraphFragment.onTouch(button, MotionEvent.obtain(downTime, downTime, MotionEvent.ACTION_DOWN, 0, 0, 0));
        telegraphFragment.onTouch(button, MotionEvent.obtain(downTime, downTime + 90, MotionEvent.ACTION_UP, 0, 0, 0));        
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
        verify(mockListener);
        reset(mockListener);
    }
}
