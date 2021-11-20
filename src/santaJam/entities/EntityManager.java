package santaJam.entities;

import java.awt.Graphics2D;
import java.util.ArrayList;

public class EntityManager {
	ArrayList<Entity> entities = new ArrayList<Entity>();
	
	public void update() {
		for(Entity i:entities) {
			i.update();
		}
	}
	public void render(Graphics2D g) {
		for(Entity i:entities) {
			i.render(g);
		}
	}
	
	public void addEntity(Entity e) {
		entities.add(e);
	}
	

}
