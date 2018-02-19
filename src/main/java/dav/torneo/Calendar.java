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
        // pj. n = 5
        if (n % 2 != 0) {
            // si el numero de competidores es impar,
            // entonces resuelve nuevo problema con n = n+1,
            // y luego elimina la ultima fila
            // y las partidas contra el equipo numero n+1 convierte en descanso
            Calendar result = createCalendarBruteForce(n + 1);
            // pj. resuelve el problema de 5+1, 6
            // no elimina la fila explicitamente,
            // solamente que no la muestra
            result.n = n;
            return result;
        } else {
            // el numeros de rotaciones que hace falta
            int rounds = n - 1;
            // matrix de n equipos por n - 1 semanas
            int[][] calendar = new int[n][rounds];
            // numero de parejas excluyendo n-1
            final int nPair = n / 2 - 1;
            for (int r = 0; r < rounds; r++) {
                // pj, para n=6
                // 5 0 1
                // 4 3 2
                // asignacion de partida 5 con el 4
                calendar[rounds][r] = Math.floorMod(-1 - r, rounds);
                calendar[Math.floorMod(-1 - r, rounds)][r] = rounds;
                for (int j = 0; j < nPair; j++) {
                    // 0 - 3 y la siguiente iteracion 1 - 2
                    calendar[Math.floorMod(j - r, rounds)][r] = Math.floorMod(-2 - j - r, rounds);
                    calendar[Math.floorMod(-2 - j - r, rounds)][r] = Math.floorMod(j - r, rounds);
                }
                // rotacion en el sentodo del reloj una posicion, excluyendo el elemento n-1
                // j = j +1
                // ahora
                // 5 4 0
                // 3 2 1
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
