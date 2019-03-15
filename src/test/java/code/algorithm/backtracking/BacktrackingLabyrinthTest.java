package code.algorithm.backtracking;

import code.StdIn;

public class BacktrackingLabyrinthTest {
    public static void main(String[] args) {
        final Backtracking<Labyrinth> backtracking = new Backtracking<>();
        System.out.println(backtracking.solve(new Labyrinth(StdIn.readIntMatrix())));
    }
}