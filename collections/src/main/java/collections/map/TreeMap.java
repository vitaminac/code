package collections.map;

import collections.tree.SearchTree;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class TreeMap<Key extends Comparable<? super Key>, Value>
        implements MutableMap<Key, Value>, OrderedMap<Key, Value> {
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
    public void put(Key domain, Value coDomain) {
        this.tree.insert(domain, coDomain);
    }

    @Override
    public Value get(Key key) {
        return this.tree.search(key);
    }

    @Override
    public void remove(Key domain) {
        this.tree.remove(domain);
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
