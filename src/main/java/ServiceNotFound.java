public class ServiceNotFound extends Exception {
    public ServiceNotFound(Class<?> clazz) {
        super(clazz.getName() + " not found in the application context");
    }
}
