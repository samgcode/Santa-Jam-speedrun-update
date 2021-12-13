package santaJam.states;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import santaJam.Assets;
import santaJam.Game;
import santaJam.entities.Entity;
import santaJam.entities.player.Player;
import santaJam.inputs.Inputs;
import santaJam.maps.Map;
import santaJam.maps.Room;

public class MapState implements State{
	private final int LEFT=0, RIGHT=1, UP=2, DOWN=3;
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
		mapImg = new BufferedImage((maxX-minX)/Map.TILESIZE+10, (maxY-minY)/Map.TILESIZE+10, BufferedImage.TYPE_4BYTE_ABGR);	
		xOffset=minX/Map.TILESIZE;
		yOffset=minY/Map.TILESIZE;
		
		drawRooms(mapImg, map, minX, minY, maxX, maxY);
		for(RoomExit e:roomExits(map)) {
			
			int x=(e.x-minX)/Map.TILESIZE;
			int y=e.y/Map.TILESIZE-minY/Map.TILESIZE;
			if(e.direction==LEFT) {
				for(int j=0;j<=5;j++) {
					mapImg.setRGB(x-j+5,y+5, Color.white.getRGB());
					//fadeOut(x+j,y,(6-j)*30, mapImg);
				}
			}
			if(e.direction==RIGHT) {
				for(int j=0;j<=5;j++) {
					mapImg.setRGB(x+j+5,y+5, Color.white.getRGB());
					//fadeOut(x-j,y,(6-j)*30, mapImg);
				}
				
			}
			if(e.direction==UP) {
				for(int j=0;j<=5;j++) {
					mapImg.setRGB(x+5,y-j+5, Color.white.getRGB());
					//fadeOut(x,y+j,(6-j)*30, mapImg);
				}
				
			}
			if(e.direction==DOWN) {
				for(int j=0;j<=5;j++) {
					mapImg.setRGB(x+5,y+j+5, Color.white.getRGB());
					//fadeOut(x,y-j,(6-j)*30, mapImg);
				}
				
			}		
			
		}
		return mapImg;
	}
	private void drawRooms(BufferedImage img, Map map, int minX, int minY, int maxX, int maxY) {
		for(int i:openedRooms) {
			Room r = map.getAllRooms().get(i);
			
			//drawing the room to the map
			for(int y=0;y<r.getHeight()/Map.TILESIZE;y++) {
				for(int x=0;x<r.getWidth()/Map.TILESIZE;x++) {
					int mapX=x-minX/Map.TILESIZE+r.getX()/Map.TILESIZE;
					int mapY=y-minY/Map.TILESIZE+r.getY()/Map.TILESIZE;
					if(r.isIceBlock(x,y)) {
						img.setRGB(mapX+5,mapY+5, Color.blue.getRGB());
					}//else if(!r.isWall(x,y)) {
						if(r.getArea()==1) {
							img.setRGB(mapX+5,mapY+5, new Color(80,230,100).getRGB());
						}else if(r.getArea()==2) {
							img.setRGB(mapX+5,mapY+5, new Color(230,100,100).getRGB());
						}else if(r.getArea()==3) {
							img.setRGB(mapX+5,mapY+5, new Color(100,190,230).getRGB());
						}else if(r.getArea()==4) {
							img.setRGB(mapX+5,mapY+5, new Color(42,22,24).getRGB());
						}
					//}
				}
			}
		}
		
	}
	private void fadeOut(int x, int y, int amount, BufferedImage img) {
		Color c = new Color(img.getRGB(x, y), true);
		int alpha = c.getAlpha()-amount;
		if(!(c.getRed()==0&&c.getGreen()==0&&c.getBlue()==0)) {
			img.setRGB(x,y,new Color(c.getRed(),c.getGreen(),c.getBlue(),Math.max(alpha, 5)).getRGB());
		}
		
		
	}
	private ArrayList<RoomExit> roomExits(Map map) {
		ArrayList<RoomExit> exits = new ArrayList<>();
		
		ArrayList<Room> unopenedRooms  = new ArrayList<>();
		for(int i=0;i<map.getAllRooms().size();i++) {
			boolean opened=false;
			for(int j:openedRooms) {
				if(i==j) {
					opened=true;
				}
			}
			if(!opened) {
			unopenedRooms.add(map.getAllRooms().get(i));
			}
		}
		
		for(int i:openedRooms) {
			Room checkRoom = map.getAllRooms().get(i);
			for(int x=0;x<checkRoom.getWidth()/Map.TILESIZE;x++) {
				if(!checkRoom.isWall(x, 0)){
					for(Room r:map.getAllRooms()) {
						if(r.getBounds().contains(checkRoom.getX()+x*Map.TILESIZE,checkRoom.getY()-1)) {
							int xOffset = checkRoom.getX()-r.getX();
							if(!r.isWall(xOffset/Map.TILESIZE+x,r.getHeight()/Map.TILESIZE-1)) {
								exits.add(new RoomExit(checkRoom.getX()+x*Map.TILESIZE, checkRoom.getY(),UP));
							}
						}
					}
				}
				if(!checkRoom.isWall(x, checkRoom.getHeight()/Map.TILESIZE)){
					for(Room r:map.getAllRooms()) {
						if(r.getBounds().contains(checkRoom.getX()+x*Map.TILESIZE,checkRoom.getY()+checkRoom.getHeight()+1)) {
							int xOffset = checkRoom.getX()-r.getX();
							if(!r.isWall(xOffset/Map.TILESIZE+x,0)) {
								exits.add(new RoomExit(checkRoom.getX()+x*Map.TILESIZE, checkRoom.getY()+checkRoom.getHeight(),DOWN));
							}
						}
					}
				}
			}
			for(int y=0;y<checkRoom.getHeight()/Map.TILESIZE;y++) {
				if(!checkRoom.isWall(0, y)){
					for(Room r:map.getAllRooms()) {
						if(r.getBounds().contains(checkRoom.getX()-1,checkRoom.getY()+y*Map.TILESIZE)) {
							int yOffset = checkRoom.getY()-r.getY();
							if(!r.isWall(r.getWidth()/Map.TILESIZE,yOffset/Map.TILESIZE+y)) {
								exits.add(new RoomExit(checkRoom.getX(), checkRoom.getY()+y*Map.TILESIZE,LEFT));
							}
						}
					}
				}
				if(!checkRoom.isWall(checkRoom.getWidth()/Map.TILESIZE, y)){
					for(Room r:map.getAllRooms()) {
						if(r.getBounds().contains(checkRoom.getX()+checkRoom.getWidth()+1,checkRoom.getY()+y*Map.TILESIZE)) {
							int yOffset = checkRoom.getY()-r.getY();
							if(!r.isWall(0,yOffset/Map.TILESIZE+y)) {
								exits.add(new RoomExit(checkRoom.getX()+checkRoom.getWidth()-1, checkRoom.getY()+y*Map.TILESIZE,RIGHT));
							}
						}
					}
				}
			}
		}
		
		return exits;
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

class RoomExit {
	int x, y;
	int direction;
	public RoomExit(int x, int y, int direction) {
		this.x=x;
		this.y=y;
		this.direction=direction;
	}
	
}
