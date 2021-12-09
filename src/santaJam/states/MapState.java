package santaJam.states;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import santaJam.Assets;
import santaJam.Game;
import santaJam.SantaJam;
import santaJam.entities.Entity;
import santaJam.entities.player.Player;
import santaJam.inputs.Inputs;
import santaJam.maps.Map;
import santaJam.maps.Room;

public class MapState implements State{
	private final double MINSCALE=0.3;
	GameState gameState;
	BufferedImage mapImg;
	double scale=MINSCALE;
	int mapX=0, mapY=0,xOffset,yOffset;
	ArrayList<Integer> openedRooms = new ArrayList<Integer>();
	public MapState(GameState gameState) {
		this.gameState = gameState;
		openedRooms = gameState.getOpenedRooms();
		mapImg= buildMap(gameState.getMap());
		
		
		
	}
	@Override
	public void start(State prevState) {
		// TODO Auto-generated method stub
		
	}
	
	private BufferedImage buildMap(Map map) {
		BufferedImage mapImg;
	
		int minX=0, minY=0, maxX=0, maxY=0;
		for(int i:openedRooms) {
			Room r = map.getAllRooms().get(i);
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
		xOffset=minX/Map.TILESIZE;
		yOffset=minY/Map.TILESIZE;
		
		for(int i:openedRooms) {
			Room r = map.getAllRooms().get(i);
			
			//drawing the room to the map
			for(int y=0;y<r.getHeight()/Map.TILESIZE;y++) {
				for(int x=0;x<r.getWidth()/Map.TILESIZE;x++) {
					int mapX=x-minX/Map.TILESIZE+r.getX()/Map.TILESIZE;
					int mapY=y-minY/Map.TILESIZE+r.getY()/Map.TILESIZE;
					if(r.isIceBlock(x,y)) {
						mapImg.setRGB(mapX,mapY, Color.blue.getRGB());
					}else if(!r.isWall(x,y)) {
						try {
							
								if(r.getArea()==1) {
									mapImg.setRGB(mapX,mapY, new Color(80,230,100).getRGB());
								}else if(r.getArea()==2) {
									mapImg.setRGB(mapX,mapY, new Color(230,100,100).getRGB());
								}else if(r.getArea()==3) {
									mapImg.setRGB(mapX,mapY, new Color(100,190,230).getRGB());
								}else if(r.getArea()==4) {
									mapImg.setRGB(mapX,mapY, new Color(42,22,24).getRGB());
								}
								
							
						}catch(IndexOutOfBoundsException e) {
							System.out.println((x+r.getX()/Map.TILESIZE)+", "+(y+r.getY()/Map.TILESIZE)+"out of bounds");
							e.printStackTrace();
						}
						
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
		int mapDrawX=Game.WIDTH/2-(int)(mapImg.getWidth()*scale)/2+mapX;
		int mapDrawY=Game.HEIGHT/2-(int)(mapImg.getHeight()*scale)/2+mapY;
		g.setColor(new Color(78,16,69));
		g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
		
		g.drawImage(mapImg,mapDrawX,mapDrawY,(int)(mapImg.getWidth()*scale),(int)(mapImg.getHeight()*scale), null);
		
		//drawing collectible icons
		for(Room r:gameState.getMap().getAllRooms()) {
			if(gameState.getSave().hasBinoculars() && r.hasCollectible()) {
				g.setColor(Color.white);
				
				
				g.drawRect((int)(mapDrawX+(r.getBounds().x+r.getBounds().width/2-xOffset*Map.TILESIZE)/Map.TILESIZE*scale-3),
						(int)(mapDrawY+(r.getBounds().y+r.getBounds().height/2-yOffset*Map.TILESIZE)/Map.TILESIZE*scale-3),3,3);
			}
		}
		
		for(Entity i :Entity.getManager().getEntities()) {
			if(i instanceof Player) {
				g.setColor(Color.red);
					
				g.drawRect((int)(mapDrawX+(i.getBounds().x+i.getBounds().width/2-xOffset*Map.TILESIZE)/Map.TILESIZE*scale-3),
						(int)(mapDrawY+(i.getBounds().y+i.getBounds().height/2-yOffset*Map.TILESIZE)/Map.TILESIZE*scale-3),6,6);
			}
		}
		
		
		g.setColor(new Color(57,11,50));
		g.fillRect(125,0,Game.WIDTH-250,15);
		g.setColor(Color.white);
		g.setFont(Assets.font);
		g.drawString("MAP", Game.WIDTH/2-8, 10);
		
		if(scale<=MINSCALE) {
			g.drawString("ABIL.", 5, 100);
			g.drawString("LEGEND:", 350, 50);
			
			g.drawString("YOU", 360, 60);
			g.setColor(Color.red);
			g.drawRect(350,54,6,6);
		//	g.drawString("", 360, 70);
			//g.setColor(Color.black);
			//g.fillRect(350,64,6,6);
			g.setColor(Color.white);
			g.drawString("ICEWALL", 360, 70);
			g.setColor(Color.blue);
			g.fillRect(350,64,6,6);
		}
		
		
	}

	@Override
	public void end() {
		// TODO Auto-generated method stub
		
	}
}
