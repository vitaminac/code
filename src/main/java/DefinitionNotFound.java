public class DefinitionNotFound extends Exception {
    public DefinitionNotFound(Class<?> type) {
        super(type.getName() + " not found in the application context");
    }
}
