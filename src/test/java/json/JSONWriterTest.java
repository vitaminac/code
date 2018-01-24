package json;

import org.junit.Test;
import testobject.TestJSONObject;

import java.io.IOException;
import java.io.Writer;

import static org.junit.Assert.assertEquals;

public class JSONWriterTest {
    @Test
    public void writeInt() throws Exception {
        StringWriter stringWriter = new StringWriter();
        final JSONWriter jsonWriter = new JSONWriter(stringWriter);
        jsonWriter.write(123456);
        assertEquals("123456", stringWriter.toString());
    }

    @Test
    public void writeShort() throws Exception {
        StringWriter stringWriter = new StringWriter();
        final JSONWriter jsonWriter = new JSONWriter(stringWriter);
        jsonWriter.write((short) 1234);
        assertEquals("1234", stringWriter.toString());
    }

    @Test(expected = IOException.class)
    public void writeIntAgain() throws Exception {
        StringWriter stringWriter = new StringWriter();
        final JSONWriter jsonWriter = new JSONWriter(stringWriter);
        jsonWriter.write(123456);
        assertEquals("123456", stringWriter.toString());
        jsonWriter.write(22);
    }

    @Test
    public void writeByte() throws Exception {
        StringWriter stringWriter = new StringWriter();
        final JSONWriter jsonWriter = new JSONWriter(stringWriter);
        byte b = 0xf;
        jsonWriter.write(b);
        assertEquals("15", stringWriter.toString());
    }

    @Test
    public void writeBoolean() throws Exception {
        StringWriter stringWriter = new StringWriter();
        final JSONWriter jsonWriter = new JSONWriter(stringWriter);
        jsonWriter.write(true);
        assertEquals("true", stringWriter.toString());
    }

    @Test(expected = IOException.class)
    public void writeBooleanTwice() throws Exception {
        StringWriter stringWriter = new StringWriter();
        final JSONWriter jsonWriter = new JSONWriter(stringWriter);
        jsonWriter.write(true);
        assertEquals("true", stringWriter.toString());
        jsonWriter.write(false);
    }

    @Test
    public void writeDoublePositive() throws Exception {
        StringWriter stringWriter = new StringWriter();
        final JSONWriter jsonWriter = new JSONWriter(stringWriter);
        jsonWriter.write(1.258644444E45);
        assertEquals("1.258644444E45", stringWriter.toString());
    }

    @Test
    public void writeFloat() throws Exception {
        StringWriter stringWriter = new StringWriter();
        final JSONWriter jsonWriter = new JSONWriter(stringWriter);
        jsonWriter.write(1.2E10f);
        assertEquals("1.2E10", stringWriter.toString());
    }

    @Test
    public void writeDoubleNegative() throws Exception {
        StringWriter stringWriter = new StringWriter();
        final JSONWriter jsonWriter = new JSONWriter(stringWriter);
        jsonWriter.write(1.258644444E-45);
        assertEquals("1.258644444E-45", stringWriter.toString());
    }

    @Test(expected = IOException.class)
    public void writeDoubleAgain() throws Exception {
        StringWriter stringWriter = new StringWriter();
        final JSONWriter jsonWriter = new JSONWriter(stringWriter);
        jsonWriter.write(1.258644444E45);
        assertEquals("1.258644444E45", stringWriter.toString());
        jsonWriter.write(5.5);
    }

    @Test
    public void writeChar() throws Exception {
        StringWriter stringWriter = new StringWriter();
        final JSONWriter jsonWriter = new JSONWriter(stringWriter);
        jsonWriter.write('c');
        assertEquals("\"c\"", stringWriter.toString());
    }

    @Test
    public void writeCharArrayAsSingleValue() throws Exception {
        StringWriter stringWriter = new StringWriter();
        final JSONWriter jsonWriter = new JSONWriter(stringWriter);
        jsonWriter.write(new char[]{'c', 'h', 'a', 'r', 'A', 'r', 'r', 'a', 'y'});
        assertEquals("\"charArray\"", stringWriter.toString());
    }

    @Test
    public void writeNullCharArray() throws Exception {
        StringWriter stringWriter = new StringWriter();
        final JSONWriter jsonWriter = new JSONWriter(stringWriter);
        jsonWriter.write((char[]) null);
        assertEquals("null", stringWriter.toString());
    }

    @Test
    public void writeString() throws Exception {
        StringWriter stringWriter = new StringWriter();
        final JSONWriter jsonWriter = new JSONWriter(stringWriter);
        jsonWriter.write("string");
        assertEquals("\"string\"", stringWriter.toString());
    }

    @Test
    public void writeStringWithSpecialSymbol() throws Exception {
        StringWriter stringWriter = new StringWriter();
        final JSONWriter jsonWriter = new JSONWriter(stringWriter);
        jsonWriter.write("\"\\/\b\f\n\r\t");
        assertEquals("\"\\\"\\\\\\/\\b\\f\\n\\r\\t\"", stringWriter.toString());
    }

    @Test(expected = IOException.class)
    public void writeStringTwice() throws Exception {
        StringWriter stringWriter = new StringWriter();
        final JSONWriter jsonWriter = new JSONWriter(stringWriter);
        jsonWriter.write("string");
        assertEquals("\"string\"", stringWriter.toString());
        jsonWriter.write("string2");
    }

    @Test
    public void writeNullString() throws Exception {
        StringWriter stringWriter = new StringWriter();
        final JSONWriter jsonWriter = new JSONWriter(stringWriter);
        jsonWriter.write((String) null);
        assertEquals("null", stringWriter.toString());
    }

    @Test
    public void writeNullJSONObject() throws Exception {
        StringWriter stringWriter = new StringWriter();
        JSONWriter jsonWriter = new JSONWriter(stringWriter);
        jsonWriter.write((JSONObject) null);
        assertEquals("null", stringWriter.toString());
    }

    @Test
    public void writeJSONObject() throws Exception {
        StringWriter stringWriter = new StringWriter();
        final JSONWriter jsonWriter = new JSONWriter(stringWriter);
        jsonWriter.write(new TestJSONObject());
        assertEquals("{\"c\":\"c\",\"d\":1.23,\"i\":1,\"object\":null,\"s\":\"string\"}", stringWriter.toString());
    }

    @Test
    public void writeJSONObjectWithSelfReference() throws Exception {
        StringWriter stringWriter = new StringWriter();
        final JSONWriter jsonWriter = new JSONWriter(stringWriter);
        final TestJSONObject testJSONObject = new TestJSONObject();
        testJSONObject.setObject(testJSONObject);
        jsonWriter.write(testJSONObject);
        assertEquals("{\"c\":\"c\",\"d\":1.23,\"i\":1,\"object\":$,\"s\":\"string\"}", stringWriter.toString());
    }

    @Test
    public void writeJSONObjectWithCircularReference() throws Exception {
        StringWriter stringWriter = new StringWriter();
        final JSONWriter jsonWriter = new JSONWriter(stringWriter);
        final TestJSONObject testJSONObject = new TestJSONObject();
        final TestJSONObject testJSONObject2 = new TestJSONObject('d', 5.5, 55, null, "sadf");
        testJSONObject2.setObject(testJSONObject2);
        testJSONObject.setObject(testJSONObject2);
        jsonWriter.write(testJSONObject);
        assertEquals("{\"c\":\"c\",\"d\":1.23,\"i\":1,\"object\":{\"c\":\"d\",\"d\":5.5,\"i\":55,\"object\":$#object,\"s\":\"sadf\"},\"s\":\"string\"}", stringWriter.toString());
    }

    @Test
    public void writeJSONObjectWithCircularReferenceSameValueOfObject() throws Exception {
        StringWriter stringWriter = new StringWriter();
        final JSONWriter jsonWriter = new JSONWriter(stringWriter);
        final TestJSONObject testJSONObject = new TestJSONObject();
        final TestJSONObject testJSONObject2 = new TestJSONObject();
        testJSONObject2.setObject(testJSONObject2);
        testJSONObject.setObject(testJSONObject2);
        jsonWriter.write(testJSONObject);
        assertEquals("{\"c\":\"c\",\"d\":1.23,\"i\":1,\"object\":$,\"s\":\"string\"}", stringWriter.toString());
    }

    @Test
    public void writeCustomJSONObject() throws Exception {
        StringWriter stringWriter = new StringWriter();
        final JSONWriter jsonWriter = new JSONWriter(stringWriter);
        jsonWriter.write(new JSONObject() {
            private int a = 1;
            private int b = 2;

            @Override
            public void writeJSONObject(JSONObjectWriter jsonObjectWriter) throws IOException {
                jsonObjectWriter.writePair("c", a);
            }
        });
        assertEquals("{\"c\":1}", stringWriter.toString());
    }

    @Test
    public void writeShortArray() throws Exception {
        StringWriter stringWriter = new StringWriter();
        final JSONWriter jsonWriter = new JSONWriter(stringWriter);
        final JSONArray jsonArray = new JSONArray(new short[]{1, 2, 3, 4});
        jsonWriter.write(jsonArray);
        assertEquals("[1,2,3,4]", stringWriter.toString());
    }

    @Test
    public void writeIntegerArray() throws Exception {
        StringWriter stringWriter = new StringWriter();
        final JSONWriter jsonWriter = new JSONWriter(stringWriter);
        final JSONArray jsonArray = new JSONArray(new int[]{1, 2, 3, 4});
        jsonWriter.write(jsonArray);
        assertEquals("[1,2,3,4]", stringWriter.toString());
    }

    @Test
    public void writeLongArray() throws Exception {
        StringWriter stringWriter = new StringWriter();
        final JSONWriter jsonWriter = new JSONWriter(stringWriter);
        final JSONArray jsonArray = new JSONArray(new long[]{1, 2, 3, 4});
        jsonWriter.write(jsonArray);
        assertEquals("[1,2,3,4]", stringWriter.toString());
    }

    @Test
    public void writeBooleanArray() throws Exception {
        StringWriter stringWriter = new StringWriter();
        final JSONWriter jsonWriter = new JSONWriter(stringWriter);
        final JSONArray jsonArray = new JSONArray(new boolean[]{true, false, true, true, false});
        jsonWriter.write(jsonArray);
        assertEquals("[true,false,true,true,false]", stringWriter.toString());
    }

    @Test
    public void writeCharArray() throws Exception {
        StringWriter stringWriter = new StringWriter();
        final JSONWriter jsonWriter = new JSONWriter(stringWriter);
        final JSONArray jsonArray = new JSONArray(new char[]{'a', 'r', 'r', 'a', 'y'});
        jsonWriter.write(jsonArray);
        assertEquals("[\"a\",\"r\",\"r\",\"a\",\"y\"]", stringWriter.toString());
    }

    @Test
    public void writeDoubleArray() throws Exception {
        StringWriter stringWriter = new StringWriter();
        final JSONWriter jsonWriter = new JSONWriter(stringWriter);
        final JSONArray jsonArray = new JSONArray(new double[]{1.1, 2.1, 3.1, 4.1});
        jsonWriter.write(jsonArray);
        assertEquals("[1.1,2.1,3.1,4.1]", stringWriter.toString());
    }

    @Test
    public void writeFloutArray() throws Exception {
        StringWriter stringWriter = new StringWriter();
        final JSONWriter jsonWriter = new JSONWriter(stringWriter);
        final JSONArray jsonArray = new JSONArray(new float[]{1.1f, 2.1f, 3.1f, 4.1f});
        jsonWriter.write(jsonArray);
        assertEquals("[1.1,2.1,3.1,4.1]", stringWriter.toString());
    }

    @Test
    public void writeStringArray() throws Exception {
        StringWriter stringWriter = new StringWriter();
        final JSONWriter jsonWriter = new JSONWriter(stringWriter);
        final JSONArray jsonArray = new JSONArray(new String[]{"123", "456", "789"});
        jsonWriter.write(jsonArray);
        assertEquals("[\"123\",\"456\",\"789\"]", stringWriter.toString());
    }

    private class StringWriter extends Writer {
        private boolean closed = false;
        private StringBuilder stringBuilder = new StringBuilder();

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

        @Override
        public String toString() {
            return this.stringBuilder.toString();
        }
    }
}