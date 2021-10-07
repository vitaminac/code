package core.util;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.URL;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class Utils {
    private static final ExecutorService EXECUTOR_SERVICE = Executors.newCachedThreadPool();
    private static final Scanner SCANNER = new Scanner(System.in);

    private Utils() {
    }

    public static void fetch(final String download_url, final String path, int n_threads)
            throws IOException {
        URL url = new URL(download_url);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("HEAD");
        long size = conn.getContentLengthLong();
        conn.getInputStream().close();
        RandomAccessFile f = new RandomAccessFile(path, "rw");
        f.setLength(size);
        f.close();
        long chunk = size / n_threads + 1;
        CompletableFuture<?>[] futures = new CompletableFuture[n_threads];
        for (int i = 0; i < n_threads; i++) {
            final long start = java.lang.Math.max(0, java.lang.Math.min(chunk * i, size - 1));
            final long end = java.lang.Math.max(0, java.lang.Math.min(chunk * (i + 1) - 1, size - 1));
            futures[i] = CompletableFuture.runAsync(() -> {
                try {
                    HttpURLConnection myConn = (HttpURLConnection) url.openConnection();
                    myConn.setRequestProperty("Range", "bytes=" + start + "-" + end);
                    myConn.connect();
                    assert myConn.getResponseCode() == 206;
                    long length = Long.parseLong(myConn.getHeaderField("Content-Length"));
                    assert length == end - start + 1;
                    try (
                            BufferedInputStream bif = new BufferedInputStream(myConn.getInputStream());
                            RandomAccessFile rf = new RandomAccessFile(path, "rw")
                    ) {
                        rf.seek(start);
                        try (var bof = new BufferedOutputStream(new FileOutputStream(rf.getFD()))) {
                            bif.transferTo(bof);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }, EXECUTOR_SERVICE);
        }

        CompletableFuture.allOf(futures).join();
    }

    public static void fetch(final String download_url, final String path) throws IOException {
        fetch(download_url, path, 32);
    }

    public static String repeat(String str, int n) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) sb.append(str);
        return sb.toString();
    }

    public static String input(String message) {
        System.out.print(message);
        return SCANNER.next();
    }

    public static int inputInteger(String message) {
        System.out.print(message);
        return SCANNER.nextInt();
    }

    public static InetSocketAddress parseIpAddress(String address) {
        int sepIndex = address.lastIndexOf(":");
        return new InetSocketAddress(address.substring(0, sepIndex), Integer.parseInt(address.substring(sepIndex + 1)));
    }

    private static final String WARNING = "\u001B[33m";
    private static final String RESET_NORMAL = "\033[0;30m";

    public static void warn(String msg) {
        System.out.println(WARNING + msg + RESET_NORMAL);
    }

    public static void info(String msg) {
        System.out.println(msg);
    }
}
