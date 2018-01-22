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

    @Test(expected = IOException.class)
    public void writeIntAgain() throws Exception {
        StringWriter stringWriter = new StringWriter();
        final JSONWriter jsonWriter = new JSONWriter(stringWriter);
        jsonWriter.write(123456);
        assertEquals("123456", stringWriter.toString());
        jsonWriter.write(22);
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
    public void writeCharArray() throws Exception {
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
        jsonWriter.write((JSONObjectSerializable) null);
        assertEquals("null", stringWriter.toString());
    }

    @Test
    public void writeJSONObject() throws Exception {
        StringWriter stringWriter = new StringWriter();
        final JSONWriter jsonWriter = new JSONWriter(stringWriter);
        jsonWriter.write(new TestJSONObject());
        assertEquals("{\"i\":1,\"d\":1.23,\"c\":\"c\",\"s\":\"string\",\"object\":null}", stringWriter.toString());
    }

    @Test
    public void writeJSONObjectWithCircularReference() throws Exception {
        StringWriter stringWriter = new StringWriter();
        final JSONWriter jsonWriter = new JSONWriter(stringWriter);
        final TestJSONObject testJSONObject = new TestJSONObject();
        testJSONObject.setObject(testJSONObject);
        jsonWriter.write(testJSONObject);
        assertEquals("{\"i\":1,\"d\":1.23,\"c\":\"c\",\"s\":\"string\",\"object\":$}", stringWriter.toString());
    }

    @Test
    public void writeCustomJSONObject() throws Exception {
        StringWriter stringWriter = new StringWriter();
        final JSONWriter jsonWriter = new JSONWriter(stringWriter);
        jsonWriter.write(new JSONObjectSerializable() {
            private int a = 1;
            private int b = 2;

            @Override
            public void writeJSONObject(JSONObjectWriter jsonObjectWriter) throws IOException {
                jsonObjectWriter.writePair("c", a);
            }
        });
        assertEquals("{\"c\":1}", stringWriter.toString());
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