# define a graph class to represent directed graphs
class Graph:

    def __init__(self, n):

        self.n = n
        self.m = 0

        self.adj_list = []
        for i in range(n):
            self.adj_list.append([])

    # add an edge to the graph
    def add_edge(self, u, v, check=True):
        if (not check) or u < 0 or u >= self.n or v < 0 or v >= self.n or self.adj_list[u].count(v) > 0:
            return False
        self.m = self.m + 1
        self.adj_list[u].append(v)
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
