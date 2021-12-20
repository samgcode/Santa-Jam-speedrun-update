package santaJam.states;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import santaJam.Game;
import santaJam.audio.MusicManager;
import santaJam.entities.Entity;
import santaJam.entities.player.Player;
import santaJam.graphics.Camera;
import santaJam.graphics.UI.UIElement;
import santaJam.graphics.particles.Particle;
import santaJam.inputs.Inputs;
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
	}
	
	@Override
	public void start(State prevState) {
		
	}
	
	@Override
	public void update() {
		if(resetting) {
			//System.out.println("e");
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
		UIElement.getUIManager().update();
		Particle.getParticleManager().update();
		
		if(Inputs.pause().isPressed()) {
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
		save.saveOpenedRooms(openedRooms);
		save.savePlayerData(x,y);
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
