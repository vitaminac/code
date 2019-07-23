package code.adt.dict;

import code.adt.map.Map;

import java.util.function.Consumer;

public interface Dictionary<Key extends Comparable<Key>, Value> extends Map<Key, Value> {
    default void findRange(Key min, Key max, Consumer<Key> consumer) {
        this.enumerate(key -> {
            if (key.compareTo(min) >= 0 && key.compareTo(max) <= 0) {
                consumer.accept(key);
            }
        });
    }
}