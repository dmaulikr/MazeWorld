// Assignment 10  -  part 2
// Kim Austin
// KimA
// Pine Michelle
// pinemichelle63

import java.util.*;

//Represents a mutable collection of items
interface ICollection<T> {
  
  // Is this collection empty?
  boolean isEmpty();
  
  // EFFECT: adds the item to the collection
  void add(T item);
  
  // Returns the first item of the collection
  // EFFECT: removes that first item
  T remove();
}

//represents a stack, in which items are added at the head
class Stack<T> implements ICollection<T> {
  ArrayDeque<T> contents;

  Stack() {
    this.contents = new ArrayDeque<T>();
  }

  Stack(ArrayDeque<T> contents) {
    this.contents = contents;
  }

  //determines if this stack is empty
  public boolean isEmpty() {
    return this.contents.isEmpty();
  }

  //EFFECT: removes an item from this stack
  //gives the removed item
  public T remove() {
    return this.contents.pop();
  }

  //EFFECT: adds an item to this stack
  public void add(T item) {
    this.contents.push(item);
  }

}



//represents a queue of items, in which items are added at the tail
class Queue<T> implements ICollection<T> {

  ArrayDeque<T> contents;

  Queue() {
    this.contents = new ArrayDeque<T>();
  }

  Queue(ArrayDeque<T> contents) {
    this.contents = contents;
  }

  //determines if this queue is empty
  public boolean isEmpty() {
    return this.contents.isEmpty();
  }

  //EFFECT: removes an item from this queue
  //gives the removed item
  public T remove() {
    return this.contents.remove();
  }

  //EFFECT: adds an item to this queue
  public void add(T item) {
    this.contents.add(item); 
  }
}


//to represent a utilities class
class Utils {


  // EFFECT: Sorts the provided list according to the given comparator
  <T> void mergesort(ArrayList<T> arr, IComparator<T> comp) {
    ArrayList<T> temp = new ArrayList<T>();
    for (int i = 0; i < arr.size(); i = i + 1) {
      temp.add(arr.get(i));
    }
    mergesortHelp(arr, temp, comp, 0, arr.size());
  }

  // EFFECT: Sorts the provided list in the region [loIdx, hiIdx) according to the given comparator.
  //  Modifies both lists in the range [loIdx, hiIdx)
  <T> void mergesortHelp(ArrayList<T> source, ArrayList<T> temp, IComparator<T> comp,
      int loIdx, int hiIdx) {
    // Step 0: stop when finished
    if (hiIdx - loIdx <= 1) {
      return; // nothing to sort
    }
    // Step 1: find the middle index
    int midIdx = (loIdx + hiIdx) / 2;
    // Step 2: recursively sort both halves
    mergesortHelp(source, temp, comp, loIdx, midIdx);
    mergesortHelp(source, temp, comp, midIdx, hiIdx);
    // Step 3: merge the two sorted halves
    merge(source, temp, comp, loIdx, midIdx, hiIdx);
  }


  // Merges the two sorted regions [loIdx, midIdx) and [midIdx, hiIdx) from source
  // into a single sorted region according to the given comparator
  // EFFECT: modifies the region [loIdx, hiIdx) in both source and temp
  <T> void merge(ArrayList<T> source, ArrayList<T> temp, IComparator<T> comp,
      int loIdx, int midIdx, int hiIdx) {
    int curLo = loIdx;   // where to start looking in the lower half-list
    int curHi = midIdx;  // where to start looking in the upper half-list
    int curCopy = loIdx; // where to start copying into the temp storage
    while (curLo < midIdx && curHi < hiIdx) {
      if (comp.compare(source.get(curLo), source.get(curHi)) <= 0) {
        // the value at curLo is smaller, so it comes first
        temp.set(curCopy, source.get(curLo));
        curLo = curLo + 1; // advance the lower index
      }
      else {
        // the value at curHi is smaller, so it comes first
        temp.set(curCopy, source.get(curHi));
        curHi = curHi + 1; // advance the upper index
      }
      curCopy = curCopy + 1; // advance the copying index
    }
    // copy everything that's left -- at most one of the two
    //half-lists still has items in it
    while (curLo < midIdx) {
      temp.set(curCopy, source.get(curLo));
      curLo = curLo + 1;
      curCopy = curCopy + 1;
    }
    while (curHi < hiIdx) {
      temp.set(curCopy, source.get(curHi));
      curHi = curHi + 1;
      curCopy = curCopy + 1;
    }
    // copy everything back from temp into source
    for (int i = loIdx; i < hiIdx; i = i + 1) {
      source.set(i, temp.get(i));
    }
  }

  //An andMap for ArrayLists
  <T> boolean andMap(ArrayList<T> a, IComparator<T> f) {
    boolean notFalse = true;
    for (int i = 1; i < a.size() - 1; i = i + 1) {
      notFalse = f.compare(a.get(i), a.get(i + 1)) <= 0;
    }
    return notFalse;
  }

  //searches a list of edges for a particular edge
  boolean findEdge(ArrayList<Edge> edges, Vertex v1, Vertex v2) {
    return edges.contains(new Edge(v1, v2, 0));
  }



}


//to represent an comparison function object
interface IComparator<T> {
  int compare(T arg, T arg2);
}

//to represent a comparator that sorts items in ascending order
class AscendingOrder implements IComparator<Edge> {

  public int compare(Edge arg, Edge arg2) {
    return arg.weight - arg2.weight;
  }

}




