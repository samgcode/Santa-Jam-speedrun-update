package santaJam.entities.player;

import java.awt.Color;
import java.awt.Rectangle;

import santaJam.graphics.particles.movers.Straight;
import santaJam.graphics.particles.shapes.OvalParticle;
import santaJam.graphics.particles.shapes.colourers.Timed;
import santaJam.graphics.particles.spawners.RectangleSpawn;
import santaJam.inputs.Inputs;

public class Standing extends PlayerState{
	
	private int coyoteTime;
	private boolean firstFrame=true;
	
	@Override
	public void start(PlayerState prevState) {
		coyoteTime=0;	
		refreshAbilities();
		
	}
	
	@Override
	public PlayerState update(Player player) {
		super.update(player);
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
			player.setAnim(Player.landing);
			firstFrame=false;
		}
		
		normalMoveLeftRight(player);//moving the player
		normalGravity(player);//doing gravity
		
		if(player.getCurrentAnim()!=Player.landing||player.getCurrentAnim().getFrameIndex()==player.getCurrentAnim().getlength()-1) {
			if(Math.abs(player.getVelX())>0) {
				player.setAnim(Player.walking);
			}else {
				player.setAnim(Player.idle);
			}
		}
		
		
		
		//if they pressed/buffered a jump, then they should jump
		if(Inputs.jump().getHoldLength()<BUFFERLENGTH&&Inputs.jump().getHoldLength()>0&&!Inputs.jump().isInputUsed()) {
			Inputs.jump().useInput();
			return new Jumping();
		}
		//if they pressed/buffered an grapple, they should grapple
		if(Inputs.grapple().getHoldLength()<BUFFERLENGTH&&Inputs.grapple().getHoldLength()>0&&!Inputs.grapple().isInputUsed()) {
			Inputs.grapple().useInput();
			return new Grapple(this,player);
		}
		
		if(player.getVelY()<0) {
			System.out.println();
			return new Falling();
		}
		//transitioning to the falling state if the are not on the ground, and it is after coyote time
		if(!player.isGrounded()) {
			coyoteTime--;
			if(coyoteTime<=0) {
				
				return new Falling();
			}
		}else {
			//resetting coyote time if they are touching the ground
			coyoteTime=5;
		}
		return null;
		
	}
	@Override
	public void end() {}
}
