package greedy.graph;

import org.junit.Before;
import org.junit.Test;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;

public class DFSTest {
    private DirectedGraph g3 = new DirectedGraph(4);
    ;

    @Before
    public void setUp() throws Exception {
        g3.addEdge(0, 1);
        g3.addEdge(0, 2);
        g3.addEdge(1, 2);
        g3.addEdge(2, 0);
        g3.addEdge(2, 3);
        g3.addEdge(3, 3);
    }

    @Test
    public void depthFirstSearch() {
        DFS dfs = new DFS(g3);
        System.out.println("");
        System.out.println("Following is Depth First Traversal " +
                "(starting from vertex 2)");

        dfs.depthFirstSearch(2);
        dfs.printDFStraversal();

        assertEquals(IntStream.of(new int[]{2, 0, 1, 3}).boxed().collect(Collectors.toList()), dfs.getDFStraversal());
    }
}