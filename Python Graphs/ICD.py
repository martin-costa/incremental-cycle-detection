# python script to run and test incremental cycle detection algorithms
from graphs import *
import random
import math

# creates a graph on n nodes and adds each edge with probability p
def generate_graph_uniformly(n, p):

    G = Graph(n)

    for i in range(n):
        for j in range(n):
            if i is not j:
                r = random.uniform(0,1)
                if r <= p:
                    G.add_edge(i, j, False)
    return G

class BFG09:

    def __init__(self, n):

        # the size of the graph
        self.n = n
        self.lg_n = math.ceil(math.log2(n))

        # graph of added edges (initially n isolated nodes)
        self.G = Graph(n)

        # L[u] stores the label of u
        self.L = []

        # N[u][j] is a counter bounded by 2**(j+2)
        self.N = []

        # an old label of u
        self.M = []

        # chache((u,v)) is an old label of v (implemented with a hashmap)
        self.cache = {}

        # initailise all entries of N and M to 0
        for u in range(self.n):
            self.N.append([0]*self.lg_n)
            self.M.append([0]*self.lg_n)

        # initailise all entries of L to 0
        self.L = [0]*self.n

    # insert the edge (u,v) into the DAG
    def insert(self, u, v):
        self.cache[(u,v)] = 0
        self.G.add_edge(u,v)
        self.FOLLOW(u,v)

    # FOLLOW algorithm implementation to update labels and maintain a topological ordering
    def FOLLOW(self, u, v):

        l_v = self.L[v]

        if self.L[u] >= self.L[v]:
            self.L[v] = self.L[u] + 1

        # check if v has sufficiently many immediate predecessors to increase its label
        else:
            j = math.ceil(math.log2(self.L[v] - self.L[u]))
            self.N[v][j] = self.N[v][j] + 1
            if self.N[v][j] == 2**(j + 2):
                self.L[v] = math.max(self.L[v], self.M[v][j] + 2**j)
                self.N[v][j] = 0
                self.M[v][j] = self.L[v]

        # if L[v] has increased
        if self.L[v] > l_v:
            for w in self.G.adj_list[v]:
                if self.cache[(v,w)] <= self.L[v]:
                    self.FOLLOW(v,w)

        # done following the edge (u,v)
        self.cache[(u,v)] = self.L[v]
        self.G.add_edge(u,v)

    # checks that the labels correctly induce a topological ordering
    def check_labels(self):

        for u in range(self.n):
            for v in self.G.adj_list[u]:
                # if any edge goes against labels return false
                if self.L[u] >= self.L[v]: return False

        # return true if labels induce a topological ordering
        return True
