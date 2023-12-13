package santaJam.entities.player;

import java.awt.Color;
import java.awt.Rectangle;

import santaJam.audio.MusicManager;
import santaJam.graphics.particles.movers.Straight;
import santaJam.graphics.particles.shapes.OvalParticle;
import santaJam.graphics.particles.shapes.RectangleShape;
import santaJam.graphics.particles.shapes.colourers.Timed;
import santaJam.graphics.particles.spawners.RectangleSpawn;
import santaJam.inputs.Inputs;
import santaJam.inputs.Keybind;
import santaJam.states.StateManager;

public class Standing extends PlayerState{
	private final int WALKTIME=25;
	
	private int coyoteTime, walkTimer=25;
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
			new RectangleSpawn(3, pBounds.x-3, pBounds.y+pBounds.height-3, 6,5,new Straight(0, 0, 210,30,0.6),
					new OvalParticle(2, new Timed(Color.white,15)) , true);
			new RectangleSpawn(3, pBounds.x+pBounds.width-3, pBounds.y+pBounds.height-3, 6,5,new Straight(0, 0, -20,30,0.6),
					new OvalParticle(2, new Timed(Color.white,15)) , true);
			if(!player.changeBounds(width, height)){
				return new Crawling();
			}
			
			MusicManager.playSound(MusicManager.landing);
			player.setAnim(Player.landing);
			firstFrame=false;
			
		}
		
		normalMoveLeftRight(player);//moving the player
		normalGravity(player);//doing gravity
		
		if(player.getCurrentAnim()!=Player.landing||player.getCurrentAnim().getFrameIndex()==player.getCurrentAnim().getlength()-1) {
			if(Math.abs(player.getVelX())>0) {
				walkTimer++;
				if(walkTimer>=WALKTIME) {
					walkTimer=0;
					MusicManager.playSound(MusicManager.walking);
					Rectangle pBounds = player.getBounds();
					new RectangleSpawn(3, pBounds.x-2, pBounds.y+pBounds.height-3, pBounds.width+4,5,new Straight(0, 0, -90,90,0.3),
						new RectangleShape(1,1, new Timed(Color.white,10,5)) , true);
				}
				player.setAnim(Player.walking);
			}else {
				walkTimer=20;
				player.setAnim(Player.idle);
			}
		}
	
		//if they pressed/buffered a jump, then they should jump
		if(Inputs.getKey(Keybind.JUMP).getHoldLength()<BUFFERLENGTH&&Inputs.getKey(Keybind.JUMP).getHoldLength()>0&&!Inputs.getKey(Keybind.JUMP).isInputUsed()) {
			// System.out.println("jumping");
			Inputs.getKey(Keybind.JUMP).useInput();
			return new Jumping();
		}
		if(StateManager.getGameState().getSave().hasGrapple()) {
			//if they pressed/buffered an grapple, they should grapple
			if(Inputs.getKey(Keybind.GRAPPLE).getHoldLength()<BUFFERLENGTH&&Inputs.getKey(Keybind.GRAPPLE).getHoldLength()>0&&!Inputs.getKey(Keybind.GRAPPLE).isInputUsed()) {
				Inputs.getKey(Keybind.GRAPPLE).useInput();
				System.out.println("I think I found the issue");
				return new Grapple(this,player);
			}
		}
		
		if(player.getVelY()<0) {
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
			coyoteTime=COYOTETIME;
		}
		return null;
		
	}
	@Override
	public void end() {}
}
