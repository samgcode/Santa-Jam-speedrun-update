package santaJam.entities.wallEntities;

import java.awt.Rectangle;

import santaJam.entities.Entity;
import santaJam.maps.Map;

public abstract class WallEntity extends Entity{
	private boolean jumpable = true;
	public WallEntity(int x, int y) {
		bounds = new Rectangle(x,y,Map.TILESIZE,Map.TILESIZE);
		this.x=x;
		this.y=y;
	}
	public boolean isJumpable() {
		return jumpable;
	}

}
