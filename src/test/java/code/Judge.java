package code;


import code.so.DekkerAlgorithm;
import code.sql.JudgeSQL;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class Judge {
    private static final Set<Class> classes = new HashSet<>();

    static {
        classes.add(Judge.class);
        classes.add(JudgeSQL.class);
    }

    private static <T> void judge(Class<T> clazz, String[] params) throws Exception {
        if (classes.contains(clazz) || !Modifier.isPublic(clazz.getModifiers())) {
            System.out.println("\u001B[33m" + "Skipped " + clazz.getName() + "\033[0;30m");
        } else {
            final Method method = clazz.getMethod("main", String[].class);
            final URL inputResource = clazz.getResource(clazz.getSimpleName() + "Input.txt");
            assertNotNull(clazz.getSimpleName() + "'s input is empty", inputResource);
            final byte[] input = Files.readAllBytes(Paths.get(inputResource.toURI()));

            // get rid of platform depend new line
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
            final URL outputResource = clazz.getResource(clazz.getSimpleName() + "Output.txt");
            assertNotNull(clazz.getSimpleName() + "'s output is empty", outputResource);
            final byte[] expected = Files.readAllBytes(Paths.get(outputResource.toURI()));
            final byte[] output = stdout.toByteArray();
            assertEquals(new String(expected, StandardCharsets.UTF_8), new String(output, StandardCharsets.UTF_8));

            System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
            System.out.println("Time elapsed for " + clazz.getSimpleName() + ": " + elapsedTime / 1000 + " microsecond");
        }
    }

    @Test
    public void judgeAll() throws Exception {
        String packageName = this.getClass().getPackage().getName();
        for (Utils.Pair pair : Utils.getResources(this.getClass(), "class")) {
            Class<?> clazz = Class.forName(pair.packageName + '.' + pair.file.getName().substring(0, pair.file.getName().length() - 6));
            try {
                judge(clazz, null);
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void judgeOne() throws Exception {
        judge(DekkerAlgorithm.class, null);
    }
}
