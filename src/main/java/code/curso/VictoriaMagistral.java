package code.curso;

import java.util.Scanner;

/**
 * Todos sabemos que en fortnite te pueden derribar en cuesti´on de mil´esimas. Tu amigo, Robius,
 * necesita saber cu´antos disparos tiene que dar a su rival para derrotarlo, es decir sabiendo la
 * vida que tiene el rival y el da˜no que hace un disparo cuanto es necesario para vencerle. Pero
 * como todos sabemos existen diferentes armas y algunos rivales pueden tener m´as o menos vida
 * que otro, por lo que para ´el es un lio y nos ha pedido que le ayudemos realizando un programa.
 *
 * Entrada
 * Un n´umero N con el n´umero de casos, N casos que constituyen dos n´umeros V (vida que tiene
 * el rival) y D (da˜no que hace la bala).
 *
 * Salida
 * N´umero de disparos que tiene que realizar Robius para vencer a su rival. Todos los n´umeros
 * son divisibles.
 */
public class VictoriaMagistral {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        for (int i = 0; i < n; i++) {
            int dividend = scanner.nextInt();
            int divisor = scanner.nextInt();
            System.out.println(dividend / divisor);
        }
    }
}
