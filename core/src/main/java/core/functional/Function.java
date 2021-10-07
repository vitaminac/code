package core.functional;

@FunctionalInterface
public interface Function<INPUT, OUTPUT, EXCEPTION extends Throwable> {
    OUTPUT callback(INPUT value) throws EXCEPTION;
}
