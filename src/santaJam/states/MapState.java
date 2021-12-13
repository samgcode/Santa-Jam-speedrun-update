package santaJam.states;

import java.awt.Color;
import java.awt.Graphics2D;
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
		mapImg = buildMap(gameState.getMap());
		
		
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
		mapImg = new BufferedImage((int)((maxX-minX)/Map.TILESIZE*scale)+2,
				(int)((maxY-minY)/Map.TILESIZE*scale)+2, BufferedImage.TYPE_4BYTE_ABGR);	
		xOffset=minX/Map.TILESIZE;
		yOffset=minY/Map.TILESIZE;
		
		Graphics2D g = mapImg.createGraphics();
		drawRooms(mapImg,g, map, minX, minY);
		if(gameState.getSave().hasBinoculars()) {
			drawCollectibles(mapImg, g, map, minX, minY);
		}
		for(RoomExit e:roomExits(map)) {
			
			int x=(int) Math.round((e.x-minX)/Map.TILESIZE*scale);
			int y=(int)Math.round((e.y-minY)/Map.TILESIZE*scale);
			mapImg.setRGB(x,y,Color.white.getRGB());		
			
		}
		drawPlayer(mapImg, g, map, minX, minY);
		return mapImg;
	}
	private void drawRooms(BufferedImage img,Graphics2D g, Map map, int minX, int minY) {
		for(int i:openedRooms) {
			Room room = map.getAllRooms().get(i);
			int roomX= (int) Math.round((room.getX()-minX)/Map.TILESIZE*scale);
			int roomY= (int) Math.round((room.getY()-minY)/Map.TILESIZE*scale);
			if(room.getArea()==1){
				g.setColor(new Color(16,80,61));
			}if(room.getArea()==2){
				g.setColor(new Color(0,63,148));
			}if(room.getArea()==3){
				g.setColor(new Color(113,134,150));
			}if(room.getArea()==4){
				g.setColor(new Color(20,166,34));
			}
			
			g.fillRect(roomX,roomY,(int) Math.round(room.getWidth()/Map.TILESIZE*scale),
					(int) Math.round(room.getHeight()/Map.TILESIZE*scale));
			g.setColor(Color.black);
			g.drawRect(roomX,roomY,(int) Math.round(room.getWidth()/Map.TILESIZE*scale),
					(int) Math.round(room.getHeight()/Map.TILESIZE*scale));
		}
		
	}
	
	private ArrayList<RoomExit> roomExits(Map map) {
		ArrayList<RoomExit> exits = new ArrayList<>();
		
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
	
	private void drawCollectibles(BufferedImage img, Graphics2D g, Map map,int minX, int minY) {
		for(Room r:gameState.getMap().getAllRooms()) {
			if(r.hasCollectible()) {
				g.setColor(Color.white);
				int roomX= (int) Math.round((r.getX()-minX+r.getWidth()/2)/Map.TILESIZE*scale);
				int roomY= (int) Math.round((r.getY()-minY+r.getHeight()/2)/Map.TILESIZE*scale);
				g.drawRect(roomX-2,roomY-2,3,3);
			}
				
		}
	}
		
	private void drawPlayer(BufferedImage img, Graphics2D g, Map map,int minX, int minY) {
		for(Entity i :Entity.getManager().getEntities()) {
			if(i instanceof Player) {
				g.setColor(Color.red);
				int x = (int) Math.round((i.getBounds().x+i.getBounds().width/2-minX)/Map.TILESIZE*scale);	
				int y = (int) Math.round((i.getBounds().y+i.getBounds().height/2-minY)/Map.TILESIZE*scale);	

				g.drawRect(x-3,y-3,5,5);
			}
		}
		
	}
	@Override
	public void update() { 
		mapImg = buildMap(gameState.getMap());
		if(Inputs.jump().isHeld()&&scale<1-scale/20) {
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
		int mapDrawX=Game.WIDTH/2+mapX;
		int mapDrawY=Game.HEIGHT/2+mapY;
		g.setColor(new Color(78,16,69));
		g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
		
		g.drawImage(mapImg,mapDrawX-mapImg.getWidth()/2,mapDrawY-mapImg.getHeight()/2, null);
		
		//drawing collectible icons
		
		
		
		
		
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
