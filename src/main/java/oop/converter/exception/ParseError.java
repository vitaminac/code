package oop.converter.exception;

public class ParseError extends Exception {
    @Override
    public String getMessage() {
        return "Malformed Text";
    }
}
