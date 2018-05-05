package backtraking.labyrinth;

import org.junit.Test;

public class LabyrinthTest {
    @Test
    public void findRoute() {
        final Labyrinth labyrinth = new Labyrinth();
        labyrinth.findRoute();
        System.out.println(labyrinth);
    }
}