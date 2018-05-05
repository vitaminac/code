package backtraking.labyrinth;

public class Labyrinth {
    private int[][] labyrinth = new int[][]{
            {0, 0, -1, 0, 0, 0, 0, -1, 0, 0},
            {-1, 0, -1, 0, 0, -1, -1, 0, -1, 0},
            {0, 0, 0, 0, 0, 0, -1, 0, -1, 0},
            {0, -1, 0, 0, -1, -1, -1, 0, 0, 0},
            {0, 0, -1, -1, 0, 0, 0, -1, 0, 0},
            {0, 0, 0, 0, 0, -1, 0, -1, 0, 0},
            {-1, 0, 0, -1, -1, 0, 0, -1, 0, -1},
            {0, -1, -1, 0, 0, 0, 0, 0, -1, -1},
            {-1, 0, 0, 0, 0, -1, 0, -1, -1, 0},
            {0, 0, -1, 0, -1, -1, 0, 0, 0, 0}
    };
    private int step = 1;

    private boolean findRoute(int x, int y) {
        if (x < 0 || x >= 10 || y < 0 || y >= 10) {
            return false;
        }
        if (!(this.labyrinth[x][y] == 0)) {
            return false;
        }
        labyrinth[x][y] = this.step++;
        if (x == 9 && y == 9) {
            return true;
        }
        if (findRoute(x, y + 1) || findRoute(x + 1, y) || findRoute(x - 1, y) || findRoute(x, y - 1)) {
            return true;
        }
        labyrinth[x][y] = 0;
        --this.step;
        return false;
    }

    public void findRoute() {
        this.findRoute(0, 0);
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
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }
}
