package santaJam.states;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import santaJam.Assets;
import santaJam.Game;
import santaJam.SantaJam;
import santaJam.audio.MusicManager;
import santaJam.components.Timer;
import santaJam.graphics.UI.TextElement;
import santaJam.inputs.Inputs;
import santaJam.inputs.Keybind;
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
		Color textColour = new Color(200,254,255),hoverColour = new Color(5,28,40) ;

		menu = new Menu(new Rectangle(), new MenuObject[] {
				new MenuSelection(new Rectangle(Game.WIDTH/2-40,50,50,20), "QUIT TO TITLE", textColour,hoverColour) {
					@Override
					public void select() {
						mainState.saveTime();
						if(!Timer.TASPlayback) { StateManager.getGameState().saveTas(); }
						stateToSwitch =  new TitleScreen();
					}
					@Override
					public void render(Graphics g) {
						super.render(g);
						bounds.x=Game.WIDTH/2-(name.length()*TextElement.BIGMONOWIDTH/2);
					}
				},
				new MenuSelection(new Rectangle(Game.WIDTH/2-40,50,50,30), "MUSIC VOLUME:"+SantaJam.getGame().getSettings().getMusic(),textColour,hoverColour) {
					@Override
					public void select() {
						int volume = SantaJam.getGame().getSettings().getMusic()-10;
						if(volume<0) {
							volume=100;
						}
						SantaJam.getGame().getSettings().setMusic(volume);
						name = "MUSIC VOLUME:"+SantaJam.getGame().getSettings().getMusic();
					}
					@Override
					public void render(Graphics g) {
						super.render(g);
						bounds.x=Game.WIDTH/2-(name.length()*TextElement.BIGMONOWIDTH/2);
					}
				},
				new MenuSelection(new Rectangle(Game.WIDTH/2-40,50,50,40), "EFFECTS VOLUME:"+SantaJam.getGame().getSettings().getSounds(),textColour,hoverColour) {
					@Override
					public void select() {
						int volume = SantaJam.getGame().getSettings().getSounds()-10;
						if(volume<0) {
							volume=100;
						}
						SantaJam.getGame().getSettings().setSounds(volume);
						name = "EFFECTS VOLUME:"+SantaJam.getGame().getSettings().getSounds();
					}
					@Override
					public void render(Graphics g) {
						super.render(g);
						bounds.x=Game.WIDTH/2-(name.length()*TextElement.BIGMONOWIDTH/2);
					}
				},
				new MenuSelection(new Rectangle(Game.WIDTH/2-40,50,50,50), "REBIND CONTROLS",textColour,hoverColour) {
					@Override
					public void select() {
						stateToSwitch = new RebindControls(new SettingsState(mainState));
					}
					@Override
					public void render(Graphics g) {
						super.render(g);
						bounds.x=Game.WIDTH/2-(name.length()*TextElement.BIGMONOWIDTH/2);
					}
				},
				new MenuSelection(new Rectangle(Game.WIDTH/2-40,50,50,60), "RESET SETTINGS",textColour,hoverColour) {
					@Override
					public void select() {
						SantaJam.getGame().getSettings().resetSettings();
						StateManager.setCurrentState(new PauseState(mainState));
					}
					@Override
					public void render(Graphics g) {
						super.render(g);
						bounds.x=Game.WIDTH/2-(name.length()*TextElement.BIGMONOWIDTH/2);
					}
				},
				new MenuSelection(new Rectangle(Game.WIDTH/2-40,50,50,70), "SPEEDRUN MODE:" + SantaJam.getGame().getSettings().getSpeedrunEnabled(), textColour,hoverColour) {
					@Override
					public void select() {
						SantaJam.getGame().getSettings().toggleSpeedrun();
						name = "SPEEDRUN MODE:" + SantaJam.getGame().getSettings().getSpeedrunEnabled();
						
					}
					@Override
					public void render(Graphics g) {
						super.render(g);
						bounds.x=Game.WIDTH/2-(name.length()*TextElement.BIGMONOWIDTH/2);
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
		if(Inputs.getKey(Keybind.RIGHT).isPressed()) {
			MusicManager.playSound(MusicManager.menuBack);
			StateManager.setCurrentState(new PauseState(mainState));
		}if(Inputs.getKey(Keybind.PAUSE).isPressed()) {
			MusicManager.playSound(MusicManager.menuBack);
			StateManager.setCurrentState(mainState);
			
		}
	}

	@Override
	public void render(Graphics2D g) {
		g.drawImage(Assets.settingsScreen,0,0, null);
		g.setColor(new Color(200,254,255));
		g.setFont(Assets.bigFont);
		g.drawString("Settings", 133, 24);
		
		int hoverWidth= ((MenuSelection) menu.getHovered()).getName().length()*TextElement.BIGMONOWIDTH;
		g.drawImage(Assets.menuMarker,Game.WIDTH/2-hoverWidth/2-12,63+menu.getHoveredIndex()*10, null);
		g.drawImage(Assets.menuMarker,Game.WIDTH/2+hoverWidth/2+12,63+menu.getHoveredIndex()*10,
				-Assets.menuMarker.getWidth(),Assets.menuMarker.getHeight(), null);
		menu.render(g);
		
		
	}

	@Override
	public void end() {
		// TODO Auto-generated method stub
		
	}

}
