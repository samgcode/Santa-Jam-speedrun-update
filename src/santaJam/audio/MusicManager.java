package santaJam.audio;




public class MusicManager extends Thread{
	boolean run=true;
	private int currentSong=-1;
	private int nextSong=-1;
	
	public static final int FOREST=0, ICECAVE=1,PEAK=2,MENU=3;
	private static final Song forest = new Song("res/sound/forest.wav",true);
	private static final Song iceCave = new Song("res/sound/iceCave.wav",true);
	private static final Song peak = new Song("res/sound/peak.wav",true);
	
	@Override
	public void run() {
		
		while(run) {
			if(nextSong!=currentSong) {
				if(getSong(currentSong)!=null) {
					getSong(currentSong).stop();
				}if(getSong(nextSong)!=null) {
					getSong(nextSong).play();
					System.out.println("ee");
				}
				currentSong=nextSong;
			}
			
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
	}
	
	private Song getSong(int index) {
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
		System.out.println("switching to "+nextSong+"from"+this.nextSong);
		this.nextSong=nextSong;
		
	}
	public synchronized void close() {
		run=false;
	}
	
	
	
	

}
