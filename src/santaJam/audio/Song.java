package santaJam.audio;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Song {
	private String path;
	 Clip clip;
	 AudioInputStream audioStream;
	
	public Song(String path, boolean loops) { 
		this.path=path;
	    try {
	    	loadAudioStream();
	          
	        // create clip reference
	        clip = AudioSystem.getClip();	       
	        if(loops) {
	        	clip.loop(Clip.LOOP_CONTINUOUSLY);
	        }
	       
	        
	    }catch (LineUnavailableException ex) {
	        System.out.println("Audio line for playing back is unavailable.");
	        ex.printStackTrace();
	    }
	}
	private void loadAudioStream() {
		try {
			audioStream = AudioSystem.getAudioInputStream(
			        new File(path).getAbsoluteFile());
		} catch (UnsupportedAudioFileException | IOException e) {
			 System.out.println(path+"is not supported.");
			e.printStackTrace();
		}
		       
	}
	
	public void play() {
		 try {
			loadAudioStream();
			clip.open(audioStream);
		} catch (LineUnavailableException | IOException e) {
			 System.out.println("Audio line for playing back is unavailable.");
		        e.printStackTrace();
		}
         clip.start();
	}
	
	public void stop() {
		clip.stop();
		clip.close();
		
	}
	
	
	

}
