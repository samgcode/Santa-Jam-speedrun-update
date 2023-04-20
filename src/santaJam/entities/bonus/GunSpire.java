package santaJam.entities.bonus;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import santaJam.Assets;
import santaJam.entities.Entity;
import santaJam.graphics.Animation;
import santaJam.graphics.Camera;

public class GunSpire extends Entity{
    private Animation anim = new Animation(Assets.gunSpire,8);
   
    public GunSpire(int x, int y) {
		this.x=x;
		this.y=y-9;
        bounds = new Rectangle((int)this.x,(int)this.y,6,16);
		anim.setLooping(true);
	
	}
    @Override
    public void update() {
        anim.update();
    }

    @Override
    public void render(Graphics2D g, Camera camera) {
        g.drawImage(anim.getCurrentFrame(),(int)x-1-camera.getxOffset(),(int)y-camera.getyOffset(),null);
    }

    
}
