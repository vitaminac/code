package converter;

import java.lang.reflect.ParameterizedType;

public interface TypeSpecificConverter<Source, Destination> extends Converter {
    private boolean canSupportSourceType(Class<?> clazz) {
        return ((Class<?>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]).isAssignableFrom(clazz);
    }

    private boolean canSupportDestinationType(Class<?> clazz) {
        return clazz.isAssignableFrom((Class<?>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1]);
    }

    @Override
    default boolean canSupport(Class<?> sourceType, Class<?> destinationType) {
        return this.canSupportSourceType(sourceType) && this.canSupportDestinationType(destinationType);
    }

    @SuppressWarnings("unchecked")
    @Override
    default <T> T convert(Object source, Class<T> destinationType) {
        return (T) this.convert((Source) source);
    }

    Destination convert(Source source);
}