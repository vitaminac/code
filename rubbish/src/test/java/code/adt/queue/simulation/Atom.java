package code.adt.queue.simulation;

import core.map.OpenAddressingHashTableMapLP;
import core.map.Map;

public class Atom {
    private Map<String, Object> map = new OpenAddressingHashTableMapLP<>();

    public void save(String name, Object value) {
        this.map.put(name, value);
    }

    public Object get(String name) {
        return this.map.get(name);
    }
}