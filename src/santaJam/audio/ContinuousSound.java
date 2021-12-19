package santaJam.audio;

import java.io.IOException;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;

import santaJam.SantaJam;

public class ContinuousSound extends Sound{
	private String path;
	private Clip clip;
	

	public ContinuousSound(String path) { 
		this.path=path;
		try {
			loadAudioStream(path);
			// create clip reference
			clip = AudioSystem.getClip();
			clip.open(audioStream);
			
		}catch (LineUnavailableException ex) {
			System.out.println("Audio line for playing back is unavailable.");
			ex.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		

	}
	public void update() {
		if (playing) {
			volume = SantaJam.getGame().getSettings().getSounds()/100f;
			FloatControl gainControl;
		
			gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			gainControl.setValue(Sound.volumeToDb(volume));

		}
	}

	public void play() {
		playing=true;
		volume = SantaJam.getGame().getSettings().getSounds()/100f;
		System.out.println(path);
		
		FloatControl gainControl;
		gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
		gainControl.setValue(Sound.volumeToDb(volume));
		clip.start();
		clip.loop(Clip.LOOP_CONTINUOUSLY);
		

	}

	public void stop() {
		playing=false;
		clip.stop();
	}
	public void close() {
		clip.stop();
		clip.close();
	}
}
