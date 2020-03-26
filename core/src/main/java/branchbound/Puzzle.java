package branchbound;

import java.util.Arrays;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

public class Puzzle {
    public static void main(String[] args) {
        Puzzle puzzle = new Puzzle(new int[]{1, 2, 3, 5, 6, 0, 7, 8, 4});
        System.out.println(puzzle.solve());
        puzzle = new Puzzle(new int[]{7, 4, 6, 1, 2, 8, 0, 5, 3});
        System.out.println(puzzle.solve());
    }

    private final int table[];

    public Puzzle(int[] table) {
        this.table = table;
    }

    public Node solve() {
        HashSet<Node> exploredNode = new HashSet<>();
        PriorityQueue<Node> q = new PriorityQueue<>();
        for (int i = 0; i < 9; i++) {
            if (this.table[i] == 0) {
                q.add(new Node(null, this.table, 0, i / 3, i % 3));
            }
        }
        int itr = 0;
        while (!q.isEmpty()) {
            final Node sol = q.remove();
            if (sol.isSolution()) {
                System.out.println(itr);
                return sol;
            } else {
                for (Node child : sol.getChildren()) {
                    if (!exploredNode.contains(child)) {
                        exploredNode.add(child);
                        q.add(child);
                    }
                }
            }
            itr++;
        }
        return null;
    }

    private class Node implements Comparable<Node> {
        private final Node parent;
        private final int cost;
        private final int x;
        private final int y;
        private final int table[];
        private final int step;

        public Node(Node parent, int table[], int step, int x, int y) {
            this.parent = parent;
            this.table = table;
            this.step = step;
            this.cost = step + this.calCost();
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node node = (Node) o;
            return Arrays.equals(table, node.table);
        }

        @Override
        public int hashCode() {
            return Arrays.hashCode(table);
        }

        public boolean isSolution() {
            return this.calCost() <= 1;
        }

        public int calCost() {
            int cost = 0;
            for (int i = 0; i < 9; i++) {
                if (this.table[i] != i + 1) {
                    ++cost;
                }
            }
            return cost;
        }

        public Set<Node> getChildren() {
            Set<Node> children = new HashSet<>();
            if (this.x - 1 >= 0) {
                int table[] = new int[9];
                System.arraycopy(this.table, 0, table, 0, 9);
                int newX = this.x - 1;
                table[newX * 3 + this.y] = 0;
                table[this.x * 3 + this.y] = this.table[newX * 3 + this.y];
                children.add(new Node(this, table, step + 1, newX, this.y));
            }
            if (this.x + 1 < 3) {
                int table[] = new int[9];
                System.arraycopy(this.table, 0, table, 0, 9);
                int newX = this.x + 1;
                table[newX * 3 + this.y] = 0;
                table[this.x * 3 + this.y] = this.table[newX * 3 + this.y];
                children.add(new Node(this, table, step + 1, newX, this.y));
            }
            if (this.y - 1 >= 0) {
                int table[] = new int[9];
                System.arraycopy(this.table, 0, table, 0, 9);
                int newY = this.y - 1;
                table[this.x * 3 + newY] = 0;
                table[this.x * 3 + this.y] = this.table[this.x * 3 + newY];
                children.add(new Node(this, table, step + 1, this.x, newY));
            }
            if (this.y + 1 < 3) {
                int table[] = new int[9];
                System.arraycopy(this.table, 0, table, 0, 9);
                int newY = this.y + 1;
                table[this.x * 3 + newY] = 0;
                table[this.x * 3 + this.y] = this.table[this.x * 3 + newY];
                children.add(new Node(this, table, step + 1, this.x, newY));
            }
            return children;
        }

        @Override
        public int compareTo(Node o) {
            return this.cost - o.cost;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("cost:" + this.cost + " step:" + this.step + " x:" + this.x + " y:" + this.y);
            sb.append(System.lineSeparator());
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    sb.append(this.table[i * 3 + j]);
                    sb.append(" ");
                }
                sb.append(System.lineSeparator());
            }
            sb.append(System.lineSeparator());
            sb.append(this.parent);
            return sb.toString();
        }
    }
}
