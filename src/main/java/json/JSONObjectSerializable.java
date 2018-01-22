package json;

import java.io.IOException;
import java.lang.reflect.Field;

public interface JSONObjectSerializable extends JSONSerializable {
    default void writeJSONObject(JSONObjectWriter jsonObjectWriter) throws IOException {
        for (Field field : this.getClass().getDeclaredFields()) {
            try {
                field.setAccessible(true);
                Object value = field.get(this);
                String key = field.getName();
                jsonObjectWriter.writePair(key, JSONValue.build(value));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (ClassIsNotJSONSerializableException e) {
                continue;
            }
        }
    }

    @Override
    default void writeJSON(JSONWriter jsonWriter) throws IOException {
        jsonWriter.writeOutput("{");
        this.writeJSONObject(new JSONObjectWriter(jsonWriter));
        jsonWriter.writeOutput("}");
    }
}
