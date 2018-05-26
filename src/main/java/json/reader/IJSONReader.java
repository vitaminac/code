package json.reader;

import java.io.IOException;

public interface IJSONReader extends AutoCloseable {
    void close() throws IOException;

    JSONArrayReader getJSONArrayReader() throws IOException;

    JSONObjectReader getJSONObjectReader() throws IOException;
}
