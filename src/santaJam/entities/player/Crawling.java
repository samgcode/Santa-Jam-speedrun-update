package santaJam.entities.player;

import java.awt.Color;
import java.awt.Rectangle;

import santaJam.audio.MusicManager;
import santaJam.graphics.particles.movers.Straight;
import santaJam.graphics.particles.shapes.OvalParticle;
import santaJam.graphics.particles.shapes.RectangleShape;
import santaJam.graphics.particles.shapes.colourers.Timed;
import santaJam.graphics.particles.spawners.RectangleSpawn;

public class Crawling extends PlayerState{
	private final int WALKTIME=25;
	
	private int walkTimer=25;
	private boolean firstFrame=true;
	
	
	@Override
	public void start(PlayerState prevState) {
		refreshAbilities();	
	}
	
	@Override
	public PlayerState update(Player player) {
		System.out.println("crawlin time");
		super.update(player);
		//letting them slide
		if(Math.abs(player.getVelX())>TOPWALKSPEED) {
			return new Sliding();
			
		}
		if(firstFrame) {
			
			Rectangle pBounds = player.getBounds();
			new RectangleSpawn(2, pBounds.x-3, pBounds.y+pBounds.height-3, 6,5,new Straight(0, 0, 180,30,0.5),
					new OvalParticle(2, new Timed(Color.white,10)) , true);
			new RectangleSpawn(2, pBounds.x+pBounds.width-3, pBounds.y+pBounds.height-3, 6,5,new Straight(0, 0, 0,30,0.5),
					new OvalParticle(2, new Timed(Color.white,10)) , true);
			player.changeBounds(width, height);
			MusicManager.playSound(MusicManager.landing);
			player.setAnim(Player.sliding);
			firstFrame=false;
		}
		
		
		slowMoveLeftRight(player);//moving the player
		normalGravity(player);//doing gravity
		
		if(Math.abs(player.getVelX())>0) {
			Rectangle pBounds = player.getBounds();
			new RectangleSpawn(1, pBounds.x, pBounds.y+pBounds.height-3, pBounds.width,5,new Straight(0, 0, -90,90,0.5),
						new RectangleShape(1,1, new Timed(Color.white,6,3)) , true);
			walkTimer++;
			if(walkTimer>=WALKTIME) {
				walkTimer=0;
				MusicManager.playSound(MusicManager.walking);
			}
			
		}else {
			walkTimer=20;
		}
	
		
		if(player.changeBounds(width, height)){
			return new Standing();
		}
		return null;
		
	}
	@Override
	public void end() {}
}
