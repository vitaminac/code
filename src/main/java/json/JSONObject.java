package json;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public interface JSONObject extends JSONSerializable {
    default void writeJSONObject(JSONObjectWriter jsonObjectWriter) throws IOException {
        final List<Field> fields = Arrays.asList(this.getClass().getDeclaredFields());
        fields.sort(Comparator.comparing(Field::getName));
        for (Field field : fields) {
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
        final JSONObjectWriter jsonObjectWriter = new JSONObjectWriter(jsonWriter);
        this.writeJSONObject(jsonObjectWriter);
        jsonObjectWriter.close();
        jsonWriter.writeOutput("}");
    }
}
