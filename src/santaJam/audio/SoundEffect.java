package santaJam.audio;

import java.io.IOException;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;

import santaJam.SantaJam;

public class SoundEffect extends Sound{
	private String path;
	private Clip clip;

	public SoundEffect(String path) { 
		this.path=path;
		try {
			loadAudioStream(path);
			// create clip reference
			clip = AudioSystem.getClip();
		}catch (LineUnavailableException ex) {
			System.out.println("Audio line for playing back is unavailable.");
			ex.printStackTrace();
		}

	}
	public void update() {
		if(clip.getFramePosition()>=clip.getFrameLength()) {
			clip.close();
		}
	}

	public void play() {
		System.out.println("playing:"+path);
		playing=true;
		volume = SantaJam.getGame().getSettings().getSounds()/100f;
		if(clip.isOpen()) {
			stop();
		}
		FloatControl gainControl;
		
		
		try {
			loadAudioStream(path);
			clip.open(audioStream);
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
		gainControl.setValue(Sound.volumeToDb(volume));
		clip.start();

	}

	public void close() {
		clip.stop();
		clip.close();
	}
	@Override
	public void stop() {
		close();
	}
}
