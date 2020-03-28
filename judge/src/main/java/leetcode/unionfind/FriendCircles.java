package leetcode.unionfind;

/* https://leetcode.com/problems/friend-circles/ */
public class FriendCircles {
    private static class FriendCycle {
        private int[] parent;
        private int size;

        public FriendCycle(int n) {
            this.size = n;
            this.parent = new int[n];
            for (int i = 0; i < n; i++) {
                this.parent[i] = i;
            }
        }

        public void union(int i, int j) {
            i = this.find(i);
            j = this.find(j);
            if (i != j) {
                this.parent[i] = j;
                this.size--;
            }
        }

        public int find(int i) {
            while (this.parent[i] != i) i = this.parent[this.parent[i]]; // path compression by halving
            return i;
        }
    }

    public int findCircleNum(int[][] M) {
        FriendCycle cycle = new FriendCycle(M.length);
        for (int i = 0; i < M.length; i++) {
            for (int j = 0; j < M.length; j++) {
                if (M[i][j] != 0) {
                    cycle.union(i, j);
                }
            }
        }
        return cycle.size;
    }
}
