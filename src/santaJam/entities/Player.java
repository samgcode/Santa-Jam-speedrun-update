package santaJam.entities;

import java.awt.Color;
import java.awt.Graphics2D;

import santaJam.inputs.Inputs;

public class Player extends Entity {
	
	public Player() {
		super(100,100,10,10);
	}

	@Override
	public void update() {
	
		velY+=GRAVITY;
		velX=0;
		if(velY>10) {
			velY=10;
		}
		if(Inputs.left()) {
			velX=-2;
		}if(Inputs.right()) {
			velX=2;
		}if(Inputs.jump()&&grounded) {
			velY=-10;
		}
		updateBounds();
		
	}

	@Override
	public void render(Graphics2D g) {
		g.setColor(Color.black);
		g.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
		g.setColor(Color.yellow);
		g.fillRect(bounds.x,bounds.y+bounds.height,bounds.width,1);
	}
	
	

}
