import random

# define a graph class to represent directed graphs
class Graph:

    def __init__(self, n):

        # stores the amount of nodes in the graph
        self.n = n

        # stores the amount of edges in the graph
        self.m = 0

        # creates an adjacency list
        self.adj_list = []
        for i in range(self.n):
            self.adj_list.append([])

        # global counters to be used in DFS
        self.y = 0
        self.z = 0

    # add an edge to the graph
    def add_edge(self, u, v, check=True):
        if check:
            if u < 0 or u >= self.n or v < 0 or v >= self.n or self.adj_list[u].count(v) > 0:
                return False

        self.m = self.m + 1
        self.adj_list[u].append(v)
        return True

    # run DFS on G starting at u, keeping the pre and post numbers
    def DFS(self, u=None, pre=None, post=None):

        if pre == None:
            pre, self.y = [0]*self.n, 0

        if post == None:
            post, self.z = [0]*self.n, 0

        if u == None:
            for u in range(self.n):
                if pre[u] == 0:
                    self.DFS(u, pre, post)

        # if u is unvisted
        if pre[u] == 0:
            self.y = self.y + 1
            pre[u] = self.y
            for v in self.adj_list[u]:
                if pre[v] == 0:
                    self.DFS(v, pre, post)
            self.z = self.z + 1
            post[u] = self.z

        # return the pre and post numbers
        return (pre, post)

    # find a topological ordering by finding post numbers and inverting them
    def topological_sort(self):

        (pre, post), top = self.DFS(), [0]*self.n

        for u in range(self.n):
            top[u] = self.n - post[u] - 1

        return top

    # check if we can find a correct topological sort of the graph
    def is_acyclic(self):

        # find a topological sort of the graph
        top = self.topological_sort()

        for u in range(self.n):
            for v in self.adj_list[u]:
                # if any edge goes against sort return false
                if top[u] >= top[v]: return False

        # return true if top is a topological ordering
        return True

    # create string representation of the class
    def __str__(self):

        s = "n = " + str(self.n) + "\nm = " + str(self.m) + "\n\n"

        for i in range(self.n):
            s = s + str(i) + ": "
            l = len(self.adj_list[i])
            for j in range(l):
                s = s + str(self.adj_list[i][j]) + " "
            s = s + "\n"

        return s

# creates a graph on n nodes and adds each edge with probability p, but no self loops
def generate_graph_uniformly(n, p):

    # create a graph G with all the edges and a stack S of all the edges
    G = Graph(n)
    S = []

    for i in range(n):
        for j in range(n):
            if i is not j:
                r = random.uniform(0,1)
                if r <= p:
                    G.add_edge(i, j, False)
                    S.append((i,j))
    return (G,S)

# creates a graph of size n from a list of edges
def induce_graph(S, n):

    G = Graph(n)

    for i in range(len(S)):
        (u,v) = S[i]
        G.add_edge(u,v)

    return G
