package json;

import java.io.EOFException;

public class JSONEOFException extends EOFException {
    public JSONEOFException(int position) {
        super("Unexpected end of JSON input at position " + position);
    }
}
