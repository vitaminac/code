public class DefinitionNotFound extends RuntimeException {
    public DefinitionNotFound(Class<?> type) {
        super(type.getName() + " not found in the application context");
    }
}
