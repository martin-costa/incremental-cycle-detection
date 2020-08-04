import java.util.*;

public class DivideAndConquer {

  // amount of nodes
  private int n;

  // total totalRecourse of algorithm
  public int totalRecourse = 0;

  // maintains graph as edges are added
  private Graph forward;

  // maintains reverse of graph as edges are added
  private Graph backward;

  // the meta-tree maintained by the algorithm
  private MetaTree T;

  public DivideAndConquer(int n) {
    this.n = n;

    this.forward = new Graph(n);
    this.backward = new Graph(n);

    this.T = new MetaTree(n);
  }

  // get topo. ordering defined here
  public ArrayList<Integer> getOrdering() {
    return T.getRoot().getOrder();
  }

  // insert an edge into the graph
  public boolean insert(int u, int v) {

    forward.addEdge(u, v);
    backward.addEdge(v, u);

    // classify the insertion of the edge
    int type = T.classifyInsertion(u, v);

    // type 1 insertion: do nothing
    if (type == 1) return true;

    //type 3 insertion: absorb free nodes
    if (type == 3) {

      MetaNode x = T.getMetaNode(u);
      MetaNode y = T.getMetaNode(v);

      // least common ancestor of x and y
      MetaNode z = T.leastCommonAncestor(x, y);

      HashSet<Integer> F = z.getMid().getSet();
      ArrayList<Integer> orderF = z.getMid().getOrder();

      HashSet<Integer> A = new HashSet<Integer>();
      HashSet<Integer> B = new HashSet<Integer>();

      int recourse = 0;

      if (F.contains(v)) {
        forward.restrictedDFS(v, F, A);
        ArrayList<Integer> orderA = new ArrayList<Integer>();
        orderA.addAll(orderF);
        orderA.retainAll(A);
        z.getMid().removeFromSet(A);
        recourse = T.ADD(z.getRight(), A, B, orderA, new ArrayList<Integer>(), forward, backward);
      }
      if (F.contains(u)) {
        backward.restrictedDFS(u, F, B);
        ArrayList<Integer> orderB = new ArrayList<Integer>();
        orderB.addAll(orderF);
        orderB.retainAll(B);
        z.getMid().removeFromSet(B);
        recourse = T.ADD(z.getLeft(), A, B, new ArrayList<Integer>(), orderB, forward, backward);
      }

      totalRecourse += recourse;

      // break it down into an STP after if they are in same set
      if (T.getMetaNode(u).equals(T.getMetaNode(v)))
        type = 2;
    }

    //type 2 insertion: break down into further STP
    if (type == 2) {

      // the set containing both u and v
      MetaNode x = T.getMetaNode(u);

      // nodes we wants to search
      HashSet<Integer> S = x.getSet();

      // nodes found in forward search
      HashSet<Integer> R = new HashSet<Integer>();

      // nodes found in backward search
      HashSet<Integer> L = new HashSet<Integer>();

      forward.restrictedDFS(v, S, R);
      backward.restrictedDFS(u, S, L);

      int recourse = 0;

      recourse += L.size(); // CAN BE IMPROVED
      recourse += R.size();

      totalRecourse += recourse;

      // break down x into 3 children
      ArrayList<Integer> orderL = new ArrayList<Integer>();
      orderL.addAll(x.getOrder());
      orderL.retainAll(L);

      MetaNode xL = new MetaNode(L, orderL);

      ArrayList<Integer> orderR = new ArrayList<Integer>();
      orderR.addAll(x.getOrder());
      orderR.retainAll(R);

      MetaNode xR = new MetaNode(R, orderR);

      HashSet<Integer> F = new HashSet<Integer>();
      F.addAll(S);
      F.removeAll(L);
      F.removeAll(R);

      ArrayList<Integer> orderF = new ArrayList<Integer>();
      orderF.addAll(x.getOrder());
      orderF.retainAll(F);

      MetaNode xF = new MetaNode(F, orderF);

      x.breakDown(xL, xF, xR);
    }

    return false;
  }

  public boolean insert(Edge e) {
    return insert(e.l, e.r);
  }

  public int depth() {
    return T.depth();
  }

  public String toString() {
    return T.toString();
  }

  // setters and getters
  public int N() { return n; }
}
