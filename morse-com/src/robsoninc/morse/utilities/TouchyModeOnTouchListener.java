package robsoninc.morse.utilities;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public abstract class TouchyModeOnTouchListener implements OnTouchListener
{
    // Defines the minimum time the pointer has to press the screen continuously 
    // so that the current gesture is recognised as a long tap (and not as a short tap), in ms.
    private static final long LONG_TAP_THRESHOLD_TIME = 250;
    
    // Defines the minimum distance the pointer has to traverse so that 
    // the current gesture is seen as a line (and not as a tap).
    private static final double LINE_DRAW_THRESHOLD_DISTANCE = 1;
    
    private float downX;
    private float downY;
    
    @Override
    public boolean onTouch(View v, MotionEvent event)
    {   
        Log.d("touchy", "event = " + event);
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                this.downX = event.getX();
                this.downY = event.getY();
                break;
                
            case MotionEvent.ACTION_UP:
                if (event.getEventTime() - event.getDownTime() < LONG_TAP_THRESHOLD_TIME)
                {
                    this.onShortTouch();
                }
                else if (getDistance(this.downX, this.downY, event.getX(), event.getY()) < LINE_DRAW_THRESHOLD_DISTANCE)
                {
                    this.onLongTouch();
                }
                else 
                {
                    this.onLineTouch();
                }
                break;
        }

        return true;
    }

    public abstract void onShortTouch();

    public abstract void onLongTouch();

    public abstract void onLineTouch();

    private static double getDistance(float x1, float y1, float x2, float y2)
    {
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }
}
