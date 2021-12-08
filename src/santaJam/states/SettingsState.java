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
				new MenuSelection(new Rectangle(50,50,50,20), "MUSIC VOLUME:"+SantaJam.getGame().getSettings().getMusic()) {
					@Override
					public void select() {
						int volume = SantaJam.getGame().getSettings().getMusic()-10;
						if(volume<0) {
							volume=100;
						}
						SantaJam.getGame().getSettings().setMusic(volume);
						name = "MUSIC VOLUME:"+SantaJam.getGame().getSettings().getMusic();
					}
				},
				new MenuSelection(new Rectangle(50,50,50,30), "EFFECTS VOLUME:"+SantaJam.getGame().getSettings().getSounds()) {
					@Override
					public void select() {
						int volume = SantaJam.getGame().getSettings().getSounds()-10;
						if(volume<0) {
							volume=100;
						}
						SantaJam.getGame().getSettings().setSounds(volume);
						name = "EFFECTS VOLUME:"+SantaJam.getGame().getSettings().getSounds();
					}
				},
				new MenuSelection(new Rectangle(50,50,50,40), "REBIND CONTROLS") {
					@Override
					public void select() {
						stateToSwitch = new RebindControls(new SettingsState(mainState));
					}
				},
				new MenuSelection(new Rectangle(50,50,50,50), "RESET SETTINGS") {
					@Override
					public void select() {
						SantaJam.getGame().getSettings().resetSettings();
						StateManager.setCurrentState(new PauseState(mainState));
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
		g.drawString("ABIL.", 365, 100);
	}

	@Override
	public void end() {
		// TODO Auto-generated method stub
		
	}

}
