package code;


import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Judge {
    private <T> void judge(Class<T> clazz, String[] params) throws Exception {
        final Method method = clazz.getMethod("main", String[].class);
        final URL resource = clazz.getResource(clazz.getSimpleName() + "Input.txt");
        final String input = new String(Files.readAllBytes(Paths.get(resource.toURI())));
        InputStream stdin = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        System.setIn(stdin);
        method.invoke(null, (Object) params);
    }

    @Test
    public void judgeAll() throws Exception {
        judge(HolaMundo.class, null);
    }
}
