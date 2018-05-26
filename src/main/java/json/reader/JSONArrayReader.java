package json.reader;

import java.io.IOException;

public class JSONArrayReader implements IJSONReader {
    private final JSONRawReader rawReader;

    public JSONArrayReader(JSONRawReader rawReader) throws IOException {
        this.rawReader = rawReader;
        assert this.rawReader.match('[');
    }

    @Override
    public void close() throws IOException {
        assert this.rawReader.match(']');
    }

    @Override
    public JSONArrayReader getJSONArrayReader() throws IOException {
        return new JSONArrayReader(this.rawReader);
    }

    @Override
    public JSONObjectReader getJSONObjectReader() throws IOException {
        return new JSONObjectReader(this.rawReader);
    }
}
