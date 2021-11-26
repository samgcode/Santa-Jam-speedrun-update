package santaJam.graphics.particles.shapes.colourers;

import java.awt.Color;
import java.util.concurrent.ThreadLocalRandom;

public class RandFadeOut extends ParticleColourer{
	private Color[] colours;
	private double alpha, fadeSpeed;
	private int rRange, gRange,bRange;
	
	private RandFadeOut(Color colour, double fadeSpeed, int startAlpha) {
		super(colour);
		this.fadeSpeed=fadeSpeed;
		alpha=startAlpha;	
		
	}
	
	public RandFadeOut(Color[] colours, double fadeSpeed, int startAlpha) {
		super(colours[0]);
		this.colours=colours;
		this.fadeSpeed=fadeSpeed;
		alpha=startAlpha;	
	}
	
	public RandFadeOut(Color[] colours, double fadeSpeed) {
		this(colours, fadeSpeed,255);
	}
	
	@Override
	public void update() {
		alpha-=fadeSpeed;
		if(alpha<=0) {
			remove=true;
			alpha=0;
		}
		//setting the colour to a new colour with the updateded transparancy
		colour=new Color(colour.getRed(),colour.getGreen(),colour.getBlue(),(int)alpha);
	}

	@Override
	public ParticleColourer copy() {
		Color colour = colours[ThreadLocalRandom.current().nextInt(colours.length)];
		return new RandFadeOut(colour, fadeSpeed, (int)Math.round(alpha));
	}
}
