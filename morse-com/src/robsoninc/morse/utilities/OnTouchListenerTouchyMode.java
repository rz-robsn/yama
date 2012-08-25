package robsoninc.morse.utilities;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewConfiguration;

public class OnTouchListenerTouchyMode implements OnTouchListener
{
    // Defines the minimum distance the pointer has to traverse so that
    // the current gesture is seen as a line (and not as a tap).
    private static final float LINE_DRAW_THRESHOLD_DISTANCE = 10;
    private float downX;
    private float downY;    
    private boolean lineTouchDispatched = false;
    
    private Handler listener;

    /**
     * @param modeListener
     */
    public OnTouchListenerTouchyMode(Handler modeListener)
    {
        super();
        this.listener = modeListener;
    }

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
                if (!lineTouchDispatched && this.motionEventExceedsThreeshold(v, event)) // The gesture is a line draw
                {
                	this.sendMorseSignal(MorseStringConverter.GAP);
                    lineTouchDispatched = true;
                } 
                break;
                
            case MotionEvent.ACTION_UP:
                if(lineTouchDispatched)
                {
                    this.lineTouchDispatched = false;
                }
                else if (event.getEventTime() - event.getDownTime() < ViewConfiguration.getLongPressTimeout()) // The gesture is a short press
                {                    
                	this.sendMorseSignal(MorseStringConverter.SHORT);
                }
                else // The gesture is a long press
                {
                    this.sendMorseSignal(MorseStringConverter.LONG);
                }                
                break;
                
            case MotionEvent.ACTION_CANCEL:
                this.lineTouchDispatched = false ;
        }

        return true;
    }

    private static double getDistance(float x1, float y1, float x2, float y2)
    {
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }
    
    private void sendMorseSignal(int signal)
    {
    	Message m = new Message();
    	m.arg1 = signal;
    	m.setTarget(listener);
    	m.sendToTarget();
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
