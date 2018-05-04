package greedy.graph;

import java.util.HashSet;

public class ArticulationPoints {
    private final Tree<Integer> dfsTree;
    private final Graph graph;

    public ArticulationPoints(Graph g) {
        this.graph = g;
        final DFS dfs = new DFS(g);
        dfs.depthFirstSearch(0);
        this.dfsTree = dfs.getDFSSpanningTree();
    }

    private void findArticulationPoint(Tree<Integer> noRootNode, boolean vertices[], HashSet<Integer> parents) {
        int parent = noRootNode.getElement();
        vertices[parent] = noRootNode.getChildren().stream()
                .anyMatch(child -> this.graph.getAdjVertices(child.getElement()).stream()
                        .allMatch((e) -> !parents.contains(e)));
        parents.add(parent);
        for (Tree<Integer> child : noRootNode.getChildren()) {
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
