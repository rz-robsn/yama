package robsoninc.morse;

import java.io.IOException;
import java.util.Timer;

import robsoninc.morse.utilities.BeepPlayer;
import robsoninc.morse.utilities.MorseStringConverter;
import robsoninc.morse.utilities.OnMorseSignalSentListener;
import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnTouchListener;
import android.widget.Button;

public class FragmentTouchyMode extends Fragment implements OnTouchListener
{
    private OnMorseSignalSentListener listener;
    private Activity activity;
    
    private MediaPlayer player;
    
    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try
        {
            this.activity = activity;
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
                
                this.player.start();
                break;

            case MotionEvent.ACTION_UP:


                // Scheduling the next onSpace() events to call if there is
                // no Down event before each threshold.
                
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
        }
        return true;
    }

    public void setListener(OnMorseSignalSentListener listener)
    {
        this.listener = listener;
    }

    public void setActivity(Activity activity)
    {
        this.activity = activity;
    }

    public void setPlayer(MediaPlayer player)
    {
        this.player = player;
    }    
}
