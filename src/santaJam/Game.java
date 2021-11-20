package santaJam;

import santaJam.states.GameState;
import santaJam.states.StateManager;
import santaJam.window.Window;

public class Game {
	private Window window;
	private boolean running=true;
	
	public Game() {
		window = new Window(900, 600);
		StateManager.setCurrentState(new GameState());
	}
	
	public void run() {
		final int FPS = 60, DELAY = 1000000000 / FPS;
		
		while(running) {
			double startTime= System.nanoTime();//getting the time at the start of the frame
			
			//updating
			StateManager.update();
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
				
			}
			
			
		}
	}

}
