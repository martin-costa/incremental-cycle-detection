from graphs import *

class SplitSet:

    def __init__(self, F, T, G, threshold):

        # the graph that L,R and F are subsets of
        self.G = G

        # an ordering of F that agrees with G (is None iff not intact)
        self.T = T

        # L and R will be SplitSets is the set splits up
        self.L = None
        self.F = F
        self.R = None

        # how much work needs to be done in a step before we split up the data structure
        self.threshold = threshold

        # True iff the data structure has not been split up yet
        self.intact = True

    # insert an edge (u,v) into G
    def insert(u,v):

        # if the set has not been split yet
        if self.intact:
            False

    # check if (u,v) agrees with ordering defined by the data structure
    def top(u,v):

        if self.intact:
            return T(u) < T(v)
        else:
            if v in self.L:
                if u in



    # partition F into (L,F,R) to get a semi-topological partition
    def partition(self, L, R):

        self.L = SplitSet(L, self.G, self.threshold)
        self.R = SplitSet(R, self.G, self.threshold)

        self.F = self.F.difference(L)
        self.F = self.F.difference(R)



# test algorithm
if __name__ == "__main__":
