package json.writer;

import json.JSON;

import java.io.IOException;
import java.io.Writer;
import java.util.Collection;

class JSONRawWriter implements IJSONWriter {
    private final Writer underlyingWriter;

    JSONRawWriter(Writer underlyingWriter) {
        this.underlyingWriter = underlyingWriter;
    }

    public void close() throws IOException {
        this.underlyingWriter.flush();
        this.underlyingWriter.close();
    }

    @Override
    public JSONArrayWriter getJSONArrayWriter() throws IOException {
        return new JSONArrayWriter(this);
    }

    @Override
    public JSONObjectWriter getJSONObjectWriter() throws IOException {
        return new JSONObjectWriter(this);
    }

    public void write(char c) throws IOException {
        this.write(String.valueOf(c));
    }

    public void write(Collection<JSON> value) throws IOException {
        try (JSONArrayWriter writer = this.getJSONArrayWriter()) {
            writer.write(value);
        }
    }

    void write(String str) throws IOException {
        if (str == null) {
            this.underlyingWriter.write("null");
        } else {
            str = str.replaceAll("\\\\", "\\\\\\\\").replaceAll("\"", "\\\\\"").replaceAll("/", "\\\\/")
                    .replaceAll("\b", "\\\\b").replaceAll("\f", "\\\\f").replaceAll("\n", "\\\\n")
                    .replaceAll("\r", "\\\\r").replaceAll("\t", "\\\\t");
            this.underlyingWriter.write('\"');
            this.underlyingWriter.write(str);
            this.underlyingWriter.write('\"');
        }
    }

    void write(boolean bool) throws IOException {
        this.underlyingWriter.write(String.valueOf(bool));
    }

    void write(JSON value) throws IOException {
        if (value == null) {
            this.writeNull();
        } else {
            try (JSONObjectWriter writer = this.getJSONObjectWriter()) {
                value.writeJSON(writer);
            }
        }
    }

    void write(Number number) throws IOException {
        if (number == null) {
            this.writeNull();
        } else {
            this.underlyingWriter.write(number.toString());
        }
    }

    void writeNull() throws IOException {
        this.underlyingWriter.write("null");
    }

    void writeSymbol(char c) throws IOException {
        this.underlyingWriter.write(c);
    }
}
