package santaJam.components;

import santaJam.maps.Room;
import santaJam.saves.Save;

public class Split {
  public int time;
  public int collectibles;
  public String room;
  public boolean slide;
  public boolean grapple;
  public boolean doubleJump;
  public boolean upBoost;
  public boolean binoculars;

  public Split(String room, Save save, int time) {
    this.collectibles = save.getCollectibles().length;
    this.room = room;
    this.slide = save.hasSlide();
    this.grapple = save.hasGrapple();
    this.doubleJump = save.hasDoubleJump();
    this.upBoost = save.hasUpBoost();
    this.binoculars = save.hasBinoculars();
    this.time = time;
  }
  public Split(Split split) {
    this.collectibles = split.collectibles;
    this.room = split.room;
    this.slide = split.slide;
    this.grapple = split.grapple;
    this.doubleJump = split.doubleJump;
    this.upBoost = split.upBoost;
    this.binoculars = split.binoculars;
    this.time = split.time;
  }
  public Split(String room, Save save) {
    this(room, save, -1);
  }
  public Split(Room room, Save save) {
    this(room.getName(), save, -1);
  }
  public Split(Room room, Save save, int time) {
    this(room.getName(), save, time);
  }


  public Split(String split) {
    String[] data = split.split(", ");
    this.room =                          data[0].substring(3);
    this.collectibles = Integer.parseInt(data[1].substring(3));
    this.slide =         Boolean.valueOf(data[2].substring(3));
    this.grapple =       Boolean.valueOf(data[3].substring(3));
    this.doubleJump =    Boolean.valueOf(data[4].substring(3));
    this.upBoost =       Boolean.valueOf(data[5].substring(3));
    this.binoculars =    Boolean.valueOf(data[6].substring(3));
    this.time =         Integer.parseInt(data[7].substring(3));
  }

  public boolean matches(String room, Save save) {
    return collectibles == save.getCollectibles().length
      && this.room.equals(room)
      && slide == save.hasSlide()
      && grapple == save.hasGrapple()
      && doubleJump == save.hasDoubleJump()
      && upBoost == save.hasUpBoost()
      && binoculars == save.hasBinoculars()
    ;
  }

public void setTime(int time) {
  this.time = time;
}

  @Override
  public String toString() {
    return "r: " + room
      + ", c: " + collectibles
      + ", s: " + slide
      + ", g: " + grapple
      + ", d: " + doubleJump
      + ", u: " + upBoost
      + ", b: " + binoculars
      + ", t: " + time
    ;
  }
}
