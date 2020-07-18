package json;

import java.util.Arrays;
import java.util.LinkedHashMap;

public class DefaultObjectJsonConverter extends JsonConverter<Object> {
    @Override
    public String stringify(Object obj) {
        return JSON.stringify(Arrays.stream(obj.getClass().getDeclaredFields())
                .filter(field -> !field.isAnnotationPresent(JsonIgnore.class))
                .collect(LinkedHashMap<String, Object>::new, (map, field) -> {
                    field.setAccessible(true);
                    try {
                        map.put(field.getName(), field.get(obj));
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException("Failed to get value with reflection", e);
                    }
                }, LinkedHashMap::putAll));
    }
}
