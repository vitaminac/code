package core.ioc;

public class DuplicateDefinitionException extends RuntimeException {
    public DuplicateDefinitionException(Object id) {
        super("Duplicate definition for " + id);
    }
}
