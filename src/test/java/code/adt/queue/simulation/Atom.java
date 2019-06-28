package code.adt.queue.simulation;

import code.adt.map.HashTableMapLP;
import code.adt.map.Map;

public class Atom {
    private Map<String, Object> map = new HashTableMapLP<>();

    public void save(String name, Object value) {
        this.map.link(name, value);
    }

    public Object get(String name) {
        return this.map.map(name);
    }
}