package core.map;

import core.tree.SearchTree;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class TreeMap<Key extends Comparable<? super Key>, Value>
        implements OrderedMap<Key, Value> {
    private final Supplier<SearchTree<Key, Value>> supplier;
    private SearchTree<Key, Value> tree;

    public TreeMap(final Supplier<SearchTree<Key, Value>> treeSupplier) {
        this.supplier = treeSupplier;
        this.clear();
    }

    @Override
    public int size() {
        return this.tree.size();
    }

    @Override
    public boolean isEmpty() {
        return this.tree.isEmpty();
    }

    @Override
    public void put(Key key, Value value) {
        this.tree.insert(key, value);
    }

    @Override
    public Value get(Key key) {
        return this.tree.search(key);
    }

    @Override
    public void remove(Key key) {
        this.tree.remove(key);
    }

    @Override
    public void clear() {
        this.tree = this.supplier.get();
    }

    @Override
    public void enumerate(Consumer<? super Key> consumer) {
        this.tree.forEach(consumer);
    }
}
