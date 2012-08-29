package robsoninc.morse.utilities;

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

import android.app.Activity;
import android.media.MediaPlayer;
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
    private FragmentActivity mockFragmentActivity;
    private OnMorseSignalSentListener mockListener;
    private FragmentTelegraphMode telegraphFragment;
    
    private long downTime;
    private View button;
    
    @Before
    public void setUp() throws Exception
    {
        // Setting up intent Mock.    
        mockFragmentActivity = createMock(FragmentActivity.class);
        mockListener = createMock(OnMorseSignalSentListener.class);
        
        telegraphFragment = new FragmentTelegraphMode();
        telegraphFragment.setActivity(mockFragmentActivity);
        telegraphFragment.setListener(mockListener);
        telegraphFragment.setPlayer(new MediaPlayer());

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
    	mockFragmentActivity.runOnUiThread(EasyMock.anyObject(Runnable.class));
    	
        replay(mockListener);
        replay(mockFragmentActivity);
     
        // Pressing the same spot for a short time
        telegraphFragment.onTouch(button, MotionEvent.obtain(downTime, downTime, MotionEvent.ACTION_DOWN, 0, 0, 0));
        telegraphFragment.onTouch(button, MotionEvent.obtain(downTime, downTime + 90, MotionEvent.ACTION_UP, 0, 0, 0));        
        this.waitFor(500);
        
        verify(mockFragmentActivity);
        reset(mockFragmentActivity);
    }    
    
    @Test
    public void shouldCallDitAndTwoSpaces() throws Exception 
    {
    	mockListener.onSignalSent(MorseStringConverter.DIT);
    	mockFragmentActivity.runOnUiThread(EasyMock.anyObject(Runnable.class));    	
    	mockFragmentActivity.runOnUiThread(EasyMock.anyObject(Runnable.class));
    	
        replay(mockListener);  
        replay(mockFragmentActivity);
     
        // Pressing the same spot for a short time        
        telegraphFragment.onTouch(button, MotionEvent.obtain(downTime, downTime, MotionEvent.ACTION_DOWN, 0, 0, 0));
        telegraphFragment.onTouch(button, MotionEvent.obtain(downTime, downTime + 90, MotionEvent.ACTION_UP, 0, 0, 0));        
        this.waitFor(1600);
        
        verify(mockFragmentActivity);
        reset(mockFragmentActivity);
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
