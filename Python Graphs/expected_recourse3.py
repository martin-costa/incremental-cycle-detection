import matplotlib.pyplot as plt
import matplotlib
import math
import random
import numpy as np

def get_recourse(sub_sizes):

    size = len(sub_sizes)

    crit_nodes = []

    present = list(range(1, size+1))

    for i in range(size):
        for j in range(sub_sizes[i]):
            crit_nodes.append(i+1)

    random.shuffle(crit_nodes)

    point = random.randint(1, len(crit_nodes))

    moves = 0
    insertions = 0
    p = -1

    while len(present) > 0:

        #print(crit_nodes)

        insertions = insertions + 1

        x = random.choice(present)
        i = crit_nodes.index(x)
        crit_nodes[i] = -x

        if i > p:
            moves = moves + 1
            p = i

        if not (x in crit_nodes):
            present.remove(x)

    return moves

if __name__ == "__main__":

    sub_sizes = [3,4,5,6]

    a = 0

    for i in range(1000):
        a = a + get_recourse(sub_sizes)
        print(a/(i+1))
