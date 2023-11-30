package santaJam.inputs;

public enum Keybind {
  UP("up", 0, false),
  DOWN("down", 1, false),
  LEFT("left", 2, false),
  RIGHT("right", 3, false),
  JUMP("jump", 4, false),
  GRAPPLE("grapple", 5, false),
  PAUSE("pause", 6, false),
  SAVE_STATE("save state", 7, true),
  RESET("reset", 8, false),
  FULL_RESET("hard reset", 9, true);


  public String name;
  public int index;
  public boolean speedrun;

  Keybind(String name, int index, boolean speedrun) {
    this.name = name;
    this.index = index;
    this.speedrun = speedrun;
  }
}
