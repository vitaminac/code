package error;

public class DuplicateDefinitionException extends RuntimeException {
    public DuplicateDefinitionException(Class<?> type) {
        super("Duplicate definition for type " + type.getName());
    }
}
