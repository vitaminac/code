package converter;

import json.JSON;
import json.JsonSerializable;

public class ObjectToJsonConverter implements Converter {
    @Override
    public boolean canSupport(Class<?> sourceType, Class<?> destinationType) {
        return sourceType.isAnnotationPresent(JsonSerializable.class) && destinationType.equals(String.class);
    }

    @Override
    public <T> T convert(Object source, Class<T> destinationType) {
        return (T) JSON.stringify(source);
    }
}
