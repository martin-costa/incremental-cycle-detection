import matplotlib.pyplot as plt
import matplotlib
import math
import random
import numpy as np

def wacky_log(n):
    A = [0]*n
    for i in range(n):
        A[i] = n*math.log(i+1) + random.uniform(-1,1)
    return A

n = 50
X = list(range(n))

A = wacky_log(n)
B = wacky_log(n)

fig, (ax1, ax2) = plt.subplots(1, 2)

ax1.set_title('One-Way Search')
ax1.set_ylabel('expected amortized recourse per node')
ax1.set_xlabel('number of nodes in the graph, |V|')
ax1.plot(X, A, color="red", label="A_1")
ax1.legend()

ax2.set_title('Two-Way Search')
ax2.set_ylabel('recourse/|V|')
ax2.set_xlabel('number of nodes in the graph, |V|')
ax2.plot(X, B, color="blue", label="A_2")
ax2.legend()

plt.show()

# plt.clf()
# x = np.linspace(0, 1, 101)
# y1 = np.sin(x * np.pi / 2)
# y2 = np.cos(x * np.pi / 2)
# plt.plot(x, y1, label='sin')
# plt.plot(x, y2, label='cos')
# plt.legend()
# plt.show()
