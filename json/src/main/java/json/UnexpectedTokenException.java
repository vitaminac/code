package json;

public class UnexpectedTokenException extends RuntimeException {
    // TODO: removed
    public UnexpectedTokenException(char token, int position) {
        this(String.valueOf(token), position);
    }

    public UnexpectedTokenException(String token, int position) {
        super("Unexpected token " + token + " in JSON at position " + position);
    }
}
