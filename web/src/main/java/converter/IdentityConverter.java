package converter;

public class IdentityConverter implements RawConverter {

    @Override
    public boolean isSupported(Class<?> sourceType, Class<?> destinationType) {
        return sourceType.equals(destinationType);
    }

    @Override
    public <T> T convert(Object source, Class<T> destinationType) {
        return (T) source;
    }
}
