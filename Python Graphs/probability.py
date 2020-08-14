import matplotlib
import matplotlib.pyplot as plt
import math
import random

delta = 2

if __name__ == "__main__":

    runs = 15000

    n = 3200

    vals_x = [0]*delta
    vals_y = [0]*delta

    for k in range(delta-1, delta):

        average = 0

        for j in range(runs):

            A = 1
            B = k

            total = n

            for i in range(n):

                p = random.uniform(0, A+B)

                if p <= A:
                    total = i + 1
                    break
                else:
                    B += k - 1

            average = average + total

            #print(average/(j+1))

        print(average/runs)

        vals_x[k] = k
        vals_y[k] = average/runs

    plt.plot(vals_x, vals_y)

    plt.show()
