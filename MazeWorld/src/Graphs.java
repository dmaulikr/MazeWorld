// Assignment 10  -  part 2
// Kim Austin
// KimA
// Pine Michelle
// pinemichelle63

import java.awt.Color;
import java.util.*;
import javalib.worldimages.*;

//to represent a vertex
class Vertex  {
  MyPosn center;
  boolean travelledOnce = false;
  boolean travelledTwice = false; 
  ArrayList<Edge> outEdges;
  int count;
  boolean showOnce = false;
  boolean showTwice = false; 
  WorldImage img;

  Vertex(MyPosn center)  {
    this.center = center;
    this.outEdges = new ArrayList<Edge>();
    this.count = this.findCount();
    img = this.displayVertex(new ArrayList<Edge>(), true, true);
  }

  Vertex(MyPosn center, int count)  {
    this.center = center;
    this.outEdges = new ArrayList<Edge>();
    this.count = count;
  }

  Vertex(MyPosn center, ArrayList<Edge> outEdges)  {
    this.center = center;
    this.outEdges = outEdges;
  }

  //gives the hashCode of this Vertex
  public int hashCode()  {
    return this.count;
  }

  //determines if an object is equal to this Vertex
  public boolean equals(Object other) {
    if (!(other instanceof Vertex)) {
      return false;
    }
    Vertex that = (Vertex) other;
    return that.center.x == this.center.x
        && that.center.y == this.center.y
        && that.findCount() == this.findCount();
  }

  //finds the count of this vertex
  public int findCount()  {
    return this.center.x + 
        (this.center.y) * (MazeWorld.mWIDTH);
  }

  //displays the vertex
  public WorldImage displayVertex(ArrayList<Edge> edges,
      boolean showFirst, boolean showSecond)  {
    if (this.travelledOnce && this.showOnce && showFirst 
        && !this.showTwice
        || (this.travelledOnce && this.travelledTwice 
            && !showSecond && showFirst && this.showOnce))  {
      return this.makeWalls(edges, 
          new RectangleImage(MazeWorld.SCALE, MazeWorld.SCALE, 
              OutlineMode.SOLID, 
              new Color(102, 153, 255)));
    }
    else if (this.travelledTwice && this.travelledOnce 
        && showSecond && this.showTwice)  {
      return this.makeWalls(edges, 
          new RectangleImage(MazeWorld.SCALE, MazeWorld.SCALE, 
              OutlineMode.SOLID, 
              new Color(0, 102, 255)));
    }
    else  {
      return this.makeWalls(edges, 
          new RectangleImage(MazeWorld.SCALE, MazeWorld.SCALE,
              OutlineMode.SOLID, Color.LIGHT_GRAY));
    }
  }

  //EFFECT: updates the image according to the fields
  void updateImg(ArrayList<Edge> edges,
      boolean showFirst, boolean showSecond) {
    this.img = new FrozenImage(this.displayVertex(edges, 
        showFirst, showSecond));

  }

  //creates the walls of the vertex
  public WorldImage makeWalls(ArrayList<Edge> edges, WorldImage bg)  {
    Vertex other;
    WorldImage img1 = bg;
    for (Edge e: this.outEdges)  {
      if (!edges.contains(e))  {
        if (this.hashCode() == e.to.hashCode())  {
          other = e.from;
        }
        else  {
          other = e.to;
        }
        if (other.center.x > this.center.x)  {
          WorldImage pic = new LineImage(new Posn(0, MazeWorld.SCALE), 
              Color.BLACK);
          img1 = new OverlayOffsetAlign(AlignModeX.RIGHT, 
              AlignModeY.TOP, pic, 0, 0, img1);
        }
        if (other.center.y > this.center.y)  {
          WorldImage pic = new LineImage(new Posn(MazeWorld.SCALE, 0), 
              Color.BLACK);
          img1 = new OverlayOffsetAlign(AlignModeX.LEFT, 
              AlignModeY.BOTTOM, pic, 0, 0, img1);
        }
        if (other.center.x < this.center.x)  {
          WorldImage pic = new LineImage(new Posn(0, MazeWorld.SCALE), 
              Color.BLACK);
          img1 = new OverlayOffsetAlign(AlignModeX.LEFT, 
              AlignModeY.TOP, pic, 0, 0, img1);
        }
        if (other.center.y < this.center.y)  {
          WorldImage pic = new LineImage(new Posn(MazeWorld.SCALE, 0), 
              Color.BLACK);
          img1 = new OverlayOffsetAlign(AlignModeX.LEFT, 
              AlignModeY.TOP, pic, 0, 0, img1);
        }
      }
    }
    if (this.center.y == MazeWorld.mHEIGHT - 1)  {
      WorldImage pic = new LineImage(new Posn(MazeWorld.SCALE, 0), 
          Color.BLACK);
      img1 = new OverlayOffsetAlign(AlignModeX.LEFT, 
          AlignModeY.BOTTOM, pic, 0, 0, img1);
    }
    if (this.center.y == 0)  {
      WorldImage pic = new LineImage(new Posn(MazeWorld.SCALE, 0), 
          Color.BLACK);
      img1 = new OverlayOffsetAlign(AlignModeX.LEFT, 
          AlignModeY.TOP, pic, 0, 0, img1);
    }
    if (this.center.x == 0)  {
      WorldImage pic = new LineImage(new Posn(0, MazeWorld.SCALE), 
          Color.BLACK);
      img1 = new OverlayOffsetAlign(AlignModeX.LEFT, 
          AlignModeY.BOTTOM, pic, 0, 0, img1);
    }
    if (this.center.x == MazeWorld.mWIDTH - 1)  {
      WorldImage pic = new LineImage(new Posn(0, MazeWorld.SCALE), 
          Color.BLACK);
      img1 = new OverlayOffsetAlign(AlignModeX.RIGHT, 
          AlignModeY.BOTTOM, pic, 0, 0, img1);
    }

    return img1;
  }

}

//to represent an edge
class Edge {
  Vertex from;
  Vertex to;
  int weight;

  Edge(Vertex from, Vertex to, int weight) {
    this.from = from;
    this.to = to;
    this.weight = weight;  
  }

  //checks if this edge has the given vertices, in either order
  boolean eitherEdge(Vertex v1, Vertex v2) {
    return (this.from.hashCode() == v1.hashCode() 
        && this.to.hashCode() == v2.hashCode())
        || (this.from.hashCode() == v2.hashCode() 
        && this.to.hashCode() == v1.hashCode());
  }
  
  //to produce a hash code for this edge
  public int hashCode() {
    return this.from.count + this.to.count;
  }

  //checks if the given object is equal to this Edge
  public boolean equals(Object o) {
    if (!(o instanceof Edge)) {
      return false;
    }
    Edge that = (Edge) o;
    return that.eitherEdge(this.to, this.from);

  }

}


//to represent a MyPosn
class MyPosn  {
  int x;
  int y; 

  MyPosn(int x, int y)  {
    this.x = x;
    this.y = y;
  }

  //to create a hashCode for a MyPosn
  public int hashCode()  {
    return (this.y * (MazeWorld.mWIDTH))
        + this.x;
  }

  //to determine if another posn is equal to this posn
  public boolean equals(Object other) {
    if (!(other instanceof MyPosn)) {
      return false;
    }
    MyPosn that = (MyPosn) other;
    return that.x == this.x
        && that.y == this.y;

  }


}


