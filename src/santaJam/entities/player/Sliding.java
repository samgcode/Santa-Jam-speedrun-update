package santaJam.entities.player;

import java.awt.Color;
import java.awt.Rectangle;

import santaJam.audio.MusicManager;
import santaJam.graphics.particles.movers.Straight;
import santaJam.graphics.particles.shapes.OvalParticle;
import santaJam.graphics.particles.shapes.RectangleShape;
import santaJam.graphics.particles.shapes.colourers.Timed;
import santaJam.graphics.particles.spawners.EvenRectSpawn;
import santaJam.graphics.particles.spawners.RectangleSpawn;
import santaJam.inputs.Inputs;
import santaJam.inputs.Keybind;
import santaJam.states.StateManager;

public class Sliding extends PlayerState{
	int coyoteTime=0;
	PlayerState prevState;
	private EvenRectSpawn bigParticles, smallParticles;
	private boolean firstFrame=true;
	
	@Override
	public void start(PlayerState prevState) {
		width=slideWidth;
		height=slideHeight;
		refreshAbilities();
		this.prevState=prevState;
		
		smallParticles= new EvenRectSpawn(0,0,0,0,0,new Straight(0, 0, -90,60,0.5),
				new RectangleShape(1, new Timed(Color.white,6,3)) , true);
		bigParticles= new EvenRectSpawn(0,0,0,0,0,new Straight(0, 0, -90,60,0.5),
				new RectangleShape(2, new Timed(Color.white,6,3)) , true);
	}

	@Override
	public PlayerState update(Player player) {
		
		if(!StateManager.getGameState().getSave().hasSlide()) {
			normalGravity(player);
			normalMoveLeftRight(player);
			return prevState;
		}
		super.update(player);
		if(firstFrame) {
			
			Rectangle pBounds = player.getBounds();
			new RectangleSpawn(4, pBounds.x-3, pBounds.y+pBounds.height-3, 6,5,new Straight(0, 0, 210,30,0.6),
					new OvalParticle(2, new Timed(Color.white,15)) , true);
			new RectangleSpawn(4, pBounds.x+pBounds.width-3, pBounds.y+pBounds.height-3, 6,5,new Straight(0, 0, -20,30,0.6),
					new OvalParticle(2, new Timed(Color.white,15)) , true);
			if(!player.changeBounds(width, height)){
				return new Crawling();
			}
			
			MusicManager.playSound(MusicManager.landing);
			player.setAnim(Player.landing);
			firstFrame=false;
			
		}
		MusicManager.slide.play();
		player.setAnim(Player.sliding);
		slideGravity(player);
		
		Rectangle pBounds = player.getBounds();
		smallParticles.move(pBounds.x, pBounds.y+pBounds.height-3, pBounds.width, 3,40);
		bigParticles.move(pBounds.x, pBounds.y+pBounds.height-3, pBounds.width, 3,20);
		smallParticles.update();
		bigParticles.update();
		
		if(Math.abs(player.getVelX())<=TOPWALKSPEED) {	
			return new Falling();
		}
		if(Inputs.getKey(Keybind.GRAPPLE).getHoldLength()<BUFFERLENGTH&&Inputs.getKey(Keybind.GRAPPLE).getHoldLength()>0&&!Inputs.getKey(Keybind.GRAPPLE).isInputUsed()) {
			Inputs.getKey(Keybind.GRAPPLE).useInput();
			return new SlideGrapple(this,player);
		}
		if(Inputs.getKey(Keybind.JUMP).getHoldLength()<BUFFERLENGTH&&Inputs.getKey(Keybind.JUMP).getHoldLength()>0&&!Inputs.getKey(Keybind.JUMP).isInputUsed()) {
			Inputs.getKey(Keybind.JUMP).useInput();
			return new SlideJump();
		}
		if(Inputs.getKey(Keybind.UP).getHoldLength()<BUFFERLENGTH&&Inputs.getKey(Keybind.UP).getHoldLength()>0&&!Inputs.getKey(Keybind.UP).isInputUsed()) {
			Inputs.getKey(Keybind.UP).useInput();
			return new UpBoost(this,player);	
		}
		
		if(!player.isGrounded()) {
			coyoteTime--;
			if(coyoteTime==0) {
				return new SlideFalling();
			}
		}else {
			//resetting coyote time if they are touching the ground
			coyoteTime=COYOTETIME;
		}
		
		return null;
	}

	@Override
	public void end() {
		MusicManager.slide.stop();
	}

}
