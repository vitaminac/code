package json;

import json.reader.JSONReader;
import org.junit.Test;

import java.io.Reader;
import java.io.StringReader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class JSONReaderTest {
    @Test
    public void readBoolean() throws Exception {
        StringReader stringReader = new StringReader("\t true ");
        JSONReader jsonReader = new JSONReader(stringReader);
        assertTrue(jsonReader.readBoolean());
        stringReader = new StringReader("\t false ");
        jsonReader = new JSONReader(stringReader);
        assertFalse(jsonReader.readBoolean());
    }

    @Test
    public void readDouble() throws Exception {
        Reader reader = this.getUnderlyingReader("1.258644444E45");
        final JSONReader jsonReader = new JSONReader(reader);
        assertEquals(1.258644444E45, jsonReader.readNumber(), 0);
    }

    @Test
    public void readInt() throws Exception {
        Reader reader = this.getUnderlyingReader("123");
        final JSONReader jsonReader = new JSONReader(reader);
        assertEquals(123, jsonReader.readNumber(), 0);
    }

    @Test
    public void readObject() throws Exception {
        Reader reader = this
                .getUnderlyingReader("{\"b\":73,\"str\":\"love\",\"c\":\"u\",\"d\":6.15,\"f\":8.6,\"i\":520," +
                        "\"l\":1314,\"o\":null,\"s\":5,\"os\":[]}");
        final JSONReader jsonReader = new JSONReader(reader);
        assertEquals(new JSONObject(), jsonReader.getObject(new JSONObjectFactory()));
    }

    @Test
    public void readString() throws Exception {
        StringReader stringReader = new StringReader(" \"str\\\\\\\"ing\" ");
        JSONReader jsonReader = new JSONReader(stringReader);
        String str = jsonReader.readString();
        assertEquals("str\\\"ing", str);
        stringReader = new StringReader(" null ");
        jsonReader = new JSONReader(stringReader);
        str = jsonReader.readString();
        assertNull(str);
    }

    private Reader getUnderlyingReader(String str) {
        return new StringReader(str);
    }
}