package santaJam.graphics.particles.shapes.colourers;

import java.awt.Color;
import java.util.concurrent.ThreadLocalRandom;

public class FadeOut extends ParticleColourer{
	
	private double alpha, fadeSpeed;
	private int rRange, gRange,bRange;
	
	public FadeOut(Color colour,int rRange,int gRange, int bRange, double fadeSpeed, int startAlpha) {
		super(colour);
		this.fadeSpeed=fadeSpeed;
		this.rRange=rRange;
		this.gRange=gRange;
		this.bRange=bRange;
		alpha=startAlpha;	
	}
	public FadeOut(Color colour, double fadeSpeed, int startAlpha) {
		this(colour,0,0,0,fadeSpeed,startAlpha);
		this.fadeSpeed=fadeSpeed;
		alpha=startAlpha;	
	}
	
	public FadeOut(Color colour, double fadeSpeed) {
		this(colour, fadeSpeed,255);
	}
	
	public FadeOut(double fadeSpeed) {
		this(Color.WHITE, fadeSpeed,255);
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
		int r=colour.getRed();
		int g=colour.getGreen();
		int b=colour.getBlue();
		if(rRange>0) {
			r+=ThreadLocalRandom.current().nextInt(0, rRange);
		}
		if(gRange>0) {
			g+=ThreadLocalRandom.current().nextInt(0, gRange);
		}
		if(bRange>0) {
			b+=ThreadLocalRandom.current().nextInt(0, bRange);
		}
		return new FadeOut(new Color(r,g,b), fadeSpeed, (int)Math.round(alpha));
	}
}
