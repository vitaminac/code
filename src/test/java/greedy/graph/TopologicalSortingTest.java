package greedy.graph;

import org.junit.Before;
import org.junit.Test;

public class TopologicalSortingTest {
    private DirectedGraph g = new DirectedGraph(6);

    @Before
    public void setUp() throws Exception {
        g.addEdge(5, 0);
        g.addEdge(4, 0);
        g.addEdge(5, 2);
        g.addEdge(4, 1);
        g.addEdge(2, 3);
        g.addEdge(3, 1);
    }

    @Test
    public void sort() {
        final TopologicalSorting topologicalSorting = new TopologicalSorting(this.g);
        topologicalSorting.sort();
        topologicalSorting.getSorting().stream().forEach(System.out::println);
    }
}