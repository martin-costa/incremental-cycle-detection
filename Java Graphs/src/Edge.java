// edge class to be used in algorithms
public class Edge {

  int l;
  int r;

  Edge(int l, int r) {
    this.l = l;
    this.r = r;
  }

  public boolean equals(Object e) {
    return (l == ((Edge)e).l && r == ((Edge)e).r);
  }

  public String toString() {
    return "(" + Integer.toString(l) + "," + Integer.toString(r) + ")";
  }
}
