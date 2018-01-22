package json;


import java.io.IOException;
import java.io.StringWriter;

public interface JSONSerializable {

    static String stringify(JSONValue value) {
        final StringWriter stringWriter = new StringWriter();
        final JSONWriter jsonWriter = new JSONWriter(stringWriter);
        try {
            value.writeJSON(jsonWriter);
            return stringWriter.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    void writeJSON(JSONWriter jsonWriter) throws IOException;
}
