package greedy.graph;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

public class ArticulationPointsTest {

    @Test
    public void findArticulationPoint() {
        final Graph graph = new Graph(5);
        graph.add(1, 2, 0);
        graph.add(1, 0, 0);
        graph.add(2, 0, 0);
        graph.add(0, 3, 0);
        graph.add(3, 4, 0);
        final ArticulationPoints articulationPoints = new ArticulationPoints(graph);
        assertArrayEquals(new boolean[]{true, false, false, true, false}, articulationPoints.findArticulationPoint());
    }
}