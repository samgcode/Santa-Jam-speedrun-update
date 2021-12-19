package santaJam.audio;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

import santaJam.SantaJam;

public class MusicManager extends Thread{
	boolean run=true;
	private int currentSong=-1;
	private int nextSong=-1;
	
	public static final int MENU=0,FOREST=1, ICECAVE=2,PEAK=3,HOME=4,RADIOFOREST=5,RADIOCAVE=6,RADIOPEAK=7, GOOSE1=8,GOOSE2=9;
	public static final SoundEffect death = new SoundEffect("res/sound/death.wav");

	public static final SoundEffect[] smash = new SoundEffect[] {
			new SoundEffect("res/sound/ice smash1.wav"), new SoundEffect("res/sound/ice smash2.wav")
	};
	public static final SoundEffect[] walking = new SoundEffect[] {
			new SoundEffect("res/sound/walking1.wav"),new SoundEffect("res/sound/walking2.wav"),new SoundEffect("res/sound/walking3.wav"),
		
	};
	public static final SoundEffect[] jump = new SoundEffect[] {
			new SoundEffect("res/sound/jump1.wav"),new SoundEffect("res/sound/jump2.wav"),new SoundEffect("res/sound/jump3.wav")
		
	};
	public static final SoundEffect[] landing = new SoundEffect[] {
			new SoundEffect("res/sound/landing1.wav"),new SoundEffect("res/sound/landing2.wav")
		
	};
	public static final ContinuousSound slide = new ContinuousSound("res/sound/slide.wav");
	public static final ContinuousSound fire = new ContinuousSound("res/sound/fire.wav");
	public static final SoundEffect[] grappleThrow = new SoundEffect[] {
			new SoundEffect("res/sound/throw1.wav"),new SoundEffect("res/sound/throw2.wav"),new SoundEffect("res/sound/throw3.wav"),
			new SoundEffect("res/sound/throw4.wav")	,new SoundEffect("res/sound/throw5.wav")	
	};
	public static final SoundEffect grappleYoink = new SoundEffect("res/sound/yoink.wav");
	public static final SoundEffect[] grappleClank = new SoundEffect[] {
			new SoundEffect("res/sound/grapple clank1.wav"),new SoundEffect("res/sound/grapple clank2.wav"),new SoundEffect("res/sound/grapple clank3.wav")
	};
	public static final SoundEffect boostStart = new SoundEffect("res/sound/boost start.wav");
	public static final SoundEffect boostShoot = new SoundEffect("res/sound/boost 2.wav");
	public static final SoundEffect itemGet = new SoundEffect("res/sound/item get.wav");
	public static final SoundEffect[] spring = new SoundEffect[] {
			new SoundEffect("res/sound/spring1.wav"),new SoundEffect("res/sound/spring2.wav"),new SoundEffect("res/sound/spring3.wav")			
	};
	public static final SoundEffect crack = new SoundEffect("res/sound/ice crack.wav");
	
	public static final SoundEffect menuMove = new SoundEffect("res/sound/menu move.wav");
	public static final SoundEffect menuBack = new SoundEffect("res/sound/menu back.wav");
	public static final SoundEffect menuSelect = new SoundEffect("res/sound/menu select.wav");
	
	public static final SoundEffect radioStatic = new SoundEffect("res/sound/static.wav");
	
	private static final Sound forest = new SplitSong("res/sound/forest start.wav","res/sound/forest loop.wav");
	private static final Sound iceCave = new SplitSong("res/sound/iceCaveStart.wav","res/sound/iceCave.wav");
	private static final SplitSong peak = new SplitSong("res/sound/peak start.wav","res/sound/peak loop.wav");
	private static final Sound radioForest = new SplitSong("res/sound/radio/forest start.wav","res/sound/radio/forest loop.wav");
	private static final Sound radioPeak = new Song("res/sound/radio/peak.wav",true);
	private static final Song home = new Song("res/sound/radio/home.wav",true);
	private static final Song goose1 = new Song("res/sound/radio/goose1.wav",true);
	private static final Song goose2 = new Song("res/sound/radio/goose2.wav",true);
	
	
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
			home.update();
			goose1.update();
			goose2.update();
			crack.update();
			menuBack.update();
			menuMove.update();
			menuSelect.update();
			slide.update();
			fire.update();
			for(SoundEffect i:smash) {
				i.update();
			}
			for(SoundEffect i:walking) {
				i.update();
			}
			for(SoundEffect i:landing) {
				i.update();
			}
			for(SoundEffect i:grappleThrow) {
				i.update();
			}
			for(SoundEffect i:grappleClank) {
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
		}else if(index==RADIOFOREST) {
			return radioForest ;
		}else if(index==RADIOCAVE) {
			return iceCave ;
		}else if(index==RADIOPEAK) {
			return radioPeak ;
		}else if(index==GOOSE1) {
			return goose1 ;
		}else if(index==GOOSE2) {
			return goose2 ;
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
	
	public static void playSound(SoundEffect sound) {
		sound.play();
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
