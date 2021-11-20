package santaJam.entities;

import java.awt.Color;
import java.awt.Graphics2D;

import santaJam.inputs.Inputs;

public class Player extends Entity {
	
	public Player() {
		x=30;
		y=0;
	}

	@Override
	public void update() {
		velY+=GRAVITY;
		if(velY>6) {
			velY=6;
		}
		if(Inputs.left()) {
			velX=-2;
		}if(Inputs.right()) {
			velX=2;
		}if(Inputs.jump()) {
			velY-=1.5;
		}
		x+=velX;
		y+=velY;
		velX=0;
	}

	@Override
	public void render(Graphics2D g) {
		g.setColor(Color.black);
		g.fillRect((int)Math.round(x), (int)Math.round(y), 10, 10);
	}

}
