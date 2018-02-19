package dav.torneo;

public class Calendar {
    private final int[][] calendar;
    private int n;

    private Calendar(int[][] calendarMatrix, int n) {
        this.calendar = calendarMatrix;
        this.n = n;
    }

    // pares = n-1
    // impares = n
    public static Calendar createCalendarBruteForce(int n) {
        if (n % 2 != 0) {
            Calendar result = createCalendarBruteForce(n + 1);
            result.n = n;
            return result;
        } else {
            int rounds = n - 1;
            int[][] calendar = new int[n][rounds];
            final int nPair = n / 2 - 1;
            for (int r = 0; r < rounds; r++) {
                calendar[rounds][r] = Math.floorMod(-1 - r, rounds);
                calendar[Math.floorMod(-1 - r, rounds)][r] = rounds;
                for (int j = 0; j < nPair; j++) {
                    calendar[Math.floorMod(j - r, rounds)][r] = Math.floorMod(-2 - j - r, rounds);
                    calendar[Math.floorMod(-2 - j - r, rounds)][r] = Math.floorMod(j - r, rounds);
                }
                // rotacion en el sentodo del reloj una posicion
            }
            return new Calendar(calendar, n);
        }
    }

    public void print() {
        for (int i = 0; i < this.n; i++) {
            int arr[] = this.calendar[i];
            for (int e : arr) {
                if (e < n) {
                    System.out.print(e + 1);
                } else {
                    System.out.print("D");
                }
                System.out.print(" ");
            }
            System.out.println(System.lineSeparator());
        }
    }
}
