package json;


import org.apache.commons.lang3.ClassUtils;

import java.io.IOException;

public interface JSONSerializable {
    static String stringify(Object value) {
        if (value == null) {
            return "null";
        } else if ((value instanceof String) || (value instanceof Character)) {
            return "\"" + value + "\"";
        } else {
            return String.valueOf(value);
        }
    }

    static boolean isJSONSerializable(Object o) {
        return ClassUtils.isPrimitiveOrWrapper(o.getClass()) || o instanceof String || o instanceof JSONSerializable;
    }

    void writeJSON(JSONWriter jsonWriter) throws IOException;
}
