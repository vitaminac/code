package oop.serializable;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

public interface Serializable<T extends Serializable<T>> {
    void write(Writer writer) throws IOException;

    default String serialize() {
        try (StringWriter writer = new StringWriter()) {
            this.write(writer);
            return writer.toString();
        } catch (IOException e) {
            return null;
        }
    }

    void read(Reader reader) throws IOException;

    default void deserialize(String text) {
        try {
            this.read(new StringReader(text));
        } catch (IOException e) {
        }
    }
}
