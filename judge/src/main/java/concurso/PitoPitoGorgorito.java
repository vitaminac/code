package concurso;

import java.util.Scanner;

/**
 * ”Pito pito gorgorito donde vas tu tan bonito a la era verdadera pin pan pun fuera t´u te vas y
 * t´u te quedas”
 * ”En la casa de pinocho todos cuentan hasta 8. 1 2 3 4 5 6 7 8.”
 * ”Calcet´ın suerte para mi si no fuera para mi ser´a para ti”
 * Todos de peque˜no alguna vez hemos utilizado esta forma para determinar quien ”se la hinca” a
 * jugar a cualquier juego (escondite, pilla pilla, etc). Dada la cantidad de silabas, calcular dado
 * una posici´on si nos salvamos de hincarla o no.
 * Por ejemplo si somos 10 personas y la frase tiene 5 silabas, el 5 la hinca. Si somos 4 personas
 * y la frase tiene 5 silabas la hinca el n´umero 1.
 *
 * Entrada
 * Un n´umero T con el n´umero de casos, luego, la descripci´on de T casos de prueba.
 * Cada caso contiene tres n´umeros: N (n´umero de silabas de la canci´on) y M (n´umero del
 * grupo de amigos) K (n´umero de posiciones que nos preguntamos si nos la hincar´ıamos o no).
 * Posteriormente K lineas definiendo cada posici´on por la que se pregunta.
 *
 * Salida
 * Para cada consulta Ki
 * , ”SI” si nos la hincamos en dicha posici´on y ”NO” si nos salvamos
 */
public class PitoPitoGorgorito {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        int T = scanner.nextInt();
        for (int i = 0; i < T; i++) {
            int N = scanner.nextInt();
            int M = scanner.nextInt();
            int K = scanner.nextInt();
            int answer;
            if (N < M) {
                answer = N;
            } else if (N % M == 0) {
                answer = M;
            } else {
                answer = N % M;
            }
            for (int j = 0; j < K; j++) {
                if (scanner.nextInt() == answer) {
                    System.out.println("SI");
                } else {
                    System.out.println("NO");
                }
            }
        }
    }
}
