package robsoninc.morse.utilities;

import java.io.IOException;

import robsoninc.morse.R;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;

public class BeepPlayer extends MediaPlayer
{

    public BeepPlayer(Context c)
    {
        super();
        
        this.reset();        
        AssetFileDescriptor afd = c.getResources().openRawResourceFd(R.raw.bip);        
        try
        {
            this.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getDeclaredLength());
            this.prepare();            
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
        
        this.setLooping(true);
    }
    
}
