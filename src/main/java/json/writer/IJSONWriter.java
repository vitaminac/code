package json.writer;

import java.io.IOException;

interface IJSONWriter extends AutoCloseable {
    void close() throws IOException;

    JSONArrayWriter getJSONArrayWriter() throws IOException;

    JSONObjectWriter getJSONObjectWriter() throws IOException;
}
