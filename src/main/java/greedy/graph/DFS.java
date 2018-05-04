package greedy.graph;

import java.util.List;

public class DFS {
    private Graph g;
    private boolean visited[];
    private Tree<Integer> root;

    public DFS(Graph g) {
        this.g = g;
        this.visited = new boolean[g.getNumberOfVertices()];
    }

    public void depthFirstSearch(int v) {
        for (int i = 0; i < g.getNumberOfVertices(); i++) {
            this.visited[i] = false;
        }
        this.root = new Tree<Integer>(v);
        recursiveDFS(v, this.root);
    }

    private void recursiveDFS(int v, Tree<Integer> root) {
        this.visited[v] = true;
        for (int j : this.g.getAdjVertices(v)) {
            if (!this.visited[j]) {
                final Tree<Integer> child = new Tree<>(j);
                recursiveDFS(j, child);
                root.addChild(child);
            }
        }
    }

    public void printDFSTraversal() {
        // Print contents of list
        System.out.println("");
        for (int i : this.getDFSTraversal()) {
            System.out.print(i + " ");
        }
        System.out.println();
    }

    public Tree<Integer> getDFSSpanningTree() {
        return this.root;
    }

    public List<Integer> getDFSTraversal() {
        return this.getDFSSpanningTree().preOrderTraversal();
    }

}
