package geeksforgeeks.dfs;

import java.util.HashSet;
import java.util.Set;

import core.functional.Iterable;
import collections.graph.SimpleDirectedGraph;
import collections.graph.SimpleEdge;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class TopologicalSortingTest {
    @Test
    public void sort() {
        SimpleDirectedGraph g = new SimpleDirectedGraph(6);
        g.addEdge(new SimpleEdge(5, 0));
        g.addEdge(new SimpleEdge(4, 0));
        g.addEdge(new SimpleEdge(5, 2));
        g.addEdge(new SimpleEdge(4, 1));
        g.addEdge(new SimpleEdge(2, 3));
        g.addEdge(new SimpleEdge(3, 1));
        var result = new TopologicalSorting().getTopologicalSorting(g);
        Set<Integer> candidates = new HashSet<>();
        Iterable.range(0, 6, 1).forEach(candidates::add);
        while (!result.isEmpty()) {
            int u = result.pop();
            candidates.remove(u);
            for (int i : candidates) {
                assertTrue(g.getAdjacentVertices(i).filter(candidates::contains).all(v -> v != u));
            }
        }
    }
}