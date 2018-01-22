package json;

import java.io.IOException;

public class JSONObjectWriter {
    private final JSONWriter jsonWriter;
    private int membersCount = 0;

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
        if (this.membersCount > 0) {
            this.jsonWriter.writeOutput(",");
        }
        this.writeKeyPart(key);
        this.jsonWriter.pushLocation(key);
        this.jsonWriter.writeJSONValue(jsonValue);
        this.jsonWriter.popLocation();
        ++this.membersCount;
    }

    public void writePair(String key, String string) throws IOException {
        this.writePair(key, new JSONValue(string));
    }

    private void writeSeparator() throws IOException {
        this.jsonWriter.writeOutput(",");
    }

    private void writeKeyPart(String key) throws IOException {
        this.jsonWriter.writeJSONValue(new JSONValue(key));
        this.jsonWriter.writeOutput(":");
    }
}
