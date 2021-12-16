package santaJam.audio;

import java.io.IOException;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;

import santaJam.SantaJam;

public class SplitSong extends Sound {
	private Clip startClip, loopClip;
	private boolean starting = true, fadeOut = false;
	

	public SplitSong(String startPath, String loopPath) {
		try {
			loadAudioStream(startPath);
			startClip = AudioSystem.getClip();
			startClip.open(audioStream);
			loadAudioStream(loopPath);
			loopClip = AudioSystem.getClip();
			loopClip.open(audioStream);
			

		} catch (LineUnavailableException ex) {
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
			startClip.stop();
			loopClip.stop();
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
		loopClip.loop(Clip.LOOP_CONTINUOUSLY);
		FloatControl gainControl;
		gainControl = (FloatControl) loopClip.getControl(FloatControl.Type.MASTER_GAIN);
		gainControl.setValue(Sound.volumeToDb(volume));
		loopClip.start();
		startClip.stop();
		
	}

	public void play() {
		volume = SantaJam.getGame().getSettings().getMusic()/100f;
		playing = true;
		fadeOut=false;
		
		
		FloatControl gainControl;
		gainControl = (FloatControl) startClip.getControl(FloatControl.Type.MASTER_GAIN);
		gainControl.setValue(Sound.volumeToDb(volume));
		startClip.setMicrosecondPosition(0);
		loopClip.setMicrosecondPosition(0);
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
