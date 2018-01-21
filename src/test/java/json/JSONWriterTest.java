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
    public void writeNullJSONObject() throws Exception {
        StringWriter stringWriter = new StringWriter();
        JSONWriter jsonWriter = new JSONWriter(stringWriter);
        jsonWriter.write((JSONSerializable) null);
        assertEquals("null", stringWriter.toString());
    }

    @Test
    public void writeJSONObject() throws Exception {
        StringWriter stringWriter = new StringWriter();
        final JSONWriter jsonWriter = new JSONWriter(stringWriter);
        jsonWriter.write(new TestJSONObject());
        assertEquals("{\"i\":1,\"d\":1.23,\"c\":\"c\",\"s\":\"string\"}", stringWriter.toString());
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