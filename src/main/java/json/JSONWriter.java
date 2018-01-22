package json;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.StringJoiner;

public class JSONWriter {
    private final Writer outputWriter;
    private final HashMap<JSONValue, String> set = new HashMap<>();
    private JSONReferenceKey referenceKey = new JSONReferenceKey();

    public JSONWriter(Writer writer) {
        this.outputWriter = writer;
    }

    public void write(int c) throws IOException {
        this.write(new JSONValue(c));
    }

    public void write(char[] cbuf) throws IOException {
        if (cbuf == null) {
            this.write(new JSONValue());
        } else {
            this.write(String.valueOf(cbuf));
        }
    }

    public void write(String str) throws IOException {
        this.write(new JSONValue(str));
    }

    public void write(char c) throws IOException {
        this.write(new JSONValue(c));
    }

    public void write(double d) throws IOException {
        this.write(new JSONValue(d));
    }

    public void write(float f) throws IOException {
        this.write(new JSONValue(f));
    }

    public void write(boolean b) throws IOException {
        this.write(new JSONValue(b));
    }

    public void write(JSONObject jsonObject) throws IOException {
        this.write(new JSONValue(jsonObject));
    }

    public void write(JSONArray jsonArray) throws IOException {
        this.write(new JSONValue(jsonArray));
    }

    public void write(JSONValue jsonValue) throws IOException {
        this.writeJSONValue(jsonValue);
        this.close();
    }

    void pushLocation(String location) {
        this.referenceKey.push(location);
    }

    void popLocation() {
        this.referenceKey.pop();
    }

    void writeOutput(String s) throws IOException {
        this.outputWriter.write(s);
    }

    void writeJSONValue(@NotNull JSONValue jsonValue) throws IOException {
        if (this.set.containsKey(jsonValue)) {
            this.writeOutput(this.set.get(jsonValue));
        } else {
            this.set.put(jsonValue, this.getLocation());
            jsonValue.writeJSON(this);
        }
    }

    private String getLocation() {
        return this.referenceKey.toString();
    }

    private void flush() throws IOException {
        this.outputWriter.flush();
    }

    private void close() throws IOException {
        this.flush();
        this.outputWriter.close();
    }

    private class JSONReferenceKey {
        private LinkedList<String> keys = new LinkedList<>();

        public JSONReferenceKey() {
            this.push("$");
        }

        public void push(String s) {
            keys.push(s);
        }

        public String pop() {
            return keys.pop();
        }

        @Override
        public String toString() {
            StringJoiner stringJoiner = new StringJoiner("#");
            return String.join("#", this.keys.toArray(new String[this.keys.size()]));
        }
    }
}
