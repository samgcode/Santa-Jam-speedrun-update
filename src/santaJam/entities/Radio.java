package santaJam.entities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.concurrent.ThreadLocalRandom;

import santaJam.Assets;
import santaJam.SantaJam;
import santaJam.audio.MusicManager;
import santaJam.entities.player.Player;
import santaJam.graphics.Animation;
import santaJam.graphics.Camera;

public class Radio extends Entity{
	private boolean colliding = false;
	private int currentSong = MusicManager.HOME;
	Animation anim = new Animation(Assets.radio);
	
	
	public Radio(int x, int y) {
		anim.setLooping(false);
		
		SantaJam.getGame().getMusic().switchSong(MusicManager.HOME);
		bounds.width=anim.getCurrentFrame().getWidth();
		bounds.height=anim.getCurrentFrame().getHeight();
		this.x=x;
		this.y=y-bounds.height+8;
		bounds.x=(int) this.x;
		bounds.y=(int) this.y;
		grappleable=true;

	}
	@Override
	public void update() {
		anim.update();
		boolean frameCollision=false;
		for(Entity i:entityCollide()) {
			if(i instanceof Player) {
				frameCollision=true;
				if(!colliding) {
					colliding=true;
					playerCollide((Player) i);
					anim.setPaused(false);
				}
			}
		}
		if(!frameCollision) {
			colliding=false;
		}
		
	}
	private void playerCollide(Player player) {
		int nextSong =currentSong;
		while(nextSong==currentSong) {
			nextSong= ThreadLocalRandom.current().nextInt(MusicManager.HOME,MusicManager.GOOSE2);
		}
		currentSong=nextSong;
		System.out.println(nextSong);
		
		MusicManager.radioStatic.play();
		SantaJam.getGame().getMusic().switchSong(nextSong);	
	}
	
	
	
	
	@Override
	public void render(Graphics2D g, Camera camera) {
		
		g.setColor(Color.blue);
		g.drawImage(anim.getCurrentFrame(),bounds.x-camera.getxOffset(), bounds.y-camera.getyOffset(),null);
	}
	
	
	
}




