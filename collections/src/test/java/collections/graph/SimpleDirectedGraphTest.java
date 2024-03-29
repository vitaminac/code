package collections.graph;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class SimpleDirectedGraphTest {
    @Test
    public void dfs() {
        SimpleDirectedGraph g3 = new SimpleDirectedGraph(4);
        g3.addEdge(new SimpleEdge(0, 1));
        g3.addEdge(new SimpleEdge(0, 2));
        g3.addEdge(new SimpleEdge(1, 2));
        g3.addEdge(new SimpleEdge(2, 0));
        g3.addEdge(new SimpleEdge(2, 3));
        g3.addEdge(new SimpleEdge(3, 3));

        List<Integer> list = new ArrayList<>();
        g3.dfs(2).forEach(list::add);
        // TODO: compare two tree
        assertEquals(Arrays.asList(2, 0, 1, 3), list);
    }

    @Test
    public void bfs() {
        SimpleDirectedGraph g3 = new SimpleDirectedGraph(4);
        g3.addEdge(new SimpleEdge(0, 1));
        g3.addEdge(new SimpleEdge(0, 2));
        g3.addEdge(new SimpleEdge(1, 2));
        g3.addEdge(new SimpleEdge(2, 0));
        g3.addEdge(new SimpleEdge(2, 3));
        g3.addEdge(new SimpleEdge(3, 3));

        List<Integer> list = new ArrayList<>();
        g3.bfs(2).forEach(list::add);
        assertEquals(Arrays.asList(2, 0, 3, 1), list);
    }
}