package santaJam.graphics.particles.shapes.colourers;

import java.awt.Color;
import java.util.concurrent.ThreadLocalRandom;

public class RandTimed extends ParticleColourer{
	
	private int time,range;
	private Color[] colours;
	
	private RandTimed(Color colour, int time, int range) {
		super(colour);
		this.range=range;
		
		if(range==0) {
			this.time=time;
		}else{
			this.time=ThreadLocalRandom.current().nextInt(time-range,time+range);	
		}
	}
	
	/**
	 * makes particles that disapear after a set amount of time 
	 * colour is the colour of the particle, time is how long it stays in frames, and 
	 * range determines how big of a range the particle will stay for 
	 * this means that the particle will stay for time +/- range frames
	 */
	public RandTimed(Color[] colours, int time, int range) {
		super(colours[0]);
		this.colours=colours;
		this.range=range;
		this.time=time;
	}
	
	
	@Override
	public void update() {
		time--;
		if(time<=0) {
			remove=true;
		}
	}

	@Override
	/**returns a particleColourer with this colourers current colour, time, and range*/
	public ParticleColourer copy() {
		Color colour = colours[ThreadLocalRandom.current().nextInt(colours.length)];
		return new RandTimed(colour, time,range);
	}
	
}
