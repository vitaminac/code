package code.geekforgeek.dfs;

import code.adt.graph.SimpleDirectedGraph;
import code.adt.graph.SimpleEdge;
import org.junit.Test;

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
        result.forEach(System.out::println);
    }
}