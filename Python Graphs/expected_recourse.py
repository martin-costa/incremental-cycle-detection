import matplotlib.pyplot as plt
import matplotlib
import math
import random
import numpy as np

def expected(n):

    sum = 0

    n = math.floor(n)

    for t in range(1, n):
        for i in range(0, t):
            sum = sum + ((t/n)**i)*(1-t/n)*i

    return sum

def path_gen(n):

    edges = list(range(n-1))
    random.shuffle(edges)

    current_pos = 0
    moves = 0

    path = set()

    while len(edges) > 0:
        e = edges.pop()
        path.add(e)

        if current_pos == e:
            moves = moves + 1
            #return n-1-len(edges)
            while current_pos in path:
                current_pos = current_pos + 1

    return moves

if __name__ == "__main__":

    a = 0
    for i in range(100000):
        a = a + path_gen(45)
        print(a/(i+1))

    size = 10

    N = 4

    tries = 400

    vals_y = [0]*size
    vals_logn = [0]*size
    vals_lognfbyn = [0]*size
    vals_x = np.linspace(1, N, size)

    for j in range(size):
        a = 0
        for i in range(tries):
            a = a + path_gen(int(vals_x[j]))

        vals_y[j] = a/tries
        vals_logn[j] = math.log(vals_x[j]) + 0.5
        vals_lognfbyn[j] = 0
        for l in range(1, int(vals_x[j])):
            vals_lognfbyn[j] = vals_lognfbyn[j] + math.log(l) + 0.5
        vals_lognfbyn[j] = vals_lognfbyn[j] / int(vals_x[j]) + 1

    plt.plot(vals_x, vals_y)
    plt.plot(vals_x, vals_logn)
    plt.plot(vals_x, vals_lognfbyn)
    plt.show()

    # N = 1000
    #
    # size = 100
    #
    # vals_x = np.linspace(1, N, size)
    #
    # vals_y = [0]*size
    # vals_nlogn = [0]*size
    #
    # for i in range(size):
    #     vals_y[i] = expected(vals_x[i])
    #     vals_nlogn[i] = math.log(vals_x[i])*vals_x[i]
    #
    # plt.plot(vals_x, vals_y)
    # plt.plot(vals_x, vals_nlogn)
    # plt.plot(vals_x, vals_x)
    # plt.show()
