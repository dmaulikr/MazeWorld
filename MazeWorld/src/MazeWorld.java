// Assignment 10  -  part 2
// Kim Austin
// KimA
// Pine Michelle
// pinemichelle63

import java.awt.Color;
import java.util.*;

import javalib.worldimages.*;
import javalib.impworld.*;

//to represent a MazeWorld
public class MazeWorld extends World   {
  static final int mWIDTH = 50;
  static final int mHEIGHT = 50; 
  static final int SCALE = 10;
  int height = mHEIGHT;
  int width = mWIDTH;
  HashMap<Integer, Vertex> vertexMap;
  ArrayList<ArrayList<Vertex>> vertexList;
  ArrayList<Edge> edgeList = new ArrayList<Edge>();
  ArrayList<Vertex> vertexList2;
  ArrayList<Edge> spanningTree = new ArrayList<Edge>();
  ArrayList<Edge> spanningTreeDisplay = new ArrayList<Edge>();
  Player user = new Player(0,0);
  boolean secondTravel = false;
  boolean showFirstPath = true;
  boolean showSecondPath = true;
  boolean vertical = false;
  boolean horizontal = false; 
  int vVal = 20;
  int hVal = 20; 
  boolean go = false;
  int count = 0;
  boolean won = false; 
  int count2 = 0; 
  boolean getFinalPath = false;
  boolean affect = true; 
  ArrayList<Vertex> travelled = new ArrayList<Vertex>();
  ArrayList<Vertex> travelledTwice = new ArrayList<Vertex>();

  MazeWorld()   {
    this.vertexList = this.generateVertices();
    this.makeEdges();
    this.sortEdges();
    this.vertexMap = this.makeVertexMap();
    this.vertexList2 = this.flattenVertices();
    this.spanningTree = this.unionFind();
    this.secondTravel = false;
    this.updateVertexImgs();
  }
  
  //EFFECT: knocks down a wall, one at a time, to a displayed vertex array. 
  //If bfs or dfs search is activated, changes the image of the vertices 
  //that have been searched, one at a time
  public void onTick()   {
    if (this.spanningTree.size() > 0)   {
      Edge e = this.spanningTree.remove(0);
      this.spanningTreeDisplay.add(e);
      e.to.updateImg(this.spanningTreeDisplay, 
          this.showFirstPath, this.showSecondPath);
      e.from.updateImg(this.spanningTreeDisplay,
          this.showFirstPath, this.showSecondPath);
    }
    else   {
      this.go = true;
      this.count++;
      for (Iterator<Vertex> iter = this.travelled.iterator(); 
          iter.hasNext();)   {
        Vertex v = iter.next();
        if (!v.travelledOnce)   {
          iter.remove();
        }
      }
    }
    if (this.vertexList2.get(0).showTwice)   {
      this.won = true;
    }
    if (this.go && this.secondTravel)   {
      if (this.travelled.size() > 0 && !this.getFinalPath)   {
        Vertex ver = this.travelled.remove(0);
        ver.showOnce = ver.travelledOnce;
        ver.updateImg(this.spanningTreeDisplay, this.showFirstPath, 
            this.showSecondPath);
        this.travelledTwice.add(ver);
        if (ver.hashCode() == (mWIDTH * mHEIGHT - 1))   {
          Collections.reverse(this.travelledTwice);
          for (Iterator<Vertex> iter = this.travelledTwice.iterator(); 
              iter.hasNext();)   {
            Vertex v = iter.next();
            if (!v.travelledTwice)   {
              iter.remove();
            }

          }
          this.getFinalPath = true;
        }
      }
      else if (this.travelledTwice.size() > 0 && this.getFinalPath)   {
        Vertex ver2 = this.travelledTwice.remove(0);
        ver2.showTwice = ver2.travelledTwice;
        ver2.updateImg(this.spanningTreeDisplay, 
            this.showFirstPath, this.showSecondPath);
      }

    }
  }

  //EFFECT: changes the state of the World based on keystrokes
  //"r" creates a new random maze
  //"b" does bfs search, "d" does dfs search
  //"1" toggles all the explored cells on/off
  //"2" toggles the final path on/off
  //"v" creates vertically-biased maze
  //"h" creates a horizontally-biased maze
  //arrow keys move the player
  public void onKeyEvent(String key)   {
    if (key.equals("r"))   {
      this.hVal = 20;
      this.vVal = 20;
      this.edgeList = new ArrayList<Edge>();
      this.vertexList = this.generateVertices();
      this.makeEdges();
      this.sortEdges();
      this.vertexMap = this.makeVertexMap();
      this.vertexList2 = this.flattenVertices();
      this.spanningTree = this.unionFind();
      spanningTreeDisplay = new ArrayList<Edge>();
      user = new Player(0, 0);
      this.secondTravel = false;
      secondTravel = false;
      showFirstPath = true;
      showSecondPath = true;
      this.spanningTreeDisplay = new ArrayList<Edge>();
      this.user = new Player(0, 0);
      this.secondTravel = false;
      this.secondTravel = false;
      this.showFirstPath = true;
      this.showSecondPath = true;
      this.vertical = false;
      this.horizontal = false;
      this.go = false;
      this.count = 0;
      this.won = false;
      this.travelled = new ArrayList<Vertex>();
      this.getFinalPath = false;
      this.travelledTwice = new ArrayList<Vertex>();
      this.affect = true;
      this.updateVertexImgs();
    }
    else if (key.equals("b") && go)   {
      this.affect = false;
      this.secondTravel = this.bfs(this.vertexList2.get(0), 
          this.vertexList2.get((MazeWorld.mWIDTH - 1) + 
              (MazeWorld.mHEIGHT - 1) * (MazeWorld.mWIDTH)));
    }
    else if (key.equals("d") && go)   {
      this.affect = false;
      this.secondTravel = this.dfs(this.vertexList2.get(0), 
          this.vertexList2.get((MazeWorld.mWIDTH - 1) + 
              (MazeWorld.mHEIGHT - 1) * (MazeWorld.mWIDTH)));
    }
    else if (key.equals("1") && go)   {
      this.showFirstPath = !this.showFirstPath;
      this.updateVertexImgs();
    }
    else if (key.equals("2") && go)   {
      this.showSecondPath = !this.showSecondPath;
      this.updateVertexImgs();
    }
    else if (key.equals("v"))   {
      this.vVal = 20;
      this.hVal = 1;
      this.edgeList = new ArrayList<Edge>();
      this.vertexList = this.generateVertices();
      this.makeEdges();
      this.sortEdges();
      this.vertexMap = this.makeVertexMap();
      this.vertexList2 = this.flattenVertices();
      this.spanningTree = this.unionFind();
      spanningTreeDisplay = new ArrayList<Edge>();
      user = new Player(0, 0);
      this.spanningTreeDisplay = new ArrayList<Edge>();
      this.user = new Player(0, 0);
      this.secondTravel = false;
      this.secondTravel = false;
      this.showFirstPath = true;
      this.showSecondPath = true;
      this.vertical = false;
      this.horizontal = false;
      this.go = false;
      this.count = 0;
      this.won = false;
      this.travelled = new ArrayList<Vertex>();
      this.getFinalPath = false;
      this.travelledTwice = new ArrayList<Vertex>();
      this.affect = true;
      this.updateVertexImgs();
    }
    else if (key.equals("h"))   {
      this.vVal = 1;
      this.hVal = 20; 
      this.edgeList = new ArrayList<Edge>();
      this.vertexList = this.generateVertices();
      this.makeEdges();
      this.sortEdges();
      this.vertexMap = this.makeVertexMap();
      this.vertexList2 = this.flattenVertices();
      this.spanningTree = this.unionFind();
      this.spanningTreeDisplay = new ArrayList<Edge>();
      this.user = new Player(0, 0);
      this.secondTravel = false;
      this.secondTravel = false;
      this.showFirstPath = true;
      this.showSecondPath = true;
      this.vertical = false;
      this.horizontal = false;
      this.go = false;
      this.count = 0;
      this.won = false;
      this.travelled = new ArrayList<Vertex>();
      this.getFinalPath = false;
      this.travelledTwice = new ArrayList<Vertex>();
      this.affect = true;
      this.updateVertexImgs();
    }
    else if (this.go)   {
      this.user.move(key, this.spanningTreeDisplay);
      if (this.affect)   {
        this.userTravel();
        Vertex v = this.vertexList2.get(new Vertex(
            new MyPosn(this.user.x, this.user.y)).hashCode());
        v.updateImg(this.spanningTreeDisplay,
            this.showFirstPath, this.showSecondPath);
      }
    }
  }

  //EFFECT: changes searched vertices to travelledOnce and travelledTwice
  //implements a breadth first search
  boolean bfs(Vertex from, Vertex to)   {
    return searchHelp(from, to, new Queue<Vertex>());
  }

  //EFFECT: changes searched vertices to travelledOnce and travelledTwice
  //implements a depth first search
  boolean dfs(Vertex from, Vertex to)   {
    return searchHelp(from, to, new Stack<Vertex>());
  }

  //EFFECT: changes searched vertices to travelledOnce and travelledTwice
  //helps search  list
  boolean searchHelp(Vertex from, Vertex to, ICollection<Vertex> worklist)   {
    HashMap<String, Edge> cameFromEdge = new HashMap<String, Edge>();
    ArrayList<Vertex> alreadySeen = new ArrayList<Vertex>();
    worklist.add(from);
    while (!worklist.isEmpty())   {
      Vertex next = worklist.remove();
      if (next.hashCode() == to.hashCode())   {
        next.travelledOnce = true;
        this.reconstruct(cameFromEdge, next);
        this.travelled.add(next);
        return true;
      }
      else if (alreadySeen.contains(next))   {
        // do nothing: we've already seen this one
      }
      else   {
        ArrayList<Edge> onlyInTree = new ArrayList<Edge>();
        for (Edge e: next.outEdges)   {
          if (this.spanningTreeDisplay.contains(e))   {
            onlyInTree.add(e);
            if (!cameFromEdge.containsKey(e.toString()))   {
              if (e.from.equals(next))   {
                cameFromEdge.put(e.toString(), e);
              }
              else if (e.to.equals(next))   {
                e.to = e.from;
                e.from = next;
                cameFromEdge.put(e.toString(), e);
              }
            }
          }
        }
        for (Edge e : onlyInTree)   {
          if (e.to.equals(next))   {
            worklist.add(e.from);
          }
          else   {
            worklist.add(e.to);
          }
        }
        next.travelledOnce = true;
        alreadySeen.add(next);
        this.travelled.add(next);
      }
    }
    return false;
  }


  //to generate nodes
  ArrayList<ArrayList<Vertex>> generateVertices()   {
    ArrayList<ArrayList<Vertex>> a = new ArrayList<ArrayList<Vertex>>();
    int count = 0;
    for (int i = 0; i < MazeWorld.mHEIGHT; i = i + 1)   {
      ArrayList<Vertex> r = new ArrayList<Vertex>();
      for (int j = 0; j < MazeWorld.mWIDTH; j = j + 1)   {
        r.add(new Vertex(new MyPosn(j, i), count));
        count = count + 1;
      }
      a.add(r);
    }
    return a;
  }

  //EFFECT: connects all neighbor nodes
  //returns the list of all edges in the array
  void makeEdges()   {
    for (int i = 0; i <= (this.vertexList.size() - 1); i = i + 1)    {
      for (int j = 0; j <= (this.vertexList.get(0).size() - 1); j = j + 1)    {
        if (j >= 1)    {
          Edge e = this.connect(this.vertexList.get(i).get(j - 1), 
              this.vertexList.get(i).get(j),
              new Random().nextInt(this.vVal));
          this.edgeList.add(e);
        }
        if (i >= 1)    {
          Edge e = this.connect(this.vertexList.get(i - 1).get(j), 
              this.vertexList.get(i).get(j), 
              new Random().nextInt(this.hVal));
          this.edgeList.add(e);
        }
      }
    }
  }

  //EFFECT creates an edge between two cells
  //returns an edge to add to the edgeList
  Edge connect(Vertex leftTop, Vertex rightBottom, int random)   {
    Edge e = new Edge(leftTop, rightBottom, random);
    leftTop.outEdges.add(e); 
    rightBottom.outEdges.add(e);
    return e;
  }


  //EFFECT: sorts a list of edges by weight
  void sortEdges()   {
    new Utils().mergesort(this.edgeList, new AscendingOrder());
  }

  //EFFECT: adds vertices to the hash map
  public HashMap<Integer, Vertex> makeVertexMap()   {
    HashMap<Integer, Vertex> vertices = new HashMap<Integer, Vertex>();
    for (int i = 0; i < this.vertexList.size(); i = i + 1)   {
      for (int j = 0; j < this.vertexList.get(0).size(); j = j + 1)   {
        Vertex v = this.vertexList.get(i).get(j);
        vertices.put(v.count, v);
      }
    }
    return vertices;
  }

  //implements the union/find algorithm
  public ArrayList<Edge> unionFind()   {
    HashMap<Integer, Vertex> reps = this.vertexMap;
    ArrayList<Edge> worklist = new ArrayList<Edge>();
    for (Edge e: this.edgeList)   {
      worklist.add(e);
    }
    ArrayList<Edge> edgesInTree = new ArrayList<Edge>();
    while (edgesInTree.size() < this.width * this.height - 1 
        || worklist.size() > 0)   {
      Edge e = worklist.remove(0);
      if (this.hasCycle(e.from, e.to, reps))   {
        //do nothing
      }
      else   {
        reps.put(this.findRep(reps, e.from).hashCode(), e.to);
        edgesInTree.add(e);  
      }
    }
    return edgesInTree;
  }


  //helps union/find determine if a node indirectly 
  //or directly has a representative
  public boolean hasCycle(Vertex from, Vertex to, HashMap<Integer, Vertex> reps)   {
    return this.findRep(reps,  from).hashCode() 
        == this.findRep(reps, to).hashCode();

  }

  //finds the ultimate representative of the given vertex
  public Vertex findRep(HashMap<Integer, Vertex> reps, Vertex v)   {
    if (v.hashCode() == reps.get(v.hashCode()).hashCode())   {
      return v;
    }
    else   {
      return this.findRep(reps, reps.get(v.hashCode()));
    }
  }

  //checks if a Vertex contains a link to another vertex
  boolean hasEdgeTo(Vertex v1, Vertex v2)   {
    boolean isTrue = false; 
    for (int i = 0; i < v1.outEdges.size() && !isTrue; i = i + 1)   {
      Edge e = v1.outEdges.get(i);
      isTrue = (v2.hashCode() == e.to.hashCode())
          || (v2.hashCode() == e.from.hashCode());
    }
    return isTrue;
  }

  //flattens an arrayList of vertices
  ArrayList<Vertex> flattenVertices()   {
    ArrayList<Vertex> newArr = new ArrayList<Vertex>();
    for (ArrayList<Vertex> a: this.vertexList)   {
      newArr.addAll(a);
    }
    return newArr;

  }

  //EFFECT: changes the WorldScene of this World
  //makes the scene for the world
  public WorldScene makeScene()   {
    WorldScene w = this.getEmptyScene();
    for (Vertex v : this.vertexList2)   {
      w.placeImageXY(v.img,
          v.center.x * SCALE + SCALE / 2 ,
          v.center.y * SCALE + SCALE / 2);
    }
    w.placeImageXY(new RectangleImage(
        MazeWorld.SCALE, MazeWorld.SCALE, OutlineMode.SOLID, Color.GREEN),
        SCALE / 2, SCALE / 2); 
    w.placeImageXY(new RectangleImage(
        MazeWorld.SCALE, MazeWorld.SCALE, OutlineMode.SOLID, 
        new Color(140, 0, 140)), mWIDTH * MazeWorld.SCALE - SCALE / 2, 
        mHEIGHT * MazeWorld.SCALE - SCALE / 2);
    w.placeImageXY(user.img, user.x * SCALE + SCALE / 2, 
        user.y * SCALE + SCALE / 2);
    if (this.won)   {
      w.placeImageXY(new TextImage("The Maze is Solved", 15, Color.red), 
          (mWIDTH / 2 * SCALE), (mHEIGHT / 2) * MazeWorld.SCALE);
    }
    if (this.go && this.count < 100 && !this.won)  {
      w.placeImageXY(new TextImage("GO!", 15, Color.red), 
          (mWIDTH / 2 * SCALE), (mHEIGHT / 2) * MazeWorld.SCALE);
    }
    return w;

  }

  //EFFECT: changes the vertices that are part of the path to travelled twice
  //reconstructs the path
  public void reconstruct(HashMap<String, Edge> map, Vertex v)   {
    if (v.hashCode() == 0)   {
      //do nothing
    }
    else    { 
      v.travelledTwice = true;
      for (Edge e: v.outEdges)   {
        if (map.containsKey(e.toString()))   {
          if (e.to.equals(v))   {
            e.from.travelledTwice = true;
            this.reconstruct(map, e.from);
          }
        }
      }
    }
  }

  //EFFECT: if the user is on this vertex, change 
  //its travelledOnce boolean
  //EFFECT: if the user has landed on the target vertex, 
  //allow it to travel backwards.
  public void userTravel()   {
    Vertex v = this.vertexList2
        .get(new Vertex(new MyPosn(this.user.x, this.user.y))
            .findCount());
    if (v.findCount() == (MazeWorld.mWIDTH - 1) + 
        (MazeWorld.mHEIGHT - 1) * (MazeWorld.mWIDTH))   {
      this.secondTravel = true;
    }
    if (this.secondTravel)   {
      v.travelledTwice = true;
      v.showTwice = true;
    }
    else   {
      v.travelledOnce = true;
      v.showOnce = true;
    }
  }

  //EFFECT: update the images for all vertices
  void updateVertexImgs()  {
    for (Vertex v: this.vertexList2)   {
      v.updateImg(this.spanningTreeDisplay, 
          this.showFirstPath, this.showSecondPath);
    }
  }

}


