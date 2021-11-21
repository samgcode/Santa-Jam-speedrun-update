package santaJam.entities.player;

import santaJam.inputs.Inputs;

public abstract class PlayerState {
	private static PlayerState currentState = new Standing();
	
	
	private final static double FRICTION=0.5, TOPWALKSPEED=3.5, WALKACCELERATION=1;
	
	public abstract void start(PlayerState prevState);
	public abstract PlayerState update(Player player);
	public abstract void end();
	
	protected void normalMoveLeftRight(Player player) {
		if(Inputs.left().isHeld()) {
			player.addVelX(-WALKACCELERATION);
			player.setDirection(true);
		}if(Inputs.right().isHeld()) {
			player.addVelX(WALKACCELERATION);
			player.setDirection(false);
		}
		doFriction(player);
		
	}
	protected void normalGravity(Player player) {
		player.addVelY(Player.GRAVITY);
		if(player.getVelY()>Player.MAXGRAVITY) {
			player.setVelY(Player.MAXGRAVITY);
		}
	}
	protected void doFriction(Player player) {
		if(player.getVelX()>FRICTION) {
			player.addVelX(-FRICTION);
		}else if(player.getVelX()<-FRICTION){
			player.addVelX(FRICTION);
		}else {
			player.setVelX(0);
		}
		player.setVelX(Math.min(player.getVelX(), TOPWALKSPEED));
		player.setVelX(Math.max(player.getVelX(), -TOPWALKSPEED));
	}
	
	
	public static void setState(PlayerState newState) {
		currentState.end();
		newState.start(currentState);
		currentState=newState;
		
	}
	
	

}
