package error;

public class LoadDefinitionException extends RuntimeException {
    public LoadDefinitionException(Exception e) {
        super("Exception " + e + " occurred when loading the definitions");
    }

    public LoadDefinitionException(String message) {
        super(message);
    }
}
