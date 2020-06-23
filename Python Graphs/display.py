# a python program to visualize graphs
from graphs import Graph
import matplotlib.pyplot as plt
import matplotlib
import math

def draw_graph(G):

    n = G.n

    h = 0.11
    w = 0.03

    # starting size of image
    plt.figure(figsize = (5,5))

    # lock aspect ratio
    ax = plt.gca()
    ax.set_aspect(1)

    # generate the positions of nodes in a circle and draw them
    nodes_x, nodes_y = [0]*n, [0]*n

    for i in range(n):
        nodes_x[i], nodes_y[i] = math.cos(2*math.pi*i/n), math.sin(2*math.pi*i/n)
        plt.text(nodes_x[i], nodes_y[i], str(i), ha='center', va='center', size=12, color='white')

    plt.plot(nodes_x, nodes_y, 'o',markersize=15, color='red', alpha=0.7)

    for u in range(n):
        for v in G.adj_list[u]:

            L = math.sqrt((nodes_x[u] - nodes_x[v])**2 + (nodes_y[u] - nodes_y[v])**2)
            r = (L - h)/L

            plt.arrow(nodes_x[u], nodes_y[u], (nodes_x[v] - nodes_x[u])*r, (nodes_y[v] - nodes_y[u])*r,
            head_width=w, head_length=h, width=0.0005, head_starts_at_zero=False, fc='0.5', ec='0.5', alpha=0.9)

    # do not display axes
    plt.axis('off')

    plt.show()
