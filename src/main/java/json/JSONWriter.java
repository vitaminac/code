package json;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.StringJoiner;

public class JSONWriter extends Writer {
    private final Writer outputWriter;
    private final HashMap<JSONSerializable, String> set = new HashMap<>();
    private MembersCounts membersCounts = new MembersCounts();
    private JSONReferenceKey referenceKey = new JSONReferenceKey();

    public JSONWriter(Writer writer) {
        this.outputWriter = writer;
    }

    @Override
    public void write(int c) throws IOException {
        this.writePrimitiveAndClose(c);
    }

    @Override
    public void write(@NotNull char[] cbuf) throws IOException {
        this.writePrimitiveAndClose(String.valueOf(cbuf));
    }

    @Override
    public void write(@NotNull char[] cbuf, int off, int len) throws IOException {
        this.write(Arrays.copyOfRange(cbuf, off, off + len));
    }

    @Override
    public void write(@NotNull String str) throws IOException {
        this.writePrimitiveAndClose(str);
    }

    @Override
    public void write(@NotNull String str, int off, int len) throws IOException {
        this.write(str.substring(off, off + len));
    }

    @Override
    public Writer append(@NotNull CharSequence csq) throws IOException {
        this.write(csq.toString());
        return this;
    }

    @Override
    public Writer append(@NotNull CharSequence csq, int start, int end) throws IOException {
        this.write(csq.subSequence(start, end).toString());
        return this;
    }

    @Override
    public Writer append(char c) throws IOException {
        this.writePrimitiveAndClose(c);
        return this;
    }

    @Override
    public void flush() throws IOException {
        this.outputWriter.flush();
    }

    @Override
    public void close() throws IOException {
        this.flush();
        this.outputWriter.close();
    }

    public void write(double d) throws IOException {
        this.writePrimitiveAndClose(d);
    }

    public void write(boolean b) throws IOException {
        this.writePrimitiveAndClose(b);
    }

    public void write(JSONSerializable jsonSerializable) throws IOException {
        if (jsonSerializable == null) {
            this.writePrimitiveAndClose(null);
        } else {
            this.membersCounts.push(0);
            this.writeJSONSerializable(jsonSerializable);
            this.close();
        }
    }

    public void writePair(String key, JSONSerializable jsonSerializable) throws IOException {
        this.writeKeyPart(key);
        if (jsonSerializable == null) {
            this.writePrimitive(null);
        } else {
            this.referenceKey.push(key);
            this.writeJSONSerializable(jsonSerializable);
            this.referenceKey.pop();
            this.membersCounts.increment();
        }
    }

    void writeOutput(String s) throws IOException {
        this.outputWriter.write(s);
    }

    void writeJSONSerializable(JSONSerializable jsonSerializable) throws IOException {
        if (this.set.containsKey(jsonSerializable)) {
            this.writeOutput(this.set.get(jsonSerializable));
        } else {
            this.set.put(jsonSerializable, this.referenceKey.toString());
            this.membersCounts.push(0);
            jsonSerializable.writeJSON(this);
            this.membersCounts.pop();
        }
    }

    void writePair(String key, Object primitive) throws IOException {
        if (this.membersCounts.peek() > 0) {
            this.writeSeparator();
        }
        this.writeKeyPart(key);
        this.writePrimitive(primitive);
        this.membersCounts.increment();
    }

    private void writeKeyPart(String key) throws IOException {
        this.writePrimitive(key);
        this.writeOutput(":");
    }

    private void writeSeparator() throws IOException {
        this.writeOutput(",");

    }

    private void writePrimitive(Object o) throws IOException {
        this.writeOutput(JSONSerializable.stringify(o));
    }

    private void writePrimitiveAndClose(Object o) throws IOException {
        this.writePrimitive(o);
        this.close();
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
