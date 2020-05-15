package concurrente;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.OptionalDouble;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Tema5 {
    private Tema5() {
    }

    private static void experiment(int N, ExecutorService executorService, String name) {
        final long start = System.currentTimeMillis();
        System.out.println("Start experiment with executor " + name + " at time " + start);
        IntStream.range(0, N).forEach(id -> {
            executorService.execute(() -> {
                System.out.println("Starting thread " + id);
                System.out.println("Max in thread " + id + ": " + IntStream.range(1000, 2000).map(i -> (int) (i * Math.random())).max());
            });
        });
        System.out.println("End experiment with executor " + name + " elapsed " + (System.currentTimeMillis() - start));
        executorService.shutdown();
    }

    public static void performance_experiment(int N) {
        experiment(N, Executors.newSingleThreadExecutor(), "newSingleThreadExecutor");
        experiment(N, Executors.newFixedThreadPool(10), "newFixedThreadPool");
        experiment(N, Executors.newCachedThreadPool(), "newCachedThreadPool");
    }

    private static long _count(String str) {
        int count = 0;
        for (int i = 0; i < str.length(); i++) {
            switch (str.charAt(i)) {
                case 'a':
                case 'e':
                case 'i':
                case 'o':
                case 'u':
                    count += 1;
                default:
            }
        }
        return count;
    }

    public static long count(String str) throws InterruptedException {
        String[] strings = str.split("\\s");
        ExecutorService executor = Executors.newSingleThreadExecutor();
        return executor.invokeAll(
                Arrays.stream(strings)
                        .map(string -> (Callable<Long>) () -> _count(string))
                        .collect(Collectors.toList())
        ).stream().map(longFuture -> {
            try {
                return longFuture.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            return 0L;
        }).reduce(0L, Long::sum);
    }

    public static void experiment_invokeAll_invokeAny() throws InterruptedException, ExecutionException {
        ExecutorService executor = Executors.newFixedThreadPool(10);
        List<Callable<String>> callables = IntStream.range(0, 1000).mapToObj(i -> (Callable<String>) () -> {
            Thread.sleep((int) (1000 * Math.random()));
            return "Task " + i;
        }).collect(Collectors.toList());
        long start = System.currentTimeMillis();
        System.out.println("Start experiment with invokeAll at time " + start);
        executor.invokeAll(callables);
        System.out.println("End experiment with invokeAll elapsed " + (System.currentTimeMillis() - start));
        System.out.println("Start experiment with invokeAny at time " + start);
        executor.invokeAny(callables);
        System.out.println("End experiment with invokeAny elapsed " + (System.currentTimeMillis() - start));
    }

    public static void availability(List<URL> pages) throws InterruptedException {
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(4);
        pages.forEach(url -> executorService.scheduleAtFixedRate(() -> {
            try {
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("HEAD");
                conn.connect();
                if (conn.getResponseCode() == 200) {
                    System.out.println(url.toString() + " is alive");
                } else {
                    System.out.println(url.toString() + " is dead");
                }
            } catch (IOException e) {
                System.out.println(url.toString() + " is dead");
            }
        }, 0, 5, TimeUnit.SECONDS));
        Thread.sleep(60000);
    }

    private static class FJMax extends RecursiveTask<Integer> {
        private static final int THRESH_HOLD = 10;
        private final int[] arr;
        private final int lo;
        private final int hi;

        public FJMax(int[] arr, int lo, int hi) {
            this.arr = arr;
            this.lo = lo;
            this.hi = hi;
        }

        @Override
        protected Integer compute() {
            if (hi - lo < THRESH_HOLD) {
                int max = Integer.MIN_VALUE;
                for (int i = lo; i <= hi; i++) max = Math.max(max, this.arr[i]);
                return max;
            } else {
                int mid = this.lo + (this.hi - this.lo) / 2;
                FJMax left = new FJMax(arr, lo, mid);
                FJMax right = new FJMax(arr, mid + 1, hi);
                right.fork();
                return Math.max(left.compute(), right.join());
            }
        }
    }

    public static int fjmax(int[] arr) {
        ForkJoinPool pool = new ForkJoinPool();
        int result = pool.invoke(new FJMax(arr, 0, arr.length - 1));
        pool.shutdown();
        return result;
    }

    public static Double mean(String lines) {
        return Arrays.stream(lines.split("\n")).map(line -> Arrays.stream(line.split("\\s")).map(Integer::parseInt).mapToDouble(Double::valueOf).average()).mapToDouble(OptionalDouble::getAsDouble).max().getAsDouble();
    }

    private static class FJCountRange extends RecursiveTask<Integer> {
        private static final int THRESH_HOLD = 10;
        private final int[] arr;
        private final int lo;
        private final int hi;
        private final int min;
        private final int max;

        public FJCountRange(int[] arr, int lo, int hi, int min, int max) {
            this.arr = arr;
            this.lo = lo;
            this.hi = hi;
            this.min = min;
            this.max = max;
        }

        @Override
        protected Integer compute() {
            if (hi - lo < THRESH_HOLD) {
                int count = 0;
                for (int i = lo; i <= hi; i++) if (arr[i] >= min && arr[i] <= max) count += 1;
                return count;
            } else {
                int mid = this.lo + (this.hi - this.lo) / 2;
                FJCountRange left = new FJCountRange(arr, lo, mid, min, max);
                FJCountRange right = new FJCountRange(arr, mid + 1, hi, min, max);
                right.fork();
                return left.compute() + right.join();
            }
        }
    }

    /**
     * Dado un array de enteros
     * Contar el número de veces que un número está entre
     * dos valores fijos (variables globales).
     * Una vez el tamaño del array es inferior a 10 realizar
     * esta acción.
     * Por simplicidad, dividirlo a la mitad.
     *
     * @param min
     * @param max
     * @return
     */
    public static int fjCountRange(int[] nums, int min, int max) {
        ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
        int result = pool.invoke(new FJCountRange(nums, 0, nums.length - 1, min, max));
        pool.shutdown();
        return result;
    }
}
