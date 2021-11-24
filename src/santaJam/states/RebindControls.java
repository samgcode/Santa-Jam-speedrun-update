package santaJam.states;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import santaJam.Assets;
import santaJam.Game;
import santaJam.inputs.Inputs;

public class RebindControls implements State{
	State returnState;

	
	private String[] actionNames = new String[] {"up", "down", "left", "right","jump/confirm","grapple"};
	private int[] keyCodes = new int[actionNames.length+1];
	private int currentAction=0;
	
	public RebindControls(State returnState) {
		this.returnState = returnState;
	}
	
	@Override
	public void start(State prevState) {
		
	}

	@Override
	
	public void update() {
		
		if(Inputs.pause().isPressed()) {
			StateManager.setCurrentState(returnState);
		}
		if(Inputs.AnyKey().isPressed()&&currentAction<actionNames.length) {
			keyCodes[currentAction]=Inputs.getLastKeyCode();
			currentAction++;
		}
		if(currentAction==keyCodes.length-1) {
			keyCodes[currentAction]=KeyEvent.VK_ESCAPE;
			Inputs.setKeyBinds(keyCodes);
			StateManager.setCurrentState(returnState);
		}
	}

	@Override
	public void render(Graphics2D g) {
		g.setColor(new Color(78,16,69));
		g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
		g.setColor(Color.white);
		g.setFont(Assets.font);
		g.drawString("REBIND CONTROLS", 150, 10);
		g.drawString("PRESS BUTTON FOR "+actionNames[currentAction].toUpperCase()+"  (ESCAPE TO CANCEL)", 70, 50);
		if(currentAction>0) {
			g.drawString("BINDED KEY "+KeyEvent.getKeyText(keyCodes[currentAction-1]).toUpperCase()+" TO "+
		actionNames[currentAction-1].toUpperCase(), 130, 70);
		}
		
	}

	@Override
	public void end() {
		// TODO Auto-generated method stub
		
	}

}
