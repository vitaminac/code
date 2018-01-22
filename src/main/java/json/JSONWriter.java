package json;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.Writer;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.StringJoiner;

public class JSONWriter {
    private final Writer outputWriter;
    private final HashMap<JSONValue, String> set = new HashMap<>();
    private MembersCounts membersCounts = new MembersCounts();
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

    public void write(boolean b) throws IOException {
        this.write(new JSONValue(b));
    }

    public void write(JSONObjectSerializable jsonObjectSerializable) throws IOException {
        this.write(new JSONValue(jsonObjectSerializable));
    }

    public void write(JSONValue jsonValue) throws IOException {
        this.membersCounts.push(0);
        this.writeJSONValue(jsonValue);
        this.membersCounts.pop();
        this.close();
    }

    public void writePair(String key, JSONObjectSerializable jsonObjectSerializable) throws IOException {
        this.writePair(key, new JSONValue(jsonObjectSerializable));
    }

    public void writePair(String key, JSONValue jsonValue) throws IOException {
        if (this.membersCounts.peek() > 0) {
            this.writeSeparator();
        }
        this.writeKeyPart(key);
        this.referenceKey.push(key);
        this.writeJSONValue(jsonValue);
        this.referenceKey.pop();
        this.membersCounts.increment();
    }

    public void writePair(String key, String string) throws IOException {
        this.writePair(key, new JSONValue(string));
    }

    void writeOutput(String s) throws IOException {
        this.outputWriter.write(s);
    }

    void writeJSONValue(@NotNull JSONValue jsonValue) throws IOException {
        if (this.set.containsKey(jsonValue)) {
            this.writeOutput(this.set.get(jsonValue));
        } else {
            this.set.put(jsonValue, this.referenceKey.toString());
            this.membersCounts.push(0);
            jsonValue.writeJSON(this);
            this.membersCounts.pop();
        }
    }

    private void flush() throws IOException {
        this.outputWriter.flush();
    }

    private void close() throws IOException {
        this.flush();
        this.outputWriter.close();
    }

    private void writeKeyPart(String key) throws IOException {
        this.writeJSONValue(new JSONValue(key));
        this.writeOutput(":");
    }

    private void writeSeparator() throws IOException {
        this.writeOutput(",");

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

    private class MembersCounts {
        private Deque<Integer> counts = new LinkedList<>();

        private Integer peek() {
            return counts.peek();
        }

        private void push(Integer integer) {
            counts.push(integer);
        }

        private Integer pop() {
            return counts.pop();
        }

        private void increment() {
            this.push(this.pop() + 1);
        }
    }
}
