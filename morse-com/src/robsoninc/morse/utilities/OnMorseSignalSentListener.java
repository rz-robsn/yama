package robsoninc.morse.utilities;

import android.os.Handler;

public interface OnMorseSignalSentListener
{
    public Handler getHandler();
    
    public void onSignalSent(int morseSignal);   
}
