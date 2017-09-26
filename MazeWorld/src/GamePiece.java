// Assignment 10  -  part 2
// Kim Austin
// KimA
// Pine Michelle
// pinemichelle63

import java.awt.Color;
import java.util.*;

import javalib.worldimages.*;


//to represent a player
class Player {
  int x;
  int y;
  WorldImage img;

  Player(int x, int y) {
    this.x = x;
    this.y = y;
    this.img = new EllipseImage(MazeWorld.SCALE, MazeWorld.SCALE, 
            OutlineMode.SOLID, Color.YELLOW);
  }

  //to display the player
  WorldImage display() {
    return new EllipseImage(MazeWorld.SCALE, MazeWorld.SCALE, 
        OutlineMode.SOLID, Color.YELLOW);
  }

  //EFFECT: moves the x and y coordinate of the player
  public void move(String key, ArrayList<Edge> tree) {
    Vertex here = new Vertex(
        new MyPosn(this.x, this.y));
    Utils u = new Utils();
    if (key.equals("right")) {
      Vertex other = new Vertex(
          new MyPosn(this.x + 1, this.y));
      if (u.findEdge(tree, here, other)) {
        this.x = this.x + 1;
      }
    }
    else if (key.equals("left")) {
      Vertex other = new Vertex(
          new MyPosn(this.x - 1, this.y));
      if (u.findEdge(tree, here, other)) {
        this.x = this.x - 1;
      }
    }
    else if (key.equals("down")) {
      Vertex other = new Vertex(
          new MyPosn(this.x, this.y + 1));
      if (u.findEdge(tree, here, other)) {
        this.y = this.y + 1;
      }
    }
    else if (key.equals("up")) {
      Vertex other = new Vertex(
          new MyPosn(this.x, this.y - 1));
      if (u.findEdge(tree, here, other)) {
        this.y = this.y - 1;
      }
    }

  }

}
