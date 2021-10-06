package concurrente;

import core.Arrays;
import core.Utils;
import core.concurrent.SemaphoreArrayBlockingQueue;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

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
    public static void download(final String download_url, final String path)
            throws IOException {
        Utils.fetch(download_url, path);
    }

    /**
     * Implementa una herramienta para manipulación de imágenes capaz de cambiar
     * a escala de grises una imagen dada. La herramienta definirá la variable NPROC y
     * NxM que se corresponde con la dimensión de la imagen. La estructura de datos
     * que se utilizará para representar la imagen es una matriz de objetos de orden
     * NxM. El objeto que se almacenará en cada celda de la matriz será un objeto Pixel
     * que tendrá tres atributos (R, G, B) simulando el color de cada pixel de la imagen.
     * Una vez generada la matriz de manera aleatoria, se dividirá por conjuntos de filas
     * la matriz y serán asignados a cada proceso que llevará a cabo el cambio de escala
     * de color.
     *
     * @param path
     * @param N
     * @throws IOException
     */
    public static void to_grey(String path, int N) throws IOException, InterruptedException {
        File file = new File(path);
        BufferedImage img = ImageIO.read(file);
        int width = img.getWidth();
        int height = img.getHeight();
        int chunk = height / N + 1;
        Thread[] threads = new Thread[N];
        for (int i = 0; i < N; i++) {
            final int start = Math.max(0, Math.min(chunk * i, height - 1));
            final int end = Math.min(chunk * (i + 1), height);
            threads[i] = new Thread(() -> {
                for (int y = start; y < end; y++) {
                    for (int x = 0; x < width; x++) {
                        int p = img.getRGB(x, y);
                        int a = (p >> 24) & 0xff;
                        int r = (p >> 16) & 0xff;
                        int g = (p >> 8) & 0xff;
                        int b = (p >> 0) & 0xff;
                        int avg = (r + g + b) / 3;
                        img.setRGB(x, y, (a << 24) | (avg << 16) | (avg << 8) | avg);
                    }
                }
            });
        }
        for (int i = 0; i < N; i++) threads[i].start();
        for (int i = 0; i < N; i++) threads[i].join();
        ImageIO.write(img, "png", file);
    }

    /**
     * Simular la gestión de cuenta bancaria donde existen dos tipos de procesos, uno
     * encargado de extraer 200 euros y otro que ingresa 200 euros. La cuenta bancaria
     * nunca podrá exceder de 1000 euros ni tampoco puede encontrarse en números
     * rojos. El número de cada tipo de proceso será configurado mediante dos
     * parámetros del algoritmo.
     *
     * @param deposit
     * @param withdraw
     * @throws InterruptedException
     */
    public static void ges_account(final int deposit, final int withdraw) throws InterruptedException {
        Semaphore funds = new Semaphore(0);
        Semaphore exceed = new Semaphore(1000);
        Thread deposit_thread = new Thread(() -> {
            for (int i = 0; i < deposit; i++) {
                funds.release(200);
                exceed.acquireUninterruptibly(200);
            }
        });
        Thread withdraw_thread = new Thread(() -> {
            for (int i = 0; i < withdraw; i++) {
                funds.acquireUninterruptibly(200);
                exceed.release(200);
            }
        });
        deposit_thread.start();
        withdraw_thread.start();
        deposit_thread.join();
        withdraw_thread.join();
    }

    /**
     * Simula el funcionamiento de una peluquería que tiene una silla utilizada por el
     * peluquero para cortar el pelo y N sillas en la que esperan los clientes su turno. El
     * funcionamiento será el siguiente: si no hay clientes, el peluquero se sienta en la
     * silla a la espera que llegue el siguiente cliente. Cuando llega un cliente, si el
     * peluquero está activo, es decir, se encuentra cortando el cabello a un cliente,
     * tendrá que esperarse en las N sillas siempre y cuando una se encuentren vacías.
     *
     * @param N
     * @param clients
     * @throws InterruptedException
     */
    public static void barber(int N, int clients) throws InterruptedException {
        SemaphoreArrayBlockingQueue<Integer> buffer = new SemaphoreArrayBlockingQueue<>(N);
        Thread barber = new Thread(() -> {
            try {
                for (int i = 0; i < clients; i++) {
                    Thread.sleep(buffer.dequeue());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread generator = new Thread(() -> {
            for (int i = 0; i < clients; i++) {
                try {
                    buffer.enqueue(ThreadLocalRandom.current().nextInt(100));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        barber.start();
        generator.start();
        barber.join();
        generator.join();
    }

    private enum OP {
        ADD, SUB, MUL
    }

    /**
     * Simular servidor de operaciones matemáticas con vectores remoto. En este
     * ejercicio el objetivo es desarrollar un servidor que proporciona un servicio
     * remoto de operaciones sobre vectores numéricos. El servidor debe poder sumar,
     * restar vectores y realizar el producto escalar. Los clientes le enviaran solicitudes
     * al servidor con vectores para que realice las operaciones y esperan a que éste las
     * termine. Una vez obtenidos los resultados se imprimen por pantalla. El número
     * de hilos será un parámetro de la aplicación concurrente.
     *
     * @param port
     * @throws IOException
     */
    public static void operationServer(final int port) throws IOException {
        ServerSocket ssc = new ServerSocket(port);
        while (!ssc.isClosed()) {
            final Socket sc = ssc.accept();
            new Thread(() -> {
                try {
                    try (
                            ObjectInputStream ois = new ObjectInputStream(sc.getInputStream());
                            ObjectOutputStream oos = new ObjectOutputStream(sc.getOutputStream())
                    ) {
                        OP op = (OP) ois.readObject();
                        int op1 = ois.readInt();
                        int op2 = ois.readInt();
                        switch (op) {
                            case ADD:
                                oos.writeInt(op1 + op2);
                                break;
                            case SUB:
                                oos.writeInt(op1 - op2);
                                break;
                            case MUL:
                                oos.writeInt(op1 * op2);
                                break;
                        }
                    }
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    /**
     * Simular garaje gratuito de vehículos. Implementar un garaje con capacidad para
     * N coches, cuyo acceso se realiza a través de E barreras automáticas de entrada y
     * S de salida. Dichas barreras se encuentran numeradas, desde la barrera 0 hasta
     * la E − 1 son de entrada y desde la barrera E hasta la E + S − 1 son de salida. Simular
     * el uso de las barreras mandando a dormir durante 5 milisegundos al vehículo
     * que está pasando. El número de vehículos será un parámetro de la aplicación
     * concurrente.
     *
     * @param N
     * @param EN
     * @param EX
     * @param nVehicles
     * @throws InterruptedException
     */
    public static void parking(int N, int EN, int EX, int nVehicles) throws InterruptedException {
        Semaphore lots = new Semaphore(N);
        Semaphore entrances = new Semaphore(EN);
        Semaphore exits = new Semaphore(EX);
        Thread[] vehicles = new Thread[nVehicles];
        for (int i = 0; i < nVehicles; i++) {
            vehicles[i] = new Thread(() -> {
                try {
                    lots.acquire();
                    entrances.acquire();
                    Thread.sleep(5);
                    entrances.release();
                    Thread.sleep(100);
                    exits.acquire();
                    Thread.sleep(5);
                    exits.release();
                    lots.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        for (Thread vehicle : vehicles) vehicle.start();
        for (Thread vehicle : vehicles) vehicle.join();
    }

    private static int station1_size;
    private static Semaphore station1_size_sem = new Semaphore(1);
    private static int station2_size;
    private static Semaphore station2_size_sem = new Semaphore(1);

    /**
     * Simular un controlador para gestionar las vías de dos estaciones de trenes. El
     * ejercicio se basa en diseñar un algoritmo que regule las salidas de dos estaciones
     * de tren cuyas vías se cruzan. En ambas estaciones se cargan trenes de mercancías
     * y se solicita la salida de un tren cuando está lleno. La forma de solicitar la salida
     * es preguntándole al controlador si el semáforo que da acceso a la vía en la propia
     * estación está en rojo y si la vía de la estación 2 no está siendo utilizada por otro
     * tren. Para evitar el uso simultáneo de vías, cada estación ha ubicado un sensor
     * de presencia que indica cuando un tren ha salido del cruce (ver Imagen 1).
     * El número de trenes por cada estación serán establecidos como un parámetro del
     * algoritmo.
     *
     * @param station1_max
     * @param station2_max
     * @param station1_in
     * @param station2_in
     * @throws Exception
     */
    public static void train(
            final int station1_max,
            final int station2_max,
            int station1_in,
            int station2_in
    ) throws Exception {
        station1_size = 0;
        station2_size = 0;
        Semaphore controller = new Semaphore(1);
        Semaphore station1_drain = new Semaphore(0);
        Semaphore station1_full = new Semaphore(station1_max);
        Semaphore station2_drain = new Semaphore(0);
        Semaphore station2_full = new Semaphore(station2_max);
        Thread[] station1_trains = new Thread[station1_in];
        Thread[] station2_trains = new Thread[station2_in];
        for (int i = 0; i < station1_in; i++) {
            station1_trains[i] = new Thread(() -> {
                try {
                    station1_full.acquire();
                    station1_size_sem.acquire();
                    station1_size += 1;
                    if (station1_size == station1_max) station1_drain.release();
                    station1_size_sem.release();
                    station1_drain.tryAcquire(1, TimeUnit.SECONDS);
                    controller.acquire();
                    station1_size_sem.acquire();
                    station1_size -= 1;
                    station1_size_sem.release();
                    controller.release();
                    station1_full.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        for (int i = 0; i < station2_in; i++) {
            station2_trains[i] = new Thread(() -> {
                try {
                    station2_full.acquire();
                    station2_size_sem.acquire();
                    station2_size += 1;
                    if (station2_size == station2_max) station2_drain.release();
                    station2_size_sem.release();
                    station2_drain.tryAcquire(1, TimeUnit.SECONDS);
                    controller.acquire();
                    station2_size_sem.acquire();
                    station2_size -= 1;
                    station2_size_sem.release();
                    controller.release();
                    station2_full.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        for (Thread train : station1_trains) train.start();
        for (Thread train : station2_trains) train.start();
        for (Thread train : station1_trains) train.join();
        for (Thread train : station2_trains) train.join();
    }
}
