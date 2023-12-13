package santaJam.entities.player;

import java.awt.Color;
import java.awt.Rectangle;

import santaJam.audio.MusicManager;
import santaJam.graphics.Animation;
import santaJam.graphics.particles.movers.Straight;
import santaJam.graphics.particles.shapes.OvalParticle;
import santaJam.graphics.particles.shapes.colourers.Timed;
import santaJam.graphics.particles.spawners.RectangleSpawn;
import santaJam.inputs.Inputs;
import santaJam.inputs.Keybind;
import santaJam.states.StateManager;

public class Jumping extends PlayerState{
	public static final double JUMPSTRENGTH=5, STOPSTRENGTH=0.75;
	boolean firstFrame=true;
	protected Animation anim = Player.jumping;

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
		player.setAnim(anim);
		normalMoveLeftRight(player);
		normalGravity(player);
		
		
		if(firstFrame) {
			MusicManager.playSound(MusicManager.jump);
			Rectangle pBounds = player.getBounds();
			new RectangleSpawn(5, pBounds.x-3, pBounds.y+pBounds.height-3, pBounds.width+10,5,new Straight(0, 0, -90,60,0.5),
					new OvalParticle(2, new Timed(Color.white,10,5)) , true);
			player.setVelY(-JUMPSTRENGTH);
			firstFrame=false;
		}
		if(StateManager.getGameState().getSave().hasGrapple()) {
			if(Inputs.getKey(Keybind.GRAPPLE).getHoldLength()<BUFFERLENGTH&&Inputs.getKey(Keybind.GRAPPLE).getHoldLength()>0&&!Inputs.getKey(Keybind.GRAPPLE).isInputUsed()) {
				Inputs.getKey(Keybind.GRAPPLE).useInput();
				return new Grapple(this,player);
				
			}
		}
		if(player.getVelY()<=0) {
			if(!Inputs.getKey(Keybind.JUMP).isHeld()) {
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
