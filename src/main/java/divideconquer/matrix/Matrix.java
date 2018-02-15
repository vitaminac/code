package divideconquer.matrix;

import java.lang.reflect.Array;

import utils.NumberUtilities;

public class Matrix<E extends Number> {
	private E[][] aij;
	
	public Matrix(int n, int m) {
		this.aij = (E[][]) new Number[n][m];
	}

	public Matrix(int n) {
		this.aij = (E[][]) new Number[n][n];
	}

	public int size() {
		return aij.length;
	}

	public E get(int i, int j) {
		return aij[i][j];
	}

	public void set(E elem, int i, int j) {
		this.aij[i][j] = elem;
	}

	public Matrix<E> add(Matrix<E> B) {
		int n = this.size();
		Matrix<E> C = new Matrix<>(n);
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				E elem = (E) NumberUtilities.addNumbers(this.get(i, j), B.get(i, j));
				C.set(elem, i, j);
			}
		}
		return C;
	}

	public Matrix<E> subtract(Matrix<E> B) {
		int n = this.size();
		Matrix<E> C = new Matrix<>(n);
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				E elem = (E) NumberUtilities.subNumbers(this.get(i, j), B.get(i, j));
				C.set(elem, i, j);
			}
		}
		return C;
	}

	public Matrix<E> mult(Matrix<E> B) {
		int n = this.size();

		// initialise C
		Matrix<E> C = new Matrix<>(n);

		for (int i = 0; i < n; i++) {
			for (int k = 0; k < n; k++) {
				for (int j = 0; j < n; j++) {
					E elem = (E) NumberUtilities.multNumbers(this.get(i, j), B.get(i, j));
					C.set(elem, i, j);
				}
			}
		}
		return C;
	}
}
