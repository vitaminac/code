package collections.graph;

public class SimpleUndirectedGraph extends AbstractUndirectedGraph<SimpleEdge> {
    public SimpleUndirectedGraph(int n) {
        super(n);
    }

    public void addEdge(int u, int v) {
        this.addEdge(new SimpleEdge(u, v));
    }
}
