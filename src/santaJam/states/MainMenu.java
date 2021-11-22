package santaJam.states;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import santaJam.Game;
import santaJam.SantaJam;
import santaJam.saves.Save;
import santaJam.states.menus.Menu;
import santaJam.states.menus.MenuObject;
import santaJam.states.menus.MenuSelection;

public class MainMenu implements State{

	Menu menu;
	State stateToSwitch = null;
	@Override
	public void start(State prevState) {
		String startText;
		if(new Save().isStarted()) {
			startText="CONTINUE";
		}else {
			startText="START";
		}
		
		menu = new Menu(new Rectangle(), new MenuObject[] {
			new MenuSelection(new Rectangle(50,50,50,10), startText) {
				@Override
				public void select() {
					stateToSwitch = new GameState(new Save());
				}
			},
			new Menu(new Rectangle(50,60,50,10),"RESET SAVE", new MenuObject[] {
					new MenuSelection(new Rectangle(50,50,50,10), "YES RESET EVERYTHING") {
						@Override
						public void select() {
							new Save().resetSave();
							stateToSwitch = new MainMenu();
						}
					},new MenuSelection(new Rectangle(50,60,50,10), "NEVERMIND") {
						@Override
						public void select() {
							menu.closeSubMenu();
						}
					},	
			}),
			new MenuSelection(new Rectangle(50,70,50,10), "REBIND CONTROLS") {
				@Override
				public void select() {
					stateToSwitch = new RebindControls();
				}
			},
			new MenuSelection(new Rectangle(50,80,50,10), "QUIT") {
				@Override
				public void select() {
					SantaJam.getGame().quitGame();
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
		
	}

	@Override
	public void render(Graphics2D g) {
		g.setColor(new Color(78,16,69));
		g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
		menu.render(g);
		g.setColor(new Color(50,10,45));
		g.drawRect(1, 1, Game.WIDTH-3, Game.HEIGHT-3);
	}

	@Override
	public void end() {
		// TODO Auto-generated method stub
		
	}

}
