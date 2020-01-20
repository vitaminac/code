package backtraking.color;

import code.adt.graph.SimpleUndirectedGraph;

import java.util.HashMap;

public class Color {
    private final SimpleUndirectedGraph graph;
    private final int nColor;
    private HashMap<Integer, Integer> coloring = new HashMap<>();

    public Color(SimpleUndirectedGraph graph, int nColor) {
        this.graph = graph;
        this.nColor = nColor;
        for (int i = 0; i < graph.size(); i++) {
            coloring.put(i, -1);
        }
    }

    private boolean isFeasible(int vertex) {
        return graph.getAdjacentVertices(vertex).all(v -> !coloring.get(v).equals(coloring.get(vertex)));
    }

    private boolean color(int vertex) {
        for (int i = 0; i < this.nColor; i++) {
            coloring.put(vertex, i);
            if (isFeasible(vertex)) {
                if (vertex == graph.size() - 1) {
                    return true;
                } else if (color(vertex + 1)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void color() {
        this.color(0);
    }

    @Override
    public String toString() {
        return "Color{" + "coloring=" + coloring + '}';
    }
}
