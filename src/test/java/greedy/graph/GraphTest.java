package greedy.graph;

import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;

import static org.junit.Assert.assertEquals;

public class GraphTest {
    private final Graph graph = new Graph(7);
    private final HashSet<Edge> sol = new HashSet<>();

    @Before
    public void setUp() throws Exception {
        this.graph.add(0, 2, 1);
        this.graph.add(0, 3, 2);
        this.graph.add(6, 2, 5);
        this.graph.add(3, 4, 1);
        this.graph.add(4, 1, 2);
        this.graph.add(1, 5, 4);
        this.graph.add(2, 3, 3);
        this.graph.add(0, 6, 6);
        this.graph.add(6, 4, 8);
        this.graph.add(6, 1, 7);
        this.graph.add(3, 5, 9);


        Edge edge1_3 = new Edge(0, 2, 1);
        Edge edge1_4 = new Edge(0, 3, 2);
        Edge edge7_3 = new Edge(6, 2, 5);
        Edge edge4_5 = new Edge(3, 4, 1);
        Edge edge5_2 = new Edge(4, 1, 2);
        Edge edge2_6 = new Edge(1, 5, 4);
        this.sol.add(edge1_3);
        this.sol.add(edge7_3);
        this.sol.add(edge1_4);
        this.sol.add(edge4_5);
        this.sol.add(edge5_2);
        this.sol.add(edge2_6);
    }

    @Test
    public void getMinSpanningTreeKruskal() {
        assertEquals(sol, this.graph.getMinSpanningTreeKruskal());
    }

    @Test
    public void getMinSpanningTreePrim() {
        assertEquals(sol, this.graph.getMinSpanningTreePrim());
    }
}