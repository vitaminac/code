package converter;

import java.lang.reflect.ParameterizedType;

public interface TypeSpecificConverter<Source, Destination> extends RawConverter {
    private boolean isSourceType(Class<?> clazz) {
        return clazz.equals(((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
    }

    private boolean isDestinationType(Class<?> clazz) {
        return clazz.equals(((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1]);
    }

    @Override
    default boolean isSupported(Class<?> sourceType, Class<?> destinationType) {
        return this.isSourceType(sourceType) && this.isDestinationType(destinationType);
    }

    default <T> T convert(Object source, Class<T> destinationType) {
        return (T) this.convert((Source) source);
    }

    Destination convert(Source source);
}