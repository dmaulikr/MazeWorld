// Assignment 10  -  part 2
// Kim Austin
// KimA
// Pine Michelle
// pinemichelle63

import java.awt.Color;
import java.util.*;
import javalib.worldimages.*;
import tester.Tester;
import javalib.impworld.*;

class ExamplesMazeWorld {

  //tests the maze rendering
  //UNCOMMENT THIS BLOCK TO SEE THE MAZE, PRESS R TO GENERATE A NEW MAZE
  void testGame(Tester t)   {
    MazeWorld w  =  new MazeWorld();
    w.bigBang((MazeWorld.mWIDTH) * MazeWorld.SCALE,(MazeWorld.mHEIGHT)
        * MazeWorld.SCALE, 0.00000001);
  }


  //tests hashCode for a vertex and findCount 
  //NOTE: CHANGING THE SIZE OF THE GRAPH BELOW 5X5 WILL CAUSE INDEX OUT OF RANGE ERROS
  //FOR THESE TESTS, SINCE THEY USE THE (5, 5) NODE FOR TESTING
  boolean testCountHashCode(Tester t) {
    Vertex v1 = new Vertex(new MyPosn(5,5));
    MazeWorld g = new MazeWorld();
    Vertex v2 = g.vertexList.get(5).get(5);
    Vertex v3 = g.vertexList.get(0).get(0);
    Vertex v4 = g.vertexList.get(MazeWorld.mHEIGHT - 1).get(MazeWorld.mWIDTH - 1);
    return t.checkExpect(v1.hashCode(), v1.count)
        && t.checkExpect(v2.hashCode(), 
            MazeWorld.mWIDTH * v1.center.y + v1.center.x)
        && t.checkExpect(v2.count,
            MazeWorld.mWIDTH * v1.center.y + v1.center.x)
        && t.checkExpect(v2.findCount(),
            MazeWorld.mWIDTH * v1.center.y + v1.center.x)
        && t.checkExpect(v3.hashCode(), 0)
        && t.checkExpect(v3.findCount(), 0)
        && t.checkExpect(v3.count, 0)
        && t.checkExpect(v4.hashCode(), 
            MazeWorld.mWIDTH * MazeWorld.mHEIGHT - 1)
        && t.checkExpect(v4.findCount(), 
            MazeWorld.mWIDTH * MazeWorld.mHEIGHT - 1)
        && t.checkExpect(v4.count,
            MazeWorld.mWIDTH * MazeWorld.mHEIGHT - 1);
  }

  //test displayVertex and MakeWalls
  boolean testDisplayVertexMakeWalls(Tester t) {
    Vertex v1 = new Vertex(new MyPosn(5,5));
    Vertex v2 = new Vertex(new MyPosn(4, 5));
    Vertex v3 = new Vertex(new MyPosn(6, 5));
    Vertex v4 = new Vertex(new MyPosn(5, 4));
    Vertex v5 = new Vertex(new MyPosn(5, 6));
    Edge e1 = new Edge(v1, v2, 5);
    Edge e2 = new Edge(v1, v3, 5);
    Edge e3 = new Edge(v1, v4, 5);
    Edge e4 = new Edge(v1, v5, 5);
    v1.outEdges.add(e1);
    v2.outEdges.add(e1);
    v1.outEdges.add(e2);
    v3.outEdges.add(e2);
    v1.outEdges.add(e3);
    v4.outEdges.add(e3);
    v1.outEdges.add(e4);
    v5.outEdges.add(e4);
    WorldImage rect = 
        new RectangleImage(MazeWorld.SCALE, MazeWorld.SCALE, 
            OutlineMode.SOLID, Color.LIGHT_GRAY);
    WorldImage rectT = 
        new RectangleImage(MazeWorld.SCALE, MazeWorld.SCALE, 
            OutlineMode.SOLID, new Color(102, 153, 255));
    WorldImage rectT2 = 
        new RectangleImage(MazeWorld.SCALE, MazeWorld.SCALE, 
            OutlineMode.SOLID, new Color(0, 102, 255));
    WorldImage pic = 
        new LineImage(new Posn(MazeWorld.SCALE, 0), Color.BLACK);
    WorldImage pic2 = 
        new LineImage(new Posn(MazeWorld.SCALE, 0), Color.BLACK);
    WorldImage pic3 = 
        new LineImage(new Posn(0, MazeWorld.SCALE), Color.BLACK);
    WorldImage rect2 = 
        new OverlayOffsetAlign(AlignModeX.LEFT, 
            AlignModeY.BOTTOM, pic, 0, 0, rect);
    WorldImage rect3 = 
        new OverlayOffsetAlign(AlignModeX.LEFT, 
            AlignModeY.BOTTOM, pic2, 0, 0, 
            new OverlayOffsetAlign(AlignModeX.LEFT,
                AlignModeY.TOP, pic, 0, 0, rect));
    WorldImage rect4 = new OverlayOffsetAlign(
        AlignModeX.LEFT, AlignModeY.BOTTOM, pic2, 0, 0,
        new OverlayOffsetAlign(AlignModeX.LEFT, 
            AlignModeY.TOP, pic2, 0, 0, 
            new OverlayOffsetAlign(AlignModeX.RIGHT,
                AlignModeY.TOP, pic3, 0, 0, rect)));
    ArrayList<Edge> l1 = new ArrayList<Edge>(Arrays.asList(e1, e2, e3, e4));
    ArrayList<Edge> l2 = new ArrayList<Edge>(Arrays.asList(e1, e2, e3));
    ArrayList<Edge> l3 = new ArrayList<Edge>(Arrays.asList(e1, e2));
    ArrayList<Edge> l4 = new ArrayList<Edge>(Arrays.asList(e1));
    boolean tests1 = t.checkExpect(v1.displayVertex(l1, true, true), rect)
        && t.checkExpect(v1.displayVertex(l2, true, true),
            rect2)
        && t.checkExpect(v1.displayVertex(l3, true, true),
            rect3)
        && t.checkExpect(v1.displayVertex(l4, true, true), 
            rect4);
    v1.travelledOnce = true;
    v1.showOnce = true;
    boolean tests2 = t.checkExpect(v1.displayVertex(l1, true, true),
        rectT)
        && t.checkExpect(v1.displayVertex(l1, false, true), 
            rect);
    v1.travelledTwice = true;
    v1.showTwice = true;
    boolean tests3 = t.checkExpect(v1.displayVertex(l1,  true, true), 
        rectT2)
        && t.checkExpect(v1.displayVertex(l1, false, true), 
            rectT2)
        && t.checkExpect(v1.displayVertex(l1, true, false),
            rectT)
        && t.checkExpect(v1.displayVertex(l1, false, false), 
            rect);
    return tests1 && tests2 && tests3;
  }

  //tests hashCode for MyPosn
  boolean testHashCodePosn(Tester t) {
    MyPosn p = new MyPosn(5, 5);
    MyPosn p2 = new MyPosn(0, 0);
    return t.checkExpect(p.hashCode(), 5 + 5 * MazeWorld.mWIDTH)
        && t.checkExpect(p2.hashCode(), 0);
  }

  //tests apply for ascending order
  boolean testApply(Tester t) {
    Vertex v1 = new Vertex(new MyPosn(5,5));
    Vertex v2 = new Vertex(new MyPosn(4, 5));
    Vertex v3 = new Vertex(new MyPosn(6, 5));
    Vertex v5 = new Vertex(new MyPosn(5, 6));
    Edge e1 = new Edge(v1, v2, 5);
    Edge e2 = new Edge(v1, v3, 6);
    Edge e4 = new Edge(v1, v5, 5);
    IComparator<Edge> ascend = new AscendingOrder();
    return t.checkExpect(ascend.compare(e1, e2), -1)
        && t.checkExpect(ascend.compare(e2, e1), 1)
        && t.checkExpect(ascend.compare(e1, e4), 0);
  }

  //tests isEmpty for ICollection
  boolean testIsEmpty(Tester t) {
    ICollection<Integer> stack1 = new Stack<Integer>(
        new ArrayDeque<Integer>());
    ICollection<Integer> stack2 = new Stack<Integer>(
        new ArrayDeque<Integer>(Arrays.asList(1, 2, 3)));
    ICollection<Integer> queue1 = new Queue<Integer>(
        new ArrayDeque<Integer>());
    ICollection<Integer> queue2 = new Queue<Integer>(
        new ArrayDeque<Integer>(Arrays.asList(1, 2, 3)));
    return t.checkExpect(stack1.isEmpty(), true)
        && t.checkExpect(stack2.isEmpty(), false)
        && t.checkExpect(queue1.isEmpty(), true)
        && t.checkExpect(queue2.isEmpty(), false);
  }

  //tests remove for ICollection
  boolean testRemove(Tester t) {
    ICollection<Integer> stack1 = new Stack<Integer>(
        new ArrayDeque<Integer>());
    ICollection<Integer> stack2 = new Stack<Integer>(
        new ArrayDeque<Integer>(Arrays.asList(1, 2, 3)));
    ICollection<Integer> queue1 = new Queue<Integer>(
        new ArrayDeque<Integer>());
    ICollection<Integer> queue2 = new Queue<Integer>(
        new ArrayDeque<Integer>(Arrays.asList(1, 2, 3)));
    return t.checkException(new NoSuchElementException(),
        stack1, "remove")
        && t.checkExpect(stack2.remove(), 1)
        && t.checkExpect(stack2, 
            new Stack<Integer>(
                new ArrayDeque<Integer>(Arrays.asList(2, 3))))
        && t.checkException(new NoSuchElementException(), 
            queue1, "remove")
        && t.checkExpect(queue2.remove(), 1)
        && t.checkExpect(queue2, 
            new Queue<Integer>(
                new ArrayDeque<Integer>(Arrays.asList(2, 3))));
  }

  //tests add for ICollection
  boolean testAdd(Tester t) {
    ICollection<Integer> stack1 = new Stack<Integer>(
        new ArrayDeque<Integer>());
    ICollection<Integer> stack2 = new Stack<Integer>(
        new ArrayDeque<Integer>(Arrays.asList(1, 2, 3)));
    ICollection<Integer> queue1 = new Queue<Integer>(
        new ArrayDeque<Integer>());
    ICollection<Integer> queue2 = new Queue<Integer>(
        new ArrayDeque<Integer>(Arrays.asList(1, 2, 3)));
    stack1.add(1);
    stack2.add(0);
    queue1.add(1);
    queue2.add(4);
    return t.checkExpect(stack1, new Stack<Integer>(
        new ArrayDeque<Integer>(Arrays.asList(1))))
        && t.checkExpect(stack2,
            new Stack<Integer>(new ArrayDeque<Integer>(
                Arrays.asList(0, 1, 2, 3))))
        && t.checkExpect(queue1, new Queue<Integer>(
            new ArrayDeque<Integer>(Arrays.asList(1))))
        && t.checkExpect(queue2, 
            new Queue<Integer>(
                new ArrayDeque<Integer>(Arrays.asList(1, 2, 3, 4))));
  }


  //tests MergeSort
  boolean testMergeSort(Tester t) {
    Vertex v1 = new Vertex(new MyPosn(5,5));
    Vertex v2 = new Vertex(new MyPosn(4, 5));
    Vertex v3 = new Vertex(new MyPosn(6, 5));
    Vertex v4 = new Vertex(new MyPosn(5, 4));
    Vertex v5 = new Vertex(new MyPosn(5, 6));
    Edge e1 = new Edge(v1, v2, 10);
    Edge e2 = new Edge(v1, v3, 9);
    Edge e3 = new Edge(v1, v4, 8);
    Edge e4 = new Edge(v1, v5, 7);
    v1.outEdges.add(e1);
    v2.outEdges.add(e1);
    v1.outEdges.add(e2);
    v3.outEdges.add(e2);
    v1.outEdges.add(e3);
    v4.outEdges.add(e3);
    v1.outEdges.add(e4);
    v5.outEdges.add(e4);
    ArrayList<Edge> l1 = 
        new ArrayList<Edge>(Arrays.asList(e1, e2, e3, e4));
    ArrayList<Edge> l2 = 
        new ArrayList<Edge>(Arrays.asList(e3, e2, e1, e4));
    ArrayList<Edge> l3 = 
        new ArrayList<Edge>(Arrays.asList(e1, e1));
    Utils util = new Utils();
    util.mergesort(l1, new AscendingOrder());
    util.mergesort(l2, new AscendingOrder());
    util.mergesort(l3, new AscendingOrder());
    return t.checkExpect(l1, 
        new ArrayList<Edge>(Arrays.asList(e4, e3, e2, e1)))
        && t.checkExpect(l2, 
            new ArrayList<Edge>(Arrays.asList(e4, e3, e2, e1)))
        && t.checkExpect(l3, 
            new ArrayList<Edge>(Arrays.asList(e1, e1)));

  }

  //tests MergeSortHelp
  boolean testMergeSortHelp(Tester t) {
    Vertex v1 = new Vertex(new MyPosn(5,5));
    Vertex v2 = new Vertex(new MyPosn(4, 5));
    Vertex v3 = new Vertex(new MyPosn(6, 5));
    Vertex v4 = new Vertex(new MyPosn(5, 4));
    Vertex v5 = new Vertex(new MyPosn(5, 6));
    Edge e1 = new Edge(v1, v2, 10);
    Edge e2 = new Edge(v1, v3, 9);
    Edge e3 = new Edge(v1, v4, 8);
    Edge e4 = new Edge(v1, v5, 7);
    v1.outEdges.add(e1);
    v2.outEdges.add(e1);
    v1.outEdges.add(e2);
    v3.outEdges.add(e2);
    v1.outEdges.add(e3);
    v4.outEdges.add(e3);
    v1.outEdges.add(e4);
    v5.outEdges.add(e4);
    ArrayList<Edge> l1 = 
        new ArrayList<Edge>(Arrays.asList(e1, e2, e3, e4));
    ArrayList<Edge> l1a = 
        new ArrayList<Edge>(Arrays.asList(e1, e2, e3, e4));
    ArrayList<Edge> l2 = 
        new ArrayList<Edge>(Arrays.asList(e3, e2, e1, e4));
    ArrayList<Edge> l2a = 
        new ArrayList<Edge>(Arrays.asList(e3, e2, e1, e4));
    ArrayList<Edge> l3 = 
        new ArrayList<Edge>(Arrays.asList(e1, e1));
    Utils util = new Utils();
    util.mergesortHelp(l1, l1a, new AscendingOrder(), 0, l1.size());
    util.mergesortHelp(l2, l2a, new AscendingOrder(), 0, l2.size());
    util.mergesortHelp(l3, l3, new AscendingOrder(), 0, l3.size());
    return t.checkExpect(l1, new ArrayList<Edge>(Arrays.asList(e4, e3, e2)))
        && t.checkExpect(l2, 
            new ArrayList<Edge>(Arrays.asList(e4, e3, e2)))
        && t.checkExpect(l3, 
            new ArrayList<Edge>(Arrays.asList(e1, e1)));

  }


  //tests Merge
  boolean testMerge(Tester t) {
    Vertex v1 = new Vertex(new MyPosn(5,5));
    Vertex v2 = new Vertex(new MyPosn(4, 5));
    Vertex v3 = new Vertex(new MyPosn(6, 5));
    Vertex v4 = new Vertex(new MyPosn(5, 4));
    Vertex v5 = new Vertex(new MyPosn(5, 6));
    Edge e1 = new Edge(v1, v2, 10);
    Edge e2 = new Edge(v1, v3, 9);
    Edge e3 = new Edge(v1, v4, 8);
    Edge e4 = new Edge(v1, v5, 7);
    v1.outEdges.add(e1);
    v2.outEdges.add(e1);
    v1.outEdges.add(e2);
    v3.outEdges.add(e2);
    v1.outEdges.add(e3);
    v4.outEdges.add(e3);
    v1.outEdges.add(e4);
    v5.outEdges.add(e4);
    ArrayList<Edge> l1 = 
        new ArrayList<Edge>(Arrays.asList(e1, e2, e3, e4));
    ArrayList<Edge> l1a = 
        new ArrayList<Edge>(Arrays.asList(e1, e2, e3, e4));
    ArrayList<Edge> l2 = 
        new ArrayList<Edge>(Arrays.asList(e3, e2, e1, e4));
    ArrayList<Edge> l2a = 
        new ArrayList<Edge>(Arrays.asList(e3, e2, e1, e4));
    ArrayList<Edge> l3 = 
        new ArrayList<Edge>(Arrays.asList(e1, e1));
    Utils util = new Utils();
    util.mergesortHelp(l1, l1a, 
        new AscendingOrder(), 0, l1.size());
    util.mergesortHelp(l2, l2a, 
        new AscendingOrder(), 0, l2.size());
    util.mergesortHelp(l3, l3, 
        new AscendingOrder(), 0, l3.size());
    util.merge(l1, l1a, 
        new AscendingOrder(), 0, l1.size(), l1.size());
    util.merge(l2, l2a, 
        new AscendingOrder(), 0, l2.size(), l2.size());
    util.merge(l3, l3, 
        new AscendingOrder(), 0, l3.size(), l3.size());
    return t.checkExpect(l1, 
        new ArrayList<Edge>(Arrays.asList(e4, e3, e2)))
        && t.checkExpect(l2, 
            new ArrayList<Edge>(Arrays.asList(e4, e3, e2)))
        && t.checkExpect(l3, 
            new ArrayList<Edge>(Arrays.asList(e1, e1)));
  }

  //tests andMap
  boolean testAndMap(Tester t) {
    Vertex v1 = new Vertex(new MyPosn(5,5));
    Vertex v2 = new Vertex(new MyPosn(4, 5));
    Vertex v3 = new Vertex(new MyPosn(6, 5));
    Vertex v4 = new Vertex(new MyPosn(5, 4));
    Vertex v5 = new Vertex(new MyPosn(5, 6));
    Edge e1 = new Edge(v1, v2, 10);
    Edge e2 = new Edge(v1, v3, 9);
    Edge e3 = new Edge(v1, v4, 8);
    Edge e4 = new Edge(v1, v5, 7);
    v1.outEdges.add(e1);
    v2.outEdges.add(e1);
    v1.outEdges.add(e2);
    v3.outEdges.add(e2);
    v1.outEdges.add(e3);
    v4.outEdges.add(e3);
    v1.outEdges.add(e4);
    v5.outEdges.add(e4);
    ArrayList<Edge> l1 = 
        new ArrayList<Edge>(Arrays.asList(e1, e2, e3, e4));
    ArrayList<Edge> l4 = 
        new ArrayList<Edge>(Arrays.asList(e4, e3, e2, e1));
    Utils util = new Utils();
    return t.checkExpect(util.andMap(l1, new AscendingOrder()), false)
        && t.checkExpect(util.andMap(l4, new AscendingOrder()), true);
  }

  //tests hasEdgeTo
  boolean testEdgeTo(Tester t) {
    MazeWorld g = new MazeWorld();
    Vertex v1 = new Vertex(new MyPosn(5,5));
    Vertex v2 = new Vertex(new MyPosn(4, 5));
    Vertex v3 = new Vertex(new MyPosn(6, 5));
    Vertex v4 = new Vertex(new MyPosn(5, 4));
    Vertex v5 = new Vertex(new MyPosn(5, 6));
    Vertex v6 = new Vertex(new MyPosn(5, 7));
    Edge e1 = new Edge(v1, v2, 10);
    Edge e2 = new Edge(v1, v3, 9);
    Edge e3 = new Edge(v1, v4, 8);
    Edge e4 = new Edge(v1, v5, 7);
    v1.outEdges.add(e1);
    v2.outEdges.add(e1);
    v1.outEdges.add(e2);
    v3.outEdges.add(e2);
    v1.outEdges.add(e3);
    v4.outEdges.add(e3);
    v1.outEdges.add(e4);
    v5.outEdges.add(e4);
    return t.checkExpect(g.hasEdgeTo(v1, v2), true)
        && t.checkExpect(g.hasEdgeTo(v1, v6), false);
  }



  //tests sorted edges
  boolean testSortEdges(Tester t) {
    MazeWorld g = new MazeWorld();
    ArrayList<Edge> edgesNow = g.edgeList;
    return t.checkExpect(edgesNow.get(0).weight 
        <= edgesNow.get(1).weight, true)
        && t.checkExpect(edgesNow.get(1).weight 
            <= edgesNow.get(2).weight, true)
        && t.checkExpect(edgesNow.get(2).weight 
            <= edgesNow.get(3).weight, true)
        && t.checkExpect(edgesNow.get(4).weight 
            <= edgesNow.get(5).weight, true)
        & t.checkExpect(new Utils().andMap(g.edgeList,
            new AscendingOrder()), true);
  }

  //tests generated vertices
  boolean testGenerateVertices(Tester t) {
    MazeWorld g = new MazeWorld();
    return t.checkExpect(g.vertexList.size(), MazeWorld.mHEIGHT)
        && t.checkExpect(g.vertexList.get(0).size(), MazeWorld.mWIDTH);
  }

  //tests makeEdges
  boolean testMakeEdges(Tester t) {
    MazeWorld g = new MazeWorld();
    return t.checkExpect(g.edgeList.size(), 
        (MazeWorld.mWIDTH - 1) * MazeWorld.mHEIGHT 
        + (MazeWorld.mHEIGHT - 1) * MazeWorld.mWIDTH)
        && t.checkExpect(g.hasEdgeTo(g.vertexList.get(0).get(0),
            g.vertexList.get(0).get(1)), true)
        && t.checkExpect(g.hasEdgeTo(g.vertexList.get(0).get(0), 
            g.vertexList.get(1).get(0)), true)
        && t.checkExpect(g.hasEdgeTo(g.vertexList.get(0).get(0),
            g.vertexList.get(4).get(0)), false)
        && t.checkExpect(g.hasEdgeTo(g.vertexList.get(2).get(2), 
            g.vertexList.get(1).get(2)), true)
        && t.checkExpect(g.hasEdgeTo(g.vertexList.get(2).get(2),
            g.vertexList.get(3).get(2)), true)
        && t.checkExpect(g.hasEdgeTo(g.vertexList.get(2).get(2), 
            g.vertexList.get(2).get(1)), true)
        && t.checkExpect(g.hasEdgeTo(g.vertexList.get(2).get(2),
            g.vertexList.get(2).get(3)), true)
        && t.checkExpect(g.hasEdgeTo(g.vertexList.get(2).get(2), 
            g.vertexList.get(2).get(4)), false);

  }

  //tests flattenArray
  boolean testFlattenArray(Tester t) {
    MazeWorld g = new MazeWorld();
    return t.checkExpect(g.flattenVertices().size(), 
        MazeWorld.mHEIGHT * MazeWorld.mWIDTH);
  }

  //tests connect and hasEdgeTo
  boolean testConnect(Tester t) {
    MazeWorld g = new MazeWorld();
    Vertex v1 = new Vertex(new MyPosn(1,1));
    Vertex v2 = new Vertex(new MyPosn(2,2));
    Vertex v3 = new Vertex(new MyPosn(2,1));
    boolean noEdges = v1.outEdges.size() == 0 
        && v2.outEdges.size() == 0;
    g.connect(v1, v2, 3);
    return t.checkExpect(noEdges, true)
        && t.checkExpect(v1.outEdges.size(), 1)
        && t.checkExpect(v2.outEdges.size(), 1)
        && t.checkExpect(g.hasEdgeTo(v1, v2), true)
        && t.checkExpect(g.hasEdgeTo(v2, v1), true)
        && t.checkExpect(g.hasEdgeTo(v2, v3), false)
        && t.checkExpect(g.hasEdgeTo(v1, v3), false);

  }

  //tests makeVertexMap
  boolean testMakeVertexMap(Tester t) {
    MazeWorld g = new MazeWorld();
    return t.checkExpect(g.makeVertexMap().size(), 
        MazeWorld.mHEIGHT * MazeWorld.mWIDTH)
        && t.checkExpect(g.makeVertexMap().get(0).hashCode(), 0)
        && t.checkExpect(g.makeVertexMap().get(1).hashCode(), 1)
        && t.checkExpect(g.makeVertexMap().get(2).hashCode(), 2)
        && t.checkExpect(g.makeVertexMap().get(3).hashCode(), 3);
  }


  //tests union find
  boolean testUnion(Tester t) {
    MazeWorld d = new MazeWorld();
    return t.checkExpect(d.spanningTree.size(), 
        (MazeWorld.mHEIGHT * MazeWorld.mWIDTH) - 1);

  }


  //tests hasCycle
  boolean testHasCycle(Tester t) {
    MazeWorld g = new MazeWorld();
    Vertex v1 = new Vertex(new MyPosn(5,5));
    Vertex v2 = new Vertex(new MyPosn(4, 5));
    Vertex v3 = new Vertex(new MyPosn(6, 5));
    Vertex v4 = new Vertex(new MyPosn(5, 4));
    Vertex v5 = new Vertex(new MyPosn(5, 6));
    Edge e1 = new Edge(v1, v2, 10);
    Edge e2 = new Edge(v1, v3, 9);
    Edge e3 = new Edge(v1, v4, 8);
    Edge e4 = new Edge(v1, v5, 7);
    v1.outEdges.add(e1);
    v2.outEdges.add(e1);
    v1.outEdges.add(e2);
    v3.outEdges.add(e2);
    v1.outEdges.add(e3);
    v4.outEdges.add(e3);
    v1.outEdges.add(e4);
    v5.outEdges.add(e4);
    return t.checkExpect(g.hasCycle(v1, v2, 
        g.makeVertexMap()), false)
        && t.checkExpect(g.hasCycle(v2, v2, 
            g.makeVertexMap()), true);
  }

  //tests findRep
  boolean testFindRep(Tester t) {
    MazeWorld g = new MazeWorld();
    Vertex v1 = new Vertex(new MyPosn(5,5));
    Vertex v2 = new Vertex(new MyPosn(4, 5));
    Vertex v3 = new Vertex(new MyPosn(6, 5));
    Vertex v4 = new Vertex(new MyPosn(5, 4));
    Vertex v5 = new Vertex(new MyPosn(5, 6));
    Edge e1 = new Edge(v1, v2, 10);
    Edge e2 = new Edge(v1, v3, 9);
    Edge e3 = new Edge(v1, v4, 8);
    Edge e4 = new Edge(v1, v5, 7);
    v1.outEdges.add(e1);
    v2.outEdges.add(e1);
    v1.outEdges.add(e2);
    v3.outEdges.add(e2);
    v1.outEdges.add(e3);
    v4.outEdges.add(e3);
    v1.outEdges.add(e4);
    v5.outEdges.add(e4);
    return t.checkExpect(g.findRep(g.vertexMap, g.vertexMap.get(0)), 
        g.findRep(g.vertexMap, g.vertexMap.get(0)))
        && t.checkExpect(g.findRep(g.makeVertexMap(), v2), v2);
  }

  //to test updateVertexImgs
  public boolean testUpdateVertexImgs(Tester t) {
    MazeWorld g = new MazeWorld();
    Vertex v =  g.vertexList2.get(g.vertexList2.size() - 1);
    boolean test1 = t.checkExpect(v.img, 
        new FrozenImage(v.displayVertex(g.spanningTreeDisplay,
            g.showFirstPath, g.showSecondPath)));
    g.bfs(g.vertexList2.get(0), g.vertexList2.get(g.vertexList2.size() - 1));
    v.showOnce = true;
    g.updateVertexImgs();
    boolean test2 = t.checkExpect(v.img, 
        new FrozenImage(v.displayVertex(g.spanningTreeDisplay, 
            g.showFirstPath, g.showSecondPath)));
    v.showTwice = true;
    boolean test3 = t.checkExpect(v.img, 
        new FrozenImage(v.displayVertex(g.spanningTreeDisplay, 
            g.showFirstPath, g.showSecondPath)));
    return test1 && test2 && test3;
  }

  //tests makeScene
  boolean testMakeScene(Tester t) {
    MazeWorld g = new MazeWorld();
    Vertex v1 = new Vertex(new MyPosn(5,5));
    Vertex v2 = new Vertex(new MyPosn(4, 5));
    Vertex v3 = new Vertex(new MyPosn(6, 5));
    Vertex v4 = new Vertex(new MyPosn(5, 4));
    Vertex v5 = new Vertex(new MyPosn(5, 6));
    Vertex v6 = new Vertex(new MyPosn(5, 7));
    Edge e1 = new Edge(v1, v2, 10);
    Edge e2 = new Edge(v1, v3, 9);
    Edge e3 = new Edge(v1, v4, 8);
    Edge e4 = new Edge(v1, v5, 7);
    v1.outEdges.add(e1);
    v2.outEdges.add(e1);
    v1.outEdges.add(e2);
    v3.outEdges.add(e2);
    v1.outEdges.add(e3);
    v4.outEdges.add(e3);
    v1.outEdges.add(e4);
    v5.outEdges.add(e4);
    g.vertexList = new ArrayList<ArrayList<Vertex>>(
        Arrays.asList(new ArrayList<Vertex>(Arrays.asList(v1, v2, v3)), 
            new ArrayList<Vertex>(Arrays.asList(v4, v5, v6))));
    g.vertexList2 = g.flattenVertices();
    g.height = 1;
    g.width = 2;
    g.edgeList = new ArrayList<Edge>(Arrays.asList(e4, e3, e2, e1));
    g.vertexMap = g.makeVertexMap();
    g.spanningTree = g.unionFind();
    g.spanningTreeDisplay = g.spanningTree;
    WorldScene w = g.getEmptyScene();
    w.placeImageXY(v1.displayVertex(g.edgeList, true, true),
        v1.center.x * MazeWorld.SCALE + MazeWorld.SCALE / 2, 
        v1.center.y * MazeWorld.SCALE + MazeWorld.SCALE / 2);
    w.placeImageXY(v2.displayVertex(g.edgeList, true, true), 
        v2.center.x * MazeWorld.SCALE + MazeWorld.SCALE / 2,
        v2.center.y * MazeWorld.SCALE + MazeWorld.SCALE / 2);
    w.placeImageXY(v3.displayVertex(g.edgeList, true, true), 
        v3.center.x * MazeWorld.SCALE + MazeWorld.SCALE / 2, 
        v3.center.y * MazeWorld.SCALE + MazeWorld.SCALE / 2);
    w.placeImageXY(v4.displayVertex(g.edgeList, true, true), 
        v4.center.x * MazeWorld.SCALE + MazeWorld.SCALE / 2, 
        v4.center.y * MazeWorld.SCALE + MazeWorld.SCALE / 2);
    w.placeImageXY(v5.displayVertex(g.edgeList, true, true), 
        v5.center.x * MazeWorld.SCALE + MazeWorld.SCALE / 2, 
        v5.center.y * MazeWorld.SCALE + MazeWorld.SCALE / 2);
    w.placeImageXY(v6.displayVertex(g.edgeList, true, true), 
        v6.center.x * MazeWorld.SCALE + MazeWorld.SCALE / 2, 
        v6.center.y * MazeWorld.SCALE + MazeWorld.SCALE / 2);
    return t.checkExpect(g.makeScene(), w);

  }

  //test display for player
  public boolean testDisplayPlayer(Tester t) {
    Player user = new Player(0, 0);
    return t.checkExpect(user.display(), 
        new EllipseImage(MazeWorld.SCALE, MazeWorld.SCALE, OutlineMode.SOLID, Color.YELLOW));
  }

  //test move for Player
  public boolean testMovePlayer(Tester t) {
    Player user = new Player(5,5);
    MazeWorld g = new MazeWorld();
    Vertex v1 = new Vertex(new MyPosn(5,5));
    Vertex v2 = new Vertex(new MyPosn(4, 5));
    Vertex v3 = new Vertex(new MyPosn(6, 5));
    Vertex v4 = new Vertex(new MyPosn(5, 4));
    Vertex v5 = new Vertex(new MyPosn(5, 6));
    Vertex v6 = new Vertex(new MyPosn(5, 7));
    Edge e1 = new Edge(v1, v2, 10);
    Edge e2 = new Edge(v1, v3, 9);
    Edge e3 = new Edge(v1, v4, 8);
    Edge e4 = new Edge(v1, v5, 7);
    v1.outEdges.add(e1);
    v2.outEdges.add(e1);
    v1.outEdges.add(e2);
    v3.outEdges.add(e2);
    v1.outEdges.add(e3);
    v4.outEdges.add(e3);
    v1.outEdges.add(e4);
    v5.outEdges.add(e4);
    g.vertexList = new ArrayList<ArrayList<Vertex>>(
        Arrays.asList(new ArrayList<Vertex>(Arrays.asList(v1, v2, v3)), 
            new ArrayList<Vertex>(Arrays.asList(v4, v5, v6))));
    g.vertexList2 = g.flattenVertices();
    g.height = 1;
    g.width = 2;
    g.edgeList = new ArrayList<Edge>(Arrays.asList(e4, e3, e2, e1));
    g.vertexMap = g.makeVertexMap();
    g.spanningTree = g.unionFind();
    g.spanningTreeDisplay = g.spanningTree;
    user.move("right", g.spanningTreeDisplay);
    boolean test1 = t.checkExpect(user.x == 6 && user.y == 5, true);
    user.move("right", g.spanningTreeDisplay);
    boolean test2 = t.checkExpect(user.x == 6 && user.y == 5, true);
    user = new Player(5,5);
    user.move("left", g.spanningTreeDisplay);
    user.move("left", g.spanningTreeDisplay);
    boolean test3 = t.checkExpect(user.x == 4 && user.y == 5, true);
    user = new Player(5,5);
    user.move("down", g.spanningTreeDisplay);
    user.move("down", g.spanningTreeDisplay);
    boolean test4 = t.checkExpect(user.x == 5 && user.y == 6, true);
    user = new Player(5,5);
    user.move("up", g.spanningTreeDisplay);
    user.move("up", g.spanningTreeDisplay);
    boolean test5 = t.checkExpect(user.x == 5 && user.y == 4, true);
    return test1 && test2 && test3 && test4 && test5; 
  }


  //tests onKey
  boolean testOnKey(Tester t) {
    MazeWorld g = new MazeWorld();
    g.vertexList2.add(new Vertex(new MyPosn(1000, 1000)));
    g.onKeyEvent("r");
    boolean test1 = t.checkExpect(
        g.vertexList2.contains(new Vertex(new MyPosn(1000, 1000))), false)
        && t.checkExpect(g.vertexList.size(), MazeWorld.mHEIGHT)
        && t.checkExpect(g.vertexList.get(0).size(), MazeWorld.mWIDTH)
        && t.checkExpect(g.vertexList2.size(), MazeWorld.mWIDTH * MazeWorld.mHEIGHT);
    g.go = true;
    g.spanningTreeDisplay = g.spanningTree;
    g.onKeyEvent("b");
    boolean test2 = t.checkExpect(g.secondTravel, true);
    g.onKeyEvent("r");
    g.spanningTreeDisplay = g.spanningTree;
    g.go = true;
    g.onKeyEvent("d");
    boolean test3 = t.checkExpect(g.secondTravel, true);
    boolean test4 = t.checkExpect(g.showFirstPath, true) &&
        t.checkExpect(g.showSecondPath, true);
    g.onKeyEvent("1");
    boolean test5 = t.checkExpect(g.showFirstPath, false) &&
        t.checkExpect(g.showSecondPath, true);
    g.onKeyEvent("2");
    boolean test6 = t.checkExpect(g.showFirstPath, false) &&
        t.checkExpect(g.showSecondPath, false);
    g.onKeyEvent("v");
    boolean test7 = t.checkExpect(g.vVal == 20 && g.hVal == 1, true);
    g.onKeyEvent("h");
    boolean test8 = t.checkExpect(g.vVal == 1 && g.hVal == 20, true);
    g.onKeyEvent("right");
    boolean test9 = t.checkExpect(g.user.x == 0, true) &&
        t.checkExpect(g.user.y == 0, true);
    return test1 && test2 && test3 && test4 && test5 && test6
        && test7 && test8 && test9;


  }

  //to test bfs
  public boolean testBFS(Tester t) {
    MazeWorld g = new MazeWorld();
    g.spanningTreeDisplay = g.spanningTree;
    return t.checkExpect(g.bfs(g.vertexList2.get(0), 
        g.vertexList2.get(MazeWorld.mWIDTH * MazeWorld.mHEIGHT - 1)), true)
        && t.checkExpect(g.bfs(g.vertexList2.get(0), 
            new Vertex(new MyPosn(-10, -10))), false);
  }

  //to test dfs
  public boolean testDFS(Tester t) {
    MazeWorld g = new MazeWorld();
    g.spanningTreeDisplay = g.spanningTree;
    return t.checkExpect(g.dfs(g.vertexList2.get(0), 
        g.vertexList2.get(MazeWorld.mWIDTH * MazeWorld.mHEIGHT - 1)), true)
        && t.checkExpect(g.dfs(g.vertexList2.get(0), 
            new Vertex(new MyPosn(-10, -10))), false);
  }

  //to test searchHelp
  public boolean testSearchHelp(Tester t) {
    MazeWorld g = new MazeWorld();
    MazeWorld g2 = new MazeWorld();
    g.user = new Player(5,5);
    g2.user = new Player(5,5);
    g.spanningTreeDisplay = g.spanningTree;
    g2.spanningTreeDisplay = g2.spanningTree;
    return t.checkExpect(g.searchHelp(g.vertexList2.get(0), 
        g.vertexList2.get(MazeWorld.mWIDTH * MazeWorld.mHEIGHT - 1),
        new Stack<Vertex>()), true)
        && t.checkExpect(g2.searchHelp(g2.vertexList2.get(0), 
            g2.vertexList2.get(MazeWorld.mWIDTH * MazeWorld.mHEIGHT - 1),
            new Queue<Vertex>()), true);

  }


  //to test reconstruct
  public boolean testReconstruct(Tester t) {
    MazeWorld g = new MazeWorld();
    MazeWorld g2 = new MazeWorld();
    g.user = new Player(5,5);
    g2.user = new Player(5,5);
    g.spanningTreeDisplay = g.spanningTree;
    g2.spanningTreeDisplay = g2.spanningTree;
    g.searchHelp(g.vertexList2.get(0), 
        g.vertexList2.get(MazeWorld.mWIDTH * MazeWorld.mHEIGHT - 1),
        new Stack<Vertex>());
    g2.searchHelp(g2.vertexList2.get(0), 
        g2.vertexList2.get(MazeWorld.mWIDTH * MazeWorld.mHEIGHT - 1),
        new Queue<Vertex>());
    return t.checkExpect(g.vertexList2
        .get(MazeWorld.mWIDTH * MazeWorld.mHEIGHT - 1).travelledOnce, true)
        && t.checkExpect(g.vertexList2
            .get(0).travelledTwice, true)
        && t.checkExpect(g.vertexList2.get(MazeWorld.mWIDTH 
            * MazeWorld.mHEIGHT - 1).travelledTwice, true)
        && t.checkExpect(g2.vertexList2.get(MazeWorld.mWIDTH 
            * MazeWorld.mHEIGHT - 1).travelledOnce, true)
        && t.checkExpect(g.vertexList2.get(MazeWorld.mWIDTH 
            * MazeWorld.mHEIGHT - 1).travelledTwice, true)
        && t.checkExpect(g2.vertexList2
            .get(0).travelledTwice, true);

  }

  //to test onTick
  public boolean testOnTick(Tester t) {
    MazeWorld g = new MazeWorld();
    boolean test1 = t.checkExpect(g.spanningTreeDisplay.size(), 0)
        && t.checkExpect(g.spanningTree.size(),
            MazeWorld.mWIDTH * MazeWorld.mHEIGHT - 1);
    g.onTick();
    boolean test2 = t.checkExpect(g.spanningTreeDisplay.size(), 1)
        && t.checkExpect(g.spanningTree.size(),
            MazeWorld.mWIDTH * MazeWorld.mHEIGHT - 2);
    g.bfs(g.vertexList2.get(0), g.vertexList2.get(g.vertexList2.size() - 1));
    g.go = true;
    g.secondTravel = true;
    g.onTick();
    return test1 && test2;

  }

  //to test userTravel
  public boolean testUserTravel(Tester t) {
    MazeWorld g = new MazeWorld();
    boolean test0 =  t.checkExpect(g.vertexList2
        .get(1).travelledOnce, false);
    g.user = new Player(1, 0);
    g.userTravel();
    boolean test1 = t.checkExpect(g.vertexList2
        .get(1).travelledOnce, true);
    g.secondTravel = true;
    g.userTravel();
    boolean test2 = t.checkExpect(g.vertexList2.get(1).travelledTwice
        && g.vertexList2.get(1).travelledOnce, true);
    return test1 && test2 && test0;
  }

  //to test updateImage
  public boolean testUpdateImage(Tester t) {
    MazeWorld g = new MazeWorld();
    Vertex v = g.vertexList2.get(0);
    g.spanningTreeDisplay = g.spanningTree;
    v.updateImg(g.spanningTreeDisplay, g.showFirstPath, g.showSecondPath);
    return t.checkExpect(v.img, 
        new FrozenImage(v.displayVertex(g.spanningTreeDisplay, 
            g.showFirstPath, g.showSecondPath)));

  }



}