package santaJam.states;

import java.awt.Graphics2D;

import santaJam.entities.Enemy;
import santaJam.entities.Entity;
import santaJam.entities.player.Player;
import santaJam.maps.Map;

public class GameState implements State {
	int x=0, y=0,xVel=0,yVel=0;
	
	Map map;
	Camera camera;
	Player player;
	

	
	@Override
	public void start(State prevState) {
		player = new Player();
		Entity.getManager().addEntity(player);
		map = new Map("res/WorldTest.world");
		camera = new Camera();
	}
	
	@Override
	public void update() {
		Entity.getManager().update();
		map.update();
		camera.moveToEntity(player);
		camera.update(map.getCurrentRoom());
		
	}

	@Override
	public void render(Graphics2D g) {
		map.render(g, camera);
		Entity.getManager().render(g, camera);
	}
	public void gameOver() {
		player = new Player();
		Entity.getManager().reset();
		Entity.getManager().addEntity(player);
	}

	@Override
	public void end() {}
	
	
	public Map getMap() {
		return map;
	}
	public Player getPlayer() {
		return player;
	}

	

}
