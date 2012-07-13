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
    private static final float LINE_DRAW_THRESHOLD_DISTANCE = 1;

    private float downX;
    private float downY;

    @Override
    public boolean onTouch(View v, MotionEvent event)
    {
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                this.downX = event.getX();
                this.downY = event.getY();
                break;

            case MotionEvent.ACTION_UP:
                if (event.getEventTime() - event.getDownTime() < ViewConfiguration.getLongPressTimeout())
                {
                    this.onShortTouch();
                }
                else if (getDistance(this.downX, this.downY, event.getX(), event.getY()) < convertDpToPxl(
                        LINE_DRAW_THRESHOLD_DISTANCE, v.getContext()))
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

    public static float convertDpToPxl(float lineDrawThresholdDistance, Context c)
    {
        // Density is the scale factor
        return (lineDrawThresholdDistance * c.getResources().getDisplayMetrics().density + 0.5f);
    }
}
