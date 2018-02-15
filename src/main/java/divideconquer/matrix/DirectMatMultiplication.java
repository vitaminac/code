package divideconquer.matrix;

import divideconquer.matrix.Matrix;
import divideconquer.matrix.MatrixMultiplication;

public class DirectMatMultiplication<E extends Number> extends MatrixMultiplication<E> {

	protected Matrix<E> multRec(Matrix<E> A, Matrix<E> B) {
		int n = A.size();

		if (n <= LEAF_SIZE) {
			return A.mult(B);
		} else {
			// initializing the new sub-matrices
			int newSize = n / 2;

			Matrix<E> a11 = new Matrix<>(newSize);
			Matrix<E> a12 = new Matrix<>(newSize);
			Matrix<E> a21 = new Matrix<>(newSize);
			Matrix<E> a22 = new Matrix<>(newSize);

			Matrix<E> b11 = new Matrix<>(newSize);
			Matrix<E> b12 = new Matrix<>(newSize);
			Matrix<E> b21 = new Matrix<>(newSize);
			Matrix<E> b22 = new Matrix<>(newSize);

			// dividing the matrices in 4 sub-matrices:
			squareDivision(A, a11, a12, a21, a22);
			squareDivision(B, b11, b12, b21, b22);

			// Divide and Conquer multiplication. Calculating c21, c21, c11,
			// c22:
			Matrix<E> c11 = multRec(a11, b11).add(multRec(a12, b21));
			Matrix<E> c12 = multRec(a11, b12).add(multRec(a12, b22));

			Matrix<E> c21 = multRec(a21, b11).add(multRec(a22, b21));
			Matrix<E> c22 = multRec(a21, b12).add(multRec(a22, b22));

			// Grouping the results obtained in a single matrix:
			Matrix<E> C = squareFusion(c11, c12, c21, c22);

			return C;
		}
	}
}
