from graphs import *
import math

# O(n^2 log n) algorithm from BFG 2009, almost optimal for dense graphs
class BFG09:

    def __init__(self, n):

        # the size of the graph
        self.n = n
        self.lg_n = math.ceil(math.log2(n))

        # graph of added edges (initially n isolated nodes)
        self.G = Graph(n)

        # L[u] stores the label of u
        self.L = [0]*self.n

        # N[u][j] is a counter bounded by 2**(j+2)
        self.N = []

        # an old label of u
        self.M = []

        # chache((u,v)) is an old label of v (implemented with a hashmap)
        self.cache = {}

        # initailise all entries of N and M to 0
        for u in range(self.n):
            self.N.append([0]*(self.lg_n+1))
            self.M.append([0]*(self.lg_n+1))

        # stores nodes visited while following edgea after an insertion
        self.visited = set()
        self.acyclic = True

    # insert the edge (u,v) into the DAG
    def insert(self, u, v):

        # initailize the cache of v along this edge to 0
        self.cache[(u,v)] = 0

        # add the inserted edge to the graph
        self.G.add_edge(u,v)

        self.visited = {u}

        # follow the edge and update labels where necessary
        return self.FOLLOW(u,v)

    # FOLLOW algorithm implementation to update labels and maintain a topological ordering
    def FOLLOW(self, u, v):

        # overhead to detect cycles without increasing (asymptotic) runtime
        # checks for cylces and stops following edges when a cycle is detected
        if not self.acyclic or v in self.visited:
            self.acyclic = False
            return False
        self.visited.add(v)

        # store the value of the label of v
        l_v = self.L[v]

        if self.L[u] >= self.L[v]:
            self.L[v] = self.L[u] + 1

        # check if v has sufficiently many immediate predecessors to increase its label
        else:
            j = math.ceil(math.log2(self.L[v] - self.L[u]))
            self.N[v][j] = self.N[v][j] + 1
            if self.N[v][j] == 2**(j + 2):
                self.L[v] = max(self.L[v], self.M[v][j] + 2**j)
                self.N[v][j] = 0
                self.M[v][j] = self.L[v]

        # if L[v] has increased
        if self.L[v] > l_v:
            for w in self.G.adj_list[v]:
                if self.cache[(v,w)] <= self.L[v]:

                    # return false if a cycle is detected
                    if not self.FOLLOW(v,w):
                        return False

        # done following the edge (u,v)
        self.cache[(u,v)] = self.L[v]
        self.G.add_edge(u,v)

        self.visited.remove(v)

        # returns true when no cycles have been detected
        return True

    # checks that the labels correctly induce a topological ordering
    def check_labels(self):

        for u in range(self.n):
            for v in self.G.adj_list[u]:
                # if any edge goes against labels return false
                if self.L[u] >= self.L[v]: return False

        # return true if labels induce a topological ordering
        return True
