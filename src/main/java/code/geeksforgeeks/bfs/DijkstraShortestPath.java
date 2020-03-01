package code.geeksforgeeks.bfs;

import code.adt.graph.SimpleDirectedWeightedGraph;
import code.adt.graph.SimpleWeightedEdge;

import java.util.Arrays;
import java.util.PriorityQueue;

// https://www.geeksforgeeks.org/dijkstras-shortest-path-algorithm-greedy-algo-7/
public class DijkstraShortestPath {
    public double[] find(SimpleDirectedWeightedGraph g, int u) {
        PriorityQueue<SimpleWeightedEdge> candidate = new PriorityQueue<>();
        // Keeps track of vertices whose minimum distance from source is calculated and finalized
        boolean[] visited = new boolean[g.size()];
        // Assign a distance value to all vertices in the input graph. Initialize all distance values as INFINITE.
        double[] distances = new double[g.size()];
        Arrays.fill(distances, Double.POSITIVE_INFINITY);
        // keep path
        int[] parent = new int[g.size()];
        Arrays.fill(parent, -1);
        // Set distance from source to it self is zero
        distances[u] = 0.0;
        candidate.add(new SimpleWeightedEdge(u, u, 0.0));
        // each iteration find a new path from source to a un-calculated vertex
        for (int i = 0; i < distances.length; i++) {
            // Pick a vertex v which is not there in visited and has minimum distance value
            SimpleWeightedEdge nearest;
            do {
                nearest = candidate.remove();
            } while (visited[nearest.getDestination()]);
            // Include v to visited.
            visited[nearest.getDestination()] = false;
            // Calculate the distance of next vertex
            double baseDistance = distances[nearest.getDestination()];
            for (SimpleWeightedEdge edge : g.getEdges(nearest.getDestination())) {
                double nextDistance = baseDistance + edge.getWeight();
                // Update distance value of all adjacent vertices of u
                if (nextDistance < distances[edge.getDestination()]) {
                    distances[edge.getDestination()] = nextDistance;
                    parent[edge.getDestination()] = nearest.getDestination();
                }
                candidate.add(new SimpleWeightedEdge(u, edge.getDestination(), nextDistance));
            }
        }
        Arrays.stream(parent).forEach(System.out::println);
        return distances;
    }
}
