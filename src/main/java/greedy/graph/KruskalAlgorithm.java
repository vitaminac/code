package greedy.graph;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class KruskalAlgorithm {
    private final TreeSet<Edge> candidate = new TreeSet<>(new Comparator<Edge>() {
        @Override
        public int compare(Edge o1, Edge o2) {
            return o1.getWeight() - o2.getWeight() > 0 ? 1 : -1;
        }
    });
    private final Set<Edge> sol = new HashSet<>();
    private final Graph graph;
    private final HashSet<HashSet<Integer>> subGraphs = new HashSet<>();

    private Edge select() {
        return this.candidate.pollFirst();
    }

    private HashSet<Integer> getSubGraph(Integer vertex) {
        for (HashSet<Integer> subGraph : this.subGraphs) {
            if (subGraph.contains(vertex)) {
                return subGraph;
            }
        }
        return null;
    }

    private void fusionSubGraphs(Edge edge) {
        HashSet<Integer> subGraph1 = this.getSubGraph(edge.getSourcevertex());
        HashSet<Integer> subGraph2 = this.getSubGraph(edge.getDestinationvertex());
        if (subGraph1 == null && subGraph2 == null) {
            final HashSet<Integer> subGraph = new HashSet<>();
            subGraph.add(edge.getSourcevertex());
            subGraph.add(edge.getDestinationvertex());
            this.subGraphs.add(subGraph);
        } else if (subGraph1 == null) {
            subGraph2.add(edge.getSourcevertex());
        } else if (subGraph2 == null) {
            subGraph1.add(edge.getDestinationvertex());
        } else {
            subGraph1.addAll(subGraph2);
            this.subGraphs.remove(subGraph2);
        }

    }

    private boolean isFeasible(Edge edge) {
        HashSet<Integer> subGraph1 = this.getSubGraph(edge.getSourcevertex());
        HashSet<Integer> subGraph2 = this.getSubGraph(edge.getDestinationvertex());
        return subGraph1 != subGraph2 || subGraph1 == null;
    }

    public KruskalAlgorithm(Graph g) {
        this.candidate.addAll(g.getEdges());
        this.graph = g;
    }

    public Set<Edge> kruskalAlgorithm() {
        while (this.sol.size() < this.graph.getNumberOfVertices() - 1 && !this.candidate.isEmpty()) {
            final Edge cand = this.select();
            if (this.isFeasible(cand)) {
                this.fusionSubGraphs(cand);
                this.sol.add(cand);
            }
        }
        return this.sol;
    }
}

