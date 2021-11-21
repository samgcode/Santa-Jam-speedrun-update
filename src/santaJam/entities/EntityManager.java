package santaJam.entities;

import java.awt.Graphics2D;
import java.util.ArrayList;

import santaJam.entities.player.Player;
import santaJam.states.Camera;
import santaJam.states.StateManager;

public class EntityManager {
	ArrayList<Entity> entities = new ArrayList<Entity>();
	
	public void update() {		
		for(int i = 0; i <entities.size() ; i++) {
			entities.get(i).update();
		}
		for(Entity i:entities) {
			i.damage();	
		}
		for (int i = entities.size()-1; i >=0 ; i--) {
			if(entities.get(i).isKilled()||!StateManager.getGameState().getMap().getCurrentRoom().inBounds(entities.get(i).getBounds())){ //killing the player
				
				if(entities.get(i) instanceof Player) {
					StateManager.getGameState().gameOver();
				}else {
					entities.remove(i); //If an other entity besides the player gets hit remove that entity
				}
				
			}
		}
		
	}
	public void render(Graphics2D g, Camera camera) {
		for(Entity i:entities) {
			i.render(g, camera);
		}
	}
	public void reset() {
		entities = new ArrayList<Entity>();
	}
	
	public void addEntity(Entity e) {
		entities.add(e);
	}
	public ArrayList<Entity> getEntities() {
		// TODO Auto-generated method stub
		return entities;
	}
	

}
