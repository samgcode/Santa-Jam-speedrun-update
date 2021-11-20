package santaJam.entities.player;

public class Falling extends PlayerState{

	@Override
	public void start(PlayerState prevState) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public PlayerState update(Player player) {
		normalMoveLeftRight(player);
		normalGravity(player);
		if(player.isGrounded()) {
			return standing;
		}
		return null;
	}

	@Override
	public void end() {
		// TODO Auto-generated method stub
		
	}

}
