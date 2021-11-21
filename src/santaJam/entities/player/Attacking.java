package santaJam.entities.player;

import java.awt.Rectangle;

import santaJam.entities.Hitbox;

public class Attacking extends PlayerState{
	private final int ACTIVELENGTH=5, ENDLAG=10, WIDTH=25,HEIGHT=15;
	private int xOffset, yOffset=4;
	
	private boolean hit=false;
	
	
	private int totalTime;
	
	private Hitbox hitbox;
	
	
	@Override
	public void start(PlayerState prevState) {
		
		totalTime=0;
	}

	@Override
	public PlayerState update(Player player) {
		normalGravity(player);
		normalMoveLeftRight(player);
		
		totalTime++;
		if(totalTime==1) {
			
			Rectangle pBounds=player.getBounds();
			if(player.isFaceLeft()) {
				xOffset=-WIDTH+2;
			}else {
				xOffset=pBounds.width-2;
			}
			hitbox = new Hitbox(pBounds.x+xOffset, pBounds.y+yOffset,WIDTH,HEIGHT,ACTIVELENGTH,0,1,player.isFaceLeft());
			Player.getManager().addEntity(hitbox);
		}else if(totalTime==ACTIVELENGTH+ENDLAG) {
			return new Falling();
		}
		hitbox.move(player.getBounds().x+xOffset,player.getBounds().y+yOffset);
		
		if(hitbox.isHit()&&hit==false) {
			System.out.println("hit");
			hit=true;
			player.knockBack(!player.isFaceLeft(),3,0);
		}
		
		return null;
	}

	@Override
	public void end() {}

}
