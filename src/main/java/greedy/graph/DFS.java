package greedy.graph;

import java.util.ArrayList;
import java.util.List;

public class DFS {
    private DirectedGraph g;
    private boolean visited[];
    private List<Integer> list = new ArrayList<>();

    public DFS(DirectedGraph g) {
        this.g = g;
        this.visited = new boolean[g.getNumVertices()];
    }

    public void depthFirstSearch(int v) {
        if (!visited[v]) {
            recursiveDFS(v);
        }
    }

    private void recursiveDFS(int v) {
        this.visited[v] = true;
        this.list.add(v);
        for (int j : this.g.getAdjacents(v)) {
            if (!this.visited[j]) {
                recursiveDFS(j);
            }
        }
    }

    public void printDFStraversal() {
        // Print contents of list
        System.out.println("");
        for (int i : this.getDFStraversal()) {
            System.out.print(i + " ");
        }
    }

    public List<Integer> getDFStraversal() {
        return this.list;
    }
}
