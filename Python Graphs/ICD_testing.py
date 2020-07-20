from BFG09 import *
from BFGT11 import *
from graphs import *

# function to test icremental cycle detection algorithm A on a randomly
# generated digraph of density p on n nodes
def ICD_algorithm_test(A, n, p):
    return False

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
