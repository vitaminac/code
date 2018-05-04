package greedy.graph;

import java.util.ArrayList;
import java.util.List;

public class DFS {
    private Graph g;
    private boolean visited[];
    private List<Integer> list = new ArrayList<>();

    public DFS(Graph g) {
        this.g = g;
        this.visited = new boolean[g.getNumberOfVertices()];
    }

    public void depthFirstSearch(int v) {
        if (!visited[v]) {
            recursiveDFS(v);
        }
    }

    private void recursiveDFS(int v) {
        this.visited[v] = true;
        this.list.add(v);
        for (int j : this.g.getAdjVertices(v)) {
            if (!this.visited[j]) {
                recursiveDFS(j);
            }
        }
    }

    public void printDFSTraversal() {
        // Print contents of list
        System.out.println("");
        for (int i : this.getDFSTraversal()) {
            System.out.print(i + " ");
        }
    }

    public List<Integer> getDFSTraversal() {
        return this.list;
    }
}
