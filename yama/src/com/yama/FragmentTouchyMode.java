package com.yama;

import com.yama.utilities.MorseStringConverter;
import com.yama.utilities.OnMorseSignalSentListener;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnTouchListener;

public class FragmentTouchyMode extends Fragment implements OnTouchListener
{
    private OnMorseSignalSentListener listener;
    
    private FragmentBeepPlayer beepFragment;
    
    // Defines the minimum distance the pointer has to traverse so that
    // the current gesture is seen as a line (and not as a tap).
    private static final float LINE_DRAW_THRESHOLD_DISTANCE = 10;
    
    private final long DIT_TO_DAH_THRESHOLD = 100;
    
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
        
		// Adding a new FragmentBeepPlayer to play a sound. 
		FragmentManager fragmentManager = this.getFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		beepFragment = new FragmentBeepPlayer();
		fragmentTransaction.add(beepFragment, "beepFragment");
		fragmentTransaction.commit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
    	View v = (View) inflater.inflate(R.layout.touchy_fragment, container);
        v.setOnTouchListener(this);                
        return v;
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
                
				// Play Sound
				beepFragment.playSound();

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
                else if (event.getEventTime() - event.getDownTime() < DIT_TO_DAH_THRESHOLD) // The gesture is a short press
                {                    
                	this.listener.onSignalSent(MorseStringConverter.DIT);
                }
                else // The gesture is a long press
                {
                    this.listener.onSignalSent(MorseStringConverter.DAH);
                }

                // Play Sound
				beepFragment.pauseSound();
                
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
