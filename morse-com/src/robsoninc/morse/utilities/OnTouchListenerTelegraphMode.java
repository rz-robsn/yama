/**
 * 
 */
package robsoninc.morse.utilities;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import android.os.Handler;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.View.OnTouchListener;

public class OnTouchListenerTelegraphMode implements OnTouchListener
{
    private Handler listener;
        
    private final long DIT_TO_DAH_THRESHOLD = 100;
    private final long SPACE_THRESHOLD = 400;
    private final long DOUBLE_SPACE_THRESHOLD = 1500;
    
    private long onSpaceCallTime = 0;
    private Timer onSpaceTimer = null;
    
    private long onSecondSpaceCallTime = 0;
    private Timer onSecondSpaceTimer = null;
    
    private class TimerTaskCallListenerOnSpace extends TimerTask
    {
    	private Handler listener;
    	
		public TimerTaskCallListenerOnSpace(Handler listener)
        {
	        super();
	        this.listener = listener;
        }

		@Override
        public void run()
        {
//			this.listener.onSpace();	
        }    	
    }
    
    /**
     * @param listener
     */
    public OnTouchListenerTelegraphMode(Handler listener)
    {
        super();
        this.listener = listener;
    }

    /* (non-Javadoc)
     * @see android.view.View.OnTouchListener#onTouch(android.view.View, android.view.MotionEvent)
     */
    @Override
    public boolean onTouch(View v, MotionEvent event)
    {
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
            	if(onSecondSpaceTimer != null && event.getEventTime() > this.onSecondSpaceCallTime)
            	{
            		this.onSecondSpaceTimer.cancel();
            	}
            	if (onSpaceTimer != null && event.getEventTime() > this.onSpaceCallTime)
            	{
            		this.onSpaceTimer.cancel();
            	}
                break;
                
            case MotionEvent.ACTION_UP:
            	if (event.getEventTime() - event.getDownTime() < DIT_TO_DAH_THRESHOLD)
            	{
//            		this.listener.onDit();
            	}
            	else 
            	{
//            		this.listener.onDah();
            	}
            	
            	// Scheduling the next onSpace() events to call if there is no 
            	// Down event before each threshold.
            	this.onSpaceCallTime = event.getEventTime() + SPACE_THRESHOLD;
            	this.onSecondSpaceCallTime = event.getEventTime() + DOUBLE_SPACE_THRESHOLD;
            	
            	onSpaceTimer = new Timer();
            	onSecondSpaceTimer = new Timer();
            	this.onSpaceTimer.schedule(new TimerTaskCallListenerOnSpace(listener), new Date(event.getEventTime() + SPACE_THRESHOLD));
            	this.onSecondSpaceTimer.schedule(new TimerTaskCallListenerOnSpace(listener), new Date(event.getEventTime() + DOUBLE_SPACE_THRESHOLD));
                break;
        }

        return true;
    }

}
