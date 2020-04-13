package core.dict;

import core.map.Map;

import java.util.function.Consumer;

public interface Dictionary<Key extends Comparable<Key>, Value> extends Map<Key, Value> {
    default void findRange(Key min, Key max, Consumer<Key> consumer) {
        this.forEach(key -> {
            if (key.compareTo(min) >= 0 && key.compareTo(max) <= 0) {
                consumer.accept(key);
            }
        });
    }
}