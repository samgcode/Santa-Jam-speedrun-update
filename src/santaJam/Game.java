package santaJam;

import santaJam.audio.MusicManager;
import santaJam.inputs.Inputs;
import santaJam.saves.Settings;
import santaJam.states.StateManager;
import santaJam.states.TitleScreen;
import santaJam.window.Window;

public class Game {
	public static final int WIDTH=320, HEIGHT=180;
	private static Window window;
	private boolean running=true;
	private Settings settings;
	private MusicManager music = new MusicManager();
	static int FPS = 60, DELAY = 1000000000 / FPS;
	
	public Game() {
		window = new Window(WIDTH,HEIGHT);
		settings = new Settings();
		Inputs.setKeyBinds(settings.getKeyBinds());
	}
	
	public void run() {
		StateManager.setCurrentState(new TitleScreen());
		while(running) {
			double startTime= System.nanoTime();//getting the time at the start of the frame
			
			//updating
			
			Inputs.update();
			StateManager.update();
			music.update();
			window.render();
			
			
			
			
			double endTime= System.nanoTime();//the time at the end of the frame
			double delta=endTime-startTime;//how long the fame took
			//saying that things are lagging if it took more than 1/60th of a second
			if(delta >= DELAY) {
				
				System.out.println("\nuh oh things are lagging");
				System.out.println("it is "+(delta-DELAY) +" nanoseconds behind");
				System.out.println("FPS is "+1f/(delta/1000000000f));
				
			}
			//stopping the main loop until it has been 1/60th of a second after the start of the frame
			while(delta<(DELAY)) {
				endTime= System.nanoTime();
				delta=endTime-startTime;
				Thread.currentThread().setPriority(2);
				
			}
			Thread.currentThread().setPriority(7);
			
			
		}
		music.close();
		window.getFrame().dispose();
	}
	public void quitGame() {
		running=false;
	}
	public static Window getWindow() {
		return window;
	}
	public Settings getSettings() {
		return settings;
	}
	public MusicManager getMusic() {
		return music;
	}
	
	public static int getFps() {
		return FPS;
	}
	public static void setFps(int newFps) {
		FPS = newFps;
		DELAY = 1000000000 / FPS;
	}
}
