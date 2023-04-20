package santaJam.entities;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import santaJam.Assets;
import santaJam.entities.player.Player;
import santaJam.graphics.Animation;
import santaJam.graphics.Camera;
import santaJam.states.StateManager;

public class SaveStatue extends Entity{
	private int saveTimer=0, maxSaveTime=300;
	Animation anim = new Animation(Assets.save, 3);
	public SaveStatue(int x, int y) {
		anim.setLooping(false);
		anim.setPaused(true);
		
		this.x=x-3;
		this.y=y;
		bounds = new Rectangle((int)this.x,(int)this.y,14,14);
		team=1;
		grappleable=true;
	}
	@Override
	public void update() {
		saveTimer--;
		anim.update();
		for(Entity i:entityCollide()) {
			if(i instanceof Player) {
				if(saveTimer<=0) {
					
					Player player = StateManager.getGameState().getPlayer();
					player.health=player.getMaxHealth();
					StateManager.getGameState().saveData(bounds.x+bounds.width/2,bounds.y+bounds.height/2);
					anim.setPaused(false);
					saveTimer=maxSaveTime;
				}
			}
		}
	}
	
	@Override
	public void render(Graphics2D g, Camera camera) {
		//g.setColor(Color.white);
		//g.drawRect(bounds.x-camera.getxOffset(), bounds.y-camera.getyOffset(),bounds.width,bounds.height);
		g.drawImage(anim.getCurrentFrame(),bounds.x-camera.getxOffset()-8, bounds.y-camera.getyOffset()-11,null);
		/*if(saveTimer>maxSaveTime-60) {
			g.setFont(Assets.font);
			g.drawString("GAME SAVED", bounds.x-30-camera.getxOffset(), bounds.y-camera.getyOffset()-5);
		}*/
	}
	
	
}




