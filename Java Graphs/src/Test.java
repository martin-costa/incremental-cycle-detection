import java.util.*;

// run tests from here
class Test {

  // main method
  public static void main(String args[]) {

    int n = 40000;

    double c = 2;

    int runs = 20;

    double totalEdges = 0;

    double totalRec = 0;

    double totAv = 0;

    double p = 2*c/(n-1);

    double avDepth = 0;

    int k = (int) Math.sqrt(5000);

    ArrayList<Edge> E = new ArrayList<Edge>(k);

    for (int i = 0; i < k; i++) {
      for (int j = 0; j < k - 1; j++) {
        E.add(new Edge(i*k + j, i*k + j + 1));
      }
    }

    for (int i = 0; i < k; i++) {
      for (int j = i + 1; j < k; j++) {
        E.add(new Edge(k*j + k - 1, k*i));
      }
    }

    System.out.println(DivideAndConquerAverage(E, k*k, runs, true));

    System.exit(0);

    // int j = 0;
    // while(j < 100) {
    //   j++;
    //   Graph G = Graph.generateDAG(n, p);
    //   totalEdges += G.M();
    //   System.out.println(totalEdges/(j));
    // }
    //
    // while(j < 1000) {
    //   Graph G = Graph.generateGraph(n, p);
    // }

    for (int i = 0; i < runs; i++) {
      Graph G = Graph.generateGraph(n, p);
      //System.out.println(G.isAcyclic());

      ArrayList<Edge> edges = G.getEdges();

      //System.out.print("density*n: ");
      //System.out.println(G.M()/ (0.5*(n-1)));

      GreedyAlgorithm D = new GreedyAlgorithm(n);
      //DivideAndConquer D = new DivideAndConquer(n);

      //System.out.println(totalEdges/(i + 1));

      HashSet<Edge> added = new HashSet<Edge>();

      ArrayList<Integer> order = D.getOrdering();

      //System.out.println(D);

      for (Edge e : edges) {
        //System.out.println(e);
        //System.out.println(added.size());
        // System.out.print("   ");
        // System.out.print(D.totalRecourse);
        // System.out.print("\n");
        D.insert(e.l, e.r);
        added.add(e);
        //System.out.println(D);

        //order = D.getOrdering();
        //  System.out.println(order);
        // for (Edge f : added) {
        //   if (order.indexOf(f.l) >= order.indexOf(f.r)) {
        //     System.out.println("AHHHHHHHHHHHHHHHHHHHHHHH");
        //     //System.out.println(edges);
        //     i = 10000000;
        //   }
        // }
      }

      totalEdges += edges.size();

      //avDepth += D.depth();

      totalRec += D.totalRecourse;

      double avRec = totalRec/(i+1);
      double avEdg = totalEdges/(i+1);

      totAv += (double)D.totalRecourse/(double)edges.size();

      System.out.print(totalRec/totalEdges);
      System.out.print("    ");
      System.out.print(avEdg);
      System.out.println();
    }

  }

  public static int greedyAverage(List<Edge> E, int n, int runs, boolean output) {

    GreedyAlgorithm G;
    int recourse = 0;

    for (int i = 0; i < runs; i++) {

      java.util.Collections.shuffle(E);

       G = new GreedyAlgorithm(n);
       for (Edge e : E) {
         G.insert(e);
       }
       if (output) System.out.println(G.totalRecourse);
       recourse += G.totalRecourse;
    }
    return recourse/runs;
  }

  public static int DivideAndConquerAverage(List<Edge> E, int n, int runs, boolean output) {

    DivideAndConquer G;
    int recourse = 0;

    for (int i = 0; i < runs; i++) {

      java.util.Collections.shuffle(E);

       G = new DivideAndConquer(n);
       for (Edge e : E) {
         G.insert(e);
       }
       if (output) System.out.println(G.totalRecourse);
       recourse += G.totalRecourse;
    }
    return recourse/runs;
  }

  // this example gives a case that yields over Omega(n*sqrt(n)) recourse with O(n) insertions
  public static boolean counterExample(int n, boolean output) {

    int k = (int) Math.sqrt(n);
    n = k*k;

    DivideAndConquer D = new DivideAndConquer(n);
    // GreedyAlgorithm D = new GreedyAlgorithm(n);

    // add edges to graph to create nested STPs
    for (int i = 0; i < k; i++) {
      for (int j = 0; j < k - 1; j++) {
        D.insert(i*k + j, i*k + j + 1);
      }
    }

    // move free nodes out of nested STPs slowly, blowing up recourse to super-linear levels
    for (int i = 0; i < k; i++) {
      for (int j = i + 1; j < k; j++) {
        D.insert(k*j + k - 1, k*i);
      }
    }

    if (output) {
      System.out.println("n: "+Integer.toString(n)+"\n"+"recourse: "+Integer.toString(D.totalRecourse)+"\n"+"n*sqrt(n)/2: "+Integer.toString(k*k*k/2));
    }
    return D.totalRecourse >= k*k*k/2;
  }
}
