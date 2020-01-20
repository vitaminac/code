package code.geekforgeek.dfs;

import code.adt.graph.SimpleEdge;
import code.adt.graph.SimpleUndirectedGraph;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

public class ArticulationPointsTest {

    @Test
    public void findArticulationPoint() {
        final SimpleUndirectedGraph<SimpleEdge> graph = new SimpleUndirectedGraph<>(5);
        graph.addEdge(new SimpleEdge(1, 2));
        graph.addEdge(new SimpleEdge(1, 0));
        graph.addEdge(new SimpleEdge(2, 0));
        graph.addEdge(new SimpleEdge(0, 3));
        graph.addEdge(new SimpleEdge(3, 4));
        assertArrayEquals(
                new boolean[]{true, false, false, true, false},
                new ArticulationPoints().findArticulationPoint(graph)
        );
    }

    @Test
    public void findArticulationPointTestCase() {
        final SimpleUndirectedGraph<SimpleEdge> graph = new SimpleUndirectedGraph<>(7);
        graph.addEdge(new SimpleEdge(0, 1));
        graph.addEdge(new SimpleEdge(0, 2));
        graph.addEdge(new SimpleEdge(1, 2));
        graph.addEdge(new SimpleEdge(1, 3));
        graph.addEdge(new SimpleEdge(1, 4));
        graph.addEdge(new SimpleEdge(1, 6));
        graph.addEdge(new SimpleEdge(3, 5));
        graph.addEdge(new SimpleEdge(4, 5));
        assertArrayEquals(
                new boolean[]{false, true, false, false, false, false, false},
                new ArticulationPoints().findArticulationPoint(graph)
        );
    }
}