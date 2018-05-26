package json.reader;

import json.MalformedJSONInput;
import json.reader.JSONRawReader;

import java.io.IOException;
import java.io.Reader;

public class JSONReader {
    private JSONRawReader rawReader;

    public JSONReader(Reader underlyingReader) {
        this.rawReader = new JSONRawReader(underlyingReader);
    }

    public boolean readBoolean() throws IOException, MalformedJSONInput {
        return this.rawReader.readBoolean();
    }

    public double readNumber() throws IOException, MalformedJSONInput {return rawReader.readNumber();}

    public String readString() throws IOException, MalformedJSONInput {
        return rawReader.readString();
    }
}
