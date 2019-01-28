package branchbound.assignation;

import java.util.Arrays;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

public class Assign {
    private final double[][] cost;
    private final double[] minCost;

    public Assign(double[][] cost) {
        this.cost = cost;
        this.minCost = Arrays.stream(cost).mapToDouble(task -> Arrays.stream(task).min().getAsDouble()).toArray();
    }

    public int[] minimize() {
        Node upperBound = new Node();
        PriorityQueue<Node> q = new PriorityQueue<>();
        q.add(upperBound);
        while (!q.isEmpty()) {
            Node sol = q.remove();
            if (sol.isSolution()) {
                if (sol.compareTo(upperBound) < 0) {
                    upperBound = sol;
                }
            } else {
                if (sol.compareTo(upperBound) < 0) {
                    q.addAll(sol.getChildren());
                }
            }
        }
        return upperBound.toArray();
    }

    private class Node implements Comparable<Node> {
        private final Node parent;
        private final int job;
        private final int agent;
        private final double cost;
        private final HashSet<Integer> candidates = new HashSet<>();

        public Node() {
            this.agent = -1;
            this.job = -1;
            this.parent = null;
            this.cost = 0;
            for (int i = 0; i < Assign.this.minCost.length; i++) {
                this.candidates.add(i);
            }
        }

        public Node(Node parent, int job, int agent) {
            this.parent = parent;
            this.job = job;
            this.agent = agent;
            this.cost = parent.cost + Assign.this.cost[job][agent];
            this.candidates.addAll(parent.candidates);
            this.candidates.remove(job);
        }

        public Set<Node> getChildren() {
            Set<Node> children = new HashSet<>();
            for (int job : this.candidates) {
                final Node child = new Node(this, job, this.agent + 1);
                if (child.isFeasible()) {
                    children.add(child);
                }
            }
            return children;
        }

        public boolean isFeasible() {
            return true;
        }

        public boolean isSolution() {
            return this.agent == Assign.this.minCost.length - 1;
        }

        @Override
        public int compareTo(Node o) {
            return this.lowBound() - o.lowBound() > 0 ? 1 : -1;
        }

        private double lowBound() {
            if (this.agent < 0) {
                return Double.POSITIVE_INFINITY;
            } else if (this.isSolution()) {
                return this.cost;
            } else {
                double cost = this.cost;
                for (int job : this.candidates) {
                    cost += Assign.this.minCost[job];
                }
                return cost;
            }
        }

        public int[] toArray() {
            int[] arr = new int[Assign.this.minCost.length];
            Node node = this;
            for (int i = agent; i >= 0; i--) {
                arr[i] = node.job;
                node = node.parent;
            }
            return arr;
        }
    }
}
