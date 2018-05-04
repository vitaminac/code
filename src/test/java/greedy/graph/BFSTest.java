package greedy.graph;

import org.junit.Test;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;

public class BFSTest {

    @Test
    public void breathFirstSearch() {
        DirectedGraph g = new DirectedGraph(4);
        BFS bfs = new BFS(g);

        g.add(0, 1, 0);
        g.add(0, 2, 0);
        g.add(1, 2, 0);
        g.add(2, 0, 0);
        g.add(2, 3, 0);
        g.add(3, 3, 0);

        System.out.println("Following is Breadth First Traversal " + "(starting from vertex 2)");
        bfs.breathFirstSearch(2);
        bfs.printBFStraversal();
        assertEquals(IntStream.of(new int[]{2, 0, 3, 1}).boxed().collect(Collectors.toList()), bfs.getBFStraversal());
    }
}