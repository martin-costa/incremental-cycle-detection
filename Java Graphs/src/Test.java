import graphs.*;

// run tests from here
class Test {

  // main method
  public static void main(String args[]) {

    Graph G = new Graph(6);

    G.addEdge(0,3);
    G.addEdge(0,2);
    G.addEdge(3,4);
    G.addEdge(2,1);
    G.addEdge(0,1);
    G.addEdge(4,3);
    G.addEdge(4,2);
    G.addEdge(4,6);

    System.out.println(G);

    for (int i = 0; i < G.N(); i++) {
      int[] post = G.DFS(i, true);
      int[] pre = G.DFS(i, false);
      for (int j = 0; j < G.N(); j++) {
        System.out.print(pre[j]);
      }
      System.out.println("");
      for (int j = 0; j < G.N(); j++) {
        System.out.print(post[j]);
      }
      System.out.println("\n");
    }

  }

}
