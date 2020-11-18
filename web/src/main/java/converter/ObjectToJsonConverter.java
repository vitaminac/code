package converter;

import json.JSON;
import json.JsonSerializable;

public class ObjectToJsonConverter implements RawConverter {
    @Override
    public boolean isSupported(Class<?> sourceType, Class<?> destinationType) {
        return sourceType.isAnnotationPresent(JsonSerializable.class) && destinationType.equals(String.class);
    }

    @Override
    public <T> T convert(Object source, Class<T> destinationType) {
        return (T) JSON.stringify(source);
    }
}
