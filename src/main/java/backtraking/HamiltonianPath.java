package backtraking;

import greedy.graph.Graph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HamiltonianPath {
    private final Graph graph;
    private final List<Integer> paths = new ArrayList<>();
    private final Set<Integer> noVisited = new HashSet<>();

    public HamiltonianPath(Graph graph) {
        this.graph = graph;
    }

    private boolean find(int u, int v) {
        if (this.noVisited.contains(v)) {
            this.noVisited.remove(v);
            this.paths.add(v);
            if (noVisited.isEmpty()) {
                if (this.graph.getAdjVertices(v).contains(u)) {
                    return true;
                }
            } else {
                for (int adj : this.graph.getAdjVertices(v)) {
                    if (find(u, adj)) {
                        return true;
                    }
                }
            }
            this.paths.remove(this.paths.size() - 1);
            this.noVisited.add(v);
        }
        return false;
    }

    public List<Integer> find(int u) {
        for (int i = 0; i < this.graph.getNumberOfVertices(); i++) {
            noVisited.add(i);
        }
        this.paths.clear();
        this.find(u, u);
        return this.paths;
    }
}
