package code.adt.queue.simulation;

import java.util.HashMap;
import java.util.Map;

public class Atom {
    private Map<String, Object> map = new HashMap<>(); // TODO

    public void save(String name, Object value) {
        this.map.put(name, value);
    }

    public Object get(String name) {
        return this.map.get(name);
    }
}