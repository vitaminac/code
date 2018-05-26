package json;

import java.io.IOException;

interface IJSONWriter extends AutoCloseable {
    JSONObjectWriter getJSONObjectWriter() throws IOException;

    JSONArrayWriter getJSONArrayWriter() throws IOException;

    void close() throws IOException;
}
