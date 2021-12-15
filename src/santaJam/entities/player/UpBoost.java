package santaJam.entities.player;

import java.awt.Color;
import java.awt.Rectangle;

import santaJam.graphics.particles.movers.Straight;
import santaJam.graphics.particles.shapes.RectangleShape;
import santaJam.graphics.particles.shapes.colourers.Timed;
import santaJam.graphics.particles.spawners.RectangleSpawn;
import santaJam.states.StateManager;

public class UpBoost extends PlayerState{
	PlayerState prevState;
	
	private boolean firstFrame=true;
	private int chargeTime=35;
	private double boostVel;
	
	
	public UpBoost(PlayerState prevState, Player player) {
		boostVel=Math.abs(player.getVelX());
		System.out.println("boosting with "+boostVel);
		this.prevState = prevState;
	}
	@Override
	public void start(PlayerState prevState) {
		
	}
	@Override
	public PlayerState update(Player player) {
		if(!StateManager.getGameState().getSave().hasUpBoost()||!player.changeBounds(width, height)) {
			slideGravity(player);
			return prevState;
		}
		
		
		chargeTime--;
		if(firstFrame) {
			//stopping the player and checking direction if it is the first frame
			player.setVelX(0);
			player.setVelY(0);
			player.setAnim(Player.boostStart);
			firstFrame=false;
			System.out.println("stopped");
			
		}
		if(chargeTime==0) {
			player.setAnim(Player.boost);
			player.setVelY(-boostVel*1.75);
			System.out.println("boost");
			
		}
		if(chargeTime<0) {
			if(chargeTime%2==0) {
				Rectangle pBounds = player.getBounds();
				new RectangleSpawn(1, pBounds.x-3, pBounds.y+pBounds.height-3, pBounds.width+10,5,new Straight(0, 0, 90,0,0.75),
						new RectangleShape(2,2, new Timed(Color.white,10,5)) , true);
			}
			
			normalGravity(player);
			normalMoveLeftRight(player);
			if(player.getVelY()>=0) {
				System.out.println("returning");
				return new Falling();
			}
			
		}

		return null;
	}

	@Override
	public void end() {
		// TODO Auto-generated method stub
		
	}

}
