from ICD_testing import *
from display import *

if __name__ == "__main__":

    cont = True
    i = -1

    G = Graph(0)

    while(cont):
        i = i + 1
        (G,S) = generate_graph_uniformly(math.floor(np.random.uniform(3,25)), np.random.uniform(0,1))
        cont = BFG09_TEST(S, G, False)
        if cont and i % 1000 == 0:
            print(i)

    print("fail at " + str(i))

    print(G)


    (G,_) = generate_graph_uniformly(17, 0.7)

    draw_graph(G)

    N = 100000
    n = 7
    p = 0.01

    #BFG09_GEN_TEST(N, n, p)

    # print(G)
    # print(S)
    #
    # print("acyclic: " + str(G.is_acyclic()) + "\n")
    #
    # ICD = BFG09(n)
    #
    # while len(S) > 0:
    #     (u,v) = S.pop()
    #     print(str(ICD.insert(u,v)) + "  " + str(ICD.check_labels()))

    # G = Graph(6)
    #
    # G.add_edge(0,3)
    # G.add_edge(0,2)
    # G.add_edge(3,4)
    # G.add_edge(2,1)
    # G.add_edge(0,1)
    # G.add_edge(4,3)
    # G.add_edge(4,2)
    #
    # print(G)
    # print("acyclic: " + str(G.is_acyclic()) + "\n")
    #
    # ICD = BFG09(6)
    #
    # print(ICD.check_labels())
    #
    # ICD.insert(0,3)
    # print(ICD.check_labels())
    #
    # ICD.insert(0,2)
    # print(ICD.check_labels())
    #
    # ICD.insert(3,4)
    # print(ICD.check_labels())
    #
    # ICD.insert(2,1)
    # print(ICD.check_labels())
    #
    # ICD.insert(0,1)
    # print(ICD.check_labels())
    #
    # ICD.insert(4,3)
    # print(ICD.check_labels())
    #
    # ICD.insert(4,2)
    # print(ICD.check_labels())

    # N = 1
    #
    # n = 10
    # p = 0.1
    #
    # for i in range(N):
    #
    #     (G,S) = generate_graph_uniformly(n,p)
    #
    #     print("acyclic: " + str(G.is_acyclic()) + "\n")
    #
    #     ICD = BFG09(n)
    #
    #     while len(S) > 0:
    #         (u,v) = S.pop()
    #         print(str(ICD.insert(u,v)) + "  " + str(ICD.check_labels()))
