package santaJam.audio;




public class MusicManager extends Thread{
	boolean run=true;
	private int currentSong=-1;
	private int nextSong=-1;
	
	public static final int FOREST=0, ICECAVE=1,PEAK=2,MENU=3;
	private static final Sound forest = new Song("res/sound/forest.wav",true);
	private static final Sound iceCave = new SplitSong("res/sound/iceCaveStart.wav","res/sound/iceCave.wav");
	private static final Song peak = new Song("res/sound/peak.wav",true);
	
	@Override
	public void run() {
		final int FPS = 100, DELAY = 1000000000 / FPS;
		while(run) {
			double startTime= System.nanoTime();//getting the time at the start of the frame
			
			forest.update();
			iceCave.update();
			peak.update();
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
		if(getSong(currentSong)!=null) {
			getSong(currentSong).stop();
		}
	}
	
	private Sound getSong(int index) {
		if(index==FOREST) {
			return forest;
		}else if(index==ICECAVE) {
			return iceCave;
		}else if(index==PEAK) {
			return peak;
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
	
	
	
	

}
