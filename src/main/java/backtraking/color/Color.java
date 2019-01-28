package backtraking.color;

import greedy.graph.Graph;

import java.util.HashMap;

public class Color {
    private final Graph graph;
    private final int nColor;
    private HashMap<Integer, Integer> coloring = new HashMap<>();

    public Color(Graph graph, int nColor) {
        this.graph = graph;
        this.nColor = nColor;
        for (int i = 0; i < graph.getNumberOfVertices(); i++) {
            coloring.put(i, -1);
        }
    }

    private boolean isFeasible(int vertex) {
        return graph.getAdjVertices(vertex).stream().noneMatch(v -> coloring.get(v).equals(coloring.get(vertex)));
    }

    private boolean color(int vertex) {
        for (int i = 0; i < this.nColor; i++) {
            coloring.put(vertex, i);
            if (isFeasible(vertex)) {
                if (vertex == graph.getNumberOfVertices() - 1) {
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
