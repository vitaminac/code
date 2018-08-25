public class ServiceNotFound extends Exception {
    public ServiceNotFound(Class<?> type) {
        super(type.getName() + " not found in the application context");
    }
}
