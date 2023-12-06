package code.adt.queue.simulation;

import collections.map.MutableMap;
import collections.map.SeparateChainingHashTableMap;

public class Atom {
    private final MutableMap<String, Object> map = new SeparateChainingHashTableMap<>();

    public void save(String name, Object value) {
        this.map.put(name, value);
    }

    public Object get(String name) {
        return this.map.get(name);
    }
}