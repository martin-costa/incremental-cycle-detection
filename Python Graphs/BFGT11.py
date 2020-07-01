from graphs import *
import math

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
