package divideconquer.torneo;

public class Calendar {
    // matrix de n equipos por n - 1 semanas
    private final int[][] calendar;
    private int n;

    private Calendar(int n) {
        this.calendar = new int[n + 1][n];
        this.n = n;
    }

    public static void createCalendar(Calendar calendar, int n) {
        if (n == 2) {
            // caso base
            calendar.calendar[0][0] = 1;
            calendar.calendar[1][0] = 0;
        } else if (n % 2 == 0) {
            // si n es par
            // divide el problema y resuelve n / 2
            createCalendar(calendar, n / 2);
            int round;
            // determina cuantas semanas tienen los subproblemas
            if ((n / 2) % 2 == 0) {
                round = n / 2 - 1;
            } else {
                round = n / 2;
            }
            // relleno la parte inferior izquierda que es identico que la parte superior izquierda
            for (int i = 0; i < n / 2; i++) {
                for (int j = 0; j < round; j++) {
                    calendar.calendar[i + n / 2][j] = calendar.calendar[i][j] + n / 2;
                }
            }
            // si n/2 es impar rellena los huecos de descando que han generado en los subproblemas
            if ((n / 2) % 2 != 0) {
                // sustituye y llena cuadrantes
                for (int i = 0; i < n / 2; i++) {
                    for (int j = 0; j < n / 2; j++) {
                        if (calendar.calendar[i][j] >= n / 2) {
                            calendar.calendar[i][j] = i + n / 2;
                            calendar.calendar[i + n / 2][j] = i;
                        }
                    }
                }
            }
            // relleno parte derecha, que corresponde a la fusion de dos subproblemas
            for (int jugador = 0; jugador < n / 2; jugador++) {
                for (int dia = round; dia < n - 1; dia++) {
                    calendar.calendar[jugador][dia] = (n / 2) + (jugador + dia + 1) % (n / 2);
                    calendar.calendar[(n / 2) + (jugador + dia + 1) % (n / 2)][dia] = jugador;
                }
            }
        } else {
            // si es impar resuelve con n + 1
            createCalendar(calendar, n + 1);
        }
    }

    public static Calendar createCalendar(int n) {
        Calendar calendar = new Calendar(n);
        createCalendar(calendar, n);
        return calendar;
    }

    private static Calendar createCalendarBruteForce(Calendar calendar, int n) {
        // el numeros de rotaciones que hace falta
        int rounds = n - 1;
        // numero de parejas excluyendo n-1
        final int nPair = n / 2 - 1;
        for (int r = 0; r < rounds; r++) {
            // pj, para n=6
            // 5 0 1
            // 4 3 2
            // asignacion de partida 5 con el 4
            calendar.calendar[rounds][r] = Math.floorMod(-1 - r, rounds);
            calendar.calendar[Math.floorMod(-1 - r, rounds)][r] = rounds;
            for (int j = 0; j < nPair; j++) {
                // 0 - 3 y la siguiente iteracion 1 - 2
                calendar.calendar[Math.floorMod(j - r, rounds)][r] = Math.floorMod(-2 - j - r, rounds);
                calendar.calendar[Math.floorMod(-2 - j - r, rounds)][r] = Math.floorMod(j - r, rounds);
            }
            // rotacion en el sentodo del reloj una posicion, excluyendo el elemento n-1
            // j = j +1
            // ahora
            // 5 4 0
            // 3 2 1
        }
        return calendar;
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
            Calendar calendar = new Calendar(n);
            return createCalendarBruteForce(calendar, n);
        }
    }

    public void print() {
        for (int i = 0; i < this.n; i++) {
            int round;
            if (this.n % 2 == 0) {
                round = this.n - 1;
            } else {
                round = this.n;
            }
            for (int j = 0; j < round; j++) {
                if (this.calendar[i][j] < n) {
                    System.out.print(this.calendar[i][j] + 1);
                } else {
                    System.out.print("D");
                }
                System.out.print("\t");
            }
            System.out.println(System.lineSeparator());
        }
    }
}
