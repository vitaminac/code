package code;


import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.assertArrayEquals;

public class Judge {
    private <T> void judge(Class<T> clazz, String[] params) throws Exception {
        final Method method = clazz.getMethod("main", String[].class);
        final URL inputResource = clazz.getResource(clazz.getSimpleName() + "Input.txt");
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
        method.invoke(null, (Object) params);

        // refresh buffer
        printStream.flush();
        printStream.close();

        // compare result
        final URL outputResource = clazz.getResource(clazz.getSimpleName() + "Output.txt");
        final byte[] expected = Files.readAllBytes(Paths.get(outputResource.toURI()));
        final byte[] output = stdout.toByteArray();
        assertArrayEquals(expected, output);
    }

    @Test
    public void judgeAll() throws Exception {
        judge(HolaMundo.class, null);
    }
}
