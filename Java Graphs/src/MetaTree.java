import java.util.*;

// the meta-tree class needed by the algorithm
public class MetaTree {

  // number of nodes attached to the meta-leaves of the tree
  private int n;

  // the root of this tree
  private MetaNode root;

  // constructor to create meta-tree
  public MetaTree(int n) {
    this.n = n;

    // create a set of all the nodes
    HashSet<Integer> set = new HashSet<Integer>();
    for (int i = 0; i < n; i++) set.add(i);

    // creates a random order
    ArrayList<Integer> order = new ArrayList<Integer>();
    order.addAll(set);
    java.util.Collections.shuffle(order);

    // create the root node
    this.root = new MetaNode(set, order);
  }

  // gets the set of the meta-node containing u
  public HashSet<Integer> getSet(int u) {
    return getMetaNode(u).getSet();
  }

  // gets the meta-node containing u
  public MetaNode getMetaNode(int u) {
    return metaNodeSearch(u, root);
  }

  private MetaNode metaNodeSearch(int u, MetaNode x) {

    // if x is a meta-leaf
    if (x.isLeaf()) {
      if (x.contains(u))
        return x;
      else
        return null;
    }

    // if x is an internal meta-node
    MetaNode y = metaNodeSearch(u, x.getLeft());
    if (y != null) return y;
    y = metaNodeSearch(u, x.getMid());
    if (y != null) return y;
    y = metaNodeSearch(u, x.getRight());
    if (y != null) return y;

    // if node not found
    return null;
  }

  public MetaNode leastCommonAncestor(int u, int v) {
    MetaNode x = getMetaNode(u);
    MetaNode y = getMetaNode(v);
    return leastCommonAncestor(x, y);
  }

  public MetaNode leastCommonAncestor(MetaNode x, MetaNode y) {
    if (x.equals(y)) return x;

    // get paths from root to each meta-node
    Stack<MetaNode> X = new Stack<MetaNode>();
    X.push(root);
    getPathFromRoot(X, x);

    Stack<MetaNode> Y = new Stack<MetaNode>();
    Y.push(root);
    getPathFromRoot(Y, y);

    while(X.size() < Y.size()) {
      Y.pop();
    }
    while(X.size() > Y.size()) {
      X.pop();
    }

    while(X.size() > 0) {
      if (X.peek().equals(Y.peek())) {
        return X.peek();
      }
      X.pop();
      Y.pop();
    }
    return null;
  }

  private boolean getPathFromRoot(Stack<MetaNode> S, MetaNode x) {

    MetaNode y = S.peek();

    // check if equal
    if (y.equals(x)) return true;

    // if leaf go back
    if (y.isLeaf()) {
      S.pop();
      return false;
    }

    S.push(y.getLeft());
    if (getPathFromRoot(S, x)) return true;

    S.push(y.getMid());
    if (getPathFromRoot(S, x)) return true;

    S.push(y.getRight());
    if (getPathFromRoot(S, x)) return true;

    S.pop();
    return false;
  }

  // classifies the insertion of an edge
  public int classifyInsertion(int u, int v) {
    return classifyInsertionSearch(u, v, root);
  }

  public int classifyInsertion(Edge e) {
    return classifyInsertion(e.l, e.r);
  }

  // recursively performs search to classify edge
  private int classifyInsertionSearch(int u, int v, MetaNode x) {

    // if x is a meta-leaf, check if it contains the nodes, and classify edge
    if (x.isLeaf()) {
      if (x.contains(u)) {
        if (x.contains(v))
          return 2; // type 2 insertion: u and v in same set
        else
          return 1; // type 1 insertion: u before v
      }
      if (x.contains(v)) {
        return 3; // type 3 insertion: v before u
      }
      return -1; // not in this set
    }

    // if x is an internal meta-node, make recursive calls
    int i = classifyInsertionSearch(u, v, x.getLeft());
    if (i > 0) return i;
    i = classifyInsertionSearch(u, v, x.getMid());
    if (i > 0) return i;
    i = classifyInsertionSearch(u, v, x.getRight());
    if (i > 0) return i;

    // if node not found in subtree of x, return -1
    return -1;
  }

  // adds the sets A and B to the set of x, returns total recourse
  public int ADD(MetaNode x, HashSet<Integer> A, HashSet<Integer> B, ArrayList<Integer> orderA, ArrayList<Integer> orderB, Graph forward, Graph backward) {

    if (A.isEmpty() && B.isEmpty()) return 0;

    // STEP 1: BASE CASE
    if (x.isLeaf()) {

      HashSet<Integer> newSet = new HashSet<Integer>();
      newSet.addAll(x.getSet());
      newSet.addAll(A);
      newSet.addAll(B);

      ArrayList<Integer> newOrder = new ArrayList<Integer>();
      newOrder.addAll(orderA);
      newOrder.addAll(x.getOrder());
      newOrder.addAll(orderB);

      x.updateSet(newSet, newOrder);
      return (A.size() + B.size());
    }

    // STEP 2: RECURSIVE CALL
    HashSet<Integer> L = x.getLeft().getSet();
    HashSet<Integer> F = x.getMid().getSet();
    HashSet<Integer> R = x.getRight().getSet();

    ArrayList<Integer> orderF = new ArrayList<Integer>();
    orderF.addAll(x.getMid().getOrder());

    // create the 6 sets used to partition A and B
    HashSet<Integer> LA = new HashSet<Integer>();
    HashSet<Integer> FA = new HashSet<Integer>();
    HashSet<Integer> RA = new HashSet<Integer>();
    HashSet<Integer> LB = new HashSet<Integer>();
    HashSet<Integer> FB = new HashSet<Integer>();
    HashSet<Integer> RB = new HashSet<Integer>();

    // STEP 3
    HashSet<Integer> start = new HashSet<Integer>();

    // reach-1(L) and A --> LA
    moveNodes(L, A, LA, backward);

    // reach-1(L) and B --> LB
    moveNodes(L, B, LB, backward);

    // reach(R) and A --> RA
    moveNodes(R, A, RA, forward);

    // reach(R) and B --> RB
    moveNodes(R, B, RB, forward);

    // this next part of step 3 could be made nicer -->

    // reach-1(L) and A --> LA
    moveNodes(LB, A, LA, backward);

    // reach-1(L) and B --> LB
    moveNodes(LA, B, LB, backward);

    // reach(R) and A --> RA
    moveNodes(RB, A, RA, forward);

    // reach(R) and B --> RB
    moveNodes(RA, B, RB, forward);

    // STEP 4
    start.addAll(LA);
    start.addAll(LB);
    moveNodes(start, F, LB, backward);
    start.clear();

    start.addAll(RA);
    start.addAll(RB);
    moveNodes(start, F, RA, forward);
    start.clear();

    HashSet<Integer> removedNodes = new HashSet<Integer>();
    removedNodes.addAll(LB);
    removedNodes.addAll(RA);
    removedNodes.retainAll(F);

    x.getMid().removeFromSet(removedNodes);
    F = x.getMid().getSet();

    // STEP 5
    removedNodes.clear();
    removedNodes.addAll(LA);
    removedNodes.addAll(RA);
    A.removeAll(removedNodes);

    removedNodes.clear();
    removedNodes.addAll(LB);
    removedNodes.addAll(RB);
    B.removeAll(removedNodes);

    FA.addAll(A); // initialA \ (LA and RA)
    FB.addAll(B); // initialB \ (LB and RB)

    // STEP 6: MAKE THE CALLS

    // create the 6 lists used to store the orders of the sets
    ArrayList<Integer> orderLA = new ArrayList<Integer>();
    ArrayList<Integer> orderFA = new ArrayList<Integer>();
    ArrayList<Integer> orderRA = new ArrayList<Integer>();
    ArrayList<Integer> orderLB = new ArrayList<Integer>();
    ArrayList<Integer> orderFB = new ArrayList<Integer>();
    ArrayList<Integer> orderRB = new ArrayList<Integer>();

    // can only contain nodes from one set
    orderLA.addAll(orderA);
    orderRB.addAll(orderB);
    orderFA.addAll(orderA);
    orderFB.addAll(orderB);

    orderLB.addAll(orderF);
    orderLB.addAll(orderB);

    orderRA.addAll(orderA);
    orderRA.addAll(orderF);

    orderLA.retainAll(LA);
    orderFA.retainAll(FA);
    orderRA.retainAll(RA);
    orderLB.retainAll(LB);
    orderFB.retainAll(FB);
    orderRB.retainAll(RB);

    int a = ADD(x.getLeft(), LA, LB, orderLA, orderLB, forward, backward);
    int b = ADD(x.getMid(), FA, FB, orderFA, orderFB, forward, backward);
    int c = ADD(x.getRight(), RA, RB, orderRA, orderRB, forward, backward);

    return (a + b + c);
  }

  // X and Y disjoint, finds reach_G(X) and Y, and puts it in Z (only for the specific structure of these graphs!!!)
  private void moveNodes(HashSet<Integer> X, HashSet<Integer> Y, HashSet<Integer> Z, Graph G) {

    HashSet<Integer> explore = new HashSet<Integer>();
    HashSet<Integer> found = new HashSet<Integer>();

    explore.addAll(X);
    explore.addAll(Y);

    G.restrictedDFS(X, explore, found);

    found.retainAll(Y);
    Z.addAll(found);
  }

  // note: these depth methods are horrifyingly implemented :) only used for a little test tho
  public int depth() {
    return depth(root);
  }

  private int depth(MetaNode x) {
    if (x.isLeaf()) return 0;

    int a = depth(x.getLeft());
    int b = depth(x.getMid());
    int c = depth(x.getRight());
    return 1 + Math.max(a, Math.max(b, c));
  }

  // to string method
  public String toString() {
    String s = stringSearch("", root);
    return "\u001B[31m[" + s + "\u001B[31m]\u001B[0m";
  }

  private String stringSearch(String s, MetaNode x) {

    // if x is a meta-leaf
    if (x.isLeaf()) {

      for (int u : x.getOrder()) {
        s = s + Integer.toString(u);
      }
      return s;

      // int i = x.getSet().size();
      //
      // for (int j = 0; j < i; j++) {
      //   s = s + "0";
      // }
      // return s;
    }

    // if x is an internal meta-node
    s = s + "\u001B[34m[";
    s = stringSearch(s, x.getLeft());
    s = s + "\u001B[34m]\u001B[31m[";

    s = stringSearch(s, x.getMid());
    s = s + "\u001B[31m]\u001B[32m[";

    s = stringSearch(s, x.getRight());
    s = s + "\u001B[32m]";

    return s;
  }

  // setters and getters
  public MetaNode getRoot() { return root; }
}
