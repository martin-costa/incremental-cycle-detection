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
    //java.util.Collections.shuffle(order);
  }

  public void insert(int u, int v) {

    forward.addEdge(u, v);
    backward.addEdge(v, u);

    // check if forward edge
    if (order.indexOf(u) < order.indexOf(v)) return;

    HashSet<Integer> S = new HashSet<Integer>();

    int start = order.indexOf(u);
    int end = order.indexOf(v);

    // create set of nodes between u and v in ordering
    for (int i = end; i <= start; i++) {
      S.add(order.get(i));
    }

    HashSet<Integer> F = new HashSet<Integer>();
    HashSet<Integer> B = new HashSet<Integer>();

    backward.restrictedDFS(u, S, B);
    forward.restrictedDFS(v, S, F);

    ArrayList<Integer> orderF = new ArrayList<Integer>();
    ArrayList<Integer> orderB = new ArrayList<Integer>();

    orderF.addAll(order);
    orderB.addAll(order);

    orderF.retainAll(F);
    orderB.retainAll(B);

    order.removeAll(F);
    order.removeAll(B);

    order.addAll(start - F.size() - B.size() + 1, orderF);
    order.addAll(end, orderB);

    S.removeAll(F);
    S.removeAll(B);

    //int rec = Math.min(B.size() + F.size(), Math.min(B.size() + S.size(), F.size() + S.size()));
    //totalRecourse += rec;

    totalRecourse += F.size() + B.size();
  }

  public void insert(Edge e) {
    insert(e.l, e.r);
  }

  public ArrayList<Integer> getOrdering() { return this.order; }

}
