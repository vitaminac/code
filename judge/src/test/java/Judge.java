import algs4.stdlib.StdIn;
import algs4.stdlib.StdOut;
import core.util.Utils;

import java.io.*;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public final class Judge {
    private static <T> void judge(Class<T> clazz, Method method, Object instance, Object params, Path input, Path output)
        throws Exception {
        // map rid of platform depend new line
        System.setProperty("line.separator", "\n");

        // redirect stdin
        InputStream stdin = new ByteArrayInputStream(Files.readAllBytes(input));
        System.setIn(stdin);
        StdIn.resync();

        // backup standard output stream
        final PrintStream backup = System.out;

        // redirect stdout
        ByteArrayOutputStream stdout = new ByteArrayOutputStream();
        final PrintStream printStream = new PrintStream(stdout);
        System.setOut(printStream);
        StdOut.resync();

        // call main
        long startTime = System.nanoTime();
        method.setAccessible(true);
        method.invoke(instance, params);
        long endTime = System.nanoTime();
        long elapsedTime = endTime - startTime;

        // refresh buffer
        printStream.flush();
        printStream.close();

        // compare result
        assertEquals(Files.readString(output, StandardCharsets.UTF_8), stdout.toString(StandardCharsets.UTF_8));

        // restore standard output stream
        System.setOut(backup);

        // report
        System.out.printf("Time elapsed for %s with input %s and output %s: %d microsecond\n",
            clazz.getSimpleName(),
            input.getFileName(),
            output.getFileName().toString(),
            elapsedTime / 1000);
    }

    public static <T> void judge(Class<T> clazz, String[] params) throws Exception {
        if (clazz.getEnclosingClass() != null ||
            Modifier.isStatic(clazz.getModifiers())) {
            Utils.warn(clazz.getName() + " skipped, inner class or lambda");
            return;
        }

        if (Modifier.isPrivate(clazz.getModifiers())) {
            Utils.warn(clazz.getName() + " skipped, class is hidden!");
            return;
        }

        final Method main;
        try {
            main = clazz.getMethod("main", String[].class);
        } catch (NoSuchMethodException e) {
            Utils.warn(clazz.getName() + " skipped, no main exist!");
            return;
        }

        int count = 0;
        final URL testResource = clazz.getResource(clazz.getSimpleName());
        if (testResource != null) {
            Path testSourcePath = Paths.get(testResource.toURI());
            File testSourceFile = testSourcePath.toFile();
            if (testSourceFile.exists() && testSourceFile.isDirectory()) {
                final List<Path> inputs = Files.list(testSourcePath).toList();
                assertNotEquals(clazz.getSimpleName() + "'s input is empty", 0, inputs.size());
                for (Path input : inputs) {
                    if (input.toString().endsWith(".in")) {
                        String filename = input.getFileName().toString();
                        judge(clazz, main, null, params, input, testSourcePath.resolve(filename.substring(0, filename.length() - 3) + ".out"));
                        count += 1;
                    }
                }
            }
        }

        final URL inputResource = clazz.getResource(clazz.getSimpleName() + ".in");
        final URL outputResource = clazz.getResource(clazz.getSimpleName() + ".out");
        if (inputResource != null && outputResource != null) {
            judge(clazz, main, null, null, Paths.get(inputResource.toURI()), Paths.get(outputResource.toURI()));
            count += 1;
        }
        assertTrue(clazz.getSimpleName() + " hasn't been tested! No input found!", count > 0);
    }
}