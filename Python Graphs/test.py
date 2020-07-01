from ICD_testing import *

if __name__ == "__main__":

    # k = 0
    # M = 50
    # N = 1
    #
    # for i in range(N):
    #     G = generate_graph_uniformly(M, 0.5)
    #     k = k + G.m
    #     print(G.m/(M*(M-1)))
    #
    # print(k/(N*M*(M-1)))
    #
    # print(G)
    #
    # icd = BFG09(12)
    #
    # print(icd.check_labels())

    A = [1,2,3]


    A.insert(0,4)

    print(A)

    n = 12
    p = 0.1

    (H,S) = generate_graph_uniformly(n, p)

    print(S)

    print("expected m = " + str(n*(n-1)*p))
    print(H)

    G = Graph(6)

    G.add_edge(0,3)
    G.add_edge(0,2)
    # G.add_edge(3,4)
    G.add_edge(2,1)
    G.add_edge(0,1)
    G.add_edge(4,3)
    G.add_edge(4,2)
    G.add_edge(4,6)

    # print(G)
    #
    (pre, post) = H.DFS()
    #
    print(pre)
    print(post)
    #
    print(H.is_acyclic())

    # 0 3 2 1 4 5
    # 3 1 2 0 4 5

    # 1 4 3 2 5 6
    # 4 2 3 1 5 6
