package converter;

public class ConversionService {
    private final RawConverter[] converters;

    public ConversionService(RawConverter... converters) {
        this.converters = converters;
    }

    public <T> T convert(Object source, Class<T> destinationType) {
        for (var converter : this.converters) {
            if (converter.isSupported(source.getClass(), destinationType)) {
                return converter.convert(source, destinationType);
            }
        }
        throw new IllegalArgumentException("conversion from " + source.getClass().getCanonicalName() + " to " + destinationType.getCanonicalName() + " is not supported");
    }
}
