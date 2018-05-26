package json;

import json.reader.JSONRawReader;
import json.reader.JSONReader;
import org.junit.Test;

import java.io.Reader;
import java.io.StringReader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class JSONReaderTest {
    @Test
    public void readBoolean() throws Exception {
        final StringReader stringReader = new StringReader("true");
        final JSONReader jsonReader = new JSONReader(stringReader);
        Boolean b = jsonReader.readBoolean();
        assertTrue(b);
    }

    @Test
    public void readDouble() throws Exception {
        Reader reader = this.getUnderlyingReader("1.258644444E45");
        final JSONRawReader jsonReader = new JSONRawReader(reader);
        assertEquals(1.258644444E45, jsonReader.readNumber(), 0);
    }

    @Test
    public void readInt() throws Exception {
        Reader reader = this.getUnderlyingReader("123");
        final JSONRawReader jsonReader = new JSONRawReader(reader);
        assertEquals(123, jsonReader.readNumber(), 0);
    }

    @Test
    public void readString() throws Exception {
        final StringReader stringReader = new StringReader(" \"str\\\\\\\"ing\" ");
        final JSONReader jsonReader = new JSONReader(stringReader);
        String str = jsonReader.readString();
        assertEquals("str\\\"ing", str);
    }

    private Reader getUnderlyingReader(String str) {
        return new StringReader(str);
    }
}