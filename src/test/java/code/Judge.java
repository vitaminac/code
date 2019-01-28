package code;


import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class Judge {
    private static <T> void judge(Class<T> clazz, String[] params) throws Exception {
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

    private static List<Class> findClasses(File directory, String packageName) throws ClassNotFoundException {
        List<Class> classes = new ArrayList<>();
        if (!directory.exists()) {
            return classes;
        }
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    assert !file.getName().contains(".");
                    classes.addAll(findClasses(file, packageName + "." + file.getName()));
                } else if (file.getName().endsWith(".class")) {
                    classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
                }
            }
        }
        return classes;
    }

    private static Class[] getClasses(String packageName) throws Exception {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        assert classLoader != null;
        String path = packageName.replace('.', '/');
        Enumeration<URL> resources = classLoader.getResources(path);
        List<File> dirs = new ArrayList<>();
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            dirs.add(new File(resource.getFile()));
        }
        ArrayList<Class> classes = new ArrayList<>();
        for (File directory : dirs) {
            classes.addAll(findClasses(directory, packageName));
        }
        return classes.toArray(new Class[0]);
    }

    @Test
    public void judgeAll() throws Exception {
        for (Class<?> clazz : getClasses(this.getClass().getPackage().getName())) {
            if (!clazz.equals(this.getClass())) {
                try {
                    judge(clazz, null);
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
