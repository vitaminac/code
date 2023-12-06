package code.adt.queue.simulation;

import collections.map.MutableMap;
import collections.hashtable.SeparateChainingHashTable;

public class Atom {
    private final MutableMap<String, Object> map = MutableMap.fromHashTable(SeparateChainingHashTable::new);

    public void save(String name, Object value) {
        this.map.put(name, value);
    }

    public Object get(String name) {
        return this.map.get(name);
    }
}