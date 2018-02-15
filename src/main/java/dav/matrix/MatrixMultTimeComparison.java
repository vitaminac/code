package dav.matrix;

public class MatrixMultTimeComparison {

	public static void main(String[] args) {
		long time_start, time_end;

		final int n = 512;
		Matrix<Integer> matrix1 = new Matrix<Integer>(n);
		Matrix<Integer> matrix2 = new Matrix<Integer>(n);
		int [][] matrix3 = new int [n][n];
		int [][] matrix4 = new int [n][n];


		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				matrix1.set((int) (Math.random() * 10), i, j);
				matrix3[i][j]  = matrix1.get(i, j);
				matrix2.set((int) (Math.random() * 10), i, j);
				matrix4[i][j]  = matrix2.get(i, j);
			}
		}


		MatrixMultiplication<Integer> mult1 = new StrassenMatMultiplication<>();
		
		time_start = System.currentTimeMillis();
		Matrix<Integer> C1 = mult1.multDaC(matrix1, matrix2);
		time_end = System.currentTimeMillis();
		System.out.println("Multiplication with the Strassen strategy has taken " + (time_end - time_start)+ " milliseconds");

		
		MatrixMultiplication<Integer> mult2 = new DirectMatMultiplication<>();
		
		time_start = System.currentTimeMillis();
		Matrix<Integer> C2 = mult2.multDaC(matrix1, matrix2);
		time_end = System.currentTimeMillis();
		System.out.println("Multiplication with the Direct strategy has taken " + (time_end - time_start)+ " milliseconds");
		
		
		MatrixMultiplicationInt mult3 = new MatrixMultiplicationInt();
		
		time_start = System.currentTimeMillis();
		int[][] C3 = mult3.strassen(matrix3, matrix4);
		time_end = System.currentTimeMillis();
		System.out.println("Multiplication with static methods and int data has taken " + (time_end - time_start)+ " milliseconds");
		
		
		MatrixMultiplicationInt mult4 = new MatrixMultiplicationInt(n);
		
		time_start = System.currentTimeMillis();
		int[][] C4 = mult4.strassen(matrix3, matrix4);
		time_end = System.currentTimeMillis();
		System.out.println("Multiplication with iterative algorithm " + (time_end - time_start)+ " milliseconds");
		
		
		// check solution
		for (int i = 0; i < n; i++){
			for (int j = 0; j < n; j++){		
				boolean comp1 = C1.get(i, j).equals(C2.get(i, j));
				boolean comp2 = C3[i][j] == C4[i][j];
				boolean comp3 = ((int)C1.get(i, j)) == C3[i][j];
				if(comp1 & comp2 & comp3){
					continue;
				}
				else{
					System.out.println("error in the multiplication" );
					break;
				}				
			}
		}		
	}
}
