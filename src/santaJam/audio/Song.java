package santaJam.audio;

import java.io.IOException;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;

import santaJam.SantaJam;

public class Song extends Sound{
	private String path;
	private Clip clip;
	private boolean fadeOut=false,loops;

	public Song(String path, boolean loops) { 
		this.path=path;
		this.loops=loops;
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
		if (fadeOut&&playing) {
			volume-=0.01;			
		}
		if(volume<=-2) {
			playing=false;
			clip.stop();
		}
		if (playing || fadeOut) {
			FloatControl gainControl;
		
			gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			gainControl.setValue(Sound.volumeToDb(volume));

		}
	}

	public void play() {
		fadeOut=false;
		playing=true;
		volume = SantaJam.getGame().getSettings().getMusic()/100f;
		
		FloatControl gainControl;
		gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
		gainControl.setValue(Sound.volumeToDb(volume));
		clip.setMicrosecondPosition(0);
		clip.start();
		if(loops) {
			clip.loop(Clip.LOOP_CONTINUOUSLY);
		}

	}

	public void stop() {
		fadeOut=true;
	}
	public void close() {
		clip.stop();
		clip.close();
	}
}
