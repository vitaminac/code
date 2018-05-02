package divideconquer;

public class Calendar {
    // matrix de n equipos por n - 1 semanas
    private final int[][] calendar;
    private final int n;
    private final int round;

    private Calendar(int n) {
        this.n = n;
        // determina cuantas semanas necesitan los subproblemas
        if (n % 2 == 0) {
            this.round = n - 1;
        } else {
            this.round = n;
        }
        this.calendar = new int[this.n][this.round];
    }

    public void print() {
        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.round; j++) {
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

    private static Calendar createCalendar(int n) {
        final Calendar calendar = new Calendar(n);
        if (n == 2) {
            // caso base
            calendar.calendar[0][0] = 1;
            calendar.calendar[1][0] = 0;
        } else if (n % 2 == 0) {
            // si n es par
            // divide el problema y resuelve n / 2
            final Calendar subCalendar = createCalendar(n / 2);
            // relleno la parte superior izquierda que es identica
            // y la parte inferior izquierda sumando el desplazamiento de filas
            // en vez de resolver dos veces la misma subproblema
            for (int i = 0; i < subCalendar.n; i++) {
                for (int j = 0; j < subCalendar.round; j++) {
                    calendar.calendar[i][j] = subCalendar.calendar[i][j];
                    calendar.calendar[i + subCalendar.n][j] = subCalendar.calendar[i][j] + subCalendar.n;
                }
            }
            // si el subproblema generado es impar
            // rellena los huecos de descando que han generado en los subproblemas
            if (subCalendar.n % 2 != 0) {
                // sustituye y llena cuadrantes
                for (int i = 0; i < subCalendar.n; i++) {
                    for (int j = 0; j < subCalendar.n; j++) {
                        if (calendar.calendar[i][j] >= subCalendar.n) {
                            calendar.calendar[i][j] = i + subCalendar.n;
                            calendar.calendar[i + subCalendar.n][j] = i;
                        }
                    }
                }
            }
            // relleno parte derecha, que corresponde a la fase de fusion
            // asigna cada jugador de parte superior a cada jugador de parte inferior
            for (int jugador = 0; jugador < subCalendar.n; jugador++) {
                for (int dia = subCalendar.round; dia < calendar.round; dia++) {
                    calendar.calendar[jugador][dia] = subCalendar.n + (jugador + dia + 1) % subCalendar.n;
                    calendar.calendar[subCalendar.n + (jugador + dia + 1) % subCalendar.n][dia] = jugador;
                }
            }
        } else {
            // si es impar resuelve con n + 1
            final Calendar calendarPlus1 = createCalendar(n + 1);
            for (int i = 0; i < calendar.n; i++) {
                for (int j = 0; j < calendar.round; j++) {
                    calendar.calendar[i][j] = calendarPlus1.calendar[i][j];
                }
            }
        }
        return calendar;
    }

    public static void main(String[] args) {
        createCalendar(4).print();
        createCalendar(8).print();
        createCalendar(7).print();
        createCalendar(3).print();
        createCalendar(6).print();
        createCalendar(5).print();
        createCalendar(12).print();
    }
}
