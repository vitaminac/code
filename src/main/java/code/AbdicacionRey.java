package code;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

// https://www.aceptaelreto.com/problem/statement.php?id=214

/**
 * Abdicación de un Rey
 * <p>
 * Cuando un rey abdica,
 * su primogénito hereda el trono y debe recibir,
 * en su coronación, un número que lo identificará para la posteridad.
 * La numeración es importante porque, de otro modo,
 * sería difícil diferenciar a reyes
 * con el mismo nombre de una misma dinastía al compartir también apellido.
 * <p>
 * El resultado es que ante la abdicación de un rey,
 * toca revisar los libros de historia para averiguar su número.
 * ¿Eres capaz de hacerlo tú?
 * <p>
 * El programa recibirá, por la entrada estándar, múltiples casos de prueba.
 * Cada uno consta de una primera línea con un número indicando la cantidad de reyes de una determinada dinastía.
 * A continuación vendrá, en otra línea, los nombres de todos sus reyes separados por espacio.
 * <p>
 * Después aparecerán dos líneas más, una con la cantidad de sucesores futuros que hay que numerar (al menos uno),
 * y otra con sus nombres separados por espacio.
 * <p>
 * Todos los nombres estarán compuestos de una única palabra de no más de 20 letras del alfabeto inglés,
 * y serán sensibles a mayúsculas.
 * Además, se garantiza que en cada caso de prueba no habrá más de 20 nombres de reyes diferentes.
 * <p>
 * El último caso de prueba, que no deberá procesarse, no contendrá ningún rey en la dinastía.
 * <p>
 * Para cada sucesor de cada caso de prueba se indicará, en una línea independiente,
 * el número que le corresponderá.
 * Aunque normalmente se utilizan números romanos,
 * por simplicidad se indicará el número en la notación arábiga tradicional.
 * Después de cada caso de prueba se escribirá una línea en blanco.
 */
class AbdicacionRey {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        int numKing;
        HashMap<String, Integer> map = new HashMap<>();
        while (scanner.hasNextInt() && (numKing = scanner.nextInt()) != 0) {
            map.clear();

            for (int i = 0; i < numKing; i++) {
                String name = scanner.next();
                if (map.containsKey(name)) {
                    map.put(name, map.get(name) + 1);
                } else {
                    map.put(name, 1);
                }
            }

            for (int j = scanner.nextInt(); j > 0; j--) {
                String candidate = scanner.next();
                if (map.containsKey(candidate)) {
                    map.put(candidate, map.get(candidate) + 1);
                } else {
                    map.put(candidate, 1);
                }
                System.out.println(map.get(candidate));
            }
            System.out.println();
        }
    }
}
