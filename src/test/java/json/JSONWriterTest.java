package json;

import json.writer.JSONObjectWriter;
import json.writer.JSONWriter;
import org.junit.Test;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class JSONWriterTest {
    @Test
    public void writeBoolean() throws Exception {
        Writer writer = this.getUnderlyingWriter();
        final JSONWriter jsonWriter = new JSONWriter(writer);
        jsonWriter.write(true);
        assertEquals("true", writer.toString());
    }

    @Test
    public void writeBooleanArray() throws Exception {
        Writer writer = this.getUnderlyingWriter();
        final JSONWriter jsonWriter = new JSONWriter(writer);
        jsonWriter.write(new boolean[]{true, false, true, true, false});
        assertEquals("[true,false,true,true,false]", writer.toString());
    }

    @Test(expected = IOException.class)
    public void writeBooleanTwice() throws Exception {
        Writer writer = this.getUnderlyingWriter();
        final JSONWriter jsonWriter = new JSONWriter(writer);
        jsonWriter.write(true);
        assertEquals("true", writer.toString());
        jsonWriter.write(false);
    }

    @Test
    public void writeByte() throws Exception {
        Writer writer = this.getUnderlyingWriter();
        final JSONWriter jsonWriter = new JSONWriter(writer);
        byte b = 0xf;
        jsonWriter.write(b);
        assertEquals("15", writer.toString());
    }

    @Test
    public void writeChar() throws Exception {
        Writer writer = this.getUnderlyingWriter();
        final JSONWriter jsonWriter = new JSONWriter(writer);
        jsonWriter.write('c');
        assertEquals("\"c\"", writer.toString());
    }

    @Test
    public void writeCharArray() throws Exception {
        Writer writer = this.getUnderlyingWriter();
        final JSONWriter jsonWriter = new JSONWriter(writer);
        jsonWriter.write(new char[]{'a', 'r', 'r', 'a', 'y'});
        assertEquals("[\"a\",\"r\",\"r\",\"a\",\"y\"]", writer.toString());
    }

    @Test
    public void writeComplexJSONObject() throws Exception {
        Writer writer = this.getUnderlyingWriter();
        final JSONWriter jsonWriter = new JSONWriter(writer);
        List<JSONObject> list = new ArrayList<>();
        list.add(new JSONObject());
        jsonWriter
                .write(new JSONObject((byte) 30, "hola", 'I', 3.1415, 3.14f, 3, 30, new JSONObject(), (short) -1, list));
        assertEquals("{\"b\":30,\"str\":\"hola\",\"c\":\"I\",\"d\":3.1415,\"f\":3.14,\"i\":3,\"l\":30,\"o\":{\"b\":73,\"str\":\"love\",\"c\":\"u\",\"d\":6.15,\"f\":8.6,\"i\":520,\"l\":1314,\"o\":null,\"s\":5,\"os\":[]},\"s\":-1,\"os\":[{\"b\":73,\"str\":\"love\",\"c\":\"u\",\"d\":6.15,\"f\":8.6,\"i\":520,\"l\":1314,\"o\":null,\"s\":5,\"os\":[]}]}", writer
                .toString());
    }

    @Test
    public void writeCustomJSONObject() throws Exception {
        Writer writer = this.getUnderlyingWriter();
        final JSONWriter jsonWriter = new JSONWriter(writer);
        jsonWriter.write(new JSON() {
            private int a = 1;
            private int b = 2;

            @Override
            public void writeJSON(JSONObjectWriter writer) throws IOException {
                writer.write("a", 1);
                writer.write("b", 2);
            }
        });
        assertEquals("{\"a\":1,\"b\":2}", writer.toString());
    }

    @Test(expected = IOException.class)
    public void writeDoubleAgain() throws Exception {
        Writer writer = this.getUnderlyingWriter();
        final JSONWriter jsonWriter = new JSONWriter(writer);
        jsonWriter.write(1.258644444E45);
        assertEquals("1.258644444E45", writer.toString());
        jsonWriter.write(5.5);
    }

    @Test
    public void writeDoubleArray() throws Exception {
        Writer writer = this.getUnderlyingWriter();
        final JSONWriter jsonWriter = new JSONWriter(writer);
        jsonWriter.write(new double[]{1.1, 2.1, 3.1, 4.1});
        assertEquals("[1.1,2.1,3.1,4.1]", writer.toString());
    }

    @Test
    public void writeDoubleNegative() throws Exception {
        Writer writer = this.getUnderlyingWriter();
        final JSONWriter jsonWriter = new JSONWriter(writer);
        jsonWriter.write(1.258644444E-45);
        assertEquals("1.258644444E-45", writer.toString());
    }

    @Test
    public void writeDoublePositive() throws Exception {
        Writer writer = this.getUnderlyingWriter();
        final JSONWriter jsonWriter = new JSONWriter(writer);
        jsonWriter.write(1.258644444E45);
        assertEquals("1.258644444E45", writer.toString());
    }

    @Test
    public void writeFloat() throws Exception {
        Writer writer = this.getUnderlyingWriter();
        final JSONWriter jsonWriter = new JSONWriter(writer);
        jsonWriter.write(1.2E10f);
        assertEquals("1.2E10", writer.toString());
    }

    @Test
    public void writeFloutArray() throws Exception {
        Writer writer = this.getUnderlyingWriter();
        final JSONWriter jsonWriter = new JSONWriter(writer);
        jsonWriter.write(new float[]{1.1f, 2.1f, 3.1f, 4.1f});
        assertEquals("[1.1,2.1,3.1,4.1]", writer.toString());
    }

    @Test
    public void writeInt() throws Exception {
        Writer writer = this.getUnderlyingWriter();
        final JSONWriter jsonWriter = new JSONWriter(writer);
        jsonWriter.write(123456);
        assertEquals("123456", writer.toString());
    }

    @Test(expected = IOException.class)
    public void writeIntAgain() throws Exception {
        Writer writer = this.getUnderlyingWriter();
        final JSONWriter jsonWriter = new JSONWriter(writer);
        jsonWriter.write(123456);
        assertEquals("123456", writer.toString());
        jsonWriter.write(22);
    }

    @Test
    public void writeIntegerArray() throws Exception {
        Writer writer = this.getUnderlyingWriter();
        final JSONWriter jsonWriter = new JSONWriter(writer);
        jsonWriter.write(new int[]{1, 2, 3, 4});
        assertEquals("[1,2,3,4]", writer.toString());
    }

    @Test
    public void writeJSONObject() throws Exception {
        Writer writer = this.getUnderlyingWriter();
        final JSONWriter jsonWriter = new JSONWriter(writer);
        jsonWriter.write(new JSONObject());
        assertEquals("{\"b\":73,\"str\":\"love\",\"c\":\"u\",\"d\":6.15,\"f\":8.6,\"i\":520,\"l\":1314,\"o\":null,\"s\":5,\"os\":[]}", writer
                .toString());
    }

    @Test
    public void writeLongArray() throws Exception {
        Writer writer = this.getUnderlyingWriter();
        final JSONWriter jsonWriter = new JSONWriter(writer);
        jsonWriter.write(new long[]{1, 2, 3, 4});
        assertEquals("[1,2,3,4]", writer.toString());
    }

    @Test
    public void writeNullCharArray() throws Exception {
        Writer writer = this.getUnderlyingWriter();
        final JSONWriter jsonWriter = new JSONWriter(writer);
        jsonWriter.write((char[]) null);
        assertEquals("null", writer.toString());
    }

    @Test
    public void writeNullJSONObject() throws Exception {
        Writer writer = this.getUnderlyingWriter();
        final JSONWriter jsonWriter = new JSONWriter(writer);
        jsonWriter.write((JSONObject) null);
        assertEquals("null", writer.toString());
    }

    @Test
    public void writeNullString() throws Exception {
        Writer writer = this.getUnderlyingWriter();
        final JSONWriter jsonWriter = new JSONWriter(writer);
        jsonWriter.write((String) null);
        assertEquals("null", writer.toString());
    }

    @Test
    public void writeShort() throws Exception {
        Writer writer = this.getUnderlyingWriter();
        final JSONWriter jsonWriter = new JSONWriter(writer);
        jsonWriter.write((short) 1234);
        assertEquals("1234", writer.toString());
    }

    @Test
    public void writeShortArray() throws Exception {
        Writer writer = this.getUnderlyingWriter();
        final JSONWriter jsonWriter = new JSONWriter(writer);
        jsonWriter.write(new short[]{1, 2, 3, 4});
        assertEquals("[1,2,3,4]", writer.toString());
    }

    @Test
    public void writeString() throws Exception {
        Writer writer = this.getUnderlyingWriter();
        final JSONWriter jsonWriter = new JSONWriter(writer);
        jsonWriter.write("string");
        assertEquals("\"string\"", writer.toString());
    }

    @Test
    public void writeStringArray() throws Exception {
        Writer writer = this.getUnderlyingWriter();
        final JSONWriter jsonWriter = new JSONWriter(writer);
        jsonWriter.write(new String[]{"123", "456", "789"});
        assertEquals("[\"123\",\"456\",\"789\"]", writer.toString());
    }

    @Test(expected = IOException.class)
    public void writeStringTwice() throws Exception {
        Writer writer = this.getUnderlyingWriter();
        final JSONWriter jsonWriter = new JSONWriter(writer);
        jsonWriter.write("string");
        assertEquals("\"string\"", writer.toString());
        jsonWriter.write("string2");
    }

    @Test
    public void writeStringWithSpecialSymbol() throws Exception {
        Writer writer = this.getUnderlyingWriter();
        final JSONWriter jsonWriter = new JSONWriter(writer);
        jsonWriter.write("\"\\/\b\f\n\r\t");
        assertEquals("\"\\\"\\\\\\/\\b\\f\\n\\r\\t\"", writer.toString());
    }

    private Writer getUnderlyingWriter() {
        return new StringWriter();
    }

    private class StringWriter extends Writer {
        private boolean closed = false;
        private StringBuilder stringBuilder = new StringBuilder();

        @Override
        public String toString() {
            return this.stringBuilder.toString();
        }

        @Override
        public void write(char[] cbuf, int off, int len) throws IOException {
            if (this.closed) {
                throw new IOException();
            }
            this.stringBuilder.append(cbuf, off, len);
        }

        @Override
        public void flush() throws IOException {
            if (this.closed) {
                throw new IOException();
            }
        }

        @Override
        public void close() {
            this.closed = true;
        }
    }
}