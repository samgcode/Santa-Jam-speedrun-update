package santaJam.entities;

import java.awt.Graphics2D;
import java.util.ArrayList;

import santaJam.entities.player.Player;
import santaJam.graphics.Camera;
import santaJam.states.StateManager;

public class EntityManager {
	ArrayList<Entity> entities = new ArrayList<Entity>();
	
	public void update() {	
		StateManager.getGameState().getPlayer().update();
		for(int i = 0; i <entities.size() ; i++) {
			if(!(entities.get(i) instanceof Player)){
				entities.get(i).update();
			}
		}
		
		for (int i = entities.size()-1; i >=0 ; i--) {
			if(entities.get(i).isKilled()||!StateManager.getGameState().getMap().getPlayerRoom().getBounds().contains(entities.get(i).getBounds())){ //killing the player
				
				if(entities.get(i) instanceof Player && entities.get(i).isKilled() ) {
					StateManager.getGameState().gameOver();
					System.out.println("ee");
				}else if(!(entities.get(i) instanceof Player)){
					entities.remove(i); //If an other entity besides the player gets hit remove that entity
				}
				
			}
		}
		
	}
	public void render(Graphics2D g, Camera camera) {
		for(Entity i:entities) {
			if(!( i instanceof Player)) {
				i.render(g, camera);
			}
			
		}
		StateManager.getGameState().getPlayer().render(g, camera);
		
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
