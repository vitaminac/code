package code.curso;

import java.util.HashMap;
import java.util.Scanner;

/**
 * Eres el protagonista de un videojuego de magia, y est´as entrando en la mazmorra final. En esta
 * mazmorra, sabes que hay muchas salas con distintos enemigos, as´ı que te has preparado bien
 * para la aventura. A lo largo del videojuego, has ido recolectando distintas pociones m´agicas
 * para utilizar contra los enemigos. Sabes que los dragones requieren cinco pociones para derrotar, los duendes solo una, para matar un glifo con dos bastan, y que hay algunos jefes finales
 * contra los que necesitas usar 10 pociones para vencerlos. Adem´as, cada monstruo puede ser de
 * un tipo, por lo que necesitar´as utilizar pociones del mismo tipo para vencerlos. Sabiendo las
 * pociones que llevas encima, ¿saldr´as vivo de la mazmorra y vencer´as a todos los enemigos o te
 * quedar´as sin pociones y perder´as la partida?
 *
 * Cada caso de prueba comenzar´a con un n´umero P, el n´umero de pociones que llevas. A
 * continuaci´on, aparecer´an P l´ıneas con las pociones que llevas en la aventura, descritas por
 * su tipo. Posteriormente aparecer´a un n´umero M, seguido de M lineas con los monstruos que
 * vencer, descritos por una string con el nombre del monstruo y otra string con su tipo, separadas
 * por un espacio. Se deben seguir procesando casos hasta que llegue uno conteniendo 0 pociones.
 * Los monstruos pueden ser ”dragon”, ”duende”, ”glifo” y ”jefe”. Los tipos son string que solo
 * contienen las letras a-z en may´uscula y min´uscula, sin tildes y sin ˜n
 *
 * Output
 * El programa responder´a ”Victoria” si podemos salir vivos de la mazmorra. En caso contrario,
 * imprimir´a ”Derrota ante ” seguido del monstruo que nos haya matado
 */
public class Magia {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        HashMap<String, Integer> map = new HashMap<>();
        int P;
        while ((P = scanner.nextInt()) != 0) {
            map.clear();
            for (int i = 0; i < P; i++) {
                String type = scanner.next();
                if (map.containsKey(type)) {
                    map.put(type, map.get(type) + 1);
                } else {
                    map.put(type, 1);
                }
            }

            int M = scanner.nextInt();
            boolean victory = true;
            String type = "";
            String name = "";
            for (int i = 0; i < M; i++) {
                if (victory) {
                    name = scanner.next();
                    type = scanner.next();
                    if (name.equals("dragon")) {
                        if (map.containsKey(type) && map.get(type) >= 5) {
                            map.put(type, map.get(type) - 5);
                        } else {
                            victory = false;
                        }
                    } else if (name.equals("duende")) {
                        if (map.containsKey(type) && map.get(type) >= 1) {
                            map.put(type, map.get(type) - 1);
                        } else {
                            victory = false;
                        }
                    } else if (name.equals("glifo")) {
                        if (map.containsKey(type) && map.get(type) >= 2) {
                            map.put(type, map.get(type) - 2);
                        } else {
                            victory = false;
                        }
                    } else if (name.equals("jefe")) {
                        if (map.containsKey(type) && map.get(type) >= 10) {
                            map.put(type, map.get(type) - 10);
                        } else {
                            victory = false;
                        }
                    } else {
                        throw new RuntimeException();
                    }
                } else {
                    scanner.next();
                    scanner.next();
                }
            }
            if (victory) {
                System.out.println("Victoria");
            } else {
                System.out.println("Derrota ante " + name + " " + type);
            }
        }
    }
}
