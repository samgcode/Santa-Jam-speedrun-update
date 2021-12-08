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
		}catch (LineUnavailableException ex) {
			System.out.println("Audio line for playing back is unavailable.");
			ex.printStackTrace();
		}

	}
	public void update() {
		if (fadeOut&&playing) {
			volume-=0.01;			
		}
		if(volume<=-2) {
			playing=false;
			close();
		}
		if (playing || fadeOut) {
			FloatControl gainControl;
		
			gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			gainControl.setValue(Sound.volumeToDb(volume));

		}
	}

	public void play() {
		System.out.println("playing:"+path);
		fadeOut=false;
		playing=true;
		volume = SantaJam.getGame().getSettings().getMusic()/100f;
		if(clip.isOpen()) {
			stop();
		}
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
		if(loops) {
			clip.loop(Clip.LOOP_CONTINUOUSLY);
		}
		clip.start();

	}

	public void stop() {
		fadeOut=true;
	}
	public void close() {
		clip.stop();
		clip.close();
	}
}
