package santaJam.entities;

import java.awt.Graphics2D;
import java.util.ArrayList;

import santaJam.states.Camera;

public class EntityManager {
	ArrayList<Entity> entities = new ArrayList<Entity>();
	
	public void update() {
		for(Entity i:entities) {
			i.update();
		}
	}
	public void render(Graphics2D g, Camera camera) {
		for(Entity i:entities) {
			i.render(g, camera);
		}
	}
	
	public void addEntity(Entity e) {
		entities.add(e);
	}
	

}
