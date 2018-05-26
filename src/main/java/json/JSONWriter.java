package json;

import java.io.IOException;
import java.io.Writer;

/**
 * Facade class
 */
public class JSONWriter {
    private final JSONRawWriter writer;

    public JSONWriter(Writer underlyingWriter) {
        this.writer = new JSONRawWriter(underlyingWriter);
    }

    public void close() throws IOException {
        this.writer.close();
    }

    public void write(Number number) throws IOException {
        this.writer.write(number);
        this.close();
    }

    public void write(boolean bool) throws IOException {
        writer.write(bool);
        this.close();
    }

    public void write(boolean arr[]) throws IOException {
        if (arr == null) {
            this.writer.writeNull();
        } else {
            try (JSONArrayWriter writer = this.writer.getJSONArrayWriter()) {
                writer.write(arr);
            }
        }
        this.close();
    }

    public void write(char arr[]) throws IOException {
        if (arr == null) {
            this.writer.writeNull();
        } else {
            try (JSONArrayWriter writer = this.writer.getJSONArrayWriter()) {
                writer.write(arr);
            }
        }
        this.close();
    }

    public void write(char c) throws IOException {
        this.writer.write(String.valueOf(c));
    }

    public void write(String str) throws IOException {
        writer.write(str);
        this.close();
    }

    public void write(JSON value) throws IOException {
        this.writer.write(value);
        this.close();
    }

    public void write(double[] arr) throws IOException {
        if (arr == null) {
            this.writer.writeNull();
        } else {
            try (JSONArrayWriter writer = this.writer.getJSONArrayWriter()) {
                writer.write(arr);
            }
        }
        this.close();
    }

    public void write(float[] arr) throws IOException {
        if (arr == null) {
            this.writer.writeNull();
        } else {
            try (JSONArrayWriter writer = this.writer.getJSONArrayWriter()) {
                writer.write(arr);
            }
        }
        this.close();
    }

    public void write(short[] arr) throws IOException {
        if (arr == null) {
            this.writer.writeNull();
        } else {
            try (JSONArrayWriter writer = this.writer.getJSONArrayWriter()) {
                writer.write(arr);
            }
        }
        this.close();
    }

    public void write(int[] arr) throws IOException {
        if (arr == null) {
            this.writer.writeNull();
        } else {
            try (JSONArrayWriter writer = this.writer.getJSONArrayWriter()) {
                writer.write(arr);
            }
        }
        this.close();
    }

    public void write(long[] arr) throws IOException {
        if (arr == null) {
            this.writer.writeNull();
        } else {
            try (JSONArrayWriter writer = this.writer.getJSONArrayWriter()) {
                writer.write(arr);
            }
        }
        this.close();
    }

    public void write(String[] arr) throws IOException {
        if (arr == null) {
            this.writer.writeNull();
        } else {
            try (JSONArrayWriter writer = this.writer.getJSONArrayWriter()) {
                writer.write(arr);
            }
        }
        this.close();
    }
}
