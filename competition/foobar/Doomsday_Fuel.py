#!/bin/python

"""
Doomsday Fuel
=============

Making fuel for the LAMBCHOP's reactor core is a tricky process because of the exotic matter involved. It starts as raw ore, then during processing, begins randomly changing between forms, eventually reaching a stable form. There may be multiple stable forms that a sample could ultimately reach, not all of which are useful as fuel.

Commander Lambda has tasked you to help the scientists increase fuel creation efficiency by predicting the end state of a given ore sample. You have carefully studied the different structures that the ore can take and which transitions it undergoes. It appears that, while random, the probability of each structure transforming is fixed. That is, each time the ore is in 1 state, it has the same probabilities of entering the next state (which might be the same state).  You have recorded the observed transitions in a matrix. The others in the lab have hypothesized more exotic forms that the ore can become, but you haven't seen all of them.

Write a function solution(m) that takes an array of array of nonnegative ints representing how many times that state has gone to the next state and return an array of ints for each terminal state giving the exact probabilities of each terminal state, represented as the numerator for each state, then the denominator for all of them at the end and in simplest form. The matrix is at most 10 by 10. It is guaranteed that no matter which state the ore is in, there is a path from that state to a terminal state. That is, the processing will always eventually end in a stable state. The ore starts in state 0. The denominator will fit within a signed 32-bit integer during the calculation, as long as the fraction is simplified regularly.

For example, consider the matrix m:
[
 [0,1,0,0,0,1],  # s0, the initial state, goes to s1 and s5 with equal probability
 [4,0,0,3,2,0],  # s1 can become s0, s3, or s4, but with different probabilities
 [0,0,0,0,0,0],  # s2 is terminal, and unreachable (never observed in practice)
 [0,0,0,0,0,0],  # s3 is terminal
 [0,0,0,0,0,0],  # s4 is terminal
 [0,0,0,0,0,0],  # s5 is terminal
]
So, we can consider different paths to terminal states, such as:
s0 -> s1 -> s3
s0 -> s1 -> s0 -> s1 -> s0 -> s1 -> s4
s0 -> s1 -> s0 -> s5
Tracing the probabilities of each, we find that
s2 has probability 0
s3 has probability 3/14
s4 has probability 1/7
s5 has probability 9/14
So, putting that together, and making a common denominator, gives an answer in the form of
[s2.numerator, s3.numerator, s4.numerator, s5.numerator, denominator] which is
[0, 3, 2, 9, 14].

Languages
=========

To provide a Java solution, edit Solution.java
To provide a Python solution, edit solution.py

Test cases
==========
Your code should pass the following test cases.
Note that it may also be run against hidden test cases not shown here.

-- Java cases --
Input:
Solution.solution({{0, 2, 1, 0, 0}, {0, 0, 0, 3, 4}, {0, 0, 0, 0, 0}, {0, 0, 0, 0,0}, {0, 0, 0, 0, 0}})
Output:
    [7, 6, 8, 21]

Input:
Solution.solution({{0, 1, 0, 0, 0, 1}, {4, 0, 0, 3, 2, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}})
Output:
    [0, 3, 2, 9, 14]

-- Python cases --
Input:
solution.solution([[0, 2, 1, 0, 0], [0, 0, 0, 3, 4], [0, 0, 0, 0, 0], [0, 0, 0, 0,0], [0, 0, 0, 0, 0]])
Output:
    [7, 6, 8, 21]

Input:
solution.solution([[0, 1, 0, 0, 0, 1], [4, 0, 0, 3, 2, 0], [0, 0, 0, 0, 0, 0], [0, 0, 0, 0, 0, 0], [0, 0, 0, 0, 0, 0], [0, 0, 0, 0, 0, 0]])
Output:
    [0, 3, 2, 9, 14]
"""

from fractions import Fraction, gcd
from functools import reduce
try:
    from math import gcd
except ImportError:
    from fractions import gcd


def identity(n):
    return [[1 if i == j else 0 for j in range(n)] for i in range(n)]


def subtract(A, B):
    assert len(A) == len(B)
    result = [x[:] for x in [[0] * len(A)] * len(A)]
    for i in range(len(A)):
        for j in range(len(B)):
            result[i][j] = A[i][j] - B[i][j]
    return result


def invert(m):
    n = len(m)
    # print("n:", n)
    inverse = identity(n)
    for col in range(n):
        # print("reducing colunm " + str(col))
        # print("m:", m)
        # print("inverse:", inverse)
        pivot = col
        # select pivot
        k = Fraction(1, m[pivot][col])
        # divide to 1
        m[pivot] = [k*i for i in m[pivot]]
        inverse[pivot] = [k*i for i in inverse[pivot]]
        # print("m:", m)
        # print("inverse:", inverse)
        for row in range(n):
            # print("eliminating row " + str(row))
            if row != pivot:
                k = m[row][col]
                m[row] = [(v - m[pivot][c]*k) for c, v in enumerate(m[row])]
                inverse[row] = [(v - inverse[pivot][c]*k)
                                for c, v in enumerate(inverse[row])]
            # print("m:", m)
            # print("inverse:", inverse)
    return inverse


def multiply(A, B):
    result = [x[:] for x in [[0] * len(B[0])] * len(A)]
    for i in range(len(A)):
        for j in range(len(B[0])):
            # print(result)
            for k in range(len(A[0])):
                result[i][j] += A[i][k] * B[k][j]
    return result


def determinate_states(m):
    # determine absorbing state and non transient state
    absorbing_states = []
    transient_states = []
    for idx, row in enumerate(m):
        if sum(row) == 0:
            absorbing_states.append(idx)
        else:
            transient_states.append(idx)
    # print("absorbing_states:", absorbing_states)
    # print("transient_states:", transient_states)
    return absorbing_states, transient_states


def extract_Q_and_R(m, absorbing_states, transient_states):
    # denominator
    denominators = [sum(m[idx]) for idx in transient_states]
    # print("denominators:", denominators)
    # determine Q
    Q = [x[:] for x in [[0] * len(transient_states)] * len(transient_states)]
    # print("Q:", Q)
    for i, t1 in enumerate(transient_states):
        for j, t2 in enumerate(transient_states):
            Q[i][j] = Fraction(m[t1][t2], denominators[i])
    # print("Q:", Q)
    # determine R
    R = [x[:] for x in [[0] * len(absorbing_states)] * len(transient_states)]
    # print("R:", R)
    for i, t in enumerate(transient_states):
        for j, a in enumerate(absorbing_states):
            R[i][j] = Fraction(m[t][a], denominators[i])
    # print("R:", R)
    return Q, R


def solution(m):
    # https://en.wikipedia.org/wiki/Absorbing_Markov_chain
    if len(m) == 1:
        return 1, 1
    absorbing_states, transient_states = determinate_states(m)
    Q, R = extract_Q_and_R(m, absorbing_states, transient_states)
    # print("Q:", Q)
    # print("R:", R)
    I_minus_Q = subtract(identity(len(Q)), Q)
    # print("I_minus_Q:", I_minus_Q)
    # calculate fundamental matrix
    F = invert(I_minus_Q)
    # print("F:", F)
    # calculate limiting matrix
    B = multiply(F, R)
    # print("B:", B)
    answer = B[0]
    # print("answer:", answer)
    denominator = reduce(lambda x, y: x * y // gcd(x, y),
                         map(lambda f: f.denominator, answer), 1)
    # print("denominator:", denominator)
    normalized_answer = list(
        map(lambda f: f.numerator * denominator // f.denominator, answer))
    normalized_answer.append(denominator)
    # print("normalized answer:", normalized_answer)
    return normalized_answer


if __name__ == "__main__":
    # Absorbing Markov chain
    # https://www.youtube.com/watch?v=BsOkOaB8SFk
    # https://github.com/ivanseed/google-foobar-help/blob/master/challenges/doomsday_fuel/doomsday_fuel.md
    # https://github.com/rajakodumuri/Google-Foobar/blob/master/doomsday_fuel.py
    # https://surajshetiya.github.io/Google-foobar/#round-3
    # https://math.stackexchange.com/questions/2560653/proof-of-the-equation-of-the-fundamental-matrix-in-absorbing-markov-chains

    from unittest import TestCase
    t = TestCase()
    expected = [7, 6, 8, 21]
    actual = solution([[0, 2, 1, 0, 0], [0, 0, 0, 3, 4], [
                      0, 0, 0, 0, 0], [0, 0, 0, 0, 0], [0, 0, 0, 0, 0]])

    assert len(expected) == len(actual)
    assert all([a == b for a, b in zip(expected, actual)])

    expected = [0, 3, 2, 9, 14]
    actual = solution([[0, 1, 0, 0, 0, 1], [4, 0, 0, 3, 2, 0], [0, 0, 0, 0, 0, 0], [
                      0, 0, 0, 0, 0, 0], [0, 0, 0, 0, 0, 0], [0, 0, 0, 0, 0, 0]])
