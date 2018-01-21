package json;

import org.junit.Test;

import java.io.StringWriter;

import static org.junit.Assert.assertEquals;

public class JSONValueTest {

    @Test
    public void string() throws Exception {
        StringWriter stringWriter = new StringWriter();
        final JSONWriter jsonWriter = new JSONWriter(stringWriter);
        JSONValue jsonValue = new JSONValue("string");
        jsonValue.writeJSON(jsonWriter);
        assertEquals("\"string\"", stringWriter.toString());
    }
}