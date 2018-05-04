package greedy.graph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

public class DirectedGraph extends Graph {

    public DirectedGraph(int n) {
        super(n);
    }

    @Override
    public void add(int u, int v, double w) {
        final Edge edge = new Edge(u, v, w);
        this.edges.get(u).add(edge);
    }

    public Map<Integer, Double> getShortestPath(int u) {
        HashSet<Integer> vertices = new HashSet<>(this.edges.keySet());
        HashMap<Integer, Double> distances = new HashMap<>();
        for (int i = 0; i < this.n; i++) {
            distances.put(i, Double.POSITIVE_INFINITY);
        }
        PriorityQueue<Edge> paths = new PriorityQueue<>();
        distances.put(u, 0.0);
        paths.add(new Edge(u, u, 0.0));
        for (int i = 0; i < this.n; i++) {
            Edge nearestVertex;
            do {
                nearestVertex = paths.remove();
            } while (!vertices.contains(nearestVertex.getDestination()));
            vertices.remove(nearestVertex.getDestination());
            double baseDistance = distances.get(nearestVertex.getDestination());
            for (Edge edge : this.getEdge(nearestVertex.getDestination())) {
                paths.add(new Edge(nearestVertex.getDestination(), edge.getDestination(), baseDistance + edge.getWeight()));
            }
            for (Edge path : paths) {
                if (path.getWeight() < distances.get(path.getDestination())) {
                    distances.put(path.getDestination(), path.getWeight());
                }
            }
        }
        return distances;
    }

    @Override
    public Set<Integer> getAdjVertices(int v) {
        Set<Integer> vertices = new HashSet<>();
        for (Edge edge : this.getEdge(v)) {
            vertices.add(edge.getDestination());
        }
        return vertices;
    }
}
