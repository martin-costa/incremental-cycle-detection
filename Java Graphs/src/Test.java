import java.util.*;

// run tests from here
class Test {

  // main method
  public static void main(String args[]) {

    int n = 800;

    int runs = 10;

    int ii = 32;

    // while(ii < 1000000) {
    //   System.out.println(n*ii);
    //   testSimpleSearch(n*ii, runs);
    //   ii *= 2;
    //   System.out.println("\n");
    // }

    double c = 2;

    double totalEdges = 0;

    double totalRec = 0;

    double totAv = 0;

    double p = 2*c/(n-1);

    double avDepth = 0;

    double avl2 = 0;

    // int k = (int) Math.sqrt(5000);
    //
    // ArrayList<Edge> E = new ArrayList<Edge>(k);
    //
    // for (int i = 0; i < k; i++) {
    //   for (int j = 0; j < k - 1; j++) {
    //     E.add(new Edge(i*k + j, i*k + j + 1));
    //   }
    // }
    //
    // for (int i = 0; i < k; i++) {
    //   for (int j = i + 1; j < k; j++) {
    //     E.add(new Edge(k*j + k - 1, k*i));
    //   }
    // }

    // System.out.println(DivideAndConquerAverage(E, k*k, runs, true));
    //
    // System.exit(0);

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

      SimpleSearch D = new SimpleSearch(n);
      D.setSmall();
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

        order = D.getOrdering();
        //System.out.println(order);
        for (Edge f : added) {
          if (order.indexOf(f.l) >= order.indexOf(f.r)) {
            System.out.println("AHHHHHHHHHHHHHHHHHHHHHHH");
            //System.out.println(edges);
            i = 10000000;
          }
        }
      }

      totalEdges += edges.size();

      //avDepth += D.depth();

      totalRec += D.getTotalRecourse();

      double avRec = totalRec/(i+1);
      double avEdg = totalEdges/(i+1);

      totAv += (double)D.getTotalRecourse()/(double)n;

      // System.out.print(totalRec/totalEdges);
      // System.out.print("    ");
      // System.out.print(avEdg);
      // System.out.println();

      //avl2 += D.recoursel2();

      // System.out.print(totalRec/(i+1));
      // System.out.print("     ");
      // System.out.print(avl2/(i+1));
      // System.out.println();

      System.out.println(totAv/(i+1));
    }
  }

  // test different simple algorithms on random DAGs
  public static void testSimpleSearch(int n, int runs) {

    double greedyRec = 0;
    double smallRec = 0;
    double fullRec = 0;
    double oneWayRec = 0;

    int repeats = 1;

    double c = 2;
    double p = 2*c/(n-1);

    // set on runs many graphs
    for (int i = 0; i < runs; i++) {

      Graph G = Graph.generateGraph(n, p);
      ArrayList<Edge> E = G.getEdges();

      double[] recourse = new double[4];

      // test on each graph repeats many times
      for (int j = 0; j < repeats; j++) {
        for (int k = 0; k < 4; k++) {
          recourse[k] += runSimpleSearch(E, n, k);
        }
        Collections.shuffle(E);
      }

      greedyRec += recourse[0]/repeats;
      smallRec += recourse[1]/repeats;
      fullRec += recourse[2]/repeats;
      oneWayRec += recourse[3]/repeats;
    }

    System.out.print("greedy: ");
    System.out.print(greedyRec/(n*runs));

    System.out.print("\nsmall: ");
    System.out.print(smallRec/(n*runs));

    System.out.print("\nfull: ");
    System.out.print(fullRec/(n*runs));

    System.out.print("\noneWay: ");
    System.out.print(oneWayRec/(n*runs));
  }

  public static int runSimpleSearch(List<Edge> E, int n, int type) {

    SimpleSearch simpleSearch = new SimpleSearch(n);

    if (type == 0) simpleSearch.setGreedy();
    if (type == 1) simpleSearch.setSmall();
    if (type == 2) simpleSearch.setFull();
    if (type == 3) simpleSearch.setOneWay();

    // insert all edges
    for (Edge e : E) {
      simpleSearch.insert(e.l, e.r);
    }

    return simpleSearch.getTotalRecourse();
  }

  // average recourse of greedy over edge set E over 'runs' runs
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

  // average recourse of divide andconquer over edge set E over 'runs' runs
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
