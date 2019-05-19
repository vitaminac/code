package code.adt.map;

import java.util.function.Consumer;

public abstract class AbstractHashMap<Key, Value> implements Map<Key, Value> {
    private static final int DEFAULT_INITIAL_CAPACITY = 1 << 4;
    private static final double LOAD_FACTOR = 0.5;
    protected int capacity;
    private int size;
    private Relation<Key, Value>[] relations;

    @SuppressWarnings("unchecked")
    public AbstractHashMap(int capacity) {
        this.capacity = capacity;
        this.relations = new Relation[this.capacity];
    }

    public AbstractHashMap() {
        this(DEFAULT_INITIAL_CAPACITY);
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    @Override
    public void link(Key key, Value value) {
        if (((double) this.size) / this.capacity > LOAD_FACTOR) this.resize(this.capacity * 2);
        int k = this.hash(key);
        int hash = this.compress(k);
        int index = hash;
        int i = 1;
        while (this.relations[index] != null && this.relations[index] != Relation.SKIP && !this.relations[index].getKey().equals(key)) {
            index = this.compress(this.rehash(hash, k, i++));
        }
        this.relations[index] = new Relation<>(key, value);
        ++this.size;
    }

    @Override
    public Value map(Key key) {
        int k = this.hash(key);
        int hash = this.compress(k);
        int index = hash;
        int i = 1;
        while (this.relations[index] != null && (this.relations[index] == Relation.SKIP || !this.relations[index].getKey().equals(key))) {
            index = this.compress(this.rehash(hash, k, i++));
        }
        if (this.relations[index] == null) {
            return null;
        } else {
            return this.relations[index].getValue();
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public Value remove(Key key) {
        int k = this.hash(key);
        int hash = this.compress(k);
        int index = hash;
        int i = 1;
        while (index >= 0 && this.relations[index] == Relation.SKIP) {
            index = this.compress(this.rehash(hash, k, i++));
        }
        if (this.relations[index] == null) {
            return null;
        } else {
            Value value = this.relations[index].getValue();
            this.relations[index] = Relation.SKIP;
            --this.size;
            if (this.size < this.capacity / 8) this.resize(this.capacity / 2);
            return value;
        }
    }

    @Override
    public void enumerate(Consumer<Key> consumer) {
        for (Relation<Key, Value> relation : this.relations) {
            if (relation != null && relation != Relation.SKIP) {
                consumer.accept(relation.getKey());
            }
        }
    }

    protected int hash(Key key) {
        return key.hashCode();
    }

    protected int compress(int hash) {
        return Math.floorMod(hash, this.capacity);
    }

    protected abstract int rehash(int hash, int key, int trial);

    @SuppressWarnings("unchecked")
    private void resize(int capacity) {
        Relation<Key, Value>[] old = this.relations;
        this.relations = new Relation[capacity];
        this.capacity = capacity;
        this.size = 0;
        for (Relation<Key, Value> relation : old) {
            if (relation != null && relation != Relation.SKIP) {
                this.link(relation.getKey(), relation.getValue());
            }
        }
    }
}
