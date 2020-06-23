from ICD import *
from display import *

def BFG09_GEN_TEST(N, n, p, print_results=True):

    passes = 0
    cyclic_count = 0

    for i in range(N):

        (G,S) = generate_graph_uniformly(n,p)
        acyclic = G.is_acyclic()
        ICD = BFG09(n)

        for i in range(len(S)):
            (u,v) = S[i]

            # check that the new labels induce a correct topological ordering if and only
            # if the graph remains acylic after the insertion of (u,v)
            if ICD.insert(u,v) != ICD.check_labels():
                print("error: correctness of ordering disagrees with cycle detection")

        # count the number of correctly classified graphs and cyclic graphs
        if ICD.acyclic == acyclic:
            passes = passes + 1
        if not acyclic:
            cyclic_count = cyclic_count + 1

    if print_results:
        s = str(N) + " graphs on " + str(n) + " nodes generated with p = " + str(p) + "\n"
        s = s + str(passes) + "/" + str(N) + " tests passed\n"
        s = s + str(cyclic_count) + "/" + str(N) + " generated graphs were cyclic\n"
        s = s + "pass rate: " + str((passes/N)* 100) + "%\n"
        s = s + "cyclic: " + str((cyclic_count/N)* 100) + "%"
        print(s)

    return (passes, cyclic_count, (passes/N)* 100, (cyclic_count/N)* 100)

# check the output of BFG09 on G
def BFG09_TEST(S, G=None, print_results=True):

    if G == None:
        G = induce_graph(S)
    ICD = BFG09(G.n)

    acyclic = G.is_acyclic()

    for i in range(len(S)):
        (u,v) = S[i]

        # check that the new labels induce a correct topological ordering if and only
        # if the graph remains acylic after the insertion of (u,v)
        if ICD.insert(u,v) != ICD.check_labels():
            print("error: correctness of ordering disagrees with cycle detection")
            print(G)
            draw_graph(G)

    if print_results:
        print(ICD.acyclic == acyclic)
    return ICD.acyclic == acyclic


if __name__ == "__main__":

    cont = True
    i = -1

    G = Graph(0)

    while(cont):
        i = i + 1
        (G,S) = generate_graph_uniformly(math.floor(random.uniform(3,25)), random.uniform(0,1))
        cont = BFG09_TEST(S,G,False)
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
