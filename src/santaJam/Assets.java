package santaJam;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Assets {
	public static Font font = loadFont("res/monofont.ttf",16);
	public static Font bigFont = loadFont("res/setback-tt-brk.ttf",13);
	
	public static BufferedImage[] tiles = splitSpriteSheet(loadImage("tileset.png"), 8, 8, 12, 37);

	public static BufferedImage[] walking = splitSpriteSheet(loadImage("walking.png"), 9, 13, 8, 1);
	public static BufferedImage[] landing = splitSpriteSheet(loadImage("landing.png"), 7, 12, 3, 1);
	public static BufferedImage[] jumping = splitSpriteSheet(loadImage("jumping.png"), 11, 12, 3, 1);
	public static BufferedImage[] doubleJump = splitSpriteSheet(loadImage("double jump.png"), 11, 12, 2, 1);
	public static BufferedImage[] falling = new BufferedImage[] {loadImage("falling.png")};
	
	public static BufferedImage[] sliding = new BufferedImage[] {loadImage("slide.png")};
	public static BufferedImage[] slideFall = new BufferedImage[] {loadImage("slide fall.png")};
	
	public static BufferedImage[] grappleThrow = splitSpriteSheet(loadImage("grapple throw.png"), 13,15, 6, 1);
	public static BufferedImage[] grapplePull = splitSpriteSheet(loadImage("grapple pull.png"), 12, 12, 3, 1);
	public static BufferedImage grappleHook= loadImage("grapple hook.png");
	
	public static BufferedImage[] dance= splitSpriteSheet(loadImage("dance.png"), 11, 13, 4, 1);
	public static BufferedImage[] boostStart= splitSpriteSheet(loadImage("boost start.png"), 14,15, 7, 1);
	public static BufferedImage[] boost = splitSpriteSheet(loadImage("boost.png"), 10,16, 4, 1);
	
	public static BufferedImage[] slideIcon = splitSpriteSheet(loadImage("slide icon.png"), 10,10, 16, 1);
	public static BufferedImage[] grappleIcon = splitSpriteSheet(loadImage("grapple icon.png"), 10,10, 16, 1);
	public static BufferedImage[] doubleJumpIcon = splitSpriteSheet(loadImage("double jump icon.png"), 10,10, 16, 1);
	public static BufferedImage[] boostIcon = splitSpriteSheet(loadImage("boost icon.png"), 10,10, 16, 1);
	public static BufferedImage[] binoculars = splitSpriteSheet(loadImage("boost icon.png"), 10,10, 16, 1);
	
	
	public static BufferedImage[] marshmallow = splitSpriteSheet(loadImage("marshmallow.png"), 8,10, 8, 1);
	public static BufferedImage[] milk = splitSpriteSheet(loadImage("milk.png"), 8,8, 8, 1);
	public static BufferedImage[] chocolate = splitSpriteSheet(loadImage("choccy.png"),8,8,5,1);
	
	public static BufferedImage[] icicle = splitSpriteSheet(loadImage("icicle.png"), 8,16, 4, 1);
	public static BufferedImage[] iceParticle = splitSpriteSheet(loadImage("ice particles.png"), 4,4, 4, 1);
	public static BufferedImage[] iceWall = splitSpriteSheet(loadImage("ice wall.png"), 8,8, 3, 2);
	public static BufferedImage[] spikeUp = splitSpriteSheet(loadImage("spike u.png"), 8,4, 6, 1);
	public static BufferedImage[] spikeDown = splitSpriteSheet(loadImage("spike d.png"), 8,4, 6, 1);
	public static BufferedImage[] spikeLeft = splitSpriteSheet(loadImage("spike l.png"), 4,8, 6, 1);
	public static BufferedImage[] spikeRight = splitSpriteSheet(loadImage("spike r.png"), 4,8, 6, 1);
	public static BufferedImage[] bounceUp = splitSpriteSheet(loadImage("bounce pad u.png"), 8,7, 6, 1);
	public static BufferedImage[] bounceLeft = splitSpriteSheet(loadImage("bounce pad l.png"), 7,8, 6, 1);
	public static BufferedImage[] bounceRight = splitSpriteSheet(loadImage("bounce pad r.png"), 7,8, 6, 1);
	public static BufferedImage grappplePoint = loadImage("grapple point.png");
	
	
	public static BufferedImage marchmallowIcon= loadImage("ui/marshmallow icon.png");
	public static BufferedImage chocolateIcon= loadImage("ui/chocolate icon.png");
	public static BufferedImage milkIcon= loadImage("ui/milk icon.png");
	public static BufferedImage playerIcon= loadImage("ui/player icon.png");
	
	public static BufferedImage chair= loadImage("chair.png");

	public static BufferedImage upgradeTop= loadImage("ui/upgrade top.png");
	public static BufferedImage upgradeBottom= loadImage("ui/upgrade bottom.png");
	
	public static BufferedImage menuMarker= loadImage("ui/menu marker.png");
	public static BufferedImage menuMarkerGreen= loadImage("ui/selection green.png");

	public static BufferedImage titleScreen= loadImage("ui/title.png");
	public static BufferedImage mainMenuScreen= loadImage("ui/main menu.png");
	public static BufferedImage pauseScreen= loadImage("ui/pause.png");
	public static BufferedImage mapScreen= loadImage("ui/map.png");
	public static BufferedImage settingsScreen= loadImage("ui/settings.png");
	
	
	
	
	
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
	private static Font loadFont(String path, float size) {
		Font font=null;
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, new File(path)).deriveFont(size);
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
