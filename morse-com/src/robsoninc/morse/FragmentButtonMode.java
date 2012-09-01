package robsoninc.morse;

import robsoninc.morse.utilities.MorseStringConverter;
import robsoninc.morse.utilities.OnMorseSignalSentListener;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class FragmentButtonMode extends Fragment 
{
	private OnMorseSignalSentListener listener;
    private FragmentBeepPlayer beepFragment;
	
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
        return inflater.inflate(R.layout.button_fragment, container);
    }
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        
        Button bDit = (Button) this.getActivity().findViewById(R.id.button_short);
        bDit.setSoundEffectsEnabled(false);
        bDit.findViewById(R.id.button_short).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
            	FragmentButtonMode.this.listener.onSignalSent(MorseStringConverter.DIT);
            	FragmentButtonMode.this.beepFragment.play025SecondsSound();
            }
        });
        
        Button bDah = (Button) this.getActivity().findViewById(R.id.button_long);
        bDah.setSoundEffectsEnabled(false);
        bDah.findViewById(R.id.button_long).setOnClickListener(new View.OnClickListener()
		{			
			@Override
			public void onClick(View v)
			{
				FragmentButtonMode.this.listener.onSignalSent(MorseStringConverter.DAH);
				FragmentButtonMode.this.beepFragment.play025SecondsSound();
			}
		});
        
        Button bSpace = (Button) this.getActivity().findViewById(R.id.button_space);
        bSpace.setSoundEffectsEnabled(false);
        bSpace.findViewById(R.id.button_space).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
            	FragmentButtonMode.this.listener.onSignalSent(MorseStringConverter.SPACE);
            	FragmentButtonMode.this.beepFragment.play025SecondsSound();
            }
        });
        
        setRetainInstance(true);
    }

	public void setListener(OnMorseSignalSentListener listener)
	{
		this.listener = listener;
	}
}
