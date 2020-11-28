import matplotlib.pyplot as plt
import matplotlib
import math
import random
import numpy as np

def wacky_log(n,k):
    A = [0]*n
    d = k
    for i in range(n):
        A[i] = d*(i+1)*(math.log(d*(i+1)) + random.uniform(-0.1,0.1))
    return A

n = 10
k = 100

T = n*k

X = [100, 200, 300, 400, 500, 600, 700, 800, 900, 1000]

# λ = 5
SA1_5 = [360.27, 881.2, 1460.54, 2126.03, 2862.99, 3535.49, 4385.2, 5154.57, 5907.63, 6710.69]
SA2_5 = [254.92, 589.28, 939.19, 1298.35, 1693.58, 2083.22, 2504.47, 2878.45, 3269.19, 3711.24]
SD_5 = [678.16, 1589.18, 2630.19, 3687.52, 4723.55, 5878.26, 7178.09, 8130.07, 9601.55, 10629.58]

# λ = 15
SA1_15 = [449.86, 1133.56, 1950.49, 2821.58, 3756.24, 4724.57, 5796.74, 6668.41, 7928.12, 8992.79]
SA2_15 = [323.81, 748.68, 1226.05, 1721.5, 2239.96, 2767.22, 3275.39, 3810.57, 4372.5, 4895.31]
SD_15 = [801.07, 1912.25, 3155.12, 4479.3, 5873.68, 7372.15, 8832.65, 10224.57, 11975.69, 13273.88]

# λ = 25
SA1_25 = [478.69, 1226.04, 2060.59, 3012.84, 4058.2, 5083.71, 6186.07, 7376.69, 8509.87, 9755.65]
SA2_25 = [343.93, 813.11, 1329.13, 1857.46, 2412.14, 2979.89, 3540.63, 4148.26, 4755.42, 5330.14]
SD_25 = [819.24, 2038.17, 3382.29, 4791.66, 6210.41, 7723.29, 9329.96, 11041.10, 12537.32, 14201.55]

for i in range(n):
    SA1_5[i] = SA1_5[i]/(k*(i+1))
    SA2_5[i] = SA2_5[i]/(k*(i+1))
    SD_5[i] = SD_5[i]/(k*(i+1))

    SA1_15[i] = SA1_15[i]/(k*(i+1))
    SA2_15[i] = SA2_15[i]/(k*(i+1))
    SD_15[i] = SD_15[i]/(k*(i+1))

    SA1_25[i] = SA1_25[i]/(k*(i+1))
    SA2_25[i] = SA2_25[i]/(k*(i+1))
    SD_25[i] = SD_25[i]/(k*(i+1))

fig, (ax1, ax2) = plt.subplots(1, 2, figsize=(12,6))

# # ONE WAY
# ax1.set_title('One-Way Search')
# ax1.set_ylabel('expected recourse per node, total recourse/|V|')
# ax1.set_xlabel('number of nodes in the graph, |V|')
# ax1.set(xlim=(k, T), ylim=(0, 20))
# ax1.plot(X, np.log2(X), label="log₂")
#
# ax1.plot(X, SA1_5, label="λ = 5")
# ax1.plot(X, SA1_15, label="λ = 15")
# ax1.plot(X, SA1_25, label="λ = 25")
#
# ax1.legend()
#
# # TWO WAY
# ax2.set_title('Two-Way Search')
# ax2.set_ylabel('expected recourse per node, total recourse/|V|')
# ax2.set_xlabel('number of nodes in the graph, |V|')
# ax2.set(xlim=(k, T), ylim=(0, 20))
# ax2.plot(X, np.log2(X), label="log₂")
#
# ax2.plot(X, SA2_5, label="λ = 5")
# ax2.plot(X, SA2_15, label="λ = 15")
# ax2.plot(X, SA2_25, label="λ = 25")
#
# ax2.legend()

# DNC
ax1.set_title('Divide and Conquer')
ax1.set_ylabel('expected recourse per node, total recourse/|V|')
ax1.set_xlabel('number of nodes in the graph, |V|')
ax1.set(xlim=(k, T), ylim=(0, 20))
ax1.plot(X, np.log2(X), label="log₂")

ax1.plot(X, SD_5, label="λ = 5")
ax1.plot(X, SD_15, label="λ = 15")
ax1.plot(X, SD_25, label="λ = 25")

ax1.legend()

# DNC2
for i in range(n):
    SD_5[i] = SD_5[i]/5
    SD_15[i] = SD_15[i]/15
    SD_25[i] = SD_25[i]/25

ax2.set_title('Divide and Conquer')
ax2.set_ylabel('expected recourse per insertion, total recourse/|E|')
ax2.set_xlabel('number of nodes in the graph, |V|')
ax2.set(xlim=(k, T), ylim=(0, 15))
ax2.plot(X, np.log2(X), label="log₂")

ax2.plot(X, SD_5, label="λ = 5")
ax2.plot(X, SD_15, label="λ = 15")
ax2.plot(X, SD_25, label="λ = 25")

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
