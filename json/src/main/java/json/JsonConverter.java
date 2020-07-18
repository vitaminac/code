package json;

public abstract class JsonConverter<T> {
    public abstract String stringify(T obj);
}
