package santaJam.graphics.UI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

import santaJam.Assets;

public class TextElement extends UIElement{
	/*
	 * this class is for parts of ui that will have text such as how much money you have
	 */
	public static final int SMALLMONOWIDTH=4,SMALLMONOHEIGHT=7,BIGMONOWIDTH=6,BIGMONOHEIGHT=7;
	private Font font=Assets.font;
	private int width,charWidth=SMALLMONOWIDTH,height, lineHeight=SMALLMONOHEIGHT+1;
	private Color color;
	String[] lines= new String[1];
	String text;//only really used to return the text back if it is needed
	
	//this constructor takes the location and value of the text and uses the default font
	public TextElement(int x, int y, String text) {
		super(x, y);
		this.color = new Color(250, 250, 250);
		update(text);//adding line breaks if there is a new line in the text
	}
	
	public TextElement(int x, int y, int charWidth, int lineHeight,  String text) {
		this(x,y,text);
		this.lineHeight=lineHeight;
		this.charWidth=charWidth;
		
	}
	public TextElement(boolean pauseGame, int x, int y, int charWidth, int lineHeight, int maxTextWidth, String text, Font font) {
		this(x,y,text);
		this.lineHeight=lineHeight;
		this.charWidth=charWidth;
		this.font=font;
		this.pauseGame=pauseGame;
		
		update(text, maxTextWidth);
		
	}
	//this constructor lets you specify which font you want to use
	public TextElement(int x, int y, String text, Font font) {
		this(x,y,text);
		this.font=font;
	}

	public void setOpacity(int opacity) {
		this.color = new Color(
			color.getRed(),
			color.getGreen(),
			color.getBlue(),
			opacity
		);
	}

	public void setColor(Color color) {
		this.color = color;
	}
	
	
	@Override
	public void render(Graphics g) {
		g.setColor(color);//we could have a variable to change font colours later but its fine for now
		g.setFont(font);//setting the font to the right font
		for(int i=0;i<lines.length;i++) {
			g.drawString(lines[i].toUpperCase(), x,y+(lineHeight*(i+1)));
			
		}
	//	g.drawRect(Game.WIDTH/2,Game.HEIGHT/2,1,1);
	//	g.drawRect(x,y,width,height);

		
	}

	/**
	 * lets you change the text to say something different
	 * @apiNote ui elements do not need to be updated every frame
	 * @param text - the new text that should be displayed. "\n" can be used to create line breaks
	 */
	public void update(String text) {
		lines=text.split("\n");//adding line breaks if there is a new line in the text
		this.text=text;
		measureText();
		
	}
	
	
	/**
	 * 
	 * lets you change the text to say something different, but makes sure that the text will not go past a set width
	 * @apiNote only works with monospace fonts
	 * @apiNote a \n needs to be its own word like "a \n b" in order to work
	 * @apiNote ui elements do not need to be updated every frame
	 * @param text
	 * @param maxWidth
	 */
	public void update(String text, int maxWidth) {
		this.text=text;
		String[] words=text.split(" ");
		ArrayList<String> lines = new ArrayList<String>();
		
		int currentWidth=0, lineIndex=0;
		lines.add("");
		for(String i:words) {
			if(currentWidth+(i.length()*charWidth)>maxWidth) {
				lines.set(lineIndex, lines.get(lineIndex).substring(0,lines.get(lineIndex).length()-1));
				lines.add("");
				lineIndex++;
				currentWidth=0;
			}
			if(i.equals("\n")) {
				if(lines.get(lineIndex).length()>0) {
					lines.set(lineIndex, lines.get(lineIndex).substring(0,lines.get(lineIndex).length()-1));
				}
				lines.add("");
				lineIndex++;
				currentWidth=0;
			}else {
				lines.set(lineIndex, lines.get(lineIndex)+i+" ");
				currentWidth+=(i.length()+1)*charWidth;
			}
		}
		lines.set(lineIndex, lines.get(lineIndex).substring(0,lines.get(lineIndex).length()-1));
		this.lines=new String[lines.size()];
		this.lines=(String[])lines.toArray(this.lines);	
		measureText();
	}
	private void measureText() {
		height=lines.length*lineHeight;
		int widthChars=0;
		for(String i:lines) {
			if(i.length()>widthChars) {
				widthChars=i.length();
			}
		}
		width=widthChars*charWidth;
		
	}
	
	
	
	/**
	 * adds spaces to each line so that all lines are centered horizontally
	 * only works with monospace fonts
	 */
	public void centre(int lineWidth) {
		int widthChars = lineWidth/charWidth;
		for(int i=0;i<lines.length;i++) {
			int charsNeeded=(widthChars-lines[i].length())/2;
			for(int j=0;j<charsNeeded;j++) {
				lines[i]=" "+lines[i];
			}
		}
		
	}
	
	//getters/setters
	public int getHeight() {
		return height;
	}
	/**
	 * 
	 * @return - width of the string
	 * only works with monospace fonts
	 */
	public int getWidth() {
		return width;
	}
	
	public String getText() {
		return text;
	}
}
