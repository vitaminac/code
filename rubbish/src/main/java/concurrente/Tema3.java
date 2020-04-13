package concurrente;

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

    public static void merge_sort(int[] arr, int low, int high, int[] aux, int proc) {
        if (low >= high) return;
    }
}
