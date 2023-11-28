package santaJam.states;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import santaJam.Assets;
import santaJam.Game;
import santaJam.SantaJam;
import santaJam.graphics.UI.TextElement;
import santaJam.inputs.Inputs;

public class RebindControls implements State{
	State returnState;
	TextElement action  = new TextElement(false, 40,50,TextElement.BIGMONOWIDTH,TextElement.SMALLMONOHEIGHT+1,Game.WIDTH-80,
			"", Assets.font), rebind  = new TextElement(false, 40,70,TextElement.BIGMONOWIDTH,TextElement.SMALLMONOHEIGHT+1,Game.WIDTH-80,
					"", Assets.font);

	
	private String[] actionNames = new String[] {"up", "down", "left", "right","jump/confirm","grapple"};
	private String[] speedActionNames = new String[] {"up", "down", "left", "right","jump/confirm","grapple", "savestate", "reset save" };
	private int[] keyCodes = new int[speedActionNames.length+1];
	private int currentAction=0;
	
	public RebindControls(State returnState) {
		this.returnState = returnState;
		if(SantaJam.getGame().getSettings().getSpeedrunEnabled()) {
			actionNames = speedActionNames;
		}
	}
	
	@Override
	public void start(State prevState) {
		
	}

	@Override
	
	public void update() {
		action.update("  PRESS BUTTON FOR "+actionNames[currentAction].toUpperCase()+" \n ESCAPE TO CANCEL");
		action.centre(Game.WIDTH-80);
		
		
		if(currentAction>0) {
			rebind.update("\n \n BINDED KEY "+KeyEvent.getKeyText(keyCodes[currentAction-1]).toUpperCase()+" TO "+
		actionNames[currentAction-1].toUpperCase());
			rebind.centre(Game.WIDTH-80);
		}
		
		
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
		g.drawImage(Assets.creditScreen,0,0,null);
		g.setColor(Color.white);
		g.setFont(Assets.bigFont);
		g.drawString("Set Controls", 118, 24);
		
		action.render(g);
		rebind.render(g);

		
	}

	@Override
	public void end() {
		// TODO Auto-generated method stub
		
	}

}
