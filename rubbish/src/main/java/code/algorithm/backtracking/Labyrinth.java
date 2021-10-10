package code.algorithm.backtracking;

import code.algorithm.common.SolutionNode;
import core.linkedlist.SinglyLinkedListDoubleReference;
import core.queue.Queue;

public class Labyrinth implements SolutionNode<Labyrinth> {
    private final int[][] labyrinth;
    private int step;

    private int x;
    private int y;

    public Labyrinth(int[][] labyrinth, int step, int x, int y) {
        this.labyrinth = labyrinth;
        this.step = step;
        this.x = x;
        this.y = y;
    }

    public Labyrinth(int[][] labyrinth) {
        this(labyrinth, 1, 0, 0);
    }

    @Override
    public boolean isSolution() {
        return this.x == 9 && this.y == 9;
    }

    @Override
    public boolean isFeasible() {
        return true;
    }

    @Override
    public Queue<Labyrinth> branch() {
        this.labyrinth[this.x][this.y] = this.step;
        Queue<Labyrinth> queue = Queue.fromSteque(SinglyLinkedListDoubleReference::new);
        if (x - 1 >= 0 && this.labyrinth[x - 1][this.y] == 0) {
            queue.enqueue(new Labyrinth(this.labyrinth, step + 1, x - 1, y));
        }
        if (x + 1 < 10 && this.labyrinth[x + 1][this.y] == 0) {
            queue.enqueue(new Labyrinth(this.labyrinth, step + 1, x + 1, y));
        }
        if (y - 1 >= 0 && this.labyrinth[this.x][y - 1] == 0) {
            queue.enqueue(new Labyrinth(this.labyrinth, step + 1, x, y - 1));
        }
        if (y + 1 >= 0 && this.labyrinth[this.x][y + 1] == 0) {
            queue.enqueue(new Labyrinth(this.labyrinth, step + 1, x, y + 1));
        }
        return queue;
    }

    @Override
    public void backtrack() {
        if (this.labyrinth[this.x][this.y] > 0) {
            this.labyrinth[this.x][this.y] = -2;
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        for (int[] row : this.labyrinth) {
            for (int cell : row) {
                switch (cell) {
                    case 0:
                        sb.append("\t \t");
                        break;
                    case -1:
                        sb.append("\tX\t");
                        break;
                    default:
                        sb.append('\t');
                        sb.append(cell);
                        sb.append('\t');
                        break;
                }
            }
            sb.append('\n');
        }
        return sb.toString();
    }
}
