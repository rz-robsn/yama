package robsoninc.morse;

import robsoninc.morse.utilities.MorseStringConverter;
import robsoninc.morse.utilities.OnMorseSignalSentListener;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentButtonMode extends Fragment 
{
	private OnMorseSignalSentListener listener;
	
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
    }
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.button_fragment, container);
    }
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        
        this.getActivity().findViewById(R.id.button_short).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
            	FragmentButtonMode.this.listener.onSignalSent(MorseStringConverter.DIT);
            }
        });
        
        this.getActivity().findViewById(R.id.button_long).setOnClickListener(new View.OnClickListener()
		{			
			@Override
			public void onClick(View v)
			{
				FragmentButtonMode.this.listener.onSignalSent(MorseStringConverter.DAH);
			}
		});
        
        this.getActivity().findViewById(R.id.button_space).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
            	FragmentButtonMode.this.listener.onSignalSent(MorseStringConverter.SPACE);
            }
        });
        
        setRetainInstance(true);
    }

	public void setListener(OnMorseSignalSentListener listener)
	{
		this.listener = listener;
	}
}
