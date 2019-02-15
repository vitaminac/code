package oop.coordinates;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Matrix {
    private final double[][] elements;

    public Matrix(double[][] elements) {
        if (elements == null) {
            elements = new double[0][0];
        }
        this.elements = elements;
    }

    public int getCols() {
        if (this.elements.length > 0) {
            return this.elements[0].length;
        } else {
            return 0;
        }
    }

    public double[][] getElements() {
        return this.elements;
    }

    public int getRows() {
        return this.elements.length;
    }

    public Matrix transpose() {
        double[][] matrix = new double[this.getCols()][this.getRows()];
        for (int i = 0; i < this.getRows(); i++) {
            for (int j = 0; j < this.getCols(); j++) {
                matrix[j][i] = this.getElements()[i][j];
            }
        }
        return new Matrix(matrix);
    }

    public Matrix multiply(Matrix other) {
        if (this.getCols() != other.getRows())
            throw new IllegalArgumentException("you are trying to multiply (" + this.getRows() + "," + this.getCols() + ") with " + "(" + this.getRows() + "," + this.getCols() + ")");
        double[][] matrix = new double[this.getRows()][other.getCols()];
        double rowPerCol = 0;
        for (int i = 0; i < this.getRows(); i++) {
            for (int k = 0; k < other.getCols(); k++) {
                for (int j = 0; j < this.getCols(); j++) {
                    rowPerCol += this.getElements()[i][j] * other.getElements()[j][k];
                }
                matrix[i][k] = rowPerCol;
                rowPerCol = 0;
            }
        }
        return new Matrix(matrix);
    }

    public double get(int i, int j) {
        return this.elements[i][j];
    }

    public void set(int i, int j, double e) {
        this.elements[i][j] = e;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Matrix matrix = (Matrix) o;
        return Arrays.deepEquals(getElements(), matrix.getElements());
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(getElements());
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append('\n');
        sb.append(Arrays.stream(this.elements)
                .map(Arrays::toString)
                .collect(Collectors.joining(",\n")));
        sb.append('\n');
        return sb.toString();
    }
}
