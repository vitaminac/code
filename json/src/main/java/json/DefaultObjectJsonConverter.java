package json;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

public class DefaultObjectJsonConverter implements JsonConverter<Object, Map<String, Object>> {
    @Override
    public Map<String, Object> stringify(Object obj) {
        return Arrays.stream(obj.getClass().getDeclaredFields())
                .filter(field -> !field.isAnnotationPresent(JsonIgnore.class))
                .collect(LinkedHashMap<String, Object>::new, (map, field) -> {
                    field.setAccessible(true);
                    try {
                        map.put(field.getName(), field.get(obj));
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException("Failed to get value with reflection", e);
                    }
                }, LinkedHashMap::putAll);
    }

    @Override
    public Object parse(Map<String, Object> from, Class<Object> toClazz) {
        Object o;
        try {
            o = toClazz.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException("Unable to create " + toClazz.getSimpleName() + " instance with default constructor");
        }
        for (Field field : toClazz.getDeclaredFields()) {
            if (!field.isAnnotationPresent(JsonIgnore.class)) {
                field.setAccessible(true);
                try {
                    field.set(o, from.get(field.getName()));
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("Failed to get value with reflection", e);
                }
            }
        }
        return o;
    }
}
