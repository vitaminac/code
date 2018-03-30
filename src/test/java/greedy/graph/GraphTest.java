package greedy.graph;

import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;

import static org.junit.Assert.assertEquals;

public class GraphTest {
    private final Graph graph = new Graph(9);

    @Before
    public void setUp() throws Exception {
        this.graph.add(1, 3, 1);
        this.graph.add(1, 4, 2);
        this.graph.add(7, 3, 5);
        this.graph.add(4, 5, 1);
        this.graph.add(5, 2, 2);
        this.graph.add(2, 6, 4);
        this.graph.add(new Edge(3, 4, 3));
        this.graph.add(new Edge(1, 7, 6));
        this.graph.add(new Edge(7, 5, 8));
        this.graph.add(new Edge(7, 2, 7));
        this.graph.add(new Edge(4, 6, 9));
    }

    @Test
    public void getMinSpanningTree() {
        Edge edge1_3 = new Edge(1, 3, 1);
        Edge edge1_4 = new Edge(1, 4, 2);
        Edge edge7_3 = new Edge(7, 3, 5);
        Edge edge4_5 = new Edge(4, 5, 1);
        Edge edge5_2 = new Edge(5, 2, 2);
        Edge edge2_6 = new Edge(2, 6, 4);

        HashSet<Edge> sol = new HashSet<>();
        sol.add(edge1_3);
        sol.add(edge7_3);
        sol.add(edge1_4);
        sol.add(edge4_5);
        sol.add(edge5_2);
        sol.add(edge2_6);
        assertEquals(sol, this.graph.getMinSpanningTree());
    }
}