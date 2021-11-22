package santaJam.states;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import santaJam.Assets;
import santaJam.Game;
import santaJam.SantaJam;
import santaJam.inputs.Inputs;
import santaJam.states.menus.Menu;
import santaJam.states.menus.MenuObject;
import santaJam.states.menus.MenuSelection;

public class SettingsState implements State{

	GameState mainState;
	State stateToSwitch = null;
	Menu menu;
	
	public SettingsState(GameState mainState) {
		this.mainState = mainState;
		
	}
	@Override
	public void start(State prevState) {
		menu = new Menu(new Rectangle(), new MenuObject[] {
				new MenuSelection(new Rectangle(50,50,50,10), "QUIT-TO-MENU") {
					@Override
					public void select() {
						stateToSwitch =  new MainMenu();
					}
				},	
				new MenuSelection(new Rectangle(50,50,50,20), "REBIND CONTROLS") {
					@Override
					public void select() {
						stateToSwitch = new RebindControls();
					}
				},
				new MenuSelection(new Rectangle(50,50,50,30), "RESET SETTINGS") {
					@Override
					public void select() {
						SantaJam.getGame().getSettings().resetSettings();
					}
				},
							
			}) ;
		 
			menu.select();
	}

	@Override
	public void update() {
		
		menu.update();
	
		if(stateToSwitch!=null) {
			StateManager.setCurrentState(stateToSwitch);
		}
		if(Inputs.right().isPressed()) {
			StateManager.setCurrentState(new PauseState(mainState));
		}if(Inputs.pause().isPressed()) {
			StateManager.setCurrentState(mainState);
			
		}
	}

	@Override
	public void render(Graphics2D g) {
		g.setColor(new Color(78,16,69));
		g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
		g.setColor(Color.white);
		g.setFont(Assets.font);
		g.drawString("SETTINGS", 150, 10);
		menu.render(g);
	}

	@Override
	public void end() {
		// TODO Auto-generated method stub
		
	}

}
