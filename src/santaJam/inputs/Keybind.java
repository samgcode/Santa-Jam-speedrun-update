package santaJam.inputs;

import java.awt.event.KeyEvent;

public enum Keybind {
  UP        ("up",        0, false,true, 38),
  DOWN      ("down",      1, false,true, 40),
  LEFT      ("left",      2, false,true, 37),
  RIGHT     ("right",     3, false,true, 39),
  JUMP      ("jump",      4, false,true, 67),
  GRAPPLE   ("grapple",   5, false,true, 88),
  RESET     ("reset",     6, false,true, 32),
  SAVE_STATE("save state",7, true, true, 65),
  LOAD_STATE("load state",8, true, true, 83),
  FULL_RESET("hard reset",9, true, true, 82),
  SPLIT     ("split",     10,true, true, 16),
  ENTER     ("enter",     11,false,false,10),
  PAUSE     ("pause",     12,false,false, KeyEvent.VK_ESCAPE),
  DEBUG     ("debug",     13,true, false, KeyEvent.getExtendedKeyCodeForChar('`')),
  FRAME_ADV ("",          14,true, false, KeyEvent.getExtendedKeyCodeForChar('=')),
  FA_PLAY   ("",          15,true, false, KeyEvent.getExtendedKeyCodeForChar('-')),
  RECORD_SPLITS("enter",  16,false,false,112),
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
