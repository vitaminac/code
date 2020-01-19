package code.geekforgeek.backtracking;

import greedy.graph.Graph;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class HamiltonianCycle {
    private boolean find(Graph graph, int u, int v, List<Integer> path, Set<Integer> noVisited) {
        if (noVisited.contains(v)) {
            noVisited.remove(v);
            path.add(v);
            if (noVisited.isEmpty() && graph.getAdjVertices(v).contains(u)) {
                path.add(u);
                return true;
            } else {
                for (int adj : graph.getAdjVertices(v)) {
                    if (this.find(graph, u, adj, path, noVisited)) {
                        return true;
                    }
                }
            }
            path.remove(path.size() - 1);
            noVisited.add(v);
        }
        return false;
    }

    public List<Integer> find(Graph graph, int u) {
        List<Integer> path = new LinkedList<>();
        Set<Integer> noVisited = new HashSet<>();
        for (int i = 0; i < graph.getNumberOfVertices(); i++) noVisited.add(i);
        this.find(graph, u, u, path, noVisited);
        return path;
    }
}
