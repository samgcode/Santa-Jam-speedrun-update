package santaJam.states;

import java.awt.Graphics2D;

public interface State {
	
	public abstract void start(State prevState);
	public abstract void update();
	public abstract void render(Graphics2D g);
	public abstract void end();
}
