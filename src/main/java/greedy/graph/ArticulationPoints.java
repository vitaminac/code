package greedy.graph;

import java.util.HashSet;
import java.util.List;

public class ArticulationPoints {
    private final Tree<Integer> dfsTree;
    private final Graph graph;

    public ArticulationPoints(Graph g) {
        this.graph = g;
        final DFS dfs = new DFS(g);
        dfs.depthFirstSearch(0);
        this.dfsTree = dfs.getDFSSpanningTree();
    }

    private boolean hasBackEdge(Tree<Integer> tree, HashSet<Integer> parents) {
        return this.graph.isConnectedWith(tree.getElement(), parents) || tree.getChildren().stream()
                .anyMatch(child -> this.hasBackEdge(child, parents));
    }

    private void findArticulationPoint(Tree<Integer> noRootNode, boolean vertices[], HashSet<Integer> parents) {
        int parent = noRootNode.getElement();
        final List<Tree<Integer>> children = noRootNode.getChildren();
        vertices[parent] = !children.isEmpty() && !children.stream()
                .allMatch(child -> this.hasBackEdge(child, parents));
        parents.add(parent);
        for (Tree<Integer> child : children) {
            this.findArticulationPoint(child, vertices, parents);
        }
        parents.remove(parent);
    }

    public boolean[] findArticulationPoint() {
        boolean vertices[] = new boolean[this.graph.n];
        HashSet<Integer> parents = new HashSet<>();
        if (dfsTree.getChildren().size() > 1) {
            vertices[dfsTree.getElement()] = true;
        }
        parents.add(dfsTree.getElement());

        for (Tree<Integer> child : dfsTree.getChildren()) {
            findArticulationPoint(child, vertices, parents);
        }
        return vertices;
    }
}
