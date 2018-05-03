package greedy.graph;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class DirectedGraphhTest {
    @Test
    public void getShortestPath() {
        DirectedGraphh graph = new DirectedGraphh(5);
        graph.add(0, 1, 10);
        graph.add(0, 4, 5);
        graph.add(1, 2, 1);
        graph.add(1, 4, 2);
        graph.add(2, 3, 4);
        graph.add(3, 2, 6);
        graph.add(3, 0, 7);
        graph.add(4, 1, 3);
        graph.add(4, 2, 9);
        graph.add(4, 3, 2);

        final Map<Integer, Double> shortestPath = graph.getShortestPath(0);
        final Map<Integer, Double> sol = new HashMap<>();
        sol.put(0, 0.0);
        sol.put(1, 8.0);
        sol.put(2, 9.0);
        sol.put(3, 7.0);
        sol.put(4, 5.0);
        assertEquals(sol, shortestPath);
    }
}