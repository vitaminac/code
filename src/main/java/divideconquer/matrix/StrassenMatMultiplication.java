package divideconquer.matrix;

public class StrassenMatMultiplication<E extends Number> extends MatrixMultiplication<E>{

	protected Matrix<E> multRec(Matrix<E> A, Matrix<E> B) {
		int n = A.size();

		if (n <= this.LEAF_SIZE) {
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

			// Strassen factor computations
			Matrix<E> aResult = new Matrix<>(newSize);
			Matrix<E> bResult = new Matrix<>(newSize);

			// Calculating p1 to p7:
			aResult = a11.add(a22);
			bResult = b11.add(b22);
			Matrix<E> p1 = multRec(aResult, bResult);
			// p1 = (a11+a22) * (b11+b22)

			aResult = a21.add(a22); // a21 + a22
			Matrix<E> p2 = multRec(aResult, b11); // p2 = (a21+a22) *
													// (b11)

			bResult = b12.subtract(b22); // b12 - b22
			Matrix<E> p3 = multRec(a11, bResult);
			// p3 = (a11) * (b12 - b22)

			bResult = b21.subtract(b11); // b21 - b11
			Matrix<E> p4 = multRec(a22, bResult);
			// p4 = (a22) * (b21 - b11)

			aResult = a11.add(a12); // a11 + a12
			Matrix<E> p5 = multRec(aResult, b22);
			// p5 = (a11+a12) * (b22)

			aResult = a21.subtract(a11); // a21 - a11
			bResult = b11.add(b12); // b11 + b12
			Matrix<E> p6 = multRec(aResult, bResult);
			// p6 = (a21-a11) * (b11+b12)

			aResult = a12.subtract(a22); // a12 - a22
			bResult = b21.add(b22); // b21 + b22
			Matrix<E> p7 = multRec(aResult, bResult);
			// p7 = (a12-a22) * (b21+b22)

			// calculating c21, c21, c11 e c22:
			Matrix<E> c12 = p3.add(p5); // c12 = p3 + p5
			Matrix<E> c21 = p2.add(p4); // c21 = p2 + p4

			aResult = p1.add(p4); // p1 + p4
			bResult = aResult.add(p7); // p1 + p4 + p7
			Matrix<E> c11 = bResult.subtract(p5);
			// c11 = p1 + p4 - p5 + p7

			aResult = p1.add(p3); // p1 + p3
			bResult = aResult.add(p6); // p1 + p3 + p6
			Matrix<E> c22 = bResult.subtract(p2);
			// c22 = p1 + p3 - p2 + p6

			// Grouping the results obtained in a single matrix:
			Matrix<E> C = squareFusion(c11, c12, c21, c22);

			return C;
		}
	}
}
