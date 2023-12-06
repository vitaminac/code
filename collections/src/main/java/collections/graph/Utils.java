package collections.graph;

import java.util.*;

public class Utils {
    private Utils() {
    }

    /**
     * @param source
     * @param sink
     * @param residual_network    bidirectional graph
     * @param residual_capacities
     * @return find a path from source to sink that every (u, v) in p satisfies c_f(u, v) > 0
     */
    private static List<Integer> bfs(int source, int sink, List<Integer>[] residual_network, Map<String, Integer> residual_capacities) {
        // number of vertices
        int N = residual_network.length;

        // init close set
        int[] parents = new int[N];
        Arrays.fill(parents, -1);

        // init open set
        Queue<Integer> queue = new ArrayDeque<>();
        queue.add(source);

        // while there are nodes without exploring and have not reached the sink yet
        int u;
        while (!queue.isEmpty() && parents[sink] == -1) {
            u = queue.remove();
            for (int v : residual_network[u]) {
                if (parents[v] == -1 && residual_capacities.get(u + "-" + v) > 0) {
                    queue.add(v);
                    parents[v] = u;
                }
            }
        }

        // find the reverse path
        List<Integer> path = new ArrayList<>();
        u = parents[sink];
        while (u != source) {
            if (u == -1) return null;
            path.add(u);
            u = parents[u];
        }
        Collections.reverse(path);
        return path;
    }

    public static long maxflowFordFulkerson(int source, int sink, List<Integer>[] residual_network, Map<String, Integer> residual_capacities) {
        // Ford-Fulkerson, see reference: Introduction to algorithm - pages 708~730
        long max_flow = 0;
        List<Integer> path;
        while ((path = bfs(source, sink, residual_network, residual_capacities)) != null) {
            // calculate residual capacity c_f(p)
            int residual_capacity = Integer.MAX_VALUE;
            int u = source;
            for (int v : path) {
                residual_capacity = Math.min(residual_capacity, residual_capacities.get(u + "-" + v));
                u = v;
            }

            // increment max flow
            max_flow += residual_capacity;

            // update residual network
            u = source;
            for (int v : path) {
                residual_capacities.put(u + "-" + v, residual_capacities.get(u + "-" + v) - residual_capacity);
                residual_capacities.put(v + "-" + u, residual_capacities.get(v + "-" + u) + residual_capacity);
                residual_network[v].add(u);
                u = v;
            }
        }
        return max_flow;
    }
}
