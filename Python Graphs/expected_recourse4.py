import matplotlib.pyplot as plt
import matplotlib
import math
import random
import numpy as np

def trial(n):

    A = [0]*n

    a = 0
    b = n

    for k in range(n):

        p = random.uniform(0,1)

        if p <= a/(a+b):
            a = a - 1
        else:
            A[k] = 1
            a = a + (b-1)/2
            b = (b-1)/2

    return A

def ith_val_idx(A, count, val):

    i = -1
    c = 0

    while c < count:
        i = i + 1
        if A[i] == val:
            c = c + 1

    return i

def F(k):

    nodes = [0]*k
    t = 0
    head = -1

    crits = [0]*k
    right = [0]*k

    while t < k:

        right[t] = k - head - 1

        p = random.randint(1, k-t)
        i = ith_val_idx(nodes, p, 0)

        if i > head:
            head = i
            crits[t] = 1

        nodes[i] = 1
        t = t + 1

    return (crits, right)


if __name__ == "__main__":

    runs = 100

    n = 50

    X = list(range(n))
    A = [0]*n
    A1 = [0]*n

    B = [0]*n
    C = [0]*n

    for i in range(runs):
        B, C = F(n)

        for j in range(n):
            A[j] = A[j] + B[j]
            A1[j] = A1[j] + C[j]

    for j in range(n):
        A[j] = A[j]/runs
        A1[j] = A1[j]/runs

        B[j] = (n-j)/(j+1)
        C[j] = 1/(j+1)

    plt.plot(X, A, color="red")
    plt.plot(X, C)

    plt.show()

    plt.plot(X, A1, color="red")
    plt.plot(X, B)

    plt.show()
