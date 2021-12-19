package santaJam.entities.player;

import java.awt.Rectangle;
import java.util.ArrayList;

import santaJam.audio.MusicManager;
import santaJam.entities.Entity;
import santaJam.inputs.Inputs;
import santaJam.maps.Room;
import santaJam.states.StateManager;

public class Grapple extends PlayerState {
	protected double GRAPPLESTRENGTH = 1.5, MAXSPEED = 4.6, CHECKSPERFRAME = 4, SHOTSPEED = 20, SHOTDURATION = 15,
			PULLDURATION = 30;
	protected static boolean canGrapple = true;
	protected boolean drawGrapple=false;

	protected boolean firstFrame = true, facingLeft, shooting = true;
	protected int grappleX, grappleY, duration;

	protected PlayerState prevState;

	public Grapple(PlayerState prevState, Player player) {
		this.prevState = prevState;
		duration = 0;
		facingLeft = player.isFaceLeft();
		grappleX = player.getBounds().x + player.getBounds().width / 2;
		grappleY=player.getBounds().y + 5;

	}

	@Override
	public void start(PlayerState prevState) {
	}

	@Override
	public PlayerState update(Player player) {
		if (!canGrapple || !StateManager.getGameState().getSave().hasGrapple()) {
			normalGravity(player);
			normalMoveLeftRight(player);
			
			return prevState;
		}
		drawGrapple=true;
		if (firstFrame) {
			// stopping the player and checking direction if it is the first frame
			player.setVelX(0);
			player.setVelY(0);
			player.changeBounds(width, height);
			MusicManager.playSound(MusicManager.grappleThrow);
		}

		duration++;
		grappleY=player.getBounds().y + 5;
		// doing things for the grapple shoot
		if (shooting) {
			PlayerState returnVal = grappleShoot(player);
			player.setAnim(Player.grappleThrow);
			if (returnVal != null) {
				return returnVal;
			}

			// bringing the player towards the wall if they hit something
		} else {
			PlayerState returnVal = grapplePull(player);
			player.setAnim(Player.grapplePull);
			if (returnVal != null) {
				return returnVal;
			}
		}

		firstFrame = false;
		return null;
	}

	protected PlayerState grappleShoot(Player player) {
		PlayerState returnState = null;
		int checks = 0;
		while (returnState == null && checks < CHECKSPERFRAME && shooting) {
			returnState = moveGrapple(player, SHOTSPEED / CHECKSPERFRAME);
			checks++;
		}

		// going back to the previous state if it has been too long
		if (duration > SHOTDURATION) {
			canGrapple = false;
			return prevState;
		}
		return returnState;

	}

	private PlayerState moveGrapple(Player player, double amount) {
		// moving the grapple depending on their direction
		if (facingLeft) {
			grappleX -= amount;
		} else {
			grappleX += amount;
		}

		// chekcing for walls
		Rectangle checkBox = new Rectangle(grappleX-1, grappleY, 2, 5);
		ArrayList<Rectangle> walls = new ArrayList<Rectangle>();
		for (Room i : StateManager.getGameState().getMap().getLoadedRooms()) {
			if (i != null) {
				for (Rectangle r : i.getWalls()) {
					walls.add(r);
				}
			}

		}
		ArrayList<Rectangle> smoothWalls = new ArrayList<Rectangle>();
		for (Room i : StateManager.getGameState().getMap().getLoadedRooms()) {
			if (i != null) {
				for (Rectangle r : i.getSmoothWalls()) {
					smoothWalls.add(r);
				}
			}

		}
		for (Rectangle r : walls) {
			if (r.intersects(checkBox)) {
				for (Rectangle i : smoothWalls) {
					if (i == r) {
						MusicManager.playSound(MusicManager.grappleClank);
						System.out.println("eee");
						canGrapple = false;
						return prevState;
					}
				}
				MusicManager.grappleYoink.play();
				shooting = false;
			}
		}
		for (Entity i : Entity.getManager().getEntities()) {
			if (i.getBounds().intersects(checkBox) && !(i instanceof Player)) {
				if (i.isGrappleable()) {
					MusicManager.grappleYoink.play();
					shooting = false;
				} else {
					MusicManager.playSound(MusicManager.grappleClank);
					canGrapple = false;
					return prevState;
				}
			}
		}
		return null;
	}

	protected PlayerState grapplePull(Player player) {
		super.update(player);
		refreshAbilities();// refreshing abilities if the land the grapple
		if (player.getVelY() != 0) {
			return new Falling();
		}
		if (facingLeft) {
			if (player.getVelX() > 0 || player.getBounds().x < grappleX) {

				return new Falling();

			}
			// moving left
			if (player.getVelX()-GRAPPLESTRENGTH <= -MAXSPEED) {
				player.setVelX(-MAXSPEED);
			} else {
				player.addVelX(-GRAPPLESTRENGTH);
			}

		} else {
			if (player.getVelX() < -0 || player.getBounds().x > grappleX) {
				return new Falling();
			}
			// moving right
			if (player.getVelX()+GRAPPLESTRENGTH>=MAXSPEED) {
				player.setVelX(MAXSPEED);
				
			} else {
				player.addVelX(GRAPPLESTRENGTH);
			}
		}
		// letting you cancel the pull into a jump
		if (Inputs.jump().getHoldLength() < BUFFERLENGTH && Inputs.jump().getHoldLength() > 0&&!Inputs.jump().isInputUsed()) {
			Inputs.jump().useInput();
			return new Jumping();
		}

		if (duration > SHOTDURATION + PULLDURATION) {

			return new Falling();

		}
		return null;
	}

	@Override
	public void end() {
	}

	public int getCheckX() {
		return grappleX;
	}
	public int getCheckY() {
		return grappleY;
	}
	public boolean isDrawGrapple() {
		return drawGrapple;
	}

	public static void refreshGrapple() {
		canGrapple = true;
	}
}
