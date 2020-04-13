package geeksforgeeks.greedy;

import core.graph.SimpleUndirectedGraph;
import core.pq.ArrayHeap;
import core.pq.PriorityQueue;

import java.util.Arrays;

// https://www.geeksforgeeks.org/graph-coloring-set-2-greedy-algorithm/
public class GraphVertexColor {
    public static int[] color(final SimpleUndirectedGraph graph) {
        int[] coloring = new int[graph.size()];
        Arrays.fill(coloring, -1);
        coloring[0] = 0;
        PriorityQueue<Integer> pq = new ArrayHeap<>(Integer::compareTo, graph.size());
        for (int u = 1; u < graph.size(); u++) {
            int color = graph.getAdjacentVertices(u).reduce(0, (v, lowest) -> {
                if (coloring[v] >= lowest) pq.add(coloring[v]);
                while (!pq.isEmpty() && lowest == (int) pq.min()) {
                    lowest += 1;
                    pq.remove();
                }
                return lowest;
            });
            coloring[u] = color;
            pq.clear();
        }
        return coloring;
    }
}
