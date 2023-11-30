package santaJam.states;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import santaJam.Assets;
import santaJam.Game;
import santaJam.SantaJam;
import santaJam.graphics.UI.TextElement;
import santaJam.inputs.Inputs;
import santaJam.inputs.Keybind;

public class RebindControls implements State{
	State returnState;
	TextElement action  = new TextElement(false, 40,50,TextElement.BIGMONOWIDTH,TextElement.SMALLMONOHEIGHT+1,Game.WIDTH-80,
	"", Assets.font), rebind  = new TextElement(false, 40,70,TextElement.BIGMONOWIDTH,TextElement.SMALLMONOHEIGHT+1,Game.WIDTH-80,
	"", Assets.font);
	
	private int[] keyCodes = new int[Keybind.values().length];
	private int currentAction=0;

	public RebindControls(State returnState) {
		this.returnState = returnState;
	}
	
	@Override
	public void start(State prevState) {
		
	}

	@Override
	public void update() {
		Keybind current = Keybind.values()[currentAction];
		int index = current.index;

		if(!current.speedrun || SantaJam.getGame().getSettings().getSpeedrunEnabled()) {
			action.update("  PRESS BUTTON FOR "+ current.name.toUpperCase()+" \n ESCAPE TO CANCEL");
			action.centre(Game.WIDTH-80);
			
			
			if(currentAction > 0) {
				rebind.update("\n \n BINDED KEY "+KeyEvent.getKeyText(keyCodes[index-1]).toUpperCase() + " TO "+ Keybind.values()[currentAction-1].name.toUpperCase());
				rebind.centre(Game.WIDTH-80);
			}
			
			
			if(Inputs.getKey(Keybind.PAUSE).isPressed()) {
				StateManager.setCurrentState(returnState);
			}
			if(Inputs.AnyKey().isPressed() && currentAction < keyCodes.length) {
				keyCodes[index]=Inputs.getLastKeyCode();
				currentAction++;
			}
			if(index == Keybind.PAUSE.index) {
				// keyCodes[currentAction]=KeyEvent.VK_ESCAPE;
				Inputs.setKeyBinds(keyCodes);
				StateManager.setCurrentState(returnState);
			}
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
