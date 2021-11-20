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

import santaJam.states.Camera;

public class Map {
	public static final int TILESIZE=12;
	private int [][] tiles;
	private int wallTile=1;
	private int width,height;
	private ArrayList<Rectangle> walls = new ArrayList<Rectangle>();
	
	public Map(String path) {
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
		for(int y=0;y<height;y++) {
			for(int x=0;x<width;x++) {
				tiles[x][y] =(int)((long) mapData.get((y *width) + x ));
				if(tiles[x][y]==wallTile) {
					walls.add(new Rectangle(x*TILESIZE,y*TILESIZE,TILESIZE,TILESIZE));
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
				g.fillRect(x*TILESIZE-camera.getxOffset(),y*TILESIZE-camera.getyOffset(), TILESIZE,TILESIZE);
					
			}
		}
		
	}
	
	
	public boolean checkWall(int x, int y){
		
		try {
			if(tiles[x][y]!=wallTile) {
				return false;
			}
		} catch (IndexOutOfBoundsException e) {	
			
		}
		return true;
	}
	public ArrayList<Rectangle> getWalls() {
		return walls;
	}

}
