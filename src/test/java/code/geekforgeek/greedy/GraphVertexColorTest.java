package code.geekforgeek.greedy;

import code.adt.graph.SimpleUndirectedGraph;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

public class GraphVertexColorTest {

    @Test
    public void coloring1() {
        final SimpleUndirectedGraph graph = new SimpleUndirectedGraph(4);
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(0, 3);
        graph.addEdge(2, 3);
        assertArrayEquals(new int[]{0, 1, 1, 2}, new GraphVertexColor().color(graph));
    }

    @Test
    public void coloring2() {
        final SimpleUndirectedGraph g1 = new SimpleUndirectedGraph(5);
        g1.addEdge(0, 1);
        g1.addEdge(0, 2);
        g1.addEdge(1, 2);
        g1.addEdge(1, 3);
        g1.addEdge(2, 3);
        g1.addEdge(3, 4);
        assertArrayEquals(new int[]{0, 1, 2, 0, 1}, new GraphVertexColor().color(g1));
    }

    @Test
    public void coloring3() {
        final SimpleUndirectedGraph g2 = new SimpleUndirectedGraph(5);
        g2.addEdge(0, 1);
        g2.addEdge(0, 2);
        g2.addEdge(1, 2);
        g2.addEdge(1, 4);
        g2.addEdge(2, 4);
        g2.addEdge(4, 3);
        assertArrayEquals(new int[]{0, 1, 2, 0, 3}, new GraphVertexColor().color(g2));
    }
}