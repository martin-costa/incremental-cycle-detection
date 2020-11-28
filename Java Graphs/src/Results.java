import java.util.*;

// run tests from here
class Results {

  // main method
  public static void main(String args[]) {

    int n = 1100;

    int lambda = 25;

    int runs = 3;
    int runs2 = 2;
    int totalRuns = 0;

    double c = 3;

    double totalEdges = 0;

    double totalRecA1 = 0;
    double totalRecA2 = 0;
    double totalRecD = 0;

    for (int l = 0; l < 6; l++) {
      for (int j = 0; j < runs2; j++) {

        Graph G = Graph.generateDAG(n + l*100, lambda);
        ArrayList<Edge> edges = G.getEdges();

        // System.out.print("density: ");
        // System.out.println(G.M()/(0.5*(n-1)*n));
        //
        // System.out.print("average degree: ");
        // System.out.println(2*G.M()/n);

        for (int i = 0; i < runs; i++) {

          Collections.shuffle(edges);

          SimpleSearch A1 = new SimpleSearch(n + l*100);
          A1.setOneWay();

          SimpleSearch A2 = new SimpleSearch(n + l*100);
          A2.setGreedy();

          DivideAndConquer D = new DivideAndConquer(n + l*100);

          for (Edge e : edges) {

            A1.insert(e.l, e.r);
            A2.insert(e.l, e.r);
            D.insert(e.l, e.r);

            // for (Edge f : added) {
            //   if (order.indexOf(f.l) >= order.indexOf(f.r)) {
            //     System.out.println("<<< CYCLE DETECTED >>>");
            //     //System.out.println(edges);
            //     i = runs;
            //   }
            // }
          }

          totalRuns++;

          totalRecA1 += A1.getTotalRecourse();
          totalRecA2 += A2.getTotalRecourse();
          totalRecD += D.totalRecourse;
        }
      }

      System.out.println(n+l*100);
      System.out.println("rec is:");
      System.out.print(totalRecA1/(totalRuns));
      System.out.print("  ");
      System.out.print(totalRecA2/(totalRuns));
      System.out.print("  ");
      System.out.print(totalRecD/(totalRuns));
      System.out.println("");

      totalRuns=0;

      totalRecA1=0;
      totalRecA2=0;
      totalRecD=0;
    }
  }

  private static String toString(double array[])
    {
        return toString(array, Locale.ENGLISH, "%.5f");
    }

    private static String toString(double array[], Locale locale, String format)
    {
        if (array == null)
        {
            return "null";
        }
        StringBuilder sb = new StringBuilder("[");
        for (int i=0; i<array.length; i++)
        {
            if (i > 0)
            {
                sb.append(", ");
            }
            sb.append(String.format(locale, format, array[i]));
        }
        sb.append("]");
        return sb.toString();
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
