package robsoninc.morse;

import java.io.IOException;
import java.util.Timer;

import robsoninc.morse.utilities.BeepPlayer;
import robsoninc.morse.utilities.MorseStringConverter;
import robsoninc.morse.utilities.OnMorseSignalSentListener;
import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.TextView;

public class FragmentTouchyMode extends Fragment implements OnTouchListener
{
    private OnMorseSignalSentListener listener;
    
    private MediaPlayer player;
    
    // Defines the minimum distance the pointer has to traverse so that
    // the current gesture is seen as a line (and not as a tap).
    private static final float LINE_DRAW_THRESHOLD_DISTANCE = 10;
    private float downX;
    private float downY;    
    private boolean lineTouchDispatched = false;
    
    public static float convertDpToPxl(float lineDrawThresholdDistance, Context c)
    {
        // Density is the scale factor
        return (lineDrawThresholdDistance * c.getResources().getDisplayMetrics().density + 0.5f);
    }
    
    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try
        {
            this.listener = (OnMorseSignalSentListener) activity;
        }
        catch (ClassCastException e)
        {
            throw new ClassCastException(activity.toString() + " must implement OnMorseSignalSentListener");
        }
        this.player = new BeepPlayer(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
    	TextView t = (TextView) inflater.inflate(R.layout.touchy_fragment, container);
        t.setOnTouchListener(this);                
        return t;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event)
    {        
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                this.downX = event.getX();
                this.downY = event.getY();
                
                this.player.start();
                break;

            case MotionEvent.ACTION_MOVE:                
                if (!lineTouchDispatched && this.motionEventExceedsThreeshold(v, event)) // The gesture is a line draw
                {
                	this.listener.onSignalSent(MorseStringConverter.SPACE);
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
                	this.listener.onSignalSent(MorseStringConverter.DIT);
                }
                else // The gesture is a long press
                {
                    this.listener.onSignalSent(MorseStringConverter.DAH);
                }
                
                this.player.stop();
                try
                {
                    this.player.prepare();
                }
                catch (IllegalStateException e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                catch (IOException e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                break;
                
            case MotionEvent.ACTION_CANCEL:
                this.lineTouchDispatched = false ;
                break;
        }
        return true;
    }

    public void setListener(OnMorseSignalSentListener listener)
    {
        this.listener = listener;
    }

    public void setPlayer(MediaPlayer player)
    {
        this.player = player;
    }
    
    private static double getDistance(float x1, float y1, float x2, float y2)
    {
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }
    
    private boolean motionEventExceedsThreeshold(View v, MotionEvent event)
    {
        return getDistance(this.downX, this.downY, event.getX(), event.getY()) 
                > convertDpToPxl(LINE_DRAW_THRESHOLD_DISTANCE, v.getContext());
    }
}
