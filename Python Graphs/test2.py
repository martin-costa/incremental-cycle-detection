from ICD_testing import *
import math
from display import *

def flog(last, n, k, i):
    return last*((math.sqrt(n)/(k-i+1)) + 0.5 * (1-last/(2*(k-i+1))) )

def f1(last, n, k, i):
    return last*((1/(k-i+1)) + 0.5 * (1-last/(2*(k-i+1))) )

def R(Ri, i, delta):

    Li = 1 - Ri
    return Li*delta + 0.5*Ri*(Li + 0.5*Ri)

if __name__ == "__main__":

    n = 250
    delta = 0.2

    vals_x = [0]*n
    vals_y = [0]*n

    vals_x[0] = 0
    vals_y[0] = 1

    for i in range(1, n):

        vals_x[i] = i
        vals_y[i] = R(vals_y[i-1], i, delta)

    plt.plot(vals_x, vals_y)
    plt.show()
