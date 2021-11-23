package santaJam.states;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import santaJam.Assets;
import santaJam.Game;
import santaJam.inputs.Inputs;
import santaJam.maps.Map;
import santaJam.maps.Room;

public class MapState implements State{
	private final double MINSCALE=0.75;
	GameState gameState;
	BufferedImage mapImg;
	double scale=MINSCALE;
	int mapX=0, mapY=0;
	public MapState(GameState gameState) {
		this.gameState = gameState;
		mapImg= buildMap(gameState.getMap());
		//centreMap();
		
		
	}
	@Override
	public void start(State prevState) {
		// TODO Auto-generated method stub
		
	}
	
	private BufferedImage buildMap(Map map) {
		BufferedImage mapImg;
		int minX=0, minY=0, maxX=0, maxY=0;
		for(Room r:map.getAllRooms()) {
			if(r.getX()<minX) {
				minX=r.getX();
			}
			if(r.getY()<minY) {
				minY=r.getY();
			}
			if(r.getX()+r.getWidth()>maxX) {
				maxX=r.getX()+r.getWidth();
			}
			if(r.getY()+r.getHeight()>maxY) {
				maxY=r.getY()+r.getHeight();
			}
		}
		mapImg = new BufferedImage((maxX-minX)/Map.TILESIZE, (maxY-minY)/Map.TILESIZE, BufferedImage.TYPE_4BYTE_ABGR);		
		for(Room r:map.getAllRooms()) {
			
			for(int y=0;y<r.getHeight()/Map.TILESIZE;y++) {
				for(int x=0;x<r.getWidth()/Map.TILESIZE;x++) {
					 if(r.isIceBlock(x,y)) {
						mapImg.setRGB(x+r.getX()/Map.TILESIZE,y+r.getY()/Map.TILESIZE, Color.blue.getRGB());
						//mapImg.setRGB(x+r.getX()/Map.TILESIZE,y+r.getY()/Map.TILESIZE);
					}else if(!r.isWall(x,y)) {
						mapImg.setRGB(x+r.getX()/Map.TILESIZE,y+r.getY()/Map.TILESIZE, Color.WHITE.getRGB());
						//mapImg.setRGB(x+r.getX()/Map.TILESIZE,y+r.getY()/Map.TILESIZE);
					}
				}
			}
		}
		
		return mapImg;
	}

	@Override
	public void update() {
		if(Inputs.jump().isHeld()&&scale<5) {
			scale+=scale/20;
		
		}
		if(Inputs.grapple().isHeld()) {
			if(scale>MINSCALE) {
				scale-=scale/20;
			}else {
				mapX=0;
				mapY=0;
			}
		}		
		
		if(Inputs.left().isHeld()&&scale>MINSCALE) {
			mapX+=4;
		}
		if(Inputs.right().isHeld()&&scale>MINSCALE) {
			mapX-=4;
		}
		
		if(Inputs.up().isHeld()&&scale>MINSCALE) {
			mapY+=4;
		}
		if(Inputs.down().isHeld()&&scale>MINSCALE) {
			mapY-=4;
		}
	
		if(Inputs.left().isPressed()) {
			if(scale<=MINSCALE) {
				StateManager.setCurrentState(new PauseState(gameState));
			}else {
				
			}
		}if(Inputs.pause().isPressed()) {
			StateManager.setCurrentState(gameState);
		}
		
		
		
	}

	@Override
	public void render(Graphics2D g) {
		g.setColor(new Color(78,16,69));
		g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
		
		g.drawImage(mapImg,Game.WIDTH/2-(int)(mapImg.getWidth()*scale)/2+mapX,Game.HEIGHT/2-(int)(mapImg.getHeight()*scale)/2+mapY,
				(int)(mapImg.getWidth()*scale),(int)(mapImg.getHeight()*scale), null);
		
		g.setColor(new Color(78,16,69));
		g.fillRect(125,0,Game.WIDTH-250,15);
		g.setColor(Color.white);
		g.setFont(Assets.font);
		g.drawString("MAP", 180, 10);
		
		if(scale<=MINSCALE) {
			g.drawString("ABIL.", 5, 100);
			g.drawString("KEY", 365, 50);
			
		}
		
		
	}

	@Override
	public void end() {
		// TODO Auto-generated method stub
		
	}
}
