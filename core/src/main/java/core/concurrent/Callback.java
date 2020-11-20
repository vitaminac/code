package core.concurrent;

public interface Callback<INPUT, OUTPUT, EXCEPTION extends Throwable> {
    OUTPUT callback(INPUT value) throws EXCEPTION;
}
