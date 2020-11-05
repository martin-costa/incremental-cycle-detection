import java.util.*;
import java.util.Random;

public class GraphGame {

  // number of nodes in graph
  private int n;

  // number of edges in the graph
  private int m;

  // stores the full graph
  private Graph G;

  public GraphGame(int n, int m) {
    this.n = n;
    this.m = m;
  }

  public int play(ArrayList<Edge> E, int r) {

    Graph forward = new Graph(n);
    Graph backward = new Graph(n);

    int k = 0;

    HashMap<Edge, Integer> c = new HashMap<Edge, Integer>();

    Random rand = new Random();

    ArrayList<Integer> order = new ArrayList<Integer>(n);
    for (int i = 0; i < n; i++) {
      order.add(i);
    }
    Collections.shuffle(order);

    for (Edge e : E) {
      if (order.get(e.l) <= order.get(e.r))
        c.put(e, 2);
      else
        c.put(e, 1);
    }

    // main loop
    for (int i = 0; i < m; i++) {
      Edge e = E.get(i);

      // if not green (ie. if blue)
      if (c.get(e) == 2) {
        c.put(e, 0);
      }
      else {

        // step 1
        HashSet<Integer> reach = forward.reach(e.r);
        HashSet<Integer> reach2 = backward.reach(reach);

        for (int j = 0; j < m; j++) {
          if (rand.nextDouble() < 0.5 && reach2.contains(E.get(j).r)) {
            c.put(E.get(j), 2);
          }
        }

        // step 2
        // for (int j = 0; j < m; j++) {
        //   if (reach.contains(E.get(j).l) && !reach.contains(E.get(j).r)) c.put(E.get(j), 1);
        // }

        // step 3
        if (reach.contains(r)) k++;
        //k++;

        // step 4
        c.put(e, 0);

        forward.addEdge(e.l, e.r);
        backward.addEdge(e.r, e.l);
      }
    }

    return k;
  }

  public static void main(String[] args) {

    int n = 500;

    double a = 0;

    ArrayList<Edge> E = new ArrayList<Edge>();

    for (int i = 0; i < n; i++) {
      for (int j = i+1; j < n; j++) {
        E.add(new Edge(i, j));
      }
    }

    GraphGame game = new GraphGame(n, E.size());

    for (int i = 0; i < 1000; i++) {
      Collections.shuffle(E);

      a += game.play(E, n-1);
      System.out.println(a/(i+1));
    }

  }
}
