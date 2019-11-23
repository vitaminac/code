/*
  You've blown up the LAMBCHOP doomsday device and broken the bunnies out of Lambda's prison,
  and now you need to escape from the space station as quickly and as orderly as possible!
  The bunnies have all gathered in various locations throughout the station,
  and need to make their way towards the seemingly endless amount of escape pods positioned
  in other parts of the station.
  You need to get the numerous bunnies through the various rooms to the escape pods.
  Unfortunately, the corridors between the rooms can only fit so many bunnies at a time.
  What's more, many of the corridors were resized to accommodate the LAMBCHOP,
  so they vary in how many bunnies can move through them at a time.
  <p>
  Given the starting room numbers of the groups of bunnies,
  the room numbers of the escape pods,
  and how many bunnies can fit through at a time in each direction of every corridor in between,
  figure out how many bunnies can safely make it to the escape pods at a time at peak.
  <p>
  Write a function solution(entrances, exits, path) that takes
  an array of integers denoting where the groups of gathered bunnies are,
  an array of integers denoting where the escape pods are located,
  and an array of an array of integers of the corridors,
  returning the total number of bunnies that can get through at each time step as an int.
  The entrances and exits are disjoint and thus will never overlap.
  The path element path[A][B] = C describes that the corridor going from A to B can fit C bunnies at each time step.
  There are at most 50 rooms connected by the corridors and at most 2000000 bunnies that will fit at a time.
  <p>
  For example, if you have:
  entrances = [0, 1]
  exits = [4, 5]
  path = [
  [0, 0, 4, 6, 0, 0],  # Room 0: Bunnies
  [0, 0, 5, 2, 0, 0],  # Room 1: Bunnies
  [0, 0, 0, 0, 4, 4],  # Room 2: Intermediate room
  [0, 0, 0, 0, 6, 6],  # Room 3: Intermediate room
  [0, 0, 0, 0, 0, 0],  # Room 4: Escape pods
  [0, 0, 0, 0, 0, 0],  # Room 5: Escape pods
  ]
  <p>
  Then in each time step, the following might happen:
  0 sends 4/4 bunnies to 2 and 6/6 bunnies to 3
  1 sends 4/5 bunnies to 2 and 2/2 bunnies to 3
  2 sends 4/4 bunnies to 4 and 4/4 bunnies to 5
  3 sends 4/6 bunnies to 4 and 4/6 bunnies to 5
  <p>
  So, in total, 16 bunnies could make it to the escape pods at 4 and 5 at each time step.
  (Note that in this example, room 3 could have sent any variation of 8 bunnies to 4 and 5,
  such as 2/6 and 6/6, but the final solution remains the same.)
  <p>
  Languages
  =========
  <p>
  To provide a Java solution, edit Solution.java
  To provide a Python solution, edit solution.py
  <p>
  Test cases
  ==========
  Your code should pass the following test cases.
  Note that it may also be run against hidden test cases not shown here.
  <p>
  -- Java cases --
  Input:
  Solution.solution({0, 1}, {4, 5}, {{0, 0, 4, 6, 0, 0}, {0, 0, 5, 2, 0, 0}, {0, 0, 0, 0, 4, 4}, {0, 0, 0, 0, 6, 6}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}})
  Output:
  16
  <p>
  Input:
  Solution.solution({0}, {3}, {{0, 7, 0, 0}, {0, 0, 6, 0}, {0, 0, 0, 8}, {9, 0, 0, 0}})
  Output:
  6
  <p>
  -- Python cases --
  Input:
  solution.solution([0], [3], [[0, 7, 0, 0], [0, 0, 6, 0], [0, 0, 0, 8], [9, 0, 0, 0]])
  Output:
  6
  <p>
  Input:
  solution.solution([0, 1], [4, 5], [[0, 0, 4, 6, 0, 0], [0, 0, 5, 2, 0, 0], [0, 0, 0, 0, 4, 4], [0, 0, 0, 0, 6, 6], [0, 0, 0, 0, 0, 0], [0, 0, 0, 0, 0, 0]])
  Output:
  16
 */

import java.util.List;
import java.util.Collections;
import java.util.ArrayList;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Arrays;


class Solution {
    private static final int INF = Integer.MAX_VALUE;

    private static int[][] transform(int[] sources, int[] sinks, int[][] network) {
        // transform to a equivalent single-source, single-sink flow network
        int length = network.length;
        int newLength = length + 2;
        int[][] newNetwork = new int[newLength][newLength];
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                newNetwork[i + 1][j + 1] = network[i][j];
            }
        }
        for (int entrance : sources) {
            newNetwork[0][entrance + 1] = INF;
        }
        for (int exit : sinks) {
            newNetwork[exit + 1][newLength - 1] = INF;
        }
        return newNetwork;
    }

    private static List<Integer> bfs(int[][] residual_network) {
        // find a path from s to t that every (u, v) in p satisfies c_f(u, v) > 0
        int[] parents = new int[residual_network.length];
        Arrays.fill(parents, -1);
        Queue<Integer> queue = new ArrayDeque<>();
        queue.add(0);
        int u;
        for (; !queue.isEmpty() && parents[parents.length - 1] == -1; ) {
            u = queue.remove();
            for (int v = 0; v < parents.length; v++) {
                if (residual_network[u][v] > 0 && parents[v] == -1) {
                    queue.add(v);
                    parents[v] = u;
                }
            }
        }
        List<Integer> path = new ArrayList<>();
        u = parents[parents.length - 1];
        while (u != 0) {
            if (u == -1) return null;
            path.add(u);
            u = parents[u];
        }
        Collections.reverse(path);
        return path;
    }

    private static int solveWithFordFulkerson(int[][] residual_network) {
        // https://en.wikipedia.org/wiki/Ford%E2%80%93Fulkerson_algorithm
        int max_flow = 0;
        List<Integer> path;
        while ((path = bfs(residual_network)) != null) {
            // calculate residual capacity c_f(p)
            int residual_capacity = INF;
            int u = 0;
            for (int v : path) {
                residual_capacity = Math.min(residual_capacity, residual_network[u][v]);
                u = v;
            }
            // increment max flow
            max_flow += residual_capacity;
            u = 0;
            // update residual network
            for (int v : path) {
                residual_network[u][v] -= residual_capacity;
                residual_network[v][u] += residual_capacity;
                u = v;
            }
        }
        return max_flow;
    }

    public static int solution(int[] entrances, int[] exits, int[][] path) {
        return solveWithFordFulkerson(transform(entrances, exits, path));
    }

    public static void main(String[] args) {
        assert solution(new int[]{0}, new int[]{3}, new int[][]{{0, 7, 0, 0}, {0, 0, 6, 0}, {0, 0, 0, 8}, {9, 0, 0, 0}}) == 6;
        assert solution(new int[]{0, 1}, new int[]{4, 5}, new int[][]{{0, 0, 4, 6, 0, 0}, {0, 0, 5, 2, 0, 0}, {0, 0, 0, 0, 4, 4}, {0, 0, 0, 0, 6, 6}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}}) == 16;
    }
}