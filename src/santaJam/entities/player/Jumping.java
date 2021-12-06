package santaJam.entities.player;

import java.awt.Color;
import java.awt.Rectangle;

import santaJam.graphics.particles.movers.Straight;
import santaJam.graphics.particles.shapes.OvalParticle;
import santaJam.graphics.particles.shapes.colourers.Timed;
import santaJam.graphics.particles.spawners.RectangleSpawn;
import santaJam.inputs.Inputs;

public class Jumping extends PlayerState{
	public static final double JUMPSTRENGTH=5, STOPSTRENGTH=0.75;
	boolean firstFrame=true;

	public Jumping() {
	}
	@Override
	public void start(PlayerState prevState) {}

	@Override
	public PlayerState update(Player player) {
		super.update(player);
		if(Math.abs(player.getVelX())>TOPWALKSPEED) {
			return new SlideJump();
			
		}
		player.setAnim(Player.jumping);
		normalMoveLeftRight(player);
		normalGravity(player);
		
		
		if(firstFrame) {
			Rectangle pBounds = player.getBounds();
			new RectangleSpawn(5, pBounds.x-3, pBounds.y+pBounds.height-3, pBounds.width+10,5,new Straight(0, 0, -90,60,0.5),
					new OvalParticle(2, new Timed(Color.white,10,5)) , true);
			player.setVelY(-JUMPSTRENGTH);
			firstFrame=false;
		}
		
		if(Inputs.grapple().getHoldLength()<BUFFERLENGTH&&Inputs.grapple().getHoldLength()>0) {
			return new Grapple(this,player);
			
		}
		if(player.getVelY()<=0) {
			if(!Inputs.jump().isHeld()) {
				player.addVelY(STOPSTRENGTH);
			}	
		}else {
			return new Falling();
		}		
		return null;
		
		
	}

	@Override
	public void end() {}
}
