package greedy.graph;

import org.junit.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

    @Test
    public void kruskalAlgorithm() throws Exception {
        ArrayList<Edge> edges = new ArrayList<>();
        edges.add(new Edge(1, 4, 2));
        edges.add(new Edge(3, 4, 3));
        edges.add(new Edge(1, 7, 6));
        edges.add(new Edge(1, 3, 1));
        edges.add(new Edge(7, 3, 5));
        edges.add(new Edge(4, 5, 1));
        edges.add(new Edge(7, 5, 8));
        edges.add(new Edge(7, 2, 7));
        edges.add(new Edge(4, 6, 9));
        edges.add(new Edge(2, 6, 4));
        final Graph g = this.createTestGraph(7, edges);
        KruskalAlgorithm kruskalAlgorithm = new KruskalAlgorithm(g);
        Set<Edge> minSpanningTree = kruskalAlgorithm.kruskalAlgorithm();

        System.out.println("The spanning tree is ");
        for (Edge e : minSpanningTree) {
            int o = e.getSourcevertex();
            int d = e.getDestinationvertex();
            double w = e.getWeight();
            System.out.println("Origin " + o + " Destination " + d + " Weight " + w);
        }
    }
}