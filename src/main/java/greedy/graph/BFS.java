package greedy.graph;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BFS {
    private final Graph g;
    private final boolean visited[];
    private Tree<Integer> root;

    public BFS(Graph g) {
        this.g = g;
        this.visited = new boolean[this.g.getNumberOfVertices()];
    }

    public void breathFirstSearch(int v) {
        Queue<Tree<Integer>> parents = new LinkedList<>();
        Queue<Integer> q = new LinkedList<>();
        this.visited[v] = true;
        q.add(v);
        Tree<Integer> parent = new Tree<>(v);
        this.root = parent;
        parents.add(this.root);
        while (!q.isEmpty()) {
            v = q.remove();
            parent = parents.remove();
            for (int j : this.g.getAdjVertices(v)) {
                if (!this.visited[j]) {
                    this.visited[j] = true;
                    q.add(j);
                    Tree<Integer> child = new Tree<>(j);
                    parent.addChild(child);
                    parents.add(child);
                }
            }
        }
    }

    public void printBFSTraversal() {
        // Print contents of list
        System.out.println("");
        for (int vertex : this.getBFSTraversal()) {
            System.out.print(vertex + " ");
        }
        System.out.println();
    }

    public Tree<Integer> getBFSSpanningTree() {
        return this.root;
    }

    public List<Integer> getBFSTraversal() {
        return this.getBFSSpanningTree().levelOrderTraversal();
    }
}
