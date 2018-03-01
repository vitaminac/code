package greedy.graph;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Graph {
    private int n;
    private List<Edge> edges;

    public Graph(int n, int m) {
        this.n = n;
        this.edges = new LinkedList<Edge>();
        randomGraph(n, m);
    }

    public void randomGraph(int V, int E) {
        Random rand = new Random();
        if (E > (long) V * (V - 1) / 2) {
            throw new IllegalArgumentException("Too many edges");
        }
        if (E < 0) {
            throw new IllegalArgumentException("Too few edges");
        }
        while (this.edges.size() < E) {
            int origin = rand.nextInt(V);
            int dest = rand.nextInt(V);
            double weight = (rand.nextDouble()) * 100;
            Edge e = new Edge(origin, dest, weight);
            //No es lo más eficinte si se mete el grafo como lista de aristas
            if ((origin != dest) && !this.edges.contains(e)) {
                this.edges.add(e);
            }
        }
    }

    public void add(Edge e) {
        this.edges.add(e);
    }

    public int getNumberOfVertices() {
        return this.n;
    }

    public List<Edge> getEdges() {
        return this.edges;
    }
}
