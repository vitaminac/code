package json.writer;

import json.JSON;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Collection;

public class JSONArrayWriter implements IJSONWriter {
    private final JSONRawWriter writer;
    private boolean separator = false;

    JSONArrayWriter(JSONRawWriter writer) throws IOException {
        this.writer = writer;
        this.writer.writeSymbol('[');
    }

    @Override
    public void close() throws IOException {
        this.writer.writeSymbol(']');
    }

    @Override
    public JSONArrayWriter getJSONArrayWriter() throws IOException {
        return new JSONArrayWriter(this.writer);
    }

    @Override
    public JSONObjectWriter getJSONObjectWriter() throws IOException {
        return new JSONObjectWriter(this.writer);
    }

    public void write(@NotNull  Collection<? extends JSON> arr) throws IOException {
        for (JSON o : arr) {
            this.writeSeparatorIfNecessary();
            this.writer.write(o);
        }
    }

    public void write(@NotNull String[] arr) throws IOException {
        for (String o : arr) {
            this.writeSeparatorIfNecessary();
            this.writer.write(o);
        }
    }

    void write(@NotNull boolean arr[]) throws IOException {
        for (int i = 0; i < arr.length; i++) {
            this.writeSeparatorIfNecessary();
            this.writer.write(arr[i]);
        }
    }

    void write(@NotNull char[] arr) throws IOException {
        for (int i = 0; i < arr.length; i++) {
            this.writeSeparatorIfNecessary();
            this.writer.write(arr[i]);
        }
    }

    void write(@NotNull double[] arr) throws IOException {
        for (int i = 0; i < arr.length; i++) {
            this.writeSeparatorIfNecessary();
            this.writer.write(arr[i]);
        }
    }

    void write(@NotNull float[] arr) throws IOException {
        for (int i = 0; i < arr.length; i++) {
            this.writeSeparatorIfNecessary();
            this.writer.write(arr[i]);
        }
    }

    void write(@NotNull int[] arr) throws IOException {
        for (int i = 0; i < arr.length; i++) {
            this.writeSeparatorIfNecessary();
            this.writer.write(arr[i]);
        }
    }

    void write(@NotNull short[] arr) throws IOException {
        for (int i = 0; i < arr.length; i++) {
            this.writeSeparatorIfNecessary();
            this.writer.write(arr[i]);
        }
    }

    void write(@NotNull long[] arr) throws IOException {
        for (int i = 0; i < arr.length; i++) {
            this.writeSeparatorIfNecessary();
            this.writer.write(arr[i]);
        }
    }

    void write(@NotNull Number[] arr) throws IOException {
        for (int i = 0; i < arr.length; i++) {
            this.writeSeparatorIfNecessary();
            this.writer.write(arr[i]);
        }
    }

    private void writeSeparatorIfNecessary() throws IOException {
        if (this.separator) {
            this.writer.writeSymbol(',');
        } else {
            this.separator = true;
        }
    }
}
