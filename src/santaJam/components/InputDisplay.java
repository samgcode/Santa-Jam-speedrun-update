package santaJam.components;

import java.awt.Color;
import java.util.HashMap;

import santaJam.graphics.UI.RectElement;
import santaJam.graphics.UI.UIElement;
import santaJam.inputs.Inputs;
import santaJam.inputs.Keybind;

public class InputDisplay {
  int size = 15;
  int offset = 2;
  int y = 180-(size+offset);
  int x = 320-(3*size+4*offset);

  Color activeColor = new Color(0,150,255);
  Color deactiveColor = new Color(0, 255, 150);

	RectElement left = new RectElement(x+0,y+0,size,size, deactiveColor);
	RectElement right = new RectElement(x+2*size+2*offset,y+0,size,size, deactiveColor);
	RectElement down = new RectElement(x+1*size+1*offset,y+0,size,size, deactiveColor);
	RectElement up = new RectElement(x+1*size+1*offset,y-size-offset,size,size, deactiveColor);
	RectElement jump = new RectElement(x+2*size+(int)(2.5*offset), y-size-(int)(0.5*offset), size-offset, size-offset, deactiveColor);
	RectElement grapple = new RectElement(x+(int)(0.5*offset), y-size-(int)(0.5*offset), size-offset, size-offset, deactiveColor);
  
  HashMap<Keybind, RectElement> displays = new HashMap<Keybind, RectElement>() {{
    put(Keybind.LEFT, left);
    put(Keybind.RIGHT, right);
    put(Keybind.UP, up);
    put(Keybind.DOWN, down);
    put(Keybind.JUMP, jump);
    put(Keybind.GRAPPLE, grapple);
  }};

  public void show() {
    for (Keybind keybind : displays.keySet()) {
      UIElement.getUIManager().addElement(displays.get(keybind));
    }
  }

  public void update() {
    for (Keybind keybind : displays.keySet()) {
      if(Inputs.getKey(keybind).isHeld()) {
        displays.get(keybind).colour = activeColor;
      } else {
        displays.get(keybind).colour = deactiveColor;
      }
    }
  }
}
