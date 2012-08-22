/**
 * 
 */
package robsoninc.morse.utilities;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class OnTouchListenerTelegraphMode implements OnTouchListener
{
    private ModeListener listener;
    
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
        return false;
    }

}
