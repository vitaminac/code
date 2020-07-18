package json;

public class UnexpectedEOFException extends RuntimeException {
    public UnexpectedEOFException(int position) {
        super("Unexpected end of JSON input at position " + position);
    }
}
