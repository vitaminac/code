package json;

public interface JsonConverter<T, R> {
    R stringify(T obj);

    T parse(R from, Class<T> toClazz);
}
