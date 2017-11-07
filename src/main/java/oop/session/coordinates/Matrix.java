package oop.session.coordinates;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Matrix {
    private final double[][] matrix;
    private final int rows;
    private final int cols;

    public Matrix(double[][] matrix) {
        matrix = checkNull(matrix);
        this.rows = matrix.length;
        this.cols = matrix[0].length;
        this.matrix = matrix.clone();
    }

    public double[][] checkNull(double[][] matrix) {
        if (matrix == null) {
            matrix = new double[0][0];
        }
        return matrix;
    }

    public int getCols() {
        return this.cols;
    }

    public double[][] getMatrix() {
        return this.matrix;
    }

    public int getRows() {
        return this.rows;
    }

    public void print() {
        String content = "[\n" +
                         Arrays.stream(this.matrix)
                               .map(row -> '[' +
                                           Arrays.stream(row)
                                                 .mapToObj(element -> String.valueOf(element))
                                                 .collect(Collectors.joining(", ")) +
                                           ']')
                               .collect(Collectors.joining(",\n")) +
                         "\n]";
        System.out.println(content);
    }

    public Matrix transpose() {
        double[][] matrix = new double[this.getCols()][this.getRows()];
        for (int i = 0; i < this.getRows(); i++) {
            for (int j = 0; j < this.getCols(); j++) {
                matrix[j][i] = this.getMatrix()[i][j];
            }
        }
        return new Matrix(matrix);
    }

    public Matrix multiply(Matrix other) throws DimensionNotCoincide {
        this.checkMulplicable(other);
        double[][] matrix = new double[this.getRows()][other.getCols()];
        double rowPerCol = 0;
        for (int i = 0; i < this.getRows(); i++) {
            for (int k = 0; k < other.getCols(); k++) {
                for (int j = 0; j < this.getCols(); j++) {
                    rowPerCol += this.getMatrix()[i][j] * other.getMatrix()[j][k];
                }
                matrix[i][k] = rowPerCol;
                rowPerCol = 0;
            }
        }
        return new Matrix(matrix);
    }

    public boolean checkMulplicable(Matrix other) throws DimensionNotCoincide {
        if (this.getCols() != other.getRows()) {
            throw new DimensionNotCoincide(this.getCols(), this.getRows());
        }
        return true;
    }

    public void printSymmetric() throws DimensionNotCoincide {
        System.out.println("A*A^T");
        this.multiply(this.transpose())
            .print();
        System.out.println("A^T*A");
        this.transpose()
            .multiply(this)
            .print();
    }
}
