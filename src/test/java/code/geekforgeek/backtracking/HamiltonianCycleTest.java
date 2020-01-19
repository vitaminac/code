package code.geekforgeek.backtracking;

import greedy.graph.Graph;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

// https://www.geeksforgeeks.org/hamiltonian-cycle-backtracking-6/
public class HamiltonianCycleTest {

    @Test
    public void findOne() {
        final Graph graph = new Graph(5);
        graph.add(0, 1);
        graph.add(0, 3);
        graph.add(1, 0);
        graph.add(1, 2);
        graph.add(1, 3);
        graph.add(1, 4);
        graph.add(2, 1);
        graph.add(2, 4);
        graph.add(3, 0);
        graph.add(3, 1);
        graph.add(3, 4);
        graph.add(4, 1);
        graph.add(4, 2);
        graph.add(4, 3);
        assertEquals(Arrays.asList(0, 1, 2, 4, 3, 0), new HamiltonianCycle().find(graph, 0));
    }

    @Test
    public void findNone() {
        final Graph graph = new Graph(5);
        graph.add(0, 1);
        graph.add(0, 3);
        graph.add(1, 0);
        graph.add(1, 2);
        graph.add(1, 3);
        graph.add(1, 4);
        graph.add(2, 1);
        graph.add(2, 4);
        graph.add(3, 0);
        graph.add(3, 1);
        graph.add(4, 1);
        graph.add(4, 2);
        assertTrue(new HamiltonianCycle().find(graph, 0).isEmpty());
    }
}