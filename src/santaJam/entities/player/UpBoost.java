package santaJam.entities.player;

import santaJam.states.StateManager;

public class UpBoost extends PlayerState{
	PlayerState prevState;
	
	private boolean firstFrame=true;
	private int chargeTime=30;
	private double boostVel;
	
	
	public UpBoost(PlayerState prevState, Player player) {
		boostVel=Math.abs(player.getVelX());
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
			firstFrame=false;
			System.out.println("stopped");
		}
		if(chargeTime==0) {
			player.setVelY(-boostVel*1.5);
			System.out.println("boost");
			
		}
		if(chargeTime<0) {
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
