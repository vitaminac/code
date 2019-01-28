import java.util.Arrays;

import static utils.NumberUtilities.addNumbers;
import static utils.NumberUtilities.multNumbers;

public class Matrix<E extends Number> {
    private E[][] elements;

    public Matrix(E[][] elements) {
        this.elements = elements;
    }

    private Matrix(int n) {
        this.elements = (E[][]) new Number[n][n];
    }

    public static void createRandomMatrix(int n) {
        Double[][] elements = (Double[][]) new Number[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                elements[i][j] = Math.random() * 10;
            }
        }
    }

    public int size() {
        return this.elements.length;
    }

//    public Matrix<E> multiply(Matrix<E> matrix) {
//        Matrix<E> C = new Matrix<>(this.size());
//        int n = this.size();
//        for (int i = 0; i < n; i++) {
//            for (int j = 0; j < n; j++) {
//                for (int k = 0; k < n; k++) {
//                    E e;
//                    multNumbers(A[i][k], B[k][j]);
//                    C.setElement(i, j,C.getElement(i,j) ) +=  * ;
//                }
//            }
//        }
//    }

    public E getElement(int i, int j) {
        return this.elements[i][j];
    }

    public void setElement(int i, int j, E e) {
        this.elements[i][j] = e;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(elements);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Matrix<?>))
            return false;
        Matrix<?> matrix = (Matrix<?>) o;
        return Arrays.equals(elements, matrix.elements);
    }
}
