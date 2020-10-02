package ioc.error;

public class DuplicateDefinitionException extends RuntimeException {
    public DuplicateDefinitionException(Object id) {
        super("Duplicate definition for type " + id);
    }
}
