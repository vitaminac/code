import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 * Un juego com´un entre los ni˜nos de antes, cuando las consolas no eran port´atiles y los m´oviles
 * s´olo serv´ıan para llamar, era el de palabras encadenadas. Este juego consiste en que dos jugadores van diciendo una palabra alternativamente, con la condici´on de que la primera s´ılaba
 * de la palabra del segundo jugador sea igual a la ´ultima s´ılaba de la palabra del primero.
 * Nuestro trabajo consistir´a en, dada una lista de N palabras, encontrar cu´antas parejas de palabras encadenadas hay en total. Para simplificar un poco nuestro trabajo, lo que haremos ser´a
 * decir que dos palabras forman una pareja de palabras encadenadas si las dos ´ultimas letras de
 * una se corresponden con las dos primeras de otra en la lista. Ten en cuenta que una palabra
 * puede formar pareja con varias palabras de la lista.
 */
class Encadenadas {
    public static void main(String[] args) throws Exception {
        HashMap<String, Integer> beginning = new HashMap<>();
        List<String> words = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            Scanner scanner = new Scanner(br);
            while (scanner.hasNext()) {
                words.clear();
                beginning.clear();
                int num = scanner.nextInt();
                scanner.nextLine();
                for (int i = 0; i < num; i++) {
                    String word = scanner.nextLine();
                    words.add(word);
                    String start = word.substring(0, 2);
                    if (beginning.containsKey(start)) {
                        beginning.put(start, beginning.get(start) + 1);
                    } else {
                        beginning.put(start, 1);
                    }
                }

                int count = 0;
                for (String word : words) {
                    String end = word.substring(word.length() - 2);
                    count += word.substring(0, 2).equals(end) ? (beginning.get(end) == null ? 0 : beginning.get(end)) - 1 : (beginning.get(end) == null ? 0 : beginning.get(end));
                }
                System.out.println(count);
            }
        }
    }
}
