package greedy.graph;

import org.junit.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class KruskalAlgorithmTest {
    private Graph createTestGraph(int nVertex, List<Edge> edges) throws Exception {
        final Graph graph = new Graph(nVertex, edges.size());
        final Field nField = Graph.class.getDeclaredField("n");
        final Field edgesField = Graph.class.getDeclaredField("edges");
        nField.setAccessible(true);
        edgesField.setAccessible(true);
        nField.set(graph, nVertex);
        edgesField.set(graph, edges);
        return graph;
    }

    private void test(int nVertex, List<Edge> edges, HashSet<Edge> sol) throws Exception {
        final Graph g = this.createTestGraph(7, edges);
        KruskalAlgorithm kruskalAlgorithm = new KruskalAlgorithm(g);
        Set<Edge> minSpanningTree = kruskalAlgorithm.kruskalAlgorithm();
        assertEquals(sol, minSpanningTree);
    }

    @Test
    public void kruskalAlgorithm() throws Exception {
        ArrayList<Edge> edges = new ArrayList<>();
        HashSet<Edge> sol = new HashSet<>();

        Edge edge1_3 = new Edge(1, 3, 1);
        Edge edge1_4 = new Edge(1, 4, 2);
        Edge edge7_3 = new Edge(7, 3, 5);
        Edge edge4_5 = new Edge(4, 5, 1);
        Edge edge5_2 = new Edge(5, 2, 2);
        Edge edge2_6 = new Edge(2, 6, 4);

        edges.add(edge1_4);
        edges.add(new Edge(3, 4, 3));
        edges.add(new Edge(1, 7, 6));
        edges.add(edge1_3);
        edges.add(edge7_3);
        edges.add(edge4_5);
        edges.add(new Edge(7, 5, 8));
        edges.add(new Edge(7, 2, 7));
        edges.add(new Edge(4, 6, 9));
        edges.add(edge2_6);
        edges.add(edge5_2);

        sol.add(edge1_3);
        sol.add(edge7_3);
        sol.add(edge1_4);
        sol.add(edge4_5);
        sol.add(edge5_2);
        sol.add(edge2_6);
        this.test(7, edges, sol);
    }
}