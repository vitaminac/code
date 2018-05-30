package json.reader;

import json.JSON;
import json.JSONRestoreFactory;
import json.MalformedJSONInput;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

public class JSONReader {
    private Reader underlyingReader;

    public JSONReader(Reader underlyingReader) {
        this.underlyingReader = underlyingReader;
    }

    public <T extends JSON> T getObject(JSONRestoreFactory<T> factory) throws IOException, MalformedJSONInput {
        return this.readValue().getObject(factory);
    }

    public boolean readBoolean() throws IOException, MalformedJSONInput {
        return this.readValue().getBoolean();
    }

    public double readNumber() throws IOException, MalformedJSONInput {
        return this.readValue().getNumber();
    }

    public String readString() throws IOException, MalformedJSONInput {
        return this.readValue().getString();
    }

    private JSONValue readValue() throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader buffer = new BufferedReader(this.underlyingReader)) {
            String line;
            while ((line = buffer.readLine()) != null) {
                sb.append(line);
            }
        }
        return new JSONValue(sb.toString());
    }
}
