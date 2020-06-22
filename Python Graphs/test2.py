from ICD import *

if __name__ == "__main__":

    G = Graph(6)

    G.add_edge(0,3)
    G.add_edge(0,2)
    G.add_edge(3,4)
    G.add_edge(2,1)
    G.add_edge(0,1)
    G.add_edge(4,3)
    G.add_edge(4,2)

    print(G)
    print("acyclic: " + str(G.is_acyclic()) + "\n")

    ICD = BFG09(6)

    print(ICD.check_labels())

    ICD.insert(0,3)
    print(ICD.check_labels())

    ICD.insert(0,2)
    print(ICD.check_labels())

    ICD.insert(3,4)
    print(ICD.check_labels())

    ICD.insert(2,1)
    print(ICD.check_labels())

    ICD.insert(0,1)
    print(ICD.check_labels())

    ICD.insert(4,3)
    print(ICD.check_labels())

    ICD.insert(4,2)
    print(ICD.check_labels())
