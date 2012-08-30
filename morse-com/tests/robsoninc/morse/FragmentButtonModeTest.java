package robsoninc.morse;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.reset;
import static org.easymock.EasyMock.verify;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import robsoninc.morse.utilities.MorseStringConverter;
import robsoninc.morse.utilities.OnMorseSignalSentListener;
import android.app.Activity;
import android.media.MediaPlayer;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.TextView;

public class FragmentButtonModeTest
{
    private OnMorseSignalSentListener mockListener;    
    private FragmentButtonMode buttonFragment;        
    
    @Before
    public void setUp() throws Exception
    {
        // Setting up intent Mock.        
        mockListener = createMock(OnMorseSignalSentListener.class);
        
        buttonFragment = new FragmentButtonMode();
        buttonFragment.setListener(mockListener);       
    }
    
    @Test
    public void shouldCallDit() throws Exception 
    {
    	mockListener.onSignalSent(MorseStringConverter.DIT);
        replay(mockListener);      

        Button b = (Button) buttonFragment.getActivity().findViewById(R.id.button_short);
        b.performClick();
    }
    
    @Test
    public void shouldCallDah() throws Exception 
    {
    	mockListener.onSignalSent(MorseStringConverter.DAH);
        replay(mockListener);      
     
        Button b = (Button) buttonFragment.getActivity().findViewById(R.id.button_long);
        b.performClick();
    }

    @Test
    public void shouldCallOnSpace() throws Exception 
    {
    	mockListener.onSignalSent(MorseStringConverter.SPACE);
        replay(mockListener);      
     
        Button b = (Button) buttonFragment.getActivity().findViewById(R.id.button_space);
        b.performClick();
    }
    
    @After
    public void tearDown() throws Exception 
    {
        verify(mockListener);
        reset(mockListener);
    }
}
