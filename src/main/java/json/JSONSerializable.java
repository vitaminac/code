package json;


import org.apache.commons.lang3.ClassUtils;

import java.io.IOException;
import java.io.StringWriter;

public interface JSONSerializable {
    static boolean isJSONSerializable(Object o) {
        return ClassUtils.isPrimitiveOrWrapper(o.getClass()) || o instanceof String || o instanceof JSONSerializable;
    }

    static String stringify(JSONValue value) {
        final StringWriter stringWriter = new StringWriter();
        final JSONWriter jsonWriter = new JSONWriter(stringWriter);
        try {
            value.writeJSON(jsonWriter);
            return stringWriter.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    void writeJSON(JSONWriter jsonWriter) throws IOException;
}
