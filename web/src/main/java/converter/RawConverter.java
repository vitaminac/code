package converter;

public interface RawConverter {
    boolean isSupported(Class<?> sourceType, Class<?> destinationType);

    <T> T convert(Object source, Class<T> destinationType);
}
