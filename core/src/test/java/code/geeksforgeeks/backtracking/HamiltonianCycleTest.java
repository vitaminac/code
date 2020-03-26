package code.geeksforgeeks.backtracking;

import code.adt.graph.SimpleEdge;
import code.adt.graph.SimpleUndirectedGraph;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

// https://www.geeksforgeeks.org/hamiltonian-cycle-backtracking-6/
public class HamiltonianCycleTest {

    @Test
    public void findOne() {
        final SimpleUndirectedGraph graph = new SimpleUndirectedGraph(5);
        graph.addEdge(new SimpleEdge(0, 1));
        graph.addEdge(new SimpleEdge(0, 3));
        graph.addEdge(new SimpleEdge(1, 0));
        graph.addEdge(new SimpleEdge(1, 2));
        graph.addEdge(new SimpleEdge(1, 3));
        graph.addEdge(new SimpleEdge(1, 4));
        graph.addEdge(new SimpleEdge(2, 1));
        graph.addEdge(new SimpleEdge(2, 4));
        graph.addEdge(new SimpleEdge(3, 0));
        graph.addEdge(new SimpleEdge(3, 1));
        graph.addEdge(new SimpleEdge(3, 4));
        graph.addEdge(new SimpleEdge(4, 1));
        graph.addEdge(new SimpleEdge(4, 2));
        graph.addEdge(new SimpleEdge(4, 3));
        Assert.assertEquals(Arrays.asList(0, 1, 2, 4, 3, 0), HamiltonianCycle.find(graph, 0));
    }

    @Test
    public void findNone() {
        final SimpleUndirectedGraph graph = new SimpleUndirectedGraph(5);
        graph.addEdge(new SimpleEdge(0, 1));
        graph.addEdge(new SimpleEdge(0, 3));
        graph.addEdge(new SimpleEdge(1, 0));
        graph.addEdge(new SimpleEdge(1, 2));
        graph.addEdge(new SimpleEdge(1, 3));
        graph.addEdge(new SimpleEdge(1, 4));
        graph.addEdge(new SimpleEdge(2, 1));
        graph.addEdge(new SimpleEdge(2, 4));
        graph.addEdge(new SimpleEdge(3, 0));
        graph.addEdge(new SimpleEdge(3, 1));
        graph.addEdge(new SimpleEdge(4, 1));
        graph.addEdge(new SimpleEdge(4, 2));
        assertTrue(HamiltonianCycle.find(graph, 0).isEmpty());
    }
}