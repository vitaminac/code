package json;

public class MalformedJSONInput extends Exception {
    public MalformedJSONInput(char token, int position) {
        super("Unexpected token " + token + " in JSON at position " + position);
    }
}
