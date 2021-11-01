package geeksforgeeks.greedy;

import java.util.Arrays;

import core.util.Reference;
import core.graph.SimpleUndirectedGraph;
import core.heap.ArrayHeap;
import core.heap.Heap;

// https://www.geeksforgeeks.org/graph-coloring-set-2-greedy-algorithm/
public class GraphVertexColor {
    public static int[] color(final SimpleUndirectedGraph graph) {
        int[] coloring = new int[graph.size()];
        Arrays.fill(coloring, -1);
        coloring[0] = 0;
        Reference<Heap<Integer>> pqRef = new Reference<>(new ArrayHeap<>(Integer::compareTo, graph.size()));
        for (int u = 1; u < graph.size(); u++) {
            int color = graph.getAdjacentVertices(u).reduce(0, (v, lowest) -> {
                if (coloring[v] >= lowest) pqRef.getReference().add(coloring[v]);
                while (!pqRef.getReference().isEmpty() && lowest == (int) pqRef.getReference().min()) {
                    lowest += 1;
                    pqRef.getReference().removeMin();
                }
                return lowest;
            });
            coloring[u] = color;
            pqRef.setReference(new ArrayHeap<>(Integer::compareTo, graph.size()));
        }
        return coloring;
    }
}
