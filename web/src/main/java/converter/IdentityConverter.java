package converter;

public class IdentityConverter implements Converter {

    @Override
    public boolean canSupport(Class<?> sourceType, Class<?> destinationType) {
        return sourceType.equals(destinationType);
    }

    @Override
    public <T> T convert(Object source, Class<T> destinationType) {
        return (T) source;
    }
}
