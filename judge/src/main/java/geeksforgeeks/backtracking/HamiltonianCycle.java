package geeksforgeeks.backtracking;

import collections.graph.SimpleUndirectedGraph;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class HamiltonianCycle {
    private static boolean find(SimpleUndirectedGraph graph, int u, int v, List<Integer> path, Set<Integer> unvisited) {
        if (unvisited.contains(v)) {
            unvisited.remove(v);
            path.add(v);
            if (unvisited.isEmpty() && graph.getAdjacentVertices(v).some(w -> w == u)) {
                path.add(u);
                return true;
            } else {
                for (int adj : graph.getAdjacentVertices(v)) {
                    if (find(graph, u, adj, path, unvisited)) {
                        return true;
                    }
                }
            }
            path.remove(path.size() - 1);
            unvisited.add(v);
        }
        return false;
    }

    public static List<Integer> find(SimpleUndirectedGraph graph, int u) {
        List<Integer> path = new LinkedList<>();
        Set<Integer> noVisited = new HashSet<>();
        for (int i = 0; i < graph.size(); i++) noVisited.add(i);
        find(graph, u, u, path, noVisited);
        return path;
    }
}
