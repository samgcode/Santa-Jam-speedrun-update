package santaJam.states;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import santaJam.Game;
import santaJam.SantaJam;
import santaJam.audio.MusicManager;
import santaJam.components.InputDisplay;
import santaJam.components.Timer;
import santaJam.entities.Entity;
import santaJam.entities.player.Player;
import santaJam.graphics.Camera;
import santaJam.graphics.UI.RectElement;
import santaJam.graphics.UI.TextElement;
import santaJam.graphics.UI.UIElement;
import santaJam.graphics.particles.Particle;
import santaJam.inputs.Inputs;
import santaJam.inputs.Keybind;
import santaJam.maps.Map;
import santaJam.saves.Save;

public class GameState implements State {
	int x=0, y=0,xVel=0,yVel=0;
	
	private double deathTransition=0;
	private boolean resetting=false;
	
	private Map map;
	private Camera camera;
	private Player player;
	private Save save;
	private ArrayList<Integer> openedRooms = new ArrayList<>();
	private boolean stalled=false;

	private TasPlayback tas;
	
	TextElement timerText = new TextElement(2, 0, 3, 7, "00:00:00.000 \n frame: 0000");
	RectElement timerBG = new RectElement(timerText.getX()-2,timerText.getY()-2,140,timerText.getHeight()+4, new Color(6,50,52));
	InputDisplay inputDisplay = new InputDisplay();

	public GameState(Save saveFile) {
		this.save=saveFile;
		saveFile.startTimer();
		Entity.getManager().reset();
		player = new Player(save.getStartX(),save.getStartY());
		Entity.getManager().addEntity(player);
		map = new Map("res/maps/main.world");
		camera = new Camera();
		for(int i:save.getOpenedRooms()) {
			openedRooms.add(i);
		}
		if(Timer.TASPlayback) {
			tas = new TasPlayback();
		}
	}
	
	@Override
	public void start(State prevState) {
		if(Timer.TASPlayback) { tas.update(); }
		showTimer();
	}
	
	public void showTimer() {
		if(SantaJam.getGame().getSettings().getSpeedrunEnabled()) {
			UIElement.getUIManager().addElement(timerBG);
			UIElement.getUIManager().addElement(timerText);
			inputDisplay.show();
		}
	}
	
	@Override
	public void update() {
		if(Timer.TASPlayback) { tas.update(); }

		Timer.update();
		if(SantaJam.getGame().getSettings().getSpeedrunEnabled()) {
			inputDisplay.update();
			if(Inputs.getKey(Keybind.DEBUG).isPressed()) {
				Game.setFps(0);
			}
		}

		if(resetting) {
			deathTransition+=0.1;
			
		}
		if(deathTransition>0) {
			if(deathTransition>=1) {
				resetting=false;
				player = new Player(save.getStartX(),save.getStartY());
				Entity.getManager().reset();
				Entity.getManager().addEntity(player);
				map.getPlayerRoom().loadRoom();
				UIElement.getUIManager().clear();
				showTimer();
			}
			if(!resetting) {
				deathTransition-=0.1;
			}else {
				return;
			}		
		}
		
		stalled=false;
		for (UIElement i:UIElement.getUIManager().getElements()) {
			if(i.visible&&i.pauseGame) {
				stalled=true;
			}
		}
		if(!stalled) {
			Entity.getManager().update();
		}
		map.update();
		camera.moveToEntity(player);
		camera.update(map.getPlayerRoom());
		
		timerText.update(Timer.getTimeString() + 
			String.format("\nframe: %d,", Timer.getFrames()) + 
			String.format(" fps: %d", Game.getFps()));
		
		UIElement.getUIManager().update();
		Particle.getParticleManager().update();
		
		if(Inputs.getKey(Keybind.PAUSE).isPressed()) {
			System.out.println(Timer.getTimeString());
			MusicManager.menuSelect.play();
			StateManager.setCurrentState(new PauseState(this));
			UIElement.getUIManager().clear();
		}
	}

	@Override
	public void render(Graphics2D g) {
		Particle.getParticleManager().renderBack(g, camera);
		map.render(g, camera);
		Entity.getManager().render(g, camera);
		Particle.getParticleManager().renderFront(g, camera);
		UIElement.getUIManager().render(g);
		
		g.setColor(new Color(5,28,40));
		if(resetting) {
			g.fillRect(0, 0, Game.WIDTH,(int)(Game.HEIGHT*deathTransition*1.2));
		}else if(deathTransition>0) {
			g.fillRect(0, Game.HEIGHT-(int)(Game.HEIGHT*deathTransition*1.2) ,Game.WIDTH,(int)(Game.HEIGHT*deathTransition*1.5));
		}
	}
	public void gameOver() {
		player.setAnim(Player.falling);
		player.getCurrentState().end();
		MusicManager.death.play();
		
		resetting=true;
		
	}
	public void saveData(int x, int y) {
		save.saveGameTime();
		save.saveOpenedRooms(openedRooms);
		save.savePlayerData(x,y);
	}
	
	public void saveTime() {
		save.saveGameTime();
	}

	@Override
	public void end() {}
	
	
	public Map getMap() {
		return map;
	}

	public Player getPlayer() {
		return player;
	}
	public Save getSave() {
		return save;
	}
	public ArrayList<Integer> getOpenedRooms() {
		return openedRooms;
	}
	
	public void addOpenedRoom(int room) {
		for(int i:openedRooms) {
			if (i==room) {
				return;
			}
		}
		openedRooms.add(room);
	}
	public Camera getCamera() {
		return camera;
	}

}
