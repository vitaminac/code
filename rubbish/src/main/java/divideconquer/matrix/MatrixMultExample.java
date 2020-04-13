package divideconquer.matrix;

public class MatrixMultExample {

	public static void main(String[] args) {

		final int n = 16;
		Matrix<Integer> matrix1 = new Matrix<Integer>(n);
		Matrix<Integer> matrix2 = new Matrix<Integer>(n);

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				matrix1.set((int) (Math.random() * 10), i, j);
				matrix2.set((int) (Math.random() * 10), i, j);
			}
		}

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				System.out.printf("%-5d", matrix1.get(i, j));
			}
			System.out.println();
		}

		System.out.println();
		System.out.println();

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				System.out.printf("%-5d", matrix2.get(i, j));
			}
			System.out.println();
		}

		System.out.println();
		System.out.println();
		
		MatrixMultiplication<Integer> mult1 = new DirectMatMultiplication<>();
		Matrix<Integer> C1 = mult1.multDaC(matrix1, matrix2);
		System.out.println("Multiplication with a Divde and Conquer strategy");
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				System.out.printf("%-5d", C1.get(i, j));
			}
			System.out.println();
		}

		System.out.println();
		System.out.println();

		MatrixMultiplication<Integer> mult2 = new StrassenMatMultiplication<>();
		Matrix<Integer> C2 = mult2.multDaC(matrix1, matrix2);
		System.out.println("Multiplication with a Strassen strategy");
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				System.out.printf("%-5d", C2.get(i, j));
			}
			System.out.println();
		}
	}
}
