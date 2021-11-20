package santaJam.entities.player;

import santaJam.inputs.Inputs;

public class Jumping extends PlayerState{
	private final double JUMPSTRENGTH=10;
	boolean firstFrame;

	@Override
	public void start(PlayerState prevState) {
		
		firstFrame=true;
		
	}

	@Override
	public PlayerState update(Player player) {
		normalMoveLeftRight(player);
		normalGravity(player);
		if(firstFrame) {
			player.addVelY(-JUMPSTRENGTH);
		}
		firstFrame=false;
		
		if(player.getVelY()<0) {
			if(!Inputs.jumpHeld()) {
			//	normalGravity(player);
			//	normalGravity(player);
			//	normalGravity(player);
			}
			
		}else {
			return falling;
		}
		
		
		
		
		
		
		return null;
		
		
	}

	@Override
	public void end() {
		// TODO Auto-generated method stub
		
	}

}
