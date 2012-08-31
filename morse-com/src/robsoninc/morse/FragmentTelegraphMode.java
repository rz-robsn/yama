package robsoninc.morse;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import robsoninc.morse.utilities.BeepPlayer;
import robsoninc.morse.utilities.MorseStringConverter;
import robsoninc.morse.utilities.OnMorseSignalSentListener;
import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;

public class FragmentTelegraphMode extends Fragment implements OnTouchListener
{
    private OnMorseSignalSentListener listener;
    private View telegraphBtn;
    
    private final long DIT_TO_DAH_THRESHOLD = 100;
    private final long SPACE_THRESHOLD = 400;
    private final long DOUBLE_SPACE_THRESHOLD = 1500;
    
    private SoundPool beepSound;
    private int beepSoundId;
    private int beepStreamId;

    private long onSpaceCallTime = 0;
    private Timer onSpaceTimer = null;

    private long onSecondSpaceCallTime = 0;
    private Timer onSecondSpaceTimer = null;

    private class TimerTaskCallListenerOnSpace extends TimerTask
    {
        @Override
        public void run()
        {
            FragmentTelegraphMode.this.getActivity().runOnUiThread(new Runnable()
            {
                @Override
                public void run()
                {
                    FragmentTelegraphMode.this.listener.onSignalSent(MorseStringConverter.SPACE);
                }
            });
        }
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
        
        this.beepSound = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        this.beepSoundId = this.beepSound.load(activity, R.raw.bip, 1);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.telegraph_fragment, container);        
        v.setOnTouchListener(this);                
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        
        telegraphBtn = this.getActivity().findViewById(R.id.button_telegraph);
        
        setRetainInstance(true);
    }

    @Override
    public boolean onTouch(View arg0, MotionEvent event)
    {
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                
                // Cancel spaces if the user has pressed action_down within the time interval from the 
                // last action_up.
                if (onSecondSpaceTimer != null && event.getEventTime() < this.onSecondSpaceCallTime)
                {
                    this.onSecondSpaceTimer.cancel();
                }
                if (onSpaceTimer != null && event.getEventTime() < this.onSpaceCallTime)
                {
                    this.onSpaceTimer.cancel();
                }
                
                // Play Sound
                beepStreamId = beepSound.play(beepSoundId, 0.5f, 0.5f, 1, -1, 1.0f);
                
                
                // Switch button color
                telegraphBtn.setBackgroundResource(R.drawable.red_orb);
                
                break;

            case MotionEvent.ACTION_UP:
                if (event.getEventTime() - event.getDownTime() < DIT_TO_DAH_THRESHOLD)
                {
                    this.listener.onSignalSent(MorseStringConverter.DIT);
                }
                else
                {
                    this.listener.onSignalSent(MorseStringConverter.DAH);
                }

                // Scheduling the next onSpace() events to call if there is
                // no Down event before each threshold.
                this.onSpaceCallTime = event.getEventTime() + SPACE_THRESHOLD;
                this.onSecondSpaceCallTime = event.getEventTime() + DOUBLE_SPACE_THRESHOLD;

                onSpaceTimer = new Timer();
                onSecondSpaceTimer = new Timer();
                this.onSpaceTimer.schedule(new TimerTaskCallListenerOnSpace(), SPACE_THRESHOLD);
                this.onSecondSpaceTimer.schedule(new TimerTaskCallListenerOnSpace(), DOUBLE_SPACE_THRESHOLD);
                
                // Stop playing sound
				this.beepSound.stop(beepStreamId);
                
                // Switch Button Color
                telegraphBtn.setBackgroundResource(R.drawable.grey_orb);
                
                break;
        }
        return true;
    }

    public void setListener(OnMorseSignalSentListener listener)
    {
        this.listener = listener;
    }

	public void setBeepSound(SoundPool beepSound)
	{
		this.beepSound = beepSound;
	}
}
