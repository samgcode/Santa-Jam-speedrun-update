package santaJam.entities.upgrades;

import java.awt.Rectangle;

import santaJam.entities.Entity;
import santaJam.entities.player.Player;

public abstract class Upgrade extends Entity{
	protected String description="";
	
	public Upgrade(int x, int y) {
		bounds = new Rectangle(x,y,10,10);
		this.x=x;
		this.y=y;
	}
	
	protected abstract void onCollect(Player player);
	
	
	@Override
	public void update() {
		for(Entity i:entityCollide()) {
			if(i instanceof Player) {
				onCollect((Player) i);
				killed=true;
			}
		}
	}
	public String getDescription() {
		return description;
	}
	
}
