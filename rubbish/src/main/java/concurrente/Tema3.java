package concurrente;

import core.Arrays;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.Semaphore;

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
        for (Thread thread : threads) {
            thread.join();
        }
        return result;
    }

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
        int[] result = new int[N];
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
                            result[index] += 1;
                        default:
                    }
                }
            });
            threads[idx].start();
        }
        for (Thread t : threads) t.join();
        long count = 0;
        for (int n : result) count += n;
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
}
