package santaJam.states;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import santaJam.Assets;
import santaJam.Game;
import santaJam.SantaJam;
import santaJam.audio.MusicManager;
import santaJam.graphics.Camera;
import santaJam.graphics.UI.TextElement;
import santaJam.graphics.particles.Particle;
import santaJam.graphics.particles.movers.Straight;
import santaJam.graphics.particles.shapes.RectangleShape;
import santaJam.graphics.particles.shapes.colourers.FadeOut;
import santaJam.graphics.particles.spawners.EvenRectSpawn;
import santaJam.inputs.Inputs;
import santaJam.maps.Room;
import santaJam.saves.Save;
import santaJam.states.menus.Menu;
import santaJam.states.menus.MenuObject;
import santaJam.states.menus.MenuSelection;

public class MainMenu implements State{
	EvenRectSpawn particles;
	Room home, lastRoom;
	Camera bgCam ;
	Menu menu;
	State stateToSwitch = null;
	private Color green = new Color(6,50,52);
	
	public MainMenu(Room home, Room lastRoom, Camera bgCam) {
		this.home=home;
		this.lastRoom=lastRoom;
		this.bgCam=bgCam;
		bgCam.moveToPoint(0, 17*8);
	}
	
	@Override
	public void start(State prevState) {
		particles = new EvenRectSpawn(0.01,0,0,lastRoom.getWidth(),lastRoom.getHeight(),
				new Straight(0,0,15, 5, 5),new RectangleShape(2,1,new FadeOut(1.5)),true);
		String startText;
		if(new Save().isStarted()) {
			startText="CONTINUE";
		}else {
			startText="START";
		}
		MenuSelection reset = new MenuSelection(new Rectangle(-500,60,50,10), "YES RESET EVERYTHING",green, Color.white) {
			@Override
			public void select() {
				new Save().resetSave();
				stateToSwitch = new MainMenu(home, lastRoom, bgCam);
			}
			public void render(Graphics g) {
				super.render(g);
				this.bounds.x=Game.WIDTH/2-(name.length()*TextElement.BIGMONOWIDTH/2);
			}
		};
		MenuSelection back = new MenuSelection(new Rectangle(-500,70,50,10), "NEVERMIND",green, Color.white) {
			@Override
			public void select() {
				menu.closeSubMenu();
			}
			public void render(Graphics g) {
				super.render(g);
				this.bounds.x=Game.WIDTH/2-(name.length()*TextElement.BIGMONOWIDTH/2);
			}
		};
		
		menu = new Menu(new Rectangle(), new MenuObject[] {
			new MenuSelection(new Rectangle(50,60,50,10), startText,green, Color.white ) {
				@Override
				public void select() {
					stateToSwitch = new GameState(new Save());
				}
				public void render(Graphics g) {
					super.render(g);
					bounds.x=Game.WIDTH/2-(name.length()*TextElement.BIGMONOWIDTH/2);
				}
			},
			new Menu(new Rectangle(50,70,50,10),"RESET SAVE",green, Color.white, new MenuObject[] {reset,back}) {
				public void render(Graphics g) {
					super.render(g);
					this.bounds.x=Game.WIDTH/2-(name.length()*TextElement.BIGMONOWIDTH/2);
				}
			},
			new MenuSelection(new Rectangle(50,80,50,10), "REBIND CONTROLS",green, Color.white) {
				@Override
				public void select() {
					stateToSwitch = new RebindControls(new MainMenu(home, lastRoom, bgCam));
				}
				public void render(Graphics g) {
					super.render(g);
					bounds.x=Game.WIDTH/2-(name.length()*TextElement.BIGMONOWIDTH/2);
				}
			},
			new MenuSelection(new Rectangle(50,90,50,10), "QUIT",green, Color.white) {
				@Override
				public void select() {
					SantaJam.getGame().quitGame();
				}
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
		bgCam.update(lastRoom);
		lastRoom.update();
		particles.update();
		Particle.getParticleManager().update();
		
		
		menu.update();
		if(Inputs.grapple().isPressed()) {
			MusicManager.menuBack.play();
			StateManager.setCurrentState(new TitleScreen(home, lastRoom, bgCam));
		}
		if(stateToSwitch!=null) {
			StateManager.setCurrentState(stateToSwitch);
		}
		
	}

	@Override
	public void render(Graphics2D g) {
		g.drawImage(Assets.peak,0,0, null);
		Particle.getParticleManager().renderFront(g, bgCam);
		home.render(g, bgCam);
		lastRoom.render(g, bgCam);
		menu.render(g);
		
	
		MenuObject hovered = menu.getHovered();
		if(hovered instanceof Menu &&hovered.isSelected()) {
			hovered = ((Menu) hovered).getHovered();
		}
		
		int hoverWidth= ((MenuSelection) hovered).getName().length()*TextElement.BIGMONOWIDTH;
		g.drawImage(Assets.menuMarkerGreen,hovered.getBounds().x-15,hovered.getBounds().y+3, null);
	
		g.drawImage(Assets.menuMarkerGreen,hovered.getBounds().x+hoverWidth+15,hovered.getBounds().y+3,
				-Assets.menuMarkerGreen.getWidth(),Assets.menuMarkerGreen.getHeight(), null);
		g.setColor(green);
		g.setFont(Assets.font);
		g.drawString("THE QUEST UP PENGUIN PEAK", Game.WIDTH/2-78, 40);
		
		g.drawImage(Assets.mainMenuScreen, 0, 0, null);
		
		//g.drawRect(1, 1, Game.WIDTH-3, Game.HEIGHT-3);
	}

	@Override
	public void end() {
		// TODO Auto-generated method stub
		
	}

}
