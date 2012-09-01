package robsoninc.morse;

import android.media.AudioManager;
import android.media.SoundPool;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

public class FragmentBeepPlayer extends Fragment
{
	private SoundPool beepSound;
	private int beepSoundId;
	private int beepStreamId = 0;
	
	private int beep25secSoundId;
	
	@Override
    public void onPause()
    {
		super.onPause();
		this.beepSound.release();
		this.beepSound = null;
    }

	@Override
    public void onResume()
    {
	    super.onResume();
		this.beepSound = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
		this.beepSoundId = this.beepSound.load(this.getActivity(),
		        R.raw.beep_5_seconds, 1);		
		this.beep25secSoundId = this.beepSound.load(this.getActivity(),
		        R.raw.beep_025_seconds, 1);
    }
	
	public void playSound()
	{
		if (beepStreamId == 0)
		{
			AudioManager audioManager = (AudioManager) this.getActivity().getSystemService(FragmentActivity.AUDIO_SERVICE);
			float actualVolume = (float) audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
			float maxVolume = (float) audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
			float volume = actualVolume / maxVolume;
			beepStreamId = beepSound.play(beepSoundId, volume, volume, 1, -1, 1.0f);
		}
		else
		{
			beepSound.resume(beepStreamId);
		}
	}
	
	public void pauseSound()
	{
		this.beepSound.pause(beepStreamId);
	}

	public void play025SecondsSound()
	{
		AudioManager audioManager = (AudioManager) this.getActivity().getSystemService(FragmentActivity.AUDIO_SERVICE);
		float actualVolume = (float) audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
		float maxVolume = (float) audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		float volume = actualVolume / maxVolume;
		beepSound.play(beep25secSoundId, volume, volume, 1, 0, 1.0f);
	}
}
