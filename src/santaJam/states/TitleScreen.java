package santaJam.states;

import java.awt.Color;
import java.awt.Graphics2D;

import santaJam.Assets;
import santaJam.Game;
import santaJam.graphics.Camera;
import santaJam.inputs.Inputs;
import santaJam.maps.Room;

public class TitleScreen implements State{
	Room home,lastRoom;
	Camera bgCam  ;
	
	public TitleScreen(Room home, Room lastRoom, Camera bgCam) {
		this.home=home;
		this.lastRoom=lastRoom;
		this.bgCam=bgCam;
	}
	public TitleScreen() {
		this(new Room(320, 136, "home.json"), new Room(0, 0, "3-10.json"),new Camera());
		
	}
	
	@Override
	public void start(State prevState) {
		bgCam.setEntityWeight(0);
		bgCam.setLocationWeight(1);
	}

	@Override
	public void update() {
		home.update();
		bgCam.update(home);
		if(Inputs.jump().isPressed()) {
			StateManager.setCurrentState(new MainMenu(home, lastRoom, bgCam));
		}
	}

	@Override
	public void render(Graphics2D g) {
		lastRoom.render(g, bgCam);
		home.render(g, bgCam);
		g.drawImage(Assets.titleScreen,0, 0, null);
		g.setFont(Assets.bigFont);
		//g.setColor(Color.black);
		//g.drawString("Title Name", Game.WIDTH/2-40, 51);
		g.setColor(Color.white);
		g.drawString("TITLE NAME", Game.WIDTH/2-41, 50);
		
		g.setFont(Assets.font);
		g.drawString("--PRESS "+Inputs.jump().getKey().toUpperCase()+" TO START--", Game.WIDTH/2-50, 140);
	}

	@Override
	public void end() {
		// TODO Auto-generated method stub
		
	}

}
