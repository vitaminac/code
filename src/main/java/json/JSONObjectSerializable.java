package json;

import java.io.IOException;
import java.lang.reflect.Field;

public interface JSONObjectSerializable extends JSONSerializable {
    private void defaultWriteJSONObject(JSONWriter jsonWriter) throws IOException {
        jsonWriter.writeOutput("{");
        for (Field field : this.getClass().getDeclaredFields()) {
            try {
                field.setAccessible(true);
                Object o = field.get(this);
                if (JSONSerializable.isJSONSerializable(o)) {
                    String key = field.getName();
                    Object value = field.get(this);
                    if (o instanceof JSONSerializable) {
                        jsonWriter.writePair(key, (JSONSerializable) value);
                    } else {
                        jsonWriter.writePair(key, value);
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        jsonWriter.writeOutput("}");
    }

    @Override
    default void writeJSON(JSONWriter jsonWriter) throws IOException {
        this.defaultWriteJSONObject(jsonWriter);
    }
}
