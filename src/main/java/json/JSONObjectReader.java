package json;

import java.io.IOException;

public class JSONObjectReader implements IJSONReader {
    private final JSONRawReader rawReader;

    public JSONObjectReader(JSONRawReader rawReader) throws IOException {
        this.rawReader = rawReader;
        assert this.rawReader.match('{');
    }

    @Override
    public void close() throws IOException {
        assert this.rawReader.match('}');
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
