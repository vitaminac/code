package greedy.graph;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

public class DFSTest {
    private DirectedGraph g3 = new DirectedGraph(4);

    @Before
    public void setUp() {
        g3.add(0, 1, 0);
        g3.add(0, 2, 0);
        g3.add(1, 2, 0);
        g3.add(2, 0, 0);
        g3.add(2, 3, 0);
        g3.add(3, 3, 0);
    }

    @Test
    public void depthFirstSearch() {
        DFS dfs = new DFS(g3);
        System.out.println("");
        System.out.println("Following is Depth First Traversal " + "(starting from vertex 2)");

        dfs.depthFirstSearch(2);
        dfs.printDFSTraversal();

        assertArrayEquals(new int[]{2, 0, 1, 3}, dfs.getDFSTraversal().stream().mapToInt(i -> i).toArray());
    }
}