import graphs.*;

// run tests from here
class Test {

  // main method
  public static void main(String args[]) {

    Graph G = new Graph(5);

    G.addEdge(0,3);
    G.addEdge(0,2);
    G.addEdge(3,4);
    G.addEdge(2,1);
    G.addEdge(0,1);
    G.addEdge(4,3);

    System.out.println(G.toString());
  }

}
