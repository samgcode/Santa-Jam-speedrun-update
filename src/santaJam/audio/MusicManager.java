package santaJam.audio;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

import santaJam.SantaJam;

public class MusicManager extends Thread{
	boolean run=true;
	private int currentSong=-1;
	private int nextSong=-1;
	
	public static final int MENU=0,FOREST=1, ICECAVE=2,PEAK=3,HOME=4;
	public static final SoundEffect[] smash = new SoundEffect[] {
			new SoundEffect("res/sound/ice smash1.wav"), new SoundEffect("res/sound/ice smash2.wav")
	};
	public static final SoundEffect[] walking = new SoundEffect[] {
			new SoundEffect("res/sound/walking1.wav"),new SoundEffect("res/sound/walking2.wav"),new SoundEffect("res/sound/walking3.wav"),
			new SoundEffect("res/sound/walking4.wav"),new SoundEffect("res/sound/walking5.wav"),new SoundEffect("res/sound/walking6.wav")
	};
	public static final SoundEffect[] spring = new SoundEffect[] {
			new SoundEffect("res/sound/spring1.wav"),new SoundEffect("res/sound/spring2.wav"),new SoundEffect("res/sound/spring3.wav")			
	};
	public static final SoundEffect crack = new SoundEffect("res/sound/ice crack.wav");
	private static final Sound forest = new SplitSong("res/sound/forest start.wav","res/sound/forest loop.wav");
	private static final Sound iceCave = new SplitSong("res/sound/iceCaveStart.wav","res/sound/iceCave.wav");
	private static final Song peak = new Song("res/sound/peak.wav",true);
	private static final Song home = new Song("res/sound/home.wav",true);
	
	private ArrayList<SoundEffect> sounds = new ArrayList<>();
	
	@Override
	public void run() {
		
		final int FPS = 100, DELAY = 1000000000 / FPS;
		while(run) {

			
			double startTime= System.nanoTime();//getting the time at the start of the frame
			
			for(int i=sounds.size()-1;i>=0;i--) {
				sounds.get(i).update();
				if(!sounds.get(i).isPlaying()) {
					sounds.remove(i);
					
				}
			}
			forest.update();
			iceCave.update();
			peak.update();
			crack.update();
			for(SoundEffect i:smash) {
				i.update();
			}
			for(SoundEffect i:walking) {
				i.update();
			}
			for(SoundEffect i:spring) {
				i.update();
			}
			
			//System.out.println("playing: "+currentSong+" next:"+nextSong);
			if(nextSong!=currentSong) {
				if(getSong(currentSong)!=null) {
					getSong(currentSong).stop();
				}else {
					getSong(nextSong).play();
					currentSong=nextSong;
				}
				
				if(getSong(nextSong)!=null&&!getSong(currentSong).isPlaying()) {
					getSong(nextSong).play();
					currentSong=nextSong;
					System.out.println("ee");
				}
				
			}
			if(getSong(currentSong)!=null) {
				getSong(currentSong).update();
			}
			
			
			double endTime= System.nanoTime();//the time at the end of the frame
			double delta=endTime-startTime;//how long the fame took
			while(delta<(DELAY)) {
				endTime= System.nanoTime();
				delta=endTime-startTime;
			}
			
		}
		forest.close();
		iceCave.close();
		peak.close();
		home.close();
		crack.close();
		for(SoundEffect i:smash) {
			i.close();
		}
	}
	
	private Sound getSong(int index) {
		if(index==FOREST) {
			return forest;
		}else if(index==ICECAVE) {
			return iceCave;
		}else if(index==PEAK) {
			return peak;
		}else if(index==HOME) {
			return home ;
		}
		return null;
	}
	
	public synchronized void switchSong(int nextSong) {
		//System.out.println("switching from "+this.nextSong+" to "+nextSong);
		if(currentSong==this.nextSong) {
			this.nextSong=nextSong;
		}
	
		
	}
	public synchronized void close() {
		run=false;
	}
	public synchronized void applyVolume() {
		getSong(currentSong).setVolume(SantaJam.getGame().getSettings().getMusic()/100f);
	}
	
	public static void playSound(SoundEffect[] sounds) {
		ArrayList<SoundEffect> soundArr =new ArrayList<>(Arrays.asList(sounds));
		boolean playable=false;
		int index=0;
		while(soundArr.size()>=1&&!playable) {
			
			index=ThreadLocalRandom.current().nextInt(soundArr.size());
			
			if(soundArr.get(index).isPlaying()) {
				soundArr.remove(index);
			}else{
				playable=true;
			}
		}
		
		if(soundArr.size()==0) {
			sounds[ThreadLocalRandom.current().nextInt(sounds.length)].play();
		}else {
			soundArr.get(index).play();
		}
	
	
	}
	
	
	
	

}
