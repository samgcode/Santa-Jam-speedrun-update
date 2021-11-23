package santaJam.entities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import santaJam.Assets;
import santaJam.entities.player.Player;
import santaJam.states.Camera;
import santaJam.states.StateManager;

public class SaveStatue extends Entity{
	private int saveTimer=0, maxSaveTime=300;
	public SaveStatue(int x, int y) {
		bounds = new Rectangle(x,y,10,10);
		this.x=x;
		this.y=y;
		team=1;
		grappleable=true;
	}
	@Override
	public void update() {
		saveTimer--;
		for(Entity i:entityCollide()) {
			if(i instanceof Player) {
				if(saveTimer<=0) {
					Player player = StateManager.getGameState().getPlayer();
					player.health=player.getMaxHealth();
					StateManager.getGameState().getSave().savePlayerData(player.bounds.x,player.bounds.y,player.getHealth());
					saveTimer=maxSaveTime;
				}
			}
		}
	}
	
	@Override
	public void render(Graphics2D g, Camera camera) {
		g.setColor(Color.CYAN);
		g.fillRect(bounds.x-camera.getxOffset(), bounds.y-camera.getyOffset()+5,bounds.width,bounds.height);
		if(saveTimer>maxSaveTime-60) {
			g.setFont(Assets.font);
			g.drawString("GAME SAVED", bounds.x-30-camera.getxOffset(), bounds.y-camera.getyOffset()-5);
		}
	}
	
	
}




