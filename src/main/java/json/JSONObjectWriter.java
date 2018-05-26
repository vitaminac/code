package json;

import java.io.IOException;
import java.util.Collection;

public class JSONObjectWriter implements IJSONWriter {
    private final JSONRawWriter writer;
    private boolean separator = false;

    JSONObjectWriter(JSONRawWriter writer) throws IOException {
        this.writer = writer;
        this.writer.writeSymbol('{');
    }

    @Override
    public JSONObjectWriter getJSONObjectWriter() throws IOException {
        return new JSONObjectWriter(this.writer);
    }

    @Override
    public JSONArrayWriter getJSONArrayWriter() throws IOException {
        return new JSONArrayWriter(this.writer);
    }

    @Override
    public void close() throws IOException {
        this.writer.writeSymbol('}');
    }

    public void write(String key, JSON value) throws IOException {
        this.writeSeparatorIfNecessary();
        this.writer.write(key);
        this.writer.writeSymbol(':');
        this.writer.write(value);
    }

    public void write(String key, Number value) throws IOException {
        this.writeSeparatorIfNecessary();
        this.writer.write(key);
        this.writer.writeSymbol(':');
        this.writer.write(value);
    }

    public void write(String key, boolean value) throws IOException {
        this.writeSeparatorIfNecessary();
        this.writer.write(key);
        this.writer.writeSymbol(':');
        this.writer.write(value);
    }

    public void write(String key, char value) throws IOException {
        this.writeSeparatorIfNecessary();
        this.writer.write(key);
        this.writer.writeSymbol(':');
        this.writer.write(value);
    }

    public void write(String key, String value) throws IOException {
        this.writeSeparatorIfNecessary();
        this.writer.write(key);
        this.writer.writeSymbol(':');
        this.writer.write(value);
    }

    public void write(String key, Collection<JSON> value) throws IOException {
        this.writeSeparatorIfNecessary();
        this.writer.write(key);
        this.writer.writeSymbol(':');
        this.writer.write(value);
    }

    private void writeSeparatorIfNecessary() throws IOException {
        if (this.separator) {
            this.writer.writeSymbol(',');
        } else {
            this.separator = true;
        }
    }
}
