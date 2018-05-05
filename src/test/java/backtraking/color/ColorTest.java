package backtraking.color;

import greedy.graph.Graph;
import org.junit.Test;

public class ColorTest {

    @Test
    public void color() {
        final Graph graph = new Graph(4);
        graph.add(0, 1);
        graph.add(0, 2);
        graph.add(0, 3);
        graph.add(2, 3);
        final Color color = new Color(graph, 3);
        color.color();
        System.out.println(color);
    }
}