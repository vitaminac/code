package converter;

public interface Converter {
    boolean canSupport(Class<?> sourceType, Class<?> destinationType);

    <T> T convert(Object source, Class<T> destinationType);
}
