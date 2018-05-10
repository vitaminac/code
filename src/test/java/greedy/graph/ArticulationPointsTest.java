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

    @Test
    public void findArticulationPointTestCase() {
        final Graph graph = new Graph(7);
        graph.add(0, 1);
        graph.add(0, 2);
        graph.add(1, 2);
        graph.add(1, 3);
        graph.add(1, 4);
        graph.add(1, 6);
        graph.add(3, 5);
        graph.add(4, 5);
        final ArticulationPoints articulationPoints = new ArticulationPoints(graph);
        assertArrayEquals(new boolean[]{false, true, false, false, false, false, false}, articulationPoints
                .findArticulationPoint());
    }
}