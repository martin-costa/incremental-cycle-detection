from graphs import *
import math

# python program to test incremental cycle detection and topological
# ordering algorithms

# O(min{m^1/2, n^2/3} * m) algorithm from BFGT 2011, for sparse graphs
class BFGT11:

    def __init__(self, n, m):

        # stores the number of nodes and edges to be added
        self.n, self.m = n, m

        slef.delta = min(m**(1/2), n**(2/3))

        # stores the level and index of the nodes
        self.k, self.i = [1]*self.n, range(-self.n, 0, 1)

        # sets of outgoing and incoming edges for each node
        self.outward, self.inward = [], []
        for i in range(self.n):
            self.outward.append(set())
            self.inward.append(set())

        self.index = -n

        # used during the insertion of an edge
        self.B, self.F = [], []
        self.arcs = 0
        self.marked_nodes = set()

        # stores the edge while its being inserted
        self.v, self.w = 0, 0

    def insert(self, v, w):

        self.v, self.w = v, w

        # STEP 1 (test order)
        if self.k[v] < self.k[w] or (self.k[v] == self.k[w] and self.i[v] < self.i[w]):
            return True

        # STEP 2 (search backward)
        self.B, self.F = [], []
        self.arcs = 0

        s = self.Bvisit(v)

        # return false if a cycle is detected
        if s == "stop": return False

        skip_step3 = False

        if s == "continue":
            if self.k[w] == self.k[v]:
                skip_step3 = True
            else:
                self.k[w] = self.k[v]
                self.inward[w] = set()

        # STEP 3 (forward search)
        if s == "abort" or not skip_step3:
            r = self.Fvisit(w)

            # return false if a cycle is detected
            if r == "stop": return False

        # STEP 4 (re-index)
        L = B + F

        while(len(L) > 0):
            self.index = self.index - 1
            self.i[L.pop()] = self.index

        # STEP 5 (insert edge)
        self.outward[v].add((v, w))

        if self.k[v] == self.k[w]:
            self.inward[w].add((v,w))

    # mutually recursive functions for backward search
    def Bvisit(self, y):

        self.marked_nodes.add(y)

        for (x,y) in self.inward[y]:
            s = self.Btraverse(x,y)

            # a cycle has been found, stop the algorithm
            if s == "stop": return s

            # search aborted, go to step 3
            elif s == "abort": return s

        self.B.append(y)
        return "continue"

    def Btraverse(self, x, y):

        # if a cycle has been found, stop the algorithm
        if x == self.w: return "stop"

        self.arcs = self.arcs + 1

        # if delta edges have been traversed and w not yet found, abort search
        if self.arcs >= self.delta:
            self.k[w] = self.k[v] + 1
            slef.inward[w] = []
            self.B = []
            self.marked_nodes = set()
            return "abort"

        if x not in self.marked_nodes:
            s = self.Bvisit(x)

            # a cycle has been found, stop the algorithm
            if s == "stop": return s

            # search aborted, go to step 3
            elif s == "abort": return s

        return "continue"

    # mutually recursive functions for forward search
    def Fvisit(x):

        for (x,y) in self.outward[x]:
            s = self.Ftraverse(x,y)

            # a cycle has been found, stop the algorithm
            if s == "stop": return s

        self.F = [x] + self.F # !!!!!COMPLEXITY VERY WRONG

    def Ftraverse(x,y):

        # if a cycle has been found, stop the algorithm
        if y == self.v or y in self.B: return "stop"

        if self.k[y] < self.k[w]:
            self.k[y] = self.k[w]
            self.inward[y] = set()

            s = self.Fvisit(y)

            # a cycle has been found, stop the algorithm
            if s == "stop": return s

        # now k(y) >= k(w)
        if self.k[y] == self.k[w]:
            self.inward[y].add((x,y))

        return "continue"


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
