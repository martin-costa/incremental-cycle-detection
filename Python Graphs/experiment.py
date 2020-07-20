from graphs import *
import math

# a little experiment to see how long it takes to form a rank 0 STP

class FindSTP:

    def __init__(self, n):

        # the size of the graph
        self.n = n

        # maintains the graph as we add edges
        self.forward = Graph(n)

        # maintains the reverse of the graph for backwards searches
        self.backward = Graph(n)

        # the topological ordering of the graph
        self.T = np.random.permutation(n)

        # keeps track of total recourse
        self.recourse = 0

    # insert the edge (u,v) into the DAG
    def insert(self, u, v):

        self.forward.add_edge(u, v, False)
        self.backward.add_edge(v, u, False)

        (Fpre, Fpost) = self.forward.DFS(v)
        (Bpre, Bpost) = self.backward.DFS(u)

        if not self.forward.is_acyclic():
            return "fail"

        Ffound = 0
        for w in range(0, self.n):
            if Fpre[w] != 0: Ffound = Ffound + 1

        Bfound = 0
        for w in range(0, self.n):
            if Bpre[w] != 0: Bfound = Bfound + 1

        if Ffound >= self.n/math.log(n) and Bfound >= self.n/math.log(n):
            return "found"

        return "continue"

# call experiment from here
if __name__ == "__main__":

    n = 50
    runs = 1000

    failed = 0
    passed = 0

    a = 0

    for j in range(runs):

        (G,S) = generate_graph_uniformly(n, 0.3, True)
        A = FindSTP(n)

        i = 0
        while len(S) > 0:
            i = i + 1
            (u,v) = S.pop()
            s = A.insert(u,v)

            if s == "found":
                passed = passed + 1

                a = a + i
                print(str(i) + "  " + str(a/passed))
                S = []
            if s == "fail":
                failed = failed + 1
                print("fail")
                S = []

    print("passed " + str(passed))
    print("failed " + str(failed))
