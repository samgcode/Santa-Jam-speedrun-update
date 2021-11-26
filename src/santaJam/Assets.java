package santaJam;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Assets {
	public static Font font = loadFont("res/monofont.ttf");
	
	public static BufferedImage[] tiles = splitSpriteSheet(loadImage("celesteTiles.png"), 8, 8, 7, 9);

	public static BufferedImage[] walking = splitSpriteSheet(loadImage("walking.png"), 9, 13, 8, 1);
	public static BufferedImage[] jumping = splitSpriteSheet(loadImage("jumping.png"), 11, 12, 6, 1);
	
	
	
	private static BufferedImage[]  splitSpriteSheet(BufferedImage sheet, int width, int height, int rows, int columns) {
		//this takes on image and splits it into an array of several smaller photos so we 
		//only need to load a few big spritesheet instead of a million single images
		BufferedImage[] pics=new BufferedImage[rows*columns];//creating the array
		for(int y=0;y<columns;y++) {//looping through the image vertically
			for(int x=0;x<rows;x++) {//looping horizontally
				pics[(y*rows)+x]=sheet.getSubimage(x*width, y*height, width, height);
				
				//spliting the image and putting it in the array
			}
		}
		return pics;//returning the array
	}
	private static BufferedImage loadImage(String path) {
		String folderPath = "res/textures/";
		/*
		 * this method loads a image from a String so you don't need to put 
		 * it in a try catch every time
		 */
		BufferedImage image = null;
		try { //this can throw an error so it needs to be in a try catch to run
			image =ImageIO.read(new File(folderPath+path));//loading the image
		} catch (IOException e) {
			System.out.println("picture "+folderPath+path+" not found");//showing what the problem is
		}
		return image;
	}
	private static Font loadFont(String path) {
		Font font=null;
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, new File(path)).deriveFont(16f);
		} catch (FontFormatException | IOException e) {
			System.out.println("font at path "+path+" not found");
			e.printStackTrace();
		}
		if(font!=null) {
			System.out.println("font loaded");
		}
		return font;
	}

}
