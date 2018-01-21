package json;

import java.io.IOException;
import java.lang.reflect.Field;

public interface JSONObjectSerializable extends JSONSerializable {
    private void defaultWriteJSONObject(JSONWriter jsonWriter) throws IOException {
        jsonWriter.writeOutput("{");
        for (Field field : this.getClass().getDeclaredFields()) {
            try {
                field.setAccessible(true);
                Object value = field.get(this);
                if (JSONSerializable.isJSONSerializable(value)) {
                    String key = field.getName();
                    jsonWriter.writePair(key, JSONValue.build(value));
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (ClassIsNotJSONSerializableException e) {
                continue;
            }
        }
        jsonWriter.writeOutput("}");
    }

    @Override
    default void writeJSON(JSONWriter jsonWriter) throws IOException {
        this.defaultWriteJSONObject(jsonWriter);
    }
}
