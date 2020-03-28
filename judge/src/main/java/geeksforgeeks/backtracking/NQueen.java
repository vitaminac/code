package geeksforgeeks.backtracking;

// https://www.geeksforgeeks.org/n-queen-problem-backtracking-3/
public class NQueen {
    private static boolean find(int n, int nDiagonal, int row, boolean[] cols, boolean[] diagonals, boolean[] diagonalsInv, int[] solution) {
        if (n == row) return true;
        for (int col = 0; col < n; col++) {
            int diagonal = (col - row + nDiagonal) % nDiagonal;
            int diagonalInv = row + col;
            // Comprobar si se puede poner la siguiente reina en la columna j (fila k)
            // if feasible
            if (!cols[col] && !diagonals[diagonal] && !diagonalsInv[diagonalInv]) {
                cols[col] = true;
                diagonals[diagonal] = true;
                diagonalsInv[diagonalInv] = true;
                solution[row] = col;
                if (find(n, nDiagonal, row + 1, cols, diagonals, diagonalsInv, solution)) {
                    return true;
                } else {
                    cols[col] = false;
                    diagonals[diagonal] = false;
                    diagonalsInv[diagonalInv] = false;
                }
            }
        }
        return false;
    }

    public static int[] find(int n) {
        int[] solution = new int[n];
        boolean[] cols = new boolean[n];
        int nDiagonal = 2 * n - 1;
        boolean[] diagonals = new boolean[nDiagonal];
        boolean[] diagonalsInv = new boolean[nDiagonal];
        find(n, nDiagonal, 0, cols, diagonals, diagonalsInv, solution);
        return solution;
    }
}
