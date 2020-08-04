import java.util.*;

public class GreedyAlgorithm {

  // number of nodes in graph
  private int n;

  // stores the graph
  private Graph forward;

  // reverse of forward
  private Graph backward;

  // total recourse of algo
  public int totalRecourse = 0;

  // for little l2 norm of recourse vector
  private int recourseSquared = 0;

  // stores the order of the graph
  private ArrayList<Integer> order;

  public GreedyAlgorithm(int n) {

    this.n = n;
    this.forward = new Graph(n);
    this.backward = new Graph(n);

    this.order = new ArrayList<Integer>(n);
    for (int i = 0; i < n; i++) {
      this.order.add(i);
    }
  }

  // handle an edge insertion
  public void insert(int u, int v) {

    forward.addEdge(u, v);
    backward.addEdge(v, u);

    // check if forward edge
    if (order.indexOf(u) < order.indexOf(v)) return;

    HashSet<Integer> S = new HashSet<Integer>();

    int end = order.indexOf(u);
    int start = order.indexOf(v);

    // create set of nodes between u and v in ordering
    for (int i = start; i <= end; i++) {
      S.add(order.get(i));
    }

    // sets to store nodes found in forwards and backwards searches
    HashSet<Integer> F = new HashSet<Integer>();
    HashSet<Integer> B = new HashSet<Integer>();

    // perform forwards and backwards searches
    backward.restrictedDFS(u, S, B);
    forward.restrictedDFS(v, S, F);

    // which set of nodes to we move in the ordering
    boolean moveB = B.size() <= F.size();

    // create the set of nodes to be moved and its ordering
    HashSet<Integer> X = moveB ? B : F;
    ArrayList<Integer> orderX = new ArrayList<Integer>();

    // create ordering of X (induced from current ordering)
    orderX.addAll(order);
    orderX.retainAll(X);

    // fix the ordering
    order.removeAll(X);
    if (moveB)
      order.addAll(start, orderX);
    else
      order.addAll(end - X.size() + 1, orderX);

    // add up the recourse
    totalRecourse += X.size();
    recourseSquared += X.size()*X.size();
  }

  public void insert(Edge e) {
    insert(e.l, e.r);
  }

  public double recoursel2() {
    return Math.sqrt(recourseSquared);
  }

  public ArrayList<Integer> getOrdering() { return this.order; }

}
