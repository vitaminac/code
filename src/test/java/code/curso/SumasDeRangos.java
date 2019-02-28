package code.curso;

import java.util.Scanner;

/**
 * Juanito es un estudiante muy flojo, puede ser muy inteligente, pero es incre´ıblemente flojo.
 * Su maestro, David, est´a un poco preocupado por esta situaci´on y se ha propuesto a hacerlo
 * estudiar, le pregunta a Juanito cu´al es la sumatoria de los n´umeros que existen desde 1 hasta
 * 5 y Juanito r´apidamente responde 15. Impresionado, David le pregunta cu´al es la sumatoria
 * de los n´umeros desde 2 hasta 8 y Juanito, una vez m´as, responde correctamente 35. David le
 * pregunta a Juanito como lo hace y ´este le contesta que se puede obtener la sumatoria de un
 * rango de n´umeros desde i hasta N mediante la siguiente f´ormula:
 * k
 * X=n
 * k=i
 * i ≡
 * n(n + 1)
 * 2
 * −
 * i(i − 1)
 * 2
 * (1)
 * David est´a cansado de que Juanito tenga una respuesta siempre para no hacer ejercicios
 * mec´anicos y tediosos, as´ı que le ha enviado a Juanito una lista de n´umeros muy grande y
 * le ha pedido que sume los intervalos de n´umeros desde un i hasta un j. A Juanito esto le
 * parece una injusticia debido a que su docente le est´a enviando ejercicios mec´anicos solo por
 * envi´arselos, as´ı que te ha pedido ayuda a ti, ¿Puedes ayudar a Juanito a resolver los tediosos
 * ejercicios de David?
 *
 * Entrada
 * La primera l´ınea contiene un n´umero T que denotar´an los casos de prueba
 * Por cada caso de prueba, se tendr´a un n´umero N en la primera l´ınea y despu´es, en la siguiente
 * l´ınea, N n´umeros separados por espacios. Despu´es, tendr´a un n´umero Q de queries y, luego, Q
 * l´ıneas con dos n´umeros Qi y Qj que tendr´an los ´ındices en la lista que se tendr´an que sumar,
 * los ´ındices son inclusivos, por lo que si es 1,1 solo se debe tener en consideraci´on el n´umero de
 * la lista cuyo ´ındice sea 1
 *
 * Salida
 * Para cada consulta (query) se debe imprimir un n´umero denotando la suma de todos los elementos de la lista desde Qi hasta Qj (inclusive), entre cada caso se debe colocar el string “—”
 * para diferenciar.
 */
public class SumasDeRangos {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int T = scanner.nextInt();

        for (int i = 0; i < T; i++) {
            if (i > 0) {
                System.out.println("---");
            }

            int N = scanner.nextInt();
            int[] number = new int[N];
            for (int j = 0; j < N; j++) {
                number[j] = scanner.nextInt();
            }
            int Q = scanner.nextInt();
            for (int j = 0; j < Q; j++) {
                int left = scanner.nextInt();
                int right = scanner.nextInt();
                int sum = 0;
                for (int k = left - 1; k < right; k++) {
                    sum += number[k];
                }
                System.out.println(sum);
            }
        }
    }
}
