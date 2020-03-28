package leetcode.bfs;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

/* https://leetcode.com/problems/sliding-puzzle/ */
public class SlidingPuzzle {
    public int slidingPuzzle(int[][] board) {
        String target = "123450";
        HashSet<String> visited = new HashSet<>();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                sb.append(board[i][j]);
            }
        }
        int[][] dirs = new int[][]{{1, 3}, {0, 2, 4}, {1, 5}, {0, 4}, {1, 3, 5}, {2, 4}};
        Queue<String> queue = new LinkedList<>();
        queue.add(sb.toString());
        int response = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                String current = queue.remove();
                if (current.equals(target)) return response;

                int zero = current.indexOf('0');
                for (int dir : dirs[zero]) {
                    sb = new StringBuilder(current);
                    sb.setCharAt(zero, current.charAt(dir));
                    sb.setCharAt(dir, '0');
                    String next = sb.toString();
                    if (visited.contains(next)) continue;
                    visited.add(next);
                    queue.add(next);
                }
            }
            ++response;
        }
        return -1;
    }
}
