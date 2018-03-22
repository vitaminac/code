package greedy.graph;

import java.util.Collections;
import java.util.Stack;

public class TopologicalSorting {
    private final DirectedGraph g;
    private final boolean[] visited;
    private Stack<Integer> sorting = new Stack<>();

    public TopologicalSorting(DirectedGraph g) {
        this.g = g;
        this.visited = new boolean[g.getNumVertices()];
    }

    private void topologicalVisit(int v) {
        visited[v] = true;
        for (int j : this.g.getAdjacents(v)) {
            if (!visited[j]) {
                this.topologicalVisit(j);
            }
        }
        this.sorting.push(v);
    }

    public void sort() {
        for (int j = 0; j < g.getNumVertices(); j++) {
            if (!this.visited[j]) {
                this.topologicalVisit(j);
            }
        }
        Collections.reverse(this.sorting);
    }

    public Stack<Integer> getSorting() {
        return this.sorting;
    }
}
