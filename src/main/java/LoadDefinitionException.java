public class LoadDefinitionException extends RuntimeException {
    public LoadDefinitionException(Exception e) {
        super("Exception " + e + " occurred when loading the definitions");
    }
}
