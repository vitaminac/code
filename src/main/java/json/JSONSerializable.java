package json;


import org.apache.commons.lang3.ClassUtils;

import java.io.IOException;

public interface JSONSerializable {
    static String stringify(Object value) {
        return null;
    }

    static boolean isJSONSerializable(Object o) {
        return ClassUtils.isPrimitiveOrWrapper(o.getClass()) || o instanceof String || o instanceof JSONSerializable;
    }

    void writeJSON(JSONWriter jsonWriter) throws IOException;
}
