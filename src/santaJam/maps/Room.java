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

import santaJam.entities.Entity;
import santaJam.entities.SaveStatue;
import santaJam.entities.upgrades.DoubleJumpItem;
import santaJam.entities.upgrades.GrappleItem;
import santaJam.states.Camera;
import santaJam.states.StateManager;

public class Room {
	private int x,y,width, height;
	private int[][] tiles;
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
				if(tiles[tileX][tileY]==Map.WALLTILE) {
					walls.add(new Rectangle(x+tileX*Map.TILESIZE,y+tileY*Map.TILESIZE,Map.TILESIZE,Map.TILESIZE));
				}
			}
		}
		
		
	}
	
	
	public void render(Graphics2D g, Camera camera) {
		
		for(int y=0;y<height;y++) {
			for(int x=0;x<width;x++) {
				if(checkWall(x, y)) {
					g.setColor(new Color(43,173,50));
					
				}else if((Math.round(x/5)%2==0&&Math.round(y/5)%2==1)||(Math.round(x/5)%2==1&&Math.round(y/5)%2==0)) {
					g.setColor(new Color(57,11,50));
				}else {
					g.setColor(new Color(78,16,69));
				}
				g.fillRect(this.x+x*Map.TILESIZE-camera.getxOffset(),this.y+y*Map.TILESIZE-camera.getyOffset(), Map.TILESIZE,Map.TILESIZE);
					
			}
		}
		
	}
	
	public void loadRoom() {
		for(int y=0;y<height;y++) {
			for(int x=0;x<width;x++) {
				if(tiles[x][y]==Map.ENEMYTILE) {
					//Entity.getManager().addEntity(new Enemy(this.x+x*Map.TILESIZE ,this.y+y*Map.TILESIZE));
				}else if(tiles[x][y]==Map.DOUBLEJUMP&&!StateManager.getGameState().getSave().hasDoubleJump()) {
					Entity.getManager().addEntity(new DoubleJumpItem(this.x+x*Map.TILESIZE ,this.y+y*Map.TILESIZE));
				}else if(tiles[x][y]==Map.GRAPPLE&&!StateManager.getGameState().getSave().hasGrapple()) {
					Entity.getManager().addEntity(new GrappleItem(this.x+x*Map.TILESIZE ,this.y+y*Map.TILESIZE));
				}else if(tiles[x][y]==Map.SAVEPOINT) {
					Entity.getManager().addEntity(new SaveStatue(this.x+x*Map.TILESIZE ,this.y+y*Map.TILESIZE));
				}
				
			}
		}
	}
	
	
	private boolean checkWall(int x, int y){
		try {
			if(tiles[x][y]!=Map.WALLTILE) {
				return false;
			}
		} catch (IndexOutOfBoundsException e) {	
			
		}
		return true;
	}
	
	
	public ArrayList<Rectangle> getWalls() {
		return walls;
	}
	public boolean inBounds(Rectangle bounds) {
		if(new Rectangle(this.x,this.y,getWidth(),getHeight()).intersects(bounds)) {
			return true;
		}
		return false;
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
	
	
	

}
