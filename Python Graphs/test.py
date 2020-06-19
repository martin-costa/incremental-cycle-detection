from graphs import *
import maths

n = 14
L = []
N = []
M = []
cache = []

def FOLLOW(u, v):

    lv = L[v]

    if L[u] >= L[v]:
        L[v] = L[u] + 1
    else:
        j = math.ceil(math.log2(L[v] - L[u]))
        N[v][j] = N[v][j] + 1
        if N[v][j] == 2**(j + 2):
            L[v] = math.max(L[v], M[v][j] + 2**j)
            N[v][j] = 0
            M[v][j] = L[v]

    if L[v] > lv:


if __name__ == "__main__":

    G = Graph(5)

    G.add_edge(0,3)
    G.add_edge(0,2)
    G.add_edge(3,4)
    G.add_edge(2,1)
    G.add_edge(0,1)
    G.add_edge(4,3)
    G.add_edge(4,2)
    G.add_edge(4,6)

    print(G)
