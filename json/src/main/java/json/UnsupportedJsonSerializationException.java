package json;

public class UnsupportedJsonSerializationException extends RuntimeException {
    public UnsupportedJsonSerializationException(Class<?> clazz) {
        super(clazz.getCanonicalName() + " is not supported for json serialization");
    }
}
