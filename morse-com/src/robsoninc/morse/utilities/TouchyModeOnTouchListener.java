package robsoninc.morse.utilities;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewConfiguration;

public abstract class TouchyModeOnTouchListener implements OnTouchListener
{
    // Defines the minimum distance the pointer has to traverse so that
    // the current gesture is seen as a line (and not as a tap).
    private static final float LINE_DRAW_THRESHOLD_DISTANCE = 10;

    private float downX;
    private float downY;
    
    private boolean lineTouchDispatched = false;

    @Override
    public boolean onTouch(View v, MotionEvent event)
    {
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                this.downX = event.getX();
                this.downY = event.getY();
                break;

            case MotionEvent.ACTION_MOVE:                
                if (!lineTouchDispatched && this.motionEventExceedsThreeshold(v, event))
                {   
                    this.onLineTouch();
                    lineTouchDispatched = true;
                } 
                break;
                
            case MotionEvent.ACTION_UP:
                if(lineTouchDispatched)
                {
                    this.lineTouchDispatched = false;
                }
                else if (event.getEventTime() - event.getDownTime() < ViewConfiguration.getLongPressTimeout())
                {
                    this.onShortTouch();
                }
                else
                {
                    this.onLongTouch();
                }                
                break;
                
            case MotionEvent.ACTION_CANCEL:
                this.lineTouchDispatched = false ;
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
    
    private boolean motionEventExceedsThreeshold(View v, MotionEvent event)
    {
        return getDistance(this.downX, this.downY, event.getX(), event.getY()) 
                > convertDpToPxl(LINE_DRAW_THRESHOLD_DISTANCE, v.getContext());
    }

    public static float convertDpToPxl(float lineDrawThresholdDistance, Context c)
    {
        // Density is the scale factor
        return (lineDrawThresholdDistance * c.getResources().getDisplayMetrics().density + 0.5f);
    }
}
