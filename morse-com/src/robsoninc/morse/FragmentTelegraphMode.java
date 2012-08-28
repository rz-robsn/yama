package robsoninc.morse;

import java.util.Timer;
import java.util.TimerTask;

import robsoninc.morse.utilities.MorseStringConverter;
import robsoninc.morse.utilities.OnMorseSignalSentListener;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;

public class FragmentTelegraphMode extends Fragment implements OnTouchListener
{
    private Handler listener;

    private final long DIT_TO_DAH_THRESHOLD = 100;
    private final long SPACE_THRESHOLD = 400;
    private final long DOUBLE_SPACE_THRESHOLD = 1500;

    private long onSpaceCallTime = 0;
    private Timer onSpaceTimer = null;

    private long onSecondSpaceCallTime = 0;

    private Timer onSecondSpaceTimer = null;

    private class TimerTaskCallListenerOnSpace extends TimerTask
    {
        @Override
        public void run()
        {
            FragmentTelegraphMode.this.sendMorseSignal(MorseStringConverter.SPACE);
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
            this.listener = ((OnMorseSignalSentListener)activity).getHandler();
        }
        catch (ClassCastException e)
        {
            throw new ClassCastException(activity.toString() + " must implement OnHeadlineSelectedListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        Button b = (Button) inflater.inflate(R.layout.telegraph_fragment, container);
        b.setOnTouchListener(this);
        return b;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public boolean onTouch(View arg0, MotionEvent event)
    {
        switch (event.getAction())
            {
                case MotionEvent.ACTION_DOWN:
                    if (onSecondSpaceTimer != null && event.getEventTime() < this.onSecondSpaceCallTime)
                    {
                        this.onSecondSpaceTimer.cancel();
                    }
                    if (onSpaceTimer != null && event.getEventTime() < this.onSpaceCallTime)
                    {
                        this.onSpaceTimer.cancel();
                    }

                    sendMotionEventCode(MotionEvent.ACTION_DOWN);
                    break;

                case MotionEvent.ACTION_UP:
                    if (event.getEventTime() - event.getDownTime() < DIT_TO_DAH_THRESHOLD)
                    {
                        this.sendMorseSignal(MorseStringConverter.DIT);
                    }
                    else
                    {
                        this.sendMorseSignal(MorseStringConverter.DAH);
                    }

                    // Scheduling the next onSpace() events to call if there is
                    // no
                    // Down event before each threshold.
                    this.onSpaceCallTime = event.getEventTime() + SPACE_THRESHOLD;
                    this.onSecondSpaceCallTime = event.getEventTime() + DOUBLE_SPACE_THRESHOLD;

                    onSpaceTimer = new Timer();
                    onSecondSpaceTimer = new Timer();
                    this.onSpaceTimer.schedule(new TimerTaskCallListenerOnSpace(), SPACE_THRESHOLD);
                    this.onSecondSpaceTimer.schedule(new TimerTaskCallListenerOnSpace(), DOUBLE_SPACE_THRESHOLD);

                    sendMotionEventCode(MotionEvent.ACTION_UP);
                    break;
            }
        return true;
    }

    public void setListener(Handler listener)
    {
        this.listener = listener;
    }

    private void sendMotionEventCode(int motionCode)
    {
        Message m = new Message();
        m.arg2 = motionCode;
        m.setTarget(listener);
        m.sendToTarget();
    }

    private void sendMorseSignal(int signal)
    {
        Message m = new Message();
        m.arg1 = signal;
        m.setTarget(listener);
        m.sendToTarget();
    }
}
