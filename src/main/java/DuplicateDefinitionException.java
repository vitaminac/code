public class DuplicateDefinitionException extends Exception {
    public DuplicateDefinitionException(Class<?> type) {
        super("Duplicate definition for type " + type.getName());
    }
}
