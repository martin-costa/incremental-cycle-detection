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

  // gets outdeg of u
  public int outDeg(int u) {
    return adjList[u].size();
  }

  // add a edge (u,v) to the graph
  public void addEdge(int u, int v) {
    if (u < 0 || u >= n || v < 0 || v >= n || adjList[u].contains(v)) return;
    m++;
    adjList[u].add(v);
  }

  // removes an edge from the graph
  public void removeEdge(int u, int v) {
    if (u < 0 || u >= n || v < 0 || v >= n || !adjList[u].contains(v)) return;
    m--;
    adjList[u].remove(new Integer(v));
  }

  // get the reach of u in G
  public HashSet<Integer> reach(int u) {

    HashSet<Integer> S = new HashSet<Integer>();
    S.add(u);

    return reach(S);
  }

  // get the reach of S in G
  public HashSet<Integer> reach(HashSet<Integer> S) {

    HashSet<Integer> V = new HashSet<Integer>();
    for (int i = 0; i < n; i++) {
      V.add(i);
    }

    HashSet<Integer> F = new HashSet<Integer>();

    restrictedDFS(S, V, F);

    return F;
  }

  // can u reach v in G
  public boolean canReach(int u, int v) {

    HashSet<Integer> V = new HashSet<Integer>();
    for (int i = 0; i < n; i++) {
      V.add(i);
    }

    HashSet<Integer> F = new HashSet<Integer>();

    restrictedDFS(u, V, F);

    return F.contains(v);

  }

  // perform a DFS only visiting nodes in the set E starting from all nodes in start
  public void restrictedDFS(Collection<Integer> start, Collection<Integer> E, Collection<Integer> F) {
    if (!E.containsAll(start)) return;

    // start exploring from all nodes in start
    for (int s : start) {
      if (!F.contains(s)) {
        restrictedDFS(s, E, F);
      }
    }
  }

  // perform a DFS only visiting nodes in the set E starting from s
  public void restrictedDFS(int s, Collection<Integer> E, Collection<Integer> F) {
    if (!E.contains(s)) return;

    // maintains path from s to node being explored
    Stack<Edge> P = new Stack<Edge>();

    // edges to be explored
    Stack<Edge> S = new Stack<Edge>();

    S.push(new Edge(s,s));

    while(!S.empty()) {
      Edge e = S.pop();

      int x = e.l, y = e.r;

      // visit node y if not yet visited
      if (!F.contains(y)) {
        F.add(y);
        P.push(e);
        for (int z : adjList[y]) {
          if (E.contains(z) && !F.contains(z)) S.push(new Edge(y,z));
        }
      }

      // backtrack if necessary
      while((!S.empty() && S.peek().l != P.peek().r) || (S.empty() && !P.empty())) {
        P.pop();
      }
    }
  }

  // class to store DFS numberings
  class DFSnumbering {

    public int[] pre;
    public int[] post;

    public int i = 0;
    public int j = 0;

    public int n;

    public DFSnumbering(int n) {
      this.n = n;
      this.pre = new int[n];
      this.post = new int[n];
    }
  }

  // perform DFS starting at s returns DFS numbering
  public void DFS(int s, DFSnumbering numbering) {
    if (s < 0 || s >= n) return;

    // create arrays for DFS numberings
    if (numbering == null || numbering.n < n) {
      numbering = new DFSnumbering(n);
    }

    // maintains path from s to node being explored
    Stack<Edge> P = new Stack<Edge>();

    // edges to be explored
    Stack<Edge> S = new Stack<Edge>();

    S.push(new Edge(s,s));

    while(!S.empty()) {
      Edge e = S.pop();

      int x = e.l, y = e.r;

      // visit node y if not yet visited
      if (numbering.pre[y] == 0) {
        P.push(e);
        numbering.pre[y] = ++numbering.j;
        for (int z : adjList[y]) {
          if (numbering.pre[z] == 0) S.push(new Edge(y,z));
        }
      }

      // backtrack if necessary
      while((!S.empty() && S.peek().l != P.peek().r) || (S.empty() && !P.empty())) {
        numbering.post[P.pop().r] = ++numbering.i;
      }
    }
  }

  // performs DFS over whole graph
  public void DFS(DFSnumbering numbering) {

    // create arrays for DFS numberings
    if (numbering == null || numbering.n < n) {
      numbering = new DFSnumbering(n);
    }

    for (int u = 0; u < n; u++) {
      if (numbering.pre[u] == 0) {
        DFS(u, numbering);
      }
    }
  }

  // checks if this graph is acyclic
  public boolean isAcyclic() {

    DFSnumbering numbering = new DFSnumbering(n);

    // get DFS numberings of graph
    DFS(numbering);

    // note that inverse of post number defines a topological ordering on this
    for (int u = 0; u < n; u++) {
      for (Integer v : adjList[u]) {
        // ensure (u,v) agrees with is
        if (numbering.post[u] < numbering.post[v]) return false;
      }
    }
    return true;
  }

  // get a stack of the edges in this graph
  public ArrayList<Edge> getEdges() {

    ArrayList<Edge> edges = new ArrayList<Edge>(m);

    for (int u = 0; u < n; u++) {
      for (Integer v : adjList[u]) {
        edges.add(new Edge(u, v));
      }
    }

    java.util.Collections.shuffle(edges);
    return edges;
  }

  // generates a DAG on n nodes
  public static Graph generateDAG(int n, double p) {

    Graph G = new Graph(n);
    Random rnd = new Random();

    // generate a random permutation
    ArrayList<Integer> perm = new ArrayList<Integer>(n);
    for (int i = 0; i < n; i++) {
      perm.add(i);
    }
    java.util.Collections.shuffle(perm);

    for (int i = 0; i < n; i++) {
      for (int j = i+1; j < n; j++) {
        if (rnd.nextDouble() < p) {
          G.addEdge(perm.get(i), perm.get(j));
        }
      }
    }
    return G;
  }

  // generates graph on n nodes
  public static Graph generateGraph(int n, double p) {

    Graph G = new Graph(n);
    Random rnd = new Random();

    // generate a random permutation
    ArrayList<Integer> perm = new ArrayList<Integer>(n);
    for (int i = 0; i < n; i++) {
      perm.add(i);
    }
    java.util.Collections.shuffle(perm);

    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        if (i != j && rnd.nextDouble() < p) {
          G.addEdge(perm.get(i), perm.get(j));
          if (!G.isAcyclic()) {
            G.removeEdge(perm.get(i), perm.get(j));
          }
        }
      }
    }
    return G;
  }

  // generates graph on n nodes
  public static Graph generateTree(int n, double p) {

    Graph G = new Graph(n);
    Random rnd = new Random();

    // generate a random permutation
    ArrayList<Integer> perm = new ArrayList<Integer>(n);
    for (int i = 0; i < n; i++) {
      perm.add(i);
    }
    java.util.Collections.shuffle(perm);

    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        if (i != j && rnd.nextDouble() < p) {
          if (G.outDeg(perm.get(i)) < 1) {
            G.addEdge(perm.get(i), perm.get(j));
            if (!G.isAcyclic()) {
              G.removeEdge(perm.get(i), perm.get(j));
            }
          }
        }
      }
    }
    return G;
  }

  // generates graph on n nodes
  public static Graph generateRootedDAG(int n, double p) {

    Graph G = generateTree(n, p);
    int r = G.getDAGRoot();
    Random rnd = new Random();

    // generate a random permutation
    ArrayList<Integer> perm = new ArrayList<Integer>(n);
    for (int i = 0; i < n; i++) {
      perm.add(i);
    }
    java.util.Collections.shuffle(perm);

    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        if (i != j && rnd.nextDouble() < p && !G.getAdjList()[perm.get(i)].contains(perm.get(j))) {
          G.addEdge(perm.get(i), perm.get(j));
          if (!G.isAcyclic()) {
            G.removeEdge(perm.get(i), perm.get(j));
          }
        }
      }
    }
    return G;
  }

  // returns any node of out-degree 0
  public int getDAGRoot() {

    int u = -1;
    int max = n;

    for (int i = 0; i < n; i++) {
      if (adjList[i].size() < max) {
        u = i;
        max = adjList[i].size();
      }
    }
    return u;
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

  // gets adjacency list
  public LinkedList<Integer>[] getAdjList() { return adjList; }

  // get the number of nodes
  public int N() { return this.n; }

  // get the number of edges
  public int M() { return this.m; }
}
