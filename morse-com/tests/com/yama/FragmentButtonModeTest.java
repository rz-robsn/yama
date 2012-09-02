package com.yama;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.reset;
import static org.easymock.EasyMock.verify;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.yama.utilities.MorseStringConverter;
import com.yama.utilities.OnMorseSignalSentListener;

import android.widget.Button;

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
