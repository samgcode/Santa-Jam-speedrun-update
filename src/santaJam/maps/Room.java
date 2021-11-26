package santaJam.maps;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import santaJam.Assets;
import santaJam.entities.BouncePad;
import santaJam.entities.Entity;
import santaJam.entities.GrapplePoint;
import santaJam.entities.Icicle;
import santaJam.entities.SaveStatue;
import santaJam.entities.SpikeSubstitute;
import santaJam.entities.upgrades.Collectible;
import santaJam.entities.upgrades.DoubleJumpItem;
import santaJam.entities.upgrades.GrappleItem;
import santaJam.entities.upgrades.SlideItem;
import santaJam.entities.upgrades.UpBoostItem;
import santaJam.entities.wallEntities.BreakableWall;
import santaJam.graphics.particles.movers.Straight;
import santaJam.graphics.particles.shapes.OvalParticle;
import santaJam.graphics.particles.shapes.colourers.FadeOut;
import santaJam.graphics.particles.shapes.colourers.RandFadeOut;
import santaJam.graphics.particles.spawners.EvenRectSpawn;
import santaJam.states.Camera;
import santaJam.states.StateManager;

public class Room {
	private EvenRectSpawn particles;
	
	private int area;
	private int x,y,width, height;
	private int[][] tiles;
	
	private ArrayList<Rectangle> smoothWalls = new ArrayList<Rectangle>();
	private ArrayList<Rectangle> walls = new ArrayList<Rectangle>();
	String name;
	
	
	
	
	
	public Room(int x, int y, String path) {
		this.name=path;
		this.x=x;
		this.y=y;
		JSONObject file;
		try {
			file=(JSONObject)(new JSONParser().parse(new FileReader(path)));
		} catch (IOException | ParseException e) {
			System.out.print("there was a problem loading JSON file at "+path );
			e.printStackTrace();
			tiles=new int[][] {{}};
			return;
			
		}
		JSONArray layers=(JSONArray)file.get("layers");//the array of layers
		
		//getting map data
		JSONObject mapLayer=(JSONObject) layers.get(0);
		JSONArray mapData=(JSONArray)mapLayer.get("data");
		width=(int)((long)file.get("width"));
		height=(int)((long)file.get("height"));
		tiles = new int[width][height];
		for(int tileY=0;tileY<height;tileY++) {
			for(int tileX=0;tileX<width;tileX++) {
				tiles[tileX][tileY] =(int)((long) mapData.get((tileY *width) + tileX ));
				if(Map.wallTile(tiles[tileX][tileY])) {
					walls.add(new Rectangle(x+tileX*Map.TILESIZE,y+tileY*Map.TILESIZE,Map.TILESIZE,Map.TILESIZE));
				}if(Map.smoothTile(tiles[tileX][tileY])) {
					Rectangle rect = new Rectangle(x+tileX*Map.TILESIZE,y+tileY*Map.TILESIZE,Map.TILESIZE,Map.TILESIZE);
					walls.add(rect);
					smoothWalls.add(rect);
				}
			}
		}
		
		JSONArray properties=(JSONArray)file.get("properties");
		JSONObject areaObject=(JSONObject)properties.get(0);
		area=(int)((long)areaObject.get("value"));
		
		if(area==1) {
			particles = new EvenRectSpawn(0.0015,x-50,y-50, width*Map.TILESIZE+100,
					height*Map.TILESIZE+100,new Straight(0,0,90, 30, 0.5),new OvalParticle(3,new FadeOut(1)),true);
		}else if(area==2) {
			Color[] colors = new Color[] {
					new Color(0,225,227),new Color(0,90,100),new Color(225,200,0),Color.white,
			};
			particles = new EvenRectSpawn(0.0005,x-50,y-50, width*Map.TILESIZE+100,
					height*Map.TILESIZE+100,new Straight(0,0, 0.25),new OvalParticle(1,new RandFadeOut(colors,1.5)),true);
		}else if(area==3) {
			particles = new EvenRectSpawn(0.01,x-150,y-Map.TILESIZE-50, width*Map.TILESIZE,
					height*Map.TILESIZE+50,new Straight(0,0,15, 5, 5),new OvalParticle(2,new FadeOut(1)),true);
		}
		
		
		
	}
	public void update() {
		if(particles!=null) {
			particles.update();
		}
		
	}
	
	public void render(Graphics2D g, Camera camera) {
		
		for(int y=0;y<height;y++) {
			for(int x=0;x<width;x++) {
				int tile=tiles[x][y]-Map.TileSetStart;
				if(tile>=0&&tile<Assets.tiles.length) {
					g.drawImage(Assets.tiles[tile],this.x+x*Map.TILESIZE-camera.getxOffset(),this.y+y*Map.TILESIZE-camera.getyOffset(),null);
					
				}else if((Math.round(x/5)%2==0&&Math.round(y/5)%2==1)||(Math.round(x/5)%2==1&&Math.round(y/5)%2==0)) {
					g.setColor(new Color(57,11,50));
				}else {
					g.setColor(new Color(78,16,69));
				}
				//g.fillRect(this.x+x*Map.TILESIZE-camera.getxOffset(),this.y+y*Map.TILESIZE-camera.getyOffset(), Map.TILESIZE,Map.TILESIZE);
					
			}
		}	
		
		//for(Rectangle r:walls) {
			//g.drawRect(r.x-camera.getxOffset(), r.y-camera.getyOffset(), r.width, r.height);
		//}
	}
	
	public void loadRoom() {
		System.out.println("loading "+name);
		if(particles!=null) {
			particles.start();
		}
		boolean collected = false;
		for(String i:StateManager.getGameState().getSave().getCollectibles()) {
			if(i.equals(name)) {
				collected=true;
			}
		}
		//this is probably the wrong way to do this but idk
		for(int y=0;y<height;y++) {
			for(int x=0;x<width;x++) {
				if(tiles[x][y]==Map.LEFTBOUNCE) {
					Entity.getManager().addEntity(new BouncePad(this.x+x*Map.TILESIZE ,this.y+y*Map.TILESIZE,'l'));
				}else if(tiles[x][y]==Map.RIGHTBOUNCE) {
					Entity.getManager().addEntity(new BouncePad(this.x+x*Map.TILESIZE ,this.y+y*Map.TILESIZE,'r'));
				}else if(tiles[x][y]==Map.UPBOUNCE) {
					Entity.getManager().addEntity(new BouncePad(this.x+x*Map.TILESIZE ,this.y+y*Map.TILESIZE,'u'));
				}
				else if(tiles[x][y]==Map.LEFTSPIKE) {
					Entity.getManager().addEntity(new SpikeSubstitute(this.x+x*Map.TILESIZE ,this.y+y*Map.TILESIZE,'l'));
				}else if(tiles[x][y]==Map.RIGHTSPIKE) {
					Entity.getManager().addEntity(new SpikeSubstitute(this.x+x*Map.TILESIZE ,this.y+y*Map.TILESIZE,'r'));
				}else if(tiles[x][y]==Map.UPSPIKE) {
					Entity.getManager().addEntity(new SpikeSubstitute(this.x+x*Map.TILESIZE ,this.y+y*Map.TILESIZE,'u'));
				}else if(tiles[x][y]==Map.DOWNSPIKE) {
					Entity.getManager().addEntity(new SpikeSubstitute(this.x+x*Map.TILESIZE ,this.y+y*Map.TILESIZE,'d'));
				}else if(tiles[x][y]==Map.ICICLE) {
					Entity.getManager().addEntity(new Icicle(this.x+x*Map.TILESIZE ,this.y+y*Map.TILESIZE));
				}
				else if(tiles[x][y]==Map.SLIDE&&!StateManager.getGameState().getSave().hasSlide()) {
					Entity.getManager().addEntity(new SlideItem(this.x+x*Map.TILESIZE ,this.y+y*Map.TILESIZE));
				}else if(tiles[x][y]==Map.DOUBLEJUMP&&!StateManager.getGameState().getSave().hasDoubleJump()) {
					Entity.getManager().addEntity(new DoubleJumpItem(this.x+x*Map.TILESIZE ,this.y+y*Map.TILESIZE));
				}else if(tiles[x][y]==Map.GRAPPLE&&!StateManager.getGameState().getSave().hasGrapple()) {
					Entity.getManager().addEntity(new GrappleItem(this.x+x*Map.TILESIZE ,this.y+y*Map.TILESIZE));
				}else if(tiles[x][y]==Map.UPBOOST&&!StateManager.getGameState().getSave().hasUpBoost()) {
					Entity.getManager().addEntity(new UpBoostItem(this.x+x*Map.TILESIZE ,this.y+y*Map.TILESIZE));
				}
				else if(tiles[x][y]==Map.SAVEPOINT) {
					Entity.getManager().addEntity(new SaveStatue(this.x+x*Map.TILESIZE ,this.y+y*Map.TILESIZE));
				}else if(tiles[x][y]==Map.GRAPPLEPOINT) {
					Entity.getManager().addEntity(new GrapplePoint(this.x+x*Map.TILESIZE+Map.TILESIZE/2,this.y+y*Map.TILESIZE+Map.TILESIZE/2));
				}else if(tiles[x][y]==Map.SMASHWALL) {
					Entity.getManager().addEntity(new BreakableWall(this.x+x*Map.TILESIZE ,this.y+y*Map.TILESIZE));
				}
				
				if(!collected) {
					if(tiles[x][y]==Map.MILK) {
						Entity.getManager().addEntity(new Collectible(this.x+x*Map.TILESIZE ,this.y+y*Map.TILESIZE,Collectible.MILK));
					}else if(tiles[x][y]==Map.MARSHMELLOW) {
						Entity.getManager().addEntity(new Collectible(this.x+x*Map.TILESIZE ,this.y+y*Map.TILESIZE,Collectible.MARSHMELLOW));
					}else if(tiles[x][y]==Map.CHOCOLATE) {
						Entity.getManager().addEntity(new Collectible(this.x+x*Map.TILESIZE ,this.y+y*Map.TILESIZE,Collectible.CHOCOLATE));			
					}
				}
			}
		}
	}
	public void unload() {		
	}
	
	
	
	public boolean isWall(int x, int y){
		try {
			if(Map.wallTile(tiles[x][y])||Map.smoothTile(tiles[x][y])) {
				return true;
			}
		} catch (IndexOutOfBoundsException e) {	
			
		}
		return false;
	}
	public boolean isIceBlock(int x, int y){
		try {
			if(tiles[x][y]==Map.SMASHWALL) {
				return true;
			}
		} catch (IndexOutOfBoundsException e) {	
			
		}
		return false;
	}
	
	public ArrayList<Rectangle> getWalls() {
		return walls;
	}
	public ArrayList<Rectangle> getSmoothWalls() {
		return smoothWalls;
	}
	
	public Rectangle getBounds() {
		return new Rectangle(this.x,this.y,getWidth(),getHeight());
	}
	
	public int getWidth() {
		return width*Map.TILESIZE;
	}
	public int getHeight() {
		return height*Map.TILESIZE;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public String getName() {
		return name;
	}
	public int getArea() {
		return area;
	}
	
	
	

}
