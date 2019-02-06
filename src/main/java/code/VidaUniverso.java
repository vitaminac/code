package code;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * Tu programa debe usar la fuerza bruta para encontrar la respuesta de la vida, el universo y
 * todo lo demás de forma que, todos aquellos números que no contengan la respuesta se deben
 * reescribir por pantalla, la respuesta a la vida, el universo y todo lo dem´as es 42.
 */
class VidaUniverso {
    public static void main(String[] args) throws Exception {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            Scanner in = new Scanner(br);
            while (in.hasNext()) {
                int num = in.nextInt();
                if (num != 42) {
                    System.out.println(num);
                }
            }
        }
    }
}
