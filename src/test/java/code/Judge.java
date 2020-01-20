package code;

import code.aceptaelreto.P542LaTiaManuela;
import code.aceptaelreto.P544QueNoSeAtraganten;
import org.junit.Test;

import java.io.*;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class Judge {
    private static final String WARNING = "\u001B[33m";
    private static final String NORMAL = "\033[0;30m";

    public static <T> void judge(Class<T> clazz, String[] params) throws Exception {
        try {
            Method method = clazz.getMethod("main", String[].class);
            if (method == null || Modifier.isPrivate(clazz.getModifiers())) {
                System.out.println(WARNING + "Skipped hidden class " + clazz.getName() + NORMAL);
            } else {
                final URL inputResource = clazz.getResource(clazz.getSimpleName() + ".in");
                assertNotNull(clazz.getSimpleName() + "'s input is empty", inputResource);
                final byte[] input = Files.readAllBytes(Paths.get(inputResource.toURI()));

                // map rid of platform depend new line
                System.setProperty("line.separator", "\n");

                // redirect stdin
                InputStream stdin = new ByteArrayInputStream(input);
                System.setIn(stdin);

                // redirect stdout
                ByteArrayOutputStream stdout = new ByteArrayOutputStream();
                final PrintStream printStream = new PrintStream(stdout);
                System.setOut(printStream);

                // call main
                long startTime = System.nanoTime();
                method.setAccessible(true);
                method.invoke(null, (Object) params);
                long endTime = System.nanoTime();
                long elapsedTime = endTime - startTime;

                // refresh buffer
                printStream.flush();
                printStream.close();

                // compare result
                final URL outputResource = clazz.getResource(clazz.getSimpleName() + ".out");
                System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
                final String output = new String(stdout.toByteArray(), StandardCharsets.UTF_8);
                if (outputResource != null) {
                    final byte[] expected = Files.readAllBytes(Paths.get(outputResource.toURI()));
                    assertEquals(new String(expected, StandardCharsets.UTF_8), output);
                } else {
                    System.out.println(WARNING + "Skipping " + clazz.getName());
                    System.out.println(output);
                    System.out.println(NORMAL);
                }
                System.out.println(
                        "Time elapsed for " + clazz.getSimpleName() + ": " + elapsedTime / 1000 + " microsecond");
            }
        } catch (NoSuchMethodException e) {
        }
    }

    @Test
    public void judgeAll() throws Exception {
        int n_all_tests = 0;
        int n_failed = 0;
        String[] packages = new String[]{"code.aceptaelreto", "code.concurso", "code.algs4"};
        for (String pkg : packages) {
            for (Utils.Pair pair : Utils.getResources(pkg, "class")) {
                Class<?> clazz = Class.forName(
                        pair.packageName + '.' + pair.file.getName().substring(0, pair.file.getName().length() - 6));
                try {
                    n_all_tests += 1;
                    judge(clazz, null);
                } catch (Throwable e) {
                    n_failed += 1;
                    e.printStackTrace();
                }
            }
        }
        System.out.println("Total " + n_all_tests + " unit tests and " + n_failed + " of them have failed!");
    }

    @Test
    public void test() throws Exception {
        Judge.judge(P542LaTiaManuela.class, null);
    }
}
