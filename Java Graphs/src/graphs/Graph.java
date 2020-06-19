package graphs;

import java.util.*;

// these classes were made to run and test specific algorithms, so their
// implementation is not ideal for many standard operations

// the following graph implementation is directed

// stores a graph object
public class Graph {

  // edge class to be used in algorithms
  class Edge {

    int l;
    int r;

    Edge(int l, int r) {
      this.l = l;
      this.r = r;
    }
  }

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
    if (u < 0 || u >= n || v < 0 || v >= n || adjList[u].contains(v)) return;
    m++;
    adjList[u].add(v);
  }

  // perform DFS starting at s returns DFS numbering
  public int[] DFS(int s, boolean getpost) {
    if (s < 0 || s >= n) return null;

    // create arrays for DFS numberings
    int[] pre = new int[n];
    int[] post = new int[n];
    int i = 0, j = 0;

    // keeps track of visited nodes
    boolean[] visited = new boolean[n];

    // maintains path from s to node being explored
    Stack<Edge> P = new Stack<Edge>();

    // edges to be explored
    Stack<Edge> S = new Stack<Edge>();

    S.push(new Edge(s,s));

    while(!S.empty()) {
      Edge e = S.pop();

      int x = e.l, y = e.r;

      // visit node y if not yet visited
      if (!visited[y]) {
        visited[y] = true;
        P.push(e);
        pre[y] = ++j;
        for (int z : adjList[y]) {
          if (!visited[z]) S.push(new Edge(y,z));
        }
      }

      // backtrack if necessary
      while((!S.empty() && S.peek().l != P.peek().r) || (S.empty() && !P.empty())) {
        post[P.pop().r] = ++i;
      }
    }

    // returns either the postnumber or prenumber
    return getpost ? post : pre;
  }

  // turns the graph into a string
  public String toString() {
    String s = "n = " + String.valueOf(n) + "\n" + "m = " + String.valueOf(m) + "\n\n";

    for (int i = 0; i < n; i++) {
      s += String.valueOf(i) + ": ";
      for (Integer j : adjList[i]) {
        s += String.valueOf(j) + " ";
      }
      s += "\n";
    }
    return s;
  }

  // get the number of nodes
  public int N() { return this.n; }

  // get the number of edges
  public int M() { return this.m; }
}
