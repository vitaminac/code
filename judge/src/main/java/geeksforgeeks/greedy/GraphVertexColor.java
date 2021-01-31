package geeksforgeeks.greedy;

import java.util.Arrays;

import core.Reference;
import core.graph.SimpleUndirectedGraph;
import core.queue.ArrayHeap;
import core.queue.PriorityQueue;

// https://www.geeksforgeeks.org/graph-coloring-set-2-greedy-algorithm/
public class GraphVertexColor {
    public static int[] color(final SimpleUndirectedGraph graph) {
        int[] coloring = new int[graph.size()];
        Arrays.fill(coloring, -1);
        coloring[0] = 0;
        Reference<PriorityQueue<Integer>> pqRef = new Reference<>(new ArrayHeap<>(Integer::compareTo, graph.size()));
        for (int u = 1; u < graph.size(); u++) {
            int color = graph.getAdjacentVertices(u).reduce(0, (v, lowest) -> {
                if (coloring[v] >= lowest) pqRef.getReference().enqueue(coloring[v]);
                while (!pqRef.getReference().isEmpty() && lowest == (int) pqRef.getReference().peek()) {
                    lowest += 1;
                    pqRef.getReference().dequeue();
                }
                return lowest;
            });
            coloring[u] = color;
            pqRef.setReference(new ArrayHeap<>(Integer::compareTo, graph.size()));
        }
        return coloring;
    }
}
