package backtraking.queen;

public class QueenFind {
    private final int n;

    private final int solution[];
    private final boolean cols[];
    private final boolean diagonal[];
    private final boolean diagonalInv[];
    private final int nDiagonal;
    private int i = 0;

    public QueenFind(int n) {
        this.n = n;
        this.solution = new int[n];
        this.cols = new boolean[n];
        this.nDiagonal = 2 * n - 1;
        this.diagonal = new boolean[this.nDiagonal];
        this.diagonalInv = new boolean[this.nDiagonal];
    }

    public boolean find() {
        for (int j = 0; j < this.n; j++) {
            if (this.isCandidate(j)) {
                this.addQueen(j);
                if (this.isSolution()) {
                    return true;
                } else {
                    ++this.i;
                    if (this.find()) {
                        return true;
                    } else {
                        // undo
                        --this.i;
                        this.removeQueen(j);
                    }
                }
            }
        }
        return false;
    }

    public void addQueen(int j) {
        //TODO: Poner la siguiente reina en la columna j (fila k)
        this.solution[i] = j;
        this.cols[j] = true;
        this.diagonal[this.getDiagonal(j)] = true;
        this.diagonalInv[this.getDiagonalInv(j)] = true;
    }

    public void removeQueen(int j) {
        //Quitar la ultima reina puesta
        this.cols[j] = false;
        this.diagonal[this.getDiagonal(j)] = false;
        this.diagonalInv[this.getDiagonalInv(j)] = false;
    }

    public int getDiagonal(int j) {
        return (j - i + this.nDiagonal) % this.nDiagonal;
    }

    public int getDiagonalInv(int j) {
        return i + j;
    }

    public boolean isSolution() {//es solucion factible
        //TODO:
        return i + 1 == n;
    }

    private boolean isCandidate(int j) {//EsPrometedor
        //TODO: Comprobar si se puede poner la siguiente reina en la columna j (fila k)
        return !this.cols[j] && !this.diagonal[this.getDiagonal(j)] && !this.diagonalInv[this.getDiagonalInv(j)];
    }

    public void print() {
        for (int k : this.solution) {
            System.out.println(k + 1);
        }
    }
}
