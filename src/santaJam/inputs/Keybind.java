package santaJam.inputs;

import java.awt.event.KeyEvent;

public enum Keybind {
  UP        ("up",        0, false,true, 38),
  DOWN      ("down",      1, false,true, 40),
  LEFT      ("left",      2, false,true, 37),
  RIGHT     ("right",     3, false,true, 39),
  JUMP      ("jump",      4, false,true, 67),
  GRAPPLE   ("grapple",   5, false,true, 88),
  SAVE_STATE("save state",6, true, true, 65),
  LOAD_STATE("load state",7, true, true, 83),
  RESET     ("reset",     8, false,true, 32),
  FULL_RESET("hard reset",9, true, true, 82),
  ENTER     ("enter",     10,false,false,10),
  PAUSE     ("pause",     11,false,false, KeyEvent.VK_ESCAPE),
  DEBUG     ("debug",     12,true, false, KeyEvent.getExtendedKeyCodeForChar('`')),
  FRAME_ADV ("",          13,true, false, KeyEvent.getExtendedKeyCodeForChar('=')),
  FA_PLAY   ("",          14,true, false, KeyEvent.getExtendedKeyCodeForChar('-')),
  ;

  public String name;
  public int index;
  public boolean speedrun;
  public boolean bindable;
  public int default_bind;

  Keybind(String n, int i, boolean s, boolean b, int db) {
    this.name = n;
    this.index = i;
    this.speedrun = s;
    this.bindable = b;
    this.default_bind = db;
  }
}
