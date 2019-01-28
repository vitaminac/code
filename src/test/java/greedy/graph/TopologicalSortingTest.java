package greedy.graph;

import org.junit.Before;
import org.junit.Test;

public class TopologicalSortingTest {
    private DirectedGraph g = new DirectedGraph(6);

    @Before
    public void setUp() throws Exception {
        g.add(5, 0, 0);
        g.add(4, 0, 0);
        g.add(5, 2, 0);
        g.add(4, 1, 0);
        g.add(2, 3, 0);
        g.add(3, 1, 0);
    }

    @Test
    public void sort() {
        final TopologicalSorting topologicalSorting = new TopologicalSorting(this.g);
        topologicalSorting.sort();
        topologicalSorting.getSorting().stream().forEach(System.out::println);
    }
}