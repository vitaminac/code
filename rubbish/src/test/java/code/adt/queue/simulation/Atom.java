package code.adt.queue.simulation;

import core.map.Map;
import core.map.SeparateChainingHashTableMap;

public class Atom {
    private final Map<String, Object> map = new SeparateChainingHashTableMap<>();

    public void save(String name, Object value) {
        this.map.put(name, value);
    }

    public Object get(String name) {
        return this.map.get(name);
    }
}