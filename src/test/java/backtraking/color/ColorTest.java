package backtraking.color;

import code.adt.graph.SimpleEdge;
import code.adt.graph.SimpleUndirectedGraph;
import org.junit.Test;

public class ColorTest {

    @Test
    public void color() {
        final SimpleUndirectedGraph<SimpleEdge> graph = new SimpleUndirectedGraph<SimpleEdge>(4);
        graph.addEdge(new SimpleEdge(0, 1));
        graph.addEdge(new SimpleEdge(0, 2));
        graph.addEdge(new SimpleEdge(0, 3));
        graph.addEdge(new SimpleEdge(2, 3));
        final Color color = new Color(graph, 3);
        color.color();
        System.out.println(color);
    }
}