package code.adt.queue.simulation;

import collections.hashtable.SeparateChainingHashTable;
import collections.map.MutableMap;
import collections.set.MutableSet;

import java.util.HashMap;
import java.util.Map;

public class Consumer extends Component {
    public Consumer() {
        super(null);
    }

    private final MutableSet<Atom> bag = MutableSet.fromHashTable(SeparateChainingHashTable::new);

    @Override
    public void enter(Atom atom) {
        this.onEnter(atom);
        this.bag.add(atom);
    }

    @Override
    public boolean canEnter() {
        return true;
    }

    @Override
    public void addOutput(Component component) {
        throw new RuntimeException();
    }

    public MutableSet<Atom> getAtoms() {
        return this.bag;
    }

    public double mean(String name) {
        double sum = 0;
        for (var atom : bag) {
            sum += (Double) atom.get(name);
        }
        return sum / this.bag.size();
    }

    public Map<Object, Integer> count(String name) {
        Map<Object, Integer> map = new HashMap<>(); // TODO
        for (var atom : bag) {
            var value = atom.get(name);
            map.put(value, map.getOrDefault(value, 0) + 1);
        }
        return map;
    }
}