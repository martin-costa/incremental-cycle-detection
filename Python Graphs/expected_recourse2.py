import matplotlib.pyplot as plt
import matplotlib
import math
import random
import numpy as np

def get_recourse(k):

    n = k*k
    m = int((3/2)*k*(k-1))

    vals_x = [0]*m
    vals_y = [0]*m

    t = 0

    sets = []
    for i in range(k):
        sets.append([0]*k)

    edges = []

    for i in range(k):
        for j in range(k-1):
            # edges.append((i*k + j, i*k + j + 1))  what they are not relevant
            edges.append((i, j))

    for i in range(k):
        for j in range(i+1, k):
            edges.append((-1, -1)) # what they are not relevant

    random.shuffle(edges)

    added = []
    tail_reach = [0]*(k-1)
    head_reach = [0]*(k-1)

    for i in range(k):
        added.append([0]*(k-1))

    expected_rec = 0

    while len(edges) > 0:

        vals_x[t] = t

        e = edges.pop()
        (i,j) = (e[0], e[1])

        if i >= 0:
            added[i][j] = 1
            expected_rec = expected(added, k)

        vals_y[t] = expected_rec
        t = t + 1

    return vals_y

def expected(added, k):

    tail_reach = [0]*(k)
    head_reach = [0]*(k)

    for i in range(k):

        j = 0

        while j < k-1 and added[i][j] == 1:
            tail_reach[i] = tail_reach[i] + 1
            j = j + 1

        j = k-2

        while j >= 0 and added[i][j] == 1:
            head_reach[i] = head_reach[i] + 1
            j = j - 1

    expected_rec = 0

    for i in range(k-1):
        expected_rec = expected_rec + head_reach[i] + tail_reach[i]

    expected_rec = expected_rec / (2*k)

    return expected_rec

if __name__ == "__main__":

    k = 200

    vals_y = get_recourse(k)

    vals_x = [0]*len(vals_y)

    for i in range(len(vals_x)):
        vals_x[i] = i

    plt.plot(vals_x, vals_y)
    plt.show()
