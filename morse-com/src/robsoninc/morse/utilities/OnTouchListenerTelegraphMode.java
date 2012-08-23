/**
 * 
 */
package robsoninc.morse.utilities;

import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.View.OnTouchListener;

public class OnTouchListenerTelegraphMode implements OnTouchListener
{
    private ModeListener listener;
        
    private final long DIT_TO_DAH_THREESHOLD = 100;
    private final long SPACE_THREESHOLD = 400;
    private final long DOUBLE_SPACE_THREESHOLD = 1500;
    
    /**
     * @param listener
     */
    public OnTouchListenerTelegraphMode(ModeListener listener)
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
                break;

            case MotionEvent.ACTION_MOVE:                
                break;
                
            case MotionEvent.ACTION_UP:
            	if (event.getEventTime() - event.getDownTime() < DIT_TO_DAH_THREESHOLD)
            	{
            		this.listener.onDit();
            	}
            	else 
            	{
            		this.listener.onDah();
            	}
                break;
                
            case MotionEvent.ACTION_CANCEL:
            	break;
        }

        return true;
    }

}
