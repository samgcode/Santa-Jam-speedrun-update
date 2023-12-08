package santaJam.entities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

import santaJam.Game;
import santaJam.entities.player.Player;
import santaJam.entities.upgrades.Collectible;
import santaJam.entities.wallEntities.WallEntity;
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
				}else if(!(entities.get(i) instanceof Player)){
					entities.remove(i); //If an other entity besides the player gets hit remove that entity
				}
				
			}
		}
		
	}
	public void render(Graphics2D g, Camera camera) {
		try {
			for(Entity i:entities) {
				if(!( i instanceof Player)) {
					try {
						i.render(g, camera);
						
						if(Game.DEBUG_ENABLED) {
							Rectangle bounds = i.getBounds();
							if(i instanceof BouncePad || i instanceof GrapplePoint) {
								g.setColor(Color.green);
							} else if(i instanceof WallEntity) {
								g.setColor(Color.magenta);
							} else if(i instanceof SpikeSubstitute) {
								g.setColor(Color.red);
							} else {
								g.setColor(Color.yellow);
							}
							g.drawRect(bounds.x-camera.getxOffset(), bounds.y-camera.getyOffset(), bounds.width-1, bounds.height-1);
						}
					} catch(Exception e) {
						// object removed during render
					}
				}
			}
		} catch (Exception e) {
			// something to do with concurrency ig 
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
