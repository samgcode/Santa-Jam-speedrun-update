package santaJam.entities.upgrades;

import santaJam.entities.Entity;
import santaJam.entities.player.Player;

public abstract class Upgrade extends Entity{
	
	protected abstract void onCollect();
	
	
	@Override
	public void update() {
		for(Entity i:entityCollide()) {
			if(i instanceof Player) {
				onCollect();
				killed=true;
			}
		}
	}
	@Override
	public void damage() {}

}
