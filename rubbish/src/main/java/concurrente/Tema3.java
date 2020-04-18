package concurrente;

import core.Arrays;
import core.Utils;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;

public class Tema3 {
    /**
     * Implementa un programa secuencial que realice la multiplicación entre dos
     * matrices. A continuación, introduce todos los mecanismos de paralelización que
     * consideres oportunos, y verifica que la paralelización introducida acelera el
     * cómputo.
     *
     * @param mat1
     * @param mat2
     * @param l
     * @param m
     * @param n
     * @return
     * @throws InterruptedException
     */
    public static long[] mat_mul(long[] mat1, long[] mat2, int l, int m, int n) throws InterruptedException {
        long[] result = new long[l * n];
        Thread[] threads = new Thread[l * n];
        for (int i = 0; i < l; i++) {
            for (int j = 0; j < n; j++) {
                final int row = i;
                final int col = j;
                threads[row * n + col] = new Thread(() -> {
                    for (int k = 0; k < m; k++) {
                        result[row * n + col] += mat1[row * m + k] * mat2[k * n + col];
                    }
                });
                threads[row * n + col].start();
            }
        }
        for (Thread thread : threads) thread.join();
        return result;
    }

    private static volatile int count = 0;

    /**
     * Implementa un programa secuencial que cuente las vocales que aparecen en una
     * frase de longitud indefinida. A continuación, implementa una versión paralela
     * del mismo. En concreto, si dispones de N procesos, tendrás que dividir la frase
     * en palabras. Si la frase tiene M palabras, los primeros N-1 procesos analizarán
     * M/N palabras cada uno, y el último analizará M – (N-1)*M/N palabras (las
     * restantes
     *
     * @param str
     * @param N
     * @return
     * @throws InterruptedException
     */
    public static long count(String str, int N) throws InterruptedException {
        count = 0;
        Thread[] threads = new Thread[N];
        int increment = str.length() / N + 1;
        Semaphore sem = new Semaphore(1);
        for (int idx = 0; idx * increment < str.length(); idx++) {
            final int index = idx;
            final int start = idx * increment;
            final int end = Math.min(start + increment, str.length());
            threads[idx] = new Thread(() -> {
                for (int i = start; i < end; i++) {
                    switch (str.charAt(i)) {
                        case 'a':
                        case 'e':
                        case 'i':
                        case 'o':
                        case 'u':
                            sem.acquireUninterruptibly();
                            count += 1;
                            sem.release();
                        default:
                    }
                }
            });
            threads[idx].start();
        }
        for (Thread t : threads) t.join();
        return count;
    }

    /**
     * Implementa el algoritmo secuencial mergesort para ordenar un array de enteros.
     * A continuación, paraleliza el método de forma que cada división se pueda
     * ejecutar en un thread diferente. Para evitar la creación de un número de threads
     * muy elevado, el caso base se dará cuando se hayan creado NPROC threads,
     * siendo NPROC el número de procesadores disponibles. Una vez alcanzado ese
     * número, se llevará a cabo la ordenación secuencial del sub-array
     * correspondiente siguiendo el método que consideres oportuno
     *
     * @param arr
     * @param aux
     * @param low
     * @param high
     * @param proc
     */
    public static void merge_sort(Integer[] arr, Integer[] aux, int low, int high, Semaphore proc) {
        if (low >= high) return;
        if (proc.tryAcquire()) {
            int mid = low + (high - low) / 2;
            merge_sort(arr, aux, low, mid, proc);
            Thread thread = new Thread(() -> merge_sort(arr, aux, mid + 1, high, proc));
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            proc.release();
            Arrays.copyTo(arr, aux, low, high);
            Arrays.merge(arr, aux, low, mid, high, Integer::compareTo);
        } else {
            Arrays.merge_sort(arr, aux, low, high, Integer::compareTo);
        }
    }

    /**
     * Una conocida marca de grandes almacenes está comenzando con su campaña
     * de rebajas y ha habilitado una fila única para realizar los cobros. Modela el
     * sistema de asignación de clientes de forma que sea capaz de gestionar una lista
     * de tamaño indefinido de clientes, y que cada cajero almacene cuántos clientes
     * ha procesado. Al finalizar los clientes, se deberá imprimir por pantalla los clientes
     * procesados por cada cajero, para saber qué comisión deberá cobrar.
     *
     * @param n_clients
     * @param n
     * @return
     * @throws InterruptedException
     */
    public static int n_cajero(int n_clients, int n) throws InterruptedException {
        Queue<Integer> q = new ArrayDeque<>(1000);
        Semaphore sem = new Semaphore(1);
        for (int i = 0; i < n_clients; i++) q.add((int) (Math.random() * 49 + 1));
        Thread[] threads = new Thread[n];
        int[] count = new int[n];
        for (int i = 0; i < n; i++) {
            final int id = i;
            threads[i] = new Thread(() -> {
                try {
                    while (!q.isEmpty()) {
                        sem.acquire();
                        boolean flag = q.isEmpty();
                        int t = q.remove();
                        sem.release();
                        if (!flag) {
                            count[id] += 1;
                            Thread.sleep(t);
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            threads[i].start();
        }
        for (int i = 0; i < n; i++) threads[i].join();
        return java.util.Arrays.stream(count).sum();
    }

    private static final Semaphore ask_sem = new Semaphore(0);
    private static final Semaphore answered_sem = new Semaphore(0);

    private static class Player implements Runnable {
        private int score = 0;
        private volatile int answer;
        private final int N;

        public Player(int n) {
            N = n;
        }

        @Override
        public void run() {
            for (int i = 0; i < N; i++) {
                ask_sem.acquireUninterruptibly();
                this.answer = ThreadLocalRandom.current().nextInt(1, 5);
                answered_sem.release();
            }
        }
    }

    /**
     * Implementa un juego de preguntas y respuestas que siga la siguiente mecánica:
     * a. Se formulará una pregunta (que se cargará de un fichero de texto).
     * b. Cada pregunta tendrá un total de 4 posibles respuestas (que también se
     * cargarán del mismo fichero), que se mostrarán por pantalla.
     * c. A la pregunta responderán N jugadores de forma simultánea (para
     * simular el comportamiento del jugador, se generará un número aleatorio
     * entre 1 y 4 para elegir la respuesta correspondiente).
     * d. Cuando todos los jugadores hayan respondido, se mostrará por pantalla
     * la respuesta correcta y se sumará un punto a cada jugador que haya
     * acertado.
     * e. Al finalizar con todas las preguntas del fichero, la partida termina,
     * imprimiendo por pantalla la puntuación de cada jugador
     *
     * @param N
     * @throws InterruptedException
     */
    public static void ask_answer(int N) throws InterruptedException {
        final int nQuestions = 1000;
        Player[] players = new Player[N];
        for (int i = 0; i < N; i++) players[i] = new Player(nQuestions);
        Thread[] threads = new Thread[N];
        for (int i = 0; i < N; i++) threads[i] = new Thread(players[i]);
        for (int i = 0; i < N; i++) threads[i].start();
        for (int k = 0; k < nQuestions; k++) {
            int answer = ThreadLocalRandom.current().nextInt(1, 5);
            ask_sem.release(N);
            answered_sem.acquire(N);
            for (int i = 0; i < N; i++) {
                if (players[i].answer == answer) {
                    players[i].score += 1;
                }
            }
        }
        for (int i = 0; i < N; i++) threads[i].join();
        for (int i = 0; i < N; i++) {
            System.out.println("Player " + i + " has got " + players[i].score);
        }
    }

    /**
     * Implementa un gestor de descarga de ficheros asíncrona. El gestor recibirá por
     * parámetro el número de procesos (NPROC) que se encargaran de realizar la
     * descarga y el tamaño del fichero a descargar (SIZE). Los fragmentos para
     * descargar por cada proceso serán simulados mediante el método download que
     * dado una longitud devuelve un array que representa un fragmento del fichero.
     * Programación Concurrente
     * Grado en Ingeniería del Software
     * Una vez descargados todos los fragmentos, se implementará una función print
     * que imprime el contenido del fichero por pantalla.
     *
     * @param download_url
     * @param path
     * @param N
     * @throws IOException
     * @throws InterruptedException
     */
    public static void download(final String download_url, final String path, int N)
            throws IOException, InterruptedException {
        Utils.multithreading_download(download_url, path, N);
    }
}
