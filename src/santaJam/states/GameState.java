package santaJam.states;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

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
	
	private Map map;
	private Camera camera;
	private Player player;
	private Save save;
	private ArrayList<Integer> openedRooms = new ArrayList<>();
	private boolean stalled=false;

	public GameState(Save saveFile) {
		this.save=saveFile;
		Entity.getManager().reset();
		player = new Player(save.getStartX(),save.getStartY(),save.getStartHealth());
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
	}
	public void gameOver() {
		player = new Player(save.getStartX(),save.getStartY(),save.getStartHealth());
		Entity.getManager().reset();
		Entity.getManager().addEntity(player);
		map.getPlayerRoom().loadRoom();
		UIElement.getUIManager().clear();
	}
	public void saveData(int x, int y) {
		save.saveOpenedRooms(openedRooms);
		save.savePlayerData(x,y,player.getHealth());
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
