package graphs;

import java.util.*;

// these classes were made to run and test specific algorithms, so their
// implementation is not ideal for many standard operations

// the following graph implementation is directed

// stores a graph object
public class Graph {

  // the number of nodes
  private int n;

  // the number of edges
  private int m;

  // the adjacency list
  private LinkedList<Integer>[] adjList;

  // create an empty graph on n nodes
  @SuppressWarnings("unchecked")
  public Graph(int n) {
    this.n = n;
    this.m = 0;

    adjList = new LinkedList[n];
    for (int i = 0; i < n; i++) {
      adjList[i] = new LinkedList<Integer>();
    }
  }

  // add a edge (u,v) to the graph
  public void addEdge(int u, int v) {
    adjList[u].add(v);
  }

  // turns the graph into a string
  public String toString() {
    String s = "node count: " + String.valueOf(n) + "\n\n";

    for (int i = 0; i < n; i++) {
      s += String.valueOf(i) + ": ";
      for (Integer j : adjList[i]) {
        s += String.valueOf(j) + " ";
      }
      s += "\n";
    }
    return s;
  }

}
