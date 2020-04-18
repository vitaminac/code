package core;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

public final class Utils {
    private Utils() {
    }

    public static void multithreading_download(final String download_url, final String path, int n_threads)
            throws IOException, InterruptedException {
        URL url = new URL(download_url);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("HEAD");
        long size = conn.getContentLengthLong();
        conn.getInputStream().close();
        RandomAccessFile f = new RandomAccessFile(path, "rw");
        f.setLength(size);
        f.close();
        long chunk = size / n_threads + 1;
        Thread[] threads = new Thread[n_threads];
        for (int i = 0; i < n_threads; i++) {
            final long start = java.lang.Math.max(0, java.lang.Math.min(chunk * i, size - 1));
            final long end = java.lang.Math.max(0, java.lang.Math.min(chunk * (i + 1) - 1, size - 1));
            threads[i] = new Thread(() -> {
                try {
                    HttpURLConnection myConn = (HttpURLConnection) url.openConnection();
                    myConn.setRequestProperty("Range", "bytes=" + start + "-" + end);
                    myConn.connect();
                    assert myConn.getResponseCode() == 206;
                    long length = Long.parseLong(myConn.getHeaderField("Content-Length"));
                    assert length == end - start + 1;
                    byte[] buf = new byte[256];
                    try (
                            BufferedInputStream bif = new BufferedInputStream(myConn.getInputStream());
                            RandomAccessFile rf = new RandomAccessFile(path, "rw")
                    ) {
                        rf.seek(start);
                        int read;
                        while ((read = bif.read(buf, 0, (int) Math.min(256, length))) != -1 && length > 0) {
                            rf.write(buf, 0, read);
                            length -= read;
                        }
                        assert length == 0;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
        for (int i = 0; i < n_threads; i++) threads[i].start();
        for (int i = 0; i < n_threads; i++) threads[i].join();
    }
}
