package santaJam.audio;

import java.io.IOException;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;

public class SplitSong extends Sound {
	private String startPath, loopPath;
	private Clip startClip, loopClip;
	private boolean starting = true, fadeOut = false;
	private double volume = 1;

	public SplitSong(String startPath, String loopPath) {
		this.startPath = startPath;
		this.loopPath = loopPath;
		try {
			loadAudioStream(startPath);
			startClip = AudioSystem.getClip();
			loadAudioStream(loopPath);
			loopClip = AudioSystem.getClip();

		} catch (LineUnavailableException ex) {
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
			if (starting) {
				gainControl = (FloatControl) startClip.getControl(FloatControl.Type.MASTER_GAIN);
			}else {
				gainControl= (FloatControl) loopClip.getControl(FloatControl.Type.MASTER_GAIN);
				
			}
			gainControl.setValue(Sound.volumeToDb(volume));

		}

		if (playing && startClip.getFramePosition() >= startClip.getFrameLength() && starting) {
			System.out.println(
					"switched song at frame " + startClip.getFramePosition() + "/" + startClip.getFrameLength());
			switchClips();
			System.out.println("switched");

		}

	}

	private void switchClips() {
		starting = false;
		loopClip.start();
		startClip.stop();
		startClip.close();
	}

	public void play() {
		volume = 1;
		playing = true;
		fadeOut=false;
		if (startClip.isOpen() || loopClip.isOpen()) {
			close();
		}
		try {
			loadAudioStream(startPath);
			startClip.open(audioStream);
			loadAudioStream(loopPath);
			loopClip.open(audioStream);
		} catch (LineUnavailableException | IOException e) {
			System.out.println("Audio line for playing back is unavailable.");
			e.printStackTrace();
		}
		startClip.start();

	}

	public void stop() {
		fadeOut = true;
		

	}

	public void close() {
		startClip.stop();
		loopClip.stop();
		startClip.close();
		loopClip.close();
	}
}
