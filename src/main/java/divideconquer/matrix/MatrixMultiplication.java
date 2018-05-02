package divideconquer.matrix;

public abstract class MatrixMultiplication<E extends Number> {
	protected int LEAF_SIZE = 64;

	private static int nextPowerOfTwo(int n) {
		int log2 = (int) Math.ceil(Math.log(n) / Math.log(2));
		return (int) Math.pow(2, log2);
	}

	public Matrix<E> multDaC(Matrix<E> A, Matrix<E> B) {
		
		int n = A.size();
		int m = nextPowerOfTwo(n);
		Matrix<E> C;
		if (m == n) {
			C = multRec(A, B);
		} 
		else {
			// Make the matrices bigger so that you can apply the DaC
			// algorithm recursively without having to deal with odd
			// matrix sizes
			Matrix<E> APrep = new Matrix<>(m);
			Matrix<E> BPrep = new Matrix<>(m);
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					APrep.set(A.get(i, j), i, j);
					BPrep.set(B.get(i, j), i, j);
				}
			}
			Matrix<E> CPrep = multRec(APrep, BPrep);

			C = new Matrix<>(n);
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					C.set(CPrep.get(i, j), i, j);
				}
			}
		}
		return C;
	}

	protected void squareDivision(Matrix<E> a, Matrix<E> a11, Matrix<E> a12,
			Matrix<E> a21, Matrix<E> a22) {
		int newSize = a.size() / 2;
		// dividing the matrices in 4 sub-matrices:
		for (int i = 0; i < newSize; i++) {
			for (int j = 0; j < newSize; j++) {
				a11.set(a.get(i, j), i, j); // top left
				a12.set(a.get(i, j + newSize), i, j);// top right
				a21.set(a.get(i + newSize, j), i, j);// bottom left
				a22.set(a.get(i + newSize, j + newSize), i, j); // bottom right
			}
		}
	}

	protected Matrix<E> squareFusion(Matrix<E> c11, Matrix<E> c12, Matrix<E> c21,
			Matrix<E> c22) {
		int n = c11.size();

		// Grouping the results obtained in a single matrix:
		Matrix<E> matrix = new Matrix<>(n * 2);
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				matrix.set(c11.get(i, j), i, j);
				matrix.set(c12.get(i, j), i, j + n);
				matrix.set(c21.get(i, j), i + n, j);
				matrix.set(c22.get(i, j), i + n, j + n);
			}
		}
		return matrix;
	}
	
	abstract protected Matrix<E> multRec(Matrix<E> A, Matrix<E> B);


}