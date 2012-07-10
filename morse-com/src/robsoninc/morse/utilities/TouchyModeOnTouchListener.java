package robsoninc.morse.utilities;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public abstract class TouchyModeOnTouchListener implements OnTouchListener
{

    @Override
    public boolean onTouch(View v, MotionEvent event)
    {   
        Log.d("touchy", "event action = " + event.getAction());
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                break;
                
            case MotionEvent.ACTION_MOVE:
                if (getDistance(event.getX(), event.getY(), event.getHistoricalX(0), event.getHistoricalY(0)) > 10)
                {
                    this.onLineTouch();
                    event.setAction(MotionEvent.ACTION_CANCEL);
                }
                break;
                
            case MotionEvent.ACTION_UP:
                if (event.getDownTime() < 500)
                {
                    this.onShortTouch();
                }
                // Check that this action is not the end of a MOVE action
                else if (event.getHistorySize() == 0)
                {
                    this.onLongTouch();
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
