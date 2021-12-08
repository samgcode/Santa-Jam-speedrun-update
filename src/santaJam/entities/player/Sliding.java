package santaJam.entities.player;

import java.awt.Color;
import java.awt.Rectangle;

import santaJam.graphics.particles.movers.Straight;
import santaJam.graphics.particles.shapes.RectangleShape;
import santaJam.graphics.particles.shapes.colourers.Timed;
import santaJam.graphics.particles.spawners.EvenRectSpawn;
import santaJam.inputs.Inputs;
import santaJam.states.StateManager;

public class Sliding extends PlayerState{
	int coyoteTime=0;
	PlayerState prevState;
	private EvenRectSpawn bigParticles, smallParticles;
	
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
		if(Inputs.grapple().getHoldLength()<BUFFERLENGTH&&Inputs.grapple().getHoldLength()>0) {
			return new SlideGrapple(this,player);
		}
		if(Inputs.jump().getHoldLength()<BUFFERLENGTH&&Inputs.jump().getHoldLength()>0) {
			return new SlideJump();
		}
		if(Inputs.up().getHoldLength()<BUFFERLENGTH&&Inputs.up().getHoldLength()>0) {
			return new UpBoost(this,player);	
		}
		
		if(!player.isGrounded()) {
			coyoteTime--;
			if(coyoteTime==0) {
				return new SlideFalling();
			}
		}else {
			//resetting coyote time if they are touching the ground
			coyoteTime=3;
		}
		
		return null;
	}

	@Override
	public void end() {
		// TODO Auto-generated method stub
		
	}

}
