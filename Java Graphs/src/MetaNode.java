import java.util.*;

// the class for the meta-nodes in the meta-tree
public class MetaNode {

  // true iff this is a leaf
  private boolean leaf = true;

  // the nodes attached to this (if this is a leaf)
  private HashSet<Integer> set;

  // the ordering of the nodes attached to this (if this is a leaf)
  private ArrayList<Integer> order;

  // the children of this
  private MetaNode left;
  private MetaNode mid;
  private MetaNode right;

  // constructor to create new meta-node
  public MetaNode(HashSet<Integer> set, ArrayList<Integer> order) {
    this.set = set;
    this.order = order;
  }

  // call to breakdown the nodes into 3 children
  public boolean breakDown(MetaNode left, MetaNode mid, MetaNode right) {

    // can only be done if this is not a leaf
    if (!this.leaf) return false;

    // make this an internal meta-node and remove attached nodes
    this.leaf = false;
    this.set = null;

    // give it 3 children
    this.left = left;
    this.mid = mid;
    this.right = right;

    return true;
  }

  // check if this meta-leaf contains u in its set
  public boolean contains(int u) {
    return getSet().contains(u);
  }

  // place nodes into set
  public void updateSet(HashSet<Integer> newSet, ArrayList<Integer> newOrder) {
    if (!leaf) return;
    this.set = newSet;
    this.order = newOrder;
  }

  // remove nodes from set
  public void removeFromSet(HashSet<Integer> removed) {
    if (leaf) {
      this.set.removeAll(removed);
      this.order.removeAll(removed);
      return;
    }
    left.removeFromSet(removed);
    mid.removeFromSet(removed);
    right.removeFromSet(removed);
  }

  // gets the union of the sets attached to meta-leaves in the subtree of this
  public HashSet<Integer> getSet() {
    if (leaf)
      return this.set;

    HashSet<Integer> S = new HashSet<Integer>();

    S.addAll(left.getSet());
    S.addAll(mid.getSet());
    S.addAll(right.getSet());

    return S;
  }

  public ArrayList<Integer> getOrder() {
    if (leaf)
      return this.order;

    ArrayList<Integer> O = new ArrayList<Integer>();

    O.addAll(left.getOrder());
    O.addAll(mid.getOrder());
    O.addAll(right.getOrder());

    return O;
  }

  // setters and getters
  public MetaNode getLeft() { return this.left; }
  public MetaNode getMid() { return this.mid; }
  public MetaNode getRight() { return this.right; }

  public boolean isLeaf() { return this.leaf; }
}
