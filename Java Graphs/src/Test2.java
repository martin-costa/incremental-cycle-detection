import java.util.*;

// run tests from here
class Test2 {

  // main method
  public static void main(String args[]) {

    int k = 500;
    int n = 3*k + 1;

    //double c = 3;

    //double p = 2*c/(n-1);

    //Graph G = Graph.generateRootedDAG(n, p);

    //System.out.println(G);

    ArrayList<Edge> E = new ArrayList<Edge>(n);

    // for (int i = k+1; i < 2*k; i++) {
    //   E.add(new Edge(i, i+1));
    // }
    // for (int i = 1; i <= k; i++) {
    //   E.add(new Edge(i, 0));
    //   E.add(new Edge(k+i, i));
    // }

    // for (int i = 1; i < k; i++) {
    //   E.add(new Edge(i, 0));
    //   E.add(new Edge(k+i, k+i+1));
    //   E.add(new Edge(k+i, i));
    // }
    // E.add(new Edge(k, 0));
    // E.add(new Edge(2*k, k));

    // full

    // for (int i = k+1; i < 2*k; i++) {
    //   E.add(new Edge(i, i+k));
    //   E.add(new Edge(i+k, i+1));
    // }
    // for (int i = 1; i <= k; i++) {
    //   E.add(new Edge(i, 0));
    //   E.add(new Edge(k+i, i));
    // }

    // semi normal full

    E.add(new Edge(1, 0));
    E.add(new Edge(k+1, k+k+1));
    E.add(new Edge(k+1, 1));
    for (int i = 2; i < k; i++) {

      E.add(new Edge(i, 0));

      //E.add(new Edge(2*k+i-1, k+i));
      E.add(new Edge(k+i, 2*k+i));

      E.add(new Edge(k+i, i));

      E.add(new Edge(2*k+i-1, k+i));
    }
    E.add(new Edge(k, 0));
    E.add(new Edge(2*k, k));
    E.add(new Edge(k+k-1, 2*k+k-1));

    int runs = 100;

    int a = 0;

    for (int i = 0; i < runs; i++) {
      SimpleSearch D = new SimpleSearch(n);
      D.setOneWay();

      for (Edge e : E) {
        D.insert(e);
      }

      a += D.rec;
      System.out.println(a/(i+1));
    }

  }

}
