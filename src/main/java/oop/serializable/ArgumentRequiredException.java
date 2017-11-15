package oop.serializable;

public class ArgumentRequiredException extends Exception {
    public ArgumentRequiredException(String argument) {
        super(argument + " is required");
    }
}
