package santaJam.graphics.UI;

import java.awt.Graphics;
import java.util.ArrayList;

public class UIManager {
	/*
	 * this class holds the UI so it can be rendered after everything else and the 
	 * player or enemies wont run on top of it and should also make it easier to render them.
	 */
	protected ArrayList<UIElement> elements = new ArrayList<UIElement>();
	
	public void addElement(UIElement element) {
		elements.add(element);	
	}
	public void update() {
		for (int i = elements.size()-1; i >= 0; i--) {//looping backwards so we dont skip things when elements are removed
			if(elements.get(i).visible){
				elements.get(i).update();//if it is visible then it should be drawn
			}
			if(elements.get(i).remove) {
				elements.remove(i);//removing the element if remove is true
			}
			
			
		}
	}
	public void render(Graphics g) {
		//rendering all the parts of the UI
		
		for (UIElement i:elements) {//looping backwards so we dont skip things when elements are removed
			if(i.visible){
				i.render(g);//if it is visible then it should be drawn
			}
			
		}
	}
	public void clear() {
		elements.clear();
	}
	public ArrayList<UIElement> getElements() {
		return elements;
	}
}
