import java.util.*;

// run tests from here
class Test {

  // main method
  public static void main(String args[]) {

    // Graph G = new Graph(10);
    //
    // DivideAndConquer D = new DivideAndConquer(10);
    //
    // ArrayList<Edge> edges = new ArrayList<Edge>();
    //
    // edges.add(new Edge(3,9));
    // edges.add(new Edge(5,4));
    // edges.add(new Edge(2,4));
    // edges.add(new Edge(9,4));
    // edges.add(new Edge(7,4));
    // edges.add(new Edge(1,7));
    // edges.add(new Edge(1,5));
    // edges.add(new Edge(1,6));
    // edges.add(new Edge(7,5));
    // edges.add(new Edge(8,0));
    // edges.add(new Edge(6,5));
    // edges.add(new Edge(2,5));
    // edges.add(new Edge(2,9));
    // edges.add(new Edge(8,7));
    // edges.add(new Edge(6,0));
    // edges.add(new Edge(5,9));
    // edges.add(new Edge(7,6));
    // edges.add(new Edge(3,0));
    // edges.add(new Edge(6,4));
    // edges.add(new Edge(7,9));
    // edges.add(new Edge(1,8));
    // edges.add(new Edge(1,0));
    // edges.add(new Edge(8,6));
    // edges.add(new Edge(3,5));
    // edges.add(new Edge(6,9));
    // edges.add(new Edge(0,2));
    //
    // ArrayList<Integer> order = D.getOrdering();
    //
    // HashSet<Edge> added = new HashSet<Edge>();
    //
    // System.out.println(D);
    //
    // for (Edge e : edges) {
    //   System.out.println(e);
    //   D.insert(e);
    //   added.add(e);
    //   System.out.println(D);
    //
    //   order = D.getOrdering();
    //
    //   for (Edge f : added) {
    //     if (order.indexOf(f.l) >= order.indexOf(f.r)) {
    //       System.out.println("AHHHHHHHH");
    //     }
    //   }
    // }

    int n = 30;

    for (int i = 0; i < 10000000; i++) {
      Graph G = Graph.generateDAG(n, 0.5);
      //System.out.println(G.isAcyclic());

      ArrayList<Edge> edges = G.getEdges();

      DivideAndConquer D = new DivideAndConquer(n);

      if (i % 1000 == 0) System.out.println(i);

      HashSet<Edge> added = new HashSet<Edge>();

      ArrayList<Integer> order = D.getOrdering();

      for (Edge e : edges) {
        //System.out.println(e);
        D.insert(e.l, e.r);
        added.add(e);
        //System.out.println(D);

        order = D.getOrdering();

        for (Edge f : added) {
          if (order.indexOf(f.l) >= order.indexOf(f.r)) {
            System.out.println("AHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH");
            System.out.println(edges);
            i = 10000000;
          }
        }
      }

      order = D.getOrdering();

      for (Edge e : edges) {
        if (order.indexOf(e.l) >= order.indexOf(e.r)) {
          System.out.println("AHHHHHH");
        }
      }
    }

    // DivideAndConquer D = new DivideAndConquer(n);
    //
    // System.out.println(D);
    //
    // D.insert(0,9);
    // System.out.println(D);
    //
    // D.insert(2,3);
    // System.out.println(D);
    //
    // D.insert(9,2);
    // System.out.println(D);

    // Graph G = new Graph(6);
    //
    // G.addEdge(0,3);
    // G.addEdge(0,2);
    // G.addEdge(3,4);
    // G.addEdge(2,1);
    // G.addEdge(0,1);
    // G.addEdge(4,3);
    // G.addEdge(4,2);
    // G.addEdge(4,6);
    //
    // System.out.println(G);
    //
    // for (int i = 0; i < G.N(); i++) {
    //   int[] post = G.DFS(i, true);
    //   int[] pre = G.DFS(i, false);
    //   for (int j = 0; j < G.N(); j++) {
    //     System.out.print(pre[j]);
    //   }
    //   System.out.println("");
    //   for (int j = 0; j < G.N(); j++) {
    //     System.out.print(post[j]);
    //   }
    //   System.out.println("\n");
    // }

  }

}
