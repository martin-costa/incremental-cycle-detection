import java.util.*;

// runs variants of the simple search algorithm:
// one way
// two way smaller
// two way full
// two way greedy
public class SimpleSearch {

  // stores the type of search to perform
  private int rearrageType = 0;

  // number of nodes in graph
  private int n;

  // stores the graph
  private Graph forward;

  // reverse of forward
  private Graph backward;

  // total recourse of algo
  private int totalRecourse = 0;

  // total amount of critical insertions to tracedNode
  public int critical = 0;

  // total amount of right-critical insertions to tracedNode
  public int rightCritical = 0;

  // value of potential function of tracedNode
  public int phi = 0;

  // node to be traced
  public int tracedNode = 0;

  // recourse of tracedNode
  public int rec = 0;

  // stores the order of the graph
  private ArrayList<Integer> order;

  public SimpleSearch(int n) {

    this.n = n;
    this.forward = new Graph(n);
    this.backward = new Graph(n);

    this.order = new ArrayList<Integer>(n);
    for (int i = 0; i < n; i++) {
      this.order.add(i);
    }
    Collections.shuffle(this.order);
  }

  // handle an edge insertion
  public void insert(int u, int v) {

    // check if a crit insertion for tracedNode has occured
    if (forward.canReach(v, tracedNode)) {
      if (!forward.canReach(u, tracedNode)) {
        critical++;
      }
    }

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

    boolean isRightCrit = false;

    if (F.contains(tracedNode)) {
      rightCritical++;
      isRightCrit = true;
    }

    // ONE WAY SEARCH:
    if (rearrageType == 3) {

      Random rnd = new Random();

      if (rnd.nextDouble() <= 0) {
        ArrayList<Integer> orderB = new ArrayList<Integer>();

        // move B to the left of v
        // get ordering of B
        orderB.addAll(order);
        orderB.retainAll(B);

        // fix ordering
        order.removeAll(B);
        order.addAll(start, orderB);

        totalRecourse += B.size();
      }
      else {
        ArrayList<Integer> orderF = new ArrayList<Integer>();

        // move F to the right of u
        // get ordering of F
        orderF.addAll(order);
        orderF.retainAll(F);

        // fix ordering
        order.removeAll(F);
        order.addAll(end - F.size() + 1, orderF);

        totalRecourse += F.size();

        if (F.contains(tracedNode)) rec++;
      }
    }

    // TWO WAY GREEDY:
    else if (rearrageType == 0) {

      int x = u;
      int y = v;
      int px = end;
      int py = start;
      int i = 0;

      // get position in orderings of nodes from B and F
      PriorityQueue<Integer> posF = new PriorityQueue<Integer>();
      PriorityQueue<Integer> posB = new PriorityQueue<Integer>();

      for (int w : F)
        posF.add(order.indexOf(w));

      for (int w : B)
        posB.add(n - order.indexOf(w));

      F = new HashSet<Integer>();
      B = new HashSet<Integer>();

      // search from u and v one node at a time
      while (order.indexOf(y) < order.indexOf(x)) {

        // forward search
        if (i == 0) {
          if (posF.size() <= 0) break;
          py = posF.poll();
          y = order.get(py);
          F.add(y);
        }

        // backward search
        if (i == 1) {
          if (posB.size() <= 0) break;
          px = posB.poll();
          px = n - px;
          x = order.get(px);
          B.add(x);
        }

        i = 1 - i;
      }

      ArrayList<Integer> orderF = new ArrayList<Integer>();
      ArrayList<Integer> orderB = new ArrayList<Integer>();

      orderF.addAll(order);
      orderB.addAll(order);

      orderF.retainAll(F);
      orderB.retainAll(B);

      // if two searches met
      if (order.indexOf(y) > order.indexOf(x)) {

        order.removeAll(B);
        order.addAll(px, orderB);

        py = order.indexOf(y);
        order.removeAll(F);
        order.addAll(py - F.size() + 1, orderF);

        totalRecourse += B.size() + F.size();
      }

      // if backward search terminates
      else if (posB.size() <= 0) {
        order.removeAll(B);
        order.addAll(start, orderB);
        totalRecourse += B.size();
      }

      // if forward search terminates
      else if (posF.size() <= 0) {
        order.removeAll(F);
        order.addAll(end - F.size() + 1, orderF);
        totalRecourse += F.size();
      }
    }

    // TWO WAY SMALLEST:
    else if (rearrageType == 1) {

      // which set of nodes do we move in the ordering
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
    }

    // TWO WAY FULL:
    else if (rearrageType == 2) {

      ArrayList<Integer> orderF = new ArrayList<Integer>();
      ArrayList<Integer> orderB = new ArrayList<Integer>();

      orderF.addAll(order);
      orderB.addAll(order);

      orderF.retainAll(F);
      orderB.retainAll(B);

      order.removeAll(F);
      order.removeAll(B);

      order.addAll(start, orderB);
      order.addAll(end - F.size() + 1, orderF);

      totalRecourse += F.size() + B.size();
    }
  }

  public void insert(Edge e) {
    insert(e.l, e.r);
  }

  // set search params
  public void setGreedy() { this.rearrageType = 0; }

  public void setSmall() { this.rearrageType = 1; }

  public void setFull() { this.rearrageType = 2; }

  public void setOneWay() { this.rearrageType = 3; }

  // setters and getters
  public ArrayList<Integer> getOrdering() { return this.order; }

  public int getTotalRecourse() { return this.totalRecourse; }
}
