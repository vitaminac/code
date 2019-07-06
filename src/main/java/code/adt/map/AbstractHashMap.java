package code.adt.map;

import java.util.function.Consumer;

public abstract class AbstractHashMap<Key, Value> implements Map<Key, Value> {
    private static final int DEFAULT_INITIAL_CAPACITY = 1 << 4;
    private static final double LOAD_FACTOR = 0.5;
    private static final Relation SKIP = new Relation<>(null, null);
    private int capacity;
    private int size;
    private Relation<Key, Value>[] relations;

    private AbstractHashMap(int capacity) {
        this.init(capacity);
    }

    protected AbstractHashMap() {
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
        this.remove(key); // ensure that we can place at first SKIP position
        if (((double) this.size) / this.capacity > LOAD_FACTOR) this.resize(this.capacity * 2);
        int k = this.hash(key);
        int hash = this.compress(k);
        int index = hash;
        int i = 1;
        for (Relation<Key, Value> relation = this.relations[index]; relation != null && relation != SKIP && !relation.getKey().equals(key); index = this.compress(this.rehash(hash, k, i++)), relation = this.relations[index])
            ;
        this.relations[index] = new Relation<>(key, value);
        ++this.size;
    }

    @Override
    public Value map(Key key) {
        int k = this.hash(key);
        int hash = this.compress(k);
        int index = hash;
        int i = 1;
        for (Relation<Key, Value> relation = this.relations[index]; relation != null && (relation == SKIP || !relation.getKey().equals(key)); index = this.compress(this.rehash(hash, k, i++)), relation = this.relations[index])
            ;
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
        for (Relation<Key, Value> relation = this.relations[index]; relation != null && (relation == SKIP || !relation.getKey().equals(key)); index = this.compress(this.rehash(hash, k, i++)), relation = this.relations[index])
            ;
        if (this.relations[index] == null) {
            return null;
        } else {
            Value value = this.relations[index].getValue();
            this.relations[index] = SKIP;
            --this.size;
            if (this.size < this.capacity / 8) this.resize(this.capacity / 2);
            return value;
        }
    }

    @Override
    public void clear() {
        this.init(DEFAULT_INITIAL_CAPACITY);
    }

    @Override
    public void enumerate(Consumer<Key> consumer) {
        for (Relation<Key, Value> relation : this.relations) {
            if (relation != null && relation != SKIP) {
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
    private void init(int capacity) {
        this.capacity = capacity;
        this.relations = new Relation[this.capacity];
        this.size = 0;
    }

    @SuppressWarnings("unchecked")
    private void resize(int capacity) {
        Relation<Key, Value>[] old = this.relations;
        this.relations = new Relation[capacity];
        this.capacity = capacity;
        this.size = 0;
        for (Relation<Key, Value> relation : old) {
            if (relation != null && relation != SKIP) {
                this.link(relation.getKey(), relation.getValue());
            }
        }
    }
}
