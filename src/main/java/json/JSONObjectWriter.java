package json;

import java.io.IOException;
import java.util.Map.Entry;
import java.util.TreeMap;

public class JSONObjectWriter {
    private final JSONWriter jsonWriter;
    private final TreeMap<String, JSONValue> members = new TreeMap<>();
    private static final String DELIMITER = ",";

    public JSONObjectWriter(JSONWriter jsonWriter) {
        this.jsonWriter = jsonWriter;
    }

    public void writePair(String key, JSONObject jsonObject) throws IOException {
        this.writePair(key, new JSONValue(jsonObject));
    }

    public void writePair(String key, int i) throws IOException {
        this.writePair(key, new JSONValue(i));
    }

    public void writePair(String key, JSONValue jsonValue) throws IOException {
        this.members.put(key, jsonValue);
    }

    public void writePair(String key, String string) throws IOException {
        this.writePair(key, new JSONValue(string));
    }

    public void close() throws IOException {
        Entry<String, JSONValue> firstEntry = this.members.firstEntry();
        String key;
        JSONValue value;
        if (firstEntry != null) {
            key = firstEntry.getKey();
            value = firstEntry.getValue();
            this.writeKeyPart(key);
            this.jsonWriter.pushLocation(key);
            this.jsonWriter.writeJSONValue(value);
            this.jsonWriter.popLocation();
            this.members.remove(key);
        }
        for (Entry<String, JSONValue> entry : this.members.entrySet()) {
            this.jsonWriter.writeOutput(DELIMITER);
            key = entry.getKey();
            value = entry.getValue();
            this.writeKeyPart(key);
            this.jsonWriter.pushLocation(key);
            this.jsonWriter.writeJSONValue(value);
            this.jsonWriter.popLocation();
        }
    }

    private void writeSeparator() throws IOException {
        this.jsonWriter.writeOutput(",");
    }

    private void writeKeyPart(String key) throws IOException {
        this.jsonWriter.writeJSONValue(new JSONValue(key));
        this.jsonWriter.writeOutput(":");
    }
}
