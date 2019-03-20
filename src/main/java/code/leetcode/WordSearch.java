package code.leetcode;


/**
 * https://leetcode.com/problems/word-search
 */
public class WordSearch {
    private static boolean exist(char[][] board, String word, int idx, int x, int y) {
        if (idx >= word.length()) return true;
        if (x < 0 || x >= board.length || y < 0 || y >= board[0].length) return false;
        if (board[x][y] != word.charAt(idx)) return false;
        // mark as visited
        board[x][y] ^= 256;
        if (exist(board, word, idx + 1, x - 1, y)
                || exist(board, word, idx + 1, x + 1, y)
                || exist(board, word, idx + 1, x, y - 1)
                || exist(board, word, idx + 1, x, y + 1)) {
            return true;
        }
        board[x][y] ^= 256;
        return false;
    }

    public boolean exist(char[][] board, String word) {
        int rows = board.length;
        int cols = board[0].length;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (exist(board, word, 0, i, j)) {
                    return true;
                }
            }
        }
        return false;
    }
}
